package com.example.swaramala

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels

class AvailableSwaramsFragment : Fragment() {
    private lateinit var swaramGrid: GridView
    companion object {
        fun newInstance() = AvailableSwaramsFragment()
    }

    private val availableSwaramsViewModel: AvailableSwaramsViewModel by activityViewModels()

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
        return myFragmentView
    }
}