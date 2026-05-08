package control;

// Import các class từ project của bạn
import dao.ProductDAO;

// Import các thư viện chuẩn của Java
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddControl", urlPatterns = {"/add"})
public class AddControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thông thường chúng ta không dùng GET để thêm dữ liệu,
        // nhưng nếu lỡ tay gọi vào thì cho nó quay về trang quản lý
        response.sendRedirect("admin-manager");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thiết lập tiếng Việt để không bị lỗi font khi thêm sản phẩm
        request.setCharacterEncoding("UTF-8");

        // 1. Lấy dữ liệu từ các ô Input trong Form (ManagerProduct.jsp)
        String name = request.getParameter("name");
        String image = request.getParameter("image");
        String price = request.getParameter("price");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String brand = request.getParameter("brand");

        // 2. Gọi ProductDAO để thực hiện câu lệnh INSERT vào Database
        ProductDAO dao = new ProductDAO();
        dao.insertProduct(name, image, price, description, category,brand);

        // 3. Sau khi thêm thành công, chuyển hướng về trang danh sách để thấy sản phẩm mới
        response.sendRedirect("admin-manager");
    }
}