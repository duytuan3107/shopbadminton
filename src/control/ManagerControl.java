package control;
import entity.Category; // Thay 'entity' bằng tên package chứa class Category của bạn
import dao.ProductDAO;
import entity.Product;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ManagerControl", urlPatterns = {"/admin-manager"})
public class ManagerControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ProductDAO dao = new ProductDAO();

        // Lấy tham số 'mode' từ URL để biết đang xem cái gì
        String mode = request.getParameter("mode");
        if (mode == null) mode = "product"; // Mặc định là sản phẩm

        if (mode.equals("order")) {
            // --- CHẾ ĐỘ XEM ĐƠN HÀNG ---
            // Gọi hàm getAllOrders mà mình đã làm ở các bước trước
            List<Map<String, String>> listO = dao.getAllOrders();
            request.setAttribute("listO", listO);
        } else {
            // --- CHẾ ĐỘ XEM SẢN PHẨM ---
            List<Product> list = dao.getAllProduct();
            List<Category> listC = dao.getAllCategory();
            request.setAttribute("listP", list);
            request.setAttribute("listCC", listC);
        }

        request.setAttribute("currentMode", mode); // Gửi về để JSP biết đang ở tab nào
        request.getRequestDispatcher("ManagerProduct.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. Lấy session hiện tại
        HttpSession session = request.getSession();

        // 2. Lấy đối tượng tài khoản đã lưu
        entity.Account a = (entity.Account) session.getAttribute("acc");

        // 3. Kiểm tra điều kiện: Phải đăng nhập VÀ phải là Admin
        if (a == null || a.getIsAdmin() != 1) {
            request.setAttribute("mess", "Bạn cần đăng nhập quyền Admin để vào đây!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            // QUAN TRỌNG: Nếu là Admin, phải gọi hàm xử lý dữ liệu ở đây
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Tương tự cho doPost nếu cần
        HttpSession session = request.getSession();
        entity.Account a = (entity.Account) session.getAttribute("acc");

        if (a == null || a.getIsAdmin() != 1) {
            request.setAttribute("mess", "Bạn cần đăng nhập quyền Admin để vào đây!");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        } else {
            processRequest(request, response);
        }
    }
}