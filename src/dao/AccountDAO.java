package dao;

import context.DBContext;
import entity.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public Account login(String user, String pass) {
        // Dùng hàm TRIM của SQL để cắt khoảng trắng trong Database
        String query = "SELECT * FROM accounts WHERE TRIM(user) = ? AND TRIM(pass) = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            // Dùng thêm .trim() của Java để cắt khoảng trắng từ Form nhập vào
            ps.setString(1, user.trim());
            ps.setString(2, pass.trim());
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Account(
                        rs.getInt(1),
                        rs.getString(2).trim(),
                        rs.getString(3).trim(),
                        rs.getInt(4));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Quan trọng: Xem lỗi ở Console nếu vẫn hỏng
        }
        return null;
    }
}