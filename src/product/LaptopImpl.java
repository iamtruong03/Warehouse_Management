package product;

import object.Laptop;
import util.ConnectionPoolImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class LaptopImpl implements ImplInterface<Laptop> {

    private ConnectionPoolImpl connectionPool;

    public LaptopImpl() {
        this.connectionPool = new ConnectionPoolImpl();
    }

    public static LaptopImpl getInstance() {
        return new LaptopImpl();
    }

    @Override
    public int insert(Laptop t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = connectionPool.getConnection("insert");
            String sql = "INSERT INTO MayTinh (maMay, tenMay, soLuong, tenCpu, ram, cardManHinh, gia, dungLuongPin, kichThuocMan, xuatXu, loaiMay, rom, trangThai) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaMay());
            pst.setString(2, t.getTenMay());
            pst.setInt(3, t.getSoLuong());
            pst.setString(4, t.getTenCpu());
            pst.setString(5, t.getRam());
            pst.setString(6, t.getCardManHinh());
            pst.setDouble(7, t.getGia());
            pst.setString(8, t.getDungLuongPin());
            pst.setDouble(9, t.getkichThuocMan());
            pst.setString(10, t.getXuatXu());
            pst.setString(11, "Laptop");
            pst.setString(12, t.getRom());
            pst.setInt(13, t.getTrangThai());
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thêm được " + t.getMaMay(),"Lỗi", JOptionPane.ERROR_MESSAGE);
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
    public int update(Laptop t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = connectionPool.getConnection("update");
            String sql = "UPDATE MayTinh SET maMay=?, tenMay=?, soLuong=?, tenCpu=?, ram=?, cardManHinh=?, gia=?, dungLuongPin=?, kichThuocMan=?, xuatXu=?, loaiMay=?, rom=?, trangThai=? WHERE maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaMay());
            pst.setString(2, t.getTenMay());
            pst.setInt(3, t.getSoLuong());
            pst.setString(4, t.getTenCpu());
            pst.setString(5, t.getRam());
            pst.setString(6, t.getCardManHinh());
            pst.setDouble(7, t.getGia());
            pst.setString(8, t.getDungLuongPin());
            pst.setDouble(9, t.getkichThuocMan());
            pst.setString(10, t.getXuatXu());
            pst.setString(11, "Laptop");
            pst.setString(12, t.getRom());
            pst.setInt(13, t.getTrangThai());
            pst.setString(14, t.getMaMay());
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
    public int delete(Laptop t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = connectionPool.getConnection("delete");
            String sql = "DELETE FROM MayTinh WHERE maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaMay());
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
    public ArrayList<Laptop> selectAll() {
        ArrayList<Laptop> ketQua = new ArrayList<Laptop>();
        Connection con = null;
        try {
            con = connectionPool.getConnection("selectAll");
            String sql = "SELECT * FROM MayTinh";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maMay = rs.getString("maMay");
                String tenMay = rs.getString("tenMay");
                int soLuong = rs.getInt("soLuong");
                String tenCpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String cardManHinh = rs.getString("cardManHinh");
                double gia = rs.getDouble("gia");
                double kichThuocMan = rs.getDouble("kichThuocMan");
                String dungLuongPin = rs.getString("dungLuongPin");
                String rom = rs.getString("rom");
                String xuatXu = rs.getString("xuatXu");
                int trangThai = rs.getInt("trangThai");
                Laptop mt = new Laptop(kichThuocMan, dungLuongPin, maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, rom, trangThai);
                ketQua.add(mt);
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
    public Laptop selectById(String t) {
        Laptop ketQua = null;
        Connection con = null;
        try {
            con = connectionPool.getConnection("selectById");
            String sql = "SELECT * FROM MayTinh WHERE maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maMay = rs.getString("maMay");
                String tenMay = rs.getString("tenMay");
                int soLuong = rs.getInt("soLuong");
                String tenCpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String cardManHinh = rs.getString("cardManHinh");
                double gia = rs.getDouble("gia");
                double kichThuocMan = rs.getDouble("kichThuocMan");
                String dungLuongPin = rs.getString("dungLuongPin");
                String rom = rs.getString("rom");
                String xuatXu = rs.getString("xuatXu");
                int trangThai = rs.getInt("trangThai");
                ketQua = new Laptop(kichThuocMan, dungLuongPin, maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, rom, trangThai);
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

    public boolean isLaptop(String id) {
        Connection con = null;
        try {
            con = connectionPool.getConnection("isLaptop");
            String sql = "SELECT * FROM MayTinh WHERE maMay= ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            String tl = null;
            while (rs.next()) {
                tl = rs.getString("loaiMay");
            }
            if (tl.equals("Laptop")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    connectionPool.releaseConnection(con, "isLaptop");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
