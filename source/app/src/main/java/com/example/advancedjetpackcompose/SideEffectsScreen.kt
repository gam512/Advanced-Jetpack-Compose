
package com.example.advancedjetpackcompose.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.advancedjetpackcompose.LocalAccentColor
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideEffectsScreen(
    onBack: () -> Unit
) {

    val accentColor = LocalAccentColor.current

    var loadKey by remember {
        mutableIntStateOf(0)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var loadMessage by remember {
        mutableStateOf("Press Load Data to start")
    }

    var showComponent by remember {
        mutableStateOf(false)
    }

    var counter by remember {
        mutableIntStateOf(0)
    }

    val effectLogs = remember {
        mutableStateListOf<String>()
    }

    // 1. LaunchedEffect
    LaunchedEffect(loadKey) {

        if (loadKey > 0) {

            isLoading = true
            loadMessage = "Loading data..."

            effectLogs.add(
                "LaunchedEffect: started"
            )

            Log.d(
                "ComposeDemo",
                "LaunchedEffect started"
            )

            delay(2000)

            isLoading = false
            loadMessage = "Data loaded successfully"

            effectLogs.add(
                "LaunchedEffect: completed"
            )

            Log.d(
                "ComposeDemo",
                "LaunchedEffect completed"
            )
        }
    }

    // 2. SideEffect
    SideEffect {

        Log.d(
            "ComposeDemo",
            "SideEffect: Counter = $counter"
        )
    }

    Scaffold(
        containerColor = Color(0xFFF1F3F6),

        topBar = {

            TopAppBar(
                title = {
                    Text("Side Effects")
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
                text = "Effect Playground",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )

            Spacer(modifier = Modifier.height(20.dp))

            // LaunchedEffect demo
            EffectCard(
                title = "01 — LaunchedEffect",
                description = "Asynchronous Task"
            ) {

                if (isLoading) {

                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = accentColor
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }

                Text(
                    text = loadMessage,
                    color = accentColor,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        loadKey++
                    },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text("Load Data")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // DisposableEffect demo
            EffectCard(
                title = "02 — DisposableEffect",
                description = "Component Lifecycle"
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Show component",
                        modifier = Modifier.weight(1f)
                    )

                    Switch(
                        checked = showComponent,
                        onCheckedChange = {
                            showComponent = it
                        }
                    )
                }

                if (showComponent) {

                    LifecycleComponent(
                        onLog = { message ->
                            effectLogs.add(message)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SideEffect demo
            EffectCard(
                title = "03 — SideEffect",
                description = "Synchronize external state"
            ) {

                Text(
                    text = "Counter: $counter",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {

                    Button(
                        onClick = {

                            counter++

                            effectLogs.add(
                                "State changed: $counter"
                            )
                        }
                    ) {

                        Text("Increase")
                    }

                    OutlinedButton(
                        onClick = {

                            counter = 0

                            effectLogs.add(
                                "State reset: 0"
                            )
                        }
                    ) {

                        Text("Reset")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "View SideEffect output in Logcat.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Effect Log
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF23354D)
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Effect Log",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )

                        TextButton(
                            onClick = {
                                effectLogs.clear()
                            }
                        ) {

                            Text(
                                text = "Clear",
                                color = Color.White
                            )
                        }
                    }

                    if (effectLogs.isEmpty()) {

                        Text(
                            text = "No events yet.",
                            color = Color.LightGray
                        )

                    } else {

                        effectLogs.takeLast(8).forEach { message ->

                            Text(
                                text = message,
                                color = Color(0xFFD4E8FF),
                                fontSize = 13.sp,
                                modifier = Modifier.padding(
                                    vertical = 3.dp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

// Reusable Effect Card
@Composable
fun EffectCard(
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

// DisposableEffect
@Composable
fun LifecycleComponent(
    onLog: (String) -> Unit
) {

    val accentColor = LocalAccentColor.current

    val currentOnLog by rememberUpdatedState(onLog)

    DisposableEffect(Unit) {

        currentOnLog(
            "DisposableEffect: started"
        )

        Log.d(
            "ComposeDemo",
            "Component entered composition"
        )

        onDispose {

            currentOnLog(
                "DisposableEffect: disposed"
            )

            Log.d(
                "ComposeDemo",
                "Component left composition"
            )
        }
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = accentColor.copy(alpha = 0.12f)
    ) {

        Text(
            text = "Component is active",
            color = accentColor,
            modifier = Modifier.padding(16.dp)
        )
    }
}
