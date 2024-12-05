package com.example.swaramala

import android.os.Bundle
import android.util.Log
import android.widget.Switch
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.swaramala.databinding.ActivityMainBinding
import com.example.swaramala.databinding.SelectedSwaramsBinding
import com.example.swaramala.ui.theme.SwaraMalaTheme

class MainActivity : ComponentActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var selectedSwaramsBinding: SelectedSwaramsBinding

    val selectedSwarams = ArrayList<CharSequence>()
    fun handleSwaramSelection(prmSwitch : Switch , isChecked : Boolean){
        val message = if (isChecked) "${prmSwitch.text}:ON" else "${prmSwitch.text}:OFF"
        Toast.makeText(this@MainActivity, message,
            Toast.LENGTH_SHORT).show()
        if(isChecked) {
            selectedSwarams.add(prmSwitch.text)
        } else {
            selectedSwarams.remove(prmSwitch.text)
        }
        selectedSwaramsBinding.selectedSwarams.text = "$selectedSwarams"
        println("Selected Swarams = $selectedSwarams")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        selectedSwaramsBinding = SelectedSwaramsBinding.inflate(layoutInflater)

        val switch1 : Switch = findViewById(R.id.switch1)
        val switch2 : Switch = findViewById(R.id.switch2)
        val switch3 : Switch = findViewById(R.id.switch3)
        val switch4 : Switch = findViewById(R.id.switch4)
        val switch5 : Switch = findViewById(R.id.switch5)
        val switch6 : Switch = findViewById(R.id.switch6)
        switch1.setOnCheckedChangeListener({ _ , isChecked ->
            handleSwaramSelection(switch1, isChecked)
        })

        switch1.setOnCheckedChangeListener({ _ , isChecked ->
            handleSwaramSelection(switch1, isChecked)
        })

        switch2.setOnCheckedChangeListener({ _ , isChecked ->
            handleSwaramSelection(switch2, isChecked)
        })

        switch3.setOnCheckedChangeListener({ _ , isChecked ->
            handleSwaramSelection(switch3, isChecked)
        })

        switch4.setOnCheckedChangeListener({ _ , isChecked ->
            handleSwaramSelection(switch4, isChecked)
        })

        switch5.setOnCheckedChangeListener({ _ , isChecked ->
            handleSwaramSelection(switch5, isChecked)
        })

        switch6.setOnCheckedChangeListener({ _ , isChecked ->
            handleSwaramSelection(switch6, isChecked)
        })
        /* setContent {
            SwaraMalaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Vikram",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }*/
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