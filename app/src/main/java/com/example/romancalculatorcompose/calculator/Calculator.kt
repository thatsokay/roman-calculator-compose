package com.example.romancalculatorcompose.calculator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.romancalculatorcompose.roman.Roman
import com.example.romancalculatorcompose.roman.Symbol
import com.example.romancalculatorcompose.roman.Symbol.*
import com.example.romancalculatorcompose.roman.toSignedRoman
import com.example.romancalculatorcompose.ui.theme.RomanCalculatorComposeTheme

@Composable
fun Calculator(
    result: Int?,
    input: Roman,
    operation: Operation,
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
                operation = operation,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
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

            Surface(
                color = MaterialTheme.colors.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f),
            ) {
                Column {
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
    }
}

@Preview
@Composable
fun CalculatorPreview(viewModel: CalculatorViewModel = CalculatorViewModel()) {
    RomanCalculatorComposeTheme {
        Calculator(
            result = viewModel.result,
            input = viewModel.input,
            operation = viewModel.operation,
            onSymbolPressed = { viewModel.enterSymbol(it) },
            onOperationPressed = { viewModel.enterOperation(it) },
            onDeletePressed = { viewModel.delete() },
            onClearPressed = { viewModel.clear() },
        )
    }
}

@Composable
private fun Display(
    result: Int?,
    input: Roman,
    operation: Operation,
    modifier: Modifier = Modifier
) {
    val (resultText, resultIsValid) =
        if (result == null) {
            Pair("", true)
        } else {
            try {
                Pair(result.toSignedRoman().toString(), true)
            } catch (e: Exception) {
                Pair("nope", false)
            }
        }
    val operationText = when {
        result == null -> ""
        operation == Operation.EQUALS -> ""
        else -> operation.toString()
    }
    val (inputText, inputIsValid) = Pair(input.toString(), input.value != null)
    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier,
    ) {
        Row {
            Text(
                resultText,
                color = if (resultIsValid) Color.Black else MaterialTheme.colors.secondaryVariant,
            )
            Text(operationText)
        }
        Text(
            inputText,
            color = if (inputIsValid) Color.Black else MaterialTheme.colors.secondaryVariant,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayResultPreview() {
    RomanCalculatorComposeTheme {
        Display(result = 4, input = Roman(), operation = Operation.PLUS)
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayNullResultPreview() {
    RomanCalculatorComposeTheme {
        Display(result = null, input = Roman(), operation = Operation.PLUS)
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayInvalidResultPreview() {
    RomanCalculatorComposeTheme {
        Display(4000, input = Roman(), operation = Operation.PLUS)
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayInputPreview() {
    RomanCalculatorComposeTheme {
        Display(result = 4, input = Roman(X), operation = Operation.PLUS)
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayInvalidInputPreview() {
    RomanCalculatorComposeTheme {
        Display(result = 4, input = Roman(V, V), operation = Operation.PLUS)
    }
}
