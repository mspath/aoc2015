package day20

import kotlin.math.sqrt

typealias House = Int
typealias Elv = Int

fun main() {
    val input = 36_000_000
    breakfast(input)
}

fun House.getElves(): List<Elv> {
    val max = sqrt(this.toDouble()).toInt()
    return (1..max).filter { elv ->
        this % elv == 0
    }
}

fun House.presents(): Int {
    val elves = this.getElves()
    return elves.sumOf { it * 10 }
}

fun breakfast(input: Int) {
    var address = 1
    while (address.presents() < input) {
        if (address % 100_000 == 0) println(address)
        address++
    }
    println(address)
    println(address.presents())
}