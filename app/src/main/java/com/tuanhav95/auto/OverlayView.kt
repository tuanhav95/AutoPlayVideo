package com.tuanhav95.auto

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class OverlayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var paint: Paint = Paint()

    val points = ArrayList<Point>()

    init {
        paint.color = Color.BLACK;
        paint.strokeWidth = 50f;
        paint.style = Paint.Style.FILL;
        paint.isAntiAlias = true
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        points.clear()
        points.add(Test.PointTopRight(100, 100))
        points.add(Test.PointTopLeft(layoutParams.width - 100, 100))
        points.add(Test.PointBottomLeft(layoutParams.width - 100, layoutParams.height - 100))
        points.add(Test.PointBottomRight(100, layoutParams.height - 100))

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event.action==MotionEvent.ACTION_DOWN){

        }else if()
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawLine(canvas)
    }

    private fun drawLine(canvas: Canvas?) {

        canvas?.drawLine(
            points[0].x.toFloat(),
            points[0].y.toFloat(),
            points[1].x.toFloat(),
            points[1].y.toFloat(),
            paint
        )


        canvas?.drawLine(
            points[1].x.toFloat(),
            points[1].y.toFloat(),
            points[2].x.toFloat(),
            points[2].y.toFloat(),
            paint
        )


        canvas?.drawLine(
            points[2].x.toFloat(),
            points[2].y.toFloat(),
            points[3].x.toFloat(),
            points[3].y.toFloat(),
            paint
        )


        canvas?.drawLine(
            points[3].x.toFloat(),
            points[3].y.toFloat(),
            points[0].x.toFloat(),
            points[0].y.toFloat(),
            paint
        )
    }
}