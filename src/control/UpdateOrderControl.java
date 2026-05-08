package control;

import dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateOrderControl", urlPatterns = {"/update-order"})
public class UpdateOrderControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int status = Integer.parseInt(request.getParameter("status"));

        new ProductDAO().updateOrderStatus(id, status);

        // Cập nhật xong thì quay lại trang quản lý đơn hàng
        response.sendRedirect("admin-manager?mode=order");
    }
}