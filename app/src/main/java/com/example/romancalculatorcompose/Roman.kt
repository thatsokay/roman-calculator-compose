package com.example.romancalculatorcompose

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

data class Numeral(val symbols: List<Symbol> = emptyList(), val positive: Boolean = true) {
    override fun toString(): String {
        return (if (positive) "" else "-") + symbols.joinToString("")
    }
}
