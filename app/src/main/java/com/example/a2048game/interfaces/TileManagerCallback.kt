package com.example.a2048game.interfaces

import android.graphics.Bitmap
import com.example.a2048game.sprites.Tile

interface TileManagerCallback {
    fun getBitmap(count:Int):Bitmap
    fun finishedMoving(tile: Tile)
    fun updateScore(score:Int)
}