package com.example.advancedjetpackcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.advancedjetpackcompose.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.advancedjetpackcompose.LocalAccentColor
import com.example.advancedjetpackcompose.navigation.Screen

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit
) {

    val accentColor = LocalAccentColor.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F3F6))
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Compose Lab",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )

                Text(
                    text = "Interactive Learning",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }

            Image(
                painter = painterResource(id = R.drawable.compose_logo),
                contentDescription = "Jetpack Compose Logo",
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Chapter introduction
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = accentColor
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = "CHAPTER 15",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.compose_logo),
                    contentDescription = "Jetpack Compose Logo",
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Advanced Jetpack Compose",
                    fontSize = 28.sp,
                    lineHeight = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Explore advanced UI techniques.",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Explore Components",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = accentColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        ComponentCard(
            module = "MODULE 01",
            title = "CompositionLocal",
            description = "Shared data and theme",
            icon = Icons.Default.Palette,
            onClick = {
                onNavigate(Screen.CompositionLocal.route)
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        ComponentCard(
            module = "MODULE 02",
            title = "Side Effects",
            description = "State and lifecycle management",
            icon = Icons.Default.Refresh,
            onClick = {
                onNavigate(Screen.SideEffects.route)
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        ComponentCard(
            module = "MODULE 03",
            title = "Animation",
            description = "Motion and UI transitions",
            icon = Icons.Default.Animation,
            onClick = {
                onNavigate(Screen.Animation.route)
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Kotlin · Jetpack Compose · Chapter 15",
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            ),
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ComponentCard(
    module: String,
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {

    val accentColor = LocalAccentColor.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(54.dp),
                color = accentColor.copy(alpha = 0.12f),
                shape = RoundedCornerShape(12.dp)
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = accentColor,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = module,
                    fontSize = 10.sp,
                    color = accentColor,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open $title",
                tint = accentColor
            )
        }
    }
}
