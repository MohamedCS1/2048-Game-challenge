package com.example.a2048game

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import com.example.a2048game.interfaces.SwipeCallBack

var detector:GestureDetector? = null

class SwipeListener(val context: Context, var callBack: SwipeCallBack):GestureDetector.OnGestureListener {

    init {
        detector = GestureDetector(context ,this)
    }

    fun onTouchEvent(motionEvent: MotionEvent)
    {
        detector?.onTouchEvent(motionEvent)
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
    }

    override fun onFling(
        motionEvent: MotionEvent?,
        motionEvent1: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (velocityX > 0) {
                callBack.onSwipe(SwipeCallBack.Direction.RIGHT)
            } else {
                callBack.onSwipe(SwipeCallBack.Direction.LEFT)
            }
        } else {
            if (velocityY > 0) {
                callBack.onSwipe(SwipeCallBack.Direction.DOWN)
            } else {
                callBack.onSwipe(SwipeCallBack.Direction.UP)
            }
        }
        return false
    }
}