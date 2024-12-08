package com.example.swaramala

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.activityViewModels

class ExtrapolatedSwaramPatternFragment : Fragment() {
    private lateinit var playPatternGrid: GridView
    companion object {
        fun newInstance() = ExtrapolatedSwaramPatternFragment()
    }

    val extrapolatedSwaramPatternViewModel: ExtrapolatedSwaramPatternModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myFragmentView = inflater.inflate(R.layout.fragment_play_pattern, container, false)
        playPatternGrid = myFragmentView.findViewById(R.id.playPatternGrid)
        extrapolatedSwaramPatternViewModel.extrapolatedSwaramPattern.observeForever( {
            swaram ->
            playPatternGrid.numColumns =
                extrapolatedSwaramPatternViewModel.getList()?.get(0)?.size!!;

            playPatternGrid.adapter =
                ExtrapolatedSwaramPatternAdapter(myFragmentView.context, extrapolatedSwaramPatternViewModel.getListFlattened())

        })
        return myFragmentView
    }
}