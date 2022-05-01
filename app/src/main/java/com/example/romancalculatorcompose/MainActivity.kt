package com.example.romancalculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
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
import com.example.romancalculatorcompose.Operation.*
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
    val (lastOperation, setLastOperation) = remember { mutableStateOf<Operation>(PLUS) }

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

            val buttonModifier = Modifier
                .fillMaxSize()
                .weight(1f)

            val deleteButton = @Composable {
                TextButton(
                    onClick = { inputSymbols.removeLastOrNull() },
                    modifier = buttonModifier,
                    shape = CircleShape,
                ) {
                    Text("Del")
                }
            }

            val clearButton = @Composable {
                TextButton(
                    onClick = {
                        setResult(null)
                        inputSymbols.clear()
                        setLastOperation(PLUS)
                    },
                    modifier = buttonModifier,
                    shape = CircleShape,
                ) {
                    Text("Clear")
                }
            }

            fun operationButton(operation: Operation) = @Composable {
                TextButton(
                    onClick = {
                        if (inputSymbols.isEmpty()) {
                            // Just change operation if empty input
                            setLastOperation(operation)
                            return@TextButton
                        }
                        inputSymbols.clear()
                        if (input.value == null) {
                            return@TextButton
                        }
                        val newResult = try {
                            lastOperation.apply(result ?: 0, input.value)
                        } catch (e: ArithmeticException) {
                            // Catch divide by zero and set result to zero
                            0
                        }
                        setResult(newResult)
                        setLastOperation(operation)
                    },
                    modifier = buttonModifier,
                    shape = CircleShape,
                ) {
                    Text(operation.toString())
                }
            }

            fun inputButton(symbol: Symbol) = @Composable {
                TextButton(
                    onClick = { inputSymbols.add(symbol) },
                    modifier = buttonModifier,
                    shape = CircleShape,
                ) {
                    Text(symbol.toString())
                }
            }

            arrayOf(
                arrayOf(deleteButton, clearButton, operationButton(DIVIDE)),
                arrayOf(inputButton(D), inputButton(M), operationButton(TIMES)),
                arrayOf(inputButton(L), inputButton(C), operationButton(MINUS)),
                arrayOf(inputButton(V), inputButton(X), operationButton(PLUS)),
                arrayOf(inputButton(N), inputButton(I), operationButton(EQUALS)),
            ).forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    it.forEach {
                        it()
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
    val (text, invalidValue) = when {
        input.isNotEmpty() -> Pair(input.toString(), input.value == null)
        result == null -> Pair("", false)
        else -> {
            val romanResult = intToRoman(result)
            if (romanResult == null) {
                Pair("nope", true)
            } else {
                Pair(romanResult.toString(), false)
            }
        }
    }
    val color = if (invalidValue) Color.Red else Color.Black
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
