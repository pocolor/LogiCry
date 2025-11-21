package cz.pocolor.game.logicry.core.ticking

class GameLoop(private val tickWheel: TickWheel, tps: Long) : Loop(tps, tickWheel::tick)
