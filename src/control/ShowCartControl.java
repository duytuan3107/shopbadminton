package control;

import entity.Cart;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ShowCartControl", urlPatterns = {"/show-cart"})
public class ShowCartControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Không cần gọi DAO, chỉ lấy từ Session ra hiển thị
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        // Nếu chưa có giỏ hàng, có thể tạo một đối tượng trống để tránh lỗi Null trên JSP
        if (cart == null) {
            cart = new Cart();
        }

        request.setAttribute("cart", cart);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
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