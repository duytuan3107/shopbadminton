<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng - DT BADMINTON</title>
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="assets/css/cart.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body class="dark-theme">

    <div class="topbar">
        <div class="container">
            <h1 class="logo">DT BADMINTON - GIỎ HÀNG</h1>
        </div>
    </div>

    <div class="cart-wrapper">
        <div class="container">
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>Sản phẩm</th>
                        <th>Giá</th>
                        <th>Số lượng</th>
                        <th>Thành tiền</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${cart.items}" var="item">
                        <tr>
                            <td class="product-cell">
                                <img src="${item.product.image}" alt="">
                                <span>${item.product.name}</span>
                            </td>
                            <td><fmt:formatNumber value="${item.price}" type="number"/> ₫</td>
                            <td>${item.quantity}</td>
                            <td class="subtotal"><fmt:formatNumber value="${item.price * item.quantity}" type="number"/> ₫</td>
                            <td>
                                <a href="remove-item?pid=${item.product.id}" class="btn-remove">
                                    <i class="fas fa-trash"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="cart-footer">
                <div class="total-section">
                    <h3>Tổng thanh toán: <span class="total-price"><fmt:formatNumber value="${cart.totalMoney}" type="number"/> ₫</span></h3>
                </div>
                <div class="cart-actions">
                    <a href="home" class="btn-continue">Tiếp tục mua sắm</a>
                    <a href="checkout" class="btn-checkout">Tiến hành thanh toán</a>
                </div>
            </div>
        </div>
    </div>

</body>
</html>