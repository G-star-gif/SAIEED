package com.saieedcla.app.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColors = lightColors(
    primary = androidx.compose.ui.graphics.Color(0xFF0D47A1),
    secondary = androidx.compose.ui.graphics.Color(0xFF1976D2)
)

@Composable
fun SAIEEDclaTheme(content: @Composable () -> Unit) {
    MaterialTheme(colors = LightColors) {
        content()
    }
}
