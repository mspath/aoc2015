package day11

typealias Password = String

fun main() {
    val input = "hxbxwxba"
    val resultBreakfast = breakfast(input)
    println(resultBreakfast)
    val resultLunch = breakfast(resultBreakfast)
    println(resultLunch)
}

fun Password.increment(): Password {
    val chars = this.toCharArray()
    for (position in chars.lastIndex downTo 0) {
        when (chars[position]) {
            'z' -> {
                chars[position] = 'a'
            }
            else -> {
                chars[position]++
                break
            }
        }
    }
    return String(chars)
}

fun Password.validAbc(): Boolean {
    val windows = this.windowed(3)
    return windows.any { (it[1] == it[0] + 1) && (it[2] == it[1] + 1) }
}

fun Password.validAaBb(): Boolean {
    val windows = this.windowed(2)
    val pairs = windows.filter { it[0] == it[1] }.toSet()
    return pairs.size > 1
}

fun Password.isReadable(): Boolean {
    val unreadable = listOf('i', 'o', 'l')
    return unreadable.none { this.contains(it) }
}

fun Password.valid() = this.validAbc() && this.validAaBb() && this.isReadable()

fun breakfast(seed: Password): Password {
    var password = seed.increment()
    while (!password.valid()) {
        password = password.increment()
    }
    return password
}