
package com.example.advancedjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.advancedjetpackcompose.navigation.AppNavigation

// CompositionLocal
val LocalAccentColor = compositionLocalOf {
    Color(0xFF23354D)
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AdvancedJetpackComposeApp()
        }
    }
}

@Composable
fun AdvancedJetpackComposeApp() {

    var selectedColor by remember {
        mutableStateOf(Color(0xFF23354D))
    }

    // Animate theme color
    val animatedColor by animateColorAsState(
        targetValue = selectedColor,
        animationSpec = tween(500),
        label = "ThemeAnimation"
    )

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = animatedColor,
            background = Color(0xFFF1F3F6),
            surface = Color.White,
            onPrimary = Color.White
        )
    ) {

        // Provide shared color
        CompositionLocalProvider(
            LocalAccentColor provides animatedColor
        ) {

            AppNavigation(
                selectedColor = selectedColor,
                onColorChange = { color ->
                    selectedColor = color
                }
            )
        }
    }
}
