
# Chapter 15 – Advanced Jetpack Compose

## Compose Lab – Interactive Learning

Đề tài nghiên cứu và ứng dụng các kỹ thuật nâng cao của Jetpack Compose trong phát triển giao diện Android, dựa trên Chapter 15 – Advanced Jetpack Compose thuộc sách *How to Build Android Applications with Kotlin*.

Ứng dụng Compose Lab được xây dựng nhằm minh họa ba nội dung chính: CompositionLocal, Side Effects và Animation thông qua các chức năng tương tác trực quan.

## Thông tin sinh viên

- **Họ và tên:**
- **MSSV:**
- **Lớp:**
- **Môn học:** Lập trình ứng dụng di động
- **Đề tài:** Advanced Jetpack Compose

## Cấu trúc Repository

```text
Advanced-Jetpack-Compose/
├── src/
│   ├── app/
│   │   ├── src/
│   │   │   └── main/
│   │   │       ├── java/
│   │   │       │   └── com/example/advancedjetpackcompose/
│   │   │       │       ├── MainActivity.kt
│   │   │       │       ├── AppNavigation.kt
│   │   │       │       ├── HomeScreen.kt
│   │   │       │       ├── CompositionLocalScreen.kt
│   │   │       │       ├── SideEffectsScreen.kt
│   │   │       │       └── AnimationScreen.kt
│   │   │       ├── res/
│   │   │       └── AndroidManifest.xml
│   │   └── build.gradle.kts
│   ├── gradle/
│   ├── build.gradle.kts
│   └── settings.gradle.kts
└── README.md
```

## Công nghệ sử dụng

- Android Studio
- Kotlin
- Jetpack Compose
- Material Design 3
- Navigation Compose
- Kotlin Coroutines
- Gradle Kotlin DSL
- Minimum SDK: API 24 (Android 7.0)

## Chức năng ứng dụng

Ứng dụng gồm bốn màn hình chính:

| Màn hình | Chức năng |
| --- | --- |
| Home | Giới thiệu nội dung Chapter 15 và điều hướng đến các module |
| CompositionLocal | Minh họa cơ chế chia sẻ màu chủ đề trong cây Composition |
| Side Effects | Minh họa tác vụ bất đồng bộ, vòng đời Composable và ghi log |
| Animation | Minh họa hiệu ứng chuyển động dựa trên trạng thái |

### Module 1 – CompositionLocal

- Lựa chọn màu chủ đề.
- Thay đổi màu sắc của Button, Card và Icon.
- Chia sẻ dữ liệu mà không cần truyền tham số màu qua từng cấp Composable.
- Tạo hiệu ứng chuyển đổi màu sắc giữa các chủ đề.

### Module 2 – Side Effects

- Mô phỏng tải dữ liệu bằng LaunchedEffect.
- Hiển thị và loại bỏ Composable bằng DisposableEffect.
- Thay đổi bộ đếm và ghi Logcat bằng SideEffect.
- Hiển thị nhật ký sự kiện trong Effect Log.

### Module 3 – Animation

- Thay đổi kích thước và màu sắc của biểu tượng.
- Hiển thị và ẩn nội dung với AnimatedVisibility.
- Mở rộng và thu gọn Card với animateContentSize.
- So sánh chuyển động Tween và Spring.

## Hướng dẫn chạy ứng dụng

1. Clone repository:

   ```bash
   git clone https://github.com/gam512/Advanced-Jetpack-Compose.git
   ```

2. Mở Android Studio.
3. Chọn Open và mở thư mục `src`.
4. Chờ Gradle Sync hoàn tất.
5. Khởi động Android Emulator hoặc kết nối thiết bị Android.
6. Chọn Run để chạy ứng dụng.

---

## 1. Cơ sở lý thuyết

### 1.1. Jetpack Compose

Jetpack Compose là bộ công cụ xây dựng giao diện Android theo phương pháp lập trình khai báo (Declarative UI).

Thay vì cập nhật trực tiếp từng thành phần giao diện, lập trình viên mô tả giao diện tương ứng với trạng thái hiện tại của ứng dụng.

Khi trạng thái thay đổi, Compose thực hiện Recomposition đối với những phạm vi cần cập nhật.

```text
User Interaction
       ↓
  State Change
       ↓
 Recomposition
       ↓
  Updated UI
```

Các thành phần giao diện được xây dựng bằng các hàm có annotation `@Composable`.

### 1.2. CompositionLocal

CompositionLocal cung cấp cơ chế chia sẻ dữ liệu trong cây Composition mà không cần truyền dữ liệu qua tham số của từng Composable trung gian.

Ba thành phần chính:

| Thành phần | Vai trò |
| --- | --- |
| compositionLocalOf | Khai báo dữ liệu dùng chung và giá trị mặc định |
| CompositionLocalProvider | Cung cấp giá trị cho các Composable bên trong phạm vi |
| .current | Truy xuất giá trị hiện tại |

Cơ chế hoạt động trong ứng dụng:

```text
CompositionLocalProvider
        │
 LocalAccentColor
        │
   AppNavigation
        │
CompositionLocalScreen
        │
        ├── ThemePreviewButton
        ├── ThemePreviewCard
        └── ThemePreviewIcons
```

Các Composable con có thể truy xuất màu chủ đề thông qua `LocalAccentColor.current`.

Khi giá trị được cung cấp thay đổi, những Composable đọc giá trị đó có thể được cập nhật thông qua Recomposition.

### 1.3. Side Effects

Side Effects là các tác vụ xảy ra bên ngoài việc mô tả giao diện của Composable, chẳng hạn như tải dữ liệu, ghi log hoặc quản lý tài nguyên.

Do Composable có thể được thực thi lại nhiều lần, các tác vụ cần được quản lý bằng Effect API phù hợp thay vì thực hiện tùy ý trong thân hàm Composable.

| API | Thời điểm hoạt động | Ứng dụng |
| --- | --- | --- |
| LaunchedEffect | Khi vào Composition hoặc key thay đổi | Thực hiện coroutine, tải dữ liệu |
| DisposableEffect | Khi vào Composition; dọn dẹp khi rời Composition hoặc key thay đổi | Quản lý tài nguyên và listener |
| SideEffect | Sau khi Composition hoặc Recomposition thành công của phạm vi chứa nó | Đồng bộ trạng thái với hệ thống bên ngoài |

### 1.4. State-driven Animation

State-driven Animation là cơ chế tạo chuyển động dựa trên sự thay đổi trạng thái.

Thay vì cập nhật giá trị ngay lập tức, Compose tự động chuyển đổi từ giá trị hiện tại đến giá trị mục tiêu thông qua các giá trị trung gian.

```text
Initial State
      ↓
 State Change
      ↓
 Animation
      ↓
 Target State
```

Các Animation API được sử dụng trong ứng dụng:

| API | Chức năng |
| --- | --- |
| animateDpAsState | Thay đổi kích thước |
| animateColorAsState | Thay đổi màu sắc |
| animateFloatAsState | Thay đổi giá trị số thực |
| AnimatedVisibility | Hiệu ứng xuất hiện và biến mất |
| animateContentSize | Hiệu ứng thay đổi kích thước nội dung |

### 1.5. Tween và Spring

Tween và Spring là hai cơ chế cấu hình chuyển động trong Jetpack Compose.

| Đặc điểm | Tween | Spring |
| --- | --- | --- |
| Nguyên lý | Nội suy theo thời gian | Mô phỏng chuyển động lò xo |
| Thời lượng | Được cấu hình trước | Phụ thuộc thông số vật lý |
| Thông số | durationMillis, easing | dampingRatio, stiffness |
| Độ nảy | Không có cơ chế lò xo tự nhiên | Phụ thuộc dampingRatio |
| Ứng dụng | Chuyển động có thời gian xác định | Chuyển động tự nhiên |

Trong ứng dụng, Tween sử dụng LinearEasing để tạo chuyển động tuyến tính, còn Spring sử dụng cấu hình có độ nảy để minh họa sự khác biệt.

---

## 2. Giải thích mã nguồn

Các đoạn code dưới đây được trích theo từng chức năng. Những thành phần giao diện không liên quan trực tiếp đến nội dung giải thích được trình bày trong mã nguồn đầy đủ.

### 2.1. MainActivity – Điểm khởi tạo ứng dụng

File: `MainActivity.kt`

```kotlin
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AdvancedJetpackComposeApp()
        }
    }
}
```

**Chức năng:**

- `ComponentActivity`: Activity chứa giao diện Compose.
- `onCreate()`: Được gọi khi Activity được tạo.
- `setContent`: Khởi tạo nội dung giao diện bằng Jetpack Compose.
- `AdvancedJetpackComposeApp()`: Composable gốc chứa theme và hệ thống điều hướng của ứng dụng.

Ứng dụng sử dụng kiến trúc một Activity kết hợp với nhiều màn hình Composable.

### 2.2. Quản lý trạng thái màu chủ đề

File: `MainActivity.kt`

```kotlin
var selectedColor by remember {
    mutableStateOf(Color(0xFF23354D))
}
```

- `selectedColor`: Lưu màu chủ đề được người dùng lựa chọn.
- `mutableStateOf`: Tạo trạng thái có thể thay đổi và được Compose quan sát.
- `remember`: Giữ giá trị trong các lần Recomposition.
- `Color(0xFF23354D)`: Màu chủ đề mặc định.

Hiệu ứng chuyển đổi màu:

```kotlin
val animatedColor by animateColorAsState(
    targetValue = selectedColor,
    animationSpec = tween(500),
    label = "ThemeAnimation"
)
```

- `targetValue`: Màu mục tiêu.
- `tween(500)`: Thời gian chuyển đổi màu là 500 milliseconds.
- `label`: Tên dùng để nhận diện Animation.
- `animatedColor`: Giá trị màu tại thời điểm hiện tại của quá trình chuyển động.

Khi người dùng chọn màu mới, `selectedColor` thay đổi. Compose tự động cập nhật `animatedColor` theo các giá trị trung gian để tạo hiệu ứng chuyển màu.

### 2.3. AppNavigation – Điều hướng giữa các màn hình

File: `AppNavigation.kt`

```kotlin
sealed class Screen(val route: String) {

    object Home : Screen("home")

    object CompositionLocal :
        Screen("composition_local")

    object SideEffects :
        Screen("side_effects")

    object Animation :
        Screen("animation")
}
```

**Chức năng:**

- `sealed class`: Tổ chức các màn hình thành một tập hợp kiểu xác định.
- `route`: Định danh đường dẫn điều hướng.
- Mỗi object tương ứng với một màn hình trong ứng dụng.

Khởi tạo bộ điều khiển điều hướng:

```kotlin
val navController = rememberNavController()
```

`rememberNavController()` tạo NavController để quản lý điều hướng và ngăn xếp màn hình.

Khai báo các màn hình:

```kotlin
NavHost(
    navController = navController,
    startDestination = Screen.Home.route
) {

    composable(Screen.Home.route) {

        HomeScreen(
            onNavigate = { route ->

                navController.navigate(route) {
                    launchSingleTop = true
                }
            }
        )
    }

    composable(Screen.CompositionLocal.route) {

        CompositionLocalScreen(
            selectedColor = selectedColor,
            onColorChange = onColorChange,
            onBack = {
                navController.popBackStack()
            }
        )
    }

    composable(Screen.SideEffects.route) {

        SideEffectsScreen(
            onBack = {
                navController.popBackStack()
            }
        )
    }

    composable(Screen.Animation.route) {

        AnimationScreen(
            onBack = {
                navController.popBackStack()
            }
        )
    }
}
```

- `NavHost`: Chứa các màn hình điều hướng.
- `startDestination`: Xác định Home là màn hình bắt đầu.
- `composable`: Khai báo nội dung tương ứng với từng route.
- `navigate(route)`: Điều hướng đến màn hình được lựa chọn.
- `launchSingleTop`: Tránh tạo thêm một bản sao của màn hình đích nếu màn hình đó đang ở đầu back stack.
- `popBackStack()`: Quay lại màn hình trước.

Luồng điều hướng:

```text
HomeScreen
    │
    ├── CompositionLocalScreen
    ├── SideEffectsScreen
    └── AnimationScreen
```

### 2.4. HomeScreen – Màn hình chính

File: `HomeScreen.kt`

HomeScreen giới thiệu ứng dụng Compose Lab và cung cấp các thành phần điều hướng đến ba module chính.

Mỗi module có một Card gồm:

- Tên module.
- Nội dung giới thiệu.
- Biểu tượng minh họa.
- Thao tác điều hướng.

HomeScreen sử dụng callback `onNavigate` để gửi route được lựa chọn đến AppNavigation.

Cách tổ chức này giúp tách giao diện khỏi logic điều hướng, đồng thời hỗ trợ tái sử dụng các thành phần giao diện.

### 2.5. CompositionLocal – Khai báo, cung cấp và truy xuất

#### Bước 1: Khai báo CompositionLocal

File: `MainActivity.kt`

```kotlin
val LocalAccentColor = compositionLocalOf {
    Color(0xFF23354D)
}
```

- `LocalAccentColor`: Định danh dữ liệu màu dùng chung.
- `compositionLocalOf`: Khởi tạo CompositionLocal.
- Giá trị mặc định là màu Navy.

#### Bước 2: Cung cấp dữ liệu

File: `MainActivity.kt`

```kotlin
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
```

- `CompositionLocalProvider`: Xác định phạm vi cung cấp dữ liệu.
- `provides`: Liên kết LocalAccentColor với giá trị animatedColor.
- `AppNavigation`: Nằm bên trong phạm vi được cung cấp dữ liệu.
- `onColorChange`: Callback dùng để cập nhật màu chủ đề khi người dùng lựa chọn màu mới.

#### Bước 3: Truy xuất dữ liệu

File: `CompositionLocalScreen.kt`

```kotlin
val color = LocalAccentColor.current
```

Dòng code này được sử dụng trong các hàm:

- `ThemePreviewButton()`
- `ThemePreviewCard()`
- `ThemePreviewIcons()`

Các Composable truy xuất trực tiếp màu chủ đề từ CompositionLocal mà không cần nhận tham số màu riêng.

#### Nguyên lý hoạt động

```text
Chọn màu chủ đề
       ↓
selectedColor thay đổi
       ↓
animateColorAsState
       ↓
animatedColor thay đổi
       ↓
CompositionLocalProvider
       ↓
Button / Card / Icon
```

**Kết quả:** Các thành phần giao diện sử dụng chung màu chủ đề và được cập nhật khi người dùng lựa chọn màu mới.

**Ưu điểm:**

- Giảm việc truyền tham số qua nhiều cấp Composable.
- Tăng khả năng tái sử dụng giao diện.
- Phù hợp với dữ liệu dùng chung như màu sắc và theme.

### 2.6. LaunchedEffect – Tác vụ bất đồng bộ

File: `SideEffectsScreen.kt`

Khai báo trạng thái:

```kotlin
var loadKey by remember {
    mutableIntStateOf(0)
}

var isLoading by remember {
    mutableStateOf(false)
}

var loadMessage by remember {
    mutableStateOf("Press Load Data to start")
}
```

- `loadKey`: Key điều khiển việc thực thi lại LaunchedEffect.
- `isLoading`: Xác định trạng thái đang tải.
- `loadMessage`: Nội dung thông báo trạng thái tải dữ liệu.

Xử lý LaunchedEffect:

```kotlin
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
```

**Giải thích:**

- `LaunchedEffect(loadKey)`: Khởi chạy coroutine gắn với phạm vi Composition và key.
- `if (loadKey > 0)`: Không thực hiện tải dữ liệu ngay khi mở màn hình lần đầu.
- `isLoading = true`: Chuyển giao diện sang trạng thái đang tải.
- `delay(2000)`: Tạm dừng coroutine trong 2 giây mà không chặn UI thread.
- `isLoading = false`: Kết thúc trạng thái đang tải.
- `effectLogs.add`: Ghi nhật ký sự kiện hiển thị trên ứng dụng.
- `Log.d`: Ghi thông tin vào Logcat.

Khi người dùng nhấn nút Load Data:

```kotlin
Button(
    onClick = {
        loadKey++
    },
    enabled = !isLoading,
    modifier = Modifier.fillMaxWidth()
) {

    Text("Load Data")
}
```

Key thay đổi khiến LaunchedEffect được khởi chạy lại.

Nút được vô hiệu hóa trong lúc tải để tránh người dùng thực hiện nhiều yêu cầu liên tiếp.

**Luồng xử lý:**

```text
Load Data
    ↓
loadKey++
    ↓
LaunchedEffect
    ↓
Loading data...
    ↓
delay(2000)
    ↓
Data loaded successfully
```

Coroutine của LaunchedEffect được hủy khi Composable rời khỏi Composition hoặc khi key thay đổi.

Lưu ý: Chức năng này mô phỏng quá trình tải dữ liệu, chưa kết nối API thực tế.

### 2.7. DisposableEffect – Quản lý vòng đời Composable

File: `SideEffectsScreen.kt`

Khai báo trạng thái hiển thị:

```kotlin
var showComponent by remember {
    mutableStateOf(false)
}
```

Điều khiển việc hiển thị:

```kotlin
Switch(
    checked = showComponent,
    onCheckedChange = {
        showComponent = it
    }
)
```

Composable được thêm vào Composition khi showComponent có giá trị true:

```kotlin
if (showComponent) {

    LifecycleComponent(
        onLog = { message ->
            effectLogs.add(message)
        }
    )
}
```

Xử lý vòng đời trong LifecycleComponent:

```kotlin
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
```

**Giải thích:**

- `rememberUpdatedState(onLog)`: Cho phép effect sử dụng callback mới nhất mà không cần khởi động lại effect chỉ vì callback thay đổi.
- `DisposableEffect(Unit)`: Sử dụng key cố định, effect hoạt động theo thời gian tồn tại của Composable trong Composition.
- `onDispose`: Được thực hiện khi Composable bị loại khỏi Composition.
- `Log.d`: Ghi nhật ký vòng đời vào Logcat.

**Kết quả:**

| Thao tác | Kết quả |
| --- | --- |
| Bật Switch | Component được thêm vào Composition |
| Tắt Switch | Component rời khỏi Composition và onDispose được gọi |
| Rời màn hình khi component đang hiển thị | DisposableEffect được dọn dẹp |

Trong ứng dụng, DisposableEffect được minh họa bằng việc ghi log. Trong thực tế, API này có thể dùng để đăng ký và hủy đăng ký listener hoặc giải phóng tài nguyên.

### 2.8. SideEffect – Đồng bộ trạng thái

File: `SideEffectsScreen.kt`

Khai báo bộ đếm:

```kotlin
var counter by remember {
    mutableIntStateOf(0)
}
```

SideEffect:

```kotlin
SideEffect {

    Log.d(
        "ComposeDemo",
        "SideEffect: Counter = $counter"
    )
}
```

**Giải thích:**

- `counter`: Trạng thái bộ đếm.
- `SideEffect`: Thực hiện tác vụ sau khi Composition hoặc Recomposition của phạm vi chứa nó hoàn thành thành công.
- `Log.d`: Ghi giá trị counter vào Logcat.
- `ComposeDemo`: Tag dùng để lọc nhật ký.

Thao tác tăng bộ đếm:

```kotlin
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
```

Thao tác đặt lại bộ đếm:

```kotlin
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
```

Khi counter thay đổi, những phạm vi Composable đọc trạng thái này có thể được Recomposition.

SideEffect được sử dụng để minh họa việc đồng bộ giá trị trạng thái của Compose với hệ thống bên ngoài thông qua Logcat.

Các dòng State changed và State reset trong Effect Log được ghi từ callback của Button, không phải trực tiếp từ SideEffect.

Để quan sát kết quả, mở Logcat trong Android Studio và sử dụng bộ lọc:

```text
tag:ComposeDemo
```

### 2.9. State Animation – Thay đổi kích thước và màu sắc

File: `AnimationScreen.kt`

Hàm: `HeartAnimationDemo()`

Khai báo trạng thái:

```kotlin
var isFavorite by remember {
    mutableStateOf(false)
}
```

Hiệu ứng thay đổi kích thước:

```kotlin
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
```

Hiệu ứng thay đổi màu:

```kotlin
val heartColor by animateColorAsState(
    targetValue = if (isFavorite) {
        Color(0xFFEB5476)
    } else {
        Color.Gray
    },
    animationSpec = tween(400),
    label = "HeartColor"
)
```

**Giải thích:**

- `isFavorite`: Trạng thái lựa chọn biểu tượng.
- `animateDpAsState`: Tạo chuyển động giữa các giá trị kích thước.
- `animateColorAsState`: Tạo chuyển động giữa các giá trị màu sắc.
- `targetValue`: Giá trị đích dựa trên trạng thái.
- `Spring.DampingRatioMediumBouncy`: Cấu hình độ nảy trung bình.
- `tween(400)`: Chuyển đổi màu trong 400 milliseconds.

| Trạng thái | Kích thước mục tiêu | Màu sắc mục tiêu |
| --- | --- | --- |
| false | 50.dp | Gray |
| true | 85.dp | Pink |

Khi người dùng nhấn biểu tượng, isFavorite thay đổi và Compose tự động tạo hiệu ứng chuyển đổi kích thước và màu sắc.

### 2.10. AnimatedVisibility – Hiệu ứng xuất hiện và biến mất

File: `AnimationScreen.kt`

Hàm: `VisibilityAnimationDemo()`

Khai báo trạng thái:

```kotlin
var showContent by remember {
    mutableStateOf(true)
}
```

Nút điều khiển thay đổi giá trị showContent:

```kotlin
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
```

Cấu hình chuyển động xuất hiện và biến mất:

```kotlin
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
```

**Giải thích:**

- `visible`: Điều khiển trạng thái hiển thị nội dung.
- `enter`: Cấu hình hiệu ứng xuất hiện.
- `exit`: Cấu hình hiệu ứng biến mất.
- `fadeIn`: Tăng độ hiển thị.
- `fadeOut`: Giảm độ hiển thị.
- `expandVertically`: Mở rộng nội dung theo chiều dọc.
- `shrinkVertically`: Thu gọn nội dung theo chiều dọc.
- Toán tử `+`: Kết hợp nhiều hiệu ứng chuyển động.
- `tween(500)`: Thời lượng 500 milliseconds.

**Kết quả:** Nội dung Hello Jetpack Compose! xuất hiện hoặc biến mất với hiệu ứng chuyển động thay vì thay đổi đột ngột.

### 2.11. animateContentSize – Thay đổi kích thước nội dung

File: `AnimationScreen.kt`

Hàm: `ContentSizeAnimationDemo()`

Khai báo trạng thái:

```kotlin
var isExpanded by remember {
    mutableStateOf(false)
}
```

Modifier được áp dụng cho Card:

```kotlin
modifier = Modifier
    .fillMaxWidth()
    .animateContentSize(
        animationSpec = tween(500)
    ),
```

Nội dung bổ sung của Card:

```kotlin
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
```

**Giải thích:**

- `isExpanded`: Xác định Card đang mở rộng hay thu gọn.
- Khi isExpanded thay đổi, nội dung bên trong Card thay đổi.
- `animateContentSize`: Tự động tạo Animation khi kích thước Composable thay đổi.
- `tween(500)`: Thời gian chuyển động là 500 milliseconds.

**Ứng dụng thực tế:**

- Expandable Card.
- Danh sách nội dung mở rộng.
- Menu động.
- Giao diện có kích thước thay đổi theo trạng thái.

### 2.12. So sánh Tween và Spring

File: `AnimationScreen.kt`

Hàm: `AnimationSpecDemo()`

Khai báo trạng thái và giá trị mục tiêu:

```kotlin
var moveRight by remember {
    mutableStateOf(false)
}

val target = if (moveRight) {
    1f
} else {
    0f
}
```

Hai giá trị 0f và 1f biểu diễn hai vị trí mục tiêu của chuyển động.

#### Tween Animation

Tween sử dụng:

- `animateFloatAsState`.
- `targetValue = target`.
- `durationMillis = 1000`.
- `easing = LinearEasing`.

Chuyển động được thực hiện trong 1000 milliseconds với tiến trình tuyến tính theo thời gian.

#### Spring Animation

Spring sử dụng cấu hình:

```kotlin
animationSpec = spring(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessLow
)
```

- `dampingRatio`: Điều khiển mức độ tắt dần và độ nảy của chuyển động.
- `DampingRatioMediumBouncy`: Tạo chuyển động có độ nảy trung bình.
- `stiffness`: Điều khiển độ cứng của lò xo.
- `StiffnessLow`: Sử dụng cấu hình độ cứng thấp.

#### Kết quả so sánh

| Đặc điểm | Tween | Spring |
| --- | --- | --- |
| Giá trị mục tiêu | 0f hoặc 1f | 0f hoặc 1f |
| Thời lượng | 1000 milliseconds | Không cố định |
| Cơ chế | Nội suy tuyến tính | Mô phỏng lò xo |
| Chuyển động | Đều theo thời gian | Có độ nảy và dao động tắt dần |
| Thông số | durationMillis, easing | dampingRatio, stiffness |

Hai chuyển động sử dụng cùng trạng thái và giá trị mục tiêu để người dùng có thể quan sát sự khác biệt trực tiếp trên ứng dụng.

---

## 3. Điểm khác so với sách

Chapter 15 trong sách giới thiệu ba nhóm kiến thức:

- Using CompositionLocal.
- Using side-effects with Jetpack Compose.
- Creating animations using Jetpack Compose.

Thay vì triển khai lại nguyên bản từng bài tập trong sách, dự án xây dựng Compose Lab thành một ứng dụng học tập tương tác tập trung vào ba nhóm kiến thức trên.

Các nội dung được triển khai trong dự án:

- Tích hợp ba nhóm kỹ thuật trong cùng một ứng dụng.
- Xây dựng màn hình Home và hệ thống Navigation.
- Thiết kế giao diện minh họa dành riêng cho từng module.
- Cho phép thay đổi màu chủ đề trực tiếp.
- Hiển thị nhật ký Side Effects để quan sát quá trình hoạt động.
- Mô phỏng quá trình tải dữ liệu bất đồng bộ.
- So sánh trực quan Tween và Spring.
- Tái sử dụng các thành phần giao diện.

Dự án tập trung vào việc vận dụng kiến thức của Chapter 15 để xây dựng ứng dụng Android có khả năng tương tác, thay vì chỉ trình bày các ví dụ lý thuyết độc lập.

---

## 4. Kết quả và hướng phát triển

### 4.1. Kết quả đạt được

- Xây dựng ứng dụng Compose Lab gồm bốn màn hình.
- Triển khai CompositionLocal để chia sẻ dữ liệu màu chủ đề.
- Sử dụng LaunchedEffect để mô phỏng tải dữ liệu.
- Minh họa vòng đời Composable bằng DisposableEffect.
- Sử dụng SideEffect để ghi trạng thái ra Logcat.
- Tạo hiệu ứng Animation dựa trên trạng thái.
- Minh họa sự khác biệt giữa Tween và Spring.
- Tổ chức giao diện theo các Composable có thể tái sử dụng.

### 4.2. Hạn chế

- Dữ liệu tải hiện tại chỉ là dữ liệu mô phỏng.
- Chưa tích hợp API và cơ sở dữ liệu.
- Chưa thực hiện kiểm thử giao diện chuyên sâu.
- Chưa đo lường hiệu năng Recomposition và Animation.
- Trạng thái sử dụng remember chưa được thiết kế để lưu bền vững khi Activity được tạo lại.
- Quy mô ứng dụng chủ yếu phục vụ mục đích học tập và minh họa.

### 4.3. Hướng phát triển

- Kết nối REST API để thay thế dữ liệu mô phỏng.
- Áp dụng ViewModel để quản lý trạng thái.
- Sử dụng rememberSaveable hoặc cơ chế lưu trạng thái phù hợp.
- Bổ sung Unit Test và Compose UI Test.
- Đo lường và tối ưu hiệu năng Recomposition.
- Mở rộng các ví dụ Animation.
- Bổ sung chức năng tùy chỉnh thông số Tween và Spring.
- Áp dụng kiến thức vào ứng dụng Android có quy mô lớn hơn.

---

## 5. Nguồn tham khảo
*How to Build Android Applications with Kotlin, Chapter 15.*
| Nội dung | Trang |
| --- | --- |
| Using CompositionLocal | 560 |
| Using side-effects with Jetpack Compose | 563 |
| LaunchedEffect | 564 |
| DisposableEffect | 564 |
| SideEffect | 565 |
| Creating animations using Jetpack Compose | 567 |
| Summary | 580 |

### 5.2. Tài liệu chính thức

- [Jetpack Compose](https://developer.android.com/develop/ui/compose)
- [State in Jetpack Compose](https://developer.android.com/develop/ui/compose/state)
- [Thinking in Compose](https://developer.android.com/develop/ui/compose/mental-model)
- [CompositionLocal](https://developer.android.com/develop/ui/compose/compositionlocal)
- [Side Effects in Compose](https://developer.android.com/develop/ui/compose/side-effects)
- [Animation in Compose](https://developer.android.com/develop/ui/compose/animation/introduction)
- [Customize Animations](https://developer.android.com/develop/ui/compose/animation/customize)
- [Navigation with Compose](https://developer.android.com/develop/ui/compose/navigation)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

---

Compose Lab – Advanced Jetpack Compose

Android Studio · Kotlin · Jetpack Compose
