package com.jimmyworks.minesweeper.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.jimmyworks.minesweeper.vo.ToastVO

/**
 * 基底component
 *
 * @author Jimmy Kang
 */
open class BasicComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    /** toast訊息 */
    val toastVO = MutableValue(ToastVO())

    /** 返回點選事件 */
    var onBackClicked: () -> Unit = {}

    /** 點擊返回 */
    fun back() {
        onBackClicked()
    }
}
