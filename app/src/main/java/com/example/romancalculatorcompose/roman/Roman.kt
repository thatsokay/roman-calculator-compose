package com.example.romancalculatorcompose.roman

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
    open val value by lazy { romanToInt(this) }

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
}

class SignedRoman(vararg symbols: Symbol, val negative: Boolean = false) : Roman(*symbols) {
    override val value by lazy { signedRomanToInt(this) }

    override fun equals(other: Any?): Boolean {
        if (other !is SignedRoman) {
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
