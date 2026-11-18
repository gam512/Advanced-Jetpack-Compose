
package com.example.advancedjetpackcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.advancedjetpackcompose.LocalAccentColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompositionLocalScreen(
    selectedColor: Color,
    onColorChange: (Color) -> Unit,
    onBack: () -> Unit
) {

    val accentColor = LocalAccentColor.current

    val colors = listOf(
        Color(0xFF23354D),
        Color(0xFF8063AC),
        Color(0xFFBF654A),
        Color(0xFF258B80)
    )

    Scaffold(
        containerColor = Color(0xFFF1F3F6),

        topBar = {

            TopAppBar(
                title = {
                    Text("CompositionLocal")
                },

                navigationIcon = {

                    IconButton(onClick = onBack) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = accentColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Text(
                text = "Shared Theme",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Share data across the composition tree.",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Choose your theme",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.SpaceEvenly
                    ) {

                        colors.forEach { color ->

                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .background(
                                        color,
                                        CircleShape
                                    )
                                    .clickable {
                                        onColorChange(color)
                                    },
                                contentAlignment =
                                    Alignment.Center
                            ) {

                                if (selectedColor == color) {

                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected color",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Preview Components",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            ThemePreviewButton()

            Spacer(modifier = Modifier.height(16.dp))

            ThemePreviewCard()

            Spacer(modifier = Modifier.height(16.dp))

            ThemePreviewIcons()

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor =
                        accentColor.copy(alpha = 0.10f)
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "How it works",
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "All preview components read " +
                                "the shared color from " +
                                "LocalAccentColor.current. " +
                                "No color parameter is required.",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

// Preview Button
@Composable
fun ThemePreviewButton() {

    val color = LocalAccentColor.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        color = color,
        shape = RoundedCornerShape(12.dp)
    ) {

        Box(
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "Primary Button Preview",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Preview Card
@Composable
fun ThemePreviewCard() {

    val color = LocalAccentColor.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                RoundedCornerShape(16.dp)
            )
            .border(
                1.dp,
                color,
                RoundedCornerShape(16.dp)
            )
            .padding(20.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = color
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "Sample Card",
                color = color,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Shared color from CompositionLocal",
            color = Color.Gray
        )
    }
}

// Preview Icons
@Composable
fun ThemePreviewIcons() {

    val color = LocalAccentColor.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement =
                Arrangement.SpaceEvenly
        ) {

            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite",
                tint = color,
                modifier = Modifier.size(32.dp)
            )

            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notification",
                tint = color,
                modifier = Modifier.size(32.dp)
            )

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = color,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
