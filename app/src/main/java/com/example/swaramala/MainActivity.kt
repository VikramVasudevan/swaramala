package com.example.swaramala

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.ListView
import android.widget.Toast
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
    private lateinit var activityMainBinding: ActivityMainBinding

    private val availableSwaramsViewModel: AvailableSwaramsViewModel by viewModels()
    val selectedSwaramsViewModel: SelectedSwaramsModel by viewModels()


    private fun initializeSwaramGrid(){
        val swaramModelArrayList: ArrayList<SwaramModel> = ArrayList<SwaramModel>()
        swaramModelArrayList.add(SwaramModel("P_LOW", "P_LOW", "P (LOW)"))
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

        initializeSwaramGrid()
        val playButton = findViewById<Button>(R.id.playButton)
        playButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                // Do some work here
                // println("You clicked on button ${swaramModel.getLabel()}")
                Toast.makeText(
                    applicationContext,
                    "You clicked on play button ${selectedSwaramsViewModel.getList().toString()}",
                    Toast.LENGTH_LONG
                ).show();

                //TODO Call generate sequence function
                var swaramGenUtils = availableSwaramsViewModel.getList()?.let { SwaramGenUtils(it) }
                val testSwaram = SwaramModel("P_LOW", "P_LOW", "P (LOW)")
                var testPattern = listOf(testSwaram, SwaramModel("D", "D", "D"), SwaramModel("S", "S", "S"))
                val nextNote = swaramGenUtils?.getNextNote(testSwaram)
                Log.d("MainActivity", nextNote.toString());

                val nextNotes = swaramGenUtils?.getNextNNotes(testSwaram,5)
                Log.d("MainActivity", nextNotes.toString());

                val nextSeq = swaramGenUtils?.getNextSequenceForPattern(testPattern)
                Log.d("MainActivity", "Next Sequence for $testPattern = $nextSeq");

                //TODO call play function
            }
        }
        )
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