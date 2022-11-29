package com.example.a2048game.interfaces

import android.graphics.Canvas

interface Sprite {
    fun draw(canvas: Canvas)
    fun update()
}