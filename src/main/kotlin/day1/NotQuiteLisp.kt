package day1

import java.io.File

fun main() {
    val input = File("data/day1/input.txt").readText()
    breakfast(input)
    lunch(input)
}

fun breakfast(instructions: String) {
    val instructionsGrouped = instructions.groupingBy { it }.eachCount()
    val finalFloor = instructionsGrouped.getValue('(') - instructionsGrouped.getValue(')')
    println("final floor: $finalFloor")
}

fun lunch(instructions: String) {
    var floor = 0
    var counter = 0
    instructions.forEach { instruction ->
        when (instruction) {
            '(' -> floor++
            ')' -> floor--
        }
        counter++
        if (floor < 0) {
            println("first time in basement at position: $counter")
            return
        }
    }
}