package cz.pocolor.game.logicry.core.ticking

import java.util.LinkedList

class TickWheelImpl(val wheelSize: TickCooldown) : TickWheel() {
    init {
        require(wheelSize > 0) { "wheelSize must be positive." }
    }

    private val wheel = Array(wheelSize) { LinkedList<Tickable>() }
    private var currentIndex = 0

    override fun schedule(entity: Tickable, delay: TickCooldown) {
        assert(delay >= 0) { "delay must be positive or 0 if it shouldn't be scheduled." }
        if (delay == 0) return
        wheel[(currentIndex + delay) % wheelSize].offer(entity)
    }

    override fun tick() {
        val current = wheel[currentIndex]
        while (current.isNotEmpty()) schedule(current.poll()!!.apply { onTick() })
        currentIndex = (currentIndex + 1) % wheelSize
    }
}