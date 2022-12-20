package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
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
public class FormThemCongNhan extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JLabel lblDiaChi;
	private JLabel lblEmail;
	private JLabel lblNgaySinh;
	private JLabel lblCmnd;
	private JLabel lblGioiTinh;
	private JLabel lblHoTen;
	private JButton btnThemCN;
	private JButton btnDong;
	private JPanel panel;
	private JLabel lblTrinhDo;
	private JTextField txtPhone;
	private JComboBox<String> cbGioitinh;
	private JComboBox<String> cbboxTrinhDo;
	private JDateChooser dateNgaySinh;
	private DAO_TrinhDo daoTD = new DAO_TrinhDo();
	private JTextField txtError;
	private JTextField txtHoTen;
	private JTextField txtCmnd;
	private JTextField txtEmail;
	private JTextField txtDiaChi;

	/**
	 * Create the frame.
	 */
	public FormThemCongNhan() {
		setAlwaysOnTop(true);
		setBounds(550, 250, 715, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		panel = new JPanel();
		panel.setBounds(0, 0, 700, 603);
		panel.setBackground(new Color(255, 255, 255));
		contentPane.add(panel);
		panel.setLayout(null);

		lblTrinhDo = new JLabel("Trình độ:");
		lblTrinhDo.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTrinhDo.setBounds(35, 411, 167, 30);
		panel.add(lblTrinhDo);

		lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblDiaChi.setBounds(35, 366, 167, 30);
		panel.add(lblDiaChi);

		lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblEmail.setBounds(35, 275, 167, 30);
		panel.add(lblEmail);

		lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNgaySinh.setBounds(35, 190, 167, 30);
		panel.add(lblNgaySinh);

		lblCmnd = new JLabel("CMND:");
		lblCmnd.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblCmnd.setBounds(35, 230, 167, 30);
		panel.add(lblCmnd);

		lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblGioiTinh.setBounds(35, 145, 167, 30);
		panel.add(lblGioiTinh);

		lblHoTen = new JLabel("Họ và tên:");
		lblHoTen.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblHoTen.setBounds(35, 100, 167, 30);
		panel.add(lblHoTen);

		btnThemCN = new JButton("Thêm");
		btnThemCN.setBounds(394, 543, 140, 50);
		btnThemCN.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnThemCN.setBackground(new Color(255, 204, 102));
		panel.add(btnThemCN);

		btnDong = new JButton("Đóng");
		btnDong.addActionListener(this);
		btnDong.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnDong.setBackground(new Color(255, 204, 102));
		btnDong.setBounds(544, 543, 140, 50);
		panel.add(btnDong);

		txtError = new JTextField();
		txtError.setHorizontalAlignment(SwingConstants.CENTER);
		txtError.setForeground(Color.RED);
		txtError.setFont(new Font("Tahoma", Font.BOLD, 26));
		txtError.setColumns(10);
		txtError.setBounds(35, 503, 623, 30);
		txtError.setBackground(new Color(255, 255, 255));
		txtError.setEditable(false);
		txtError.setBorder(BorderFactory.createEmptyBorder());

		panel.add(txtError);

		txtHoTen = new JTextField(20);
		txtHoTen.setColumns(10);
		txtHoTen.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtHoTen.setBounds(212, 100, 446, 30);
		panel.add(txtHoTen);

		txtCmnd = new JTextField(20);
		txtCmnd.setColumns(10);
		txtCmnd.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtCmnd.setBounds(212, 230, 446, 30);
		panel.add(txtCmnd);

		txtEmail = new JTextField(20);
		txtEmail.setColumns(10);
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtEmail.setBounds(212, 275, 446, 30);
		panel.add(txtEmail);

		txtDiaChi = new JTextField(20);
		txtDiaChi.setColumns(10);
		txtDiaChi.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtDiaChi.setBounds(212, 366, 446, 30);
		panel.add(txtDiaChi);

		dateNgaySinh = new JDateChooser();
		dateNgaySinh.setBounds(212, 190, 446, 30);
		dateNgaySinh.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateNgaySinh.getJCalendar().setPreferredSize(new Dimension(400, 400));
		dateNgaySinh.getJCalendar().getMonthChooser().setPreferredSize(new Dimension(150, 30));
		panel.add(dateNgaySinh);

		cbboxTrinhDo = new JComboBox<String>();
		cbboxTrinhDo.setBounds(212, 413, 446, 30);
		cbboxTrinhDo.setBackground(SystemColor.window);
		cbboxTrinhDo.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbboxTrinhDo.setUI(new BasicComboBoxUI() {
		      @Override protected JButton createArrowButton() {
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
		pnTitle.setBounds(0, 0, 700, 70);
		panel.add(pnTitle);
		pnTitle.setLayout(null);

		JLabel lblTitle = new JLabel("THÊM CÔNG NHÂN MỚI\r\n");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(new Color(21, 25, 28));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTitle.setBounds(0, 9, 700, 50);
		pnTitle.add(lblTitle);

		cbGioitinh = new JComboBox<String>();
		cbGioitinh.addItem("Nam");
		cbGioitinh.addItem("Nữ");
		cbGioitinh.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbGioitinh.setBackground(SystemColor.window);
		cbGioitinh.setUI(new BasicComboBoxUI() { //custom dropdown toggle
		      @Override protected JButton createArrowButton() {
		          JButton b = super.createArrowButton();
		          b.setContentAreaFilled(false);
		          b.setBackground(SystemColor.control);
		          b.setBorder(BorderFactory.createEmptyBorder());
		          return b;
		        }
		      });
		cbGioitinh.setBounds(212, 145, 446, 30);
		panel.add(cbGioitinh);

		JLabel lblSDT = new JLabel("SDT:");
		lblSDT.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblSDT.setBounds(35, 316, 142, 30);
		panel.add(lblSDT);

		txtPhone = new JTextField(20);
		txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtPhone.setColumns(10);
		txtPhone.setBounds(212, 316, 446, 30);
		panel.add(txtPhone);

		btnThemCN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnThemCN.addActionListener(this);
		btnDong.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnDong)) {
			this.dispose();
		}
		if (o.equals(btnThemCN)) {
			if (validData()) {
				DAO_CongNhan daoCongNhan = new DAO_CongNhan();
				String ten = txtHoTen.getText().trim();
				String gt = (String) cbGioitinh.getSelectedItem();
				Date ns = new Date(dateNgaySinh.getDate().getTime());
				String cmnd = txtCmnd.getText().trim();
				String email = txtEmail.getText().trim();
				String sdt = txtPhone.getText().trim();
				String diaChi = txtDiaChi.getText().trim();
				String tenTD = (String) cbboxTrinhDo.getSelectedItem();
				TrinhDo td = daoTD.getTrinhDoByName(tenTD);
				String trangThai = "Đang làm";
				CongNhan cn = new CongNhan(ten, gt, sdt, ns, cmnd, email, diaChi, trangThai, td);
				try {
					if (daoCongNhan.createCongNhan(cn)) {
						JOptionPane.showMessageDialog(this, "Thêm thành công");
						this.dispose();
					} else {
						JOptionPane.showMessageDialog(this, "Có lỗi xảy ra! Vui lòng thử lại\nThêm không thành công");
						
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
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
			if(!txtHoTen.getText().trim().matches("[\\p{L}\\s]+")) {
				JOptionPane.showMessageDialog(this, "Tên công nhân không hợp lệ!\n Tên công nhân chỉ chứa chữ, không chứa số hay kí tự đặc biệt", "Chú ý", JOptionPane.ERROR_MESSAGE);
				txtHoTen.requestFocus();
				return false;
			}
			txtError.setText("");
		}
		int tuoi = LocalDate.now().getYear() - dateNgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
		if (!(dateNgaySinh.getDate() != null && tuoi > 18)) {
			txtError.setText("Ngày sinh không hợp lệ");
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
			if(!txtCmnd.getText().trim().matches("[0-9]{1,9}")) {
				JOptionPane.showMessageDialog(this, "CMND không hợp lệ!\n CMND chỉ chứa số, không chứ các kí tự", "Chú ý", JOptionPane.ERROR_MESSAGE);
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
			if(!txtPhone.getText().trim().matches("[0-9]{1,10}")) {
				JOptionPane.showMessageDialog(this, "SDT không hợp lệ!\n SDT chỉ chứa số, không chứ các kí tự", "Chú ý", JOptionPane.ERROR_MESSAGE);
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
			if(!txtDiaChi.getText().trim().matches("[\\p{L}\\s0-9()\\/_\\\\.,]+")) {
				JOptionPane.showMessageDialog(this, "Địa chỉ không hợp lệ!\n Địa chỉ chứa chữ, số và các kí tự /-,.", "Chú ý", JOptionPane.ERROR_MESSAGE);
				txtDiaChi.requestFocus();
				return false;
			}
			txtError.setText("");
		}

		return true;
	}
}
