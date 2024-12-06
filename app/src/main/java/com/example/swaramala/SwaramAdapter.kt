package com.example.swaramala

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast

class SwaramAdapter(context: Context, swaramModelArrayList: ArrayList<SwaramModel>) :
    ArrayAdapter<SwaramModel?>(context, 0, swaramModelArrayList as List<SwaramModel?>) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(R.layout.selected_swarams, parent, false)
        }

        val swaramModel: SwaramModel? = getItem(position)
        val swaramButton = listitemView!!.findViewById<TextView>(R.id.swaramId)

        if (swaramModel != null) {
            swaramButton.setText(swaramModel.getLabel())
            swaramButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    // Do some work here
                    println("You clicked on button ${swaramModel.getLabel()}")
                }

            })
        }
        return listitemView
    }
}
