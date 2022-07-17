package day10

fun main() {
    val input = "1113222113"
    breakfast(input)
    lunch(input)
}

// FIXME this is all but elegant
fun transform(input: String): String {
    var current = '_'
    var count = 0
    var result = ""

    input.indices.forEach {
        val char = input[it]
        when (char) {
            current -> count++
            else -> {
                if (current != '_') {
                    result += count
                    result += current
                }
                current = char
                count = 1
            }
        }
    }
    // we need to add the last index manually
    result += count
    result += current
    return result
}

fun breakfast(input: String) {
    println(input)
    var result = input
    repeat(40) {
        result = transform(result)
    }
    println(result)
    println(result.length)
}

fun lunch(input: String) {
    println(input)
    var result = input
    repeat(50) {
        println(it)
        result = transform(result)
    }
    println(result.length)
}