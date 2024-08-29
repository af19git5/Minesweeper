package com.jimmyworks.minesweeper.vo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * 格子資料
 *
 * @author Jimmy Kang
 */
data class BlockVO(
    val id: Int,
    val x: Int,
    val y: Int,
    var isOpen: MutableState<Boolean> = mutableStateOf(false),
    var isTag: MutableState<Boolean> = mutableStateOf(false),
    var isMines: MutableState<Boolean> = mutableStateOf(false),
    var aroundMinesCount: MutableState<Int> = mutableStateOf(0)
)
