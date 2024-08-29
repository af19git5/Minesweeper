package com.jimmyworks.minesweeper.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.jimmyworks.minesweeper.styles.Colors
import com.jimmyworks.minesweeper.styles.PaddingStyle
import com.jimmyworks.minesweeper.styles.TextStyle
import minesweeper.shared.generated.resources.Res
import minesweeper.shared.generated.resources.cancel
import minesweeper.shared.generated.resources.confirm
import org.jetbrains.compose.resources.stringResource

/**
 * 提示彈出視窗元件
 *
 * @author Jimmy Kang
 */
@Composable
fun AlertDialog(
    text: String,
    onDismiss: () -> Unit,
    title: String = "",
    enableCancelButton: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    dismissOnBackPress: Boolean = true,
    confirmButtonText: String = stringResource(Res.string.confirm),
    cancelButtonText: String = stringResource(Res.string.cancel),
    onClickConfirm: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = title.takeIf { it.isNotEmpty() }?.let {
            { Text(title, style = TextStyle.h3Style) }
        },
        text = { Text(text, color = Colors.primary, style = TextStyle.miniTextStyle) },
        confirmButton = {
            Text(
                confirmButtonText,
                style = TextStyle.miniTextStyle,
                modifier = Modifier.padding(PaddingStyle.p3Style).clickable {
                    onClickConfirm()
                    onDismiss()
                }
            )
        },
        dismissButton = {
            if (enableCancelButton) {
                Text(
                    cancelButtonText,
                    style = TextStyle.miniTextStyle,
                    modifier = Modifier.padding(PaddingStyle.p3Style).clickable {
                        onDismiss()
                    }
                )
            }
        },
        properties = DialogProperties(
            dismissOnClickOutside = dismissOnClickOutside,
            dismissOnBackPress = dismissOnBackPress,
        )
    )
}

