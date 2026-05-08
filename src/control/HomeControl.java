package control;

import dao.ProductDAO;
import entity.Product;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HomeControl", urlPatterns = {"/home"})
public class HomeControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy dữ liệu từ Database (cổng 3308)
        ProductDAO dao = new ProductDAO();
        List<Product> list = dao.getTop9Product();

// 2. Đưa đúng cái list 9 sản phẩm này lên JSP
        request.setAttribute("listP", list);

// 3. Chuyển hướng sang index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

}
