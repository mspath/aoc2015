package day13

import java.io.File

typealias Neighbors = Pair<String, String>
typealias Rules = Set<Rule>

fun main() {
    val input = File("data/day13/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

// TODO refactor to utils, see day9
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

data class Rule(val guests: Neighbors, val happiness: Int)

fun Rules.lookup(neighbors: Neighbors): Int {
    val (left, right) = listOf(neighbors.first, neighbors.second).sorted()
    val rule = this.first { it.guests.first == left && it.guests.second == right }
    return rule.happiness
}

fun List<String>.totalHappiness(rules: Rules): Int {
    val connections = this + this[0]
    val pairs = connections.windowed(2)
    return pairs.sumOf {
        val pair = Pair(it[0], it[1])
        rules.lookup(pair)
    }
}

fun breakfast(input: List<String>) {
    val names = input.map {
        it.substringBefore(" would ")
    }.toSet().toList()
    val rules = names.flatMap { name ->
        val neighbors = names.filter { it != name }
        neighbors.map { neighbor ->
            val ruleA = input.first {
                it.startsWith(name) && it.contains(neighbor)
            }
            val dispositionA = if (ruleA.contains(" gain ")) 1 else -1
            val valueA = ruleA.split(" ")[3].toInt()
            val ruleB = input.first {
                it.startsWith(neighbor) && it.contains(name)
            }
            val dispositionB = if (ruleB.contains(" gain ")) 1 else -1
            val valueB = ruleB.split(" ")[3].toInt()
            val happiness = valueA * dispositionA + valueB * dispositionB
            val (left, right) = listOf(name, neighbor).sorted()
            Rule(Pair(left, right), happiness)
        }
    }.toSet()
    val possibilities = names.permutations()
    val result = possibilities.maxBy { it.totalHappiness(rules) }
    println(result)
    println(result.totalHappiness(rules))
}

// we just annotate the input with rules for the host
fun addHost(input: List<String>): List<String> {
    val names = input.map {
        it.substringBefore(" would ")
    }.toSet().toList()
    val tmp = input.toMutableList()
    names.forEach { name ->
        tmp.add("$name would gain 0 happiness units by sitting next to Markus.")
        tmp.add("Markus would gain 0 happiness units by sitting next to ${name}.")
    }
    return tmp.toList()
}

fun lunch(inputWithoutHost: List<String>) {
    val input = addHost(inputWithoutHost)
    val names = input.map {
        it.substringBefore(" would ")
    }.toSet().toList()
    val rules = names.flatMap { name ->
        val neighbors = names.filter { it != name }
        neighbors.map { neighbor ->
            val ruleA = input.first {
                it.startsWith(name) && it.contains(neighbor)
            }
            val dispositionA = if (ruleA.contains(" gain ")) 1 else -1
            val valueA = ruleA.split(" ")[3].toInt()
            val ruleB = input.first {
                it.startsWith(neighbor) && it.contains(name)
            }
            val dispositionB = if (ruleB.contains(" gain ")) 1 else -1
            val valueB = ruleB.split(" ")[3].toInt()
            val happiness = valueA * dispositionA + valueB * dispositionB
            val (left, right) = listOf(name, neighbor).sorted()
            Rule(Pair(left, right), happiness)
        }
    }.toSet()
    val possibilities = names.permutations()
    val result = possibilities.maxBy { it.totalHappiness(rules) }
    println(result)
    println(result.totalHappiness(rules))
}
