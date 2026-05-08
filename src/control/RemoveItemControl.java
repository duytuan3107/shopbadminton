package control;

import entity.Cart;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "RemoveItemControl", urlPatterns = {"/remove-item"})
public class RemoveItemControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // 1. Lấy ID sản phẩm cần xóa
        String pid = request.getParameter("pid");

        // 2. Lấy giỏ hàng từ Session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        // 3. Thực hiện xóa (Hàm removeItem bạn đã viết trong class Cart)
        if (cart != null && pid != null) {
            try {
                int id = Integer.parseInt(pid);
                cart.removeItem(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // 4. Lưu lại vào Session và quay về trang giỏ hàng
        session.setAttribute("cart", cart);
        response.sendRedirect("show-cart");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}