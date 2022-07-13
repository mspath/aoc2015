package day3

import java.io.File

fun main() {
    val input = File("data/day3/input.txt").readText()
    breakfast(input)
    lunch(input)
}

data class House(val x: Int, val y: Int) {
    fun neighbor(direction: Char) = when(direction) {
        '>' -> House(x + 1, y)
        '<' -> House(x - 1, y)
        'v' -> House(x, y + 1)
        '^' -> House(x, y - 1)
        else -> this
    }
}

fun breakfast(input: String) {
    val visited: MutableSet<House> = mutableSetOf()
    var house = House(0, 0)
    visited.add(house)
    input.forEach { direction ->
        val next = house.neighbor(direction)
        visited.add(next)
        house = next
    }
    val result = visited.size
    println(result)
}

fun lunch(input: String) {
    val visited: MutableSet<House> = mutableSetOf()
    var house = House(0, 0)
    visited.add(house)
    val directionsSanta = input.filterIndexed { index, _ ->
        index % 2 == 0
    }
    val directionsRoboSanta = input.filterIndexed { index, _ ->
        index % 2 == 1
    }
    directionsSanta.forEach { direction ->
        val next = house.neighbor(direction)
        visited.add(next)
        house = next
    }
    house = House(0, 0)
    directionsRoboSanta.forEach { direction ->
        val next = house.neighbor(direction)
        visited.add(next)
        house = next
    }
    val result = visited.size
    println(result)
}