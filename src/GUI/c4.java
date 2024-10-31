package GUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import product.ChiTietPhieuNhapImpl;
import product.PhieuNhapImpl;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

public class c4 extends javax.swing.JInternalFrame {


    public c4() {
        initComponents();

        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        // Fetch data and create charts
        createChart1();
        createChart2();
        createChart3();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jTabbedPane1 = new javax.swing.JTabbedPane();
        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setPreferredSize(new java.awt.Dimension(1180, 770));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1180, 770));

        // Home Tab
        jPanel18 = new JPanel();
        jTabbedPane1.addTab("Home", null, jPanel18, null);
        
        chart1 = new ChartPanel((JFreeChart) null);
        JLabel lblNewLabel = new JLabel("Biểu đồ thị phần nhà cung cấp");
        
        chart2 = new ChartPanel((JFreeChart) null);
        JLabel lblNewLabel_1 = new JLabel("Biểu đồ giá trị nhập hàng theo máy");
        
        chart3 = new ChartPanel((JFreeChart) null);
        JLabel lblNewLabel_2 = new JLabel("Biểu đồ số lượng phiếu theo người tạo");

        GroupLayout gl_jPanel18 = new GroupLayout(jPanel18);
        gl_jPanel18.setHorizontalGroup(
            gl_jPanel18.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addGroup(gl_jPanel18.createSequentialGroup()
                    .addGroup(gl_jPanel18.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_jPanel18.createSequentialGroup()
                            .addGap(115)
                            .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                            .addGap(217)
                            .addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                        .addGroup(gl_jPanel18.createSequentialGroup()
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chart1, GroupLayout.PREFERRED_SIZE, 357, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(chart2, GroupLayout.PREFERRED_SIZE, 357, GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(gl_jPanel18.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(gl_jPanel18.createSequentialGroup()
                            .addComponent(chart3, GroupLayout.PREFERRED_SIZE, 357, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(gl_jPanel18.createSequentialGroup()
                            .addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
                            .addGap(80))))
        );
        gl_jPanel18.setVerticalGroup(
            gl_jPanel18.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(gl_jPanel18.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_jPanel18.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_jPanel18.createSequentialGroup()
                            .addComponent(chart3, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblNewLabel_2))
                        .addGroup(gl_jPanel18.createSequentialGroup()
                            .addGroup(gl_jPanel18.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(chart2, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
                                .addComponent(chart1, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(gl_jPanel18.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblNewLabel_1)
                                .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap())
        );
        chart3.setLayout(null);
        chart1.setLayout(null);
        chart2.setLayout(null);
        jPanel18.setLayout(gl_jPanel18);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, -1, 620));
        pack();
    }

    private void createChart1() {
        PhieuNhapImpl phieuNhapImpl = PhieuNhapImpl.getInstance();
        Map<String, Double> dataset = phieuNhapImpl.getTongTienByNhaCungCap();

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        for (Map.Entry<String, Double> entry : dataset.entrySet()) {
            pieDataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Thị phần nhà cung cấp theo tổng tiền",
                pieDataset,
                true, true, false);

        chart1.setChart(chart);
    }

    private void createChart2() {
        ChiTietPhieuNhapImpl chiTietPhieuNhapImpl = ChiTietPhieuNhapImpl.getInstance();
        Map<String, Double> data = chiTietPhieuNhapImpl.getTotalValueByMachine();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Tổng giá trị", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
            "Tổng giá trị hàng hóa phiếu nhập theo máy",
            "Tên máy",
            "vnđ",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false);

        barChart.setBackgroundPaint(Color.white);

        chart2.setChart(barChart);
    }

    private void createChart3() {
        PhieuNhapImpl phieuNhapImpl = PhieuNhapImpl.getInstance();
        Map<String, Integer> data = phieuNhapImpl.getPhieuCountByNguoiTao();

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
            "Số lượng phiếu theo người tạo",
            dataset,
            true, true, false);

        chart3.setChart(pieChart);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setSize(1200, 800);
                c4 myFrame = new c4();
                frame.getContentPane().add(myFrame);
                myFrame.setVisible(true);
                frame.setVisible(true);
            }
        });
    }

    private javax.swing.JTabbedPane jTabbedPane1;
    private JPanel jPanel18;
    private ChartPanel chart1;
    private ChartPanel chart2;
    private ChartPanel chart3;
    private JLabel lblNewLabel_2;
}
