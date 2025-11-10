package com.example.huertohogar.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Definición de Tema
@Composable
fun HuertoHogarTheme(content: @Composable () -> Unit) {
    val colors = lightColors(
        primary = EmeraldGreen,
        primaryVariant = EmeraldGreen.copy(alpha = 0.8f),
        secondary = MustardYellow,
        background = SoftWhite,
        surface = Color.White,
        onPrimary = Color.White,
        onSecondary = DarkGrey,
        onBackground = DarkGrey,
        onSurface = DarkGrey
    )

    MaterialTheme(
        colors = colors,
        typography = Typography(
            h4 = TextStyle(fontFamily = PlayfairDisplayFont, fontWeight = FontWeight.Bold, color = LightBrown, fontSize = 28.sp),
            h6 = TextStyle(fontFamily = PlayfairDisplayFont, fontWeight = FontWeight.SemiBold, color = LightBrown, fontSize = 18.sp),
            body1 = TextStyle(fontFamily = MontserratFont, color = DarkGrey, fontSize = 16.sp),
            button = TextStyle(fontFamily = MontserratFont, fontWeight = FontWeight.Bold)
        ),
        shapes = Shapes(
            small = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(12.dp),
            large = RoundedCornerShape(16.dp)
        ),
        content = content
    )
}
