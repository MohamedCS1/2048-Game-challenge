package com.example.a2048game


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.a2048game.interfaces.GameManagerCallBack
import com.example.a2048game.interfaces.SwipeCallBack
import com.example.a2048game.sprites.Grid


private val SHAREE_NAME = "2048"

lateinit var grid: Grid
var screenWidth:Int? = null
var screenHeight:Int? = null
var standardSize:Double? = null
var tileManager:TileManager? = null
var endGame = false
var gameOverDialog: EndGame? = null
var score:Score? = null
var restartButton:Bitmap? = null
var restartButtonX:Int? = null
var restartButtonY:Int? = null
var restartButtonSize:Int? = null
class GameManager(context:Context ,val attrs:AttributeSet):SurfaceHolder.Callback,
    SurfaceView(context),SwipeCallBack ,GameManagerCallBack{

    var swipe:SwipeListener? = null
    init {
        holder.addCallback(this)
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels
        standardSize = ((screenWidth!! * .88)  / 4)
        grid = Grid(resources , screenWidth!!, screenHeight!! , standardSize!!.toInt())
        tileManager = TileManager(resources , standardSize!!.toInt(), screenWidth!!, screenHeight!!,this)
        swipe = SwipeListener(context,this)
        isLongClickable = true
        gameOverDialog = EndGame(resources , screenWidth , screenHeight!!)
        score = Score(resources , screenWidth!!, screenHeight!!, standardSize ,getContext().getSharedPreferences(
            SHAREE_NAME ,Context.MODE_PRIVATE))


        restartButtonSize = resources.getDimension(R.dimen.restart_button_size).toInt()
        val bmpRestart = BitmapFactory.decodeResource(resources, R.drawable.restart)
        restartButton = Bitmap.createScaledBitmap(
            bmpRestart,
            restartButtonSize!!, restartButtonSize!!, false
        )
        restartButtonX = (screenWidth!! / 2 + 2 * standardSize!! - restartButtonSize!!).toInt()
        restartButtonY = (screenHeight!! / 2 - 2 * standardSize!! - 3 * restartButtonSize!! / 2).toInt()
    }

    fun initGame()
    {
        endGame = false
        tileManager?.initGame()
        score = Score(resources , screenWidth!!, screenHeight!!, standardSize , context.getSharedPreferences(
            SHAREE_NAME ,Context.MODE_PRIVATE))
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
        if (!endGame)
        {
            tileManager?.update()
        }
    }

    override fun draw(canvas: Canvas?) {
        Log.d("Test2048" ,"Draw")
        super.draw(canvas)
        canvas?.drawRGB(255 ,255 ,255)
        grid.draw(canvas!!)
        tileManager?.draw(canvas)
        score?.draw(canvas)
        canvas.drawBitmap(restartButton!!, restartButtonX!!.toFloat() , restartButtonY!!.toFloat() ,null)
        if (endGame)
        {
            gameOverDialog?.draw(canvas)
        }
    }



    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (endGame) {
            if (event!!.action == MotionEvent.ACTION_DOWN) {
                initGame()
            }
        } else {
            val eventX = event!!.getAxisValue(MotionEvent.AXIS_X)
            val eventY = event.getAxisValue(MotionEvent.AXIS_Y)
            if (event.action == MotionEvent.ACTION_DOWN && eventX > restartButtonX!! && eventX < restartButtonX!! + restartButtonSize!! && eventY > restartButtonY!! && eventY < restartButtonY!! + restartButtonSize!!) {
                initGame()
            } else {
                swipe!!.onTouchEvent(event)
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onSwipe(direction: SwipeCallBack.Direction) {
        tileManager?.onSwipe(direction)
    }

    override fun gameOver() {
        endGame = true
    }

    override fun updateScore(sc: Int) {
        score?.updateScore(sc)
    }
}