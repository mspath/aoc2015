package day18

import java.io.File

typealias Grid = List<Light>

fun main() {
    val input = File("data/day18/input.txt").readLines()
    //breakfast(input)
    lunch(input)
}

data class Light(val x: Int, val y: Int, var on: Boolean, var next: Boolean = false) {
    fun step() { on = next }
}

fun Grid.lookupNext(light: Light): Boolean {
    val neighborsOn = this.filter { it.x in light.x - 1..light.x + 1
            && it.y in light.y - 1..light.y + 1
            && it != light
            && it.on }.size
    return when(light.on) {
        true -> neighborsOn in 2..3
        else -> neighborsOn == 3
    }
}

fun breakfast(input: List<String>) {
    val lights = input.flatMapIndexed { y, row ->
        row.mapIndexed { x, c ->
            if (c == '#') Light(x, y, true) else Light (x, y, false)
        }
    }
    repeat(100) {
        lights.forEach {
            it.next = lights.lookupNext(it)
        }
        lights.forEach {
            it.step()
        }
    }
    val result = lights.count { it.on }
    println(result)
}

// the corners always stay on as per description
fun Grid.lookupNextCornered(light: Light): Boolean {
    if ((light.x == 0 || light.x == 99) && (light.y == 0 || light.y == 99)) return true
    val neighborsOn = this.filter { it.x in light.x - 1..light.x + 1
            && it.y in light.y - 1..light.y + 1
            && it != light
            && it.on }.size
    return when(light.on) {
        true -> neighborsOn in 2..3
        else -> neighborsOn == 3
    }
}

fun lunch(input: List<String>) {
    val lights = input.flatMapIndexed { y, row ->
        row.mapIndexed { x, c ->
            if ((x == 0 || x == 99) && (y == 0 || y == 99)) Light(x, y, true)
            else if (c == '#') Light(x, y, true) else Light (x, y, false)
        }
    }
    repeat(100) {
        lights.forEach {
            it.next = lights.lookupNextCornered(it)
        }
        lights.forEach {
            it.step()
        }
    }
    val result = lights.count { it.on }
    println(result)
}