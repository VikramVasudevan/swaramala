package com.example.swaramala

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectedSwaramsModel : ViewModel() {
    private val mutableSelectedSwarams = MutableLiveData<ArrayList<SwaramModel>>()
    val selectedSwarams: LiveData<ArrayList<SwaramModel>> get() = mutableSelectedSwarams

    fun addSwaram(swaram: SwaramModel) {
        if(mutableSelectedSwarams.value == null) {
            Log.d("SelectedSwaramsModel.addSwaram", "Resetting list")
            mutableSelectedSwarams.setValue (ArrayList<SwaramModel>());
        }
        Log.d("SelectedSwaramsModel.addSwaram", "Adding Swaram " + swaram)
        mutableSelectedSwarams.value!!.add(swaram)
        mutableSelectedSwarams.setValue(getList()!!)
    }

    fun deleteSwaramInPosition(position : Int) {
        mutableSelectedSwarams.value?.removeAt(position)
    }

    fun setList(swaramList: ArrayList<SwaramModel>) {
        Log.d("SelectedSwaramsModel.setList", "Setting new list  " + swaramList)
        mutableSelectedSwarams.setValue( swaramList)
    }

    fun getList(): ArrayList<SwaramModel>? {
        return mutableSelectedSwarams.value
    }
}