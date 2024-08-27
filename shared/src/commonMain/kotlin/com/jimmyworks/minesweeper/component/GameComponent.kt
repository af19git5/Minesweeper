package com.jimmyworks.minesweeper.component

import com.arkivanov.decompose.ComponentContext

/**
 * 遊戲畫面Component
 *
 * @author Jimmy Kang
 */
class GameComponent(
    componentContext: ComponentContext,
    val x: Int,
    val y: Int,
    val minesCount: Int
) : BasicComponent(componentContext) {}
