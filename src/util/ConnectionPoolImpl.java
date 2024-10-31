package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPoolImpl implements ConnectionPool {

    // Các thuộc tính cần thiết cho kết nối cơ sở dữ liệu
    public String driver;
    public String username;
    public String password;
    public String url;

    // Stack để quản lý các kết nối hiện có
    private Stack<Connection> pool;

    // Hàm khởi tạo
    public ConnectionPoolImpl() {
        // Khai báo driver MySQL
        this.driver = "com.mysql.cj.jdbc.Driver";
        try {
            // Load driver MySQL
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Thông tin đăng nhập và URL của cơ sở dữ liệu
        this.username = "root";
        this.password = "123456789@";
        this.url = "jdbc:mysql://localhost:3306/khohangmaytinh?allowMultiSelectQueries=true";
        // Khởi tạo stack để quản lý các kết nối
        this.pool = new Stack<>();
    }

    // Lấy một kết nối từ pool
    public Connection getConnection(String objectName) throws SQLException {
        // Nếu pool rỗng, tạo một kết nối mới
        if (this.pool.isEmpty()) {
//            System.out.println(objectName + " đã khởi tạo kết nối mới");
            return DriverManager.getConnection(this.url, this.username, this.password);
        } else {
            // Nếu có sẵn kết nối, lấy kết nối từ pool
//            System.out.println(objectName + " đã lấy ra một kết nối");
            return this.pool.pop();
        }
    }

    // Trả lại kết nối vào pool
    @Override
    public void releaseConnection(Connection con, String objectName) throws SQLException {
//        System.out.println(objectName + " đã thu hồi một kết nối");
        this.pool.push(con);
    }

    // Hàm finalize để dọn dẹp khi đối tượng bị hủy
    @Override
    protected void finalize() throws Throwable {
        // Xóa toàn bộ kết nối trong pool
        this.pool.clear();
        this.pool = null;
        System.out.println("Connection Pool đã được đóng!");
    }
}
