package day6

import java.io.File

typealias Grid = List<Light>
typealias DimmableGrid = List<DimmableLight>

fun main() {
    val input = File("data/day6/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

data class Command(val type: String, val start: Pair<Int, Int>, val end: Pair<Int, Int>) {

    companion object {
        fun from(command: String): Command? {
            return when {
                command.startsWith("turn on ") -> {
                    val start = command.substringAfter("turn on ")
                        .substringBefore(" through ").split(",").map { it.toInt() }
                    val end = command.substringAfter(" through ").split(",").map { it.toInt() }
                    Command("on", Pair(start[0], start[1]), Pair(end[0], end[1]))
                }
                command.startsWith("turn off ") -> {
                    val start = command.substringAfter("turn off ")
                        .substringBefore(" through ").split(",").map { it.toInt() }
                    val end = command.substringAfter(" through ").split(",").map { it.toInt() }
                    Command("off", Pair(start[0], start[1]), Pair(end[0], end[1]))
                }
                command.startsWith("toggle ") -> {
                    val start = command.substringAfter("toggle ")
                        .substringBefore(" through ").split(",").map { it.toInt() }
                    val end = command.substringAfter(" through ").split(",").map { it.toInt() }
                    Command("toggle", Pair(start[0], start[1]), Pair(end[0], end[1]))
                }
                else -> null
            }
        }
    }
}

data class Light(val x: Int, val y: Int, var on: Boolean = false) {
    fun on() { on = true }
    fun off() { on = false }
    fun toggle() { on = !on }
}

data class DimmableLight(val x: Int, val y: Int, var brightness: Int = 0) {
    fun on() { brightness++ }
    fun off() { if (brightness > 0) brightness-- }
    fun toggle() { brightness += 2 }
}

fun Grid.process(command: Command) {
    val affected = this.filter { it.x in command.start.first..command.end.first
            && it.y in command.start.second..command.end.second }

    affected.forEach {
        when (command.type) {
            "on" -> it.on()
            "off" -> it.off()
            "toggle" -> it.toggle()
        }
    }
}

fun DimmableGrid.processDimmable(command: Command) {
    val affected = this.filter { it.x in command.start.first..command.end.first
            && it.y in command.start.second..command.end.second }

    affected.forEach {
        when (command.type) {
            "on" -> it.on()
            "off" -> it.off()
            "toggle" -> it.toggle()
        }
    }
}

fun breakfast(input: List<String>) {
    val grid = buildList {
        repeat(1000) { y ->
            repeat(1000) { x ->
                this.add(Light(x, y))
            }
        }
    }
    val commands = input.mapNotNull { Command.from(it) }
    commands.forEach {
        grid.process(it)
    }
    val lightsOn = grid.filter { it.on }
    println(lightsOn.size)
}

fun lunch(input: List<String>) {
    val grid = buildList {
        repeat(1000) { y ->
            repeat(1000) { x ->
                this.add(DimmableLight(x, y))
            }
        }
    }
    val commands = input.mapNotNull { Command.from(it) }
    commands.forEach {
        grid.processDimmable(it)
    }
    val result = grid.sumOf { it.brightness }
    println(result)
}