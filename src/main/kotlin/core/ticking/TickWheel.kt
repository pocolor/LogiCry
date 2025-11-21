package cz.pocolor.game.logicry.core.ticking

abstract class TickWheel {
    abstract fun schedule(entity: Tickable, delay: TickCooldown = entity.tickCooldown)
    abstract fun tick()
}