package com.example.a2048game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.a2048game.interfaces.Sprite
import com.example.a2048game.interfaces.SwipeCallBack
import com.example.a2048game.interfaces.TileManagerCallback
import com.example.a2048game.sprites.Tile


class TileManager(val resources: Resources ,val standardSize:Int ,val screenWidth:Int ,val screenHeight:Int):TileManagerCallback, Sprite {


    private val drawables: ArrayList<Int> = ArrayList()
    private val tileBitmaps: HashMap<Int, Bitmap> = HashMap()
    private var matrix = Array(4){ arrayOfNulls<Tile>(4) }
    private val moving = false
    private var movingTiles: ArrayList<Tile>? = null


    init {
        initBitmaps()
        initGame()
    }

    fun initBitmaps()
    {
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

        for (i in 1..16)
        {
            val bitmap = BitmapFactory.decodeResource(resources , drawables[i-1])
            val tileBitmap = Bitmap.createScaledBitmap(bitmap ,standardSize ,standardSize ,false)
            tileBitmaps[i] = tileBitmap
        }
    }

    fun initGame() {

        matrix = Array(4) { arrayOfNulls(4) }
        var i = 0
        while (i < 5) {
            val x = java.util.Random().nextInt(4)
            val y = java.util.Random().nextInt(4)
            if (matrix[x][y] == null) {
                val tile = Tile(standardSize, screenWidth, screenHeight, this, x, y)
                matrix[x][y] = tile
            } else {
                i--
            }
            i++
        }
    }

   override fun draw(canvas: Canvas) {
        //t.draw(canvas);
        for (i in 0..3) {
            for (j in 0..3) {
                if (matrix[i][j] != null) {
                    matrix[i][j]!!.draw(canvas)
                }
            }
        }
    }

    override fun update() {
        //t.update();
        for (i in 0..3) {
            for (j in 0..3) {
                if (matrix[i][j] != null) {
                    matrix[i][j]!!.update()
                }
            }
        }
    }

    fun onSwipe(direction: SwipeCallBack.Direction)
    {
        when(direction)
        {
            SwipeCallBack.Direction.UP->{
                return
            }

            SwipeCallBack.Direction.DOWN->{
                return
            }

            SwipeCallBack.Direction.LEFT->{
                return
            }

            SwipeCallBack.Direction.RIGHT->{
                return
            }
        }
    }


    override fun getBitmap(count: Int): Bitmap {
        return tileBitmaps[count]!!
    }

}