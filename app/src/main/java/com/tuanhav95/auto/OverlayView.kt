package com.tuanhav95.auto

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Path.FillType
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

    private val mLinePaint: Paint = Paint()
    private val mCornerPaint: Paint = Paint()
    private val mTrianglePaint: Paint = Paint()
    private val mBackgroundPaint: Paint = Paint()

    private val pointCenter = Point()
    private val pointCenterMin = Point()
    private val pointCenterMax = Point()

    private val pointRoundMin = Point()
    private val pointRoundMax = Point()

    private val points = ArrayList<Point>()

    private var pointSelected: Point? = null

    private var downX = 0f
    private var downY = 0f

    private var cornerSize = 100

    init {
        mLinePaint.strokeWidth = 5f
        mLinePaint.isAntiAlias = true
        mLinePaint.color = Color.WHITE
        mLinePaint.style = Paint.Style.FILL

        mCornerPaint.strokeWidth = 20f
        mCornerPaint.isAntiAlias = true
        mCornerPaint.color = Color.WHITE
        mCornerPaint.style = Paint.Style.FILL

        mTrianglePaint.strokeWidth = 20f
        mTrianglePaint.isAntiAlias = true
        mTrianglePaint.color = Color.parseColor("#386AF6")
        mTrianglePaint.style = Paint.Style.FILL

        mBackgroundPaint.color = Color.parseColor("#80000000");

        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)

                points.clear()
                points.add(
                    Test.PointTopLeft(
                        paddingLeft,
                        paddingTop
                    )
                )
                points.add(
                    Test.PointTopRight(
                        width - paddingRight,
                        paddingTop
                    )
                )
                points.add(
                    Test.PointBottomRight(
                        width - paddingRight,
                        height - paddingBottom
                    )
                )
                points.add(
                    Test.PointBottomLeft(
                        paddingLeft,
                        height - paddingBottom
                    )
                )

                pointRoundMin.set(paddingLeft, paddingTop)
                pointRoundMax.set(width - paddingRight, height - paddingBottom)

                pointCenter.set(
                    (width + paddingLeft - paddingRight) / 2,
                    (height + paddingTop - paddingBottom) / 2
                )

                pointCenterMin.set(pointCenter.x - 100, pointCenter.y - 100)
                pointCenterMax.set(pointCenter.x + 100, pointCenter.y + 100)

                invalidate()
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            updatePointSelected(event)

            return true
        } else if (pointSelected != null && event?.action == MotionEvent.ACTION_MOVE) {
            updatePointSelected(event)

            var x = event.rawX - downX
            var y = event.rawY - downY

            when {
                points.indexOf(pointSelected!!) == 0 -> {
                    x = min(x, pointCenterMin.x)
                    x = max(x, pointRoundMin.x)

                    y = min(y, pointCenterMin.y)
                    y = max(y, pointRoundMin.y)
                }
                points.indexOf(pointSelected!!) == 1 -> {
                    x = min(x, pointRoundMax.x)
                    x = max(x, pointCenterMax.x)

                    y = min(y, pointCenterMin.y)
                    y = max(y, pointRoundMin.y)
                }
                points.indexOf(pointSelected!!) == 2 -> {
                    x = min(x, pointRoundMax.x)
                    x = max(x, pointCenterMax.x)

                    y = min(y, pointRoundMax.y)
                    y = max(y, pointCenterMax.y)
                }
                points.indexOf(pointSelected!!) == 3 -> {
                    x = min(x, pointCenterMin.x)
                    x = max(x, pointRoundMin.x)

                    y = min(y, pointRoundMax.y)
                    y = max(y, pointCenterMax.y)
                }
            }

            pointSelected!!.set(x, y)

            Test.update(points, pointCenter, pointSelected!!)

            invalidate()

            return true
        } else if (event?.action == MotionEvent.ACTION_UP) {
            pointSelected = null
        }

        return false
    }

    private fun updatePointSelected(event: MotionEvent) {
        val point = Test.findPointNear(
            Point(event.x.toInt(), event.y.toInt()),
            points,
            pointSelected != null
        )
        if (pointSelected != point && point != null) {
            pointSelected = point
            downX = event.rawX - pointSelected!!.x
            downY = event.rawY - pointSelected!!.y
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (points.size != 4) return

        drawBackground(canvas)

        drawLine(canvas)

        drawCorner(canvas)

        drawTriangle(canvas)
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
        canvas?.drawRect(0f, 0f, width.toFloat(), points[0].y, mBackgroundPaint)
        canvas?.drawRect(
            0f,
            points[3].y,
            width.toFloat(),
            height.toFloat(),
            mBackgroundPaint
        )
        canvas?.drawRect(
            0f,
            points[0].y,
            points[0].x,
            points[3].y,
            mBackgroundPaint
        )
        canvas?.drawRect(
            points[1].x,
            points[1].y,
            width.toFloat(),
            points[2].y,
            mBackgroundPaint
        )
    }

    private fun drawLine(canvas: Canvas?) {
        canvas?.drawLine(
            points[0].x,
            points[0].y,
            points[1].x,
            points[1].y,
            mLinePaint
        )


        canvas?.drawLine(
            points[1].x,
            points[1].y,
            points[2].x,
            points[2].y,
            mLinePaint
        )


        canvas?.drawLine(
            points[2].x,
            points[2].y,
            points[3].x,
            points[3].y,
            mLinePaint
        )


        canvas?.drawLine(
            points[3].x,
            points[3].y,
            points[0].x,
            points[0].y,
            mLinePaint
        )
    }

    private fun drawCorner(canvas: Canvas?) {
        canvas?.drawLine(
            points[0].x - mCornerPaint.strokeWidth / 2,
            points[0].y,
            points[0].x + cornerSize,
            points[0].y,
            mCornerPaint
        )
        canvas?.drawLine(
            points[0].x,
            points[0].y - mCornerPaint.strokeWidth / 2,
            points[0].x,
            points[0].y + cornerSize,
            mCornerPaint
        )

        canvas?.drawLine(
            points[1].x + mCornerPaint.strokeWidth / 2,
            points[1].y,
            points[1].x - cornerSize,
            points[1].y,
            mCornerPaint
        )
        canvas?.drawLine(
            points[1].x,
            points[1].y - mCornerPaint.strokeWidth / 2,
            points[1].x,
            points[1].y + cornerSize,
            mCornerPaint
        )


        canvas?.drawLine(
            points[2].x,
            points[2].y + mCornerPaint.strokeWidth / 2,
            points[2].x,
            points[2].y - cornerSize,
            mCornerPaint
        )
        canvas?.drawLine(
            points[2].x + mCornerPaint.strokeWidth / 2,
            points[2].y,
            points[2].x - cornerSize,
            points[2].y,
            mCornerPaint
        )

        canvas?.drawLine(
            points[3].x,
            points[3].y + mCornerPaint.strokeWidth / 2,
            points[3].x,
            points[3].y - cornerSize,
            mCornerPaint
        )
        canvas?.drawLine(
            points[3].x - mCornerPaint.strokeWidth / 2,
            points[3].y,
            points[3].x + cornerSize,
            points[3].y,
            mCornerPaint
        )
    }

    private fun drawTriangle(canvas: Canvas?) {

        val point = points[2]

        val path = Path()
        path.fillType = FillType.EVEN_ODD
        path.lineTo((point.x - 30), (point.y - 70))
        path.lineTo((point.x - 30), (point.y - 30))
        path.lineTo((point.x - 70), (point.y - 30))
        path.lineTo((point.x - 30), (point.y - 70))
        path.close()

        canvas!!.drawPath(path, mTrianglePaint)
    }

}