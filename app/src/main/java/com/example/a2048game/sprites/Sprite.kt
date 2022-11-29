package com.example.a2048game.sprites

import android.graphics.Canvas

interface Sprite {
    fun draw(canvas: Canvas)
    fun update()
}