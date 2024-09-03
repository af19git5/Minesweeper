package com.jimmyworks.minesweeper

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.stack.animation.isFront
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimator
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.backhandler.BackHandler
import com.jimmyworks.minesweeper.component.RootComponent
import com.jimmyworks.minesweeper.database.getDatabaseBuilder
import platform.UIKit.UIViewController

@OptIn(ExperimentalDecomposeApi::class)
@Suppress("FunctionName", "unused")
fun AppStart(rootComponent: RootComponent): UIViewController =
    ComposeUIViewController {
        PredictiveBackGestureOverlay(
            backDispatcher = rootComponent.backHandler as BackDispatcher,
            backIcon = null,
            modifier = Modifier.fillMaxSize(),
        ) {
            App(rootComponent = rootComponent, getDatabaseBuilder().build())
        }
    }

@OptIn(ExperimentalDecomposeApi::class)
actual fun <C : Any, T : Any> backAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit
): StackAnimation<C, T> {
    return predictiveBackAnimation(
        backHandler = backHandler,
        fallbackAnimation = stackAnimation(iosLikeSlide()),
        selector = { initialBackEvent, _, _ ->
            predictiveBackAnimatable(
                initialBackEvent = initialBackEvent,
                exitModifier = { progress, _ -> Modifier.slideExitModifier(progress = progress) },
                enterModifier = { progress, _ -> Modifier.slideEnterModifier(progress = progress) },
            )
        },
        onBack = onBack,
    )
}

private fun iosLikeSlide(animationSpec: FiniteAnimationSpec<Float> = tween()): StackAnimator {
    return stackAnimator(animationSpec = animationSpec) { factor, direction, content ->
        content(
            Modifier
                .then(if (direction.isFront) Modifier else Modifier.fade(factor + 1F))
                .offsetXFactor(factor = if (direction.isFront) factor else factor * 0.5F)
        )
    }
}

private fun Modifier.slideExitModifier(progress: Float): Modifier {
    return offsetXFactor(progress)
}

private fun Modifier.slideEnterModifier(progress: Float): Modifier {
    return fade(progress).offsetXFactor((progress - 1f) * 0.5f)
}

private fun Modifier.fade(factor: Float): Modifier {
    return drawWithContent {
        drawContent()
        drawRect(color = Color(red = 0F, green = 0F, blue = 0F, alpha = (1F - factor) / 4F))
    }
}

private fun Modifier.offsetXFactor(factor: Float): Modifier {
    return layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(x = (placeable.width.toFloat() * factor).toInt(), y = 0)
        }
    }
}
