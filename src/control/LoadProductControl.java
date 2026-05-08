package control;

import dao.ProductDAO;
import entity.Category;
import entity.Product;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoadProductControl", urlPatterns = {"/loadProduct"})
public class LoadProductControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("pid"); // Lấy id từ nút Edit trên ManagerProduct.jsp
        ProductDAO dao = new ProductDAO();
        Product p = dao.getProductByID(id);
        List<Category> listC = dao.getAllCategory();

        request.setAttribute("detail", p);
        request.setAttribute("listCC", listC);
        request.getRequestDispatcher("Edit.jsp").forward(request, response);
    }
}