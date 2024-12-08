package com.example.swaramala

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AvailableSwaramsViewModel : ViewModel() {
    private val mutableAvailableSwarams = MutableLiveData<ArrayList<SwaramModel>>()
    val availableSwarams: LiveData<ArrayList<SwaramModel>> get() = mutableAvailableSwarams

    fun addSwaram(swaram: SwaramModel) {
        if(mutableAvailableSwarams.value == null)
            mutableAvailableSwarams.value = ArrayList<SwaramModel>();

        mutableAvailableSwarams.value!!.add(swaram)
    }

    fun deleteSwaramInPosition(position : Int) {
        mutableAvailableSwarams.value?.removeAt(position)
    }

    fun setList(swaramList: ArrayList<SwaramModel>) {
        mutableAvailableSwarams.value = swaramList
    }

    fun getList(): ArrayList<SwaramModel>? {
        return mutableAvailableSwarams.value
    }
}