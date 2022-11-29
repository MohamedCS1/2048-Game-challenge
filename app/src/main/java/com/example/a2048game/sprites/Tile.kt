package com.example.a2048game.sprites

import android.graphics.Canvas
import com.example.a2048game.interfaces.Sprite
import com.example.a2048game.interfaces.TileManagerCallback

var count = 1
class Tile(val standardSize:Int ,val screenWidth:Int ,val screenHeight:Int ,val callback: TileManagerCallback): Sprite {

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(callback.getBitmap(count) ,(screenWidth / 2 - standardSize).toFloat() ,(screenHeight / 2 - standardSize).toFloat() ,null)
    }

    override fun update() {
    }
}