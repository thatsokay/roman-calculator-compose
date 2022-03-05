package com.example.romancalculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.romancalculatorcompose.Symbol.*
import com.example.romancalculatorcompose.ui.theme.RomanCalculatorComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RomanCalculatorComposeTheme {
                Calculator()
            }
        }
    }
}

@Composable
fun Calculator() {
    val (result, setResult) = remember { mutableStateOf<Int?>(null) }
    val (input, setInput) = remember { mutableStateOf(emptyList<Symbol>()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Display(result, input)
    }
}

@Composable
fun Display(result: Int?, input: List<Symbol>) {
    val text = when {
        input.isNotEmpty() -> input.joinToString("")
        result == null -> ""
        else -> intToRoman(result)?.toString() ?: "nope"
    }
    Text(text, textAlign = TextAlign.Right)
}

@Preview(showBackground = true)
@Composable
fun DisplayResultPreview() {
    RomanCalculatorComposeTheme {
        Display(result = 4, input = emptyList())
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayNullResultPreview() {
    RomanCalculatorComposeTheme {
        Display(result = null, input = emptyList())
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayInvalidResultPreview() {
    RomanCalculatorComposeTheme {
        Display(4000, input = emptyList())
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayInputPreview() {
    RomanCalculatorComposeTheme {
        Display(result = 4, input = listOf(X))
    }
}
