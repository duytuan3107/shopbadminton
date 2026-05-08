<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${detail.name} - DT BADMINTON</title>
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="assets/css/product-detail.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body style="background-color: #121212; color: white; margin: 0; font-family: 'Segoe UI', sans-serif;">

    <div class="topbar">
        <div class="container">
            <div class="topbar-content" style="display: flex; justify-content: space-between; align-items: center; padding: 15px 0;">
                <div class="logo-section"><h1 class="logo" style="margin: 0; color: #ff4757;">DT BADMINTON</h1></div>
                <div class="contact-section"><span>📞 Hotline: 0886734743</span></div>
            </div>
        </div>
    </div>

    <div class="detail-wrapper" style="padding: 60px 0;">
        <div class="container" style="max-width: 1100px; margin: 0 auto; display: flex; gap: 50px;">
            <div class="detail-left" style="flex: 1; background: #1e1e1e; padding: 20px; border-radius: 15px; border: 1px solid #333;">
                <img src="${detail.image}" alt="${detail.name}" style="width: 100%; border-radius: 10px; display: block;">
            </div>

            <div class="detail-right" style="flex: 1.2;">
                <h1 style="font-size: 36px; margin: 0 0 10px 0; color: #fff;">${detail.name}</h1>
                <div style="font-size: 28px; color: #ff4757; font-weight: bold; margin-bottom: 20px;">
                    <fmt:formatNumber value="${detail.price}" type="number" /> ₫
                </div>

                <div style="background: #252525; padding: 20px; border-radius: 10px; border-left: 4px solid #ff4757; margin-bottom: 30px;">
                    <h3 style="margin-top: 0; color: #aaa; font-size: 16px; text-transform: uppercase;">Mô tả sản phẩm</h3>
                    <p style="color: #ccc; line-height: 1.8; margin: 0;">${detail.description}</p>
                </div>

                <div class="action-section">
                    <div class="quantity-box" style="margin-bottom: 20px;">
                        <label style="color: #aaa; margin-right: 10px;">Số lượng:</label>
                        <input type="number" id="buy-quantity" value="1" min="1"
                               style="padding: 12px; background: #333; color: white; border: 1px solid #444; border-radius: 8px; width: 80px;">
                    </div>

                    <button type="button" class="btn-add-cart" onclick="localAddToCart()"
                            style="width: 100%; padding: 18px; background: #ff4757; color: white; border: none; border-radius: 10px; font-weight: bold; cursor: pointer; font-size: 18px; box-shadow: 0 4px 15px rgba(255, 71, 87, 0.3);">
                        🛒 THÊM VÀO GIỎ HÀNG
                    </button>
                </div>

                <div style="margin-top: 30px;">
                    <a href="home" style="color: #888; text-decoration: none; font-size: 14px;">
                        <i class="fas fa-chevron-left"></i> Quay lại trang chủ
                    </a>
                </div>
            </div>
        </div>
    </div>

    <script>
        function localAddToCart() {
            const CART_KEY = 'dtBadmintonCart';

            // 1. Lấy dữ liệu từ JSP (Java) sang JS
            const name = "${detail.name}";
            const priceRaw = "${detail.price}";
            const img = "${detail.image}";
            const qty = parseInt(document.getElementById('buy-quantity').value) || 1;

            // 2. Làm sạch giá tiền (phòng trường hợp giá có .0 hay ₫)
            const price = Number(String(priceRaw).replace(/[^0-9]/g, ''));

            // 3. Đọc giỏ hàng cũ
            let cart = JSON.parse(localStorage.getItem(CART_KEY)) || [];

            // 4. Kiểm tra xem sản phẩm đã có chưa
            let existingItem = cart.find(item => item.title === name);

            if (existingItem) {
                existingItem.qty += qty;
            } else {
                cart.push({
                    title: name,
                    price: price,
                    image: img,
                    qty: qty,
                    attribute: 'Tiêu chuẩn'
                });
            }

            // 5. Lưu vào LocalStorage
            localStorage.setItem(CART_KEY, JSON.stringify(cart));

            // 6. Chuyển hướng sang trang thanh toán ngay lập tức
            alert("Đã thêm thành công! Đang chuyển đến trang thanh toán...");
            window.location.href = "checkout.jsp";
        }
    </script>

    <footer style="padding: 40px 0; background: #000; text-align: center; border-top: 1px solid #333; margin-top: 50px; color: #666;">
        &copy; 2024 DT BADMINTON. Nhà cung cấp cầu lông chính hãng.
    </footer>
</body>
</html>