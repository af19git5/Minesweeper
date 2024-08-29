package com.jimmyworks.minesweeper.component

import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext

/**
 * 主畫面Component
 *
 * @author Jimmy Kang
 */
class MainComponent(
    componentContext: ComponentContext,
    private val onGameStart: (Int, Int, Int) -> Unit
) : BasicComponent(componentContext) {

    val x = mutableStateOf(9)
    val y = mutableStateOf(9)
    val minesCount = mutableStateOf(10)

    fun onGameStart() {
        this.onGameStart(x.value, y.value, minesCount.value)
    }
}
