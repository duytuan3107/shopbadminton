package dao;

import context.DBContext;
import entity.Cart;
import entity.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class OrderDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void addOrder(String name, String phone, String address, Cart cart) {
        LocalDate curDate = LocalDate.now();
        try {
            conn = new DBContext().getConnection();

            // 1. Lưu vào bảng orders
            String sql1 = "INSERT INTO orders (customer_name, address, phone, total_money, order_date) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS); // Lấy ID vừa tự tăng
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, phone);
            ps.setDouble(4, cart.getTotalMoney());
            ps.setString(5, curDate.toString());
            ps.executeUpdate();

            // Lấy id của đơn hàng vừa insert
            rs = ps.getGeneratedKeys();
            int oid = 0;
            if (rs.next()) {
                oid = rs.getInt(1);
            }

            // 2. Lưu vào bảng order_detail
            String sql2 = "INSERT INTO order_detail (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
            for (Item i : cart.getItems()) {
                ps = conn.prepareStatement(sql2);
                ps.setInt(1, oid);
                ps.setInt(2, i.getProduct().getId());
                ps.setInt(3, i.getQuantity());
                ps.setDouble(4, i.getPrice());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}