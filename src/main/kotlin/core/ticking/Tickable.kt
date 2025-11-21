package cz.pocolor.game.logicry.core.ticking

typealias TickCooldown = Int

interface Tickable {
    val tickCooldown: TickCooldown
    fun onTick()
}