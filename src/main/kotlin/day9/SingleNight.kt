package day9

import java.io.File

typealias Distances = Map<Pair<String, String>, Int>

fun main() {
    val input = File("data/day9/input.txt").readLines()
    breakfast(input)
}

fun Distances.lookup(trip: Pair<String, String>) = this.getValue(trip)

fun List<String>.tripTotal(distances: Distances): Int {
    val trips = this.windowed(2)
    return trips.sumOf { distances.lookup(Pair(it[0], it[1])) }
}

fun <T> Collection<T>.permutations(): Set<List<T>> {
    if (isEmpty()) return setOf(emptyList())
    val result: MutableSet<List<T>> = mutableSetOf()
    for (element in this) {
        (this - element).permutations().forEach { item ->
            result.add(item + element)
        }
    }
    return result
}

// includes lunch
fun breakfast(input: List<String>) {
    val destinations = input.flatMap {
        val left = it.substringBefore(" to ")
        val right = it.substringAfter(" to ").substringBefore(" = ")
        listOf(left, right)
    }.toSet()
    // note the typealias
    val distances: Distances = input.flatMap {
        val left = it.substringBefore(" to ")
        val right = it.substringAfter(" to ").substringBefore(" = ")
        val distance = it.substringAfter(" = ").toInt()
        listOf(Pair(left, right) to distance, Pair(right, left) to distance)
    }.toMap()

    val permutations = destinations.permutations()
    val shortest = permutations.sortedBy { it.tripTotal(distances) }.first()
    println(shortest.tripTotal(distances))
    val longest = permutations.sortedBy { it.tripTotal(distances) }.last()
    println(longest.tripTotal(distances))
}