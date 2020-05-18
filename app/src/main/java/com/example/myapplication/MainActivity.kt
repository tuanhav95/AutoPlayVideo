package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuanha95.auto.Auto
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_video.view.*


class MainActivity : AppCompatActivity() {

    lateinit var mAuto: Auto

    lateinit var mVideoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.adapter = MessageAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

        mVideoView = VideoView(this)
        mVideoView.setOnErrorListener { _, _, _ -> true }

        mAuto = Auto(
            recyclerView,
            object : Auto.AutoListener {
                override fun onHolderNeedPlay(viewHolder: RecyclerView.ViewHolder) {
                    mVideoView.alpha = 0F

                    viewHolder.itemView.frameVideo.add(mVideoView)

                    mVideoView.animate()
                        .alpha(1f)
                        .setDuration(10000)
                        .start()

                    mVideoView.setVideoURI(Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + R.raw.video))
                    mVideoView.requestFocus()
                    mVideoView.start()
                }

                override fun onHolderNeedPause(viewHolder: RecyclerView.ViewHolder) {
                    if (!mVideoView.isPlaying) return
                    mVideoView.pause()
                }

            },
            false,
            reverse = true
        )

        mAuto.start()
    }

    override fun onResume() {
        super.onResume()
        mAuto.start()
    }

    override fun onPause() {
        super.onPause()
        mAuto.pause(true)
    }

    fun ViewGroup.add(view: View) {
        view.parent?.let {
            (it as ViewGroup).removeView(view)
        }
        addView(
            view,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

}
