package com.example.swaramala

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels


class AvailableSwaramsFragment : Fragment() {
    private lateinit var swaramGrid: GridView
    companion object {
        fun newInstance() = AvailableSwaramsFragment()
    }

    private val availableSwaramsViewModel: AvailableSwaramsViewModel by activityViewModels()

    private fun populateDropDownRagam(myFragmentView : View){
        val items = arrayOf("Mohanam", "Thodi", "Sahana")
        val dropDownRagam = myFragmentView.findViewById<Spinner>(R.id.dropDownRagam)
        val adapter = ArrayAdapter(myFragmentView.context, android.R.layout.simple_spinner_dropdown_item, items)
        adapter.setDropDownViewResource(R.layout.spinner_style);
        dropDownRagam.adapter = adapter
        dropDownRagam.setSelection(0, true)
        val view: View = dropDownRagam.getSelectedView()
        (view as TextView).setTextColor(Color.WHITE)

        dropDownRagam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                // Change the selected item's text color
                (view as TextView).setTextColor(Color.WHITE)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myFragmentView = inflater.inflate(R.layout.fragment_available_swarams, container, false)
        swaramGrid = myFragmentView.findViewById(R.id.swaramGrid)
        swaramGrid.adapter = availableSwaramsViewModel.getList()
            ?.let { SwaramAdapter(myFragmentView.context, it) }

        populateDropDownRagam(myFragmentView)
        return myFragmentView
    }
}