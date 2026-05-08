package control;

import dao.ProductDAO; // Đổi sang ProductDAO nếu bạn gộp hàm login vào đó
import entity.Account;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginControl", urlPatterns = {"/login"})
public class LoginControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Đảm bảo đọc dữ liệu từ form không bị lỗi font
        request.setCharacterEncoding("UTF-8");

        String user = request.getParameter("user");
        String pass = request.getParameter("pass");

        // Nếu bạn để hàm login trong ProductDAO thì sửa chỗ này
        ProductDAO dao = new ProductDAO();
        Account a = dao.login(user, pass);

        if (a == null) {
            // Đăng nhập thất bại: Gửi thông báo lỗi về trang Login
            request.setAttribute("mess", "Tài khoản hoặc mật khẩu không chính xác!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            // Đăng nhập thành công: Lưu đối tượng Account vào Session
            HttpSession session = request.getSession();
            session.setAttribute("acc", a);

            // Set thời gian sống của session (ví dụ: 30 phút)
            session.setMaxInactiveInterval(1800);

            // Phân quyền hướng đi
            if (a.getIsAdmin() == 1) {
                // Nếu là Admin thì vào thẳng trang quản lý của DT Badminton
                response.sendRedirect("admin-manager");
            } else {
                // Khách thường thì về trang chủ xem vợt
                response.sendRedirect("home");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nếu khách gõ trực tiếp /login trên trình duyệt thì hiện trang Login.jsp
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}