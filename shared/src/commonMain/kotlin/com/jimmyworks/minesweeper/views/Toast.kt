package com.jimmyworks.minesweeper.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jimmyworks.minesweeper.styles.Colors
import com.jimmyworks.minesweeper.styles.PaddingStyle
import com.jimmyworks.minesweeper.styles.TextStyle
import com.jimmyworks.minesweeper.vo.ToastVO
import kotlinx.coroutines.delay

/**
 * Toast元件
 *
 * @author Jimmy Kang
 */
@Composable
fun Toast(
    message: State<ToastVO>,
    delay: Long = 2000L
) {
    AnimatedVisibility(message.value.isShow.value) {
        Box(
            modifier = Modifier.fillMaxSize().padding(bottom = PaddingStyle.p2Style),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                color = Colors.primary,
                shape = RoundedCornerShape(5.dp)
            ) {
                Box(
                    modifier = Modifier.padding(PaddingStyle.p3Style, PaddingStyle.p4Style),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        message.value.message,
                        color = Colors.onPrimary,
                        style = TextStyle.textStyle
                    )
                }
                LaunchedEffect(message.value.message) {
                    delay(delay)
                    message.value.isShow.value = false
                }
            }
        }
    }
}

