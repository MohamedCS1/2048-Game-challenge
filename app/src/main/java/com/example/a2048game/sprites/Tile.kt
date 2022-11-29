package com.example.a2048game.sprites

import android.graphics.Canvas
import com.example.a2048game.interfaces.Sprite
import com.example.a2048game.interfaces.TileManagerCallback


var count = 1
var currentX:Int? = null
var currentY:Int? = null
var desX:Int? = null
var desY:Int? = null
var moving = false
var speed = 10
class Tile(val standardSize:Int ,val screenWidth:Int ,val screenHeight:Int ,val callback: TileManagerCallback ,matrixX:Int ,matrixY:Int): Sprite {
    init {
        desX = screenWidth / 2 - 2 * standardSize + matrixY * standardSize
        currentX = desX
        desY = screenHeight / 2 - 2 * standardSize + matrixX * standardSize
        currentY = desY
    }


    fun move(matrixX: Int ,matrixY: Int)
    {
        moving = true
        desX = screenWidth / 2 - 2 * standardSize + matrixY * standardSize
        desY = screenHeight / 2 - 2 * standardSize + matrixX * standardSize
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(callback.getBitmap(count) ,
            currentX!!.toFloat() , currentY!!.toFloat() ,null)

        if (moving && currentX == desX && currentY == desY)
        {
            moving = false
        }
    }

    override fun update() {
        if (currentX!! < desX!!) {
            if (currentX!! + speed > desX!!) {
                currentX = desX
            } else {
                currentX = currentX!! + speed
            }
        } else if (currentX!! > desX!!) {
            if (currentX!! - speed < desX!!) {
                currentX = desX
            } else {
                currentX = currentX!! - speed
            }
        }
        if (currentY!! < desY!!) {
            if (currentY!! + speed > desY!!) {
                currentY = desY
            } else {
                currentY = currentY!! + speed
            }
        } else if (currentY!! > desY!!) {
            if (currentY!! > desY!!) {
                if (currentY!! - speed < desY!!) {
                    currentY = desY
                } else {
                    currentY = currentY!! - speed
                }
            }
        }
    }
}