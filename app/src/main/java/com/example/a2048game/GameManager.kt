package com.example.a2048game

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameManager(context:Context ,val attrs:AttributeSet):SurfaceHolder.Callback,
    SurfaceView(context) {

    init {
        holder.addCallback(this)
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
    }

    override fun draw(canvas: Canvas?) {
        Log.d("Test2048" ,"Draw")
        super.draw(canvas)
    }
}