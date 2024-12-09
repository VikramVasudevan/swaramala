package com.example.swaramala

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.util.Log


class AudioPlayer {
    private var mMediaPlayer: MediaPlayer? = null

    fun stop() {
        Log.d("AudioPlayer","Stopping Play ...")
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    fun play(c: Context?, rid: Int) {
        stop()

        mMediaPlayer = MediaPlayer.create(c, rid)
        if(mMediaPlayer != null) {
            Log.d("AudioPlayer","Starting Play ...")
            mMediaPlayer!!.setOnCompletionListener(OnCompletionListener { stop() })
            mMediaPlayer!!.start()
        } else {
            Log.e("AudioPlayer","Mediaplayer is null")
        }
    }
}