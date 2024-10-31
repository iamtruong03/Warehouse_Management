package product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import object.Account;
import util.ConnectionPoolImpl;

public class AccountImpl implements ImplInterface<Account> {

    private ConnectionPoolImpl connectionPool;

    public AccountImpl() {
        this.connectionPool = new ConnectionPoolImpl();
    }

    public static AccountImpl getInstance() {
        return new AccountImpl();
    }

    @Override
    public int insert(Account t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = connectionPool.getConnection("insert");
            String sql = "INSERT INTO Account (fullName, userName, password, role, status, email) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getFullName());
            pst.setString(2, t.getUser());
            pst.setString(3, t.getPassword());
            pst.setString(4, t.getRole());
            pst.setInt(5, t.getStatus());
            pst.setString(6, t.getEmail());
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    connectionPool.releaseConnection(con, "insert");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ketQua;
    }

    @Override
    public int update(Account t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = connectionPool.getConnection("update");
            String sql = "UPDATE Account SET fullName=?, password=?, role=?, status=?, email=? WHERE userName=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getFullName());
            pst.setString(2, t.getPassword());
            pst.setString(3, t.getRole());
            pst.setInt(4, t.getStatus());
            pst.setString(5, t.getEmail());
            pst.setString(6, t.getUser());
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    connectionPool.releaseConnection(con, "update");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ketQua;
    }

    @Override
    public int delete(Account t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = connectionPool.getConnection("delete");
            String sql = "DELETE FROM Account WHERE userName=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getUser());
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    connectionPool.releaseConnection(con, "delete");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ketQua;
    }

    @Override
    public ArrayList<Account> selectAll() {
        ArrayList<Account> ketQua = new ArrayList<Account>();
        Connection con = null;
        try {
            con = connectionPool.getConnection("selectAll");
            String sql = "SELECT * FROM Account";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String fullName = rs.getString("fullName");
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                String role = rs.getString("role");
                int status = rs.getInt("status");
                String email = rs.getString("email");
                Account acc = new Account(fullName, userName, password, role, status, email);
                ketQua.add(acc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    connectionPool.releaseConnection(con, "selectAll");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ketQua;
    }

    @Override
    public Account selectById(String t) {
        Account acc = null;
        Connection con = null;
        try {
            con = connectionPool.getConnection("selectById");
            String sql = "SELECT * FROM Account WHERE userName=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String fullName = rs.getString("fullName");
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                String role = rs.getString("role");
                int status = rs.getInt("status");
                String email = rs.getString("email");
                acc = new Account(fullName, userName, password, role, status, email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    connectionPool.releaseConnection(con, "selectById");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return acc;
    }

    public int updatePassword(String email, String password) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = connectionPool.getConnection("updatePassword");
            String sql = "UPDATE Account SET password=? WHERE email=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, password);
            pst.setString(2, email);
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    connectionPool.releaseConnection(con, "updatePassword");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ketQua;
    }
}
