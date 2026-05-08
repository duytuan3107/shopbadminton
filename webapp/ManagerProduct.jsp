<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý DT Badminton</title>
    <link rel="stylesheet" href="assets/css/manager.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        /* Tùy chỉnh tone màu nút cho bớt chói */
        .btn-custom {
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
            transition: 0.3s;
        }
        .btn-active { background: #2c3e50; color: #3498db !important; border: 1px solid #3498db; }
        .btn-inactive { background: transparent; color: #95a5a6 !important; border: 1px solid #7f8c8d; }
        .btn-active:hover { background: #34495e; }

        /* Modal background */
        .modal { display: none; position: fixed; z-index: 1000; left: 0; top: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.8); }
        .modal-content { background: #1a1a1a; margin: 5% auto; padding: 20px; border: 1px solid #333; width: 50%; color: white; border-radius: 10px; }
        .form-control { width: 100%; padding: 8px; margin: 10px 0; background: #333; border: 1px solid #444; color: white; }
    </style>
</head>
<body class="dark-theme">

<div class="manager-container">
    <div style="margin-bottom: 30px; display: flex; gap: 10px;">
        <%-- Đã đổi tone màu bớt chói --%>
        <a href="admin-manager?mode=product" class="btn-custom ${currentMode != 'order' ? 'btn-active' : 'btn-inactive'}">🏸 Sản phẩm</a>
        <a href="admin-manager?mode=order" class="btn-custom ${currentMode == 'order' ? 'btn-active' : 'btn-inactive'}">📦 Đơn hàng</a>
    </div>

    <c:choose>
        <%-- TRƯỜNG HỢP 1: XEM ĐƠN HÀNG --%>
        <c:when test="${currentMode == 'order'}">
            <div class="header-actions">
                <h2>Quản lý <b>Đơn hàng</b></h2>
            </div>
            <table class="manager-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Khách hàng</th>
                        <th>Số điện thoại</th>
                        <th>Chi tiết món</th>
                        <th>Tổng tiền</th>
                        <th>Ngày đặt</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listO}" var="o">
                        <tr>
                            <td>${o.id}</td>
                            <td><b>${o.name}</b></td>
                            <td>${o.phone}</td>
                            <td style="font-size: 0.85rem; color: #aaa; white-space: pre-line;">${o.detail}</td>
                            <td style="color: #2ed573; font-weight: bold;">${o.total}</td>
                            <td>${o.date}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>

        <%-- TRƯỜNG HỢP 2: XEM SẢN PHẨM --%>
        <c:otherwise>
            <div class="header-actions">
                <h2>Quản lý <b>Sản phẩm</b></h2>
                <div class="buttons">
                   <a href="javascript:void(0)" class="btn btn-success" onclick="openModal()">
                       <i class="fas fa-plus-circle"></i> <span>Thêm sản phẩm mới</span>
                   </a>
                </div>
            </div>

            <table class="manager-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tên sản phẩm</th>
                        <th>Hình ảnh</th>
                        <th>Giá</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listP}" var="o">
                        <tr>
                            <td>${o.id}</td>
                            <td>${o.name}</td>
                            <td><img src="${o.image}" class="img-product"></td>
                            <td>${o.price} ₫</td>
                            <td>
                                <a href="loadProduct?pid=${o.id}" class="edit"><i class="fas fa-edit"></i></a>
                                <a href="delete?pid=${o.id}" class="delete" onclick="return confirm('Xóa hả?')"><i class="fas fa-trash"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<div id="addProductModal" class="modal">
    <div class="modal-content">
        <form action="add" method="post">
            <div class="modal-header">
                <h4 style="margin: 0">Thêm sản phẩm mới</h4>
            </div>
            <div class="modal-body">
                <input name="name" type="text" class="form-control" placeholder="Tên sản phẩm" required>
                <input name="image" type="text" class="form-control" placeholder="Link ảnh" required>
                <input name="price" type="number" class="form-control" placeholder="Giá" required>
                <input name="brand" type="text" class="form-control" placeholder="Thương hiệu" required>
                <textarea name="description" class="form-control" placeholder="Mô tả" rows="3" required></textarea>
                <select name="category" class="form-control">
                    <c:forEach items="${listCC}" var="o">
                        <option value="${o.cid}">${o.cname}</option>
                    </c:forEach>
                </select>
            </div>
            <div style="text-align: right; margin-top: 15px;">
                <input type="button" class="btn btn-default" value="Hủy" onclick="closeModal()">
                <input type="submit" class="btn btn-success" value="Xác nhận Thêm">
            </div>
        </form>
    </div>
</div>

<script>
    function openModal() {
        document.getElementById("addProductModal").style.display = "block";
    }
    function closeModal() {
        document.getElementById("addProductModal").style.display = "none";
    }
    window.onclick = function(event) {
        let modal = document.getElementById("addProductModal");
        if (event.target == modal) closeModal();
    }
</script>
</body>
</html>