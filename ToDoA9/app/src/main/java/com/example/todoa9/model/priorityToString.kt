package com.example.todoa9.model

/**
 * Konvertiert die Priorität in einen lesbaren Text.
 *
 * @param priority Prioritätswert (1, 2 oder 3).
 * @return Lesbare Priorität als String.
 */
fun priorityToString(priority: Int): String {
    return when (priority) {
        1 -> "LOW"
        2 -> "MEDIUM"
        3 -> "HIGH"
        else -> "UNKNOWN"
    }
}