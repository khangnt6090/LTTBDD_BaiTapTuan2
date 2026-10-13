// Khai báo tên gói (package) của ứng dụng, phải khớp với cấu trúc thư mục dự án của bạn
package com.example.baitapnangcao

// Import các thư viện lõi của Android để chạy ứng dụng
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

// Import các thành phần giao diện (UI) cơ bản của Jetpack Compose
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*

// Import các thư viện quản lý trạng thái (State) để giao diện tự cập nhật khi dữ liệu đổi
import androidx.compose.runtime.*

// Import các công cụ căn chỉnh, màu sắc, font chữ và xử lý số
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

// Màn hình chính của ứng dụng, kế thừa từ ComponentActivity
class MainActivity : ComponentActivity() {
    // Bỏ qua cảnh báo của Android Studio về các tính năng Material 3 đang thử nghiệm
    @OptIn(ExperimentalMaterial3Api::class)
    
    // Hàm onCreate chạy đầu tiên khi app vừa mở lên
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Cho phép app vẽ giao diện tràn ra sau thanh trạng thái (status bar) và thanh điều hướng
        enableEdgeToEdge()
        
        // Hàm setContent là nơi chứa toàn bộ code giao diện (thay thế cho file XML cũ)
        setContent {
            // Bao bọc toàn bộ app bằng MaterialTheme để dùng các thiết lập màu/font mặc định
            MaterialTheme {
                
                // Biến lưu vị trí Tab đang được chọn. 'remember' giúp giữ lại giá trị này mỗi khi giao diện vẽ lại.
                // Ban đầu gán = 0 (tức là tab đầu tiên).
                var selectedTab by remember { mutableIntStateOf(0) }
                
                // Danh sách tên các Tab hiển thị trên cùng
                val tabs = listOf("Máy tính", "Bài 1", "Bài 2", "Bài 3")

                // Scaffold là một bộ khung màn hình có sẵn chỗ để gắn thanh công cụ (topBar), thanh dưới (bottomBar)...
                Scaffold(
                    // Cho khung này chiếm toàn bộ màn hình
                    modifier = Modifier.fillMaxSize(),
                    
                    // Thiết lập thanh công cụ trên cùng
                    topBar = {
                        Column {
                            // Đẩy thanh Tab xuống một đoạn bằng đúng chiều cao của thanh trạng thái (chỗ hiện giờ/pin) để không bị lẹm chữ
                            Spacer(modifier = Modifier.statusBarsPadding())
                            
                            // Thanh chứa các Tab có thể cuộn ngang được
                            ScrollableTabRow(
                                selectedTabIndex = selectedTab, // Báo cho thanh này biết tab nào đang được chọn
                                edgePadding = 8.dp,             // Cách lề 2 bên 8dp
                                containerColor = Color(0xFF152852), // Đổ màu nền xanh tím than cho thanh Tab
                                contentColor = Color.White      // Màu chữ mặc định là trắng
                            ) {
                                // Vòng lặp duyệt qua danh sách 'tabs' để tạo ra từng nút Tab
                                tabs.forEachIndexed { index, title ->
                                    Tab(
                                        selected = selectedTab == index, // Nếu vị trí vòng lặp trùng với tab đang chọn thì đánh dấu là selected
                                        onClick = { selectedTab = index }, // Khi bấm vào tab, cập nhật biến selectedTab thành vị trí mới
                                        text = { Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold) } // Hiển thị tên Tab
                                    )
                                }
                            }
                        }
                    },
                    // Đổ màu xám nhạt cho phần nền phía dưới thanh Tab
                    containerColor = Color(0xFFF3F4F6)
                ) { innerPadding -> 
                    // innerPadding là phần khoảng trống mà Scaffold tính toán sẵn để nội dung không bị đè lên topBar
                    
                    // Box là một vùng chứa. padding(innerPadding) để đẩy nội dung xuống dưới thanh Tab
                    Box(modifier = Modifier.padding(innerPadding)) {
                        // Lệnh 'when' (giống switch-case) để kiểm tra xem đang chọn tab số mấy thì gọi hàm giao diện tương ứng
                        when (selectedTab) {
                            0 -> ThucHanh3_MayTinh() 
                            1 -> Bai1_VongLap()      
                            2 -> Bai2_IfElse()       
                            3 -> Bai3_TongHop()      
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// THỰC HÀNH 3: MÁY TÍNH CƠ BẢN
// ==========================================
// @Composable đánh dấu đây là một hàm vẽ giao diện
@Composable
fun ThucHanh3_MayTinh() {
    // 3 biến lưu trạng thái: Số thứ 1, Số thứ 2, và Kết quả. Khi 1 trong 3 thay đổi, màn hình tự update.
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    // Column xếp các thành phần từ trên xuống dưới
    Column(modifier = Modifier.fillMaxSize().padding(24.dp).background(Color.White)) {
        // align(Alignment.CenterHorizontally) đưa dòng chữ này ra giữa màn hình
        Text("Thực hành 03", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(24.dp)) // Tạo khoảng trống 24dp
        
        // Ô nhập liệu cho số thứ nhất
        OutlinedTextField(
            value = num1, // Hiển thị nội dung lấy từ biến num1
            onValueChange = { num1 = it }, // Khi người dùng gõ phím, lưu giá trị mới (it) vào biến num1
            modifier = Modifier.fillMaxWidth(), // Chiếm hết chiều ngang
            shape = RoundedCornerShape(12.dp), // Bo góc 12dp
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Bàn phím số
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Row xếp các nút bấm nằm ngang trên cùng một hàng
        // horizontalArrangement = Arrangement.SpaceBetween giúp chia đều khoảng cách giữa 4 nút
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            // Nút Cộng. Khi bấm, gọi hàm calc, truyền vào num1, num2 và dấu "+", kết quả gán vào biến result
            Button(onClick = { result = calc(num1, num2, "+") }, colors = ButtonDefaults.buttonColors(Color(0xFFEF4444)), modifier = Modifier.size(64.dp), shape = RoundedCornerShape(8.dp)) { Text("+", fontSize = 20.sp) }
            Button(onClick = { result = calc(num1, num2, "-") }, colors = ButtonDefaults.buttonColors(Color(0xFFF59E0B)), modifier = Modifier.size(64.dp), shape = RoundedCornerShape(8.dp)) { Text("-", fontSize = 20.sp) }
            Button(onClick = { result = calc(num1, num2, "*") }, colors = ButtonDefaults.buttonColors(Color(0xFF6366F1)), modifier = Modifier.size(64.dp), shape = RoundedCornerShape(8.dp)) { Text("*", fontSize = 20.sp) }
            Button(onClick = { result = calc(num1, num2, "/") }, colors = ButtonDefaults.buttonColors(Color(0xFF1F2937)), modifier = Modifier.size(64.dp), shape = RoundedCornerShape(8.dp)) { Text("/", fontSize = 20.sp) }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Ô nhập liệu cho số thứ hai (tương tự như ô 1)
        OutlinedTextField(value = num2, onValueChange = { num2 = it }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        Spacer(modifier = Modifier.height(16.dp))
        
        // In kết quả ra màn hình. Khi biến result đổi, dòng này tự đổi theo.
        Text(text = "Kết quả: $result", fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

// Hàm tính toán logic, nhận vào 2 chuỗi số và 1 phép toán, trả về chuỗi kết quả
fun calc(n1: String, n2: String, op: String): String {
    // Chuyển chuỗi thành số thực (Double). Nếu chuỗi trống hoặc chứa chữ thì trả về null
    val a = n1.toDoubleOrNull()
    val b = n2.toDoubleOrNull()
    
    // Nếu 1 trong 2 số là null (lỗi nhập liệu), trả về chuỗi rỗng
    if (a == null || b == null) return ""
    
    // Kiểm tra phép toán và tính
    return when (op) { 
        "+" -> (a + b).toString()
        "-" -> (a - b).toString()
        "*" -> (a * b).toString()
        "/" -> if (b == 0.0) "Lỗi" else (a / b).toString() // Chặn lỗi chia cho 0
        else -> "" 
    }.removeSuffix(".0") // Nếu kết quả là số nguyên (vd 4.0), xóa chữ ".0" đi cho đẹp
}

// ==========================================
// BÀI 1: VÒNG LẶP FOR VÀ KIỂU NULLABLE
// ==========================================
// data class dùng để tạo khuôn mẫu dữ liệu Sinh viên. Dấu ? sau String nghĩa là biến này có thể mang giá trị null
data class StudentB1(val name: String, val email: String?, val phone: String?) 

@Composable
fun Bai1_VongLap() {
    // Tạo danh sách 5 sinh viên có sẵn, một vài người thiếu email hoặc sđt (truyền null vào)
    val students = listOf(
        StudentB1("Nguyễn Văn An", "an@sv.edu.vn", "0901 234 567"),
        StudentB1("Trần Thị Bình", null, "0912 888 999"),
        StudentB1("Lê Minh Chí", "chi@sv.edu.vn", null),
        StudentB1("Phạm Quốc Dũng", null, null),
        StudentB1("Võ Thanh Em", "em@sv.edu.vn", "0933 111 222")
    )
    
    var missingEmailCount = 0
    // Chạy vòng lặp for đếm số người thiếu email
    for (s in students) { if (s.email == null) missingEmailCount++ } 

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // LazyColumn giống như RecyclerView, chỉ load các item đang hiển thị trên màn hình để tiết kiệm RAM. weight(1f) giúp nó đẩy phần thống kê xuống đáy.
        LazyColumn(modifier = Modifier.weight(1f)) {
            // Duyệt qua từng sinh viên trong danh sách để vẽ giao diện thẻ
            items(students) { s ->
                // Vẽ cái thẻ hình chữ nhật màu trắng, bo góc, có viền xám
                Column(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp).background(Color.White, RoundedCornerShape(8.dp)).border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(8.dp)).padding(16.dp)) {
                    Text(s.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    // s.email ?: "Chưa cập nhật" -> Đây là toán tử Elvis. Nếu email = null thì in ra chữ "Chưa cập nhật", ngược lại in ra email.
                    // Đổi màu đỏ nếu biến bị null
                    Text("Email: ${s.email ?: "Chưa cập nhật"}", fontSize = 14.sp, color = if (s.email == null) Color.Red else Color.Black) 
                    Text("SĐT: ${s.phone ?: "Chưa cập nhật"}", fontSize = 14.sp, color = if (s.phone == null) Color.Red else Color.Black) 
                }
            }
        }
        
        // Vẽ thanh thống kê nằm ở cuối màn hình
        Row(modifier = Modifier.fillMaxWidth().background(Color(0xFFE5E7EB), RoundedCornerShape(8.dp)).padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Tổng: ${students.size} sinh viên", fontWeight = FontWeight.Bold) 
            Text("Thiếu email: $missingEmailCount", fontWeight = FontWeight.Bold, color = Color(0xFF4338CA)) 
        }
    }
}

// ==========================================
// HÀM DÙNG CHUNG: XẾP LOẠI VÀ CHỌN MÀU
// ==========================================
// Nhận vào điểm, trả về chuỗi kết quả học lực
fun xepLoai(diem: Double?): String {
    if (diem == null) return "Chưa có điểm" 
    if (diem < 0 || diem > 10) return "Không hợp lệ" 
    if (diem >= 8.0) return "Giỏi" 
    if (diem >= 6.5) return "Khá" 
    if (diem >= 5.0) return "Trung bình" 
    return "Yếu" 
}

// Hàm nhận vào chuỗi học lực để quyết định màu hiển thị (Giỏi = Xanh lá, Khá = Xanh dương, Yếu = Đỏ...)
fun getColorForXepLoai(loai: String): Color {
    return when (loai) {
        "Giỏi" -> Color(0xFF4CAF50)
        "Khá" -> Color(0xFF2196F3)
        "Trung bình" -> Color(0xFFFF9800)
        "Yếu" -> Color(0xFFF44336)
        else -> Color.Gray
    }
}

// ==========================================
// BÀI 2: CÂU LỆNH IF / ELSE
// ==========================================
@Composable
fun Bai2_IfElse() {
    // Khởi tạo các giá trị cứng (hardcode) theo yêu cầu đề bài
    val diemHienTai = 8.5 
    val loaiHienTai = xepLoai(diemHienTai) 
    val listKiemTraThem = listOf(9.0, 7.0, 5.5, 3.0, 11.0) 

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Vùng trên cùng: Hiển thị điểm 8.5 nổi bật
        Column(modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(12.dp)).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Điểm trung bình", fontSize = 14.sp, color = Color.Gray)
            Text("$diemHienTai", fontSize = 48.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            
            // Box này chứa chữ "Giỏi" có màu nền nhạt. copy(alpha = 0.1f) tức là lấy màu đó nhưng làm mờ đi 90%
            Box(modifier = Modifier.fillMaxWidth().background(getColorForXepLoai(loaiHienTai).copy(alpha = 0.1f), RoundedCornerShape(8.dp)).padding(12.dp), contentAlignment = Alignment.Center) {
                Text(loaiHienTai, fontWeight = FontWeight.Bold, color = getColorForXepLoai(loaiHienTai), fontSize = 18.sp) 
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("KIỂM TRA THÊM", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        
        // Vùng bên dưới: Chạy vòng lặp test các con điểm mẫu
        Column(modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(12.dp)).padding(16.dp)) {
            listKiemTraThem.forEach { diem ->
                val loai = xepLoai(diem)
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    // if (diem % 1 == 0.0) kiểm tra xem điểm có phải số tròn không, nếu tròn (vd 9.0) thì ép kiểu về số nguyên (9) cho đẹp
                    Text("Điểm ${if(diem % 1 == 0.0) diem.toInt() else diem}")
                    Text(loai, color = getColorForXepLoai(loai), fontWeight = FontWeight.Bold)
                }
                // Vẽ đường gạch ngang mờ phân cách giữa các dòng
                HorizontalDivider(color = Color(0xFFF3F4F6)) 
            }
        }
    }
}

// ==========================================
// BÀI 3: TỔNG HỢP FOR + IF + NULLABLE
// ==========================================
data class StudentB3(val name: String, val score: Double?) 

@Composable
fun Bai3_TongHop() {
    val students = listOf(
        StudentB3("Nguyễn Văn An", 9.0),
        StudentB3("Trần Thị Bình", 7.2),
        StudentB3("Lê Minh Chí", null), 
        StudentB3("Phạm Quốc Dũng", 4.5),
        StudentB3("Võ Thanh Em", 5.8)
    )

    // Khai báo các biến tạm để tính toán số liệu
    var sumScore = 0.0
    var countValid = 0
    var soHocSinhGioi = 0
    var soChuaCoDiem = 0
    var maxScore = 0.0

    // Vòng lặp duyệt qua list sinh viên để gom số liệu
    for (s in students) {
        if (s.score == null) {
            soChuaCoDiem++ // Nếu điểm null, tăng đếm số chưa có điểm
        } else {
            sumScore += s.score // Cộng dồn điểm để tính trung bình
            countValid++ // Đếm số người đã có điểm
            if (s.score >= 8.0) soHocSinhGioi++ 
            if (s.score > maxScore) maxScore = s.score // Tìm điểm lớn nhất
        }
    }
    
    // Tính trung bình lớp. Nếu lớp có người, thì chia ra.
    // Lệnh * 100.0).roundToInt() / 100.0 dùng để làm tròn đúng 2 chữ số thập phân (Ví dụ ra 6.63)
    val avgScore = if (countValid > 0) (sumScore / countValid * 100.0).roundToInt() / 100.0 else 0.0

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Thẻ màu xanh dương đậm ở trên cùng hiển thị điểm số tổng kết
        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFF1E3A8A), RoundedCornerShape(12.dp)).padding(20.dp)) {
            Text("Điểm trung bình lớp", color = Color.White, fontSize = 14.sp)
            Text("$avgScore", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold) 
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.2f)) // Vẽ đường line trắng mờ phân cách
            Spacer(modifier = Modifier.height(16.dp))
            
            // Xếp ngang 3 thông số nhỏ: Giỏi, Chưa có điểm, Cao nhất
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column { Text("Giỏi", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp); Text("$soHocSinhGioi", color = Color.White, fontWeight = FontWeight.Bold) } 
                Column { Text("Chưa có điểm", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp); Text("$soChuaCoDiem", color = Color.White, fontWeight = FontWeight.Bold) } 
                Column { Text("Cao nhất", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp); Text("$maxScore", color = Color.White, fontWeight = FontWeight.Bold) } 
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("DANH SÁCH • ${students.size} SINH VIÊN", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))

        // Dùng LazyColumn để render danh sách từng sinh viên ở phía dưới
        LazyColumn(modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(12.dp)).padding(16.dp)) {
            items(students) { s ->
                val loai = xepLoai(s.score) 
                // Căn lề giữa theo chiều dọc bằng verticalAlignment = Alignment.CenterVertically
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(s.name, fontWeight = FontWeight.Bold)
                        Text(loai, fontSize = 12.sp, color = getColorForXepLoai(loai)) // In ra chữ Giỏi, Khá, Yếu bằng đúng màu của nó
                    }
                    // In điểm số bên góc phải. Nếu bị null thì in ra dấu gạch ngang "—"
                    Text(text = s.score?.toString() ?: "—", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                HorizontalDivider(color = Color(0xFFF3F4F6)) 
            }
        }
    }
}