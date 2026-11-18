
package com.example.advancedjetpackcompose.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.advancedjetpackcompose.LocalAccentColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimationScreen(
    onBack: () -> Unit
) {

    val accentColor = LocalAccentColor.current

    Scaffold(
        containerColor = Color(0xFFF1F3F6),

        topBar = {

            TopAppBar(
                title = {
                    Text("Animation")
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
                text = "Animation Playground",
                fontSize = 26.sp,
                color = accentColor,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            HeartAnimationDemo()

            Spacer(modifier = Modifier.height(16.dp))

            VisibilityAnimationDemo()

            Spacer(modifier = Modifier.height(16.dp))

            ContentSizeAnimationDemo()

            Spacer(modifier = Modifier.height(16.dp))

            AnimationSpecDemo()
        }
    }
}


// 1. State Animation
@Composable
fun HeartAnimationDemo() {

    var isFavorite by remember {
        mutableStateOf(false)
    }

    val heartSize by animateDpAsState(
        targetValue = if (isFavorite) {
            85.dp
        } else {
            50.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = "HeartSize"
    )

    val heartColor by animateColorAsState(
        targetValue = if (isFavorite) {
            Color(0xFFEB5476)
        } else {
            Color.Gray
        },
        animationSpec = tween(400),
        label = "HeartColor"
    )

    AnimationCard(
        title = "01 — State Animation",
        description =
            "animateDpAsState · animateColorAsState"
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clickable {
                        isFavorite = !isFavorite
                    },
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Favorite,

                    contentDescription =
                        if (isFavorite) {
                            "Remove favorite"
                        } else {
                            "Add favorite"
                        },

                    tint = heartColor,

                    modifier = Modifier.size(heartSize)
                )
            }
        }

        Text(
            text = if (isFavorite) {
                "Liked"
            } else {
                "Tap the heart"
            },
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            ),
            color = heartColor
        )
    }
}


// 2. AnimatedVisibility
@Composable
fun VisibilityAnimationDemo() {

    val accentColor = LocalAccentColor.current

    var showContent by remember {
        mutableStateOf(true)
    }

    AnimationCard(
        title = "02 — AnimatedVisibility",
        description = "Enter and exit transitions"
    ) {

        Button(
            onClick = {
                showContent = !showContent
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                if (showContent) {
                    "Hide Content"
                } else {
                    "Show Content"
                }
            )
        }

        AnimatedVisibility(
            visible = showContent,

            enter = fadeIn(
                animationSpec = tween(500)
            ) + expandVertically(
                animationSpec = tween(500)
            ),

            exit = fadeOut(
                animationSpec = tween(500)
            ) + shrinkVertically(
                animationSpec = tween(500)
            )

        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .background(
                        accentColor.copy(alpha = 0.12f),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Hello Jetpack Compose!",
                    color = accentColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


// 3. animateContentSize
@Composable
fun ContentSizeAnimationDemo() {

    val accentColor = LocalAccentColor.current

    var isExpanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(500)
            ),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isExpanded = !isExpanded
                    },

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "03 — Expandable Card",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )

                    Text(
                        text = "animateContentSize",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }

                Icon(
                    imageVector = if (isExpanded) {
                        Icons.Default.KeyboardArrowUp
                    } else {
                        Icons.Default.KeyboardArrowDown
                    },

                    contentDescription =
                        if (isExpanded) {
                            "Collapse"
                        } else {
                            "Expand"
                        },

                    tint = accentColor
                )
            }

            if (isExpanded) {

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "animateContentSize automatically " +
                            "animates changes in the size of a " +
                            "Composable when its content changes.",

                    fontSize = 15.sp,
                    lineHeight = 23.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Useful for expandable cards, " +
                            "menus and dynamic layouts.",

                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


// 4. Tween and Spring
@Composable
fun AnimationSpecDemo() {

    val accentColor = LocalAccentColor.current

    var moveRight by remember {
        mutableStateOf(false)
    }

    val target = if (moveRight) {
        1f
    } else {
        0f
    }

    // Tween animation
    val tweenProgress by animateFloatAsState(
        targetValue = target,

        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        ),

        label = "TweenProgress"
    )

    // Spring animation
    val springProgress by animateFloatAsState(
        targetValue = target,

        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),

        label = "SpringProgress"
    )

    AnimationCard(
        title = "04 — Tween & Spring",
        description = "Compare animation specifications"
    ) {

        Text(
            text = "Tween — 1000 ms",
            color = accentColor,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        AnimationTrack(
            progress = tweenProgress,
            color = Color(0xFF52769E)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Spring — Bouncy",
            color = accentColor,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        AnimationTrack(
            progress = springProgress,
            color = accentColor
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                moveRight = !moveRight
            },

            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                if (moveRight) {
                    "Move Left"
                } else {
                    "Move Right"
                }
            )
        }
    }
}


// Animation Track
@Composable
fun AnimationTrack(
    progress: Float,
    color: Color
) {

    val ballSize = 30.dp

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .background(
                Color(0xFFE0E7F0),
                RoundedCornerShape(12.dp)
            )
    ) {

        val travelDistance = maxOf(
            maxWidth - ballSize - 40.dp,
            0.dp
        )

        val offsetX =
            20.dp + travelDistance * progress

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = offsetX)
                .size(ballSize)
                .background(color, CircleShape)
        )
    }
}


// Reusable Animation Card
@Composable
fun AnimationCard(
    title: String,
    description: String,
    content: @Composable ColumnScope.() -> Unit
) {

    val accentColor = LocalAccentColor.current

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )

            Text(
                text = description,
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            content()
        }
    }
}
