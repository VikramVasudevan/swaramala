package com.example.swaramala

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast


class ExtrapolatedSwaramPatternAdapter(context: Context, swaramModelArrayList: List<SwaramModel>) :
    ArrayAdapter<SwaramModel?>(context, 0, swaramModelArrayList as List<SwaramModel>) {

    private var selectedSwaramsArrayList : ArrayList<SwaramModel> = ArrayList<SwaramModel>()
    private lateinit var selectedSwaramsListView : ListView;

    constructor(context: Context, swaramModelArrayList: ArrayList<SwaramModel>, selectedSwaramsListView: ListView) : this(context, swaramModelArrayList) {
        this.selectedSwaramsListView = selectedSwaramsListView;
    }

    fun playSound(prmSwaram : String) {
        var audioPlayer : AudioPlayer = AudioPlayer()
        val resID: Int = context.resources.getIdentifier(prmSwaram, "raw", context.packageName)
        audioPlayer.play(context, resID)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(R.layout.swaram_button, parent, false)
        }

        val swaramModel: SwaramModel? = getItem(position)
        val swaramButton = listitemView!!.findViewById<TextView>(R.id.swaramId)

        if (swaramModel != null) {
            swaramButton.setText(swaramModel.getLabel())
            swaramButton.setOnClickListener { // Do some work here
                // println("You clicked on button ${swaramModel.getLabel()}")
                Toast.makeText(
                    context,
                    "You clicked on button ${swaramModel.getLabel()}",
                    Toast.LENGTH_SHORT
                ).show()

                // TODO Play sound on click of button.
                playSound(swaramModel.getFileName())
            }
        }
        return listitemView
    }
}
