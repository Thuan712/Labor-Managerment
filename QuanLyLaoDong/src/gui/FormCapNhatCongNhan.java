package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.toedter.calendar.JDateChooser;

import connect.ConnectDB;
import dao.DAO_CongNhan;
import dao.DAO_TrinhDo;
import entity.CongNhan;
import entity.TrinhDo;
/**
 * 
 * @author nmthu
 *
 */
public class FormCapNhatCongNhan extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel lblTrinhDo;

	private JLabel lblEmail;
	private JLabel lblNgaySinh;
	private JPanel contentPane;
	private JLabel lblDiaChi;
	private JLabel lblCmnd;
	private JLabel lblGioiTinh;
	private JLabel lblHoTen;
	private JButton btnCapNhat;
	private JButton btnDong;
	private JTextField txtHoTen;
	private JTextField txtCmnd;
	private JTextField txtEmail;
	private JTextField txtDiaChi;
	private JDateChooser dateNgaySinh;
	private JComboBox<String> cbboxTrinhDo;
	private JComboBox<String> cbGioitinh;
	private JTextField txtPhone;
	private DAO_TrinhDo daoTrinhDo = new DAO_TrinhDo();
	private JTextField txtError;
	private CongNhan cnMoi;
	private CongNhan cn;
	private DAO_TrinhDo daoTD = new DAO_TrinhDo();

	/**
	 * Create the frame.
	 */

	public FormCapNhatCongNhan(String idCN, String idTD) {

		setAlwaysOnTop(true);
		setBounds(550, 250, 715, 700);
		setTitle("CẬP NHẬT THÔNG TIN CÔNG NHÂN");
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		try {
			setIconImage(ImageIO.read(new File("icons/iconFrameW.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 701, 653);
		contentPane.add(panel);
		panel.setLayout(null);

		lblTrinhDo = new JLabel("Trình độ:");
		lblTrinhDo.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTrinhDo.setBounds(35, 420, 165, 35);
		panel.add(lblTrinhDo);

		lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblDiaChi.setBounds(35, 375, 165, 35);
		panel.add(lblDiaChi);

		lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblEmail.setBounds(35, 280, 165, 35);
		panel.add(lblEmail);

		lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNgaySinh.setBounds(35, 190, 165, 35);
		panel.add(lblNgaySinh);

		lblCmnd = new JLabel("CMND:");
		lblCmnd.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblCmnd.setBounds(35, 235, 165, 35);
		panel.add(lblCmnd);

		lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblGioiTinh.setBounds(35, 145, 165, 35);
		panel.add(lblGioiTinh);

		lblHoTen = new JLabel("Họ và tên:");
		lblHoTen.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblHoTen.setBounds(35, 100, 165, 35);
		panel.add(lblHoTen);

		btnCapNhat = new JButton("Cập nhật");
		btnCapNhat.setBounds(370, 583, 160, 60);
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnCapNhat.setBackground(new Color(255, 204, 102));
		panel.add(btnCapNhat);

		btnDong = new JButton("Đóng");
		btnDong.addActionListener(this);
		btnDong.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnDong.setBackground(new Color(255, 204, 102));
		btnDong.setBounds(540, 583, 150, 60);
		panel.add(btnDong);

		txtError = new JTextField();
		txtError.setHorizontalAlignment(SwingConstants.CENTER);
		txtError.setForeground(Color.RED);
		txtError.setFont(new Font("Tahoma", Font.BOLD, 26));
		txtError.setColumns(10);
		txtError.setBounds(200, 520, 460, 35);
		txtError.setBackground(new Color(255, 255, 255));
		txtError.setEditable(false);
		txtError.setBorder(BorderFactory.createEmptyBorder());

		panel.add(txtError);

		txtHoTen = new JTextField(20);
		txtHoTen.setColumns(10);
		txtHoTen.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtHoTen.setBounds(200, 100, 460, 35);
		panel.add(txtHoTen);

		txtCmnd = new JTextField(20);
		txtCmnd.setColumns(10);
		txtCmnd.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtCmnd.setBounds(200, 235, 460, 35);
		panel.add(txtCmnd);

		txtEmail = new JTextField(20);
		txtEmail.setColumns(10);
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtEmail.setBounds(200, 280, 460, 35);
		panel.add(txtEmail);

		txtDiaChi = new JTextField(20);
		txtDiaChi.setColumns(10);
		txtDiaChi.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtDiaChi.setBounds(200, 375, 460, 35);
		panel.add(txtDiaChi);

		dateNgaySinh = new JDateChooser();
		dateNgaySinh.setBounds(200, 190, 460, 35);
		dateNgaySinh.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateNgaySinh.getJCalendar().setPreferredSize(new Dimension(400, 400));
		dateNgaySinh.getJCalendar().getMonthChooser().setPreferredSize(new Dimension(150, 30));
		panel.add(dateNgaySinh);

		cbboxTrinhDo = new JComboBox<String>();
		cbboxTrinhDo.setBounds(200, 422, 460, 35);
		cbboxTrinhDo.setBackground(SystemColor.window);
		cbboxTrinhDo.setFont(new Font("Tahoma", Font.PLAIN, 26));
		cbboxTrinhDo.setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				JButton b = super.createArrowButton();
				b.setContentAreaFilled(false);
				b.setBackground(SystemColor.control);
				b.setBorder(BorderFactory.createEmptyBorder());
				return b;
			}
		});
		panel.add(cbboxTrinhDo);
		ArrayList<TrinhDo> listTD = daoTD.getDsTrinhDo();
		for (TrinhDo trinhDo : listTD) {
			cbboxTrinhDo.addItem(trinhDo.getTenTrinhDo());
		}

		JPanel pnTitle = new JPanel();
		pnTitle.setBackground(new Color(255, 204, 102));
		pnTitle.setBounds(0, 0, 701, 70);
		panel.add(pnTitle);
		pnTitle.setLayout(null);

		JLabel lblTitle = new JLabel("CẬP NHẬT THÔNG TIN CÔNG NHÂN\r\n");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(new Color(21, 25, 28));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTitle.setBounds(0, 9, 697, 50);
		pnTitle.add(lblTitle);

		cbGioitinh = new JComboBox<String>();
		cbGioitinh.addItem("Nam");
		cbGioitinh.addItem("Nữ");
		cbGioitinh.setFont(new Font("Tahoma", Font.PLAIN, 26));
		cbGioitinh.setBackground(SystemColor.window);
		cbGioitinh.setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				JButton b = super.createArrowButton();
				b.setContentAreaFilled(false);
				b.setBackground(SystemColor.control);
				b.setBorder(BorderFactory.createEmptyBorder());
				return b;
			}
		});
		cbGioitinh.setBounds(200, 145, 460, 35);
		panel.add(cbGioitinh);

		JLabel lblSDT = new JLabel("SDT:");
		lblSDT.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblSDT.setBounds(35, 325, 165, 35);
		panel.add(lblSDT);

		txtPhone = new JTextField(20);
		txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtPhone.setColumns(10);
		txtPhone.setBounds(200, 325, 460, 35);
		panel.add(txtPhone);

		cbboxTrinhDo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbGioitinh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCapNhat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDong.addActionListener(this);
		btnCapNhat.addActionListener(this);

		DAO_CongNhan daoCN = new DAO_CongNhan();

		try {
			cn = daoCN.getCongNhanByID(idCN);
			txtHoTen.setText(cn.getTenCongNhan());
			cbGioitinh.setSelectedItem(cn.getGioiTinh());
			txtPhone.setText(cn.getPhone());
			dateNgaySinh.setDate(cn.getNgaySinh());
			txtCmnd.setText(cn.getcMND());
			txtEmail.setText(cn.getEmail());
			txtDiaChi.setText(cn.getDiaChi());
//			txtChuyenMon.setText(cn.getChuyenMon());
			TrinhDo trinhDo = daoTrinhDo.getTrinhDoByID(idTD);
			cbboxTrinhDo.setSelectedItem(trinhDo.getTenTrinhDo());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		cnMoi = new CongNhan(idCN, null, null, null, null, null, null, null, null, null);
	}

	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDong)) {
			setVisible(false);
		}
		if (o.equals(btnCapNhat)) {
			if (validData()) {
				int xacnhan = JOptionPane.showConfirmDialog(this, "Bạn xác nhận muốn thay đổi thông tin công nhân này?",
						"Chú ý", JOptionPane.YES_NO_OPTION);
				if (xacnhan == JOptionPane.YES_OPTION) {
					DAO_CongNhan daoCongNhan = new DAO_CongNhan();
					String ten = txtHoTen.getText().trim();
					String gt = (String) cbGioitinh.getSelectedItem();
					Date ns = new Date(dateNgaySinh.getDate().getTime());
					String cmnd = txtCmnd.getText().trim();
					String email = txtEmail.getText().trim();
					String sdt = txtPhone.getText().trim();
					String diaChi = txtDiaChi.getText().trim();
					String tenTD = (String) cbboxTrinhDo.getSelectedItem();
					TrinhDo td = daoTrinhDo.getTrinhDoByName(tenTD);
					String trangThai = cn.getTrangThai();

					cnMoi.setTenCongNhan(ten);
					cnMoi.setGioiTinh(gt);
					cnMoi.setNgaySinh(ns);
					cnMoi.setcMND(cmnd);
					cnMoi.setPhone(sdt);
					cnMoi.setEmail(email);
					cnMoi.setDiaChi(diaChi);
					cnMoi.setTrangThai(trangThai);
					cnMoi.setTrinhDo(td);
					if (daoCongNhan.updateCongNhan(cnMoi)) {
						JOptionPane.showMessageDialog(this, "Sửa thành công");
						this.dispose();

					} else {
						JOptionPane.showMessageDialog(this, "Sửa không thành công");
					}
				}
			}
		}

	}

	public boolean validData() {
		String ten = txtHoTen.getText().trim();
		String cmnd = txtCmnd.getText().trim();
		String email = txtEmail.getText().trim();
		String sdt = txtPhone.getText().trim();
		String diaChi = txtDiaChi.getText().trim();
		if (!(ten.length() > 0)) {
			txtError.setText("Vui lòng nhập tên công nhân");
			txtHoTen.requestFocus();
			return false;
		} else {
			if (!txtHoTen.getText().trim().matches("[\\p{L}\\.+\\s]+")) {
				JOptionPane.showMessageDialog(this,
						"Tên công nhân không hợp lệ!\n Tên công nhân chỉ chứa chữ, không chứa số hay kí tự đặc biệt",
						"Chú ý", JOptionPane.ERROR_MESSAGE);
				txtHoTen.requestFocus();
				return false;
			}
			txtError.setText("");
		}
		if ((dateNgaySinh.getDate() == null)) {
			txtError.setText("Vui lòng chọn tên ngày sinh");
			dateNgaySinh.requestFocus();
			return false;
		} else {
			txtError.setText("");
		}
		if (!(cmnd.length() > 0)) {
			txtError.setText("Vui lòng nhập CMND");
			txtCmnd.requestFocus();
			return false;
		} else {
			if (!txtCmnd.getText().trim().matches("[0-9]{1,9}")) {
				JOptionPane.showMessageDialog(this, "CMND không hợp lệ!\n CMND chỉ chứa số, không chứ các kí tự",
						"Chú ý", JOptionPane.ERROR_MESSAGE);
				txtCmnd.requestFocus();
				return false;
			}
			txtError.setText("");
		}
		if (!(email.length() > 0)) {
			txtError.setText("Vui lòng nhập địa chỉ email");
			txtEmail.requestFocus();
			return false;
		} else {
			txtError.setText("");
		}
		if (!(sdt.length() > 0)) {
			txtError.setText("Vui lòng nhập số điện thoại");
			txtPhone.requestFocus();
			return false;
		} else {
			if (!txtPhone.getText().trim().matches("[0-9]{1,10}")) {
				JOptionPane.showMessageDialog(this, "SDT không hợp lệ!\n SDT chỉ chứa số, không chứ các kí tự", "Chú ý",
						JOptionPane.ERROR_MESSAGE);
				txtPhone.requestFocus();
				return false;
			}
			txtError.setText("");
		}
		if (!(diaChi.length() > 0)) {
			txtError.setText("Vui lòng nhập địa chỉ nơi ở");
			txtDiaChi.requestFocus();
			return false;
		} else {
			if (!txtHoTen.getText().trim().matches("[\\p{L}\\s[0-9]*\\/\\.,]+")) {
				JOptionPane.showMessageDialog(this, "Địa chỉ không hợp lệ!\n Địa chỉ chứa chữ, số và các kí tự /-,.",
						"Chú ý", JOptionPane.ERROR_MESSAGE);
				txtHoTen.requestFocus();
				return false;
			}
			txtError.setText("");
		}
		
		return true;
	}
}
