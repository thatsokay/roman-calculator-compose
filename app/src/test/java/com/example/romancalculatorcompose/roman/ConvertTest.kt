package com.example.romancalculatorcompose.roman

import com.example.romancalculatorcompose.roman.Symbol.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class RomanToIntTest(private val roman: Roman, private val expected: Int?) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any?>> = listOf(
            arrayOf(Roman(N), 0), // Simple numbers
            arrayOf(Roman(I), 1),
            arrayOf(Roman(V), 5),
            arrayOf(Roman(X), 10),
            arrayOf(Roman(L), 50),
            arrayOf(Roman(C), 100),
            arrayOf(Roman(D), 500),
            arrayOf(Roman(M), 1000),
            arrayOf(Roman(V, I, I), 7), // Compound numbers
            arrayOf(Roman(C, X, I), 111),
            arrayOf(Roman(I, V), 4), // Subtractions
            arrayOf(Roman(I, X), 9),
            arrayOf(Roman(X, C), 90),
            arrayOf(Roman(X, I, X), 19),
            arrayOf(Roman(X, C, I), 91),
            arrayOf(Roman(), null),
            arrayOf(Roman(I, C), null), // Invalid ordering
            arrayOf(Roman(V, X), null),
            arrayOf(Roman(I, I, V), null),
            arrayOf(Roman(V, V), null),
            arrayOf(Roman(V, I, V), null),
            arrayOf(Roman(I, V, I), null),
            arrayOf(Roman(I, I, I, I), null),
            arrayOf(Roman(I, N), null)
        )
    }

    @Test
    fun convert() {
        assertEquals(expected, romanToInt(roman))
    }
}

@RunWith(Parameterized::class)
class IntToRomanTest(private val number: Int, private val expected: SignedRoman?) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any?>> = listOf(
            arrayOf(1, SignedRoman(I)),
            arrayOf(5, SignedRoman(V)),
            arrayOf(10, SignedRoman(X)),
            arrayOf(50, SignedRoman(L)),
            arrayOf(100, SignedRoman(C)),
            arrayOf(500, SignedRoman(D)),
            arrayOf(1000, SignedRoman(M)),
            arrayOf(7, SignedRoman(V, I, I)),
            arrayOf(101, SignedRoman(C, I)),
            arrayOf(4, SignedRoman(I, V)),
            arrayOf(9, SignedRoman(I, X)),
            arrayOf(0, SignedRoman(N)),
            arrayOf(-1, SignedRoman(I, negative = true)), // Negative numbers
            arrayOf(-4, SignedRoman(I, V, negative = true)),
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
