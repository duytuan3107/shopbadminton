package entity;

public class Account {
    private int uID; // Đổi thành uID cho khớp với database
    private String user;
    private String pass;
    private int isAdmin;

    // Constructor mặc định
    public Account() {
    }

    // Constructor đầy đủ tham số
    public Account(int uID, String user, String pass, int isAdmin) {
        this.uID = uID;
        this.user = user;
        this.pass = pass;
        this.isAdmin = isAdmin;
    }

    // --- Bắt đầu phần Getter và Setter ---

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    // Ghi đè phương thức toString để dễ dàng debug/kiểm tra dữ liệu
    @Override
    public String toString() {
        return "Account{" + "uID=" + uID + ", user=" + user + ", isAdmin=" + isAdmin + '}';
    }
}