package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import connect.ConnectDB;
import dao.DAO_BangPhanCong;
import dao.DAO_CongNhan;
import dao.DAO_CongTrinh;
import dao.DAO_CongViec;
import dao.DAO_Nhanvien;
//import dao.DAO_Chuyenmon;
import entity.BangPhanCong;
import entity.CongNhan;
import entity.CongTrinh;
import entity.CongViec;
import entity.NhanVien;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * 
 * @author nmthu
 *
 */
public class FormThemCongViec extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtTenCV;
	private JPanel contentPane;
	private JTextField txtSoluongLD;
	private JButton btnThem;
	private JButton btnDong;
	private JDateChooser dcNgayBD;
	private CongViec cv;
	private JCheckBox checkTuDongPhanCong;
	private String manv;
	private JLabel lblMota;
	private JScrollPane scrollPane;
	private JTextArea taMota;
	private JComboBox<String> cbCongtrinh;
	private JTextField txtError;

	/**
	 * Create the frame.
	 */
	public FormThemCongViec(String maCongTrinh, String maNhanvien) {
		setTitle("Thêm công việc");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(757, 632);
		setBounds(550, 250, 725, 632);

		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Button.light"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		try {
			setIconImage(ImageIO.read(new File("icons/iconFrameW.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		DAO_CongTrinh dao_Congtrinh = new DAO_CongTrinh();
//		DAO_Chuyenmon dao_cm = new DAO_Chuyenmon();

		JPanel panel_ThemCV = new JPanel();
		panel_ThemCV.setBounds(0, 70, 707, 515);
		panel_ThemCV.setLayout(null);
		panel_ThemCV.setBackground(new Color(255, 255, 255));
		contentPane.add(panel_ThemCV);

		JLabel lblTenCV = new JLabel("Tên công việc :");
		lblTenCV.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTenCV.setBounds(20, 30, 195, 30);
		panel_ThemCV.add(lblTenCV);

		txtTenCV = new JTextField();
		txtTenCV.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTenCV.setBounds(239, 30, 458, 35);
		panel_ThemCV.add(txtTenCV);

		JLabel lblSoluongLD = new JLabel("Số lượng lao động:");
		lblSoluongLD.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblSoluongLD.setBounds(20, 71, 219, 30);
		panel_ThemCV.add(lblSoluongLD);

		txtSoluongLD = new JTextField();
		txtSoluongLD.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtSoluongLD.setBounds(239, 71, 458, 35);
		panel_ThemCV.add(txtSoluongLD);

		JLabel lblNgayBD = new JLabel("Ngày bắt đầu:");
		lblNgayBD.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNgayBD.setBounds(20, 163, 195, 30);
		panel_ThemCV.add(lblNgayBD);

		dcNgayBD = new JDateChooser();
		dcNgayBD.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dcNgayBD.getJCalendar().setPreferredSize(new Dimension(400, 400));
		dcNgayBD.getJCalendar().getMonthChooser().setPreferredSize(new Dimension(150, 30));
		dcNgayBD.setBounds(239, 163, 297, 30);
		panel_ThemCV.add(dcNgayBD);

		btnThem = new JButton("Thêm");
		btnThem.setBackground(Color.ORANGE);
		btnThem.setIcon(new ImageIcon("icons/plus.png"));
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnThem.setBounds(428, 455, 130, 50);
		panel_ThemCV.add(btnThem);

		btnDong = new JButton("Đóng");
		btnDong.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnDong.setBackground(Color.ORANGE);
		btnDong.setBounds(567, 455, 130, 50);
		panel_ThemCV.add(btnDong);

		checkTuDongPhanCong = new JCheckBox("Tự động phân công");
		checkTuDongPhanCong.setFont(new Font("Tahoma", Font.PLAIN, 24));
		checkTuDongPhanCong.setSelected(true);
		checkTuDongPhanCong.setBounds(239, 114, 233, 30);
		panel_ThemCV.add(checkTuDongPhanCong);

		JLabel lblChuyenmon = new JLabel("Chuyên môn:");
		lblChuyenmon.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblChuyenmon.setBounds(20, 204, 195, 30);
		panel_ThemCV.add(lblChuyenmon);

		lblMota = new JLabel("Mô tả:");
		lblMota.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblMota.setBounds(20, 286, 195, 30);
		panel_ThemCV.add(lblMota);

//		Set<String> chuyenmons = new HashSet<String>();
//		try {
//			chuyenmons = dao_cm.getChuyenmons();
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//		cbChuyenmon = new JComboBox<String>();
//		cbChuyenmon.setFont(new Font("Tahoma", Font.PLAIN, 24));
//		for (String string : chuyenmons) {
//			cbChuyenmon.addItem(string);
//		}
//		cbChuyenmon.setBounds(239, 204, 297, 30);
//		panel_ThemCV.add(cbChuyenmon);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(239, 286, 458, 116);
		panel_ThemCV.add(scrollPane);

		taMota = new JTextArea();
		taMota.setFont(new Font("Monospaced", Font.PLAIN, 24));
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(taMota);

		JLabel lblCongtrinh = new JLabel("Công trình:");
		lblCongtrinh.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblCongtrinh.setBounds(20, 245, 195, 30);
		panel_ThemCV.add(lblCongtrinh);

		List<CongTrinh> list = new ArrayList<CongTrinh>();
		try {
			list = dao_Congtrinh.getDsCongTrinh();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		cbCongtrinh = new JComboBox<String>();
		cbCongtrinh.setFont(new Font("Tahoma", Font.PLAIN, 24));
		for (CongTrinh congTrinh : list) {
			String ct = congTrinh.getMaCT();
			cbCongtrinh.addItem(ct);
		}
		cbCongtrinh.setSelectedItem(maCongTrinh);
		cbCongtrinh.setBounds(239, 245, 297, 30);
		panel_ThemCV.add(cbCongtrinh);

		JPanel panel_Title = new JPanel();
		panel_Title.setLayout(null);
		panel_Title.setBackground(new Color(255, 204, 102));
		panel_Title.setBounds(0, 0, 707, 70);
		contentPane.add(panel_Title);

		JLabel lblTitle = new JLabel("Thêm công việc");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(new Color(21, 25, 28));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTitle.setBounds(0, 9, 707, 50);
		panel_Title.add(lblTitle);

		txtError = new JTextField();
		txtError.setForeground(Color.RED);
		txtError.setBackground(Color.WHITE);
		txtError.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtError.setEditable(false);
		txtError.setBorder(BorderFactory.createEmptyBorder());
		txtError.setHorizontalAlignment(SwingConstants.CENTER);
		txtError.setBounds(20, 412, 677, 30);
		panel_ThemCV.add(txtError);
		txtError.setColumns(10);

		btnDong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//		cbChuyenmon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbCongtrinh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		// đăng ký sự kiện
		btnThem.addActionListener(this);
		btnDong.addActionListener(this);

		// tạo 1 công việc mới
		manv = maNhanvien;
		try {
			cv = new CongViec(null, null, null, null);
			System.out.println(cv);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			DAO_Nhanvien dao_Nhanvien = new DAO_Nhanvien();
			DAO_CongTrinh dao_Congtrinh = new DAO_CongTrinh();
			DAO_CongViec dao_Congviec = new DAO_CongViec();
			DAO_BangPhanCong dao_Bpc = new DAO_BangPhanCong();
			DAO_CongNhan dao_Congnhan = new DAO_CongNhan();

			if(validData()) {
				try {
					String mact = (String) cbCongtrinh.getSelectedItem();
//					String cm = (String) cbChuyenmon.getSelectedItem();
					Date ngayBD = new Date(dcNgayBD.getDate().getTime());
					CongTrinh ct = dao_Congtrinh.getCongTrinhByID(mact);
					NhanVien nv = dao_Nhanvien.getNhanvienByID(manv);
					cv.setTenCongViec(txtTenCV.getText());
					cv.setNgayBatDauCV(ngayBD);
//					cv.setChuyenmon(cm);
//					cv.setMota(taMota.getText());
					cv.setCongtrinh(ct);
					cv.setNhanvien(nv);
					if (dao_Congviec.themCongviec(cv)) {
						ArrayList<CongNhan> listCN;
						try {
							listCN = dao_Congnhan.getDSCongNhan("select * from CongNhan where TrangThai = N'Đang rảnh'");
							int n =Integer.parseInt(txtSoluongLD.getText());
							if(listCN.size() > n) {
								for (int i = 0; i < n; i++) {
									BangPhanCong bpc = new BangPhanCong(cv, listCN.get(i), Date.valueOf(LocalDate.now()));
									if (dao_Bpc.taoBangPhancong(bpc)) {
										dao_Congnhan.thaydoiTrangthaiCongnhan(listCN.get(i).getiDCongNhan(), "Đang làm");
									}
								}
								JOptionPane.showMessageDialog(this, "Thêm công việc thành công");
								this.dispose();
							}
							else {
								ArrayList<CongNhan> list = dao_Congnhan.getDSCongNhan("select * from CongNhan where TrangThai = N'Đang rảnh'");
								for (CongNhan congNhan : list) {
									listCN.add(congNhan);
								}
								if (listCN.size() < n) {
									JOptionPane.showMessageDialog(this,
											"Chỉ có thể thêm " + listCN.size() + " công nhân vào công việc");
									for (int i = 0; i < listCN.size(); i++) {
										BangPhanCong bpc = new BangPhanCong(cv, listCN.get(i), Date.valueOf(LocalDate.now()));
										if (dao_Bpc.taoBangPhancong(bpc)) {
											dao_Congnhan.thaydoiTrangthaiCongnhan(listCN.get(i).getiDCongNhan(), "Đang làm");
										}
									}
									JOptionPane.showMessageDialog(this, "Thêm công việc thành công");
									this.dispose();
								} else {
									for (int i = 0; i < n; i++) {
										BangPhanCong bpc = new BangPhanCong(cv, listCN.get(i), Date.valueOf(LocalDate.now()));
										if (dao_Bpc.taoBangPhancong(bpc)) {
											dao_Congnhan.thaydoiTrangthaiCongnhan(listCN.get(i).getiDCongNhan(), "Đang làm");
										}
									}
									JOptionPane.showMessageDialog(this, "Thêm công việc thành công");
									this.dispose();
								}
								
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(this, "Đã có lỗi xảy ra. Vui lòng thử lại");
						}
					} else {
						JOptionPane.showMessageDialog(this, "Đã có lỗi xảy ra. Vui lòng thử lại");
					}
//						else {
//							if (dao_Congviec.themCongviec(cv)) {
//								JOptionPane.showMessageDialog(this, "Thêm công việc thành công");
//								this.dispose();
//							} else
//								JOptionPane.showMessageDialog(this, "Đã có lỗi xảy ra. Vui lòng thử lại");
//						}
//					} else {
//						JOptionPane.showMessageDialog(this, "Thêm công việc thất bại.\nTồn tại công việc có chuyên môn được chọn!");
//					}
						
				} catch (SQLException e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(this, "Đã có lỗi xảy ra. Vui lòng thử lại");
				}
			}
			
			}
					else if (o.equals(btnDong)) {
						this.dispose();
					}
			}
	public boolean validData() {
		String ten = txtTenCV.getText().trim();
		String soluong = txtSoluongLD.getText().trim();
		if (!(ten.length() > 0)) {
			JOptionPane.showMessageDialog(this, "Tên công việc không được rỗng");
			txtTenCV.requestFocus();
			return false;
		}
		if (checkTuDongPhanCong.isSelected()) {
			if (!(soluong.length() > 0)) {
				JOptionPane.showMessageDialog(this,
						"Không xác định được số lượng cần tự động thêm./nVui lòng nhập số lượng lao động");
				txtSoluongLD.requestFocus();
				return false;
			} else if (!(soluong.matches("^[0-9]*$"))) {
				JOptionPane.showMessageDialog(this, "Số lượng lao động không hợp lệ.\nVui long không nhập chữ");
				txtSoluongLD.requestFocus();
				return false;
			}
		}
		if (dcNgayBD.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày bắt đầu công việc");
			dcNgayBD.requestFocus();
			return false;
		}
		return true;
	}
}
