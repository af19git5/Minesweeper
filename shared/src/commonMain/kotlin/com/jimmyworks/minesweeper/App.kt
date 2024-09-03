package com.jimmyworks.minesweeper

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.essenty.backhandler.BackHandler
import com.jimmyworks.minesweeper.component.GameComponent
import com.jimmyworks.minesweeper.component.MainComponent
import com.jimmyworks.minesweeper.component.RootComponent
import com.jimmyworks.minesweeper.database.AppDatabase
import com.jimmyworks.minesweeper.database.dao.SettingDAO
import com.jimmyworks.minesweeper.database.entity.SettingEntity
import com.jimmyworks.minesweeper.screens.GameScreen
import com.jimmyworks.minesweeper.screens.MainScreen
import com.jimmyworks.minesweeper.styles.Theme

/**
 * App進入口
 *
 * @author Jimmy Kang
 */
@Composable
fun App(rootComponent: RootComponent, appDatabase: AppDatabase) {
    var isInitDone by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        initDataBaseData(appDatabase)
        isInitDone = true
    }
    if (!isInitDone) {
        return
    }
    Theme.AppTheme {
        Surface {
            Children(
                stack = rootComponent.createChildStack(appDatabase),
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
}

/**
 * 初始化資料庫資料
 *
 * @param appDatabase app資料庫
 */
private suspend fun initDataBaseData(appDatabase: AppDatabase) {
    initSettingData(appDatabase.settingDAO())
}

/**
 * 初始化設定資料
 *
 * @param settingDAO SettingDAO
 */
private suspend fun initSettingData(settingDAO: SettingDAO) {
    if (settingDAO.count() > 0) {
        return
    }
    settingDAO.insert(SettingEntity())
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
