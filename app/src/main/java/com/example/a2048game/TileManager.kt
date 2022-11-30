package com.example.a2048game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.a2048game.interfaces.GameManagerCallBack
import com.example.a2048game.interfaces.Sprite
import com.example.a2048game.interfaces.SwipeCallBack
import com.example.a2048game.interfaces.SwipeCallBack.Direction.*
import com.example.a2048game.interfaces.TileManagerCallback
import com.example.a2048game.sprites.Tile
import java.util.*


class TileManager(
    private val resources: Resources,
    private val standardSize: Int,
    private val screenWidth: Int,
    private val screenHeight: Int,
    private val gameManagerCallBack: GameManagerCallBack
) :
    TileManagerCallback, Sprite {
    private val drawables: ArrayList<Int> = ArrayList()
    private val tileBitmaps: HashMap<Int, Bitmap> = HashMap()
    private var matrix = Array(4) {
        arrayOfNulls<Tile>(
            4
        )
    }
    private var moving = false
    private var movingTiles: ArrayList<Tile?>? = null
    private var toSpawn = false
    private var endGame = false

    private fun initBitmaps() {
        drawables.add(R.drawable.one)
        drawables.add(R.drawable.two)
        drawables.add(R.drawable.three)
        drawables.add(R.drawable.four)
        drawables.add(R.drawable.five)
        drawables.add(R.drawable.six)
        drawables.add(R.drawable.seven)
        drawables.add(R.drawable.eight)
        drawables.add(R.drawable.nine)
        drawables.add(R.drawable.ten)
        drawables.add(R.drawable.eleven)
        drawables.add(R.drawable.twelve)
        drawables.add(R.drawable.thirteen)
        drawables.add(R.drawable.fourteen)
        drawables.add(R.drawable.fifteen)
        drawables.add(R.drawable.sixteen)
        for (i in 1..16) {
            val bmp = BitmapFactory.decodeResource(resources, drawables[i - 1])
            val tileBmp = Bitmap.createScaledBitmap(bmp, standardSize, standardSize, false)
            tileBitmaps[i] = tileBmp
        }
    }

    fun initGame() {
        matrix = Array(4) { arrayOfNulls(4) }
        movingTiles = ArrayList()
        var i = 0
        while (i < 2) {
            val x = Random().nextInt(4)
            val y = Random().nextInt(4)
            if (matrix[x][y] == null) {
                val tile = Tile(standardSize, screenWidth, screenHeight, this, x, y)
                matrix[x][y] = tile
            } else {
                i--
            }
            i++
        }
    }

    override fun getBitmap(count: Int): Bitmap {
        return tileBitmaps[count]!!
    }

    override fun draw(canvas: Canvas) {
        for (i in 0..3) {
            for (j in 0..3) {
                if (matrix[i][j] != null) {
                    matrix[i][j]!!.draw(canvas)
                }
            }
        }
        if (endGame)
        {
            gameManagerCallBack.gameOver()
        }
    }

    override fun update() {
        for (i in 0..3) {
            for (j in 0..3) {
                if (matrix[i][j] != null) {
                    matrix[i][j]!!.update()
                }
            }
        }
    }

    fun onSwipe(direction: SwipeCallBack.Direction?) {
        if (!moving) {
            moving = true
            val newMatrix = Array(4) {
                arrayOfNulls<Tile>(
                    4
                )
            }
            when (direction) {
                UP -> {
                    run {
                        var i = 0
                        while (i < 4) {
                            var j = 0
                            while (j < 4) {
                                if (matrix[i][j] != null) {
                                    newMatrix[i][j] = matrix[i][j]
                                    var k = i - 1
                                    while (k >= 0) {
                                        if (newMatrix[k][j] == null) {
                                            newMatrix[k][j] = matrix[i][j]
                                            if (newMatrix[k + 1][j] == matrix[i][j]
                                            ) {
                                                newMatrix[k + 1][j] = null
                                            }
                                        } else if (newMatrix[k][j]!!
                                                .getValue() == matrix[i][j]!!
                                                .getValue() && !newMatrix[k][j]!!
                                                .toIncrement()
                                        ) {
                                            newMatrix[k][j] =
                                                matrix[i][j]!!.increment()
                                            if (newMatrix[k + 1][j] == matrix[i][j]
                                            ) {
                                                newMatrix[k + 1][j] = null
                                            }
                                        } else {
                                            break
                                        }
                                        k--
                                    }
                                }
                                j++
                            }
                            i++
                        }
                    }
                    var i = 0
                    while (i < 4) {
                        var j = 0
                        while (j < 4) {
                            val t = matrix[i][j]
                            var newT: Tile? = null
                            var matrixX = 0
                            var matrixY = 0
                            var a = 0
                            while (a < 4) {
                                var b = 0
                                while (b < 4) {
                                    if (newMatrix[a][b] == t) {
                                        newT = newMatrix[a][b]
                                        matrixX = a
                                        matrixY = b
                                        break
                                    }
                                    b++
                                }
                                a++
                            }
                            if (newT != null) {
                                movingTiles!!.add(t)
                                t!!.move(matrixX, matrixY)
                            }
                            j++
                        }
                        i++
                    }
                }
                DOWN -> {
                    run {
                        var i = 3
                        while (i >= 0) {
                            var j = 0
                            while (j < 4) {
                                if (matrix[i][j] != null) {
                                    newMatrix[i][j] = matrix[i][j]
                                    var k = i + 1
                                    while (k < 4) {
                                        if (newMatrix[k][j] == null) {
                                            newMatrix[k][j] = matrix[i][j]
                                            if (newMatrix[k - 1][j] == matrix[i][j]
                                            ) {
                                                newMatrix[k - 1][j] = null
                                            }
                                        } else if (newMatrix[k][j]!!
                                                .getValue() == matrix[i][j]!!
                                                .getValue() && !newMatrix[k][j]!!
                                                .toIncrement()
                                        ) {
                                            newMatrix[k][j] =
                                                matrix[i][j]!!.increment()
                                            if (newMatrix[k - 1][j] == matrix[i][j]
                                            ) {
                                                newMatrix[k - 1][j] = null
                                            }
                                        } else {
                                            break
                                        }
                                        k++
                                    }
                                }
                                j++
                            }
                            i--
                        }
                    }
                    var i = 3
                    while (i >= 0) {
                        var j = 0
                        while (j < 4) {
                            val t = matrix[i][j]
                            var newT: Tile? = null
                            var matrixX = 0
                            var matrixY = 0
                            var a = 0
                            while (a < 4) {
                                var b = 0
                                while (b < 4) {
                                    if (newMatrix[a][b] == t) {
                                        newT = newMatrix[a][b]
                                        matrixX = a
                                        matrixY = b
                                        break
                                    }
                                    b++
                                }
                                a++
                            }
                            if (newT != null) {
                                movingTiles!!.add(t)
                                t!!.move(matrixX, matrixY)
                            }
                            j++
                        }
                        i--
                    }
                }
                LEFT -> {
                    run {
                        var i = 0
                        while (i < 4) {
                            var j = 0
                            while (j < 4) {
                                if (matrix[i][j] != null) {
                                    newMatrix[i][j] = matrix[i][j]
                                    var k = j - 1
                                    while (k >= 0) {
                                        if (newMatrix[i][k] == null) {
                                            newMatrix[i][k] = matrix[i][j]
                                            if (newMatrix[i][k + 1] == matrix[i][j]
                                            ) {
                                                newMatrix[i][k + 1] = null
                                            }
                                        } else if (newMatrix[i][k]!!
                                                .getValue() == matrix[i][j]!!
                                                .getValue() && !newMatrix[i][k]!!
                                                .toIncrement()
                                        ) {
                                            newMatrix[i][k] =
                                                matrix[i][j]!!.increment()
                                            if (newMatrix[i][k + 1] == matrix[i][j]
                                            ) {
                                                newMatrix[i][k + 1] = null
                                            }
                                        } else {
                                            break
                                        }
                                        k--
                                    }
                                }
                                j++
                            }
                            i++
                        }
                    }
                    var i = 0
                    while (i < 4) {
                        var j = 0
                        while (j < 4) {
                            val t = matrix[i][j]
                            var newT: Tile? = null
                            var matrixX = 0
                            var matrixY = 0
                            var a = 0
                            while (a < 4) {
                                var b = 0
                                while (b < 4) {
                                    if (newMatrix[a][b] == t) {
                                        newT = newMatrix[a][b]
                                        matrixX = a
                                        matrixY = b
                                        break
                                    }
                                    b++
                                }
                                a++
                            }
                            if (newT != null) {
                                movingTiles!!.add(t)
                                t!!.move(matrixX, matrixY)
                            }
                            j++
                        }
                        i++
                    }
                }
                RIGHT -> {
                    run {
                        var i = 0
                        while (i < 4) {
                            var j = 3
                            while (j >= 0) {
                                if (matrix[i][j] != null) {
                                    newMatrix[i][j] = matrix[i][j]
                                    var k = j + 1
                                    while (k < 4) {
                                        if (newMatrix[i][k] == null) {
                                            newMatrix[i][k] = matrix[i][j]
                                            if (newMatrix[i][k - 1] == matrix[i][j]
                                            ) {
                                                newMatrix[i][k - 1] = null
                                            }
                                        } else if (newMatrix[i][k]!!
                                                .getValue() == matrix[i][j]!!
                                                .getValue() && !newMatrix[i][k]!!
                                                .toIncrement()
                                        ) {
                                            newMatrix[i][k] =
                                                matrix[i][j]!!.increment()
                                            if (newMatrix[i][k - 1] == matrix[i][j]
                                            ) {
                                                newMatrix[i][k - 1] = null
                                            }
                                        } else {
                                            break
                                        }
                                        k++
                                    }
                                }
                                j--
                            }
                            i++
                        }
                    }
                    var i = 0
                    while (i < 4) {
                        var j = 3
                        while (j >= 0) {
                            val t = matrix[i][j]
                            var newT: Tile? = null
                            var matrixX = 0
                            var matrixY = 0
                            var a = 0
                            while (a < 4) {
                                var b = 0
                                while (b < 4) {
                                    if (newMatrix[a][b] == t) {
                                        newT = newMatrix[a][b]
                                        matrixX = a
                                        matrixY = b
                                        break
                                    }
                                    b++
                                }
                                a++
                            }
                            if (newT != null) {
                                movingTiles!!.add(t)
                                t!!.move(matrixX, matrixY)
                            }
                            j--
                        }
                        i++
                    }
                }
            }
            for (i in 0..3) {
                for (j in 0..3) {
                    if (newMatrix[i][j] !== matrix[i][j]) {
                        toSpawn = true
                        break
                    }
                }
            }
            matrix = newMatrix
        }
    }

    override fun finishedMoving(tile: Tile) {

        movingTiles!!.remove(tile)
        if (movingTiles!!.isEmpty()) {
            moving = false
            spawn()
            checkEndGame()
        }
    }

    override fun updateScore(score: Int) {
        gameManagerCallBack.updateScore(score)
    }


    private fun spawn() {
        if (toSpawn) {
            toSpawn = false
            var t: Tile? = null
            while (t == null) {
                val x = Random().nextInt(4)
                val y = Random().nextInt(4)
                if (matrix[x][y] == null) {
                    t = Tile(standardSize, screenWidth, screenHeight, this, x, y)
                    matrix[x][y] = t
                }
            }
        }
    }

    fun checkEndGame()
    {
        endGame = true
        for (i in 0..3)
        {
            for (j in 0..3)
            {
                if (matrix[i][j] == null)
                {
                    endGame = false
                    break
                }
            }
        }
        if (endGame)
        {
            for (i in 0..3) {
                for (j in 0..3) {
                    if (i > 0 && matrix[i - 1][j]!!.getValue() == matrix[i][j]!!.getValue() ||
                        i < 3 && matrix[i + 1][j]!!.getValue() == matrix[i][j]!!.getValue() ||
                        j > 0 && matrix[i][j - 1]!!.getValue() == matrix[i][j]!!.getValue() ||
                        j < 3 && matrix[i][j + 1]!!.getValue() == matrix[i][j]!!.getValue()
                    ) {
                        endGame = false
                    }
                }
            }
        }
    }


    init {
        initBitmaps()
        initGame()
    }
}