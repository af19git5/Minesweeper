package com.jimmyworks.minesweeper.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.jimmyworks.minesweeper.component.GameComponent
import com.jimmyworks.minesweeper.styles.Colors
import com.jimmyworks.minesweeper.styles.PaddingStyle
import com.jimmyworks.minesweeper.styles.TextStyle
import com.jimmyworks.minesweeper.views.AlertDialog
import com.jimmyworks.minesweeper.vo.BlockVO
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.coroutines.delay
import minesweeper.shared.generated.resources.Res
import minesweeper.shared.generated.resources.back
import minesweeper.shared.generated.resources.complete
import minesweeper.shared.generated.resources.game_over
import minesweeper.shared.generated.resources.ic_alarm
import minesweeper.shared.generated.resources.ic_chevron_left
import minesweeper.shared.generated.resources.ic_mines
import minesweeper.shared.generated.resources.ic_reset
import minesweeper.shared.generated.resources.ic_tag
import minesweeper.shared.generated.resources.reset
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration.Companion.seconds

/**
 * 遊戲畫面
 *
 * @author Jimmy Kang
 */
@Composable
@OptIn(ExperimentalResourceApi::class)
fun GameScreen(component: GameComponent) {

    val lastMinesCount by component.lastMinesCount
    val isGameOver by component.isGameOver
    val isWin by component.isWin
    val isShowDialog by component.isShowDialog
    val rowList by component.rowList
    val second by component.second
    val scrollState = rememberScrollState()

    // lottie動畫資源
    val lottieBoom by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/lottie_boom.json").decodeToString()
        )
    }
    val lottieCongratulations by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/lottie_congratulations.json").decodeToString()
        )
    }
    // lottie播放狀態
    val lottieProgress by animateLottieCompositionAsState(
        if (isWin) lottieCongratulations else lottieBoom,
        isPlaying = isGameOver
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(1.seconds)
            component.addSecond()
        }
    }

    Surface(modifier = Modifier.fillMaxSize().padding(WindowInsets.safeDrawing.asPaddingValues())) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(PaddingStyle.p4Style),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painterResource(Res.drawable.ic_chevron_left),
                    contentDescription = stringResource(Res.string.back),
                    colorFilter = ColorFilter.tint(Colors.primary),
                    modifier = Modifier.size(40.dp)
                        .clickable {
                            component.back()
                        })
                Image(
                    painterResource(Res.drawable.ic_reset),
                    contentDescription = stringResource(Res.string.reset),
                    colorFilter = ColorFilter.tint(Colors.error),
                    modifier = Modifier.size(40.dp)
                        .clickable {
                            component.resetGame()
                        })
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painterResource(Res.drawable.ic_tag),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp, 40.dp),
                    colorFilter = ColorFilter.tint(Colors.primary)
                )
                Text(
                    lastMinesCount.toString(),
                    style = TextStyle.h3Style,
                    modifier = Modifier.padding(start = 5.dp)
                )
                Image(
                    painterResource(Res.drawable.ic_alarm),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 20.dp).size(35.dp, 35.dp),
                    colorFilter = ColorFilter.tint(Colors.primary)
                )
                Text(
                    component.formatTime(second),
                    style = TextStyle.h3Style,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(
                    top = PaddingStyle.p2Style,
                    bottom = PaddingStyle.p3Style,
                    start = PaddingStyle.p3Style,
                    end = PaddingStyle.p3Style
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(rowList) { row ->
                    Row(
                        modifier = Modifier
                            .horizontalScroll(scrollState)
                    ) {
                        for (block in row) {
                            Block(component, isGameOver, block)
                        }
                    }
                }
            }
        }
        if (isGameOver) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberLottiePainter(
                    composition = if (isWin) lottieCongratulations else lottieBoom,
                    progress = { lottieProgress }
                ),
                contentDescription = "Lottie animation"
            )
        }
        if (isShowDialog && lottieProgress.equals(1f)) {
            AlertDialog(
                text = if (isWin) stringResource(Res.string.complete) else stringResource(Res.string.game_over),
                enableCancelButton = false,
                onDismiss = {
                    component.closeDialog()
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun Block(component: GameComponent, isGameOver: Boolean, block: BlockVO) {
    val backgroundColor =
        if (block.isOpen.value)
            if (block.isMines.value)
                Colors.error
            else
                Colors.primaryVariant
        else if (isGameOver)
            if (block.isMines.value && !block.isTag.value)
                Colors.primaryVariant
            else if (!block.isMines.value && block.isTag.value)
                Colors.error
            else
                Colors.primary
        else
            Colors.primary
    Box(
        modifier = Modifier.padding(1.5.dp).size(30.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(5.dp)
            )
            .indication(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Colors.onPrimary, radius = 50.dp)
            )
            .combinedClickable(
                onClick = { component.clickBlock(block) },
                onLongClick = { component.tagBlock(block) }
            ),
        contentAlignment = Alignment.Center
    ) {
        if ((block.isOpen.value || isGameOver) && block.isMines.value && !block.isTag.value) {
            Image(
                painter = painterResource(Res.drawable.ic_mines),
                contentDescription = null,
                modifier = Modifier.padding(4.dp)
            )
        } else if (block.isOpen.value && block.aroundMinesCount.value != 0) {
            Text(
                text = block.aroundMinesCount.value.toString(),
                color = Colors.onPrimary,
                style = TextStyle.h3Style
            )
        } else if (!block.isOpen.value && block.isTag.value) {
            Image(
                painter = painterResource(Res.drawable.ic_tag),
                contentDescription = null,
                modifier = Modifier.padding(4.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.background)
            )
        }
    }
}
