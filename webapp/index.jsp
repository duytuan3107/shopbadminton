<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="context.DBContext"%>
<%@page import="dao.ProductDAO"%>
<%@page import="entity.Product"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<%
    ProductDAO dao = new ProductDAO();
    List<Product> list = dao.getAllProduct();
%>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DT BADMINTON - Shop Cầu Lông</title>
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="assets/css/modal.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>

<body>

    <div class="topbar">
        <div class="container">
            <div class="topbar-content">
                <div class="logo-section">
                    <div class="logo-container">
                        <img src="assets/images/logomini.png" alt="DT Badminton Logo" class="logo-img">
                        <h1 class="logo">DT BADMINTON</h1>
                    </div>
                    <p class="tagline">Chất Lượng - Uy Tín - Thương Hiệu</p>
                </div>

                <div class="search-section">
                    <form action="search" method="post" class="search-container">
                        <input type="text" name="txt" class="search-input" placeholder="🔍 Tra cứu sản phẩm..." required>
                        <button type="submit" class="search-btn">Tìm kiếm</button>
                    </form>
                </div>

                <div class="contact-section">
                    <p>📞 Hotline: <strong>0886734743</strong></p>

                    <a href="cart.jsp" class="cart-btn" style="text-decoration: none; display: inline-block; position: relative;">
                        <i class="fas fa-shopping-cart"></i>
                        <span class="cart-count">
                            ${(sessionScope.cart != null) ? sessionScope.cart.items.size() : 0}
                        </span>
                    </a>
                </div>
            </div>
        </div>
    </div>

    <nav class="navbar">
        <ul class="menu-ngang">
            <li><a href="home">🏠 TRANG CHỦ</a></li>
            <li class="menu-dropdown">
                <a href="#products">🛍️ SẢN PHẨM</a>
                <ul class="submenu">
                    <li><a href="category?cid=1">🎾 Vợt Cầu Lông</a></li>
                    <li><a href="category?cid=2">👟 Giày Cầu Lông</a></li>
                    <li><a href="category?cid=3">👜 Túi & Balo</a></li>
                    <li><a href="category?cid=4">🧦 Tất cầu lông</a></li>
                    <li><a href="category?cid=5">👕 Áo cầu lông</a></li>
                    <li><a href="category?cid=6">🏸 Quả cầu lông</a></li>
                    <li><a href="category?cid=7">🏸 Cước cầu lông</a></li>
                </ul>
            </li>
            <li><a href="#sale">🔥 SALE OFF</a></li>
            <li><a href="#news">📰 TIN TỨC</a></li>
            <li><a href="#about">ℹ️ GIỚI THIỆU</a></li>
            <li><a href="#contact">💬 LIÊN HỆ</a></li>

            <li class="user-info">
                <c:if test="${sessionScope.acc != null}">
                    <a href="#" style="color: #2ed573;">👤 Chào, ${sessionScope.acc.user}!</a>
                    <ul class="submenu">
                        <c:if test="${sessionScope.acc.isAdmin == 1}">
                            <li><a href="admin-manager">⚙️ Quản lý Shop</a></li>
                        </c:if>
                        <li><a href="logout" style="color: #ff4757;">🚪 Đăng xuất</a></li>
                    </ul>
                </c:if>
                <c:if test="${sessionScope.acc == null}">
                    <a href="login.jsp">🔑 ĐĂNG NHẬP</a>
                </c:if>
            </li>
        </ul>
    </nav>

    <section class="banner-container" id="home">
        <div class="banner-slider">
            <div class="slide active">
                <img src="assets/images/banner1.png" alt="Banner 1">
                <div class="slide-overlay">
                    <div class="slide-content">
                        <h2 class="banner-text">Vợt Cầu Lông Pro</h2>
                        <p class="slide-subtitle">Chất Lượng Cao - Giá Tốt Nhất</p>
                        <button class="cta-button">Mua Ngay</button>
                    </div>
                </div>
            </div>
            <div class="slide">
                <img src="assets/images/banner_giay.png" alt="Banner 2">
                <div class="slide-overlay">
                    <div class="slide-content">
                        <h2 class="banner-text">Giày Thể Thao Cao Cấp</h2>
                        <p class="slide-subtitle">Thoải Mái - Bền Bỉ - Phong Cách</p>
                        <button class="cta-button">Khám Phá</button>
                    </div>
                </div>
            </div>
            <div class="slide">
                <img src="assets/images/banner3.png" alt="Banner 3">
                <div class="slide-overlay">
                    <div class="slide-content">
                        <h2 class="banner-text">Quả Cầu Lông Chính Hãng</h2>
                        <p class="slide-subtitle">Độ Bền Cao - Chất Lượng Quốc Tế</p>
                        <button class="cta-button">Xem Thêm</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="slide-nav">
            <button class="slide-prev" onclick="changeSlide(-1)">❮</button>
            <button class="slide-next" onclick="changeSlide(1)">❯</button>
        </div>

        <div class="slide-indicators">
            <span class="indicator active" onclick="currentSlide(0)"></span>
            <span class="indicator" onclick="currentSlide(1)"></span>
            <span class="indicator" onclick="currentSlide(2)"></span>
        </div>
    </section>

    <section class="commitments">
        <div class="container">
            <div class="commitment-item"><i class="fas fa-truck"></i><h3>Vận Chuyển Toàn Quốc</h3><p>Giao hàng nhanh chóng</p></div>
            <div class="commitment-item"><i class="fas fa-check-circle"></i><h3>Bảo Đảm Chất Lượng</h3><p>Sản phẩm 100% chính hãng</p></div>
            <div class="commitment-item"><i class="fas fa-lock"></i><h3>Thanh Toán An Toàn</h3><p>Bảo mật thông tin cao</p></div>
            <div class="commitment-item"><i class="fas fa-sync"></i><h3>Đổi Sản Phẩm Mới</h3><p>Hỗ trợ hoàn toàn miễn phí</p></div>
        </div>
    </section>

    <section class="products-section" id="products">
        <div class="container">
            <h2 class="section-title">SẢN PHẨM NỔI BẬT</h2>
            <div class="product-grid">
                <c:forEach items="${listP}" var="o">
                    <div class="product-card">
                        <div class="product-image">
                            <a href="detail?pid=${o.id}"><img src="${o.image}" alt="${o.name}"></a>
                            <span class="badge">MỚI</span>
                        </div>
                        <div class="product-info">
                            <h3><a href="detail?pid=${o.id}">${o.name}</a></h3>
                            <div class="price-section"><span class="new-price">${o.price} ₫</span></div>

                            <a href="add-to-cart?pid=${o.id}" class="add-cart-btn">
                                <i class="fas fa-shopping-cart"></i> Thêm vào giỏ
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>

    <footer class="footer">
        <div class="container">
            <div class="footer-content">
                <div class="footer-section">
                    <h4>Về Chúng Tôi</h4>
                    <p>DT Badminton - Nhà cung cấp đồ cầu lông chính hãng hàng đầu tại Việt Nam.</p>
                </div>
                <div class="footer-section">
                    <h4>Liên Hệ</h4>
                    <p>📞 Điện thoại: 0886734743</p>
                </div>
            </div>
            <div class="footer-bottom">
                <p>&copy; 2024 DT BADMINTON. Tất cả quyền được bảo lưu.</p>
            </div>
        </div>
    </footer>

    <div id="toast" class="toast"><span>✓ Đã thêm vào giỏ hàng</span></div>

    <script type="text/javascript" src="assets/js/main.js"></script>
</body>
</html>