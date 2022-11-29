package com.example.a2048game

import android.graphics.Canvas
import android.view.SurfaceHolder

var targetFPS = 60
var canvas:Canvas? = null
var running:Boolean? = null

class MainThread(var surfaceHolder: SurfaceHolder, val gameManager: GameManager):Thread() {


    fun setRunning(isRunning:Boolean)
    {
        running = isRunning
    }

    @JvmName("setSurfaceHolder1")
    fun setSurfaceHolder(surfaceHolder: SurfaceHolder)
    {
        this.surfaceHolder = surfaceHolder
    }

    override fun run() {
        var startTime:Long
        var timeMillis:Long
        var waitTime:Long
        var totalTime = 0
        var targetTime = 100/targetFPS

        while (running!!)
        {
            startTime = System.nanoTime()
            canvas = null
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder)
                {
                    gameManager.update()
                    gameManager.draw(canvas)
                }
            }catch (ex:Exception){}finally {
                if (canvas != null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }catch (ex:Exception){}
                }
            }
            timeMillis = (System.nanoTime() -startTime)/1000000
            waitTime = targetTime - timeMillis

            try {
                if (waitTime > 0)
                {
                    sleep(waitTime)
                }
            }catch (ex:Exception){}
        }
        super.run()
    }
}