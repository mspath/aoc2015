package day8

import java.io.File

fun main() {
    val input = File("data/day8/input.txt").readLines()
    breakfast(input)
}

fun String.lengthMemory(): Int {
    println(this)
    var rest = this.substring(1).dropLast(1)
    val r1 = """\\x..""".toRegex()
    val r2 = """\\\\""".toRegex()
    val r3 = """\\\"""".toRegex()
    rest = rest.replace(r1, "_")
    rest = rest.replace(r2, "_")
    rest = rest.replace(r3, "_")
    println(rest)
    return rest.length
}

// incomplete
fun breakfast(input: List<String>) {
    val lengthCode = input.sumOf { it.length }
    val lengthMemory = input.sumOf { it.lengthMemory() }
    val result = lengthCode - lengthMemory
    println(result)
}