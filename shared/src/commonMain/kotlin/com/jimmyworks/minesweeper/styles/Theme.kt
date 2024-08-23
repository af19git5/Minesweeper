package com.jimmyworks.minesweeper.styles

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

class Theme {
    companion object {
        @Composable
        fun AppTheme(content: @Composable () -> Unit) {
            val lightColors = lightColors(
                primary = Colors.primary,
                primaryVariant = Colors.primaryVariant,
                secondary = Colors.secondary,
                secondaryVariant = Colors.secondaryVariant,
                background = Colors.background,
                surface = Colors.surface,
                error = Colors.error,
                onPrimary = Colors.onPrimary,
                onSecondary = Colors.onSecondary,
                onBackground = Colors.onBackground,
                onSurface = Colors.onSurface,
                onError = Colors.onError
            )
            MaterialTheme(
                colors = lightColors, content = content
            )
        }
    }
}
