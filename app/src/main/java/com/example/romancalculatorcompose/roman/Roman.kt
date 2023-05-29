package com.example.romancalculatorcompose.roman

import kotlin.math.abs

enum class Symbol(val value: Int) {
    N(0),
    I(1),
    V(5),
    X(10),
    L(50),
    C(100),
    D(500),
    M(1000),
}

open class Roman(vararg symbols: Symbol) : List<Symbol> by listOf(*symbols) {
    val value by lazy {
        try {
            this.toInt()
        } catch (e: NumberFormatException) {
            null
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Roman) {
            return false
        }
        if (this.size != other.size) {
            return false
        }
        return this.zip(other).all { (a, b) -> a == b }
    }

    override fun toString(): String = this.joinToString("")

    open fun toInt(): Int {
        if (this.isEmpty()) {
            throw NumberFormatException()
        }
        if (this.size == 1 && this[0] == Symbol.N) {
            return 0
        }
        var result = 0
        for (window in this.windowed(size = 4, step = 1, partialWindows = true)) {
            when (window[0]) {
                Symbol.N -> {
                    throw NumberFormatException()
                }

                Symbol.I, Symbol.X, Symbol.C, Symbol.M -> {
                    if (window.size > 1) {
                        val nextSymbolFactor = window[1].value / window[0].value
                        // Cannot be followed by symbols larger than 10 times its value (ie. IL)
                        if (nextSymbolFactor > 10) {
                            throw NumberFormatException()
                        }
                        // Subtraction
                        if (nextSymbolFactor > 1) {
                            // Symbol after subtraction must be smaller than subtracting value (eg. IVI)
                            if (window.size > 2 && window[2].value >= window[0].value) {
                                throw NumberFormatException()
                            }
                            result -= window[0].value
                            continue
                        }
                    }
                    // Cannot be followed by a subtracting symbol of the same value (eg. IIV)
                    if (window.size > 2 && window[2].value > window[0].value) {
                        throw NumberFormatException()
                    }
                    // Cannot have 4 consecutive 1s of the same value (eg, IIII)
                    if (window.size > 3 && window[0] == window[3]) {
                        throw NumberFormatException()
                    }
                    result += window[0].value
                }

                Symbol.V, Symbol.L, Symbol.D -> {
                    // Must be followed by smaller symbol (eg. VV)
                    if (window.size > 1 && window[1].value >= window[0].value) {
                        throw NumberFormatException()
                    }
                    // Cannot be followed by subtracted symbol of the same value (eg. VIV)
                    if (window.size > 2 && window[2].value >= window[0].value) {
                        throw NumberFormatException()
                    }
                    result += window[0].value
                }
            }
        }
        return result
    }
}

class SignedRoman(vararg symbols: Symbol, val negative: Boolean = false) : Roman(*symbols) {
    override fun equals(other: Any?): Boolean {
        if (other !is SignedRoman) {
            return false
        }
        if (this.size != other.size) {
            return false
        }
        if (this.size == 1 && this[0] == Symbol.N && other[0] == Symbol.N) {
            return true
        }
        if (this.negative != other.negative) {
            return false
        }
        return this.zip(other).all { (a, b) -> a == b }
    }

    override fun toString(): String =
        (if (this.negative && this != SignedRoman(Symbol.N)) "-" else "") + this.joinToString("")

    override fun toInt(): Int {
        val result = super.toInt()
        return if (this.negative) -result else result
    }
}

private data class PlaceSymbols(val one: Symbol, val five: Symbol?, val ten: Symbol?)

private val placesSymbols = listOf(
    PlaceSymbols(Symbol.M, null, null),
    PlaceSymbols(Symbol.C, Symbol.D, Symbol.M),
    PlaceSymbols(Symbol.X, Symbol.L, Symbol.C),
    PlaceSymbols(Symbol.I, Symbol.V, Symbol.X)
)

fun Int.toSignedRoman(): SignedRoman {
    val absNumber = abs(this)
    if (absNumber >= 4000) {
        throw Exception("Magnitude is too large to represent in Roman numerals.")
    }
    if (this == 0) {
        return SignedRoman(Symbol.N)
    }
    val digits = listOf(4000, 1000, 100, 10, 1)
        .windowed(2)
        .map { window -> (absNumber % window[0]) / window[1] }
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
    return SignedRoman(*resultSymbols.toTypedArray(), negative = this < 0)
}
