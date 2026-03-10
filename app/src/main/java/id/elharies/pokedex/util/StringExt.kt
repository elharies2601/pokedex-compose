package id.elharies.pokedex.util

import java.security.MessageDigest

fun String.parseIdFromUrl(): Long {
    return this.trimEnd('/').substringAfterLast('/').toLong()
}

fun String.hashPassword(): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val bytes = digest.digest(this.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}

fun String.isValidEmail(): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    return emailRegex.matches(this)
}