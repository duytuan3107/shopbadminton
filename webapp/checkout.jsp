<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thanh toán | DT Badminton</title>
    <link rel="stylesheet" href="assets/css/manager.css">
    <style>
        .checkout-box { max-width: 500px; margin: 50px auto; background: #1a1a1a; padding: 30px; border-radius: 10px; color: white; }
        .form-input { width: 100%; padding: 10px; margin: 10px 0; background: #333; border: 1px solid #444; color: white; border-radius: 5px; }
        .btn-pay { width: 100%; padding: 12px; background: #2ed573; border: none; color: white; font-weight: bold; cursor: pointer; border-radius: 5px; }
    </style>
</head>
<body class="dark-theme">
    <div class="checkout-box">
        <h2 style="text-align: center; color: #3498db;">Xác nhận đơn hàng</h2>

        <form action="checkout" method="post">
            <label>Họ và tên người nhận:</label>
            <input type="text" name="name" class="form-input" placeholder="Nguyễn Văn A" required>

            <label>Số điện thoại:</label>
            <input type="text" name="phone" class="form-input" placeholder="090..." required>

            <label>Địa chỉ giao hàng:</label>
            <textarea name="address" class="form-input" rows="3" required></textarea>

            <%-- TỰ ĐỘNG GOM CHI TIẾT GIỎ HÀNG --%>
            <c:set var="detail" value="" />
            <c:forEach items="${sessionScope.cart.items}" var="item">
                <c:set var="detail" value="${detail} - ${item.product.name} (x${item.quantity})\n" />
            </c:forEach>

            <%-- INPUT ẨN ĐỂ GỬI SANG SERVLET --%>
            <input type="hidden" name="cartDetail" value="${detail}">
            <input type="hidden" name="totalMoney" value="${totalMoney}">

            <hr style="border: 0.5px solid #333; margin: 20px 0;">
           <p>Tổng tiền thanh toán:
               <b style="color: #2ed573;">
                   <%-- Hiển thị tiền từ request --%>
                   <fmt:formatNumber value="${totalMoney}" type="number"/> VNĐ
               </b>
           </p>

            <button type="submit" class="btn-pay">XÁC NHẬN ĐẶT HÀNG</button>
        </form>
    </div>
</body>
</html>