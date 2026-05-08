package control;

import dao.ProductDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EditControl", urlPatterns = {"/edit"})
public class EditControl extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Để không lỗi font tiếng Việt

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String image = request.getParameter("image");
        String price = request.getParameter("price");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String brand = request.getParameter("brand");

        ProductDAO dao = new ProductDAO();
        dao.editProduct(name, image, price, description, category, brand, id);
        response.sendRedirect("admin-manager"); // Quay về link Servlet quản lý
    }
}