package com.example.romancalculatorcompose.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.romancalculatorcompose.roman.Numeral
import com.example.romancalculatorcompose.roman.Symbol


class CalculatorViewModel : ViewModel() {
    var result by mutableStateOf<Int?>(null)
        private set
    private var lastOperation by mutableStateOf<Operation>(Operation.PLUS)
    private val inputSymbols = mutableStateListOf<Symbol>()

    val input: Numeral
        get() = Numeral(*inputSymbols.toTypedArray())

    fun enterSymbol(symbol: Symbol) {
        inputSymbols.add(symbol)
    }

    fun enterOperation(operation: Operation) {
        if (inputSymbols.isEmpty()) {
            // Just change operation if empty input
            lastOperation = operation
            return
        }
        val inputValue = input.value
        inputSymbols.clear()
        if (inputValue == null) {
            return
        }
        val newResult = try {
            lastOperation.apply(result ?: 0, inputValue)
        } catch (e: ArithmeticException) {
            // Catch divide by zero and set result to zero
            0
        }
        result = newResult
        lastOperation = operation
    }

    fun delete() {
        inputSymbols.removeLastOrNull()
    }

    fun clear() {
        result = null
        inputSymbols.clear()
        lastOperation = Operation.PLUS
    }
}
