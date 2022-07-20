package day16

import java.io.File

fun main() {
    val input = File("data/day16/input.txt").readLines()
    val mfcsam = File("data/day16/mfcsam.txt").readLines()
    breakfast(input, mfcsam)
    lunch(input, mfcsam)
}

data class Aunt(val name: String, val attributes: Set<Pair<String, Int>>) {

    fun check(attribute: Pair<String, Int>, knownAttributes: List<Pair<String, Int>>): Boolean {
        return when(attribute.first) {
            "pomeranians", "goldfish" -> {
                val knownAttribute = knownAttributes.first { it.first == attribute.first }
                attribute.second < knownAttribute.second
            }
            "cats", "trees" -> {
                val knownAttribute = knownAttributes.first { it.first == attribute.first }
                attribute.second > knownAttribute.second
            }
            else -> {
                val knownAttribute = knownAttributes.first { it.first == attribute.first }
                attribute.second == knownAttribute.second
            }
        }
    }

    fun fits(knownAttributes: List<Pair<String, Int>>): Boolean {
        return attributes.all { check(it, knownAttributes) }
    }
}

fun breakfast(input: List<String>, mfcsam: List<String>) {
    val aunts = input.map {
        val name = it.substringBefore(":")
        val tokens = it.substringAfter(": ").split(", ").map { token ->
            val attribute = token.substringBefore(": ")
            val qty = token.substringAfter(": ").toInt()
            Pair(attribute, qty)
        }.toSet()
        Aunt(name, tokens)
    }
    val knownAttributes = mfcsam.map { token ->
        val attribute = token.substringBefore(": ")
        val qty = token.substringAfter(": ").toInt()
        Pair(attribute, qty)
    }
    val aunt = aunts.filter { aunt ->
        aunt.attributes.none { it !in knownAttributes }
    }.first()
    println(aunt.name)
}

fun lunch(input: List<String>, mfcsam: List<String>) {
    val aunts = input.map {
        val name = it.substringBefore(":")
        val tokens = it.substringAfter(": ").split(", ").map { token ->
            val attribute = token.substringBefore(": ")
            val qty = token.substringAfter(": ").toInt()
            Pair(attribute, qty)
        }.toSet()
        Aunt(name, tokens)
    }
    val knownAttributes = mfcsam.map { token ->
        val attribute = token.substringBefore(": ")
        val qty = token.substringAfter(": ").toInt()
        Pair(attribute, qty)
    }
    val aunt = aunts.filter { aunt ->
        aunt.fits(knownAttributes)
    }.first()
    println(aunt.name)
}