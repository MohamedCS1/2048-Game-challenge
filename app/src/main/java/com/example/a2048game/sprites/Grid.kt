package com.example.a2048game.sprites

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.a2048game.R
import com.example.a2048game.interfaces.Sprite

lateinit var grid:Bitmap
class Grid(val resources: Resources ,val screenWith:Int ,val screenHeight:Int ,val standardSize:Int):
    Sprite {


    init {
        val bmp = BitmapFactory.decodeResource(resources , R.drawable.grid)
        grid = Bitmap.createScaledBitmap(bmp ,standardSize * 4 ,standardSize * 4 ,false)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(grid ,(screenWith / 2 - grid.width / 2).toFloat() ,(screenHeight / 2 - grid.height / 2).toFloat() ,null)
    }

    override fun update() {

    }
}