<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chỉnh sửa sản phẩm - DT Badminton</title>

    <link href="assets/css/style_edit.css" rel="stylesheet" type="text/css">

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
</head>
<body>

    <div class="edit-container">
        <h2>Chỉnh sửa sản phẩm</h2>

        <form action="edit" method="post">
            <!-- Input ẩn giữ ID để biết sửa sản phẩm nào -->
            <input type="hidden" name="id" value="${detail.id}">

            <div class="form-group">
                <label>Tên sản phẩm</label>
                <input type="text" name="name" value="${detail.name}" placeholder="Nhập tên vợt, giày..." required>
            </div>

            <div class="form-group">
                <label>Đường dẫn ảnh (URL)</label>
                <input type="text" name="image" value="${detail.image}" placeholder="assets/images/..." required>
            </div>

            <div class="form-group">
                <label>Giá bán (VNĐ)</label>
                <input type="number" name="price" value="${detail.price}" required>
            </div>

            <div class="form-group">
                <label>Thương hiệu</label>
                <input type="text" name="brand" value="${detail.brand}" placeholder="Yonex, Victor, Lining...">
            </div>

            <div class="form-group">
                <label>Mô tả chi tiết</label>
                <textarea name="description" placeholder="Mô tả đặc điểm sản phẩm...">${detail.description}</textarea>
            </div>

            <div class="form-group">
                <label>Danh mục sản phẩm</label>
                <select name="category">
                    <c:forEach items="${listCC}" var="o">
                        <!-- So sánh cateID của sản phẩm với cid danh mục để chọn đúng mục cũ -->
                        <option value="${o.cid}" ${detail.cateID == o.cid ? "selected" : ""}>${o.cname}</option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit" class="btn-submit">LƯU THAY ĐỔI</button>
        </form>

        <div style="text-align: center; margin-top: 15px;">
            <a href="manager" style="color: #a4b0be; text-decoration: none; font-size: 14px;">← Quay lại trang quản lý</a>
        </div>
    </div>

</body>
</html>