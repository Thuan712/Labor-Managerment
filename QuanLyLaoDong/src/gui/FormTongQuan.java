package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.SystemColor;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import org.jfree.data.jdbc.JDBCPieDataset;

import connect.ConnectDB;
import dao.DAO_CongNhan;
import dao.DAO_CongTrinh;
import dao.DAO_CongViec;
import dao.DAO_Nhanvien;
import entity.CongNhan;
import entity.CongTrinh;
import entity.CongViec;
import entity.NhanVien;
/**
 * 
 * @author nmthu
 *
 */
public class FormTongQuan extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFreeChart tienDoHT;
	private JPanel pnMain;
	private DAO_CongNhan daoCN = new DAO_CongNhan();
	private DAO_CongTrinh daoCT = new DAO_CongTrinh();
	private DAO_CongViec daoCV = new DAO_CongViec();
	private DAO_Nhanvien daoNV = new DAO_Nhanvien();
	private JButton btnHelp;
	private ArrayList<CongNhan> listCN;
	private ArrayList<CongTrinh> listCT;
	private ArrayList<NhanVien> listNV;
	private ArrayList<CongViec> listCV;
	private ArrayList<CongTrinh> listCTDangLam;
	private ArrayList<CongTrinh> listCTHoanThanh;

	/**
	 * Create the panel.
	 */
	public FormTongQuan() {
		setBounds(0, 0, 1620, 1019);
		setLayout(null);
		pnMain = new JPanel();
		pnMain.setBounds(0, 0, 1620, 1020);
		pnMain.setBackground(Color.WHITE);
		pnMain.setLayout(null);
		add(pnMain);

		JPanel pnTongQuan = new JPanel();
		pnTongQuan.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		pnTongQuan.setBackground(null);
		pnTongQuan.setBounds(0, 0, 1620, 300);
		pnTongQuan.setLayout(null);
		pnMain.add(pnTongQuan);

		JPanel pnCongNhan = new JPanel();
		pnCongNhan.setBounds(30, 110, 380, 180);
		pnCongNhan.setBackground(new Color(217, 30, 24));
		pnCongNhan.setLayout(null);
		pnTongQuan.add(pnCongNhan);

		JPanel pnTitleCN = new JPanel();
		pnTitleCN.setBounds(0, 0, 380, 60);
		pnTitleCN.setBackground(new Color(242, 38, 19));
		pnCongNhan.add(pnTitleCN);
		pnTitleCN.setLayout(null);

		JLabel lblCongNhan = new JLabel("Công nhân");
		lblCongNhan.setForeground(SystemColor.textHighlightText);
		lblCongNhan.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblCongNhan.setBounds(10, 10, 200, 40);
		pnTitleCN.add(lblCongNhan);

		JLabel lblSLCN = new JLabel("");
		lblSLCN.setForeground(SystemColor.textHighlightText);
		lblSLCN.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblSLCN.setHorizontalAlignment(SwingConstants.CENTER);
		lblSLCN.setBounds(0, 80, 380, 40);
		pnCongNhan.add(lblSLCN);

		JLabel lblMoTaCN = new JLabel("công nhân");
		lblMoTaCN.setForeground(SystemColor.textHighlightText);
		lblMoTaCN.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblMoTaCN.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoTaCN.setBounds(0, 140, 380, 40);
		pnCongNhan.add(lblMoTaCN);

		listCN = new ArrayList<>();
		try {
			listCN = daoCN.getDSCongNhan("select * from congnhan");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int countCN = listCN.size();
		lblSLCN.setText(Integer.toString(countCN));

		JPanel pnCongTrinh = new JPanel();
		pnCongTrinh.setLayout(null);
		pnCongTrinh.setBackground(new Color(2, 152, 219));
		pnCongTrinh.setBounds(423, 110, 380, 180);
		pnTongQuan.add(pnCongTrinh);

		JPanel pnTitleCT = new JPanel();
		pnTitleCT.setLayout(null);
		pnTitleCT.setBackground(new Color(34, 167, 240));
		pnTitleCT.setBounds(0, 0, 380, 60);
		pnCongTrinh.add(pnTitleCT);

		JLabel lblCongTrinh = new JLabel("Công trình");
		lblCongTrinh.setForeground(Color.WHITE);
		lblCongTrinh.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblCongTrinh.setBounds(10, 10, 200, 40);
		pnTitleCT.add(lblCongTrinh);

		JLabel lblSLCT = new JLabel("0");
		lblSLCT.setHorizontalAlignment(SwingConstants.CENTER);
		lblSLCT.setForeground(Color.WHITE);
		lblSLCT.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblSLCT.setBounds(0, 80, 380, 40);
		pnCongTrinh.add(lblSLCT);

		JLabel lblMoTaCT = new JLabel("công trình");
		lblMoTaCT.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoTaCT.setForeground(Color.WHITE);
		lblMoTaCT.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblMoTaCT.setBounds(0, 140, 380, 40);
		pnCongTrinh.add(lblMoTaCT);

		listCT = new ArrayList<>();
		try {
			listCT = daoCT.getDsCongTrinh();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int countCT = listCT.size();
		lblSLCT.setText(Integer.toString(countCT));

		// Cong viec
		JPanel pnCongViec = new JPanel();
		pnCongViec.setLayout(null);
		pnCongViec.setBackground(new Color(1, 152, 117));
		pnCongViec.setBounds(817, 110, 380, 180);
		pnTongQuan.add(pnCongViec);

		JPanel pnTitleCV = new JPanel();
		pnTitleCV.setLayout(null);
		pnTitleCV.setBackground(new Color(63, 195, 128));
		pnTitleCV.setBounds(0, 0, 380, 60);
		pnCongViec.add(pnTitleCV);

		JLabel lblCongViec = new JLabel("Công việc");
		lblCongViec.setForeground(Color.WHITE);
		lblCongViec.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblCongViec.setBounds(10, 10, 200, 40);
		pnTitleCV.add(lblCongViec);

		JLabel lblSLCV = new JLabel("0");
		lblSLCV.setHorizontalAlignment(SwingConstants.CENTER);
		lblSLCV.setForeground(Color.WHITE);
		lblSLCV.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblSLCV.setBounds(0, 80, 380, 40);
		pnCongViec.add(lblSLCV);

		JLabel lblMoTaCV = new JLabel("công việc");
		lblMoTaCV.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoTaCV.setForeground(Color.WHITE);
		lblMoTaCV.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblMoTaCV.setBounds(0, 140, 380, 40);
		pnCongViec.add(lblMoTaCV);

		listCV = new ArrayList<>();
		try {
			listCV = daoCV.getDsCongviec();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int countCV = listCV.size();
		lblSLCV.setText(Integer.toString(countCV));

		// Nhân viên
		JPanel pnNhanVien = new JPanel();
		pnNhanVien.setLayout(null);
		pnNhanVien.setBackground(new Color(142, 68, 173));
		pnNhanVien.setBounds(1210, 110, 380, 180);
		pnTongQuan.add(pnNhanVien);

		JPanel pnTitleNV = new JPanel();
		pnTitleNV.setLayout(null);
		pnTitleNV.setBackground(new Color(155, 89, 182));
		pnTitleNV.setBounds(0, 0, 380, 60);
		pnNhanVien.add(pnTitleNV);

		JLabel lblNhanVien = new JLabel("Nhân viên");
		lblNhanVien.setForeground(Color.WHITE);
		lblNhanVien.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblNhanVien.setBounds(10, 10, 200, 40);
		pnTitleNV.add(lblNhanVien);

		JLabel lblSLNV = new JLabel("0");
		lblSLNV.setHorizontalAlignment(SwingConstants.CENTER);
		lblSLNV.setForeground(Color.WHITE);
		lblSLNV.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblSLNV.setBounds(0, 80, 380, 40);
		pnNhanVien.add(lblSLNV);

		JLabel lblMoTaNV = new JLabel("nhân viên");
		lblMoTaNV.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoTaNV.setForeground(Color.WHITE);
		lblMoTaNV.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblMoTaNV.setBounds(0, 140, 380, 40);
		pnNhanVien.add(lblMoTaNV);

		listNV = new ArrayList<>();
		try {
			listNV = daoNV.getDsNhanvien();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int countNV = listNV.size();
		lblSLNV.setText(Integer.toString(countNV));

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1620, 90);
		panel.setBackground(new Color(21, 25, 28));
		pnTongQuan.add(panel);
		panel.setLayout(null);

		JLabel lblTitle = new JLabel("TỔNG QUAN");
		lblTitle.setForeground(new Color(255, 204, 102));
		lblTitle.setBounds(0, 0, 436, 90);
		panel.add(lblTitle);
		lblTitle.setBackground(SystemColor.window);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 50));

		btnHelp = new JButton("");
		btnHelp.setBounds(1540, 11, 70, 65);
		panel.add(btnHelp);
		btnHelp.setIcon(new ImageIcon("icons/infor1.png"));
		btnHelp.setBorder(null);
		btnHelp.setBorderPainted(false);
		btnHelp.setBackground(new Color(21, 25, 28));
		btnHelp.setForeground(new Color(0, 0, 0));

		JPanel pnChart = new JPanel();
		pnChart.setBounds(928, 315, 662, 680);
		pnChart.setBackground(new Color(0, 51, 102));
		pnChart.setLayout(null);
		pnMain.add(pnChart);

		JPanel pnCongTrinh_1 = new JPanel();
		pnCongTrinh_1.setLayout(null);
		pnCongTrinh_1.setBackground(new Color(2, 152, 219));
		pnCongTrinh_1.setBounds(10, 10, 300, 180);
		pnChart.add(pnCongTrinh_1);

		JPanel pnTitleCT_1 = new JPanel();
		pnTitleCT_1.setLayout(null);
		pnTitleCT_1.setBackground(new Color(34, 167, 240));
		pnTitleCT_1.setBounds(0, 0, 300, 60);
		pnCongTrinh_1.add(pnTitleCT_1);

		JLabel lblDangLam = new JLabel("Đang thực hiện");
		lblDangLam.setForeground(Color.WHITE);
		lblDangLam.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblDangLam.setBounds(10, 10, 280, 40);
		pnTitleCT_1.add(lblDangLam);

		JLabel lblSLDangLam = new JLabel("0");
		lblSLDangLam.setHorizontalAlignment(SwingConstants.CENTER);
		lblSLDangLam.setForeground(Color.WHITE);
		lblSLDangLam.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblSLDangLam.setBounds(80, 80, 140, 40);
		pnCongTrinh_1.add(lblSLDangLam);

		JLabel lblMoTaDangLam = new JLabel("công trình");
		lblMoTaDangLam.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoTaDangLam.setForeground(Color.WHITE);
		lblMoTaDangLam.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblMoTaDangLam.setBounds(0, 140, 300, 40);
		pnCongTrinh_1.add(lblMoTaDangLam);

		JPanel pnCongTrinh_2 = new JPanel();
		pnCongTrinh_2.setLayout(null);
		pnCongTrinh_2.setBackground(new Color(2, 152, 219));
		pnCongTrinh_2.setBounds(352, 10, 300, 180);
		pnChart.add(pnCongTrinh_2);

		JPanel pnTitleCT_2 = new JPanel();
		pnTitleCT_2.setLayout(null);
		pnTitleCT_2.setBackground(new Color(34, 167, 240));
		pnTitleCT_2.setBounds(0, 0, 300, 60);
		pnCongTrinh_2.add(pnTitleCT_2);

		JLabel lblHoanThanh = new JLabel("Hoàn thành");
		lblHoanThanh.setForeground(Color.WHITE);
		lblHoanThanh.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblHoanThanh.setBounds(10, 10, 200, 40);
		pnTitleCT_2.add(lblHoanThanh);

		JLabel lblSLHoanThanh = new JLabel("0");
		lblSLHoanThanh.setHorizontalAlignment(SwingConstants.CENTER);
		lblSLHoanThanh.setForeground(Color.WHITE);
		lblSLHoanThanh.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblSLHoanThanh.setBounds(80, 80, 140, 40);
		pnCongTrinh_2.add(lblSLHoanThanh);

		JLabel lblMoTaHoanThanh = new JLabel("công trình");
		lblMoTaHoanThanh.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoTaHoanThanh.setForeground(Color.WHITE);
		lblMoTaHoanThanh.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblMoTaHoanThanh.setBounds(0, 140, 300, 40);
		pnCongTrinh_2.add(lblMoTaHoanThanh);

		listCTDangLam = new ArrayList<>();
		listCTHoanThanh = new ArrayList<>();
		try {
			listCTDangLam = daoCT.getDsTienDoCT("select * from congtrinh where tiendo like N'Đang thực hiện'");
			listCTHoanThanh = daoCT.getDsTienDoCT("select * from congtrinh where tiendo like N'Hoàn thành'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int countDL = listCTDangLam.size();
		int countHT = listCTHoanThanh.size();
		lblSLDangLam.setText(Integer.toString(countDL));
		lblSLHoanThanh.setText(Integer.toString(countHT));
		
		ChartPanel barChart = new ChartPanel(BDCot_ThongKetTienDoCT());
		barChart.setBounds(10, 201, 642, 468);
		pnChart.add(barChart);

		ChartPanel pieChart = new ChartPanel(BDTron_ThongKetTienDoCT());
		pieChart.setBackground(Color.WHITE);
		pieChart.setLocation(30, 315);
		pieChart.setSize(900, 680);
		pnMain.add(pieChart);
		btnHelp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

	}
	
	
	/**
	 * @return
	 */
	private static JFreeChart BDTron_ThongKetTienDoCT() {
		final String sql = "select [TienDo], (COUNT([TienDo])*100/ (select COUNT(TienDo) from CongTrinh)) as tong from CongTrinh group by TienDo"; //Lấy tiến độ công trình gồm có hoàn thành và đang thực hiện theo %
		JDBCPieDataset pieDataset = null;
		try {
			pieDataset = new JDBCPieDataset(ConnectDB.getCon(), sql); //Lấy dataset cho biểu đồ 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tienDoHT = ChartFactory.createPieChart3D("Biểu đồ tròn thể hiện phần trăm tiến độ công trình", pieDataset, true,
				true, false);
		Font font = new Font("Dialog", Font.PLAIN, 25);
		tienDoHT.getTitle().setFont(new Font("Dialog", Font.BOLD, 28));
		
		//custom biểu đồ tròn
		PiePlot plot = (PiePlot) tienDoHT.getPlot();
		plot.setLabelFont(font);
		plot.setSimpleLabels(true);
		plot.setNoDataMessage("Không có dữ liệu");
		plot.setInteriorGap(0.04);
		plot.setCircular(true);
		tienDoHT.getPlot().setBackgroundPaint(Color.WHITE);
		return tienDoHT;
	}
	private static JFreeChart BDCot_ThongKetTienDoCT() {
		final String sql = "select [TienDo], COUNT([TienDo]) as N'Công trình' from CongTrinh group by TienDo"; //Lấy tiến độ công trình gồm có hoàn thành và đang thực hiện
		JDBCCategoryDataset BarDataset = null;
		try {
			BarDataset = new JDBCCategoryDataset(ConnectDB.getCon(), sql); //Lấy dataset cho biểu đồ 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tienDoHT = ChartFactory.createBarChart3D("Biểu đồ cột thể hiện tiến độ các công trình", "Tiến độ", "Số lượng", BarDataset);
		tienDoHT.getPlot().setBackgroundPaint(Color.WHITE);
		
		//custom biểu đồ cột
		tienDoHT.getTitle().setFont(new Font("Dialog", Font.BOLD, 28));
		Font font = new Font("Dialog", Font.PLAIN, 25);
		Font font1 = new Font("Dialog", Font.PLAIN, 22);
		tienDoHT.getCategoryPlot().getDomainAxis().setLabelFont(font);
		tienDoHT.getCategoryPlot().getDomainAxis().setTickLabelFont(font1);
		tienDoHT.getCategoryPlot().getRangeAxis().setLabelFont(font);
		tienDoHT.getCategoryPlot().getRangeAxis().setTickLabelFont(font1);
		return tienDoHT;
	}
}
