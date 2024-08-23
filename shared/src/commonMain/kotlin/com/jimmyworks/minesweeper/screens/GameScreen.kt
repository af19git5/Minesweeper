package com.jimmyworks.minesweeper.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jimmyworks.minesweeper.component.GameComponent
import com.jimmyworks.minesweeper.styles.PaddingStyle
import com.jimmyworks.minesweeper.styles.TextStyle

/**
 * 遊戲畫面
 *
 * @author Jimmy Kang
 */
@Composable
fun GameScreen(component: GameComponent) {

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
