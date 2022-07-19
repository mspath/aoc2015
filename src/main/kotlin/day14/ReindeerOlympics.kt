package day14

import java.io.File
import java.lang.Integer.min

fun main() {
    val input = File("data/day14/input.txt").readLines()
    val raceEnd = 2503
    breakfast(input, raceEnd)
    lunch(input, raceEnd)
}

data class Rule(val name: String, val speed: Int, val duration: Int, val rest: Int) {

    val cycle = duration + rest
    val cycleDistance = speed * duration

    fun distance(time: Int): Int {
        // we calculate it by adding the blocks of completed cycles and the current cycle
        val cycles = time / cycle
        val cyclesDistance = cycles * cycleDistance

        val rem = time % cycle
        val remDistance = min(rem, duration) * speed
        return cyclesDistance + remDistance
    }
}

fun breakfast(input: List<String>, raceEnd: Int) {
    val rules = input.map {
        val tokens = it.split(" ")
        val name = tokens[0]
        val speed = tokens[3].toInt()
        val duration = tokens[6].toInt()
        val rest = tokens[13].toInt()
        Rule(name, speed, duration, rest)
    }
    val winner = rules.maxBy { it.distance(raceEnd) }
    println(winner)
    println(winner.distance(raceEnd))
}

fun lunch(input: List<String>, raceEnd: Int) {
    val rules = input.map {
        val tokens = it.split(" ")
        val name = tokens[0]
        val speed = tokens[3].toInt()
        val duration = tokens[6].toInt()
        val rest = tokens[13].toInt()
        Rule(name, speed, duration, rest)
    }
    val scoreboard: MutableMap<Rule, Int> = mutableMapOf()
    repeat(raceEnd) { index ->
        val t = index + 1
        val max = rules.maxOf { it.distance(t) }
        val leaders = rules.filter { it.distance(t) == max }
        leaders.forEach {
            scoreboard[it] = scoreboard.getOrDefault(it, 0) + 1
        }
    }
    val winner = scoreboard.maxBy { it.value }
    println(winner.key)
    println(winner.value)
}