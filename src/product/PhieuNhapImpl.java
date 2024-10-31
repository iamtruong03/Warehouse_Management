package product;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import object.Phieu;
import object.PhieuNhap;
import util.ConnectionPoolImpl;

public class PhieuNhapImpl implements ImplInterface<PhieuNhap> {

    private static final ConnectionPoolImpl connectionPool = new ConnectionPoolImpl();

    public static PhieuNhapImpl getInstance() {
        return new PhieuNhapImpl();
    }

    @Override
    public int insert(PhieuNhap t) {
        int ketQua = 0;
        try {
            Connection con = connectionPool.getConnection("PhieuNhapImpl");
            String sql = "INSERT INTO PhieuNhap (maPhieu, thoiGianTao, nguoiTao, maNhaCungCap, tongTien) VALUES (?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setTimestamp(2, t.getThoiGianTao());
            pst.setString(3, t.getNguoiTao());
            pst.setString(4, t.getNhaCungCap());
            pst.setDouble(5, t.getTongTien());
            ketQua = pst.executeUpdate();
            connectionPool.releaseConnection(con, "PhieuNhapImpl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(PhieuNhap t) {
        int ketQua = 0;
        try {
            Connection con = connectionPool.getConnection("PhieuNhapImpl");
            String sql = "UPDATE PhieuNhap SET maPhieu=?, thoiGianTao=?, nguoiTao=?, maNhaCungCap=?, tongTien = ? WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setTimestamp(2, t.getThoiGianTao());
            pst.setString(3, t.getNguoiTao());
            pst.setString(4, t.getNhaCungCap());
            pst.setDouble(5, t.getTongTien());
            pst.setString(6, t.getMaPhieu());
            ketQua = pst.executeUpdate();
            connectionPool.releaseConnection(con, "PhieuNhapImpl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(PhieuNhap t) {
        int ketQua = 0;
        try {
            Connection con = connectionPool.getConnection("PhieuNhapImpl");
            String sql = "DELETE FROM PhieuNhap WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            ketQua = pst.executeUpdate();
            connectionPool.releaseConnection(con, "PhieuNhapImpl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<PhieuNhap> selectAll() {
        ArrayList<PhieuNhap> ketQua = new ArrayList<>();
        try {
            Connection con = connectionPool.getConnection("PhieuNhapImpl");
            String sql = "SELECT * FROM PhieuNhap ORDER BY thoiGianTao DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                Timestamp thoiGianTao = rs.getTimestamp("thoiGianTao");
                String nguoiTao = rs.getString("nguoiTao");
                String maNhaCungCap = rs.getString("maNhaCungCap");
                double tongTien = rs.getDouble("tongTien");
                PhieuNhap p = new PhieuNhap(maNhaCungCap, maPhieu, thoiGianTao, nguoiTao, ChiTietPhieuNhapImpl.getInstance().selectAll(maPhieu), tongTien);
                ketQua.add(p);
            }
            connectionPool.releaseConnection(con, "PhieuNhapImpl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public PhieuNhap selectById(String t) {
        PhieuNhap ketQua = null;
        try {
            Connection con = connectionPool.getConnection("PhieuNhapImpl");
            String sql = "SELECT * FROM PhieuNhap WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                Timestamp thoiGianTao = rs.getTimestamp("thoiGianTao");
                String nguoiTao = rs.getString("nguoiTao");
                String maNhaCungCap = rs.getString("maNhaCungCap");
                double tongTien = rs.getDouble("tongTien");
                ketQua = new PhieuNhap(maNhaCungCap, maPhieu, thoiGianTao, nguoiTao, ChiTietPhieuNhapImpl.getInstance().selectAll(maPhieu), tongTien);
            }
            connectionPool.releaseConnection(con, "PhieuNhapImpl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<Phieu> selectAllAccount(String acc) {
        ArrayList<Phieu> ketQua = new ArrayList<>();
        try {
            Connection con = connectionPool.getConnection("PhieuNhapImpl");
            String sql = "SELECT maPhieu, thoiGianTao, nguoiTao, tongTien FROM PhieuNhap WHERE nguoiTao = ? UNION SELECT maPhieu, thoiGianTao, nguoiTao, tongTien FROM PhieuXuat WHERE nguoiTao = ? ORDER BY thoiGianTao DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, acc);
            pst.setString(2, acc);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                Timestamp thoiGianTao = rs.getTimestamp("thoiGianTao");
                String nguoiTao = rs.getString("nguoiTao");
                double tongTien = rs.getDouble("tongTien");
                Phieu p = new Phieu(maPhieu, thoiGianTao, nguoiTao, ChiTietPhieuNhapImpl.getInstance().selectAll(maPhieu), tongTien);
                ketQua.add(p);
            }
            connectionPool.releaseConnection(con, "PhieuNhapImpl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<Phieu> selectAllP() {
        ArrayList<Phieu> ketQua = new ArrayList<>();
        try {
            Connection con = connectionPool.getConnection("PhieuNhapImpl");
            String sql = "SELECT maPhieu, thoiGianTao, nguoiTao, tongTien FROM PhieuNhap UNION SELECT maPhieu, thoiGianTao, nguoiTao, tongTien FROM PhieuXuat ORDER BY thoiGianTao DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                Timestamp thoiGianTao = rs.getTimestamp("thoiGianTao");
                String nguoiTao = rs.getString("nguoiTao");
                double tongTien = rs.getDouble("tongTien");
                Phieu p = new Phieu(maPhieu, thoiGianTao, nguoiTao, ChiTietPhieuNhapImpl.getInstance().selectAll(maPhieu), tongTien);
                ketQua.add(p);
            }
            connectionPool.releaseConnection(con, "PhieuNhapImpl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    
 // Phương thức này trả về một Map chứa số lượng phiếu nhập theo từng người tạo
    public Map<String, Integer> getPhieuCountByNguoiTao() {
        Map<String, Integer> result = new HashMap<>();
        Connection con = null;
        try {
            // Lấy kết nối từ pool kết nối
            con = connectionPool.getConnection("PhieuNhapImpl-getPhieuCountByNguoiTao");
            // Câu lệnh SQL để đếm số lượng phiếu nhập theo người tạo
            String sql = "SELECT nguoiTao, COUNT(maPhieu) as soLuongPhieu FROM PhieuNhap GROUP BY nguoiTao";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                // Lấy dữ liệu từ kết quả truy vấn và đưa vào Map
                String nguoiTao = rs.getString("nguoiTao");
                int soLuongPhieu = rs.getInt("soLuongPhieu");
                result.put(nguoiTao, soLuongPhieu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo kết nối được giải phóng sau khi sử dụng
            if (con != null) {
                try {
                    connectionPool.releaseConnection(con, "PhieuNhapImpl-getPhieuCountByNguoiTao");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

 // Phương thức này trả về một Map chứa tổng tiền theo từng nhà cung cấp
    public Map<String, Double> getTongTienByNhaCungCap() {
        Map<String, Double> result = new HashMap<>();
        Connection con = null;
        try {
            // Lấy kết nối từ pool kết nối
            con = connectionPool.getConnection("PhieuNhapImpl-getTongTienByNhaCungCap");
            // Câu lệnh SQL để tính tổng tiền theo nhà cung cấp
            String sql = "SELECT maNhaCungCap, SUM(tongTien) as tongTien FROM PhieuNhap GROUP BY maNhaCungCap";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                // Lấy dữ liệu từ kết quả truy vấn và đưa vào Map
                String maNhaCungCap = rs.getString("maNhaCungCap");
                double tongTien = rs.getDouble("tongTien");
                result.put(maNhaCungCap, tongTien);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo kết nối được giải phóng sau khi sử dụng
            if (con != null) {
                try {
                    connectionPool.releaseConnection(con, "PhieuNhapImpl-getTongTienByNhaCungCap");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    
}
