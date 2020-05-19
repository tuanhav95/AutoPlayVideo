package com.tuanhav95.auto_play_video.example.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuanhav95.auto.Auto
import com.tuanhav95.auto_play_video.example.R
import com.tuanhav95.auto_play_video.example.entities.Source
import com.tuanhav95.auto_play_video.example.entities.SourcePhoto
import com.tuanhav95.auto_play_video.example.entities.SourceVideo
import com.tuanhav95.auto_play_video.example.utils.extension.alphaAnimation
import com.tuanhav95.auto_play_video.example.utils.extension.showImage
import com.tuanhav95.auto_play_video.example.utils.extension.visible
import kotlinx.android.synthetic.main.item_source.view.*

class SourceAdapter(
    private val mList: ArrayList<Source>
) : RecyclerView.Adapter<SourceAdapter.ViewHolder<Source>>() {

    override fun getItemViewType(position: Int): Int {
        val item = mList[position]
        return if (item is SourceVideo) {
            1
        } else {
            super.getItemViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<Source> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_source, parent, false)

        return if (viewType == 1) {
            VideoHolder(view) as ViewHolder<Source>
        } else {
            PhotoHolder(view) as ViewHolder<Source>
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder<Source>, position: Int) {
        holder.onBindViewHolder(mList[position])
    }

     class PhotoHolder(itemView: View) : ViewHolder<SourcePhoto>(itemView) {

        override fun onBindViewHolder(t: SourcePhoto) {
            super.onBindViewHolder(t)
            itemView.imageView.showImage(mItem!!.data!!, -1, -1)
        }
    }

     class VideoHolder(itemView: View) : ViewHolder<SourceVideo>(itemView), Auto.PlayHolder {

        override fun getView(): View? {
            return itemView.videoView
        }

        override fun onBindViewHolder(t: SourceVideo) {
            super.onBindViewHolder(t)
            itemView.imageView.alpha = 1F
            itemView.imageView.visible()

            itemView.imageView.showImage(mItem!!.thumb, -1, -1)
        }

        open fun play() {
            println("VideoHolder play")
            itemView.imageView.alphaAnimation(0f)

            itemView.videoView.setVideoURI(Uri.parse(mItem!!.data))
            itemView.videoView.requestFocus()
            itemView.videoView.start()
        }

        open fun pause() {
            println("VideoHolder pause")
            itemView.imageView.alphaAnimation(1f)

            if (!itemView.videoView.isPlaying) return
            itemView.videoView.pause()
        }
    }

    abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mItem: T? = null

        open fun onBindViewHolder(t: T) {
            mItem = t as T
        }
    }


}