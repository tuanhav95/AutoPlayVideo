package com.tuanhav95.auto

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import com.tuanhav95.auto.Test.Point
import kotlin.math.max
import kotlin.math.min

class OverlayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var paint: Paint = Paint()
    var mBackgroundPaint: Paint = Paint()
    var pain2: Paint = Paint()

    val points = ArrayList<Point>()

    val pointRound = Point()
    val pointCenter = Point()

    var pointSelected: Point? = null


    private var _xDown = 0
    private var _yDown = 0

    init {
        paint.color = Color.WHITE
        paint.strokeWidth = 2f
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true


        mBackgroundPaint.setColor(Color.parseColor("#80000000"));

        pain2.color = Color.WHITE
        pain2.strokeWidth = 20f
        pain2.style = Paint.Style.FILL
        pain2.isAntiAlias = true

        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)

                points.clear()
                points.add(Test.PointTopLeft(100, 100))
                points.add(Test.PointTopRight(width - 100, 100))
                points.add(Test.PointBottomRight(width - 100, height - 100))
                points.add(Test.PointBottomLeft(100, height - 100))

                pointRound.set(width, height)
                pointCenter.set(width / 2, height / 2)

                invalidate()
            }
        })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            updatePointSelected(event)
        } else if (pointSelected != null && event?.action == MotionEvent.ACTION_MOVE) {
            updatePointSelected(event)

            var x = (-_xDown + event.rawX).toInt()
            var y = (-_yDown + event.rawY).toInt()


            when {
                points.indexOf(pointSelected!!) == 0 -> {
                    x = min(x, pointCenter.x - 100)
                    x = max(x, 100)

                    y = min(y, pointCenter.y - 100)
                    y = max(y, 100)
                }
                points.indexOf(pointSelected!!) == 1 -> {
                    x = min(x, pointRound.x - 100)
                    x = max(x, pointCenter.x + 100)

                    y = min(y, pointCenter.y - 100)
                    y = max(y, 100)
                }
                points.indexOf(pointSelected!!) == 2 -> {

                    x = min(x, pointRound.x - 100)
                    x = max(x, pointCenter.x + 100)

                    y = min(y, pointRound.y - 100)
                    y = max(y, pointCenter.y + 100)
                }
                points.indexOf(pointSelected!!) == 3 -> {
                    x = min(x, pointCenter.x - 100)
                    x = max(x, 100)

                    y = min(y, pointRound.y - 100)
                    y = max(y, pointCenter.y + 100)
                }
            }

            pointSelected!!.set(x, y)

            Test.update(points, pointCenter, pointSelected!!)

            println("onTouchEvent:  " + points.indexOf(pointSelected!!) + "    " + points.toString() + "   " + pointSelected.toString() + "      " + (pointSelected == points[3]) + "     " + x + "   " + y + "       ")

            invalidate()
        } else if (event?.action == MotionEvent.ACTION_UP) {
            pointSelected = null
        }
        return true
    }

    private fun updatePointSelected(event: MotionEvent) {
        val point = Test.findPointNear(
            Point(event.x.toInt(), event.y.toInt()),
            points,
            pointSelected != null
        )
        if (pointSelected != point && point != null) {
            pointSelected = point
            _xDown = event.rawX.toInt() - pointSelected!!.x
            _yDown = event.rawY.toInt() - pointSelected!!.y
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (points.size != 4) return

        drawBackground(canvas)

        drawLine(canvas)

        drawCorner(canvas)

    }

    private fun drawBackground(canvas: Canvas?) {

        /*-
          -------------------------------------
          |                top                |
          -------------------------------------
          |      |                    |       |
          |      |                    |       |
          | left |                    | right |
          |      |                    |       |
          |      |                    |       |
          -------------------------------------
          |              bottom               |
          -------------------------------------
         */

        // Draw "top", "bottom", "left", then "right" quadrants.
        canvas?.drawRect(0f, 0f, width.toFloat(), points[0].y.toFloat(), mBackgroundPaint)
        canvas?.drawRect(
            0f,
            points[3].y.toFloat(),
            width.toFloat(),
            height.toFloat(),
            mBackgroundPaint
        );
        canvas?.drawRect(
            0f,
            points[0].y.toFloat(),
            points[0].x.toFloat(),
            points[3].y.toFloat(),
            mBackgroundPaint
        );
        canvas?.drawRect(
            points[1].x.toFloat(),
            points[1].y.toFloat(),
            width.toFloat(),
            points[2].y.toFloat(),
            mBackgroundPaint
        );
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


    private fun drawCorner(canvas: Canvas?) {
        canvas?.drawLine(
            points[0].x.toFloat(),
            points[0].y.toFloat(),
            points[0].x.toFloat() + 100.toFloat(),
            points[0].y.toFloat(),
            pain2
        )
        canvas?.drawLine(
            points[0].x.toFloat(),
            points[0].y.toFloat(),
            points[0].x.toFloat(),
            points[0].y.toFloat() + 100.toFloat(),
            pain2
        )

        canvas?.drawLine(
            points[1].x.toFloat(),
            points[1].y.toFloat(),
            points[1].x.toFloat() - 100.toFloat(),
            points[1].y.toFloat(),
            pain2
        )
        canvas?.drawLine(
            points[1].x.toFloat(),
            points[1].y.toFloat(),
            points[1].x.toFloat(),
            points[1].y.toFloat() + 100.toFloat(),
            pain2
        )


        canvas?.drawLine(
            points[2].x.toFloat(),
            points[2].y.toFloat(),
            points[2].x.toFloat() ,
            points[2].y.toFloat()- 100.toFloat(),
            pain2
        )
        canvas?.drawLine(
            points[2].x.toFloat(),
            points[2].y.toFloat(),
            points[2].x.toFloat()- 100.toFloat(),
            points[2].y.toFloat() ,
            pain2
        )


        canvas?.drawLine(
            points[3].x.toFloat(),
            points[3].y.toFloat(),
            points[3].x.toFloat() ,
            points[3].y.toFloat()- 100.toFloat(),
            pain2
        )
        canvas?.drawLine(
            points[3].x.toFloat(),
            points[3].y.toFloat(),
            points[3].x.toFloat()+ 100.toFloat(),
            points[3].y.toFloat() ,
            pain2
        )
    }

}