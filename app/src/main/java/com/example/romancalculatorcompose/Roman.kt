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

class Numeral(vararg symbols: Symbol, val negative: Boolean = false) :
    List<Symbol> by listOf(*symbols) {

    override fun equals(other: Any?): Boolean {
        if (other !is Numeral) {
            return false
        }
        if (this.size != other.size) {
            return false
        }
        if (this.negative != other.negative) {
            return false
        }
        return this.zip(other).all { (a, b) -> a == b }
    }

    // TODO: Print `Numeral(N, negative = true)` as `"N"`
    override fun toString(): String = (if (this.negative) "-" else "") + this.joinToString("")
}
