package com.example.a2048game

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.a2048game.sprites.Grid

lateinit var grid: Grid
var screenWidth:Int? = null
var screenHeight:Int? = null
var standardSize:Double? = null
var tileManager:TileManager? = null
class GameManager(context:Context ,val attrs:AttributeSet):SurfaceHolder.Callback,
    SurfaceView(context) {

    init {
        holder.addCallback(this)
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels
        standardSize = ((screenWidth!! * .88) / 4)
        grid = Grid(resources , screenWidth!!, screenHeight!! , standardSize!!.toInt())
        tileManager = TileManager(resources , standardSize!!.toInt(), screenWidth!!, screenHeight!!)
    }

    lateinit var mainThread: MainThread
    override fun surfaceCreated(p0: SurfaceHolder) {
        mainThread = MainThread(p0,this)
        mainThread.setRunning(true)
        mainThread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        mainThread.setSurfaceHolder(p0)
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        var retry = true
        while (retry)
        {
            try {
                mainThread.setRunning(false)
                mainThread.join()
                retry = false
            }catch (ex:Exception){}
        }
    }
    fun update(){
        Log.d("Test2048" ,"Update")
        tileManager?.update()
    }

    override fun draw(canvas: Canvas?) {
        Log.d("Test2048" ,"Draw")
        super.draw(canvas)
        canvas?.drawRGB(255 ,255 ,255)
        grid.draw(canvas!!)
        tileManager?.draw(canvas)
    }
}