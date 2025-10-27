package com.kpnorth.knc_app_csi.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    val lightColors = lightColorScheme(
        primary = Color(0xFF0F89E5),
        secondary = Color(0xFF03DAC6),
        background = Color.White,
        surface = Color.White,
        onPrimary = Color.White,
        onSecondary = Color.Black,  // Text/Icon on secondary
        onBackground = Color.Black, // Text on background
        onSurface = Color.Black     // Text on surface (Cards, TextFields)
    )
    val AppShapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(5.dp),
        large = RoundedCornerShape(12.dp)
    )
    MaterialTheme(
        colorScheme = lightColors,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
