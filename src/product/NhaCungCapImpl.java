package product;

import object.NhaCungCap;
import util.ConnectionPoolImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class NhaCungCapImpl implements ImplInterface<NhaCungCap> {

    private static final ConnectionPoolImpl connectionPool = new ConnectionPoolImpl();

    public static NhaCungCapImpl getInstance() {
        return new NhaCungCapImpl();
    }

    @Override
    public int insert(NhaCungCap t) {
        int ketQua = 0;
        try {
            Connection con = connectionPool.getConnection("NhaCungCapImpl");
            String sql = "INSERT INTO NhaCungCap (maNhaCungCap, tenNhaCungCap, Sdt, diaChi) VALUES (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaNhaCungCap());
            pst.setString(2, t.getTenNhaCungCap());
            pst.setString(3, t.getSdt());
            pst.setString(4, t.getDiaChi());
            ketQua = pst.executeUpdate();
            connectionPool.releaseConnection(con, "NhaCungCapImpl");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không thêm được nhà cung cấp " + t.getMaNhaCungCap(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(NhaCungCap t) {
        int ketQua = 0;
        try {
            Connection con = connectionPool.getConnection("NhaCungCapImpl");
            String sql = "UPDATE NhaCungCap SET maNhaCungCap=?, tenNhaCungCap=?, Sdt=?, diaChi=? WHERE maNhaCungCap=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaNhaCungCap());
            pst.setString(2, t.getTenNhaCungCap());
            pst.setString(3, t.getSdt());
            pst.setString(4, t.getDiaChi());
            pst.setString(5, t.getMaNhaCungCap());
            ketQua = pst.executeUpdate();
            connectionPool.releaseConnection(con, "NhaCungCapImpl");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(NhaCungCap t) {
        int ketQua = 0;
        try {
            Connection con = connectionPool.getConnection("NhaCungCapImpl");
            String sql = "DELETE FROM NhaCungCap WHERE maNhaCungCap=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaNhaCungCap());
            ketQua = pst.executeUpdate();
            connectionPool.releaseConnection(con, "NhaCungCapImpl");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<NhaCungCap> selectAll() {
        ArrayList<NhaCungCap> ketQua = new ArrayList<>();
        try {
            Connection con = connectionPool.getConnection("NhaCungCapImpl");
            String sql = "SELECT * FROM NhaCungCap";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maNhaCungCap = rs.getString("maNhaCungCap");
                String tenNhaCungCap = rs.getString("tenNhaCungCap");
                String sdt = rs.getString("Sdt");
                String diaChi = rs.getString("diaChi");
                NhaCungCap ncc = new NhaCungCap(maNhaCungCap, tenNhaCungCap, sdt, diaChi);
                ketQua.add(ncc);
            }
            connectionPool.releaseConnection(con, "NhaCungCapImpl");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public NhaCungCap selectById(String t) {
        NhaCungCap ketQua = null;
        try {
            Connection con = connectionPool.getConnection("NhaCungCapImpl");
            String sql = "SELECT * FROM NhaCungCap WHERE maNhaCungCap=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String maNhaCungCap = rs.getString("maNhaCungCap");
                String tenNhaCungCap = rs.getString("tenNhaCungCap");
                String sdt = rs.getString("Sdt");
                String diaChi = rs.getString("diaChi");
                ketQua = new NhaCungCap(maNhaCungCap, tenNhaCungCap, sdt, diaChi);
            }
            connectionPool.releaseConnection(con, "NhaCungCapImpl");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }
}
