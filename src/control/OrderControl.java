package control;

import dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OrderControl", urlPatterns = {"/order"})
public class OrderControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // 1. Lấy thông tin từ form checkout
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // 2. Lấy dữ liệu giỏ hàng từ Session (ví dụ bạn lưu giỏ hàng là 'cart')
        // Ở đây mình giả định bạn đã có hàm lưu đơn trong ProductDAO
        ProductDAO dao = new ProductDAO();
        // Giả sử hàm này lưu: tên, sđt, địa chỉ, chi tiết giỏ, tổng tiền, trạng thái=0
        dao.insertOrder(name, phone, address, "Chi tiết đơn hàng...", "500000");

        // 3. Xóa giỏ hàng sau khi đặt thành công và về trang cảm ơn
        request.getSession().removeAttribute("cart");
        response.sendRedirect("thankyou.jsp");
    }
}
