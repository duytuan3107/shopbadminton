package control;

import dao.ProductDAO;
import entity.Cart;
import entity.Item;
import entity.Product;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AddToCartControl", urlPatterns = {"/add-to-cart"})
public class AddToCartControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // 1. Khởi tạo Session
        HttpSession session = request.getSession();

        // 2. Lấy Cart từ Session
        Cart cart = null;
        Object o = session.getAttribute("cart");

        if (o != null) {
            cart = (Cart) o;
        } else {
            cart = new Cart();
        }

        // 3. Lấy mã sản phẩm khách muốn mua
        String pid = request.getParameter("pid");
        int quantity = 1; // Mặc định mỗi lần bấm là thêm 1 sản phẩm

        try {
            ProductDAO dao = new ProductDAO();
            Product p = dao.getProductByID(pid);

            // 4. Kiểm tra nếu sản phẩm tồn tại thì mới cho vào giỏ
            if (p != null) {
                double price = p.getPrice();
                Item t = new Item(p, quantity, price);
                cart.addItem(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5. Cập nhật lại giỏ hàng vào Session
        session.setAttribute("cart", cart);

        // 6. Chuyển hướng về trang hiển thị giỏ hàng (sẽ làm ở bước sau)
        // Hiện tại bạn có thể chuyển hướng về trang chủ "home" để test không bị lỗi 404
        response.sendRedirect("cart.jsp");
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