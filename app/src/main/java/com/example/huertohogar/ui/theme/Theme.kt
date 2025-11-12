package com.example.huertohogar.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
// Esquema de colores M3
private val LightColorScheme = lightColorScheme(
    primary = VibrantGreen,
    onPrimary = OnVibrantGreen,
    primaryContainer = VibrantGreenContainer,
    onPrimaryContainer = Color(0xFF00201D),

    secondary = CitrusYellow,
    onSecondary = OnCitrusYellow,
    secondaryContainer = CitrusYellowContainer,
    onSecondaryContainer = Color(0xFF271A00),

    tertiary = EarthyBrown,
    onTertiary = OnEarthyBrown,
    tertiaryContainer = EarthyBrownContainer,
    onTertiaryContainer = Color(0xFF1E1A19),

    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,

    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Outline,
)

// Definición de Tema
@Composable
fun HuertoHogarTheme(content: @Composable () -> Unit) {
    val colors = LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(
            // Tipografía M3 (ejemplo de reemplazo)
            headlineSmall = TextStyle(fontFamily = PlayfairDisplayFont, fontWeight = FontWeight.Bold, color = EarthyBrown, fontSize = 24.sp),
            titleLarge = TextStyle(fontFamily = PlayfairDisplayFont, fontWeight = FontWeight.SemiBold, color = EarthyBrown, fontSize = 20.sp),
            bodyLarge = TextStyle(fontFamily = MontserratFont, color = OnBackground, fontSize = 16.sp),
            bodyMedium = TextStyle(fontFamily = MontserratFont, color = OnSurfaceVariant, fontSize = 14.sp),
            labelLarge = TextStyle(fontFamily = MontserratFont, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        ),
        shapes = Shapes(
            small = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(12.dp),
            large = RoundedCornerShape(16.dp)
        ),
        content = content
    )
}