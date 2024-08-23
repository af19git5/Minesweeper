package com.jimmyworks.minesweeper.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

class RootComponent(context: ComponentContext) : ComponentContext by context {

    private val navigation = StackNavigation<Configuration>()

    val childStack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.MainScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(configuration: Configuration, context: ComponentContext): Child {
        return when (configuration) {
            is Configuration.MainScreen -> Child.MainChild(
                MainComponent(
                    context
                ) { x, y, minesCount ->
                    navigation.pushNew(Configuration.GameScreen(x, y, minesCount))
                }
            )

            is Configuration.GameScreen -> Child.GameChild(
                GameComponent(
                    context,
                    configuration.x,
                    configuration.y,
                    configuration.minesCount
                )
            )
        }
    }

    sealed class Child {
        class MainChild(val component: MainComponent) : Child()
        class GameChild(val component: GameComponent) : Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object MainScreen : Configuration()

        @Serializable
        data class GameScreen(
            val x: Int,
            val y: Int,
            val minesCount: Int
        ) : Configuration()
    }
}
