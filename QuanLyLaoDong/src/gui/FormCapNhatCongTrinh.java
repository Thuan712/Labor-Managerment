package gui;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.toedter.calendar.JDateChooser;

import connect.ConnectDB;
import dao.DAO_LoaiCongTrinh;
import dao.DAO_CongTrinh;
import entity.CongTrinh;
import entity.LoaiCongTrinh;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.SwingConstants;

import javax.swing.BorderFactory;
/**
 * 
 * @author nmthu
 *
 */
public class FormCapNhatCongTrinh extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField txtTen;
	private JTextField txtDiaDiem;

	private JDateChooser dateKC;

	private JDateChooser dateCP;

	private CongTrinh ct;

	private JDateChooser dateDK;

	private DAO_LoaiCongTrinh dao_lct = new DAO_LoaiCongTrinh();

	private JComboBox<String> cbLoai;

	private JLabel lblLoaiCngTrinh;
	private JComboBox<String> cbTiendo;

	private JPanel panel;

	private JTextField txtError;

	private JDateChooser dateKT;

	private JButton btnDong;
	private JButton btnCapNhat;

	private CongTrinh ctMoi;

	public FormCapNhatCongTrinh(String id, String idLCT) {
		setTitle("Cập nhật công trình");
		setAlwaysOnTop(true);
		setBounds(550, 250, 830, 575);
		
		contentPane = new JPanel();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setLocation(0, 0);
		panel.setSize(814, 531);
		panel.setBackground(new Color(255, 255, 255));
		contentPane.add(panel);
		panel.setLayout(null);

		
		
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JPanel pnTitle = new JPanel();
		pnTitle.setBackground(new Color(255, 204, 102));
		pnTitle.setBounds(0, 0, 814, 70);
		panel.add(pnTitle);
		pnTitle.setLayout(null);

		JLabel lblNewLabel2 = new JLabel("Cập nhật công trình");
		lblNewLabel2.setBounds(0, 9, 814, 50);
		lblNewLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel2.setForeground(Color.BLACK);
		lblNewLabel2.setFont(new Font("Tahoma", Font.BOLD, 32));
		pnTitle.add(lblNewLabel2);

		JLabel lblTenCT = new JLabel("Tên Công trình:");
		lblTenCT.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTenCT.setBounds(35, 100, 207, 30);
		panel.add(lblTenCT);

		JLabel lblDiaDiem = new JLabel("Địa điểm:");
		lblDiaDiem.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblDiaDiem.setBounds(35, 140, 207, 30);
		panel.add(lblDiaDiem);

		JLabel lblNgayCP = new JLabel("Ngày Cấp phép:");
		lblNgayCP.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNgayCP.setBounds(35, 180, 207, 30);
		panel.add(lblNgayCP);

		JLabel lblNgayKC = new JLabel("Ngày khởi công:");
		lblNgayKC.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNgayKC.setBounds(35, 220, 207, 30);
		panel.add(lblNgayKC);

		JLabel lblNgayHT = new JLabel("Ngày dự kiến hoàn thành:");
		lblNgayHT.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNgayHT.setBounds(35, 260, 306, 30);
		panel.add(lblNgayHT);

		JLabel lblNgayKT = new JLabel("Ngày kết thúc:");
		lblNgayKT.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNgayKT.setBounds(35, 300, 207, 30);
		panel.add(lblNgayKT);

		JLabel lblTienDo = new JLabel("Tiến độ:");
		lblTienDo.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTienDo.setBounds(35, 380, 207, 30);
		panel.add(lblTienDo);

		txtTen = new JTextField(20);
		txtTen.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTen.setColumns(10);
		txtTen.setBounds(339, 102, 465, 30);
		panel.add(txtTen);

		txtDiaDiem = new JTextField(20);
		txtDiaDiem.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtDiaDiem.setColumns(10);
		txtDiaDiem.setBounds(339, 142, 465, 30);
		panel.add(txtDiaDiem);

		dateCP = new JDateChooser();
		dateCP.setDateFormatString("dd/MM/yyyy");
		dateCP.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateCP.getJCalendar().setPreferredSize(new Dimension(400, 400));
		dateCP.getJCalendar().getMonthChooser().setPreferredSize(new Dimension(150, 30));
		dateCP.setBounds(339, 180, 465, 30);
		panel.add(dateCP);

		dateKC = new JDateChooser();
		dateKC.setDateFormatString("dd/MM/yyyy");
		dateKC.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateKC.getJCalendar().setPreferredSize(new Dimension(400, 400));
		dateKC.getJCalendar().getMonthChooser().setPreferredSize(new Dimension(150, 30));
		dateKC.setBounds(339, 220, 465, 30);
		panel.add(dateKC);

		dateDK = new JDateChooser();
		dateDK.setDateFormatString("dd/MM/yyyy");
		dateDK.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateDK.getJCalendar().setPreferredSize(new Dimension(400, 400));
		dateDK.getJCalendar().getMonthChooser().setPreferredSize(new Dimension(150, 30));
		dateDK.setBounds(339, 263, 465, 30);
		panel.add(dateDK);

		dateKT = new JDateChooser();
		dateKT.setDateFormatString("dd/MM/yyyy");
		dateKT.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateKT.getJCalendar().setPreferredSize(new Dimension(400, 400));
		dateKT.getJCalendar().getMonthChooser().setPreferredSize(new Dimension(150, 30));
		dateKT.setEnabled(false);
		dateKT.setBounds(339, 303, 465, 30);
		panel.add(dateKT);

		cbLoai = new JComboBox<String>();
		cbLoai.setBounds(339, 340, 465, 30);
		cbLoai.setBackground(SystemColor.window);
		cbLoai.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbLoai.setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				JButton b = super.createArrowButton();
				b.setContentAreaFilled(false);
				b.setBackground(SystemColor.control);
				b.setBorder(BorderFactory.createEmptyBorder());
				return b;
			}
		});
		panel.add(cbLoai);
		ArrayList<LoaiCongTrinh> lct;
		try {
			lct = dao_lct.getDsLoaiCT();
			for (LoaiCongTrinh loaiCongTrinh : lct) {
				cbLoai.addItem(loaiCongTrinh.getTenLoai());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		cbTiendo = new JComboBox<String>();
		cbTiendo.addItem("Đang Thực Hiện");
		cbTiendo.addItem("Hoàn thành");
		cbTiendo.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbTiendo.setBackground(SystemColor.window);
		cbTiendo.setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				JButton b = super.createArrowButton();
				b.setContentAreaFilled(false);
				b.setBackground(SystemColor.control);
				b.setBorder(BorderFactory.createEmptyBorder());
				return b;
			}
		});
		cbTiendo.setBounds(339, 380, 465, 30);
		panel.add(cbTiendo);

		txtError = new JTextField();
		txtError.setHorizontalAlignment(SwingConstants.CENTER);
		txtError.setForeground(Color.RED);
		txtError.setFont(new Font("Tahoma", Font.BOLD, 24));
		txtError.setColumns(10);
		txtError.setBounds(0, 421, 814, 30);
		txtError.setBackground(new Color(255, 255, 255));
		txtError.setEditable(false);
		txtError.setBorder(BorderFactory.createEmptyBorder());

		panel.add(txtError);

		lblLoaiCngTrinh = new JLabel("Loại công trình");
		lblLoaiCngTrinh.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblLoaiCngTrinh.setBounds(35, 340, 207, 23);
		panel.add(lblLoaiCngTrinh);

		btnCapNhat = new JButton("Cập nhật");
		btnCapNhat.setBounds(484, 470, 160, 55);
		btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnCapNhat.setBackground(new Color(255, 204, 102));
		panel.add(btnCapNhat);
		btnCapNhat.addActionListener(this);

		btnDong = new JButton("Đóng");
		btnDong.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnDong.setBackground(new Color(255, 204, 102));
		btnDong.setBounds(654, 470, 150, 55);
		panel.add(btnDong);
		btnDong.addActionListener(this);

		btnDong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCapNhat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbLoai.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbTiendo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		DAO_CongTrinh dao_ct = new DAO_CongTrinh();
		try {

			ct = dao_ct.getCongTrinhByID(id);
			System.out.println(ct);
			txtTen.setText(ct.getTenCT());
			txtDiaDiem.setText(ct.getDiaDiem());
			dateCP.setDate(ct.getNgayCapPhep());
			dateKC.setDate(ct.getNgayKhoiCong());
			dateDK.setDate(ct.getNgayDuKien());
			LoaiCongTrinh lct1 = dao_lct.getLoaiCTByName(idLCT);
			cbLoai.setSelectedItem(lct1.getTenLoai());
			cbTiendo.setSelectedItem(ct.getTienDo());

		} catch (Exception e) {
		}
		cbTiendo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) cbTiendo.getSelectedItem();
				switch (s) {

				case "Hoàn thành":
					try {
						dateKT.setEnabled(true);
					} catch (Exception e2) {
					}

					break;
				case "Đang Thực Hiện":
					try {
						dateKT.setEnabled(false);
					} catch (Exception e2) {
					}

					break;

				default:

				}
			}
		});

		// Công trình với thông tin mới
		ctMoi = new CongTrinh(null, id, null, null, null, null, null, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnCapNhat)) {
			if (validData()) {
				int xacnhan = JOptionPane.showConfirmDialog(this,
						"Bạn xác nhận muốn thay đổi thông tin Công trình này?", "Chú ý", JOptionPane.YES_NO_OPTION);
				if (xacnhan == JOptionPane.YES_OPTION) {

					DAO_CongTrinh dao_ct = new DAO_CongTrinh();

					String ten = txtTen.getText().trim();
					String diadiem = txtDiaDiem.getText().trim();
					Date dtcp = new Date(dateCP.getDate().getTime());
					Date dtkc = new Date(dateKC.getDate().getTime());
					Date dtdk = new Date(dateDK.getDate().getTime());
					String tiendo = (String) cbTiendo.getSelectedItem();

					String lctt = (String) cbLoai.getSelectedItem();

					LoaiCongTrinh lct = null;
					try {
						lct = dao_lct.getLoaiCTByName(lctt);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					ctMoi.setTenCT(ten);
					ctMoi.setDiaDiem(diadiem);
					ctMoi.setNgayCapPhep(dtcp);
					ctMoi.setNgayKhoiCong(dtkc);
					ctMoi.setNgayDuKien(dtdk);
					ctMoi.setTienDo(tiendo);
					ctMoi.setLoaiCT(lct);

					this.dispose();
					try {
						if (dao_ct.suaTTCT(ctMoi)) {
							JOptionPane.showMessageDialog(this, "Sửa thông tin Công Trình thành công");
						} else {
							JOptionPane.showMessageDialog(this,
									"Đã có lỗi phát sinh. Sửa thông tin Công Trình thất bại.");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}

	}

	public boolean validData() {
		String ten = txtTen.getText().trim();
		String diadiem = txtDiaDiem.getText().trim();
		Date dtcp = new Date(dateCP.getDate().getTime());
		Date dtkc = new Date(dateKC.getDate().getTime());
		Date dtdk = new Date(dateDK.getDate().getTime());
		// Date dtkt = new Date(dateKT.getDate().getTime());

		if (!(ten.length() > 0)) {
			txtError.setText("Vui lòng nhập tên công trình");
			txtTen.requestFocus();
			return false;
		}
		if (!(diadiem.length() > 0)) {
			txtError.setText("Vui lòng nhập địa điểm công trình");
			txtDiaDiem.requestFocus();
			return false;
		}
		if (dateCP.getDate() == null) {
			txtError.setText("Cấp phép công rỗng");
			dateCP.requestFocus();
			return false;
		}
		if (dateKC.getDate() == null) {
			txtError.setText("Ngày khởi công rỗng");
			dateKC.requestFocus();
			return false;
		}
		if (dtkc.before(dtcp)) {
			txtError.setText("Ngày khởi công phải sau ngày cấp phép");
			dateKC.requestFocus();
			return false;

		}
		if (dateDK.getDate() == null) {
			txtError.setText("Ngày dự kiến rỗng");
			dateDK.requestFocus();
			return false;
		}
		if (dtdk.before(dtkc)) {
			txtError.setText("Ngày dự kiến phải sau ngày khởi công");
			dateDK.requestFocus();
			return false;
		}

		return true;
	}
}
