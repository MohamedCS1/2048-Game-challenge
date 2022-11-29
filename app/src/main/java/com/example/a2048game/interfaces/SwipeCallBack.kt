package com.example.a2048game.interfaces

import android.graphics.Path

interface SwipeCallBack {
    fun onSwipe(direction:Direction)

    enum class Direction
    {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}