package day19

import java.io.File

fun main() {
    val input = File("data/day19/input.txt").readLines()
    val molecule = File("data/day19/molecule.txt").readText()
    breakfast(input, molecule)
}

// returns a list of all indices of given substring or an empty list
fun String.indices(substring: String): List<Int> {
    val indexOfFirst = this.indexOf(substring)
    if (indexOfFirst == -1) return emptyList()
    val indices: MutableList<Int> = mutableListOf(indexOfFirst)
    while (true) {
        val next = this.indexOf(substring, indices.last() + 1)
        if (next == -1) break
        indices.add(next)
    }
    return indices
}

// generates a list of all possible one time replacements
fun String.replacements(from: String, to: String): List<String> {
    val indices = this.indices(from)
    val size = from.length
    val result = indices.map {
        this.replaceRange(it until it + size, to)
    }
    return result
}

fun breakfast(input: List<String>, molecule: String) {
    val rules = input.map {
        it.substringBefore(" => ") to it.substringAfter(" => ")
    }
    val result = rules.flatMap {
        molecule.replacements(it.first, it.second)
    }.toSet().size
    println(result)
}