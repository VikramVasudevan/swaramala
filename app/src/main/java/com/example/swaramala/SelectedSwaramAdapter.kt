package com.example.swaramala

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SelectedSwaramAdapter(context: Context, swaramModelArrayList: ArrayList<SwaramModel>) :
    ArrayAdapter<SwaramModel?>(context, 0, swaramModelArrayList as List<SwaramModel?>) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(R.layout.swaram_editor, parent, false)
        }

        val swaramModel: SwaramModel? = getItem(position)
        val swaramTV = listitemView!!.findViewById<TextView>(R.id.selectedSwaram)

        if (swaramModel != null) {
            swaramTV.setText(swaramModel.getLabel())
        }
        return listitemView
    }
}
