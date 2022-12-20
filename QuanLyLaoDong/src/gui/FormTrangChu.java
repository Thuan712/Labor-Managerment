package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import connect.ConnectDB;
import dao.DAO_Nhanvien;
import entity.NhanVien;
import entity.TaiKhoan;
/**
 * 
 * @author nmthu
 *
 */
public class FormTrangChu extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_Manage;
	private JButton btnQLCongTrinh;
	private JButton btnQLPhanCong;
	private JButton btnQLCongNhan;
	private JButton btnQLCongViec;
	private JButton btnQLNhanVien;
	private JButton btnThoat;
	private JButton btnDoiMk;
	private JLabel txtUsername;
	private NhanVien nv;
	private JButton btnTongQuan;
	private JTextField txtDate;
	private JPanel panel_center;
	/**
	 * Create the frame.
	 */
	public FormTrangChu(TaiKhoan tk) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1280, 1080);
		
		setResizable(true);
		setBackground(new Color(21, 25, 28));
		setExtendedState(Frame.MAXIMIZED_BOTH); 
		setAlwaysOnTop(false);
		setTitle("CÔNG TY TNHH XÂY DỰNG BLAKT");
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		try {
			setIconImage(ImageIO.read(new File("icons/iconFrameW.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(21, 25, 28));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(0, 0, 1920, 1080);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 300, 1020);
		panel.setBackground(new Color(21, 25, 28));
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel panel_north = new JPanel();
		panel_north.setBounds(0, 0, 300, 400);
		panel.add(panel_north);
		panel_north.setBackground(new Color(21, 25, 28));
		panel_north.setLayout(null);

		try {
			BufferedImage image = ImageIO.read(new File("images/admin.png"));
			Area clip = new Area( new Rectangle(0, 18, 260, 260) ); 
			Area oval = new Area( new Ellipse2D.Double(0, 18, 260 - 1, 260 - 1) ); 
			clip.subtract( oval ); //Khung ảnh
			Graphics g2d = image.createGraphics(); 
			g2d.setClip(clip); //Ảnh đại diện admin
			g2d.setColor(new Color(21, 25, 28));
			g2d.fillRect(0, 0, image.getWidth(), image.getHeight()); 
			JLabel lblImage = new JLabel();
			lblImage.setIcon(new ImageIcon(image));
			lblImage.setBackground(SystemColor.control);
			lblImage.setBounds(20, 20, 260, 260);
			
			panel_north.add(lblImage);
		} catch (IOException e) {
			e.printStackTrace();
		}

		DAO_Nhanvien dao_Nhanvien = new DAO_Nhanvien();
		try {
			nv = dao_Nhanvien.getNhanvienByIDTaiKhoan(tk.getmaTaikhoan());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		JLabel lblUser = new JLabel("Người dùng:");
		lblUser.setForeground(SystemColor.textHighlightText);
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblUser.setBounds(5, 290, 120, 31);
		panel_north.add(lblUser);
		
		txtDate = new JTextField();
		txtDate.setEditable(false);
		txtDate.setForeground(Color.WHITE);
		txtDate.setBackground(new Color(21, 25, 28));
		txtDate.setFont(new Font("Tahoma", Font.PLAIN, 19));
		txtDate.setBorder(null);
		txtDate.setBounds(130,370,170, 30);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		txtDate.setText(df.format(new java.util.Date()));
		panel_north.add(txtDate);
		
		
		txtUsername = new JLabel("Username");
		txtUsername.setForeground(Color.WHITE);
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 19));
		txtUsername.setBounds(130, 290, 170, 31);
		panel_north.add(txtUsername);
		panel_north.revalidate();
		panel_north.repaint();
		
		txtUsername.setText(nv.getTenNhanvien());
		
		JLabel lblQuanLy = new JLabel("QUẢN LÝ");
		lblQuanLy.setForeground(Color.WHITE);
		lblQuanLy.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblQuanLy.setBounds(130, 330, 170, 31);
		panel_north.add(lblQuanLy);
		
		JLabel lblChucVu = new JLabel("Chức vụ:");
		lblChucVu.setForeground(Color.WHITE);
		lblChucVu.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblChucVu.setBounds(5, 330, 120, 31);
		panel_north.add(lblChucVu);
		
		JLabel lblNgay = new JLabel("Ngày:");
		lblNgay.setForeground(Color.WHITE);
		lblNgay.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNgay.setBounds(5, 369, 120, 31);
		panel_north.add(lblNgay);
		
		panel_center = new JPanel();
		panel_center.setBounds(0, 400, 300, 540);
		panel.add(panel_center);
		panel_center.setBackground(new Color(21, 25, 28));
		panel_center.setLayout(null);

		btnTongQuan = new JButton("Tổng Quan           ");
		btnTongQuan.setIcon(new ImageIcon("icons/house.png"));
		btnTongQuan.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnTongQuan.setBorderPainted(false);
		btnTongQuan.setBorder(null);
		btnTongQuan.setBackground(new Color(255, 204, 102));
		btnTongQuan.setForeground(Color.BLACK);
		btnTongQuan.setFocusable(false);
		btnTongQuan.setBounds(0, 40, 298, 52);
		panel_center.add(btnTongQuan);

		// button truy cập panel Quản lý công trình
		btnQLCongTrinh = new JButton("Quản Lý Công Trình");
		btnQLCongTrinh.setIcon(new ImageIcon("icons/building.png"));
		btnQLCongTrinh.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnQLCongTrinh.setBorderPainted(false);
		btnQLCongTrinh.setBorder(null);
		btnQLCongTrinh.setBackground(new Color(255, 204, 102));
		btnQLCongTrinh.setForeground(Color.BLACK);
		btnQLCongTrinh.setFocusable(false);
		btnQLCongTrinh.setBounds(0, 200, 298, 52);
		panel_center.add(btnQLCongTrinh);

		// button truy cập panel Quản lý phân công
		btnQLPhanCong = new JButton("Quản Lý Phân Công ");
		btnQLPhanCong.setIcon(new ImageIcon("icons/megaphone.png"));
		btnQLPhanCong.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnQLPhanCong.setBorderPainted(false);
		btnQLPhanCong.setBorder(null);
		btnQLPhanCong.setBackground(new Color(255, 204, 102));
		btnQLPhanCong.setForeground(new Color(0, 0, 0));
		btnQLPhanCong.setFocusable(false);
		btnQLPhanCong.setBounds(0, 120, 298, 52);
		panel_center.add(btnQLPhanCong);

		// button truy cập panel Quản lý công nhân
		btnQLCongNhan = new JButton("Quản Lý Công Nhân ");
		btnQLCongNhan.setIcon(new ImageIcon("icons/stick-man.png"));
		btnQLCongNhan.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnQLCongNhan.setBorderPainted(false);
		btnQLCongNhan.setBorder(null);
		btnQLCongNhan.setBackground(new Color(255, 204, 102));
		btnQLCongNhan.setForeground(Color.BLACK);
		btnQLCongNhan.setFocusable(false);
		btnQLCongNhan.setBounds(0, 280, 298, 52);
		panel_center.add(btnQLCongNhan);

		// button truy cập panel Quản lý công việc
		btnQLCongViec = new JButton("Quản Lý Công Việc ");
		btnQLCongViec.setIcon(new ImageIcon("icons/clipboard.png"));
		btnQLCongViec.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnQLCongViec.setBorderPainted(false);
		btnQLCongViec.setBorder(null);
		btnQLCongViec.setBackground(new Color(255, 204, 102));
		btnQLCongViec.setForeground(Color.BLACK);
		btnQLCongViec.setFocusable(false);
		btnQLCongViec.setBounds(0, 360, 298, 52);
		panel_center.add(btnQLCongViec);

		// button truy cập panel Quản lý tài khoản nhân viên
		btnQLNhanVien = new JButton("Quản Lý Nhân Viên");
		btnQLNhanVien.setIcon(new ImageIcon("icons/id.png"));
		btnQLNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnQLNhanVien.setBorderPainted(false);
		btnQLNhanVien.setBorder(null);
		btnQLNhanVien.setBackground(new Color(255, 204, 102));
		btnQLNhanVien.setForeground(Color.BLACK);
		btnQLNhanVien.setFocusable(false);
		btnQLNhanVien.setBounds(0, 440, 298, 52);
		panel_center.add(btnQLNhanVien);


		JPanel panel_south = new JPanel();
		panel_south.setBounds(0, 940, 300, 100);
		panel.add(panel_south);
		panel_south.setBackground(new Color(21, 25, 28));
		panel_south.setLayout(null);

		// button thoát chương trình
		btnThoat = new JButton();
		btnThoat.setIcon(new ImageIcon("icons/logout.png"));
		btnThoat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnThoat.setBorderPainted(false);
		btnThoat.setBorder(null);
		btnThoat.setBackground(new Color(21, 25, 28));
		btnThoat.setFocusable(false);
		btnThoat.setBounds(10, 10, 59, 43);
		panel_south.add(btnThoat);

		// button đổi mật khẩu
		btnDoiMk = new JButton("Thay đổi mật khẩu?");
		btnDoiMk.setForeground(Color.WHITE);
		btnDoiMk.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnDoiMk.setBorderPainted(false);
		btnDoiMk.setBorder(null);
		btnDoiMk.setBackground(new Color(21, 25, 28));
		btnDoiMk.setFocusable(false);
		btnDoiMk.setBounds(130, 10, 170, 30);
		panel_south.add(btnDoiMk);
		
		// Đăng ký sự kiện
		btnTongQuan.addActionListener(this);
		btnQLCongTrinh.addActionListener(this);
		btnQLCongNhan.addActionListener(this);
		btnQLCongViec.addActionListener(this);
		btnQLNhanVien.addActionListener(this);
		btnQLPhanCong.addActionListener(this);
		btnDoiMk.addActionListener(this);
		btnThoat.addActionListener(this);

		// panel xử lý thông tin trong chương trình
		panel_Manage = new JPanel();
		panel_Manage.setBounds(300, 0, 1620, 1020);
		panel_Manage.add(new FormTongQuan());
		contentPane.add(panel_Manage);
		panel_Manage.setLayout(null);
		
		btnDoiMk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnQLCongNhan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnQLCongTrinh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnQLCongViec.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnQLNhanVien.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnQLPhanCong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThoat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTongQuan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnTongQuan)) {
			buttonPressed(btnTongQuan);
			changeScreen(new FormTongQuan());
		}
		if (o.equals(btnQLNhanVien)) {
			buttonPressed(btnQLNhanVien);
			changeScreen(new FormQLNhanVien());
		}
		if (o.equals(btnQLPhanCong)) {
			buttonPressed(btnQLPhanCong);
			changeScreen(new FormQLPhanCong(nv.getMaNhanvien()));
		}
		if (o.equals(btnQLCongNhan)) {
			buttonPressed(btnQLCongNhan);
			changeScreen(new FormQLCongNhan());
		}
		if (o.equals(btnQLCongTrinh)) {
			buttonPressed(btnQLCongTrinh);
			changeScreen(new FormQLCongTrinh());
		}
		if (o.equals(btnQLCongViec)) {
			buttonPressed(btnQLCongViec);
			changeScreen(new FormQLCongViec(nv.getMaNhanvien()));
		}
		if(o.equals(btnDoiMk)) {
			FormDoiMatkhau form = new FormDoiMatkhau(nv.getTaikhoan());
			form.setVisible(true);
			form.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					Frame[] frame = FormTrangChu.getFrames();
					for (Frame frame2 : frame) {
						frame2.setVisible(false);
					}
					new FormDangNhap().setVisible(true);
				}
			});
		}
		if (o.equals(btnThoat)) {
			this.dispose();
			FormDangNhap Frm = new FormDangNhap();
			Frm.setVisible(true);
		}
	}

	// thay đổi màn hình xử lý của giao diện
	public void changeScreen(JPanel newScreen) {

		panel_Manage.removeAll();
		panel_Manage.add(newScreen);
		panel_Manage.repaint();
		panel_Manage.revalidate();

	}
	public void buttonPressed(JButton button) {
		//panel_center.removeAll();
		panel_Manage.repaint();
		panel_Manage.revalidate();
		if(button.equals(btnTongQuan)) {
			button.setBackground(new Color(255, 238, 204));
			btnQLCongNhan.setBackground(new Color(255, 204, 102));
			btnQLCongTrinh.setBackground(new Color(255, 204, 102));
			btnQLCongViec.setBackground(new Color(255, 204, 102));
			btnQLPhanCong.setBackground(new Color(255, 204, 102));
			btnQLNhanVien.setBackground(new Color(255, 204, 102));
		}
		if(button.equals(btnQLCongNhan)) {
			button.setBackground(new Color(255, 238, 204));
			btnTongQuan.setBackground(new Color(255, 204, 102));
			btnQLCongTrinh.setBackground(new Color(255, 204, 102));
			btnQLCongViec.setBackground(new Color(255, 204, 102));
			btnQLPhanCong.setBackground(new Color(255, 204, 102));
			btnQLNhanVien.setBackground(new Color(255, 204, 102));
		}
		else if(button.equals(btnQLCongTrinh)) {
			button.setBackground(new Color(255, 238, 204));
			btnTongQuan.setBackground(new Color(255, 204, 102));
			btnQLCongNhan.setBackground(new Color(255, 204, 102));
			btnQLCongViec.setBackground(new Color(255, 204, 102));
			btnQLPhanCong.setBackground(new Color(255, 204, 102));
			btnQLNhanVien.setBackground(new Color(255, 204, 102));
		}
		else if(button.equals(btnQLCongViec)) {
			button.setBackground(new Color(255, 238, 204));
			btnTongQuan.setBackground(new Color(255, 204, 102));
			btnQLCongNhan.setBackground(new Color(255, 204, 102));
			btnQLCongTrinh.setBackground(new Color(255, 204, 102));
			btnQLPhanCong.setBackground(new Color(255, 204, 102));
			btnQLNhanVien.setBackground(new Color(255, 204, 102));
		}
		else if(button.equals(btnQLPhanCong)) {
			button.setBackground(new Color(255, 238, 204));
			btnTongQuan.setBackground(new Color(255, 204, 102));
			btnQLCongViec.setBackground(new Color(255, 204, 102));
			btnQLCongNhan.setBackground(new Color(255, 204, 102));
			btnQLCongTrinh.setBackground(new Color(255, 204, 102));
			btnQLNhanVien.setBackground(new Color(255, 204, 102));
		}
		else if(button.equals(btnQLNhanVien)) {
			button.setBackground(new Color(255, 238, 204));
			btnTongQuan.setBackground(new Color(255, 204, 102));
			btnQLCongNhan.setBackground(new Color(255, 204, 102));
			btnQLCongTrinh.setBackground(new Color(255, 204, 102));
			btnQLCongViec.setBackground(new Color(255, 204, 102));
			btnQLPhanCong.setBackground(new Color(255, 204, 102));
		}
	}
}
