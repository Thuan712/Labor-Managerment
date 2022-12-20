package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import connect.ConnectDB;
import dao.DAO_Nhanvien;
import dao.DAO_Taikhoan;
import entity.NhanVien;
import entity.TaiKhoan;
/**
 * 
 * @author nmthu
 *
 */
public class FormThemTaikhoan extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtTaikhoan;
	private JPasswordField txtMatkhau;
	private JPasswordField txtKiemtraMatKhau;
	private JLabel errorTaikhoan;
	private JLabel errorMatkhau;
	private JLabel errorMatkhauCheck;
	private JButton btnThem;
	private NhanVien nv;
	private JButton btnDong;

	/**
	 * Create the panel.
	 */
	public FormThemTaikhoan(String tenNhanvien, String gioitinh, Date ngaysinh, String phone, String cmnd, String email, String sdt, String diaChi) {
		setBackground(Color.WHITE);
		setLayout(null);
		setBounds(0, 0, 734, 521);
		
		nv = new NhanVien(tenNhanvien, gioitinh, ngaysinh, phone, cmnd, email, diaChi, "Đi Làm", null);
		
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		JPanel pnTitle = new JPanel();
		pnTitle.setBackground(new Color(255, 204, 102));
		pnTitle.setBounds(0, 0, 734, 70);
		add(pnTitle);
		pnTitle.setLayout(null);
		
		JLabel lblTitle = new JLabel("THÊM TÀI KHOẢN MỚI\r\n");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(new Color(21, 25, 28));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTitle.setBounds(0, 9, 734, 50);
		pnTitle.add(lblTitle);
		
		JLabel lblTaikhoan = new JLabel("Tên tài khoản:");
		lblTaikhoan.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTaikhoan.setBounds(10, 196, 227, 30);
		add(lblTaikhoan);

		btnThem = new JButton("Thêm");
		btnThem.setIcon(new ImageIcon("icons/plus32.png"));
		btnThem.setBackground(new Color(255, 204, 102));
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnThem.setBounds(454, 460, 130, 50);
		add(btnThem);
		
		btnDong = new JButton("Đóng");
		btnDong.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnDong.setBackground(new Color(255, 204, 102));
		btnDong.setBounds(594, 460, 130, 50);
		add(btnDong);
		
		

		txtTaikhoan = new JTextField();
		txtTaikhoan.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTaikhoan.setBounds(230, 196, 300, 30);
		add(txtTaikhoan);
		txtTaikhoan.setColumns(10);

		JLabel lblMatkhau = new JLabel("Mật khẩu:");
		lblMatkhau.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblMatkhau.setBounds(10, 241, 227, 30);
		add(lblMatkhau);

		txtMatkhau = new JPasswordField();
		txtMatkhau.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtMatkhau.setColumns(10);
		txtMatkhau.setBounds(230, 241, 300, 30);
		add(txtMatkhau);

		JLabel lblKiemtraMatkhau = new JLabel("Nhập lại mật khẩu:");
		lblKiemtraMatkhau.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblKiemtraMatkhau.setBounds(10, 281, 227, 30);
		add(lblKiemtraMatkhau);

		txtKiemtraMatKhau = new JPasswordField();
		txtKiemtraMatKhau.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtKiemtraMatKhau.setColumns(10);
		txtKiemtraMatKhau.setBounds(230, 281, 300, 30);
		add(txtKiemtraMatKhau);

		errorTaikhoan = new JLabel("(*)");
		errorTaikhoan.setFont(new Font("Tahoma", Font.PLAIN, 16));
		errorTaikhoan.setForeground(Color.red);
		errorTaikhoan.setBounds(535, 196, 195, 30);
		add(errorTaikhoan);

		errorMatkhau = new JLabel("(*)");
		errorMatkhau.setFont(new Font("Tahoma", Font.PLAIN, 16));
		errorMatkhau.setForeground(Color.red);
		errorMatkhau.setBounds(535, 241, 195, 30);
		add(errorMatkhau);

		errorMatkhauCheck = new JLabel("(*)");
		errorMatkhauCheck.setFont(new Font("Tahoma", Font.PLAIN, 16));
		errorMatkhauCheck.setForeground(Color.red);
		errorMatkhauCheck.setBounds(535, 281, 195, 30);
		add(errorMatkhauCheck);

		btnDong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDong.addActionListener(this);
		btnThem.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDong)) {
			this.setVisible(false);
		}
		if(o.equals(btnThem)) {
			DAO_Taikhoan dao_Taikhoan = new DAO_Taikhoan();
			DAO_Nhanvien dao_Nhanvien = new DAO_Nhanvien();
			if (validData()) {
				String username = txtTaikhoan.getText();
				String pass = String.valueOf(txtMatkhau.getPassword());
				TaiKhoan tk = new TaiKhoan(username, pass);
				nv.setTaikhoan(tk);
				try {
					if (dao_Taikhoan.themTaikhoan(tk) && dao_Nhanvien.themNhanvien(nv)) {
						JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FormThemTaikhoan.this);
						JOptionPane.showMessageDialog(frame, "Thêm nhân viên thành công");
						frame.dispose();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}				
		}
	}

	public boolean validData() {
		DAO_Taikhoan dao_Taikhoan = new DAO_Taikhoan();
		String taikhoan = txtTaikhoan.getText().trim();
		if (!((taikhoan.length() > 0)
				&& taikhoan.matches("(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])*.*"))) {
			errorTaikhoan.setText("Tài khoản không hợp lệ");
			txtTaikhoan.requestFocus();
			return false;
		} else
			try {
				TaiKhoan tk = null;
				tk = dao_Taikhoan.checkTenTaikhoanAvailable(txtTaikhoan.getText());
				if (tk != null) {
					errorTaikhoan.setText("Đã tồn tại");
					txtTaikhoan.requestFocus();
					return false;
				} else {
					errorTaikhoan.setText("");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		if (txtMatkhau.getPassword().length == 0) {
			errorMatkhau.setText("Mật khẩu rỗng");
			txtMatkhau.requestFocus();
			return false;
		} else {
			errorMatkhau.setText("");
		}
		if (txtKiemtraMatKhau.getPassword().length == 0) {
			errorMatkhauCheck.setText("Nhập lai mật khẩu");
			txtKiemtraMatKhau.requestFocus();
			return false;
		} else if (txtKiemtraMatKhau.getPassword().equals(txtMatkhau.getPassword())) {
			errorMatkhauCheck.setText("Không chính xác");
			txtKiemtraMatKhau.requestFocus();
			return false;
		} else {
			errorMatkhauCheck.setText("");
		}
		return true;
	}
}
