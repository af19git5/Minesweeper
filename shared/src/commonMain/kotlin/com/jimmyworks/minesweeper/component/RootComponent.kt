package com.jimmyworks.minesweeper.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.serialization.Serializable

class RootComponent(context: ComponentContext) : ComponentContext by context, BackHandlerOwner {

    /** 導轉器 */
    private val navigation = StackNavigation<Screen>()

    /** 子頁面堆疊 */
    val childStack: Value<ChildStack<Screen, BasicComponent>> = childStack(
        source = navigation,
        serializer = Screen.serializer(),
        initialConfiguration = Screen.MainScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    /**
     * 建立子頁面
     *
     * @param screen 頁面
     * @param context ComponentContext
     */
    private fun createChild(screen: Screen, context: ComponentContext): BasicComponent {
        val component = when (screen) {
            is Screen.MainScreen -> MainComponent(
                context
            ) { x, y, minesCount ->
                navigation.pushNew(Screen.GameScreen(x, y, minesCount))
            }

            is Screen.GameScreen -> GameComponent(
                context, screen.x, screen.y, screen.minesCount
            )
        }
        component.onBackClicked = ::onBackClicked
        return component
    }

    /** 返回鍵點擊事件 */
    fun onBackClicked() {
        navigation.pop()
    }

    /** 頁面 */
    @Serializable
    sealed class Screen {
        @Serializable
        data object MainScreen : Screen()

        @Serializable
        data class GameScreen(
            val x: Int, val y: Int, val minesCount: Int
        ) : Screen()
    }
}
