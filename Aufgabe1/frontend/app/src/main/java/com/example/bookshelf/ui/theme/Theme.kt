package com.example.bookshelf.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF282828),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000)
)

// Dark Theme Farben
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),
    onPrimary = Color.Black,
    background = Color(0xFF1C1C1C),
    onBackground = Color.White,
    surface = Color(0xFF121212),
    onSurface = Color.White
)
@Composable
fun BookShelfTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors: ColorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}