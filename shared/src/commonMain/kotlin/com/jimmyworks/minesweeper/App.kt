package com.jimmyworks.minesweeper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.essenty.backhandler.BackHandler
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
        val childStack by rootComponent.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.MainChild -> MainScreen(instance.component)
                is RootComponent.Child.GameChild -> GameScreen(instance.component)
            }
        }
    }
}
