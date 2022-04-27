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
            arrayOf(Numeral(N), 0), // Simple numbers
            arrayOf(Numeral(I), 1),
            arrayOf(Numeral(V), 5),
            arrayOf(Numeral(X), 10),
            arrayOf(Numeral(L), 50),
            arrayOf(Numeral(C), 100),
            arrayOf(Numeral(D), 500),
            arrayOf(Numeral(M), 1000),
            arrayOf(Numeral(V, I, I), 7), // Compound numbers
            arrayOf(Numeral(C, X, I), 111),
            arrayOf(Numeral(I, V), 4), // Subtractions
            arrayOf(Numeral(I, X), 9),
            arrayOf(Numeral(X, C), 90),
            arrayOf(Numeral(X, I, X), 19),
            arrayOf(Numeral(X, C, I), 91),
            arrayOf(Numeral(), null),
            arrayOf(Numeral(I, C), null), // Invalid ordering
            arrayOf(Numeral(V, X), null),
            arrayOf(Numeral(I, I, V), null),
            arrayOf(Numeral(V, V), null),
            arrayOf(Numeral(V, I, V), null),
            arrayOf(Numeral(I, V, I), null),
            arrayOf(Numeral(I, I, I, I), null),
            arrayOf(Numeral(I, N), null)
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
            arrayOf(1, Numeral(I)),
            arrayOf(5, Numeral(V)),
            arrayOf(10, Numeral(X)),
            arrayOf(50, Numeral(L)),
            arrayOf(100, Numeral(C)),
            arrayOf(500, Numeral(D)),
            arrayOf(1000, Numeral(M)),
            arrayOf(7, Numeral(V, I, I)),
            arrayOf(101, Numeral(C, I)),
            arrayOf(4, Numeral(I, V)),
            arrayOf(9, Numeral(I, X)),
            arrayOf(0, Numeral(N)),
            arrayOf(-1, Numeral(I, negative = true)), // Negative numbers
            arrayOf(-4, Numeral(I, V, negative = true)),
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
