package com.example.einfuehrung

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.einfuehrung.ui.theme.EinfuehrungTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EinfuehrungTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    // State to store the text input
    val textState = remember { mutableStateOf("") }

    // UI with a TextField and a Text that displays the input
    Column(modifier = modifier) {
        TextField(
            value = textState.value,
            onValueChange = { newText ->
                textState.value = newText
            },
            label = { Text("Enter your name") }
        )
        Text(text = "Hello ${textState.value}!")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EinfuehrungTheme {
        Greeting()
    }
}