package control;

import dao.ProductDAO;
import entity.Cart; // Đảm bảo bạn có entity này
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CheckoutControl", urlPatterns = {"/checkout"})
public class CheckoutControl extends HttpServlet {

    // --- PHẦN 1: HIỂN THỊ TRANG NHẬP THÔNG TIN (GET) ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null && cart.getTotalMoney() > 0) {
            // Gửi tổng tiền sang trang checkout.jsp để hiển thị
            request.setAttribute("totalMoney", cart.getTotalMoney());
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        } else {
            // Nếu giỏ hàng trống thì quay về home
            response.sendRedirect("home");
        }
    }

    // --- PHẦN 2: XỬ LÝ LƯU ĐƠN HÀNG KHI BẤM XÁC NHẬN (POST) ---
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String cartDetail = request.getParameter("cartDetail");
        String totalMoney = request.getParameter("totalMoney");

        System.out.println("=== ĐANG XỬ LÝ ĐƠN HÀNG: " + name + " ===");

        if (name != null && !name.isEmpty() && cartDetail != null) {
            boolean isSaved = false;
            try {
                ProductDAO dao = new ProductDAO();
                // Lưu vào bảng orders và lấy ID vừa tạo
                int orderId = dao.insertOrder(name, phone, address, cartDetail, totalMoney);

                if (orderId > 0) {
                    System.out.println("=> DATABASE: Lưu đơn thành công ID: " + orderId);
                    // Lưu chi tiết đơn hàng (nếu bạn có bảng order_detail)
                    dao.insertOrderDetail(orderId, cartDetail);
                    isSaved = true;
                }
            } catch (Exception e) {
                System.out.println("=> DATABASE: Lỗi hệ thống! " + e.getMessage());
                e.printStackTrace();
            }

            // Gửi Telegram thông báo cho chủ shop
            StringBuilder sb = new StringBuilder();
            sb.append("🏸 ĐƠN HÀNG MỚI - DT BADMINTON\n");
            sb.append("👤 Khách: ").append(name).append("\n");
            sb.append("📞 SĐT: ").append(phone).append("\n");
            sb.append("📍 Đ/C: ").append(address).append("\n");
            sb.append("📦 CHI TIẾT:\n").append(cartDetail).append("\n");
            sb.append("💰 TỔNG: ").append(totalMoney);

            sendToTelegram(sb.toString());

            if (isSaved) {
                // Đặt hàng thành công thì xóa giỏ hàng trong session
                request.getSession().removeAttribute("cart");
                // Chuyển hướng sang trang cảm ơn
                response.sendRedirect("thanks.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi lưu Database");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dữ liệu không hợp lệ");
        }
    }

    private boolean sendToTelegram(String message) {
        try {
            String token = "8473029090:AAHrVw4YMXMWrZ2NivRe4LI_I7KoiMb_QXw";
            String chatId = "6286831156";
            String encodedMsg = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
            String urlString = "https://api.telegram.org/bot" + token + "/sendMessage?chat_id=" + chatId + "&text=" + encodedMsg;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            return (conn.getResponseCode() == 200);
        } catch (Exception e) {
            System.err.println("Lỗi Telegram: " + e.getMessage());
            return false;
        }
    }
}