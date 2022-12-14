package com.example.a2048game

import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.*
import com.example.a2048game.interfaces.Sprite


class Score(
    val resources: Resources,
    val screenWidth: Int,
    val screenHeight: Int,
    val standardSize: Double?,
    val prefs: SharedPreferences
) :
    Sprite {
    private val bmpScore: Bitmap
    private val bmpTopScore: Bitmap
    private var bmp2048Bonus: Bitmap? = null
    private var score = 0
    private var topScore: Int
    private val paint: Paint
    private var topScoreBonus = false
    private var a2048Bonus = false
    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(
            bmpScore,
            (screenWidth / 4 - bmpScore.width / 2).toFloat(),
            bmpScore.height.toFloat(),
            null
        )
        canvas.drawBitmap(
            bmpTopScore,
            (3 * screenWidth / 4 - bmpTopScore.width / 2).toFloat(),
            bmpTopScore.height.toFloat(),
            null
        )
        val width1 = paint.measureText(score.toString()).toInt()
        val width2 = paint.measureText(topScore.toString()).toInt()
        canvas.drawText(
            score.toString(),
            (screenWidth / 4 - width1 / 2).toFloat(),
            (bmpScore.height * 4).toFloat(),
            paint
        )
        canvas.drawText(
            topScore.toString(),
            (3 * screenWidth / 4 - width2 / 2).toFloat(),
            (bmpTopScore.height * 4).toFloat(),
            paint
        )
    }

    override fun update() {}
    fun updateScore(delta: Int) {
        score += delta
        checkTopScore()
    }

    fun checkTopScore() {
        topScore = prefs.getInt(SCORE_PREF, 0)
        if (topScore < score) {
            prefs.edit().putInt(SCORE_PREF, score).apply()
            topScore = score
        }
    }



    companion object {
        private const val SCORE_PREF = "Score pref"
    }

    init {
        topScore = prefs.getInt(SCORE_PREF, 0)
        val width = resources.getDimension(R.dimen.score_lable_width).toInt()
        val height = resources.getDimension(R.dimen.score_lable_height).toInt()
        val sc = BitmapFactory.decodeResource(resources, R.drawable.score)
        bmpScore = Bitmap.createScaledBitmap(sc, width, height, false)
        val tsc = BitmapFactory.decodeResource(resources, R.drawable.topscore)
        bmpTopScore = Bitmap.createScaledBitmap(tsc, width, height, false)
        paint = Paint()
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        paint.textSize = resources.getDimension(R.dimen.score_text_size)
    }
}