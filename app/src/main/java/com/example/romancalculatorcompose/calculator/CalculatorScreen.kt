package com.example.romancalculatorcompose.calculator

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = viewModel()) {
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