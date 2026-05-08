package dao;
import entity.Account;
import entity.Category; // Thay 'entity' bằng tên package chứa class Category của bạn
import context.DBContext;
import entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import entity.Cart;
import entity.Item;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.util.Map;

public class ProductDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Product> getAllProduct() {
        List<Product> list = new ArrayList<>();
        // Câu lệnh SQL lấy tất cả sản phẩm từ bảng products
        String query = "select * from products";
        try {
            conn = new DBContext().getConnection(); // Kết nối tới DB cổng 3308
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                // Thứ tự rs.get dựa trên các cột trong bảng products của bạn
                list.add(new Product(
                        rs.getInt("id"),      // id
                        rs.getString("NAME"),   // name
                        rs.getDouble("price"),   // price
                        rs.getString("image"),   // image
                        rs.getString("description"),
                        rs.getString("brand"),
                        rs.getInt("category_id")// description

                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Product> getTop9Product() {
        List<Product> list = new ArrayList<>();
        // Thêm LIMIT 4 để chỉ lấy đúng 4 sản phẩm mới nhất (dựa vào ID giảm dần)
        String query = "select * from products order by id desc limit 9";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("NAME"),
                        rs.getDouble("price"),
                        rs.getString("image"),
                        rs.getString("description"),
                        rs.getString("brand"),
                        rs.getInt("category_id")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list ;
    }
    //lay 1 san pham bat ki
    public Product getProductByID(String id) {
        String query = "select * from products where id = ?";
        try {
            conn = new context.DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, id); // Truyền id vào dấu hỏi chấm
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("NAME"),
                        rs.getDouble("price"),
                        rs.getString("image"),
                        rs.getString("description"),
                        rs.getString("brand"),
                        rs.getInt("category_id")

                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Product> getProductByCID(String cid) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM products WHERE category_id = ?";
        try {
            conn = new context.DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, cid);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("NAME"),
                        rs.getDouble("price"),
                        rs.getString("image"),
                        rs.getString("description"),
                        rs.getString("brand"),
                        rs.getInt("category_id")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Product> searchByName(String txtSearch) {
        List<Product> list = new ArrayList<>();
        // Tìm kiếm sản phẩm mà tên có chứa từ khóa txtSearch
        String query = "SELECT * FROM products WHERE NAME LIKE ?";
        try {
            conn = new context.DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + txtSearch + "%"); // Dấu % giúp tìm kiếm tương đối
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("NAME"),
                        rs.getDouble("price"),
                        rs.getString("image"),
                        rs.getString("description"),
                        rs.getString("brand"),
                        rs.getInt("category_id")

                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public void deleteProduct(String id) {
        String query = "DELETE FROM products WHERE id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Sửa lại hàm insertProduct cho đúng với các cột trong ảnh image_afa58e.png
    public void insertProduct(String name, String image, String price, String description, String category, String brand) {
        // Sửa lại: Bỏ dấu ngoặc vuông [], dùng đúng tên cột như trong ảnh database
        String query = "INSERT INTO products (NAME, image, price, description, category_id, brand) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, image);
            ps.setString(3, price);
            ps.setString(4, description);
            ps.setString(5, category);
            ps.setString(6, brand);

            int check = ps.executeUpdate(); // Trả về số dòng bị ảnh hưởng
            if(check > 0) {
                System.out.println("DEBUG: Them san pham thanh cong!");
            }
        } catch (Exception e) {
            System.out.println("DEBUG LOI INSERT: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        // Đảm bảo tên bảng này tồn tại trong SQL của bạn
        String query = "SELECT * FROM Category";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {

                list.add(new Category(rs.getInt("cid"), rs.getString("cname")));
            }
            // Thêm dòng này để kiểm tra ở Console khi chạy:
            System.out.println("DEBUG: Da lay duoc " + list.size() + " danh muc");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public void editProduct(String name, String image, String price, String description, String category, String brand, String id) {
        String query = "UPDATE products SET NAME = ?, image = ?, price = ?, description = ?, category_id = ?, brand = ? WHERE id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, image);
            ps.setString(3, price);
            ps.setString(4, description);
            ps.setString(5, category);
            ps.setString(6, brand);
            ps.setString(7, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Account login(String user, String pass) {
        String query = "SELECT * FROM accounts WHERE user = ? AND pass = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("uID"),
                        rs.getString("user"),
                        rs.getString("pass"),
                        rs.getInt("isAdmin")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void addOrder(Account a, Cart cart) {
        LocalDate curDate = LocalDate.now();
        String date = curDate.toString();
        try {
            // 1. Lưu vào bảng orders
            String query = "INSERT INTO orders (uID, date, total_money) VALUES (?, ?, ?)";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); // Lấy ID vừa tạo
            ps.setInt(1, a.getuID());
            ps.setString(2, date);
            ps.setDouble(3, cart.getTotalMoney());
            ps.executeUpdate();

            // Lấy id của order vừa insert
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int oid = rs.getInt(1);
                // 2. Lưu vào bảng order_detail cho từng món hàng
                for (Item i : cart.getItems()) {
                    String query2 = "INSERT INTO order_detail (oid, pid, quantity, price) VALUES (?, ?, ?, ?)";
                    ps = conn.prepareStatement(query2);
                    ps.setInt(1, oid);
                    ps.setInt(2, i.getProduct().getId());
                    ps.setInt(3, i.getQuantity());
                    ps.setDouble(4, i.getPrice());
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int insertOrder(String name, String phone, String address, String detail, String total) {
        // Thêm Statement.RETURN_GENERATED_KEYS để lấy ID tự tăng
        String query = "INSERT INTO orders (customer_name, phone, address, cart_detail, total_money, order_date) VALUES (?, ?, ?, ?, ?, NOW())";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setString(4, detail);
            ps.setString(5, total);
            ps.executeUpdate();

            // Lấy ID vừa tự động tạo ra
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Trả về ID của đơn hàng
            }
        } catch (Exception e) {
            System.out.println("Lỗi lấy ID Order: " + e.getMessage());
        }
        return -1; // Thất bại
    }


    public void insertOrderDetail(int orderId, String detail) {
        // Câu lệnh SQL để chèn vào bảng order_detail
        // Lưu ý: Tôi dùng cột 'price' để chứa tạm chuỗi detail như logic nãy giờ mình làm
        String query = "INSERT INTO order_detail (order_id, price) VALUES (?, ?)";

        try {
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, orderId);
            ps.setString(2, detail);

            ps.executeUpdate(); // Chạy lệnh lưu vào bảng order_detail

            ps.close();
            conn.close();
            System.out.println("=> Đã lưu thành công vào bảng order_detail!");
        } catch (Exception e) {
            System.out.println("Lỗi tại insertOrderDetail: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Map<String, String>> getAllOrders() {
        List<Map<String, String>> list = new ArrayList<>();
        String query = "SELECT * FROM orders ORDER BY id DESC"; // Đảm bảo bảng của bạn tên là 'orders'
        try {
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> order = new HashMap<>();
                order.put("id", rs.getString("id"));
                order.put("name", rs.getString("customer_name"));
                order.put("phone", rs.getString("phone"));
                order.put("address", rs.getString("address"));
                order.put("detail", rs.getString("cart_detail"));
                order.put("total", rs.getString("total_money"));
                order.put("date", rs.getString("order_date"));
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateOrderStatus(int orderId, int status) {
        String query = "UPDATE orders SET status = ? WHERE id = ?";
        try {
            Connection conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}