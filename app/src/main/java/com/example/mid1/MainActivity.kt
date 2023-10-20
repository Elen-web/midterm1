package com.example.mid1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mid1.ui.theme.Mid1Theme
import kotlin.math.absoluteValue
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGame()
        }
    }
}



fun generateRandomValue(min: Int, max: Int): Int {
    require(min <= max) { "min must be less than or equal to max" }
    return Random.nextInt(min, max + 1)
}

@Composable
fun NumberGuessingGame() {
    var minRange by remember { mutableStateOf(0) }
    var maxRange by remember { mutableStateOf(100) }
    var sliderValue by remember { mutableStateOf((minRange + maxRange) / 2) }
    var targetValue by remember { mutableStateOf(generateRandomValue(minRange, maxRange)) }

    var score by remember { mutableStateOf(0) }
    var text by remember { mutableStateOf("") } // Initialize message as an empty string

    fun updateScore() {
        val difference = (sliderValue - targetValue).absoluteValue
        when {
            difference <= 3 -> {
                score += 5
                text = "You get 5 points."
            } difference <= 8 -> {
                score += 1
                text = "You get 1 point."
            } else -> {
                text = "You missed the target."
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Let's Play Bull's Eye Game", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(36.dp))

        Text("Move the slider as close to: $targetValue", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(144.dp))

        Slider(
            value = sliderValue.toFloat(),
            onValueChange = { newValue -> sliderValue = newValue.toInt() },
            valueRange = minRange.toFloat()..maxRange.toFloat()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                println(sliderValue)
                updateScore()
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("START")
        }

        Spacer(modifier = Modifier.height(72.dp))

        Text(text = "Score: $score", fontSize = 20.sp, color = Color.Blue)
        Spacer(modifier = Modifier.height(72.dp))

        Text(text = text, fontSize = 20.sp, color = Color.Blue)
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Mid1Theme {
        NumberGuessingGame()
    }
}