package com.example.unitconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.unitconverter.ui.theme.UnitConverterTheme
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import kotlin.time.times

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    UnitConverter()
                }
            }
        }
    }
}
@Composable
fun UnitConverter(){
    var inputValue by remember { mutableStateOf(value = "")}
    var outputValue by remember { mutableStateOf(value = "")}
    var inputUnit by remember { mutableStateOf(value = "Input Unit")}
    var outputUnit by remember { mutableStateOf(value = "Output Unit")}
    var iExpanded by remember { mutableStateOf(value = false)}
    var oExpanded by remember { mutableStateOf(value = false)}
    val iconversion = remember { mutableStateOf(value = 1.0)}
    val oconversion = remember { mutableStateOf(value = 1.0)}

    fun ConvertUnits(){
        val inputValueDouble = inputValue.toDoubleOrNull()?:0.0
        val result = ((inputValueDouble * iconversion.value * 100.0)/oconversion.value).roundToInt()/100.0
        outputValue = result.toString()
    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        //elements will be one below other
        Text(text = "Unit Converter")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = inputValue, onValueChange = {
            inputValue = it
            ConvertUnits()
        }, label = { Text(text = "Enter the Value")})
        Spacer(modifier = Modifier.height(16.dp))
        Row {
                Box {
                    Button(onClick = {iExpanded = true}) {
                        Text(text = inputUnit)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                    }
                    DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                        DropdownMenuItem(text = { Text(text = "Millimeters")}, onClick = {
                            iExpanded = false
                            inputUnit = "Millimeters"
                            iconversion.value = 0.1
                            ConvertUnits()
                        })
                        DropdownMenuItem(text = { Text(text = "Centimeters")}, onClick = {
                            iExpanded = false
                            inputUnit = "Centimeters"
                            iconversion.value = 1.0
                            ConvertUnits()
                        })
                        DropdownMenuItem(text = { Text(text = "Meters")}, onClick = {
                            iExpanded = false
                            inputUnit = "Meters"
                            iconversion.value = 100.0
                            ConvertUnits()
                        })
                        DropdownMenuItem(text = { Text(text = "Inches")}, onClick = {
                            iExpanded = false
                            inputUnit = "Inches"
                            iconversion.value = 2.54
                            ConvertUnits()
                        })
                    }
                }

            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = {oExpanded=true}) {
                    Text(text = outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    DropdownMenuItem(text = { Text(text = "Millimeters")}, onClick = {
                        oExpanded = false
                        outputUnit = "Millimeters"
                        oconversion.value = 0.1
                        ConvertUnits()
                    },enabled = "Millimeters" != inputUnit)
                    DropdownMenuItem(text = { Text(text = "Centimeters")}, onClick = {
                        oExpanded = false
                        outputUnit = "Centimeters"
                        oconversion.value = 1.0
                        ConvertUnits()
                    },enabled = "Centimeters" != inputUnit)
                    DropdownMenuItem(text = { Text(text = "Meters")}, onClick = {
                        oExpanded = false
                        outputUnit = "Meters"
                        oconversion.value = 100.0
                        ConvertUnits()
                    },enabled = "Meters" != inputUnit)
                    DropdownMenuItem(text = { Text(text = "Inches")}, onClick = {
                        oExpanded = false
                        outputUnit = "Inches"
                        oconversion.value = 2.54
                        ConvertUnits()
                    },enabled = "Inches" != inputUnit)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if(inputValue!="" && inputUnit!="Input Unit" && outputValue!="" && outputUnit!="Output Unit") {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Result: $inputValue $inputUnit is $outputValue $outputUnit")
            }
            Button(onClick = {
                //swap units
                var tmp=inputUnit
                inputUnit=outputUnit
                outputUnit=tmp
                iconversion.value = when(inputUnit){
                    "Millimeters" -> 0.1
                    "Centimeters" -> 1.0
                    "Meters" -> 100.0
                    else -> 2.54
                }
                oconversion.value = when(outputUnit){
                    "Millimeters" -> 0.1
                    "Centimeters" -> 1.0
                    "Meters" -> 100.0
                    else -> 2.54
                }
                ConvertUnits()
            }) {
                Text(text = "Reverse Units")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}