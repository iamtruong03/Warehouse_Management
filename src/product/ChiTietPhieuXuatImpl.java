package product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import object.ChiTietPhieu;
import util.ConnectionPoolImpl;

public class ChiTietPhieuXuatImpl implements ImplInterface<ChiTietPhieu> {

    private ConnectionPoolImpl connectionPool;

    public ChiTietPhieuXuatImpl() {
        this.connectionPool = new ConnectionPoolImpl();
    }

    public static ChiTietPhieuXuatImpl getInstance() {
        return new ChiTietPhieuXuatImpl();
    }

    @Override
    public int insert(ChiTietPhieu t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = connectionPool.getConnection("insert");
            String sql = "INSERT INTO ChiTietPhieuXuat (maPhieu, maMay, soLuong, donGia) VALUES (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setString(2, t.getMaMay());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
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
    public int update(ChiTietPhieu t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = connectionPool.getConnection("update");
            String sql = "UPDATE ChiTietPhieuXuat SET maPhieu=?, maMay=?, soLuong=?, donGia=? WHERE maPhieu=? AND maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setString(2, t.getMaMay());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            pst.setString(5, t.getMaPhieu());
            pst.setString(6, t.getMaMay());
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
    public int delete(ChiTietPhieu t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = connectionPool.getConnection("delete");
            String sql = "DELETE FROM ChiTietPhieuXuat WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
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

    public ArrayList<ChiTietPhieu> selectAll(String t) {
        ArrayList<ChiTietPhieu> ketQua = new ArrayList<ChiTietPhieu>();
        Connection con = null;
        try {
            con = connectionPool.getConnection("selectAll");
            String sql = "SELECT * FROM ChiTietPhieuXuat WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                String maMay = rs.getString("maMay");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                ChiTietPhieu ctp = new ChiTietPhieu(maPhieu, maMay, soLuong, donGia);
                ketQua.add(ctp);
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
    public ArrayList<ChiTietPhieu> selectAll() {
        ArrayList<ChiTietPhieu> ketQua = new ArrayList<ChiTietPhieu>();
        Connection con = null;
        try {
            con = connectionPool.getConnection("selectAll");
            String sql = "SELECT * FROM ChiTietPhieuXuat";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                String maMay = rs.getString("maMay");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                ChiTietPhieu ctp = new ChiTietPhieu(maPhieu, maMay, soLuong, donGia);
                ketQua.add(ctp);
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
    public ChiTietPhieu selectById(String t) {
        ChiTietPhieu ketQua = null;
        Connection con = null;
        try {
            con = connectionPool.getConnection("selectById");
            String sql = "SELECT * FROM ChiTietPhieuXuat WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                String maMay = rs.getString("maMay");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                ketQua = new ChiTietPhieu(maPhieu, maMay, soLuong, donGia);
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
        return ketQua;
    }
}
