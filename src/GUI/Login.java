package GUI;

import com.formdev.flatlaf.FlatLightLaf;
import com.mysql.cj.protocol.Resultset;
import com.sun.jdi.connect.spi.Connection;
import controller.BCrypt;
import object.Account;
import product.AccountImpl;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;


public class Login extends javax.swing.JFrame {

    /**
     * Creates new form JFrame
     */
    Connection con = null;
    Resultset rs = null;
    Color panDefualt, panEnter, panClick;

    public Login() {
        initComponents();
        setLocationRelativeTo(null);
        UIManager.put("Button.focus", Color.white);
        panDefualt = new Color(89, 168, 105);
        panClick = new Color(89, 168, 120);
        panEnter = new Color(89, 168, 120);
        JPaneLogin.setBackground(panDefualt);
        ImageIcon logo = new ImageIcon(getClass().getResource("/icon/logo.png"));
        setIconImage(logo.getImage());
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jLabel1 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        loginUser = new JTextField();
        passwordUser = new JPasswordField();
        JPaneLogin = new JPanel();
        jLabel2 = new JLabel();
        jLabel8 = new JLabel();
        jLabel7 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Đăng nhập vào phần mềm");
        setResizable(false);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new Color(13, 39, 51));
        jPanel1.setLayout(null);

        jPanel2.setBackground(UIManager.getDefaults().getColor("Actions.Green"));
        jPanel2.setLayout(null);

        jLabel1.setFont(new Font("Cantarell", Font.BOLD, 65)); 
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setText("LOGIN");
        jLabel1.setBounds(90, 290, 250, 70);
        jPanel2.add(jLabel1);

        jLabel3.setIcon(new ImageIcon(getClass().getResource("/icon/user.png")));
        jLabel3.setBounds(150, 180, 64, 64);
        jPanel2.add(jLabel3);

        jPanel2.setBounds(0, 0, 420, 580);
        jPanel1.add(jPanel2);

        jLabel4.setFont(new Font("SF Pro Display", Font.BOLD, 18)); 
        jLabel4.setForeground(Color.WHITE);
        jLabel4.setText("Username");
        jLabel4.setBounds(460, 150, 110, 40);
        jPanel1.add(jLabel4);

        jLabel5.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        jLabel5.setBounds(460, 320, 260, 1);
        jPanel1.add(jLabel5);

        jLabel6.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        jLabel6.setBounds(460, 230, 260, 1);
        jPanel1.add(jLabel6);

        loginUser.setBackground(new Color(13, 39, 51));
        loginUser.setFont(new Font("SF Pro Display", Font.BOLD, 18)); 
        loginUser.setForeground(Color.WHITE);
        loginUser.setBorder(null);
        loginUser.setBounds(460, 200, 260, 30);
        loginUser.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                loginUserMouseEntered(evt);
            }
            public void mousePressed(MouseEvent evt) {
                loginUserMousePressed(evt);
            }
        });
        loginUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loginUserActionPerformed(evt);
            }
        });
        loginUser.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                loginUserKeyPressed(evt);
            }
        });
        jPanel1.add(loginUser);

        passwordUser.setBackground(new Color(13, 39, 51));
        passwordUser.setForeground(Color.WHITE);
        passwordUser.setBorder(null);
        passwordUser.setBounds(460, 290, 260, 30);
        passwordUser.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                passwordUserKeyPressed(evt);
            }
        });
        jPanel1.add(passwordUser);

        JPaneLogin.setBackground(UIManager.getDefaults().getColor("Actions.Green"));
        JPaneLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JPaneLogin.setBounds(460, 360, 270, 40);
        JPaneLogin.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JPaneLoginMouseClicked(evt);
            }
            public void mouseEntered(MouseEvent evt) {
                JPaneLoginMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                JPaneLoginMouseExited(evt);
            }
            public void mousePressed(MouseEvent evt) {
                JPaneLoginMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                JPaneLoginMouseReleased(evt);
            }
        });

        jLabel2.setFont(new Font("SF Pro Display", Font.BOLD, 18)); 
        jLabel2.setForeground(Color.WHITE);
        jLabel2.setText("Đăng nhập");
        JPaneLogin.add(jLabel2);

        jPanel1.add(JPaneLogin);

        jLabel8.setFont(new Font("SF Pro Display", Font.BOLD, 18)); 
        jLabel8.setForeground(Color.WHITE);
        jLabel8.setText("Password");
        jLabel8.setBounds(460, 250, 130, 40);
        jPanel1.add(jLabel8);

        jLabel7.setFont(new Font("SF Pro Display", Font.BOLD, 18)); 
        jLabel7.setForeground(Color.WHITE);
        jLabel7.setText("Quên mật khẩu ?");
        jLabel7.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabel7.setBounds(540, 420, 200, 40);
        jLabel7.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel7);

        getContentPane().add(jPanel1);
        setSize(797, 578);
        setLocationRelativeTo(null);
    }

    private void loginUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loginUserActionPerformed

    private void JPaneLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPaneLoginMouseEntered
        // TODO add your handling code here:
        JPaneLogin.setBackground(panEnter);
    }//GEN-LAST:event_JPaneLoginMouseEntered

    private void JPaneLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPaneLoginMouseClicked
        checkLogin();


    }//GEN-LAST:event_JPaneLoginMouseClicked

    private void JPaneLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPaneLoginMouseExited
        // TODO add your handling code here:
        JPaneLogin.setBackground(panClick);
    }//GEN-LAST:event_JPaneLoginMouseExited

    private void JPaneLoginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPaneLoginMousePressed
        // TODO add your handling code here:

        JPaneLogin.setBackground(panEnter);
    }//GEN-LAST:event_JPaneLoginMousePressed

    private void JPaneLoginMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPaneLoginMouseReleased
        // TODO add your handling code here:
        JPaneLogin.setBackground(panClick);
    }//GEN-LAST:event_JPaneLoginMouseReleased

    private void loginUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginUserMouseEntered
        // TODO add your handling code here:    
    }//GEN-LAST:event_loginUserMouseEntered

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        System.out.println(evt.getKeyCode());

    }//GEN-LAST:event_formKeyPressed

    private void JPaneLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JPaneLoginKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JPaneLoginKeyPressed

    private void loginUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loginUserKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Enter was pressed. Your code goes here.
            checkLogin();
        }
    }//GEN-LAST:event_loginUserKeyPressed

    private void passwordUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordUserKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Enter was pressed. Your code goes here.
            checkLogin();
        }
    }//GEN-LAST:event_passwordUserKeyPressed

    private void loginUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginUserMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_loginUserMousePressed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        RecoverPassword rcv = new RecoverPassword(this, rootPaneCheckingEnabled);
        rcv.setVisible(true);
    }//GEN-LAST:event_jLabel7MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatLightLaf());

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    public void checkLogin() {
        String usercheck = loginUser.getText();
        String passwordcheck = passwordUser.getText();
        if (usercheck.equals("") || passwordcheck.equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ !", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
        } else {
            try {                
                Account acc = AccountImpl.getInstance().selectById(usercheck);                
                if (acc == null) {
                    JOptionPane.showMessageDialog(this, "Tài khoản không tồn tại trên hệ thống !", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (BCrypt.checkpw(passwordcheck, acc.getPassword())) {
                        if (acc.getStatus() == 1) {
                            try {
                                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                                this.dispose();
                                String role = acc.getRole();
                                if (role.equals("Admin")) {
                                    Admin ad = new Admin(acc);
                                    ad.setVisible(true);
//                                    ad.setCurrentAcc(acc);
                                    ad.setName(acc.getFullName());
                                } else if (role.equals("Quản lý kho")) {
                                    QuanLiKho ql = new QuanLiKho();
                                    ql.setVisible(true);
                                    ql.setCurrentAcc(acc);
                                    ql.setName(acc.getFullName());
                                } else if (role.equals("Nhân viên nhập")) {
                                    NhapKho ql = new NhapKho(acc);
                                    ql.setVisible(true);
                                    ql.setName(acc.getFullName());
                                } else if (role.equals("Nhân viên xuất")) {
                                    XuatKho ql = new XuatKho(acc);
                                    ql.setVisible(true);
                                    ql.setName(acc.getFullName());
                                }
                            } catch (UnsupportedLookAndFeelException ex) {
                                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Tài khoản của bạn đã bị khóa !", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Sai mật khẩu !", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel JPaneLogin;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JTextField loginUser;
    private JPasswordField passwordUser;

}
