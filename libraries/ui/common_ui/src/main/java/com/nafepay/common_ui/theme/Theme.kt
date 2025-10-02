package com.nafepay.common_ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = NafepayPurple,
    onPrimary = Color.White,
    secondary = DarkGrey,
    onSecondary = NafepayBlue,
    background = Color.White,
    onBackground = NafepayBlue,
    surface = Color.White,
    onSurface = NafepayBlue,
)

private val LightColorScheme = lightColorScheme(
    primary = NafepayBlue,
    onPrimary = Color.White,
    secondary = MainGrey,
    onSecondary = DarkThemeBlack,
    background = Color.White,
    onBackground = DarkThemeBlack,
    surface = Color.White,
    onSurface = DarkThemeBlack,
)

@Composable
fun AzoTheme(
    darkTheme: Boolean = ThemeHelper.isDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography, // use Material3 Typography
        shapes = Shapes,         // use Material3 Shapes
        content = content
    )
}
