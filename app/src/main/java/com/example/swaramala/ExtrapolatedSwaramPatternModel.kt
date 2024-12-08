package com.example.swaramala

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExtrapolatedSwaramPatternModel : ViewModel() {
    private val mutableExtrapolatedSwaramPattern = MutableLiveData<ArrayList<List<SwaramModel>>>()
    val extrapolatedSwaramPattern: LiveData<ArrayList<List<SwaramModel>>> get() = mutableExtrapolatedSwaramPattern

    fun addSwaramPattern(swaramPattern: List<SwaramModel>) {
        if(mutableExtrapolatedSwaramPattern.value == null) {
            Log.d("SelectedSwaramsModel.addSwaram", "Resetting list")
            mutableExtrapolatedSwaramPattern.setValue (ArrayList<List<SwaramModel>>());
        }
        Log.d("SelectedSwaramsModel.addSwaram", "Adding Swaram " + swaramPattern)
        mutableExtrapolatedSwaramPattern.value!!.add(swaramPattern)
        mutableExtrapolatedSwaramPattern.setValue(getList()!!)
    }

    fun deleteSwaramPatternInPosition(position : Int) {
        mutableExtrapolatedSwaramPattern.value?.removeAt(position)
    }

    fun setList(swaramList: ArrayList<List<SwaramModel>>) {
        Log.d("SelectedSwaramsModel.setList", "Setting new list  " + swaramList)
        mutableExtrapolatedSwaramPattern.setValue( swaramList)
    }

    fun getList(): ArrayList<List<SwaramModel>>? {
        return mutableExtrapolatedSwaramPattern.value
    }
}