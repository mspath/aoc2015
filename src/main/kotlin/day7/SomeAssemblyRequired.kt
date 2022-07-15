package day7

import java.io.File

typealias Circuit = MutableMap<String, Int>

fun main() {
    val input = File("data/day7/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

data class Instruction(val params: String, val gate: String)

// checks if a param-string is an int literal or looks up the value of the named gate
fun Circuit.getLiteralOrGate(param: String): Int? {
    val value = kotlin.runCatching { param.toInt() }.getOrElse {
        this[param]
    }
    return value
}

fun Circuit.process(instruction: Instruction) {
    when {
        instruction.params.startsWith("NOT ") -> {
            val param = instruction.params.substringAfter("NOT ")
            val value = this.getLiteralOrGate(param)
            value?.let {
                this[instruction.gate] = value.inv() and 0xFFFF
            }
        }
        instruction.params.contains(" OR ") -> {
            val left = this.getLiteralOrGate(instruction.params.substringBefore(" OR "))
            val right = this.getLiteralOrGate(instruction.params.substringAfter(" OR "))
            left?.also { right?.let { this[instruction.gate] = left or right } }
        }
        instruction.params.contains(" AND ") -> {
            val left = this.getLiteralOrGate(instruction.params.substringBefore(" AND "))
            val right = this.getLiteralOrGate(instruction.params.substringAfter(" AND "))
            left?.also { right?.let { this[instruction.gate] = left and right } }
        }
        instruction.params.contains(" RSHIFT ") -> {
            val left = this.getLiteralOrGate(instruction.params.substringBefore(" RSHIFT "))
            val value = instruction.params.substringAfter(" RSHIFT ").toInt()
            left?.let { this[instruction.gate] = left shr value }
        }
        instruction.params.contains(" LSHIFT ") -> {
            val left = this.getLiteralOrGate(instruction.params.substringBefore(" LSHIFT "))
            val value = instruction.params.substringAfter(" LSHIFT ").toInt()
            left?.let { this[instruction.gate] = left shl value }
        }
        // plain assignment
        else -> {
            val value = this.getLiteralOrGate(instruction.params)
            value?.let { this[instruction.gate] = value }
        }
    }
}

fun breakfast(input: List<String>) {
    val instructions = input.map {
        val parts = it.split(" -> ")
        Instruction(parts.first(), parts.last())
    }

    // note the typealias
    val gates: Circuit = mutableMapOf()

    // this is basically looping over a loop over all instructions until the wiring is possible
    while (!gates.contains("a")) {
        instructions.forEach {
            gates.process(it)
        }
    }
    println(gates["a"])
}

// incomplete. I don't fully understand how to rewire in part 2.
fun lunch(input: List<String>) {

    // we repeat our solution for breakfast
    val instructions = input.map {
        val parts = it.split(" -> ")
        Instruction(parts.first(), parts.last())
    }.toMutableList()

    // note the typealias
    val gates: Circuit = mutableMapOf()

    // this is basically looping over a loop over all instructions until the wiring is possible
    while (!gates.contains("a")) {
        instructions.forEach {
            gates.process(it)
        }
    }
    val a = gates.getValue("a")

    // now rewire the Circuit as per instruction and repeat the process
    gates.clear()
    //gates["b"] = a
    instructions.add(Instruction("a", "b"))
    while (!gates.contains("a")) {
        instructions.forEach {
            gates.process(it)
        }
    }
    val finalA = gates.getValue("a")
    println(finalA)
}