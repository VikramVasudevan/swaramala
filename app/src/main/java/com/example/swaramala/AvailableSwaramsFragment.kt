package com.example.swaramala

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream


class AvailableSwaramsFragment : Fragment() {
    private lateinit var swaramGrid: GridView
    companion object {
        fun newInstance() = AvailableSwaramsFragment()
    }

    private val availableSwaramsViewModel: AvailableSwaramsViewModel by activityViewModels()
    private val selectedSwaramsViewModel: SelectedSwaramsModel by activityViewModels()

    private lateinit var ragams : List<RagamModel>;
    fun initRagams(){
        Log.i("initRagams","initializing ragams ...")
        val inputStream : InputStream = resources.openRawResource(R.raw.ragams)
        val strRagams = inputStream.bufferedReader().use { it.readText() }  // defaults to UTF-8
        // Log.d("MainActivity::getRagams", strRagams )
        val type = object : TypeToken<ArrayList<RagamModel>>() {}.type
        this.ragams = Gson().fromJson<List<RagamModel>>(strRagams, type)
        availableSwaramsViewModel.setList(this.ragams.get(0).getSwarams() as ArrayList<SwaramModel>)
        Log.i("initRagams","availableSwaramsViewModel = ${availableSwaramsViewModel.getList()}")
    }

//    private fun initializeSwaramGrid(){
//        val swaramModelArrayList: ArrayList<SwaramModel> = ArrayList<SwaramModel>()
//        swaramModelArrayList.add(SwaramModel("P_LOW", "P_LOW", "P LOW", "pa"))
//        swaramModelArrayList.add(SwaramModel("D_LOW", "D_LOW", "D LOW", "dha"))
//        swaramModelArrayList.add(SwaramModel("S", "S", "S", "sa_lower"))
//        swaramModelArrayList.add(SwaramModel("R", "R", "R", "ri"))
//        swaramModelArrayList.add(SwaramModel("G", "G", "G", "ga"))
//        swaramModelArrayList.add(SwaramModel("P", "P", "P", "pa"))
//        swaramModelArrayList.add(SwaramModel("D", "D", "D", "dha"))
//        swaramModelArrayList.add(SwaramModel("S_HIGH", "S_HIGH", "S HIGH", "sa_higher"))
//        swaramModelArrayList.add(SwaramModel("R_HIGH", "R_HIGH", "R HIGH", "ri_higher"))
//        swaramModelArrayList.add(SwaramModel("G_HIGH", "G_HIGH", "G HIGH", "ga_higher"))
//        swaramModelArrayList.add(SwaramModel("P_HIGH", "P_HIGH", "P HIGH", "pa_higher"))
//
//        availableSwaramsViewModel.setList(swaramModelArrayList);
//    }

    private fun populateDropDownRagam(myFragmentView : View){
        val items = ragams.map { it.getLabel()}
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
                val swarams = ragams.get(position).getSwarams() as ArrayList<SwaramModel>
                Log.d("AvailableSwaramsFragment","Getting Swarams for ragam ${ragams.get(position)}")
                availableSwaramsViewModel.setList(swarams)
                Log.d("AvailableSwaramsFragment","swarams = ${swarams}")
                //Recreate adapter to update the swaram list.
                swaramGrid.adapter = availableSwaramsViewModel.getList()
                    ?.let { SwaramAdapter(myFragmentView.context, it) }
                selectedSwaramsViewModel.setList(ArrayList())
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
        initRagams();
        val myFragmentView = inflater.inflate(R.layout.fragment_available_swarams, container, false)
        swaramGrid = myFragmentView.findViewById(R.id.swaramGrid)
        swaramGrid.adapter = availableSwaramsViewModel.getList()
            ?.let { SwaramAdapter(myFragmentView.context, it) }

        populateDropDownRagam(myFragmentView)
        return myFragmentView
    }
}