package com.tuanhav95.auto_play_video.example.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuanhav95.auto.Auto
import com.tuanhav95.auto_play_video.example.R
import com.tuanhav95.auto_play_video.example.entities.Feed
import com.tuanhav95.auto_play_video.example.entities.SourceVideo
import com.tuanhav95.auto_play_video.example.utils.extension.resize
import kotlinx.android.synthetic.main.item_multi.view.*

class FeedAdapter(private val mList: ArrayList<Feed>) :
    RecyclerView.Adapter<FeedAdapter.ViewHolder<Feed>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<Feed> {
        return FeedHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_multi, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder<Feed>, position: Int) {
        holder.onBindViewHolder(mList[position])
    }

    class FeedHolder(view: View) : ViewHolder<Feed>(view), Auto.PlayHolder {

        var mAuto: Auto

        init {
            mAuto = Auto(itemView.recSource, object : Auto.AutoListener {
                override fun onHolderNeedPlay(
                    oldHolderPlay: RecyclerView.ViewHolder?,
                    newHolderPlay: RecyclerView.ViewHolder
                ) {
                    if (oldHolderPlay?.adapterPosition == newHolderPlay?.adapterPosition) {
                        return
                    }

                    oldHolderPlay?.let {
                        onHolderNeedPause(it)
                    }

                    if (newHolderPlay is SourceAdapter.VideoHolder) {
                        newHolderPlay.play()
                    }
                }

                override fun onHolderNeedPause(viewHolder: RecyclerView.ViewHolder) {
                    if (viewHolder is SourceAdapter.VideoHolder) {
                        viewHolder.pause()
                    }
                }

            }, true, false)
        }

        override fun getView(): View? {
            mItem?.let {
                for (source in it.source) {
                    if (source is SourceVideo) {
                        return itemView.recSource
                    }
                }
                println("getView")
                return null
            }
            return itemView.recSource
        }

        override fun onBindViewHolder(t: Feed) {
            super.onBindViewHolder(t)
            itemView.recSource.resize(-3, mItem!!.heightSource)

            itemView.recSource.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            itemView.recSource.adapter =
                SourceAdapter(mItem!!.source)
        }

        open fun play() {
            println("FeedHolder play")
            mAuto.start()
        }

        open fun pause() {
            println("FeedHolder pause")
            mAuto.pause(true)
        }
    }


    abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mItem: T? = null

        open fun onBindViewHolder(t: T) {
            mItem = t
        }
    }
}