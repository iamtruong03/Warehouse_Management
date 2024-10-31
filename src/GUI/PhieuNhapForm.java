package GUI;

import object.Account;
import object.ChiTietPhieu;
import object.PhieuNhap;
import product.AccountImpl;
import product.ChiTietPhieuNhapImpl;
import product.NhaCungCapImpl;
import product.PhieuNhapImpl;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;


public class PhieuNhapForm extends javax.swing.JInternalFrame {

	// Khai báo mô hình dữ liệu cho bảng
    private DefaultTableModel tblModel;
    // Định dạng số và ngày tháng
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");

    // Phương thức trả về định dạng số
    public DecimalFormat getFormatter() {
        return formatter;
    }

    // Phương thức trả về định dạng ngày tháng
    public SimpleDateFormat getFormatDate() {
        return formatDate;
    }

 // Hàm khởi tạo, thiết lập các thành phần giao diện và logic ban đầu
    public PhieuNhapForm(Account accCur) {
        initComponents();  // Khởi tạo các thành phần giao diện
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);  // Ẩn thanh tiêu đề của form nội bộ
        tblPhieuNhap.setDefaultEditor(Object.class, null);  // Vô hiệu hóa chỉnh sửa trực tiếp trên bảng
        initTable();  // Khởi tạo bảng dữ liệu
        loadDataToTable();  // Tải dữ liệu vào bảng
        changeTextFind();  // Thay đổi chức năng tìm kiếm
        jDateChooserFrom.setDateFormatString("dd/MM/yyyy");  // Định dạng ngày cho bộ chọn ngày
        jDateChooserTo.setDateFormatString("dd/MM/yyyy");

        // Kiểm tra quyền của người dùng hiện tại và vô hiệu hóa một số chức năng nếu cần
        if (accCur.getRole().equals("Nhân viên nhập")) {
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);
            btnImportExcel.setEnabled(false);
            jButton6.setEnabled(false);
        }
    }

    // Phương thức khởi tạo bảng với các cột tương ứng
    public final void initTable() {
        tblModel = new DefaultTableModel();
        String[] headerTbl = new String[]{"STT", "Mã phiếu nhập", "Nhà cung cấp", "Người tạo", "Thời gian tạo", "Tổng tiền"};
        tblModel.setColumnIdentifiers(headerTbl);
        tblPhieuNhap.setModel(tblModel);
        // Thiết lập độ rộng các cột
        tblPhieuNhap.getColumnModel().getColumn(0).setPreferredWidth(1);
        tblPhieuNhap.getColumnModel().getColumn(1).setPreferredWidth(2);
        tblPhieuNhap.getColumnModel().getColumn(2).setPreferredWidth(300);
        tblPhieuNhap.getColumnModel().getColumn(3).setPreferredWidth(100);
    }

    // Phương thức tải dữ liệu vào bảng
    public void loadDataToTable() {
        try {
            // Lấy tất cả các phiếu nhập từ cơ sở dữ liệu
            ArrayList<PhieuNhap> allPhieuNhap = PhieuNhapImpl.getInstance().selectAll();
            tblModel.setRowCount(0);  // Xóa hết dữ liệu cũ trong bảng
            // Thêm dữ liệu mới vào bảng
            for (int i = 0; i < allPhieuNhap.size(); i++) {
                tblModel.addRow(new Object[]{
                    i + 1, 
                    allPhieuNhap.get(i).getMaPhieu(), 
                    NhaCungCapImpl.getInstance().selectById(allPhieuNhap.get(i).getNhaCungCap()).getTenNhaCungCap(), 
                    AccountImpl.getInstance().selectById(allPhieuNhap.get(i).getNguoiTao()).getFullName(), 
                    formatDate.format(allPhieuNhap.get(i).getThoiGianTao()), 
                    formatter.format(allPhieuNhap.get(i).getTongTien()) + "đ"
                });
            }
        } catch (Exception e) {
            e.printStackTrace();  // In lỗi ra console
        }
    }

    // Phương thức tải dữ liệu tìm kiếm vào bảng
    private void loadDataToTableSearch(ArrayList<PhieuNhap> result) {
        try {
            tblModel.setRowCount(0);  // Xóa hết dữ liệu cũ trong bảng
            // Thêm dữ liệu tìm kiếm vào bảng
            for (int i = 0; i < result.size(); i++) {
                tblModel.addRow(new Object[]{
                    i + 1, 
                    result.get(i).getMaPhieu(), 
                    NhaCungCapImpl.getInstance().selectById(result.get(i).getNhaCungCap()).getTenNhaCungCap(), 
                    AccountImpl.getInstance().selectById(result.get(i).getNguoiTao()).getFullName(), 
                    formatDate.format(result.get(i).getThoiGianTao()), 
                    formatter.format(result.get(i).getTongTien()) + "đ"
                });
            }
        } catch (Exception e) {
            e.printStackTrace();  // In lỗi ra console
        }
    }

    // Phương thức tìm kiếm phiếu nhập theo tất cả các trường
    public ArrayList<PhieuNhap> searchTatCa(String text) {
        ArrayList<PhieuNhap> result = new ArrayList<>();
        ArrayList<PhieuNhap> armt = PhieuNhapImpl.getInstance().selectAll();
        for (var phieu : armt) {
            // Kiểm tra nếu bất kỳ trường nào chứa chuỗi tìm kiếm
            if (phieu.getMaPhieu().toLowerCase().contains(text.toLowerCase())
                    || phieu.getNhaCungCap().toLowerCase().contains(text.toLowerCase())
                    || phieu.getNguoiTao().toLowerCase().contains(text.toLowerCase())) {
                result.add(phieu);
            }
        }
        return result;
    }

    // Phương thức tìm kiếm phiếu nhập theo mã phiếu nhập
    public ArrayList<PhieuNhap> searchMaPhieuNhap(String text) {
        ArrayList<PhieuNhap> result = new ArrayList<>();
        ArrayList<PhieuNhap> armt = PhieuNhapImpl.getInstance().selectAll();
        for (var phieu : armt) {
            // Kiểm tra nếu mã phiếu chứa chuỗi tìm kiếm
            if (phieu.getMaPhieu().toLowerCase().contains(text.toLowerCase())) {
                result.add(phieu);
            }
        }
        return result;
    }

    // Phương thức tìm kiếm phiếu nhập theo nhà cung cấp
    public ArrayList<PhieuNhap> searchNhaCungCap(String text) {
        ArrayList<PhieuNhap> result = new ArrayList<>();
        ArrayList<PhieuNhap> armt = PhieuNhapImpl.getInstance().selectAll();
        for (var phieu : armt) {
            // Kiểm tra nếu nhà cung cấp chứa chuỗi tìm kiếm
            if (phieu.getNhaCungCap().toLowerCase().contains(text.toLowerCase())) {
                result.add(phieu);
            }
        }
        return result;
    }

    // Phương thức tìm kiếm phiếu nhập theo người tạo
    public ArrayList<PhieuNhap> searchNguoiTao(String text) {
        ArrayList<PhieuNhap> result = new ArrayList<>();
        ArrayList<PhieuNhap> armt = PhieuNhapImpl.getInstance().selectAll();
        for (var phieu : armt) {
            // Kiểm tra nếu người tạo chứa chuỗi tìm kiếm
            if (phieu.getNguoiTao().toLowerCase().contains(text.toLowerCase())) {
                result.add(phieu);
            }
        }
        return result;
    }

    // Phương thức thay đổi văn bản tìm kiếm
    public void changeTextFind() {
        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Khi có thay đổi trong văn bản tìm kiếm
                if (jTextFieldSearch.getText().length() == 0) {
                    loadDataToTable();  // Nếu văn bản tìm kiếm rỗng, tải lại toàn bộ dữ liệu
                } else {
                    loadDataToTableSearch(searchTatCa(jTextFieldSearch.getText()));  // Tìm kiếm và hiển thị kết quả
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Khi văn bản tìm kiếm bị xóa
                if (jTextFieldSearch.getText().length() == 0) {
                    loadDataToTable();  // Nếu văn bản tìm kiếm rỗng, tải lại toàn bộ dữ liệu
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Do nothing
            }
        });
    }

    // Phương thức tạo mã phiếu nhập mới
    public String createId(ArrayList<PhieuNhap> arr) {
        int id = arr.size() + 1;  // Bắt đầu từ kích thước danh sách hiện tại + 1
        String check = "";
        // Kiểm tra nếu mã phiếu nhập đã tồn tại, tăng id lên và kiểm tra lại
        for (PhieuNhap phieuNhap : arr) {
            if (phieuNhap.getMaPhieu().equals("PN" + id)) {
                check = phieuNhap.getMaPhieu();
            }
        }
        while (check.length() != 0) {
            id++;
            check = "";
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).getMaPhieu().equals("PN" + id)) {
                    check = arr.get(i).getMaPhieu();
                }
            }
        }
        return "PN" + id;  // Trả về mã phiếu nhập mới
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDetail = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton6 = new javax.swing.JButton();
        btnImportExcel = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jComboBoxLuaChon = new javax.swing.JComboBox<>();
        jTextFieldSearch = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuNhap = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jDateChooserFrom = new com.toedter.calendar.JDateChooser();
        jDateChooserTo = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        giaTu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        giaDen = new javax.swing.JTextField();

        setBorder(null);
        setPreferredSize(new java.awt.Dimension(1180, 770));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));
        jToolBar1.setRollover(true);

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_40px.png"))); // NOI18N
        btnDelete.setText("Xoá");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_edit_40px.png"))); // NOI18N
        btnEdit.setText("Sửa");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.setFocusable(false);
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);

        btnDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_eye_40px.png"))); // NOI18N
        btnDetail.setText("Xem chi tiết");
        btnDetail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDetail.setFocusable(false);
        btnDetail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDetail.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDetail);
        jToolBar1.add(jSeparator1);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_spreadsheet_file_40px.png"))); // NOI18N
        jButton6.setText("Xuất Excel");
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        btnImportExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_xls_40px.png"))); // NOI18N
        btnImportExcel.setText("Nhập Excel");
        btnImportExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImportExcel.setFocusable(false);
        btnImportExcel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImportExcel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImportExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportExcelActionPerformed(evt);
            }
        });
        jToolBar1.add(btnImportExcel);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxLuaChon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Mã phiếu", "Nhà cung cấp", "Người tạo" }));
        jComboBoxLuaChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLuaChonActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBoxLuaChon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 210, 40));

        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyReleased(evt);
            }
        });
        jPanel3.add(jTextFieldSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 310, 40));

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        jButton7.setText("Làm mới");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 140, 40));

        tblPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblPhieuNhap);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc theo ngày"));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jDateChooserFrom.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserFrom.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserFromPropertyChange(evt);
            }
        });
        jDateChooserFrom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooserFromKeyReleased(evt);
            }
        });
        jPanel4.add(jDateChooserFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 170, -1));

        jDateChooserTo.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserTo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserToPropertyChange(evt);
            }
        });
        jDateChooserTo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooserToKeyReleased(evt);
            }
        });
        jPanel4.add(jDateChooserTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 170, -1));

        jLabel1.setText("Đến");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 40, 20));

        jLabel5.setFont(new java.awt.Font("SF Pro Display", 0, 14)); // NOI18N
        jLabel5.setText("Từ");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 20, 20));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc theo giá"));

        jLabel3.setText("Từ");

        giaTu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giaTuActionPerformed(evt);
            }
        });
        giaTu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                giaTuKeyReleased(evt);
            }
        });

        jLabel4.setText("Đến");

        giaDen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giaDenActionPerformed(evt);
            }
        });
        giaDen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                giaDenKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(giaTu, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel4)
                .addGap(28, 28, 28)
                .addComponent(giaDen, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(giaTu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(giaDen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
        					.addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
        					.addGap(18)
        					.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
        					.addComponent(jToolBar1, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)))
        			.addContainerGap())
        		.addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
        			.addGap(20)
        			.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 1160, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGap(8)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
        				.addComponent(jToolBar1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        			.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 550, GroupLayout.PREFERRED_SIZE)
        			.addGap(86))
        );
        jPanel1.setLayout(jPanel1Layout);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 750));

        pack();
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        if (tblPhieuNhap.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu cần xoá");
        } else {
            deletePhieuNhap(getPhieuNhapSelect());
        }
    }

    public void deletePhieuNhap(PhieuNhap pn) {
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xoá " + pn.getMaPhieu(), "Xác nhận xoá phiếu", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            ArrayList<ChiTietPhieu> ctPhieuNhap = ChiTietPhieuNhapImpl.getInstance().selectAll(pn.getMaPhieu());
            for (ChiTietPhieu i : ctPhieuNhap) {
                ChiTietPhieuNhapImpl.getInstance().delete(i);
            }
            PhieuNhapImpl.getInstance().delete(pn);
            JOptionPane.showMessageDialog(this, "Đã xoá thành công phiếu " + pn.getMaPhieu());
            loadDataToTable();
        }
    }

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        if (tblPhieuNhap.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu cần sửa");
        } else {
            try {
                UpdatePhieuNhap a = new UpdatePhieuNhap(this, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
                a.setVisible(true);
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(PhieuNhapForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();
            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("Account");

                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < tblPhieuNhap.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tblPhieuNhap.getColumnName(i));
                }
                for (int j = 0; j < tblPhieuNhap.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tblPhieuNhap.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tblPhieuNhap.getValueAt(j, k) != null) {
                            cell.setCellValue(tblPhieuNhap.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                openFile(saveFile.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnImportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportExcelActionPerformed
        // TODO add your handling code here:
        //import excel
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;
        ArrayList<PhieuNhap> listAccExcel = new ArrayList<PhieuNhap>();
        JFileChooser jf = new JFileChooser();
        int result = jf.showOpenDialog(null);
        jf.setDialogTitle("Open file");
        Workbook workbook = null;
        DefaultTableModel table_acc = (DefaultTableModel) tblPhieuNhap.getModel();
        table_acc.setRowCount(0);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = jf.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);
                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);
                    String maPhieuNhap = excelRow.getCell(1).getStringCellValue();
                    String nguoiTao = excelRow.getCell(2).getStringCellValue();
                    String dateText = excelRow.getCell(3).getStringCellValue();
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date dateCheck = format.parse(dateText);
                    String giaFomat = excelRow.getCell(4).getStringCellValue().replaceAll(",", "");
                    System.out.println(giaFomat);
                    int viTri = giaFomat.length() - 1;
                    String giaoke = giaFomat.substring(0, viTri) + giaFomat.substring(viTri + 1);
                    double donGia = Double.parseDouble(giaoke);
                    table_acc.addRow(new Object[]{
                        row, maPhieuNhap, nguoiTao, formatDate.format(dateCheck), formatter.format(donGia) + "đ"
                    });
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProductForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ProductForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(PhieuNhapForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnImportExcelActionPerformed


    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        loadDataToTable();
        jComboBoxLuaChon.setSelectedIndex(0);
        jTextFieldSearch.setText("");
        jDateChooserFrom.setCalendar(null);
        jDateChooserTo.setCalendar(null);
        giaDen.setText("");
        giaTu.setText("");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
        // TODO add your handling code here:
        if (tblPhieuNhap.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu !");
        } else {
            CTPhieuNhap a = new CTPhieuNhap(this, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
            a.setVisible(true);
        }
    }//GEN-LAST:event_btnDetailActionPerformed

    private void giaDenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giaDenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_giaDenActionPerformed

    private void jComboBoxLuaChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxLuaChonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxLuaChonActionPerformed

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        // TODO add your handling code here:
        searchAllRepect();
    }//GEN-LAST:event_jTextFieldSearchKeyReleased

    private void giaTuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giaTuActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_giaTuActionPerformed

    private void giaTuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_giaTuKeyReleased
        // TODO add your handling code here:
        searchAllRepect();
    }//GEN-LAST:event_giaTuKeyReleased

    private void jDateChooserFromKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooserFromKeyReleased
        // TODO add your handling code here:
        searchAllRepect();
    }//GEN-LAST:event_jDateChooserFromKeyReleased

    private void jDateChooserToKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooserToKeyReleased
        // TODO add your handling code here:
        searchAllRepect();
    }//GEN-LAST:event_jDateChooserToKeyReleased

    private void giaDenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_giaDenKeyReleased
        // TODO add your handling code here:
        searchAllRepect();
        System.out.println(giaDen.getText());
    }//GEN-LAST:event_giaDenKeyReleased

    private void jDateChooserFromPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooserFromPropertyChange
        // TODO add your handling code here:
        searchAllRepect();
    }//GEN-LAST:event_jDateChooserFromPropertyChange

    private void jDateChooserToPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooserToPropertyChange
        // TODO add your handling code here:
        searchAllRepect();
    }//GEN-LAST:event_jDateChooserToPropertyChange

 // Phương thức lấy phiếu nhập hàng được chọn trong bảng
    public PhieuNhap getPhieuNhapSelect() {
        // Lấy chỉ số hàng được chọn trong bảng
        int i_row = tblPhieuNhap.getSelectedRow();
        // Lấy phiếu nhập hàng theo mã phiếu từ cơ sở dữ liệu
        PhieuNhap pn = PhieuNhapImpl.getInstance().selectById(tblModel.getValueAt(i_row, 1).toString());
        return pn;  // Trả về phiếu nhập hàng được chọn
    }

    // Phương thức kiểm tra xem ngày kiểm tra có nằm trong khoảng từ ngày bắt đầu đến ngày kết thúc hay không
    public boolean checkDate(Date dateTest, Date star, Date end) {
        // So sánh thời gian của ngày kiểm tra với ngày bắt đầu và ngày kết thúc
        return dateTest.getTime() >= star.getTime() && dateTest.getTime() <= end.getTime();
    }

    // Phương thức tìm kiếm phiếu nhập hàng theo khoảng thời gian
    public ArrayList<PhieuNhap> searchDate() {
        // Định dạng thời gian để sử dụng trong log
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        ArrayList<PhieuNhap> result = new ArrayList<>();  // Danh sách kết quả tìm kiếm
        Date from = jDateChooserFrom.getDate();  // Lấy ngày bắt đầu từ bộ chọn ngày
        Date to = jDateChooserTo.getDate();  // Lấy ngày kết thúc từ bộ chọn ngày
        ArrayList<PhieuNhap> armt = PhieuNhapImpl.getInstance().selectAll();  // Lấy tất cả phiếu nhập từ cơ sở dữ liệu
        
        // Duyệt qua tất cả phiếu nhập
        for (var phieu : armt) {
            // Log thông tin kiểm tra
            System.out.println("From: " + from + " " + from.getTime());
            System.out.println("To: " + to + " " + to.getTime());
            System.out.println("Current: " + phieu.getThoiGianTao() + " " + phieu.getThoiGianTao().getTime());
            System.out.println("Check: " + checkDate(phieu.getThoiGianTao(), from, to));
            
            // Nếu phiếu nhập nằm trong khoảng thời gian từ ngày bắt đầu đến ngày kết thúc
            if (checkDate(phieu.getThoiGianTao(), from, to)) {
                result.add(phieu);  // Thêm phiếu nhập vào danh sách kết quả
            }
        }
        return result; 
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnImportExcel;
    private javax.swing.JTextField giaDen;
    private javax.swing.JTextField giaTu;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBoxLuaChon;
    private com.toedter.calendar.JDateChooser jDateChooserFrom;
    private com.toedter.calendar.JDateChooser jDateChooserTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tblPhieuNhap;
    // End of variables declaration//GEN-END:variables

 // Phương thức mở tệp bằng ứng dụng mặc định trên hệ thống
    private void openFile(String file) {
        try {
            // Tạo đối tượng File từ đường dẫn tệp
            File path = new File(file);
            // Mở tệp bằng ứng dụng mặc định
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            // In thông báo lỗi ra console nếu có lỗi xảy ra
            System.out.println(e);
        }
    }

    // Phương thức tìm kiếm tất cả phiếu nhập theo lựa chọn
    public void searchAllRepect() {
        // Lấy lựa chọn từ combo box và nội dung tìm kiếm từ text field
        String luaChon = jComboBoxLuaChon.getSelectedItem().toString();
        String content = jTextFieldSearch.getText();
        ArrayList<PhieuNhap> result = null;
        
        // Nếu có nội dung tìm kiếm, thực hiện tìm kiếm theo lựa chọn
        if (content.length() > 0) {
            result = new ArrayList<>();
            switch (luaChon) {
                case "Tất cả":
                    result = searchTatCa(content);
                    break;
                case "Mã phiếu":
                    result = searchMaPhieuNhap(content);
                    break;
                case "Nhà cung cấp":
                    result = searchNhaCungCap(content);
                    break;
                case "Người tạo":
                    result = searchNguoiTao(content);
                    break;
            }
        } else if (content.length() == 0) {
            result = PhieuNhapImpl.getInstance().selectAll();
        }
        
        Iterator<PhieuNhap> itr = result.iterator();
        
        if (jDateChooserFrom.getDate() != null || jDateChooserTo.getDate() != null) {
            Date from;
            Date to;
            
            if (jDateChooserFrom.getDate() != null && jDateChooserTo.getDate() == null) {
                try {
                    from = ChangeFrom(jDateChooserFrom.getDate());
                    to = ChangeTo(new Date());
                    // Lọc danh sách theo ngày
                    while (itr.hasNext()) {
                        PhieuNhap phieu = itr.next();
                        if (!checkDate(phieu.getThoiGianTao(), from, to)) {
                            itr.remove();
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(PhieuNhapForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (jDateChooserTo.getDate() != null && jDateChooserFrom.getDate() == null) {
                try {
                    String sDate1 = "01/01/2002";
                    from = ChangeFrom(new SimpleDateFormat("dd/MM/yyyy").parse(sDate1));
                    to = ChangeTo(jDateChooserTo.getDate());
                    while (itr.hasNext()) {
                        PhieuNhap phieu = itr.next();
                        if (!checkDate(phieu.getThoiGianTao(), from, to)) {
                            itr.remove();
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(PhieuNhapForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    from = ChangeFrom(jDateChooserFrom.getDate());
                    to = ChangeTo(jDateChooserTo.getDate());
                    if (from.getTime() > to.getTime()) {
                        JOptionPane.showMessageDialog(this, "Thời gian không hợp lệ !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        jDateChooserFrom.setCalendar(null);
                        jDateChooserTo.setCalendar(null);
                    } else {
                        while (itr.hasNext()) {
                            PhieuNhap phieu = itr.next();
                            if (!checkDate(phieu.getThoiGianTao(), from, to)) {
                                itr.remove();
                            }
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(PhieuNhapForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        ArrayList<PhieuNhap> result1 = new ArrayList<>();
        
        if (giaTu.getText().length() > 0 || giaDen.getText().length() > 0) {
            double a;
            double b;
            if (giaTu.getText().length() > 0 && giaDen.getText().length() == 0) {
                a = Double.parseDouble(giaTu.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTongTien() >= a) {
                        result1.add(result.get(i));
                    }
                }
            } else if (giaTu.getText().length() == 0 && giaDen.getText().length() > 0) {
                b = Double.parseDouble(giaDen.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTongTien() <= b) {
                        result1.add(result.get(i));
                    }
                }
            } else if (giaTu.getText().length() > 0 && giaDen.getText().length() > 0) {
                a = Double.parseDouble(giaTu.getText());
                b = Double.parseDouble(giaDen.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTongTien() >= a && result.get(i).getTongTien() <= b) {
                        result1.add(result.get(i));
                    }
                }
            }
        }
        
        if (giaTu.getText().length() > 0 || giaDen.getText().length() > 0) {
            loadDataToTableSearch(result1);
        } else {
            loadDataToTableSearch(result);
        }
    }
    public Date ChangeFrom(Date date) throws ParseException {
        SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy 00:00:00");
        String dateText = fm.format(date);
        SimpleDateFormat par = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date result = par.parse(dateText);
        return result;
    }
    public Date ChangeTo(Date date) throws ParseException {
        SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy 23:59:59");
        String dateText = fm.format(date);
        SimpleDateFormat par = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date result = par.parse(dateText);
        return result;
    }

}
