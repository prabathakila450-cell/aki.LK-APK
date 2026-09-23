package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

enum class EyeCareThemeMode(val titleSinhala: String, val subtitle: String, val iconEmoji: String) {
  LIGHT("දිවා මාදිලිය", "Day Light • දීප්තිමත් සම්මත මාදිලිය", "☀️"),
  SEPIA("සේපියා / Warm Paper", "Eye-Care • මෘදු ඇස් ආරක්ෂක කඩදාසි මාදිලිය", "📜"),
  DARK("රාත්‍රී අඳුරු මාදිලිය", "Night Dark • අඳුරු පරිසරයට සුදුසුයි", "🌙"),
  BLUE_LIGHT_SHIELD("නිල් ආලෝක පෙරහන", "Blue Light Filter • තිර දීප්තිය අවම කරයි", "🛡️")
}

private val DarkColorScheme =
  darkColorScheme(
    primary = BluePrimaryDark,
    onPrimary = BlueOnPrimaryDark,
    primaryContainer = BluePrimaryContainerDark,
    onPrimaryContainer = BlueOnPrimaryContainerDark,
    background = BackgroundDark,
    onBackground = Color(0xFFF8FAFC),
    surface = SurfaceDark,
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = Color(0xFFE2E8F0),
    outline = Color(0xFF475569)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = BluePrimary,
    onPrimary = BlueOnPrimary,
    primaryContainer = BluePrimaryContainer,
    onPrimaryContainer = BlueOnPrimaryContainer,
    background = BackgroundLight,
    onBackground = NeutralDark,
    surface = SurfaceCardLight,
    onSurface = NeutralDark,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = NeutralMedium,
    outline = NeutralBorder,
    outlineVariant = NeutralBorderLight
  )

private val SepiaColorScheme =
  lightColorScheme(
    primary = SepiaPrimary,
    onPrimary = SepiaOnPrimary,
    primaryContainer = SepiaPrimaryContainer,
    onPrimaryContainer = SepiaTextDark,
    background = SepiaBackground,
    onBackground = SepiaTextDark,
    surface = SepiaSurface,
    onSurface = SepiaTextDark,
    surfaceVariant = SepiaSurfaceVariant,
    onSurfaceVariant = SepiaTextMedium,
    outline = SepiaBorder,
    outlineVariant = SepiaBorder
  )

private val BlueLightShieldColorScheme =
  darkColorScheme(
    primary = BlueLightShieldPrimary,
    onPrimary = BackgroundDark,
    primaryContainer = BlueLightShieldSurfaceVariant,
    onPrimaryContainer = BlueLightShieldText,
    background = BlueLightShieldBackground,
    onBackground = BlueLightShieldText,
    surface = BlueLightShieldSurface,
    onSurface = BlueLightShieldText,
    surfaceVariant = BlueLightShieldSurfaceVariant,
    onSurfaceVariant = NeutralMediumDark,
    outline = NeutralBorder
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = false,
  eyeCareMode: EyeCareThemeMode = if (darkTheme) EyeCareThemeMode.DARK else EyeCareThemeMode.LIGHT,
  // Disable dynamic color by default to preserve custom theme aesthetic
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      eyeCareMode == EyeCareThemeMode.SEPIA -> SepiaColorScheme
      eyeCareMode == EyeCareThemeMode.DARK || darkTheme -> DarkColorScheme
      eyeCareMode == EyeCareThemeMode.BLUE_LIGHT_SHIELD -> BlueLightShieldColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}


