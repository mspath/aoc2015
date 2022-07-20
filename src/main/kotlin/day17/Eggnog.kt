package day17

import java.io.File

fun main() {
    val input = File("data/day17/input.txt").readLines()
    val capacity = 150
    breakfast(input, capacity)
}

data class Container(val id: Int, val volume: Int)

fun breakfast(input: List<String>, capacity: Int) {
    val containers = input.map { it.toInt() }.sorted().reversed().mapIndexed { index, i ->
        Container(index, i)
    }
    println(containers)

    val results: MutableList<Container> = mutableListOf()

    containers.forEachIndexed { index, container ->
        val trial: MutableList<Container> = mutableListOf(container)
        val volumeRest = capacity - container.volume
        val containersRest = containers.subList(index + 1, containers.size)
        println(trial)
        println(volumeRest)
        println(containersRest)
        println("--")
    }
}