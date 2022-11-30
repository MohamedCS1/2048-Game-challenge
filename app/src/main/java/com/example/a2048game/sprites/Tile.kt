package com.example.a2048game.sprites

import android.graphics.Canvas
import com.example.a2048game.interfaces.Sprite
import com.example.a2048game.interfaces.TileManagerCallback
import kotlin.math.pow


class Tile(private val standardSize: Int, private val screenWidth: Int, private val screenHeight: Int, private val callback: TileManagerCallback, matrixX: Int, matrixY: Int) :
    Sprite {
    private var count = 1
    private var currentX: Int
    private var currentY: Int
    private var destX: Int
    private var destY: Int
    private var moving = false
    private val speed = 90
    private var increment = false

    init {
        destX = screenWidth / 2 - 2 * standardSize + matrixY * standardSize
        currentX = destX
        destY = screenHeight / 2 - 2 * standardSize + matrixX * standardSize
        currentY = destY
        val chance = java.util.Random().nextInt(100)
        if (chance >= 90)
        {
            count = 2
        }
    }
    fun move(matrixX: Int, matrixY: Int) {
        moving = true
        destX = screenWidth / 2 - 2 * standardSize + matrixY * standardSize
        destY = screenHeight / 2 - 2 * standardSize + matrixX * standardSize
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(callback.getBitmap(count), currentX.toFloat(), currentY.toFloat(), null)
        if (moving && currentX == destX && currentY == destY) {
            moving = false
            if (increment)
            {
                count++
                increment = false
                callback.updateScore(2.0.pow(count.toDouble()).toInt())
            }
            callback.finishedMoving(this)
        }
    }

    fun getValue():Int{
        return count
    }

    fun increment():Tile
    {
        increment = true
        return this
    }

    fun toIncrement(): Boolean {
        return increment
    }

    override fun update() {
        if (currentX < destX) {
            if (currentX + speed > destX) {
                currentX = destX
            } else {
                currentX += speed
            }
        } else if (currentX > destX) {
            if (currentX - speed < destX) {
                currentX = destX
            } else {
                currentX -= speed
            }
        }
        if (currentY < destY) {
            if (currentY + speed > destY) {
                currentY = destY
            } else {
                currentY += speed
            }
        } else if (currentY > destY) {
            if (currentY > destY) {
                if (currentY - speed < destY) {
                    currentY = destY
                } else {
                    currentY -= speed
                }
            }
        }
    }

}