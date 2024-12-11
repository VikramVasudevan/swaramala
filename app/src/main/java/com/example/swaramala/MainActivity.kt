package com.example.swaramala

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import com.example.swaramala.databinding.ActivityMainBinding
import com.example.swaramala.ui.theme.SwaraMalaTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : FragmentActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    private val availableSwaramsViewModel: AvailableSwaramsViewModel by viewModels()
    val selectedSwaramsViewModel: SelectedSwaramsModel by viewModels()
    val extrapolatedSwaramPatternViewModel: ExtrapolatedSwaramPatternModel by viewModels()


    private fun initializeSwaramGrid(){
        val swaramModelArrayList: ArrayList<SwaramModel> = ArrayList<SwaramModel>()
        swaramModelArrayList.add(SwaramModel("P_LOW", "P_LOW", "P LOW", "pa"))
        swaramModelArrayList.add(SwaramModel("D_LOW", "D_LOW", "D LOW", "dha"))
        swaramModelArrayList.add(SwaramModel("S", "S", "S", "sa_lower"))
        swaramModelArrayList.add(SwaramModel("R", "R", "R", "ri"))
        swaramModelArrayList.add(SwaramModel("G", "G", "G", "ga"))
        swaramModelArrayList.add(SwaramModel("P", "P", "P", "pa"))
        swaramModelArrayList.add(SwaramModel("D", "D", "D", "dha"))
        swaramModelArrayList.add(SwaramModel("S_HIGH", "S_HIGH", "S HIGH", "sa_higher"))
        swaramModelArrayList.add(SwaramModel("R_HIGH", "R_HIGH", "R HIGH", "ri_higher"))
        swaramModelArrayList.add(SwaramModel("G_HIGH", "G_HIGH", "G HIGH", "ga_higher"))
        swaramModelArrayList.add(SwaramModel("P_HIGH", "P_HIGH", "P HIGH", "pa_higher"))

        availableSwaramsViewModel.setList(swaramModelArrayList);
    }

    fun readArguments(){
        val b = intent.extras;
        Log.i("MainActivity", "Extras = $b")
        if (b != null) {
            val type = object : TypeToken<ArrayList<SwaramModel>>() {}.type

            val strSelectedSwaramsViewModel = b.getString("selectedSwaramsViewModel")
            if(strSelectedSwaramsViewModel != null) {
                selectedSwaramsViewModel.setList(
                    Gson().fromJson(strSelectedSwaramsViewModel, type)
                )
                Log.d("MainActivity", "selectedSwaramsViewModel = ${selectedSwaramsViewModel.getList()}")
            }
            else {
                Log.w("MainActivity", "selectedSwaramsViewModel is null!")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        readArguments();

        initializeSwaramGrid()
        val playButton = findViewById<Button>(R.id.playButton)
        playButton.isEnabled = false
        playButton.isClickable = false;
        selectedSwaramsViewModel.selectedSwarams.observeForever({
            val size= selectedSwaramsViewModel.getList()?.size
            if (size != null) {
                if( size > 0) {
                    playButton.isEnabled = true
                    playButton.isClickable = true;
                }
            }
        })

        playButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                // Do some work here
                // println("You clicked on button ${swaramModel.getLabel()}")
                Toast.makeText(
                    applicationContext,
                    "Now playing ${selectedSwaramsViewModel.getList().toString()} ...",
                    Toast.LENGTH_LONG
                ).show();

                //Call generate sequence function
                var swaramGenUtils = availableSwaramsViewModel.getList()?.let { SwaramGenUtils(it) }
                /*
                val testSwaram = SwaramModel("P_LOW", "P_LOW", "P (LOW)", "pa")
                var testPattern = listOf(testSwaram, SwaramModel("D", "D", "D", "dha"), SwaramModel("S", "S", "S", "sa_higher"))
                val nextNote = swaramGenUtils?.getNextNote(testSwaram)
                Log.d("MainActivity", nextNote.toString());

                val nextNotes = swaramGenUtils?.getNextNNotes(testSwaram,5)
                Log.d("MainActivity", nextNotes.toString());

                val nextSeq = swaramGenUtils?.getNextSequenceForPattern(testPattern)
                Log.d("MainActivity", "Next Sequence for $testPattern = $nextSeq");

                val highestSwaram = swaramGenUtils?.getHighestSwaramInPattern(testPattern)
                Log.d("MainActivity", "highestSwaram $testPattern = $highestSwaram");

                val lowestSwaram = swaramGenUtils?.getLowestSwaramInPattern(testPattern)
                Log.d("MainActivity", "lowestSwaram $testPattern = $lowestSwaram");
                */
                val fullpattern = selectedSwaramsViewModel.getList()
                    ?.let { swaramGenUtils?.getNextNSequenceForPattern(it, 5) }
                Log.d("MainActivity", "fullpattern $fullpattern");
                if (fullpattern != null) {
                    extrapolatedSwaramPatternViewModel.setList(fullpattern)
                }
                val intent : Intent = Intent(applicationContext, PlaySwaramsActivity::class.java);
                var gson = Gson();
                intent.putExtra("extrapolatedSwaramPatternViewModel", gson.toJson(extrapolatedSwaramPatternViewModel.getList()))
                intent.putExtra("selectedSwaramsViewModel", gson.toJson(selectedSwaramsViewModel.getList()))
                startActivity(intent)
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