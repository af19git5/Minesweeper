package com.jimmyworks.minesweeper.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.jimmyworks.minesweeper.component.MainComponent
import com.jimmyworks.minesweeper.database.entity.SettingEntity
import com.jimmyworks.minesweeper.styles.Colors
import com.jimmyworks.minesweeper.styles.PaddingStyle
import com.jimmyworks.minesweeper.styles.TextStyle
import com.jimmyworks.minesweeper.views.Toast
import com.jimmyworks.minesweeper.vo.ToastVO
import kotlinx.coroutines.runBlocking
import minesweeper.shared.generated.resources.Res
import minesweeper.shared.generated.resources.app_name
import minesweeper.shared.generated.resources.count
import minesweeper.shared.generated.resources.game_size
import minesweeper.shared.generated.resources.game_size_notice
import minesweeper.shared.generated.resources.go
import minesweeper.shared.generated.resources.ic_mines
import minesweeper.shared.generated.resources.mines_over_notice
import minesweeper.shared.generated.resources.number_of_mines
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * 主畫面
 *
 * @author Jimmy Kang
 */
@Composable
fun MainScreen(component: MainComponent) {

    val setting by component.settingFlow.collectAsState(initial = SettingEntity())
    var x by remember { mutableStateOf(setting.x) }
    var y by remember { mutableStateOf(setting.y) }
    var minesCount by remember { mutableStateOf(setting.minesCount) }
    var isEdit by remember { mutableStateOf(false) }
    val toastMessage = component.toast.subscribeAsState()

    // 提示訊息
    val gameSizeNotice = stringResource(Res.string.game_size_notice)
    val minesOverNotice = stringResource(Res.string.mines_over_notice)

    // 鍵盤控制器
    val keyboardController = LocalSoftwareKeyboardController.current

    // 監聽資料庫數值異動時更新資料
    LaunchedEffect(setting) {
        // 如果已修改過就不跟著資料庫異動資料
        if (!isEdit) {
            x = setting.x
            y = setting.y
            minesCount = setting.minesCount
        }
    }

    /** 啟動遊戲 */
    fun gameStart() = runBlocking {
        if (x !in 1..30 || y !in 1..30) {
            component.toast.value = ToastVO(gameSizeNotice)
            return@runBlocking
        }
        if (minesCount !in 0..<x * y) {
            component.toast.value = ToastVO(minesOverNotice)
            return@runBlocking
        }

        setting.x = x
        setting.y = y
        setting.minesCount = minesCount
        component.saveSetting(setting)
        component.gameStart(x, y, minesCount)
    }

    Surface(modifier = Modifier.fillMaxSize().padding(WindowInsets.safeDrawing.asPaddingValues())) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                Image(
                    painter = painterResource(Res.drawable.ic_mines),
                    contentDescription = stringResource(Res.string.app_name),
                    modifier = Modifier.size(60.dp, 60.dp)
                )
                Text(
                    stringResource(Res.string.app_name),
                    style = TextStyle.h1Style,
                    modifier = Modifier.padding(start = PaddingStyle.p4Style)
                )
            }
            Text(
                stringResource(Res.string.game_size),
                style = TextStyle.h2Style,
                modifier = Modifier.padding(top = PaddingStyle.p1Style),
            )
            Row(
                Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "X: ", style = TextStyle.h3Style,
                    color = if (x in 1..30) Colors.primary else Colors.error
                )
                OutlinedTextField(
                    value = x.toString(),
                    onValueChange = {
                        isEdit = true
                        x = it.toIntOrNull() ?: 0
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                    ),
                    isError = x !in 1..30,
                    textStyle = TextStyle.textStyle,
                    modifier = Modifier.padding(start = 5.dp).width(80.dp)
                )
                Text(
                    "Y: ",
                    style = TextStyle.h3Style,
                    modifier = Modifier.padding(start = PaddingStyle.p3Style),
                    color = if (y in 1..30) Colors.primary else Colors.error
                )
                OutlinedTextField(
                    value = y.toString(),
                    onValueChange = {
                        isEdit = true
                        y = it.toIntOrNull() ?: 0
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                    ),
                    isError = y !in 1..30,
                    textStyle = TextStyle.textStyle,
                    modifier = Modifier.padding(start = 5.dp).width(80.dp)
                )
            }
            if (x !in 1..30 || y !in 1..30) {
                Text(
                    gameSizeNotice,
                    style = TextStyle.textStyle,
                    color = Colors.error,
                    modifier = Modifier.padding(top = PaddingStyle.p5Style)
                )
            }
            Text(
                stringResource(Res.string.number_of_mines),
                style = TextStyle.h2Style,
                modifier = Modifier.padding(top = 30.dp),
            )
            Row(
                Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(Res.string.count) + ": ",
                    style = TextStyle.h3Style,
                    color = if (minesCount in 0..<x * y) Colors.primary else Colors.error
                )
                OutlinedTextField(
                    value = minesCount.toString(),
                    onValueChange = {
                        isEdit = true
                        minesCount = it.toIntOrNull() ?: 0
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    isError = minesCount !in 0..<x * y,
                    textStyle = TextStyle.textStyle,
                    modifier = Modifier.padding(start = 5.dp).width(100.dp)
                )
            }
            if (minesCount !in 0..<x * y) {
                Text(
                    minesOverNotice,
                    style = TextStyle.textStyle,
                    color = Colors.error,
                    modifier = Modifier.padding(top = PaddingStyle.p5Style)
                )
            }
            Button(
                onClick = {
                    keyboardController?.hide()
                    gameStart()
                },
                Modifier.padding(top = 40.dp),
            ) {
                Text(
                    stringResource(Res.string.go) + " !", style = TextStyle.h3Style
                )
            }
        }
        Toast(toastMessage)
    }
}
