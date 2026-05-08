// ==========================================
// CONFIG & BIẾN CHUNG
// ==========================================
const KEY_CART = 'dtBadmintonCart';

// ==========================================
// 1. XỬ LÝ MODAL CHỌN SIZE (GIỮ NGUYÊN)
// ==========================================

function openSizeModal(button) {
    const modal = document.getElementById('sizeModal');
    const productCard = button.closest('.product-card');

    const title = productCard.querySelector('h3').innerText;
    const priceText = productCard.querySelector('.new-price').innerText;
    const image = productCard.querySelector('img').src;

    modal.style.display = 'flex';
    modal.dataset.title = title;
    modal.dataset.price = priceText;
    modal.dataset.image = image;

    document.getElementById('sizeProductInfo').innerHTML = `
        <div style="display:flex; align-items:center; gap:10px; margin-bottom:15px; background:#222; padding:10px; border-radius:8px;">
            <img src="${image}" style="width:50px; height:50px; object-fit:cover; border-radius:4px;">
            <div>
                <p style="margin:0; font-size:0.9rem; font-weight:bold;">${title}</p>
                <p style="margin:0; color:#2ed573; font-size:0.85rem;">${priceText}</p>
            </div>
        </div>
    `;

    renderSizeOptions(title);
}

function renderSizeOptions(title) {
    const sizeSelect = document.getElementById('sizeSelect');
    sizeSelect.innerHTML = '<option value="">-- Chọn kích thước --</option>';

    let name = title.toLowerCase();
    let options = [];

    if (name.includes('vợt')) {
        options = ['3U', '4U'];
    } else if (name.includes('tất') || name.includes('vớ')) {
        options = ['23-25 cm', '25-28 cm'];
    } else if (name.includes('giày')) {
        options = ['38', '39', '40', '41', '42', '43', '44'];
    } else if (name.includes('áo') || name.includes('quần')) {
        options = ['M', 'L', 'XL', 'XXL'];
    } else {
        options = ['Tiêu chuẩn'];
    }

    options.forEach(s => {
        const opt = document.createElement('option');
        opt.value = s;
        opt.innerText = s;
        sizeSelect.appendChild(opt);
    });
}

function closeSizeModal() {
    document.getElementById('sizeModal').style.display = 'none';
}

function addToCartWithSize() {
    const modal = document.getElementById('sizeModal');
    const size = document.getElementById('sizeSelect').value;

    if (!size) {
        alert(" Chọn size đã bạn ơi!");
        return;
    }

    const title = modal.dataset.title;
    const price = modal.dataset.price;
    const image = modal.dataset.image;

    saveToCart(title, size, price, image);
}

// ==========================================
// 2. XỬ LÝ GIỎ HÀNG (LOCALSTORAGE - GIỮ NGUYÊN)
// ==========================================

function saveToCart(title, size, price, image) {
    let cart = JSON.parse(localStorage.getItem(KEY_CART) || '[]');
    let cleanPrice = String(price).replace(/[^0-9]/g, '');

    let item = cart.find(i => i.title === title && i.attribute === size);

    if (item) {
        item.qty += 1;
    } else {
        cart.push({
            title: title,
            price: Number(cleanPrice),
            image: image,
            qty: 1,
            attribute: size
        });
    }

    localStorage.setItem(KEY_CART, JSON.stringify(cart));
    showToast();
    closeSizeModal();
    updateCartBadge();

    setTimeout(() => {
        window.location.href = "checkout.jsp";
    }, 800);
}

function updateCartBadge() {
    const cart = JSON.parse(localStorage.getItem(KEY_CART) || '[]');
    const count = cart.reduce((sum, item) => sum + item.qty, 0);
    const badge = document.querySelector('.cart-count');
    if (badge) badge.innerText = count;
}

// ==========================================
// 3. HÀM MỚI: XỬ LÝ ĐẶT HÀNG & CLEAR GIỎ HÀNG
// ==========================================

function processOrder() {
    const name = document.getElementById('name').value;
    const phone = document.getElementById('phone').value;
    const address = document.getElementById('address').value;

    // Lấy dữ liệu giỏ hàng để gửi kèm
    const cart = JSON.parse(localStorage.getItem(KEY_CART) || '[]');
    if (cart.length === 0) {
        alert("Giỏ hàng trống trơn à!");
        return;
    }

    // Chuẩn bị dữ liệu gửi lên Servlet (Dùng URLSearchParams để Servlet lấy được qua request.getParameter)
    const params = new URLSearchParams();
    params.append('name', name);
    params.append('phone', phone);
    params.append('address', address);

    // Tạo chuỗi cartDetail giống như bạn đang làm
    const cartDetail = cart.map(item => `- ${item.title} (Size: ${item.attribute}) x${item.qty}`).join('\n');
    params.append('cartDetail', cartDetail);

    const totalMoney = cart.reduce((sum, item) => sum + (item.price * item.qty), 0);
    params.append('totalMoney', totalMoney.toLocaleString() + 'đ');

    // Gửi dữ liệu sang CheckoutServlet
    fetch('CheckoutServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params
    })
    .then(response => {
        if (response.ok) {
            // THÀNH CÔNG: XÓA GIỎ HÀNG
            localStorage.removeItem(KEY_CART);

            // Cập nhật lại badge về 0
            updateCartBadge();

            alert('📦 Đặt hàng thành công! Cảm ơn bạn.');
            window.location.href = 'index.jsp'; // Quay về trang chủ
        } else {
            alert('❌ Lỗi lưu đơn hàng!');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('❌ Lỗi kết nối!');
    });
}

// ==========================================
// 4. HIỆU ỨNG GIAO DIỆN KHÁC (GIỮ NGUYÊN)
// ==========================================

function showToast() {
    const toast = document.getElementById('toast');
    if (toast) {
        toast.classList.add('show');
        setTimeout(() => toast.classList.remove('show'), 2000);
    }
}

window.onclick = function(event) {
    const modal = document.getElementById('sizeModal');
    if (event.target == modal) closeSizeModal();
}

document.addEventListener('DOMContentLoaded', updateCartBadge);

function openCart() { window.location.href = "checkout.jsp"; }