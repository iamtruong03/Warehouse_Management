package product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.ConnectionPoolImpl;
import object.ChiTietPhieu;

public class ChiTietPhieuNhapImpl implements ImplInterface<ChiTietPhieu> {

    private ConnectionPoolImpl connectionPool;

    public ChiTietPhieuNhapImpl() {
        this.connectionPool = new ConnectionPoolImpl();
    }

    public static ChiTietPhieuNhapImpl getInstance() {
        return new ChiTietPhieuNhapImpl();
    }

    @Override
    public int insert(ChiTietPhieu t) {
        int ketQua = 0;
        try (Connection con = connectionPool.getConnection("ChiTietPhieuNhapImpl-insert");
             PreparedStatement pst = con.prepareStatement(
                 "INSERT INTO ChiTietPhieuNhap (maPhieu, maMay, soLuong, donGia) VALUES (?,?,?,?)")) {
            pst.setString(1, t.getMaPhieu());
            pst.setString(2, t.getMaMay());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(ChiTietPhieu t) {
        int ketQua = 0;
        try (Connection con = connectionPool.getConnection("ChiTietPhieuNhapImpl-update");
             PreparedStatement pst = con.prepareStatement(
                 "UPDATE ChiTietPhieuNhap SET maPhieu=?, maMay=?, soLuong=?, donGia=? WHERE maPhieu=? AND maMay=?")) {
            pst.setString(1, t.getMaPhieu());
            pst.setString(2, t.getMaMay());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            pst.setString(5, t.getMaPhieu());
            pst.setString(6, t.getMaMay());
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(ChiTietPhieu t) {
        int ketQua = 0;
        try (Connection con = connectionPool.getConnection("ChiTietPhieuNhapImpl-delete");
             PreparedStatement pst = con.prepareStatement(
                 "DELETE FROM ChiTietPhieuNhap WHERE maPhieu=? AND maMay=?")) {
            pst.setString(1, t.getMaPhieu());
            pst.setString(2, t.getMaMay());
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<ChiTietPhieu> selectAll(String maPhieu) {
        ArrayList<ChiTietPhieu> ketQua = new ArrayList<>();
        try (Connection con = connectionPool.getConnection("ChiTietPhieuNhapImpl-selectAll-by-maPhieu");
             PreparedStatement pst = con.prepareStatement(
                 "SELECT * FROM ChiTietPhieuNhap WHERE maPhieu=?")) {
            pst.setString(1, maPhieu);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String maMay = rs.getString("maMay");
                    int soLuong = rs.getInt("soLuong");
                    double donGia = rs.getDouble("donGia");
                    ChiTietPhieu ctp = new ChiTietPhieu(maPhieu, maMay, soLuong, donGia);
                    ketQua.add(ctp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<ChiTietPhieu> selectAll() {
        ArrayList<ChiTietPhieu> ketQua = new ArrayList<>();
        try (Connection con = connectionPool.getConnection("ChiTietPhieuNhapImpl-selectAll");
             PreparedStatement pst = con.prepareStatement("SELECT * FROM ChiTietPhieuNhap");
             ResultSet rs = pst.executeQuery()) {
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
        }
        return ketQua;
    }

    @Override
    public ChiTietPhieu selectById(String maPhieu) {
        ChiTietPhieu ketQua = null;
        try (Connection con = connectionPool.getConnection("ChiTietPhieuNhapImpl-selectById");
             PreparedStatement pst = con.prepareStatement(
                 "SELECT * FROM ChiTietPhieuNhap WHERE maPhieu=?")) {
            pst.setString(1, maPhieu);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String maMay = rs.getString("maMay");
                    int soLuong = rs.getInt("soLuong");
                    double donGia = rs.getDouble("donGia");
                    ketQua = new ChiTietPhieu(maPhieu, maMay, soLuong, donGia);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }

 // Phương thức này trả về một Map chứa tổng giá trị (số lượng * đơn giá) theo từng máy tính
    public Map<String, Double> getTotalValueByMachine() {
        // Tạo một Map để lưu kết quả
        Map<String, Double> data = new HashMap<>();
        // Sử dụng try-with-resources để tự động đóng các tài nguyên (Connection, PreparedStatement, ResultSet)
        try (Connection con = connectionPool.getConnection("ChiTietPhieuNhapImpl-getTotalValueByMachine");
             PreparedStatement pst = con.prepareStatement(
                 "SELECT mt.tenMay, SUM(ctpn.soLuong * ctpn.donGia) AS total " + // Câu lệnh SQL để tính tổng giá trị
                 "FROM ChiTietPhieuNhap ctpn " + // Từ bảng ChiTietPhieuNhap
                 "JOIN MayTinh mt ON ctpn.maMay = mt.maMay " + // Kết hợp với bảng MayTinh dựa trên maMay
                 "GROUP BY mt.tenMay"); // Nhóm theo tên máy
             ResultSet rs = pst.executeQuery()) {
            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                // Lấy dữ liệu từ kết quả truy vấn và đưa vào Map
                String tenMay = rs.getString("tenMay");
                double total = rs.getDouble("total");
                data.put(tenMay, total);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Trả về kết quả
        return data;
    }

}
