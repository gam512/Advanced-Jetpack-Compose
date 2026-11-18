
## Thông tin sinh viên

| Thông tin | Nội dung |
| :--- | :--- |
| Họ và tên | [Nguyễn Thị Hồng Gấm] |
| MSSV | [079306039691] |
| Lớp | [012012103402] |
| Môn học | Lập trình ứng dụng di động |

# Compose Lab — Advanced Jetpack Compose

Ứng dụng Android minh họa các kỹ thuật nâng cao trong Jetpack Compose, được xây dựng bằng **Kotlin, Jetpack Compose và Material 3**.

Ứng dụng phát triển dựa trên Chapter 15: *Advanced Jetpack Compose* trong sách *How to Build Android Applications with Kotlin*. Các kiến thức trong chương được vận dụng để xây dựng ứng dụng học tập tương tác, giúp trực quan hóa cơ chế chia sẻ dữ liệu, quản lý Side Effects và tạo hiệu ứng chuyển động.

## Chức năng

- **CompositionLocal:** Chia sẻ màu chủ đề giữa Button, Card và Icon mà không cần truyền tham số qua từng cấp Composable.
- **LaunchedEffect:** Mô phỏng quá trình tải dữ liệu bất đồng bộ bằng Kotlin Coroutines.
- **DisposableEffect:** Minh họa quá trình khởi tạo và dọn dẹp khi Composable vào hoặc rời Composition.
- **SideEffect:** Ghi trạng thái bộ đếm vào Logcat.
- **State Animation:** Tạo hiệu ứng thay đổi kích thước và màu sắc dựa trên trạng thái.
- **AnimatedVisibility:** Tạo hiệu ứng xuất hiện và biến mất của nội dung.
- **animateContentSize:** Tạo hiệu ứng mở rộng và thu gọn Card.
- **Tween và Spring:** So sánh trực quan hai cơ chế chuyển động.
- Điều hướng giữa các màn hình bằng Navigation Compose.

## Mở và chạy

1. Clone repository:

   ```bash
   git clone https://github.com/gam512/Advanced-Jetpack-Compose.git
   ```

2. Mở thư mục **src** trong Android Studio.
3. Cài đặt Android SDK theo cấu hình Gradle hiện có và đồng bộ project.
4. Chọn module `app` và chạy trên thiết bị hoặc máy ảo Android API 24 trở lên.

Project gồm một module `:app`, package `com.example.advancedjetpackcompose`.

## Kiểm tra và build

Chạy tại thư mục chứa `gradlew.bat`:

```powershell
.\gradlew.bat :app:assembleDebug --console=plain
```

Để quan sát hoạt động của Side Effects, mở Logcat trong Android Studio và sử dụng bộ lọc:

```text
tag:ComposeDemo
```

## Tài liệu tham khảo

- *How to Build Android Applications with Kotlin* — Chapter 15: Advanced Jetpack Compose.
