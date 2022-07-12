package day2

import java.io.File

fun main() {
    val input = File("data/day2/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

data class Package(val length: Int, val width: Int, val height: Int) {
    fun requiredPaper(): Int {
        val surface = 2 * length * width + 2 * width * height + 2 * height * length
        val sorted = listOf(length, width, height).sorted()
        val extra = sorted[0] * sorted[1]
        return surface + extra
    }

    fun requiredRibbon(): Int {
        val sorted = listOf(length, width, height).sorted()
        val wrap = sorted[0] * 2 + sorted[1] * 2
        val bow = length * width * height
        return wrap + bow
    }
}

fun breakfast(input: List<String>) {
    val packages = input.map { line ->
        val tokens = line.split("x").map { it.toInt() }
        Package(tokens[0], tokens[1], tokens[2])
    }
    val requiredPaper = packages.sumOf { it.requiredPaper() }
    println(requiredPaper)
}

fun lunch(input: List<String>) {
    val packages = input.map { line ->
        val tokens = line.split("x").map { it.toInt() }
        Package(tokens[0], tokens[1], tokens[2])
    }
    val requiredRibbon = packages.sumOf { it.requiredRibbon() }
    println(requiredRibbon)
}