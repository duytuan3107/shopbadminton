package filter;

import entity.Account;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Cấu hình lọc tất cả các URL bắt đầu bằng admin- (ví dụ: admin-manager, admin-edit)
@WebFilter(urlPatterns = {"/admin-*", "/loadProduct", "/delete"})
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        Account a = (Account) session.getAttribute("acc");

        if (a != null && a.getIsAdmin() == 1) {
            // Là admin -> Cho qua cửa
            chain.doFilter(request, response);
        } else {
            // Không phải admin -> Chặn lại và đẩy ra trang Login
            res.sendRedirect("Login.jsp");
        }
    }
}