package com.jimmyworks.minesweeper.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue

/**
 * 主畫面Component
 *
 * @author Jimmy Kang
 */
class MainComponent(
    componentContext: ComponentContext,
    private val onGameStart: (Int, Int, Int) -> Unit
) : BasicComponent(componentContext) {

    val x = MutableValue(9)
    val y = MutableValue(9)
    val minesCount = MutableValue(10)

    fun onGameStart() {
        this.onGameStart(x.value, y.value, minesCount.value)
    }
}
