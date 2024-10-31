package product;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import object.PhieuXuat;
import util.ConnectionPoolImpl;

public class PhieuXuatImpl implements ImplInterface<PhieuXuat> {

    private ConnectionPoolImpl connectionPool;

    public PhieuXuatImpl() {
        this.connectionPool = new ConnectionPoolImpl();
    }

    public static PhieuXuatImpl getInstance() {
        return new PhieuXuatImpl();
    }

    @Override
    public int insert(PhieuXuat t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = connectionPool.getConnection("PhieuXuatImpl.insert");
            String sql = "INSERT INTO PhieuXuat (maPhieu, thoiGianTao, nguoiTao, tongTien) VALUES (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setTimestamp(2, t.getThoiGianTao());
            pst.setString(3, t.getNguoiTao());
            pst.setDouble(4, t.getTongTien());
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    connectionPool.releaseConnection(con, "PhieuXuatImpl.insert");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ketQua;
    }

    @Override
    public int update(PhieuXuat t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = connectionPool.getConnection("PhieuXuatImpl.update");
            String sql = "UPDATE PhieuXuat SET maPhieu=?, thoiGianTao=?, nguoiTao=?, tongTien = ? WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setTimestamp(2, t.getThoiGianTao());
            pst.setString(3, t.getNguoiTao());
            pst.setDouble(4, t.getTongTien());
            pst.setString(5, t.getMaPhieu());
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    connectionPool.releaseConnection(con, "PhieuXuatImpl.update");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ketQua;
    }

    @Override
    public int delete(PhieuXuat t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = connectionPool.getConnection("PhieuXuatImpl.delete");
            String sql = "DELETE FROM PhieuXuat WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    connectionPool.releaseConnection(con, "PhieuXuatImpl.delete");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ketQua;
    }

    @Override
    public ArrayList<PhieuXuat> selectAll() {
        ArrayList<PhieuXuat> ketQua = new ArrayList<PhieuXuat>();
        Connection con = null;
        try {
            con = connectionPool.getConnection("PhieuXuatImpl.selectAll");
            String sql = "SELECT * FROM PhieuXuat ORDER BY thoiGianTao DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                Timestamp thoiGianTao = rs.getTimestamp("thoiGianTao");
                String nguoiTao = rs.getString("nguoiTao");
                double tongTien = rs.getDouble("tongTien");
                PhieuXuat p = new PhieuXuat(maPhieu, thoiGianTao, nguoiTao, ChiTietPhieuXuatImpl.getInstance().selectAll(maPhieu), tongTien);
                ketQua.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    connectionPool.releaseConnection(con, "PhieuXuatImpl.selectAll");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ketQua;
    }

    @Override
    public PhieuXuat selectById(String t) {
        PhieuXuat ketQua = null;
        Connection con = null;
        try {
            con = connectionPool.getConnection("PhieuXuatImpl.selectById");
            String sql = "SELECT * FROM PhieuXuat WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                Timestamp thoiGianTao = rs.getTimestamp("thoiGianTao");
                String nguoiTao = rs.getString("nguoiTao");
                double tongTien = rs.getDouble("tongTien");
                ketQua = new PhieuXuat(maPhieu, thoiGianTao, nguoiTao, ChiTietPhieuXuatImpl.getInstance().selectAll(maPhieu), tongTien);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    connectionPool.releaseConnection(con, "PhieuXuatImpl.selectById");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ketQua;
    }
}
