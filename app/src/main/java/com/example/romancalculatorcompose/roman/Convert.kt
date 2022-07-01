package com.example.romancalculatorcompose.roman

import com.example.romancalculatorcompose.roman.Symbol.*
import kotlin.math.abs

fun romanToInt(roman: Roman): Int? {
    if (roman.isEmpty()) {
        return null
    }
    if (roman.size == 1 && roman[0] == N) {
        return 0
    }
    var result = 0
    for (window in roman.windowed(size = 4, step = 1, partialWindows = true)) {
        when (window[0]) {
            N -> {
                return null
            }
            I, X, C, M -> {
                if (window.size > 1) {
                    val nextSymbolFactor = window[1].value / window[0].value
                    // Cannot be followed by symbols larger than 10 times its value (ie. IL)
                    if (nextSymbolFactor > 10) {
                        return null
                    }
                    // Subtraction
                    if (nextSymbolFactor > 1) {
                        // Symbol after subtraction must be smaller than subtracting value (eg. IVI)
                        if (window.size > 2 && window[2].value >= window[0].value) {
                            return null
                        }
                        result -= window[0].value
                        continue
                    }
                }
                // Cannot be followed by a subtracting symbol of the same value (eg. IIV)
                if (window.size > 2 && window[2].value > window[0].value) {
                    return null
                }
                // Cannot have 4 consecutive 1s of the same value (eg, IIII)
                if (window.size > 3 && window[0] == window[3]) {
                    return null
                }
                result += window[0].value
            }
            V, L, D -> {
                // Must be followed by smaller symbol (eg. VV)
                if (window.size > 1 && window[1].value >= window[0].value) {
                    return null
                }
                // Cannot be followed by subtracted symbol of the same value (eg. VIV)
                if (window.size > 2 && window[2].value >= window[0].value) {
                    return null
                }
                result += window[0].value
            }
        }
    }
    return result
}

fun signedRomanToInt(roman: SignedRoman): Int? {
    val result = romanToInt(roman)
    return when {
        result == null -> null
        roman.negative -> -result
        else -> result
    }
}

data class PlaceSymbols(val one: Symbol, val five: Symbol?, val ten: Symbol?)

fun intToRoman(number: Int): SignedRoman? {
    val absNumber = abs(number)
    if (absNumber >= 4000) {
        return null
    }
    if (number == 0) {
        return SignedRoman(N)
    }
    val digits = listOf(4000, 1000, 100, 10, 1)
        .windowed(2)
        .map { window -> (absNumber % window[0]) / window[1] }
    val placesSymbols = listOf(
        PlaceSymbols(M, null, null),
        PlaceSymbols(C, D, M),
        PlaceSymbols(X, L, C),
        PlaceSymbols(I, V, X)
    )
    val resultSymbols = mutableListOf<Symbol>()
    for ((digit, placeSymbols) in digits.zip(placesSymbols)) {
        val (one, five, ten) = placeSymbols
        when {
            digit == 9 -> {
                if (ten == null) {
                    throw Exception("Unreachable")
                }
                resultSymbols.addAll(listOf(one, ten))
            }
            digit == 4 -> {
                if (five == null) {
                    throw Exception("Unreachable")
                }
                resultSymbols.addAll(listOf(one, five))
            }
            digit >= 5 -> {
                if (five == null) {
                    throw Exception("Unreachable")
                }
                resultSymbols.add(five)
                resultSymbols.addAll(List(digit - 5) { one })
            }
            digit >= 1 -> {
                resultSymbols.addAll(List(digit) { one })
            }
        }
    }
    return SignedRoman(*resultSymbols.toTypedArray(), negative = number < 0)
}
