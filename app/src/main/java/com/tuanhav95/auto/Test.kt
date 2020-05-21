package com.tuanhav95.auto

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Test {

    companion object {
        /**
         * tìm kiếm point gần nhất
         */
        fun findPointNear(point: Point, points: ArrayList<Point>, touch: Boolean): Point? {
            var min = Double.MAX_VALUE

            var pointNear: Point? = null

            for (p in points) {
                var distance =
                    sqrt((point.x - p.x).toDouble().pow(2) + (point.y - p.y).toDouble().pow(2))

                distance = abs(distance)

                if (distance < min && (distance < 100 || touch)) {
                    min = distance

                    pointNear = p
                }
            }

            return pointNear
        }

//    fun canUpdate(point: Point)

        fun update(points: ArrayList<Point>, pointCenter: Point, pointMove: Point) {
            if (points.size != 4) return

            val topLeft = points[0] as PointTopLeft
            val topRight = points[1] as PointTopRight

            val bottomRight = points[2] as PointBottomRight
            val bottomLeft = points[3] as PointBottomLeft

            when (points.indexOf(pointMove)) {
                0 -> {
                    symmetricalY(pointCenter, topLeft, topRight)
                    symmetricalX(pointCenter, topRight, bottomRight)
                    symmetricalY(pointCenter, bottomRight, bottomLeft)
                }
                1 -> {
                    symmetricalX(pointCenter, topRight, bottomRight)
                    symmetricalY(pointCenter, bottomRight, bottomLeft)
                    symmetricalX(pointCenter, bottomLeft, topLeft)
                }
                2 -> {
                    symmetricalY(pointCenter, bottomRight, bottomLeft)
                    symmetricalX(pointCenter, bottomLeft, topLeft)
                    symmetricalY(pointCenter, topLeft, topRight)
                }
                3 -> {
                    symmetricalX(pointCenter, bottomLeft, topLeft)
                    symmetricalY(pointCenter, topLeft, topRight)
                    symmetricalX(pointCenter, topRight, bottomRight)
                }
            }
        }

        /**
         * đối xứng qua một điểm theo trục Y
         */
        private fun symmetricalY(pointCenter: Point, pointInput: Point, pointOutput: Point) {
            pointOutput.x = pointCenter.x - pointInput.x + pointCenter.x
            pointOutput.y = pointInput.y

        }

        /**
         * đổi xứng qua một điểm theo trục X
         */
        private fun symmetricalX(pointCenter: Point, pointInput: Point, pointOutput: Point) {
            pointOutput.x = pointInput.x
            pointOutput.y = pointCenter.y - pointInput.y + pointCenter.y
        }
    }

    open class Point : android.graphics.Point {
        open var id: String = ""

        constructor()

        constructor(x: Int, y: Int) {
            this.x = x
            this.y = y
        }


    }


    class PointTopRight : Point {
        override var id: String = "PointTopRight"

        constructor()

        constructor(x: Int, y: Int) {
            this.x = x
            this.y = y
        }
    }

    class PointTopLeft : Point {
        override var id: String = "PointTopLeft"

        constructor()

        constructor(x: Int, y: Int) {
            this.x = x
            this.y = y
        }
    }

    class PointBottomRight : Point {
        override var id: String = "PointBottomRight"

        constructor()

        constructor(x: Int, y: Int) {
            this.x = x
            this.y = y
        }
    }

    class PointBottomLeft : Point {
        override var id: String = "PointBottomLeft"

        constructor()

        constructor(x: Int, y: Int) {
            this.x = x
            this.y = y
        }
    }
}