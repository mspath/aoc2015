package day15

import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min

typealias Recipe = List<Ingredient>

fun main() {
    val input = File("data/day15/input.txt").readLines()
    breakfast(input)
}

data class Ingredient(val name: String,
                      val capacity: Int,
                      val durability: Int,
                      val flavor: Int,
                      val texture: Int,
                      val calories: Int)

// generate all possible combinations of 4 ingredients adding up to 100 teaspoons
fun teaspoons(): List<List<Int>> {
    return (0..100).flatMap { a ->
        (0..100 - a).flatMap { b ->
            (0..100 - a - b).flatMap { c ->
                (0..100 - a - b - c).mapNotNull { d ->
                    if (a + b + c + d == 100) listOf(a, b, c, d) else null
                }
            }
        }
    }.toList()
}

fun Recipe.score(teaspoons: List<Int>): Int {
    val capacityTotal = max(this.sumOf { it.capacity * teaspoons[0] }, 0)
    val durabilityTotal = max(this.sumOf { it.durability * teaspoons[1] }, 0)
    val flavorTotal = max(this.sumOf { it.flavor * teaspoons[2] }, 0)
    val textureTotal = max(this.sumOf { it.texture * teaspoons[3] }, 0)
    return capacityTotal * durabilityTotal * flavorTotal * textureTotal
}

fun breakfast(input: List<String>) {
    val regex = """^(\w+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (\d+)""".toRegex()
    val ingredients = input.mapNotNull {
        regex.matchEntire(it)?.destructured?.let { (name, capacity, durability, flavor, texture, calories) ->
            Ingredient(name, capacity.toInt(), durability.toInt(), flavor.toInt(), texture.toInt(), calories.toInt() ) }
    }
    val teaspoons = teaspoons()
    val best = teaspoons.maxBy { ingredients.score(it) }
    println(best)
    println(ingredients.score(best))
}