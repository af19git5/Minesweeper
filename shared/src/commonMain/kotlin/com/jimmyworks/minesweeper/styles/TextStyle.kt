package com.jimmyworks.minesweeper.styles

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * 文字樣式
 *
 * @author Jimmy Kang
 */
class TextStyle {
    companion object {
        val h1Style = TextStyle(
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
        )

        val h2Style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
        )

        val h3Style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )

        val textStyle = TextStyle(
            fontSize = 20.sp
        )

        val miniTextStyle = TextStyle(
            fontSize = 16.sp
        )
    }
}
