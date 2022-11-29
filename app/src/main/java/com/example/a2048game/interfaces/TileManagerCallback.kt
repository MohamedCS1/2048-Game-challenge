package com.example.a2048game.interfaces

import android.graphics.Bitmap

interface TileManagerCallback {
    fun getBitmap(count:Int):Bitmap
}