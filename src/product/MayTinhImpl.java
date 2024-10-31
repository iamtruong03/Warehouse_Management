package product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import object.MayTinh;
import util.ConnectionPoolImpl;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MayTinhImpl implements ImplInterface<MayTinh> {

    private static final ConnectionPoolImpl connectionPool = new ConnectionPoolImpl();

    public static MayTinhImpl getInstance() {
        return new MayTinhImpl();
    }

    @Override
    public int insert(MayTinh t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(MayTinh t) {
        int ketqua = 0;
        try {
            Connection con = connectionPool.getConnection("MayTinhImpl");
            String sql = "UPDATE MayTinh SET tenMay = ?,soLuong=?,gia=?,tenCpu=?,ram=?,xuatXu=?,cardManHinh=?,rom=?,trangThai=? WHERE maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getTenMay());
            pst.setInt(2, t.getSoLuong());
            pst.setDouble(3, t.getGia());
            pst.setString(4, t.getTenCpu());
            pst.setString(5, t.getRam());
            pst.setString(6, t.getXuatXu());
            pst.setString(7, t.getCardManHinh());
            pst.setString(8, t.getRom());
            pst.setInt(9, t.getTrangThai());
            pst.setString(10, t.getMaMay());
            ketqua = pst.executeUpdate();
            connectionPool.releaseConnection(con, "MayTinhImpl");
        } catch (SQLException ex) {
            Logger.getLogger(MayTinhImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ketqua;
    }

    @Override
    public int delete(MayTinh t) {
        int ketQua = 0;
        try {
            Connection con = connectionPool.getConnection("MayTinhImpl");
            String sql = "DELETE FROM MayTinh WHERE maMay=? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaMay());
            ketQua = pst.executeUpdate();
            connectionPool.releaseConnection(con, "MayTinhImpl");
        } catch (SQLException ex) {
            Logger.getLogger(MayTinhImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ketQua;
    }

    @Override
    public ArrayList<MayTinh> selectAll() {
        ArrayList<MayTinh> ketQua = new ArrayList<>();
        try {
            Connection con = connectionPool.getConnection("MayTinhImpl");
            String sql = "SELECT maMay,tenMay,soLuong,gia,tenCpu,ram,xuatXu,cardManHinh,rom,trangThai FROM MayTinh";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maMay = rs.getString("maMay");
                String tenMay = rs.getString("tenMay");
                int soLuong = rs.getInt("soLuong");
                double gia = rs.getDouble("gia");
                String tenCpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String xuatXu = rs.getString("xuatXu");
                String cardManHinh = rs.getString("cardManHinh");
                String rom = rs.getString("rom");
                int trangThai = rs.getInt("trangThai");
                MayTinh mt = new MayTinh(maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, rom, trangThai);
                ketQua.add(mt);
            }
            connectionPool.releaseConnection(con, "MayTinhImpl");
        } catch (SQLException ex) {
            Logger.getLogger(MayTinhImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ketQua;
    }

    @Override
    public MayTinh selectById(String t) {
        MayTinh ketQua = null;
        try {
            Connection con = connectionPool.getConnection("MayTinhImpl");
            String sql = "SELECT maMay,tenMay,soLuong,gia,tenCpu,ram,xuatXu,cardManHinh,rom,trangThai FROM MayTinh WHERE maMay = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String maMay = rs.getString("maMay");
                String tenMay = rs.getString("tenMay");
                int soLuong = rs.getInt("soLuong");
                double gia = rs.getDouble("gia");
                String tenCpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String xuatXu = rs.getString("xuatXu");
                String cardManHinh = rs.getString("cardManHinh");
                String rom = rs.getString("rom");
                int trangThai = rs.getInt("trangThai");
                ketQua = new MayTinh(maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, rom, trangThai);
            }
            connectionPool.releaseConnection(con, "MayTinhImpl");
        } catch (SQLException ex) {
            Logger.getLogger(MayTinhImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ketQua;
    }

    public int updateSoLuong(String maMay, int soluong) {
        int ketQua = 0;
        try {
            Connection con = connectionPool.getConnection("MayTinhImpl");
            String sql = "UPDATE MayTinh SET soLuong=? WHERE maMay=? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, soluong);
            pst.setString(2, maMay);
            ketQua = pst.executeUpdate();
            connectionPool.releaseConnection(con, "MayTinhImpl");
        } catch (SQLException ex) {
            Logger.getLogger(MayTinhImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ketQua;
    }

    public int deleteTrangThai(String maMay) {
        int ketQua = 0;
        try {
            Connection con = connectionPool.getConnection("MayTinhImpl");
            String sql = "UPDATE MayTinh SET trangThai=0 WHERE maMay=? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maMay);
            ketQua = pst.executeUpdate();
            connectionPool.releaseConnection(con, "MayTinhImpl");
        } catch (SQLException ex) {
            Logger.getLogger(MayTinhImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ketQua;
    }

    public ArrayList<MayTinh> selectAllE() {
        ArrayList<MayTinh> ketQua = new ArrayList<>();
        ArrayList<MayTinh> ketQuaTonKho = new ArrayList<>();
        try {
            Connection con = connectionPool.getConnection("MayTinhImpl");
            String sql = "SELECT maMay,tenMay,soLuong,gia,tenCpu,ram,xuatXu,cardManHinh,rom,trangThai FROM MayTinh";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maMay = rs.getString("maMay");
                String tenMay = rs.getString("tenMay");
                int soLuong = rs.getInt("soLuong");
                double gia = rs.getDouble("gia");
                String tenCpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String xuatXu = rs.getString("xuatXu");
                String cardManHinh = rs.getString("cardManHinh");
                String rom = rs.getString("rom");
                int trangThai = rs.getInt("trangThai");
                MayTinh mt = new MayTinh(maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, rom, trangThai);
                ketQua.add(mt);
            }
            for (MayTinh mayTinh : ketQua) {
                if (mayTinh.getSoLuong() > 0) {
                    ketQuaTonKho.add(mayTinh);
                }
            }
            connectionPool.releaseConnection(con, "MayTinhImpl");
        } catch (SQLException ex) {
            Logger.getLogger(MayTinhImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ketQuaTonKho;
    }

    public ArrayList<MayTinh> selectAllExist() {
        ArrayList<MayTinh> ketQua = new ArrayList<>();
        try {
            Connection con = connectionPool.getConnection("MayTinhImpl");
            String sql = "SELECT maMay,tenMay,soLuong,gia,tenCpu,ram,xuatXu,cardManHinh,rom,trangThai FROM MayTinh WHERE trangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maMay = rs.getString("maMay");
                String tenMay = rs.getString("tenMay");
                int soLuong = rs.getInt("soLuong");
                double gia = rs.getDouble("gia");
                String tenCpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String xuatXu = rs.getString("xuatXu");
                String cardManHinh = rs.getString("cardManHinh");
                String rom = rs.getString("rom");
                int trangThai = rs.getInt("trangThai");
                MayTinh mt = new MayTinh(maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, rom, trangThai);
                ketQua.add(mt);
            }
            connectionPool.releaseConnection(con, "MayTinhImpl");
        } catch (SQLException ex) {
            Logger.getLogger(MayTinhImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ketQua;
    }

    public int getSl() {
        int soluong = 0;
        try {
            Connection con = connectionPool.getConnection("MayTinhImpl");
            String sql = "SELECT * FROM MayTinh WHERE trangThai = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                soluong++;
            }
            connectionPool.releaseConnection(con, "MayTinhImpl");
        } catch (SQLException ex) {
            Logger.getLogger(MayTinhImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return soluong;
    }
}
