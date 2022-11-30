package com.example.a2048game.interfaces

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.a2048game.R

class EndGame(val resources: Resources ,val screenWidth:Int? ,val screenHeight:Int):Sprite {

    lateinit var bitmap: Bitmap
    val endGameWidth = resources.getDimension(R.dimen.endgame_width)
    val endGameHeight = resources.getDimension(R.dimen.endgame_height)

    init {
        val bmp = BitmapFactory.decodeResource(resources ,R.drawable.gameover)
        bitmap = Bitmap.createScaledBitmap(bmp ,endGameWidth.toInt() ,endGameHeight.toInt() ,false)
    }

    override fun draw(canvas: Canvas) {

        canvas.drawBitmap(bitmap, (screenWidth!! / 2 - bitmap.width / 2).toFloat(), (screenHeight / 2 - bitmap.height / 2).toFloat(), null)
    }

    override fun update() {
        TODO("Not yet implemented")
    }
}