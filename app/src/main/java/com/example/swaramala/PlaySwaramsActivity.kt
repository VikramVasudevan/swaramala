package com.example.swaramala

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.swaramala.databinding.ActivityPlaySwaramsBinding
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.concurrent.TimeUnit


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class PlaySwaramsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaySwaramsBinding
    // private lateinit var fullscreenContent: TextView
    // private lateinit var fullscreenContentControls: LinearLayout
    private val hideHandler = Handler(Looper.myLooper()!!)

    val extrapolatedSwaramPatternViewModel: ExtrapolatedSwaramPatternModel by viewModels()

    fun playSound(prmSwaram : String) {
        var audioPlayer : AudioPlayer = AudioPlayer()
        val resID: Int = resources.getIdentifier(prmSwaram, "raw", packageName)
        audioPlayer.play(applicationContext, resID)
        TimeUnit.SECONDS.sleep(3L)
        // audioPlayer.waitForPlayToEnd()
    }
    @SuppressLint("InlinedApi")
    private val hidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar
        if (Build.VERSION.SDK_INT >= 30) {
            // fullscreenContent.windowInsetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
//            fullscreenContent.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LOW_PROFILE or
//                        View.SYSTEM_UI_FLAG_FULLSCREEN or
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
//                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
//                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }
    private val showPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()
        // fullscreenContentControls.visibility = View.VISIBLE
    }
    private var isFullscreen: Boolean = false

    private val hideRunnable = Runnable { hide() }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val delayHideTouchListener = View.OnTouchListener { view, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS)
            }

            MotionEvent.ACTION_UP -> view.performClick()
            else -> {
            }
        }
        false
    }

    var playingInProgress : Boolean = false;
    var stopRequestPlaced : Boolean = false;
    var noteDuration = 4000;

    fun clickButtonAtIndex(index : Int){
        binding.playPatternGrid.smoothScrollToPosition(index)
        val gridTile = binding.playPatternGrid.getChildAt(index)
        if(gridTile != null) {
            Log.w("playAll","GridTile is not null!")
            gridTile.requestFocus();
            val button = gridTile.findViewWithTag<Button>("swaram_button")
            // button.setBackgroundColor(Color.parseColor("#FFD700"));
            TimerUtils.setTimeout({
                if(!stopRequestPlaced) {
                    button.callOnClick()
                }
            }, (noteDuration * index).toLong())
        } else {
            Log.w("playAll","GridTile is null!")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlaySwaramsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.playPatternGrid.expanded = true;

        isFullscreen = true

        // Set up the user interaction to manually show or hide the system UI.
        // fullscreenContent = binding.fullscreenContent
        // fullscreenContent.setOnClickListener { toggle() }

        // fullscreenContentControls = binding.fullscreenContentControls

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        binding.dummyButton.setOnTouchListener(delayHideTouchListener)
        binding.dummyButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
                val gson = Gson();
                mainActivityIntent.putExtra("selectedSwaramsViewModel", intent.getStringExtra("selectedSwaramsViewModel"))
                startActivity(mainActivityIntent)
            }
        })

        binding.playAllButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                onPlayStart();
                // Stop play after all swarams have finished playing.
                TimerUtils.setTimeout({
                    onPlayStopped()
                    stopRequestPlaced = false
                },
                    (noteDuration * extrapolatedSwaramPatternViewModel.getListFlattened().size).toLong()
                )
                extrapolatedSwaramPatternViewModel.getListFlattened().forEachIndexed {
                        index, swaram ->
                    Log.d("Player","Playing swaram ${swaram.getFileName()} at index $index")
                    clickButtonAtIndex(index)
                    // playSound(swaram.getFileName())
                }

            }
        })

        binding.stopButton.visibility =View.INVISIBLE
        binding.stopButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                onStopRequested();
            }
        })

        Log.i("PlaySwaramsActivity", "Getting Extras")
        val b = intent.extras;
        Log.i("PlaySwaramsActivity", "Extras = $b")
        if (b != null) {
            val type = object : TypeToken<List<ArrayList<SwaramModel>>>() {}.type

            extrapolatedSwaramPatternViewModel.setList(
                Gson().fromJson(b.getString("extrapolatedSwaramPatternViewModel"), type)
            )
            Log.i("PlaySwaramsActivity", "extrapolatedSwaramPatternViewModel = ${extrapolatedSwaramPatternViewModel.getList()}")

            extrapolatedSwaramPatternViewModel.extrapolatedSwaramPattern.observeForever({
                    swaram ->
                binding.playPatternGrid.numColumns =
                    extrapolatedSwaramPatternViewModel.getList()?.get(0)?.size!!;

                binding.playPatternGrid.adapter =
                    ExtrapolatedSwaramPatternAdapter(applicationContext, extrapolatedSwaramPatternViewModel.getListFlattened())

            })
        }
        populateDropDownPitch()
    }

    private fun populateDropDownPitch(){
        val items = arrayOf("C", "C#", "D", "D#", "E", "F","F#", "G", "G#", "A", "A#", "B")
        binding.dropDownPitch.adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, items)
    }

    private fun onStopRequested() {
        binding.stopButton.text = "Stopping ..."
        Toast.makeText(applicationContext, "Stopping ...", Toast.LENGTH_LONG).show()
        stopRequestPlaced = !stopRequestPlaced
        TimerUtils.setTimeout({
            binding.stopButton.text = "STOP"
            onPlayStopped()
        },noteDuration.toLong())

    }

    private fun onPlayStart() {
        playingInProgress = true
        binding.playAllButton.text = "Playing ..."
        binding.playAllButton.isEnabled = false;
        binding.stopButton.isEnabled = true
        binding.stopButton.visibility = View.VISIBLE
    }

    fun onPlayStopped() {
        playingInProgress = false
        binding.playAllButton.isEnabled = true;
        binding.stopButton.visibility = View.INVISIBLE
        binding.stopButton.isEnabled = false
        binding.playAllButton.text = "PLAY"
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    private fun toggle() {
        if (isFullscreen) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
        // fullscreenContentControls.visibility = View.GONE
        isFullscreen = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        hideHandler.removeCallbacks(showPart2Runnable)
        hideHandler.postDelayed(hidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {
        // Show the system bar
        if (Build.VERSION.SDK_INT >= 30) {
            // fullscreenContent.windowInsetsController?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
//            fullscreenContent.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
//                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
        isFullscreen = true

        // Schedule a runnable to display UI elements after a delay
        hideHandler.removeCallbacks(hidePart2Runnable)
        hideHandler.postDelayed(showPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        hideHandler.removeCallbacks(hideRunnable)
        hideHandler.postDelayed(hideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private const val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private const val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private const val UI_ANIMATION_DELAY = 300
    }
}