package com.example.todoa9.model

import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Formatiert einen Zeitstempel in lesbares Datum und Uhrzeit.
 *
 * @param timestamp Zeitstempel in Millisekunden.
 * @return Formatierter Zeitstempel als String.
 */
fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return sdf.format(timestamp)
}