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

/**
 * Servlet xử lý việc hiển thị sản phẩm theo danh mục (Category)
 */
@WebServlet(name = "CategoryControl", urlPatterns = {"/category"})
public class CategoryControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // 1. Lấy mã danh mục (cid) từ link trên Menu gửi về
        String cateID = request.getParameter("cid");

        // 2. Gọi DAO để thực hiện truy vấn
        ProductDAO dao = new ProductDAO();
        List<Product> list = dao.getProductByCID(cateID);

        // 3. Đẩy danh sách sản phẩm tìm được lên request
        // Lưu ý: Đặt tên là "listP" để trùng với tên biến ở trang index.jsp
        request.setAttribute("listP", list);

        // 4. Trả về trang index.jsp để hiển thị kết quả lọc
        request.getRequestDispatcher("index.jsp").forward(request, response);
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