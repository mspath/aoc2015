package day5

import java.io.File

fun main() {
    val input = File("data/day5/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

fun String.vowels() = this.filter { it in "aeiou" }.length > 2

fun String.doubles(): Boolean {
    val windows = this.windowed(2, 1)
    return windows.any { it[0] == it[1] }
}

fun String.baddies(): Boolean {
    val baddies = listOf("ab", "cd", "pq", "xy")
    return baddies.none { baddie ->
        this.contains(baddie)
    }
}

fun String.nice(): Boolean {
    return this.vowels() && this.doubles() && this.baddies()
}

fun breakfast(input: List<String>) {
    val result = input.filter { it.nice() }.size
    println(result)
}

fun String.pairOfPair(): Boolean {
    val windows = this.windowed(2, 1)
    return windows.any { this.indexOf(it) < this.lastIndexOf(it) - 1 }
}

fun String.aba(): Boolean {
    val windows = this.windowed(3, 1)
    return windows.any { it[0] == it[2] }
}

fun String.nicer(): Boolean {
    return this.pairOfPair() && this.aba()
}

fun lunch(input: List<String>) {
    val result = input.filter { it.nicer() }.size
    println(result)
}