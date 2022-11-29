package com.example.a2048game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.a2048game.interfaces.Sprite
import com.example.a2048game.interfaces.TileManagerCallback
import com.example.a2048game.sprites.Tile

lateinit var tile:Tile
var tileBitmaps = hashMapOf<Int ,Bitmap>()
var drawables = arrayListOf<Int>()
class TileManager(val resources: Resources ,val standardSize:Int ,val screenWidth:Int ,val screenHeight:Int):TileManagerCallback, Sprite {

    init {
        tile = Tile(standardSize ,screenWidth ,screenHeight ,this)
        initBitmaps()
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
    }

    override fun getBitmap(count: Int): Bitmap {
        return tileBitmaps[count]!!
    }

}