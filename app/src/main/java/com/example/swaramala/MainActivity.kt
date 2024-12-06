package com.example.swaramala

import android.os.Bundle
import android.widget.GridView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.example.swaramala.databinding.ActivityMainBinding
import com.example.swaramala.ui.theme.SwaraMalaTheme

class MainActivity : FragmentActivity() {
    // private lateinit var selectedSwaramListView : ListView

    private lateinit var activityMainBinding: ActivityMainBinding

    private val availableSwaramsViewModel: AvailableSwaramsViewModel by viewModels()
    val selectedSwaramsViewModel: SelectedSwaramsModel by viewModels()


    private fun initializeSwaramGrid(){
        val swaramModelArrayList: ArrayList<SwaramModel> = ArrayList<SwaramModel>()
        swaramModelArrayList.add(SwaramModel("P_LOW", "P_LOW", "p (LOW)"))
        swaramModelArrayList.add(SwaramModel("D_LOW", "D_LOW", "D (LOW)"))
        swaramModelArrayList.add(SwaramModel("S", "S", "S"))
        swaramModelArrayList.add(SwaramModel("R", "R", "R"))
        swaramModelArrayList.add(SwaramModel("G", "G", "G"))
        swaramModelArrayList.add(SwaramModel("P", "P", "P"))
        swaramModelArrayList.add(SwaramModel("D", "D", "D"))
        swaramModelArrayList.add(SwaramModel("S_HIGH", "S_HIGH", "S (HIGH)"))
        swaramModelArrayList.add(SwaramModel("R_HIGH", "R_HIGH", "R (HIGH)"))
        swaramModelArrayList.add(SwaramModel("G_HIGH", "G_HIGH", "G (HIGH)"))
        swaramModelArrayList.add(SwaramModel("P_HIGH", "P_HIGH", "P (HIGH)"))

        availableSwaramsViewModel.setList(swaramModelArrayList);
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        // selectedSwaramListView = findViewById(R.id.selectedSwaramsList)

        // selectedSwaramListView.adapter = SelectedSwaramAdapter(this, ArrayList<SwaramModel>())
        // swaramGrid.adapter = SwaramAdapter(this, swaramModelArrayList, selectedSwaramListView)

        initializeSwaramGrid()
        // selectedSwaramsBinding = SelectedSwaramsBinding.inflate(layoutInflater)

    }

    fun toggleSwaram(prmSelected : Any) {
        // Log.d("MainActivity","prmSelected = ${prmSelected}")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        color = Color.Blue,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SwaraMalaTheme {
        Greeting("Vikram")
    }
}