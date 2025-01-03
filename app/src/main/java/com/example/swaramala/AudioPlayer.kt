package com.example.swaramala

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Handler
import android.os.Looper
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
        try {
            stop()

            mMediaPlayer = MediaPlayer.create(c, rid)
            if (mMediaPlayer != null) {
                Log.d("AudioPlayer", "Starting Play ...")
                mMediaPlayer!!.setOnCompletionListener(OnCompletionListener { stop() })
                mMediaPlayer!!.start()
            } else {
                Log.e("AudioPlayer", "Mediaplayer is null")
            }
        } catch(e : Exception) {
            Log.w("AudioPlayer","Unable to play ...")
        }
    }

    fun waitForPlayToEnd(callbackFunction : Function<Any>) {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                // This method will be executed once the timer is over
                callbackFunction.apply {  }
            },
            1000 // value in milliseconds
        )
        if(mMediaPlayer != null) {
            while(mMediaPlayer!!.isPlaying){
                // NOOP
            }
        }
    }
}