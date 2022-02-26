package com.example.romancalculatorcompose

import com.example.romancalculatorcompose.Symbol.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class RomanToIntTest(private val roman: Numeral, private val expected: Int?) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any?>> = listOf(
            arrayOf(Numeral(listOf(N)), 0), // Simple numbers
            arrayOf(Numeral(listOf(I)), 1),
            arrayOf(Numeral(listOf(V)), 5),
            arrayOf(Numeral(listOf(X)), 10),
            arrayOf(Numeral(listOf(L)), 50),
            arrayOf(Numeral(listOf(C)), 100),
            arrayOf(Numeral(listOf(D)), 500),
            arrayOf(Numeral(listOf(M)), 1000),
            arrayOf(Numeral(listOf(V, I, I)), 7), // Compound numbers
            arrayOf(Numeral(listOf(C, X, I)), 111),
            arrayOf(Numeral(listOf(I, V)), 4), // Subtractions
            arrayOf(Numeral(listOf(I, X)), 9),
            arrayOf(Numeral(listOf(X, C)), 90),
            arrayOf(Numeral(listOf(X, I, X)), 19),
            arrayOf(Numeral(listOf(X, C, I)), 91),
            arrayOf(Numeral(listOf<Symbol>()), null),
            arrayOf(Numeral(listOf(I, C)), null), // Invalid ordering
            arrayOf(Numeral(listOf(V, X)), null),
            arrayOf(Numeral(listOf(I, I, V)), null),
            arrayOf(Numeral(listOf(V, V)), null),
            arrayOf(Numeral(listOf(V, I, V)), null),
            arrayOf(Numeral(listOf(I, V, I)), null),
            arrayOf(Numeral(listOf(I, I, I, I)), null),
        )
    }

    @Test
    fun convert() {
        assertEquals(expected, romanToInt(roman))
    }
}

@RunWith(Parameterized::class)
class IntToRomanTest(private val number: Int, private val expected: Numeral?) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any?>> = listOf(
            arrayOf(1, Numeral(listOf(I))),
            arrayOf(5, Numeral(listOf(V))),
            arrayOf(10, Numeral(listOf(X))),
            arrayOf(50, Numeral(listOf(L))),
            arrayOf(100, Numeral(listOf(C))),
            arrayOf(500, Numeral(listOf(D))),
            arrayOf(1000, Numeral(listOf(M))),
            arrayOf(7, Numeral(listOf(V, I, I))),
            arrayOf(101, Numeral(listOf(C, I))),
            arrayOf(4, Numeral(listOf(I, V))),
            arrayOf(9, Numeral(listOf(I, X))),
            arrayOf(0, Numeral(listOf(N))),
            arrayOf(-1, Numeral(listOf(I), positive = false)), // Negative numbers
            arrayOf(-4, Numeral(listOf(I, V), positive = false)),
            arrayOf(4000, null), // Big numbers
            arrayOf(9000, null),
            arrayOf(250000, null),
        )
    }

    @Test
    fun convert() {
        assertEquals(expected, intToRoman(number))
    }
}
