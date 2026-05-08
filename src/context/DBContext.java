package context;
import java.sql.Connection;
import java.sql.DriverManager;
public class DBContext {
    public Connection getConnection()throws Exception {
        String url = "jdbc:mysql://localhost:3308/shop_cau_long";
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, "root", "");
    }
}
