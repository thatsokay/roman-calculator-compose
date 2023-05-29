package com.example.romancalculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.romancalculatorcompose.calculator.CalculatorScreen
import com.example.romancalculatorcompose.ui.theme.RomanCalculatorComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RomanCalculatorComposeTheme {
                CalculatorScreen()
            }
        }
    }
}
