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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.jimmyworks.minesweeper.component.MainComponent
import com.jimmyworks.minesweeper.styles.Colors
import com.jimmyworks.minesweeper.styles.PaddingStyle
import com.jimmyworks.minesweeper.styles.TextStyle
import com.jimmyworks.minesweeper.views.Toast
import com.jimmyworks.minesweeper.vo.ToastVO
import minesweeper.shared.generated.resources.Res
import minesweeper.shared.generated.resources.app_name
import minesweeper.shared.generated.resources.count
import minesweeper.shared.generated.resources.game_size
import minesweeper.shared.generated.resources.game_size_notice
import minesweeper.shared.generated.resources.go
import minesweeper.shared.generated.resources.ic_mines
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

    val x by component.x
    val y by component.y
    val minesCount by component.minesCount
    val toastMessage = component.toastVO.subscribeAsState()

    // 提示訊息
    val gameSizeNotice = stringResource(Res.string.game_size_notice)

    // 鍵盤控制器
    val keyboardController = LocalSoftwareKeyboardController.current

    /** 啟動遊戲 */
    fun gameStart() {
        if (component.x.value !in 1..30 || component.y.value !in 1..30) {
            component.toastVO.value = ToastVO(gameSizeNotice)
            return
        }
        component.onGameStart()
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
                    color = if (component.x.value in 1..30) Colors.primary else Colors.error
                )
                OutlinedTextField(
                    value = x.toString(),
                    onValueChange = { component.x.value = it.toIntOrNull() ?: 0 },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                    ),
                    isError = component.x.value !in 1..30,
                    textStyle = TextStyle.textStyle,
                    modifier = Modifier.padding(start = 5.dp).width(80.dp)
                )
                Text(
                    "Y: ",
                    style = TextStyle.h3Style,
                    modifier = Modifier.padding(start = PaddingStyle.p3Style),
                    color = if (component.y.value in 1..30) Colors.primary else Colors.error
                )
                OutlinedTextField(
                    value = y.toString(),
                    onValueChange = { component.y.value = it.toIntOrNull() ?: 0 },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                    ),
                    isError = component.y.value !in 1..30,
                    textStyle = TextStyle.textStyle,
                    modifier = Modifier.padding(start = 5.dp).width(80.dp)
                )
            }
            if (component.x.value !in 1..30 || component.y.value !in 1..30) {
                Text(
                    gameSizeNotice,
                    style = TextStyle.textStyle,
                    color = Colors.error,
                    modifier = Modifier.padding(top = PaddingStyle.p5Style),
                )
            }
            Text(
                stringResource(Res.string.number_of_mines),
                style = TextStyle.h2Style,
                modifier = Modifier.padding(top = 30.dp)
            )
            Row(
                Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(Res.string.count) + ": ", style = TextStyle.h3Style)
                OutlinedTextField(
                    value = minesCount.toString(),
                    onValueChange = {
                        component.minesCount.value = it.toIntOrNull() ?: 1
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    textStyle = TextStyle.textStyle,
                    modifier = Modifier.padding(start = 5.dp).width(100.dp)
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
