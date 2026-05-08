package control;

import dao.ProductDAO;
import entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name  = "DetailControl", urlPatterns = {"/detail"})
public class DetailControl extends HttpServlet {
    protected void doGet(HttpServletRequest request , HttpServletResponse response)
        throws ServletException, IOException{

             //Lấy pid (product id) từ URL khi người dùng nhấn vào sản phẩm
             String id = request.getParameter("pid");

        ProductDAO dao = new ProductDAO();
        Product p = dao.getProductByID(id);

        request.setAttribute("detail", p);

        request.getRequestDispatcher("detail.jsp").forward(request, response);
    }

}
