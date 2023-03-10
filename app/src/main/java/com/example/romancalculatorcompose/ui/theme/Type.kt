package com.example.romancalculatorcompose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.romancalculatorcompose.R

val robotoSlab = FontFamily(
    Font(R.font.robotoslab_regular),
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = robotoSlab,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),
    button = TextStyle(
        fontFamily = robotoSlab,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),
)