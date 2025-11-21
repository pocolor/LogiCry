package cz.pocolor.game.logicry.core.ticking

open class Loop(tps: Long, val update: () -> Unit = {}) {
    @Volatile var running = false
        private set
    @Volatile var paused = false
        private set

    var ticksPerSecond: Long = tps
        set(value) {
            require(value > 0L) { "Ticks per second must be positive." }
            field = value
            tickIntervalMs = 1_000_000_000L / value
        }

    private var tickIntervalMs: Long = 1_000_000_000L / ticksPerSecond
    private var thread: Thread? = null

    fun start() {
        if (running) throw IllegalStateException("GameLoop is already running")
        running = true

        thread = Thread(::loop, "GameLoopThread").apply { start() }
    }

    fun stop() {
        running = false
    }

    fun pause() {
        paused = true
    }

    fun resume() {
        paused = false
    }

    fun isRunning() = running && !paused

    private fun loop() {
        var lastTick = System.nanoTime()

        while (running) {
            val now = System.nanoTime()
            val delta = now - lastTick

            if (delta >= tickIntervalMs) {
                if (!paused) update()
                lastTick = now
            } else {
                val sleepMs = (tickIntervalMs - delta) / 1_000_000L
                if (sleepMs > 0) Thread.sleep(sleepMs)
            }
        }
    }
}