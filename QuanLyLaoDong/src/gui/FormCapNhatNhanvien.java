package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import connect.ConnectDB;
import dao.DAO_Nhanvien;
import entity.NhanVien;
/**
 * 
 * @author nmthu
 *
 */
public class FormCapNhatNhanvien extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTenNV;
	private JTextField txtCMND;
	private JTextField txtEmail;
	private JTextField txtSDT;
	private JTextField txtDiachi;
	private JDateChooser ngaysinh;
	private JButton btnSuaThongtin;
	private JPanel panel_Info;
	private JLabel errorTen;
	private JLabel errorNgaysinh;
	private JLabel errorEmail;
	private JLabel errorSDT;
	private JComboBox<String> cbGioitinh;
	private NhanVien nv;
	private JButton btnDong;

	/**
	 * Create the frame.
	 */
	public FormCapNhatNhanvien(String id) {
		setTitle("Cập nhật nhân viên");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(550, 250, 720, 520);
		
		contentPane = new JPanel();
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

		JPanel pnTitle = new JPanel();
		pnTitle.setBackground(new Color(255, 204, 102));
		pnTitle.setBounds(0, 0, 704, 70);
		contentPane.add(pnTitle);
		pnTitle.setLayout(null);

		JLabel lblTitle = new JLabel("SỬA THÔNG TIN NHÂN VIÊN");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(new Color(21, 25, 28));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTitle.setBounds(0, 9, 702, 50);
		pnTitle.add(lblTitle);

		panel_Info = new JPanel();
		panel_Info.setBackground(Color.WHITE);
		panel_Info.setBounds(0, 70, 704, 411);
		contentPane.add(panel_Info);
		panel_Info.setLayout(null);

		JLabel lblTenNV = new JLabel("Tên nhân viên:");
		lblTenNV.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTenNV.setBounds(30, 30, 180, 30);
		panel_Info.add(lblTenNV);

		txtTenNV = new JTextField();
		txtTenNV.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTenNV.setBounds(212, 30, 300, 30);
		panel_Info.add(txtTenNV);
		txtTenNV.setColumns(10);

		JLabel lblGioitinh = new JLabel("Giới tính:");
		lblGioitinh.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblGioitinh.setBounds(30, 75, 180, 30);
		panel_Info.add(lblGioitinh);

		cbGioitinh = new JComboBox<String>();
		cbGioitinh.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cbGioitinh.addItem("Nam");
		cbGioitinh.addItem("Nữ");
		cbGioitinh.setBounds(212, 75, 300, 30);
		panel_Info.add(cbGioitinh);

		JLabel lblNgaysinh = new JLabel("Ngày sinh:");
		lblNgaysinh.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNgaysinh.setBounds(30, 120, 180, 30);
		panel_Info.add(lblNgaysinh);

		ngaysinh = new JDateChooser();
		ngaysinh.getCalendarButton().setFont(new Font("Tahoma", Font.PLAIN, 15));
		ngaysinh.setDateFormatString("dd/MM/yyyy");
		ngaysinh.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ngaysinh.getJCalendar().setPreferredSize(new Dimension(400, 400));
		ngaysinh.getJCalendar().getMonthChooser().setPreferredSize(new Dimension(150, 30));
		ngaysinh.setBounds(212, 120, 300, 30);
		panel_Info.add(ngaysinh);

		JLabel lblCMND = new JLabel("CMND:");
		lblCMND.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblCMND.setBounds(30, 161, 180, 30);
		panel_Info.add(lblCMND);

		txtCMND = new JTextField();
		txtCMND.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtCMND.setColumns(10);
		txtCMND.setBounds(212, 161, 300, 30);
		panel_Info.add(txtCMND);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblEmail.setBounds(30, 206, 180, 30);
		panel_Info.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtEmail.setColumns(10);
		txtEmail.setBounds(212, 206, 300, 30);
		panel_Info.add(txtEmail);

		JLabel lblSDT = new JLabel("SDT:");
		lblSDT.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblSDT.setBounds(30, 251, 180, 30);
		panel_Info.add(lblSDT);

		txtSDT = new JTextField();
		txtSDT.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtSDT.setColumns(10);
		txtSDT.setBounds(212, 251, 300, 30);
		panel_Info.add(txtSDT);

		JLabel lblDiachi = new JLabel("Địa chỉ:");
		lblDiachi.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblDiachi.setBounds(30, 296, 180, 30);
		panel_Info.add(lblDiachi);

		txtDiachi = new JTextField();
		txtDiachi.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtDiachi.setColumns(10);
		txtDiachi.setBounds(212, 296, 300, 30);
		panel_Info.add(txtDiachi);

		btnSuaThongtin = new JButton("Cập nhật");
		btnSuaThongtin.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnSuaThongtin.setBackground(new Color(255, 204, 102));
		btnSuaThongtin.setBounds(430, 360, 155, 40);
		panel_Info.add(btnSuaThongtin);

		btnDong = new JButton("Đóng");
		btnDong.addActionListener(this);
		btnDong.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnDong.setBackground(new Color(255, 204, 102));
		btnDong.setBounds(591, 360, 100, 40);
		panel_Info.add(btnDong);

		errorTen = new JLabel("(*)");
		errorTen.setForeground(Color.RED);
		errorTen.setFont(new Font("Tahoma", Font.PLAIN, 16));
		errorTen.setBounds(522, 30, 150, 30);
		panel_Info.add(errorTen);

		errorNgaysinh = new JLabel("(*)");
		errorNgaysinh.setForeground(Color.RED);
		errorNgaysinh.setFont(new Font("Tahoma", Font.PLAIN, 16));
		errorNgaysinh.setBounds(522, 120, 150, 30);
		panel_Info.add(errorNgaysinh);

		errorEmail = new JLabel("(*)");
		errorEmail.setForeground(Color.RED);
		errorEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		errorEmail.setBounds(522, 206, 150, 30);
		panel_Info.add(errorEmail);

		errorSDT = new JLabel("(*)");
		errorSDT.setForeground(Color.RED);
		errorSDT.setFont(new Font("Tahoma", Font.PLAIN, 16));
		errorSDT.setBounds(522, 251, 150, 30);
		panel_Info.add(errorSDT);

		btnSuaThongtin.addActionListener(this);
		btnDong.addActionListener(this);

		// Load thông tin nhân viên
		DAO_Nhanvien dao_Nhanvien = new DAO_Nhanvien();
		try {
			NhanVien nv = dao_Nhanvien.getNhanvienByID(id);
			txtTenNV.setText(nv.getTenNhanvien());
			cbGioitinh.setSelectedItem(nv.getGioitinh());
			ngaysinh.setDate(nv.getNgaysinh());
			txtCMND.setText(nv.getCmnd());
			txtEmail.setText(nv.getEmail());
			txtSDT.setText(nv.getPhone());
			txtDiachi.setText(nv.getDiaChi());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Nhân viên với thông tin mới
		nv = new NhanVien(id, null, null, null, null, null, null, null, null, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDong)) {
			this.dispose();
		}
		if (o.equals(btnSuaThongtin)) {
			if (validData()) {
				int xacnhan = JOptionPane.showConfirmDialog(this, "Bạn xác nhận muốn thay đổi thông tin nhân viên này?",
						"Chú ý", JOptionPane.YES_NO_OPTION);
				if (xacnhan == JOptionPane.YES_OPTION) {
					DAO_Nhanvien dao_Nhanvien = new DAO_Nhanvien();
					String tennv = txtTenNV.getText();
					String gt = (String) cbGioitinh.getSelectedItem();
					Date ns = new Date(ngaysinh.getDate().getTime());
					String cmnd = txtCMND.getText();
					String email = txtEmail.getText();
					String sdt = txtSDT.getText();
					String diachi = txtDiachi.getText();
					nv.setTenNhanvien(tennv);
					nv.setGioitinh(gt);
					nv.setNgaysinh(ns);
					nv.setPhone(sdt);
					nv.setCmnd(cmnd);
					nv.setEmail(email);
					nv.setDiaChi(diachi);
					this.dispose();
					try {
						if (dao_Nhanvien.suaThongtinNhanvien(nv)) {
							JOptionPane.showMessageDialog(this, "Sửa thông tin nhân viên thành công");
						} else {
							JOptionPane.showMessageDialog(this,
									"Đã có lỗi phát sinh. Sửa thông tin nhân viên thất bại.");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Kiểm tra dữ liệu đầu vào Rỗng return false
	 * 
	 * @return true
	 */
	public boolean validData() {
		String ten = txtTenNV.getText().trim();
		String email = txtEmail.getText().trim();
		String sdt = txtSDT.getText().trim();
		if (!(ten.length() > 0)) {
			errorTen.setText("Tên rỗng");
			txtTenNV.requestFocus();
			return false;
		} else {
			errorTen.setText("");
		}
		if (ngaysinh.getDate() == null) {
			errorNgaysinh.setText("Ngày sinh rỗng");
			ngaysinh.requestFocus();
			return false;
		} else {
			errorNgaysinh.setText("");
		}
		if (!(email.length() > 0)) {
			errorEmail.setText("Email rỗng");
			txtEmail.requestFocus();
			return false;
		} else {
			errorEmail.setText("");
		}
		if (!((sdt.length() > 0) && sdt.matches("[0-9]{10}"))) {
			errorSDT.setText("SDT không hợp lệ");
			txtSDT.requestFocus();
			return false;
		} else {
			errorSDT.setText("");
		}
		return true;
	}
}
