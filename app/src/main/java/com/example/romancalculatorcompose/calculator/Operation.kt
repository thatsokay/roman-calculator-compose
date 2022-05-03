package com.example.romancalculatorcompose.calculator

import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator

enum class Operation : BinaryOperator<Int>, IntBinaryOperator {
    PLUS {
        override fun apply(t: Int, u: Int): Int = t + u
        override fun toString() = "+"
    },
    MINUS {
        override fun apply(t: Int, u: Int): Int = t - u
        override fun toString(): String = "−"
    },
    TIMES {
        override fun apply(t: Int, u: Int): Int = t * u
        override fun toString(): String = "×"
    },
    DIVIDE {
        override fun apply(t: Int, u: Int): Int = t / u
        override fun toString(): String = "÷"
    },
    EQUALS {
        override fun apply(t: Int, u: Int): Int = t
        override fun toString(): String = "="
    };

    override fun applyAsInt(t: Int, u: Int) = apply(t, u)
}