package com.jimmyworks.minesweeper.component

import com.arkivanov.decompose.ComponentContext

class GameComponent(
    componentContext: ComponentContext,
    val x: Int,
    val y: Int,
    val minesCount: Int
) : ComponentContext by componentContext {}
