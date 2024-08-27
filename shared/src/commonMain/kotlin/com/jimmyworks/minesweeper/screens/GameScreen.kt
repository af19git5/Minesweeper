package com.jimmyworks.minesweeper.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jimmyworks.minesweeper.component.GameComponent
import com.jimmyworks.minesweeper.styles.TextStyle

/**
 * 遊戲畫面
 *
 * @author Jimmy Kang
 */
@Composable
fun GameScreen(component: GameComponent) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "x: ${component.x}, y: ${component.y}, mines count: ${component.minesCount}",
                style = TextStyle.h3Style
            )
        }
    }
}
