package com.tuanhav95.auto_play_video.example.ui.activity

import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuanhav95.auto.Auto
import com.tuanhav95.auto_play_video.example.R
import com.tuanhav95.auto_play_video.example.entities.Feed
import com.tuanhav95.auto_play_video.example.entities.SourcePhoto
import com.tuanhav95.auto_play_video.example.entities.SourceVideo
import com.tuanhav95.auto_play_video.example.ui.adapter.FeedAdapter
import com.tuanhav95.auto_play_video.example.utils.extension.toPx
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var mAuto: Auto

    lateinit var mVideoView: VideoView

    private val mList = ArrayList<Feed>()

    init {
        /**
         * fake data
         */
        mList.apply {
            add(
                Feed().apply {
                    heightSource = 200.toPx()
                }.apply {
                    source.apply {
                        add(
                            SourcePhoto().apply {
                                data =
                                    "https://images.pexels.com/photos/4275892/pexels-photo-4275892.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                            }
                        )
                    }
                }
            )
        }.apply {
            add(
                Feed().apply {
                    heightSource = 200.toPx()
                    source.apply {
                        add(SourceVideo().apply {
                            data =
                                "https://player.vimeo.com/external/403662486.sd.mp4?s=69904b97740e93b837188de1d3aa288980787936&profile_id=139&oauth2_token_id=57447761"
                            thumb =
                                "https://images.pexels.com/photos/4275892/pexels-photo-4275892.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                        })
                    }.apply {
                        add(SourceVideo().apply {
                            data =
                                "https://player.vimeo.com/external/403662486.sd.mp4?s=69904b97740e93b837188de1d3aa288980787936&profile_id=139&oauth2_token_id=57447761"
                            thumb =
                                "https://images.pexels.com/photos/4275892/pexels-photo-4275892.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                        })
                    }
                }
            )
        }.apply {
            add(
                Feed().apply {
                    heightSource = 200.toPx()
                }.apply {
                    source.apply {
                        add(
                            SourcePhoto().apply {
                                data =
                                    "https://images.pexels.com/photos/4275892/pexels-photo-4275892.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                            }
                        )
                    }
                }
            )
        }.apply {
            add(
                Feed().apply {
                    heightSource = 250.toPx()
                    source.apply {
                        add(
                            SourcePhoto().apply {
                                data =
                                    "https://images.pexels.com/photos/4275892/pexels-photo-4275892.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                            }
                        )
                    }.apply {
                        add(SourceVideo().apply {
                            data =
                                "https://player.vimeo.com/external/403662486.sd.mp4?s=69904b97740e93b837188de1d3aa288980787936&profile_id=139&oauth2_token_id=57447761"
                            thumb =
                                "https://images.pexels.com/photos/4275892/pexels-photo-4275892.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                        })
                    }
                }
            )
        }.apply {
            add(
                Feed().apply {
                    heightSource = 200.toPx()
                    source.apply {
                        add(SourceVideo().apply {
                            data =
                                "https://player.vimeo.com/external/403662486.sd.mp4?s=69904b97740e93b837188de1d3aa288980787936&profile_id=139&oauth2_token_id=57447761"
                            thumb =
                                "https://images.pexels.com/photos/4275892/pexels-photo-4275892.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                        })
                    }.apply {
                        add(SourceVideo().apply {
                            data =
                                "https://player.vimeo.com/external/403662486.sd.mp4?s=69904b97740e93b837188de1d3aa288980787936&profile_id=139&oauth2_token_id=57447761"
                            thumb =
                                "https://images.pexels.com/photos/4275892/pexels-photo-4275892.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                        })
                    }
                }
            )
        }.apply {
            add(
                Feed().apply {
                    heightSource = 200.toPx()
                }.apply {
                    source.apply {
                        add(
                            SourcePhoto().apply {
                                data =
                                    "https://images.pexels.com/photos/4275892/pexels-photo-4275892.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                            }
                        )
                    }
                }
            )
        }.apply {
            add(
                Feed().apply {
                    heightSource = 250.toPx()
                    source.apply {
                        add(
                            SourcePhoto().apply {
                                data =
                                    "https://images.pexels.com/photos/4275892/pexels-photo-4275892.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                            }
                        )
                    }.apply {
                        add(SourceVideo().apply {
                            data =
                                "https://player.vimeo.com/external/403662486.sd.mp4?s=69904b97740e93b837188de1d3aa288980787936&profile_id=139&oauth2_token_id=57447761"
                            thumb =
                                "https://images.pexels.com/photos/4275892/pexels-photo-4275892.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                        })
                    }
                }
            )
        }.apply {
            add(
                Feed().apply {
                    heightSource = 250.toPx()
                    source.apply {
                        add(
                            SourcePhoto().apply {
                                data =
                                    "https://images.pexels.com/photos/4275892/pexels-photo-4275892.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                            }
                        )
                    }.apply {
                        add(SourceVideo().apply {
                            data =
                                "https://player.vimeo.com/external/403662486.sd.mp4?s=69904b97740e93b837188de1d3aa288980787936&profile_id=139&oauth2_token_id=57447761"
                            thumb =
                                "https://images.pexels.com/photos/4275892/pexels-photo-4275892.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                        })
                    }
                }
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.adapter = FeedAdapter(mList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mVideoView = VideoView(this)
        mVideoView.setOnErrorListener { _, _, _ -> true }

        mAuto = Auto(
            recyclerView,
            object : Auto.AutoListener {
                override fun onHolderNeedPlay(
                    oldHolderPlay: RecyclerView.ViewHolder?,
                    newHolderPlay: RecyclerView.ViewHolder
                ) {
                    if (oldHolderPlay != null && oldHolderPlay.adapterPosition != newHolderPlay.adapterPosition) {
                        onHolderNeedPause(oldHolderPlay)
                    }
                    if (newHolderPlay is FeedAdapter.FeedHolder) {
                        newHolderPlay.play()
                    }
                }

                override fun onHolderNeedPause(viewHolder: RecyclerView.ViewHolder) {
                    if (viewHolder is FeedAdapter.FeedHolder) {
                        viewHolder.pause()
                    }
                }

            }, false, false
        )
    }

    override fun onResume() {
        super.onResume()
//        mAuto.start()
    }

    override fun onPause() {
        super.onPause()
        mAuto.pause(true)
    }

}
