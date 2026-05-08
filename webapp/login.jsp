<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập Admin - DT BADMINTON</title>
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="assets/css/login.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="login-page">
        <!-- Các khối tròn trang trí phía sau (Background Decor) -->
        <div class="circle circle-1"></div>
        <div class="circle circle-2"></div>

        <div class="login-card">
            <div class="login-header">
                <h1 class="logo-text">DT <span>BADMINTON</span></h1>
                <p>Hệ thống quản lý cửa hàng</p>
            </div>

            <!-- Hiển thị thông báo lỗi nếu có -->
            <c:if test="${not empty mess}">
                <div class="error-alert">
                    <i class="fas fa-exclamation-circle"></i> ${mess}
                </div>
            </c:if>

            <form action="login" method="post" class="login-form">
                <div class="input-group">
                    <i class="fas fa-user"></i>
                    <input type="text" name="user" placeholder="Tên đăng nhập" required>
                </div>

                <div class="input-group">
                    <i class="fas fa-lock"></i>
                    <input type="password" name="pass" placeholder="Mật khẩu" required>
                </div>

                <button type="submit" class="btn-submit">
                    ĐĂNG NHẬP <i class="fas fa-arrow-right"></i>
                </button>
            </form>

            <div class="login-footer">
                <a href="home"><i class="fas fa-chevron-left"></i> Quay lại trang chủ</a>
            </div>
        </div>
    </div>
</body>
</html>