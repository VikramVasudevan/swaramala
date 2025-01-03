package com.example.swaramala

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast


class ExtrapolatedSwaramPatternAdapter(context: Context, swaramModelArrayList: List<SwaramModel>) :
    ArrayAdapter<SwaramModel?>(context, 0, swaramModelArrayList as List<SwaramModel>) {

    private var selectedSwaramsArrayList : ArrayList<SwaramModel> = ArrayList<SwaramModel>()
    private lateinit var selectedSwaramsListView : ListView;

    constructor(context: Context, swaramModelArrayList: ArrayList<SwaramModel>, selectedSwaramsListView: ListView) : this(context, swaramModelArrayList) {
        this.selectedSwaramsListView = selectedSwaramsListView;
    }

    fun playSound(prmSwaram : String, callbackFunction : Function<Any>) {
        var audioPlayer : AudioPlayer = AudioPlayer()
        val resID: Int = context.resources.getIdentifier(prmSwaram, "raw", context.packageName)
        audioPlayer.play(context, resID)
        audioPlayer.waitForPlayToEnd(callbackFunction)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(R.layout.swaram_button, parent, false)
        }

        val swaramModel: SwaramModel? = getItem(position)
        val swaramButton = listitemView!!.findViewById<Button>(R.id.swaramId)

        if (swaramModel != null) {
            swaramButton.text = swaramModel.getLabel()
            swaramButton.setOnClickListener { // Do some work here
                // println("You clicked on button ${swaramModel.getLabel()}")
                var x = 1;
                synchronized(x) {
                    Toast.makeText(
                        context,
                        "Now playing ${swaramModel.getLabel()} ...",
                        Toast.LENGTH_SHORT
                    ).show()

                    val colorDrawable = (swaramButton.background as ColorDrawable)
                    val colornumber = colorDrawable.color

                    swaramButton.isEnabled = false
                    swaramButton.text = "Playing " + swaramModel.getLabel()
                    swaramButton.setBackgroundColor(Color.parseColor("#FFD700"));
                    TimerUtils.setTimeout({
                        playSound(swaramModel.getFileName(), {})
                        swaramButton.isEnabled = true
                        swaramButton.setBackgroundColor(colornumber)
                        swaramButton.text = swaramModel.getLabel()
                    }, 100)
                }
            }
        }
        return listitemView
    }
}
