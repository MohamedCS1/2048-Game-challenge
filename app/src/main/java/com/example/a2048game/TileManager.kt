package com.example.a2048game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.a2048game.interfaces.Sprite
import com.example.a2048game.interfaces.SwipeCallBack
import com.example.a2048game.interfaces.TileManagerCallback
import com.example.a2048game.sprites.Tile

lateinit var tile:Tile
var tileBitmaps = hashMapOf<Int ,Bitmap>()
var drawables = arrayListOf<Int>()
private val matrix = Array(4) { arrayOfNulls<Tile>(4) }

class TileManager(val resources: Resources ,val standardSize:Int ,val screenWidth:Int ,val screenHeight:Int):TileManagerCallback, Sprite {

    init {
        tile = Tile(standardSize ,screenWidth ,screenHeight ,this ,1 ,1)
        initBitmaps()

        matrix[1][1] = tile
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
            val titleBitmap = Bitmap.createScaledBitmap(bitmap ,standardSize ,standardSize ,false)
            tileBitmaps[i] = titleBitmap
        }
    }

    override fun draw(canvas: Canvas) {
        tile.draw(canvas)
    }

    override fun update() {
        tile.update()
    }

    fun onSwipe(direction: SwipeCallBack.Direction)
    {
        when(direction)
        {
            SwipeCallBack.Direction.UP->{
                tile.move(0 ,1)
                return
            }

            SwipeCallBack.Direction.DOWN->{
                tile.move(3 ,1)
                return
            }

            SwipeCallBack.Direction.LEFT->{
                tile.move(1 ,0)
                return
            }

            SwipeCallBack.Direction.RIGHT->{
                tile.move(1 ,3)
                return
            }
        }
    }


    override fun getBitmap(count: Int): Bitmap {
        return tileBitmaps[count]!!
    }

}