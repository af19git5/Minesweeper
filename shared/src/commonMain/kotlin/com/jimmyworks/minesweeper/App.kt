package com.jimmyworks.minesweeper

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.essenty.backhandler.BackHandler
import com.jimmyworks.minesweeper.component.GameComponent
import com.jimmyworks.minesweeper.component.MainComponent
import com.jimmyworks.minesweeper.component.RootComponent
import com.jimmyworks.minesweeper.screens.GameScreen
import com.jimmyworks.minesweeper.screens.MainScreen
import com.jimmyworks.minesweeper.styles.Theme

/**
 * App進入口
 *
 * @author Jimmy Kang
 */
@Composable
fun App(rootComponent: RootComponent) {
    Theme.AppTheme {
        Children(
            stack = rootComponent.childStack,
            animation = backAnimation(
                backHandler = rootComponent.backHandler,
                onBack = rootComponent::onBackClicked
            )
        ) { child ->
            when (val instance = child.instance) {
                is MainComponent -> MainScreen(instance)
                is GameComponent -> GameScreen(instance)
            }
        }
    }
}

/**
 * 返回動畫
 *
 * @param backHandler 返回監聽
 * @param onBack 返回事件
 */
expect fun <C : Any, T : Any> backAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit,
): StackAnimation<C, T>
