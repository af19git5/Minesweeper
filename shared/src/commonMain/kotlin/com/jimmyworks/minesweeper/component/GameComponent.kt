package com.jimmyworks.minesweeper.component

import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import com.jimmyworks.minesweeper.vo.BlockVO
import kotlin.random.Random

/**
 * 遊戲畫面Component
 *
 * @author Jimmy Kang
 */
class GameComponent(
    componentContext: ComponentContext,
    private val x: Int,
    private val y: Int,
    private val minesCount: Int
) : BasicComponent(componentContext) {
    /** 剩餘地雷數 */
    val lastMinesCount = mutableStateOf(minesCount)

    /** 是否遊戲結束 */
    var isGameOver = mutableStateOf(false)

    /** 是否勝利 */
    var isWin = mutableStateOf(false)

    /** 是否顯示彈窗 */
    var isShowDialog = mutableStateOf(false)

    /** 是否初始化地雷 */
    private var isInitMines = false

    /** 累積遊玩秒數 */
    var second = mutableStateOf(0)

    /** 格子資料 */
    val rowList = mutableStateOf(List(y) { row ->
        List(x) { column ->
            BlockVO(
                id = column + row * y,
                x = column,
                y = row
            )
        }
    })

    /** 重置遊戲 */
    fun resetGame() {
        for (columnList in rowList.value) {
            for (block in columnList) {
                block.isOpen.value = false
                block.isTag.value = false
                block.isMines.value = false
                block.aroundMinesCount.value = 0
            }
        }
        isInitMines = false
        isGameOver.value = false
        isWin.value = false
        isShowDialog.value = false
        lastMinesCount.value = minesCount
        second.value = 0
    }

    /**
     * 初始化地雷
     *
     * @param excludeId 排除代碼
     */
    private fun initMines(excludeId: Int) {
        if (isInitMines) {
            return
        }
        isInitMines = true
        // 隨機生成地雷
        val minesSet = hashSetOf<Int>()
        while (minesSet.size < minesCount) {
            val randomNumber = Random.nextInt(0, x * y)
            if (randomNumber != excludeId) {
                minesSet.add(randomNumber)
            }
        }
        // 標記地雷
        for (id in minesSet) {
            rowList.value[id / x][id % x].isMines.value = true
        }
        // 標記周圍地雷數
        for (columnList in rowList.value) {
            for (block in columnList) {
                for (aroundBlock in getAroundBlockList(block)) {
                    if (aroundBlock.isMines.value) {
                        block.aroundMinesCount.value++
                    }
                }
            }
        }
    }

    /**
     * 取得傳入格子周圍的格子
     *
     * @param block 格子資訊
     */
    private fun getAroundBlockList(block: BlockVO): MutableList<BlockVO> {
        val aroundBlockList = mutableListOf<BlockVO>()
        val position = listOf(-1, 0, 1)
        for (xRound in position) {
            for (yRound in position) {
                if (xRound == 0 && yRound == 0) {
                    continue
                }
                val pickX = block.x + xRound
                val pickY = block.y + yRound
                if (pickX < 0 || pickY < 0 || pickX >= x || pickY >= y) {
                    continue
                }
                aroundBlockList.add(rowList.value[pickY][pickX])
            }
        }
        return aroundBlockList
    }

    /** 計算剩餘地雷數 */
    private fun countLastMines() {
        var i = 0
        for (columnList in rowList.value) {
            for (block in columnList) {
                if (!block.isOpen.value && block.isTag.value) {
                    i++
                }
            }
        }
        lastMinesCount.value = if (isGameOver.value) 0 else minesCount - i
    }

    /**
     * 執行掃雷
     *
     * @param block 格子資訊
     */
    private fun doSweep(block: BlockVO) {
        block.isOpen.value = true
        checkIsGameOver()
        if (block.isMines.value || block.aroundMinesCount.value > 0) {
            return
        }
        for (aroundBlock in getAroundBlockList(block)) {
            if (aroundBlock.isMines.value) {
                block.aroundMinesCount.value++
            }
            if (!aroundBlock.isOpen.value) {
                aroundBlock.isOpen.value = true
                doSweep(aroundBlock)
            }
        }
    }

    /**
     * 檢查是否已經遊戲結束
     */
    private fun checkIsGameOver() {
        var openCount = 0
        for (columnList in rowList.value) {
            for (block in columnList) {
                if (block.isOpen.value) {
                    openCount++
                    if (block.isMines.value) {
                        isGameOver.value = true
                        isShowDialog.value = true
                        return
                    }
                }
            }
        }
        if (openCount == x * y - minesCount) {
            isGameOver.value = true
            isWin.value = true
            isShowDialog.value = true
        }
    }

    /** 增加計時秒數 */
    fun addSecond() {
        if (isGameOver.value) {
            return
        }
        second.value++
    }

    /**
     * 整理時間顯示格式
     *
     * @param second 秒數
     * @return 整理後時間
     */
    fun formatTime(second: Int): String {
        return if (second < 10) {
            "00$second"
        } else if (second < 100) {
            "0$second"
        } else if (second < 1000) {
            second.toString()
        } else {
            "999"
        }
    }

    /**
     * 點擊格子
     *
     * @param block 格子資訊
     */
    fun clickBlock(block: BlockVO) {
        if (isGameOver.value || block.isTag.value) {
            return
        }
        initMines(block.id)
        doSweep(block)
        countLastMines()
    }

    /**
     * 標記格子
     *
     * @param block 格子資訊
     */
    fun tagBlock(block: BlockVO) {
        if (isGameOver.value || block.isOpen.value) {
            return
        }
        block.isTag.value = !block.isTag.value
        countLastMines()
    }

    /** 關閉提示窗 */
    fun closeDialog() {
        isShowDialog.value = false
    }
}
