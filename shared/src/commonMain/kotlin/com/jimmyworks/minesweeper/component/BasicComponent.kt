package com.jimmyworks.minesweeper.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.jimmyworks.minesweeper.views.ToastMessage

/**
 * 基底component
 *
 * @author Jimmy Kang
 */
open class BasicComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    /** toast訊息 */
    val toastMessage = MutableValue(ToastMessage())
}
