package day12

import java.io.File

fun main() {
    val input = File("data/day12/input.json").readText()
    breakfast(input)
}

fun breakfast(json: String) {
    val regexNumbers = Regex("""(-?\d+)""")
    val matches = regexNumbers.findAll(json)
    val sum = matches.sumOf { it.value.toInt() }
    println(sum)
}