package com.jimmyworks.minesweeper

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.jimmyworks.minesweeper.component.RootComponent

@Suppress("FunctionName", "unused")
fun IosApp() = ComposeUIViewController {
    val rootComponent = remember { RootComponent(DefaultComponentContext(LifecycleRegistry())) }
    App(rootComponent)
}
