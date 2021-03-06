package com.tuanhav95.auto

import android.view.View
import androidx.recyclerview.widget.RecyclerView


class Auto(
    val mRecyclerView: RecyclerView,
    val mAutoListener: AutoListener?,
    val horizontalScroll: Boolean = false,
    val reverse: Boolean = false
) {

    private val mHolders = ArrayList<PlayHolder>()

    private var mDelayRunnable: Runnable

    private var mOnScrollListener: RecyclerView.OnScrollListener

    var mPausing = true

    var mCurrentHolder: PlayHolder? = null

    init {
        mRecyclerView.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View) {
                val holder = mRecyclerView.getChildViewHolder(view)

                if (holder is PlayHolder && getPosition(mCurrentHolder) == getPosition(holder)) {// view đã ẩn khỏi màn hình
                    println("onChildViewDetachedFromWindow")
                    pause()
                }

                if (holder is PlayHolder) mHolders.remove(holder)

                delayHandler()
            }

            override fun onChildViewAttachedToWindow(view: View) {
                val holder = mRecyclerView.getChildViewHolder(view)

                if (holder is PlayHolder) mHolders.add(holder)

                delayHandler()
            }

        })

        mOnScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                delayHandler()
            }
        }

        mDelayRunnable = object : Runnable {
            override fun run() {
                if (mPausing) return

                var max = 0
                var holder: PlayHolder? = null

                val list = ArrayList(mHolders)

                if (reverse) {
                    list.reverse()
                }

                for (viewHolder in list) {
                    val percent = getPercent(viewHolder.getView() ?: continue)

                    println("AUTO: " + percent)
                    if (percent > max && percent > 50) {
                        max = percent
                        holder = viewHolder
                    }
                }


                if (holder != null) {
//                    if (getPosition(mCurrentHolder) == getPosition(holder)) {// view cần play lại là view đang play hiện tại
//                        return
//                    }
//
//                    pause()

                    val oldHolderPlay = mCurrentHolder

                    mCurrentHolder = holder

                    println("AUTO: play")
                    play(oldHolderPlay)

                } else {// không tìm thấy view nào ở trên màn hình
                    println("AUTO: pause")
                    pause()
                }
            }

        }
    }

    fun start() {
        mPausing = false

        mRecyclerView.addOnScrollListener(mOnScrollListener)

        mRecyclerView.removeCallbacks(mDelayRunnable)
        mRecyclerView.postDelayed(mDelayRunnable, 100)
    }

    fun pause(pause: Boolean = false) {
        mPausing = true

        mRecyclerView.removeOnScrollListener(mOnScrollListener)

        mRecyclerView.removeCallbacks(mDelayRunnable)

        if (pause) pause()
    }

    private fun play(oldHolderPlay: PlayHolder?) {
        mCurrentHolder?.let {
            mAutoListener?.onHolderNeedPlay(
                oldHolderPlay as RecyclerView.ViewHolder?,
                it as RecyclerView.ViewHolder
            )
        }
    }

    private fun pause() {
        mCurrentHolder?.let {
            mAutoListener?.onHolderNeedPause(it as RecyclerView.ViewHolder)
        }
//        mCurrentHolder = null
    }

    private fun delayHandler() {
        mRecyclerView.removeCallbacks(mDelayRunnable)
        mRecyclerView.postDelayed(mDelayRunnable, 350)
    }

    private fun getPosition(viewHolder: PlayHolder?): Int {
        return when (viewHolder) {
            is RecyclerView.ViewHolder -> {
                viewHolder.adapterPosition
            }
            null -> {
                -2
            }
            else -> {
                -1
            }
        }
    }

    private fun getPercent(view: View): Int {
        val location = IntArray(2)

        view.getLocationOnScreen(location)

        return if (horizontalScroll) {

            val width = view.width
            val widthRoot = view.rootView.width

            val xTop = location[0]
            val xBottom = xTop + width

            getPercent(xTop, xBottom, width, widthRoot)
        } else {

            val height = view.height
            val heightRoot = view.rootView.height

            val yTop = location[1]
            val yBottom = yTop + height

            getPercent(yTop, yBottom, height, heightRoot)
        }
    }

    private fun getPercent(start: Int, end: Int, size: Int, sizeScreen: Int): Int {
        return if (start <= 0 && end >= sizeScreen) {// tràn màn hình
            101
        } else if (0 in start..end) {// nằm trên màn hình hình có 1 phần ở trong màn hình
            end * 100 / size
        } else if (start >= 0 && end <= sizeScreen && sizeScreen / 2 in (start + 1) until end) {// ở chính giữa màn hình
            101
        } else if (start >= 0 && end <= sizeScreen) {// nằm trong màn hình
            100
        } else if (sizeScreen in start..end) {// nằm dưới màn hình nhưng có 1 phần ở trong màn hình
            (sizeScreen - start) * 100 / size
        } else {
            0
        }
    }

    interface PlayHolder {
        fun getView(): View?
    }

    interface AutoListener {
        fun onHolderNeedPlay(
            oldHolderPlay: RecyclerView.ViewHolder?,
            newHolderPlay: RecyclerView.ViewHolder
        )

        fun onHolderNeedPause(viewHolder: RecyclerView.ViewHolder)
    }
}