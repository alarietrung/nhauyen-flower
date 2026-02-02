🌸 Nhã Uyên Flower - Website Bán Hoa Tươi Spring Boot
    Nhã Uyên Flower là một website thương mại điện tử chuyên kinh doanh hoa tươi, được xây dựng hoàn chỉnh theo mô hình Full-stack sử dụng Java Spring Boot (Backend) và Thymeleaf (Frontend).

    Dự án tập trung vào trải nghiệm người dùng mượt mà, giao diện hiện đại (UI/UX) và hệ thống quản trị (Admin Panel) mạnh mẽ.

🚀 Công Nghệ Sử Dụng (Tech Stack)

Backend
    Ngôn ngữ: Java (JDK 17/21)
    Framework: Spring Boot 3.x
    Database: MySQL (sử dụng qua XAMPP)
    ORM: Spring Data JPA (Hibernate)
    Security: Spring Security 6 (Phân quyền, Mã hóa BCrypt, Chống CSRF)
    Mail: Java Mail Sender (Gửi email SMTP)

Frontend
    Template Engine: ThymeleafCSS 
    Framework: Tailwind CSS (CDN)
    Icons: FontAwesome
    Fonts: Google Fonts (Quicksand, Playfair Display)
    JavaScript: Vanilla JS (Xử lý hiệu ứng, Ajax, Giỏ hàng)

✨ Tính Năng Nổi Bật

1. Phân hệ Khách Hàng (Client)
    Trang chủ: Giao diện đẹp, Banner động, Hiệu ứng hoa rơi, Sản phẩm nổi bật.
    Tìm kiếm & Lọc: Tìm kiếm theo tên hoa, lọc sản phẩm theo Danh mục.
    Phân trang: Hiển thị sản phẩm theo trang (Pagination) để tối ưu hiệu năng.
    Giỏ hàng: Thêm/Sửa/Xóa sản phẩm, tự động tính tổng tiền.
    Thanh toán: Đặt hàng không cần đăng nhập hoặc có đăng nhập.
    Tài khoản:
        Đăng ký / Đăng nhập / Đăng xuất.
        Quên mật khẩu: Gửi mã OTP xác thực qua Email.
        Hồ sơ cá nhân: Cập nhật thông tin, xem Lịch sử đơn hàng.
    Email: Tự động gửi email xác nhận khi đặt hàng thành công.
    
2. Phân hệ Quản Trị (Admin Panel)
    Dashboard: Thống kê tổng quan Doanh thu, Số lượng đơn hàng, Sản phẩm.
    Quản lý Sản phẩm: Thêm mới, Chỉnh sửa, Xóa, Upload hình ảnh.
    Quản lý Danh mục: CRUD (Thêm, Xem, Sửa, Xóa) danh mục hoa.
    Quản lý Đơn hàng: Xem chi tiết đơn hàng, Cập nhật trạng thái (Chờ duyệt -> Đang giao -> Hoàn thành).
    Bảo mật: Trang Admin yêu cầu quyền ROLE_ADMIN mới được truy cập.

🗄️ Thiết Kế Cơ Sở Dữ Liệu (Database Schema)
Hệ thống bao gồm 5 bảng chính:
users: Lưu thông tin tài khoản (username, password, email, role...).
categories: Lưu danh mục hoa.
products: Lưu thông tin hoa, liên kết với Category.
orders: Lưu thông tin đơn hàng tổng quát.
order_details: Lưu chi tiết từng món hàng trong đơn.

🛠️ Hướng Dẫn Cài Đặt & Chạy Dự Án

Bước 1: Chuẩn bị môi trường
Cài đặt Java JDK 17 trở lên.Cài đặt Maven.
Cài đặt XAMPP (để chạy MySQL).
IDE: Visual Studio Code (hoặc IntelliJ IDEA).

Bước 2: Cấu hình Database
Mở XAMPP, Start Apache và MySQL.
Truy cập http://localhost/phpmyadmin.
Tạo database mới tên là: nhauyen_flower_db.

Bước 3: Cấu hình Dự án

Mở file src/main/resources/application.properties và cập nhật:

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/nhauyen_flower_db
spring.datasource.username=root
spring.datasource.password=

# Email (Thay bằng App Password của bạn)
spring.mail.username=email_cua_ban@gmail.com
spring.mail.password=mat_khau_ung_dung

Bước 4: Chạy Ứng dụngMở Terminal tại thư mục gốc dự án.

Chạy lệnh: mvn spring-boot:run (Hoặc bấm nút Run trong IDE).
Hệ thống sẽ tự động tạo bảng và khởi tạo tài khoản Admin mặc định.

Bước 5: Truy cập

Trang chủ: http://localhost:8081
Trang Admin: http://localhost:8081/admin/dashboard
Tài khoản Admin mặc định:
    User: admin
    Pass: 123📂 

Cấu Trúc Thư Mụcsrc/main/java/com/nhauyen/flower
├── config          # Cấu hình Security, Data Seeder
├── controller      # Xử lý luồng đi (Web Controller)
├── entity          # Các Class đại diện cho bảng Database (Model)
├── repository      # Giao tiếp với Database (JPA)
├── service         # Xử lý logic nghiệp vụ, Email
└── FlowerApplication.java  # File chạy chính

src/main/resources
├── static          # Chứa file tĩnh (CSS, JS, Images)
├── templates       # Chứa giao diện HTML (Thymeleaf)
│   ├── admin       # Giao diện quản trị
│   ├── error       # Các trang lỗi (404, 403, 500)
│   └── ...         # Các trang người dùng (index, cart, login...)
└── application.properties
👨‍💻 Tác Giả
Dự án được thực hiện bởi Trung Lọ 30 Ngày Liên Tục 1.0.0 - 2026
