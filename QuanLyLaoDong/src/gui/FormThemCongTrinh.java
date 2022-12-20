package gui;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import dao.DAO_LoaiCongTrinh;
import dao.DAO_CongTrinh;
import entity.CongTrinh;

import entity.LoaiCongTrinh;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
/**
 * 
 * @author nmthu
 *
 */
public class FormThemCongTrinh extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	
	
	private JTextField txtTen;
	private JTextField txtDiaDiem;
	private JDateChooser dateKC;
	private JDateChooser dateCP;

	private JDateChooser dateDK;
	private DAO_LoaiCongTrinh dao_lct = new DAO_LoaiCongTrinh();
	private DAO_CongTrinh dao_ct = new DAO_CongTrinh();
	private JComboBox<String> cbLoai;

	private JComboBox<String> cbTienDo;

	private JPanel panel;
	private JButton btnThem;
	private JButton btnDong;

	private JTextField txtError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormThemCongTrinh frame = new FormThemCongTrinh();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormThemCongTrinh() {
		setTitle("Thêm công trình");
		setAlwaysOnTop(true);
		setBounds(550, 250, 830, 565);
		
		
		contentPane = new JPanel();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		try {
			setIconImage(ImageIO.read(new File("icons/iconFrameW.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		panel = new JPanel();
		panel.setLocation(0, 0);
		panel.setSize(814, 525);
		panel.setBackground(new Color(255, 255, 255));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel pnTitle = new JPanel();
		pnTitle.setBackground(new Color(255, 204, 102));
		pnTitle.setBounds(0, 0, 814, 70);
		panel.add(pnTitle);
		pnTitle.setLayout(null);
		JLabel lblNewLabel = new JLabel("THÊM CÔNG TRÌNH");
		lblNewLabel.setBounds(0, 9, 814, 50);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
		pnTitle.add(lblNewLabel);

	

		JLabel lblNewLabel_1_1 = new JLabel("Tên Công trình:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel_1_1.setBounds(35, 100, 300, 30);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Địa điểm:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel_1_2.setBounds(35, 140, 300, 30);
		panel.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_3 = new JLabel("Ngày Cấp phép:");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel_1_3.setBounds(35, 180, 300, 30);
		panel.add(lblNewLabel_1_3);

		JLabel lblNewLabel_1_4 = new JLabel("Ngày khởi công:");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel_1_4.setBounds(35, 220, 300, 30);
		panel.add(lblNewLabel_1_4);

		JLabel lblNewLabel_1_5 = new JLabel("Ngày dự kiến hoàn thành:");
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel_1_5.setBounds(35, 263, 300, 30);
		panel.add(lblNewLabel_1_5);

		JLabel lblNewLabel_1_5_1 = new JLabel("Loại Công trình:");
		lblNewLabel_1_5_1.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel_1_5_1.setBounds(35, 300, 300, 30);
		panel.add(lblNewLabel_1_5_1);

		JLabel lblNewLabel_1_5_2 = new JLabel("Tiến độ:");
		lblNewLabel_1_5_2.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel_1_5_2.setBounds(35, 340, 300, 30);
		panel.add(lblNewLabel_1_5_2);

		txtTen = new JTextField(20);
		txtTen.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTen.setColumns(10);
		txtTen.setBounds(345, 100, 459, 30);
		panel.add(txtTen);

		txtDiaDiem = new JTextField(20);
		txtDiaDiem.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtDiaDiem.setColumns(10);
		txtDiaDiem.setBounds(345, 140, 459, 30);
		panel.add(txtDiaDiem);

		dateCP = new JDateChooser();
		dateCP.setDateFormatString("dd/MM/yyyy");
		dateCP.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateCP.getJCalendar().setPreferredSize(new Dimension(400, 400));
		dateCP.getJCalendar().getMonthChooser().setPreferredSize(new Dimension(150, 30));
		dateCP.setBounds(345, 178, 459, 30);
		panel.add(dateCP);

		dateKC = new JDateChooser();
		dateKC.setDateFormatString("dd/MM/yyyy");
		dateKC.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateKC.getJCalendar().setPreferredSize(new Dimension(400, 400));
		dateKC.getJCalendar().getMonthChooser().setPreferredSize(new Dimension(150, 30));
		dateKC.setBounds(345, 218, 459, 30);
		panel.add(dateKC);

		dateDK = new JDateChooser();
		dateDK.setDateFormatString("dd/MM/yyyy");
		dateDK.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateDK.getJCalendar().setPreferredSize(new Dimension(400, 400));
		dateDK.getJCalendar().getMonthChooser().setPreferredSize(new Dimension(150, 30));
		dateDK.setBounds(345, 261, 459, 30);
		panel.add(dateDK);

		cbLoai = new JComboBox<String>();
		cbLoai.setFont(new Font("Tahoma", Font.PLAIN, 24));
		ArrayList<LoaiCongTrinh> lct = null;
		try {
			lct = dao_lct.getDsLoaiCT();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (LoaiCongTrinh loaiCongTrinh : lct) {
			cbLoai.addItem(loaiCongTrinh.getTenLoai());
		}

		cbLoai.setBounds(345, 298, 459, 30);
		panel.add(cbLoai);
		
		txtError = new JTextField();
		txtError.setHorizontalAlignment(SwingConstants.CENTER);
		txtError.setForeground(Color.RED);
		txtError.setFont(new Font("Tahoma", Font.BOLD, 24));
		txtError.setColumns(10);
		txtError.setBounds(35, 397, 769, 30);
		txtError.setBackground(new Color(255, 255, 255));
		txtError.setEditable(false);
		txtError.setBorder(BorderFactory.createEmptyBorder());

		panel.add(txtError);

		

		

		cbTienDo = new JComboBox<String>();
		cbTienDo.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbTienDo.addItem("Đang Thực Hiện");
		cbTienDo.setBounds(345, 338, 459, 30);
		panel.add(cbTienDo);
		
		btnThem = new JButton("Thêm");		
		btnThem.setBounds(524, 464, 140, 50);
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnThem.setBackground(new Color(255, 204, 102));
		panel.add(btnThem);
		
		btnDong = new JButton("Đóng");
		
		btnDong.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnDong.setBackground(new Color(255, 204, 102));
		btnDong.setBounds(674, 464, 130, 50);
		panel.add(btnDong);
		
		cbLoai.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbTienDo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnDong.addActionListener(this);
		btnThem.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o.equals(btnDong)){
			this.dispose();
		}
		

		
			if (o.equals(btnThem)) {
				if (validData()) {
					String ten = txtTen.getText();
					String diadiem = txtDiaDiem.getText();
					Date dtcp = new Date(dateCP.getDate().getTime());
					Date dtkc = new Date(dateKC.getDate().getTime());
					Date dtdk = new Date(dateDK.getDate().getTime());

					String lctt = (String) cbLoai.getSelectedItem();

					LoaiCongTrinh lct = null;
					try {
						lct = dao_lct.getLoaiCTByName(lctt);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					String tiendo = (String) cbTienDo.getSelectedItem();
					CongTrinh ct = new CongTrinh(ten, diadiem, dtcp, dtkc, dtdk, tiendo, lct);

					try {
						if (dao_ct.themCongTrinh(ct)) {
							JOptionPane.showMessageDialog(this, "Thêm thành công");
							this.dispose();
						} else
							JOptionPane.showMessageDialog(this, "Thêm Thất bại");

					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}

			}

		

	}

	public boolean validData() {
		String ten = txtTen.getText().trim();
		String diadiem = txtDiaDiem.getText().trim();
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
		if (dateKC.getDate().before(dateCP.getDate())) {
				txtError.setText("Ngày khởi công phải sau ngày cấp phép");
				dateKC.requestFocus();
				return false;
				
		}
		if (dateDK.getDate() == null) {
			txtError.setText("Ngày dự kiến rỗng");
			dateDK.requestFocus();
			return false;
		}
		if (dateDK.getDate().before(dateKC.getDate())) {
			txtError.setText("Ngày dự kiến phải sau ngày khởi công");
			dateDK.requestFocus();
			return false;
		} 
		

		return true;
	}
}
