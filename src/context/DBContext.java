package context;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
    public Connection getConnection() throws Exception {
        // Lấy các thông số từ Railway (Tự động đọc từ tab Variables bạn vừa xem)
        String host = System.getenv("MYSQLHOST");
        String port = System.getenv("MYSQLPORT");
        String dbName = System.getenv("MYSQLDATABASE");
        String user = System.getenv("MYSQLUSER");
        String password = System.getenv("MYSQLPASSWORD");

        // Nếu chạy dưới máy (localhost), các biến trên sẽ trống, ta dùng thông số cũ của bạn
        if (host == null) {
            host = "localhost";
            port = "3308"; // Port cũ dưới máy bạn
            dbName = "shop_cau_long";
            user = "root";
            password = "";
        }

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSL=false&allowPublicKeyRetrieval=true";

        // Dùng Driver mới nhất (hỗ trợ cả bản cũ và mới)
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(url, user, password);
    }
}