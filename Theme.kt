package com.noidametro.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val AquaPrimary = Color(0xFF0097A7)
val AquaDark = Color(0xFF006978)
val AquaLight = Color(0xFFE0F7FA)
val SurfaceBg = Color(0xFFF5F9FA)

private val LightColors = lightColorScheme(
    primary = AquaPrimary,
    onPrimary = Color.White,
    primaryContainer = AquaLight,
    onPrimaryContainer = AquaDark,
    secondary = Color(0xFF00796B),
    background = SurfaceBg,
    surface = Color.White
)

@Composable
fun NoidaMetroTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}
