package com.example.myapplication

import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_video.view.*


class MessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mList = ArrayList<Message>()

    init {
        mList.add(MessageVideo(200.toPx()))
        mList.add(Message())
        mList.add(Message())
        mList.add(Message())
        mList.add(MessageVideo(300.toPx()))
        mList.add(Message())
        mList.add(MessageVideo(200.toPx()))
        mList.add(MessageVideo(200.toPx()))
        mList.add(MessageVideo(200.toPx()))
        mList.add(MessageVideo(200.toPx()))
        mList.add(MessageVideo(400.toPx()))
        mList.add(Message())
        mList.add(Message())
        mList.add(Message())
        mList.add(Message())
        mList.add(Message())
        mList.add(Message())
        mList.add(Message())
        mList.add(Message())
        mList.add(Message())
        mList.add(Message())
        mList.add(Message())
        mList.add(Message())
        mList.add(Message())
        mList.add(Message())
        mList.add(MessageVideo(205.toPx()))

    }

    override fun getItemViewType(position: Int): Int {
        val item = mList[position]
        return if (item is MessageVideo) {
            1
        } else {
            super.getItemViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            VideoHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
            )
        } else {
            NormalHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VideoHolder) {
            holder.itemView.frameVideo.resize(
                FrameLayout.LayoutParams.MATCH_PARENT,
                (mList[position] as MessageVideo).height
            )
        }
    }

    private class NormalHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView)

    private class VideoHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), MainActivity.Auto.PlayHolder {

        override fun getView(): View {
            return itemView.frameVideo
        }

    }

    private fun Int.toPx(): Int {
        return this.toFloat().toPx()
    }

    private fun Float.toPx(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    private fun View.resize(width: Int, height: Int) {
        var update = false
        if (layoutParams.width != width && width >= -2) {
            layoutParams.width = width
            update = true
        }

        if (layoutParams.height != height && height >= -2) {
            layoutParams.height = height
            update = true
        }
        if (update) {
            layoutParams = layoutParams
        }
    }

}