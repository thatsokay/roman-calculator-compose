package com.example.romancalculatorcompose.calculator

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.romancalculatorcompose.roman.Roman
import com.example.romancalculatorcompose.roman.Symbol


class CalculatorViewModel : ViewModel() {
    var result by mutableStateOf<Int?>(null)
        private set
    var operation by mutableStateOf<Operation>(Operation.PLUS)
    private val inputSymbols = mutableStateListOf<Symbol>()
    val input by derivedStateOf { Roman(*inputSymbols.toTypedArray()) }

    fun enterSymbol(symbol: Symbol) {
        inputSymbols.add(symbol)
    }

    fun enterOperation(nextOperation: Operation) {
        if (inputSymbols.isEmpty()) {
            // Just change operation if empty input
            operation = nextOperation
            return
        }
        val inputValue = input.value
        inputSymbols.clear()
        if (inputValue == null) {
            return
        }
        val newResult = try {
            operation.apply(result ?: 0, inputValue)
        } catch (e: ArithmeticException) {
            // Catch divide by zero and set result to zero
            0
        }
        result = newResult
        operation = nextOperation
    }

    fun delete() {
        inputSymbols.removeLastOrNull()
    }

    fun clear() {
        result = null
        inputSymbols.clear()
        operation = Operation.PLUS
    }
}
