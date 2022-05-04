package com.example.romancalculatorcompose.calculator

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.romancalculatorcompose.roman.Roman
import com.example.romancalculatorcompose.roman.Symbol
import com.example.romancalculatorcompose.roman.Symbol.*
import com.example.romancalculatorcompose.roman.intToRoman
import com.example.romancalculatorcompose.ui.theme.RomanCalculatorComposeTheme

@Composable
fun Calculator(
    result: Int?,
    input: Roman,
    onSymbolPressed: (Symbol) -> Unit,
    onOperationPressed: (Operation) -> Unit,
    onDeletePressed: () -> Unit,
    onClearPressed: () -> Unit,
) {
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
                    onClick = onDeletePressed,
                    modifier = buttonModifier,
                    shape = CircleShape,
                ) {
                    Text("Del")
                }
            }

            val clearButton = @Composable {
                TextButton(
                    onClick = onClearPressed,
                    modifier = buttonModifier,
                    shape = CircleShape,
                ) {
                    Text("Clear")
                }
            }

            fun operationButton(operation: Operation) = @Composable {
                TextButton(
                    onClick = { onOperationPressed(operation) },
                    modifier = buttonModifier,
                    shape = CircleShape,
                ) {
                    Text(operation.toString())
                }
            }

            fun inputButton(symbol: Symbol) = @Composable {
                TextButton(
                    onClick = { onSymbolPressed(symbol) },
                    modifier = buttonModifier,
                    shape = CircleShape,
                ) {
                    Text(symbol.toString())
                }
            }

            arrayOf(
                arrayOf(deleteButton, clearButton, operationButton(Operation.DIVIDE)),
                arrayOf(inputButton(D), inputButton(M), operationButton(Operation.TIMES)),
                arrayOf(inputButton(L), inputButton(C), operationButton(Operation.MINUS)),
                arrayOf(inputButton(V), inputButton(X), operationButton(Operation.PLUS)),
                arrayOf(inputButton(N), inputButton(I), operationButton(Operation.EQUALS)),
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
fun CalculatorPreview(viewModel: CalculatorViewModel = CalculatorViewModel()) {
    RomanCalculatorComposeTheme {
        Calculator(
            result = viewModel.result,
            input = viewModel.input,
            onSymbolPressed = { viewModel.enterSymbol(it) },
            onOperationPressed = { viewModel.enterOperation(it) },
            onDeletePressed = { viewModel.delete() },
            onClearPressed = { viewModel.clear() },
        )
    }
}

@Composable
private fun Display(result: Int?, input: Roman, modifier: Modifier = Modifier) {
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
        Display(result = 4, input = Roman())
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayNullResultPreview() {
    RomanCalculatorComposeTheme {
        Display(result = null, input = Roman())
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayInvalidResultPreview() {
    RomanCalculatorComposeTheme {
        Display(4000, input = Roman())
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayInputPreview() {
    RomanCalculatorComposeTheme {
        Display(result = 4, input = Roman(X))
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayInvalidInputPreview() {
    RomanCalculatorComposeTheme {
        Display(result = 4, input = Roman(V, V))
    }
}
