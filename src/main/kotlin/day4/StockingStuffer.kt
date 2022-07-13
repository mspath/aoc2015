package day4

import java.math.BigInteger
import java.security.MessageDigest

fun md5(input:String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

fun main() {
    val saltSample = "abcdef"
    val salt = "yzbqklnj"
    breakfast(salt)
    lunch(salt)
}

fun breakfast(salt: String) {
    var counter = 0
    while (true) {
        counter++
        val hash = md5(salt + counter)
        if (hash.startsWith("00000")) break
    }
    println(counter)
}

fun lunch(salt: String) {
    var counter = 0
    while (true) {
        counter++
        val hash = md5(salt + counter)
        if (hash.startsWith("000000")) break
    }
    println(counter)
}