package com.tuanhav95.auto

import android.graphics.Point
import kotlin.math.pow
import kotlin.math.sqrt

class Test {

    /**
     * tìm kiếm point gần nhất
     */
    fun findPointNear(point: Point, points: ArrayList<Point>): Point? {
        val max = Double.MIN_VALUE

        var pointNear: Point? = null

        for (p in points) {
            val distance =
                sqrt((point.x - p.x).toDouble().pow(2) + (point.y - p.y).toDouble().pow(2))
            if (distance > max && distance < 100) {
                pointNear = point
            }
        }

        return pointNear
    }

//    fun canUpdate(point: Point)

    fun update(points: ArrayList<Point>, pointCenter: Point, pointMove: Point) {
        if (points.size != 4) return

        val pointTopRight = points[0] as PointTopRight
        val pointTopLeft = points[1] as PointTopLeft

        val pointBottomRight = points[3] as PointBottomRight
        val pointBottomLeft = points[2] as PointBottomLeft

        when (points.indexOf(pointMove)) {
            0 -> {
                symmetricalTopRightToTopLeft(pointCenter, pointTopRight, pointTopLeft)
                symmetricalTopLeftToBottomLeft(pointCenter, pointTopLeft, pointBottomLeft)
                symmetricalBottomLeftToBottomRight(pointCenter, pointBottomLeft, pointBottomRight)
            }
            1 -> {
                symmetricalTopLeftToBottomLeft(pointCenter, pointTopLeft, pointBottomLeft)
                symmetricalBottomLeftToBottomRight(pointCenter, pointBottomLeft, pointBottomRight)
                symmetricalBottomRightToTopRight(pointCenter, pointBottomRight, pointTopRight)
            }
            2 -> {
                symmetricalBottomLeftToBottomRight(pointCenter, pointBottomLeft, pointBottomRight)
                symmetricalBottomRightToTopRight(pointCenter, pointBottomRight, pointTopRight)
                symmetricalTopRightToTopLeft(pointCenter, pointTopRight, pointTopLeft)
            }
            3 -> {
                symmetricalBottomRightToTopRight(pointCenter, pointBottomRight, pointTopRight)
                symmetricalTopLeftToTopRight(pointCenter, pointTopLeft, pointTopRight)
                symmetricalTopRightToBottomRight(pointCenter, pointTopRight, pointBottomRight)
            }
        }
    }

    private fun symmetricalBottomRightToTopRight(
        pointCenter: Point,
        pointBottomRight: PointBottomRight,
        pointTopRight: PointTopRight
    ) {
        symmetricalX(pointCenter, pointBottomRight, pointTopRight)
    }

    private fun symmetricalBottomLeftToBottomRight(
        pointCenter: Point,
        pointBottomLeft: PointBottomLeft,
        pointBottomRight: PointBottomRight
    ) {
        symmetricalY(pointCenter, pointBottomLeft, pointBottomRight)
    }


    private fun symmetricalTopLeftToTopRight(
        pointCenter: Point,
        pointTopLeft: PointTopLeft,
        pointTopRight: PointTopRight
    ) {
        symmetricalY(pointCenter, pointTopLeft, pointTopRight)
    }

    private fun symmetricalTopLeftToBottomLeft(
        pointCenter: Point,
        pointTopLeft: PointTopLeft,
        pointBottomLeft: PointBottomLeft
    ) {
        symmetricalX(pointCenter, pointTopLeft, pointBottomLeft)
    }

    private fun symmetricalTopRightToTopLeft(
        pointCenter: Point,
        pointTopRight: PointTopRight,
        pointTopLeft: PointTopLeft
    ) {
        symmetricalY(pointCenter, pointTopRight, pointTopLeft)
    }

    private fun symmetricalTopRightToBottomRight(
        pointCenter: Point,
        pointTopRight: PointTopRight,
        pointBottomRight: PointBottomRight
    ) {
        symmetricalX(pointCenter, pointTopRight, pointBottomRight)
    }

    /**
     * đối xứng qua một điểm theo trục Y
     */
    private fun symmetricalY(pointCenter: Point, pointInput: Point, pointOutput: Point) {
        pointOutput.x = pointCenter.x - pointInput.x
        pointOutput.y = pointInput.y

    }

    /**
     * đổi xứng qua một điểm theo trục X
     */
    private fun symmetricalX(pointCenter: Point, pointInput: Point, pointOutput: Point) {
        pointOutput.x = pointInput.x
        pointOutput.y = pointCenter.y - pointInput.y
    }


    class PointTopRight : Point {
        constructor()

        constructor(x: Int, y: Int) {
            this.x = x
            this.y = y
        }
    }

    class PointTopLeft : Point {
        constructor()

        constructor(x: Int, y: Int) {
            this.x = x
            this.y = y
        }
    }

    class PointBottomRight : Point {
        constructor()

        constructor(x: Int, y: Int) {
            this.x = x
            this.y = y
        }
    }

    class PointBottomLeft : Point {
        constructor()

        constructor(x: Int, y: Int) {
            this.x = x
            this.y = y
        }
    }
}