package com.jimmyworks.minesweeper.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.jimmyworks.minesweeper.views.ToastMessage

class MainComponent(
    componentContext: ComponentContext,
    private val onGameStart: (Int, Int, Int) -> Unit
) : ComponentContext by componentContext {

    val x = MutableValue(9)
    val y = MutableValue(9)
    val minesCount = MutableValue(10)
    val toastMessage = MutableValue(ToastMessage())

    fun onGameStart() {
        this.onGameStart(x.value, y.value, minesCount.value)
    }
}
