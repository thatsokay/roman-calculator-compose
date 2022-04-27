package com.example.romancalculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val inputSymbols = remember { mutableStateListOf<Symbol>() }
    val input = Numeral(*inputSymbols.toTypedArray())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Display(
                result = result,
                input = input,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
            arrayOf(
                arrayOf(D, M),
                arrayOf(L, C),
                arrayOf(V, X),
                arrayOf(N, I),
            ).forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    it.forEach {
                        TextButton(
                            onClick = { inputSymbols.add(it) },
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                        ) {
                            Text(it.toString())
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CalculatorPreview() {
    RomanCalculatorComposeTheme {
        Calculator()
    }
}

@Composable
fun Display(result: Int?, input: Numeral, modifier: Modifier = Modifier) {
    val text = when {
        input.isNotEmpty() -> input.toString()
        result == null -> ""
        else -> intToRoman(result)?.toString() ?: "nope"
    }
    val color = if (input.isNotEmpty() && input.value == null) {
        Color.Red
    } else {
        Color.Black
    }
    Text(
        text,
        textAlign = TextAlign.Right,
        color = color,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun DisplayResultPreview() {
    RomanCalculatorComposeTheme {
        Display(result = 4, input = Numeral())
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayNullResultPreview() {
    RomanCalculatorComposeTheme {
        Display(result = null, input = Numeral())
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayInvalidResultPreview() {
    RomanCalculatorComposeTheme {
        Display(4000, input = Numeral())
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayInputPreview() {
    RomanCalculatorComposeTheme {
        Display(result = 4, input = Numeral(X))
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayInvalidInputPreview() {
    RomanCalculatorComposeTheme {
        Display(result = 4, input = Numeral(V, V))
    }
}
