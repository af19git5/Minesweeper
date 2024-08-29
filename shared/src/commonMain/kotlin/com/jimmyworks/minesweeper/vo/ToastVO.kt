package com.jimmyworks.minesweeper.vo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * Toast訊息物件
 *
 * @author Jimmy Kang
 */
class ToastVO {
    val message: String
    val isShow: MutableState<Boolean>

    constructor() {
        this.message = ""
        isShow = mutableStateOf(false)
    }

    constructor(message: String) {
        this.message = message
        isShow = mutableStateOf(true)
    }
}
