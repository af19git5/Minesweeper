package com.jimmyworks.minesweeper.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.jimmyworks.minesweeper.database.AppDatabase
import kotlinx.serialization.Serializable

class RootComponent(context: ComponentContext) : ComponentContext by context, BackHandlerOwner {

    /** 導轉器 */
    private val navigation = StackNavigation<Screen>()

    /** 建立子頁面堆疊 */
    fun createChildStack(appDatabase: AppDatabase): Value<ChildStack<Screen, BasicComponent>> {
        return childStack(
            source = navigation,
            serializer = Screen.serializer(),
            initialConfiguration = Screen.MainScreen,
            handleBackButton = true,
            childFactory = { screen, context -> createChild(screen, context, appDatabase) }
        )
    }

    /**
     * 建立子頁面
     *
     * @param screen 頁面
     * @param context ComponentContext
     * @param appDatabase app資料庫
     */
    private fun createChild(
        screen: Screen,
        context: ComponentContext,
        appDatabase: AppDatabase
    ): BasicComponent {
        val component = when (screen) {
            is Screen.MainScreen -> MainComponent(
                context,
                appDatabase
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
