package com.jimmyworks.minesweeper.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.retainedComponent
import com.jimmyworks.minesweeper.App
import com.jimmyworks.minesweeper.component.RootComponent
import com.jimmyworks.minesweeper.database.getDatabaseBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = retainedComponent {
            RootComponent(it)
        }
        setContent {
            App(root, getDatabaseBuilder(this).build())
        }
    }
}
