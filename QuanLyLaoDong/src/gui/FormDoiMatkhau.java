package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.DAO_Taikhoan;
import entity.TaiKhoan;
/**
 * 
 * @author nmthu
 *
 */
public class FormDoiMatkhau extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4450639666020736918L;
	private JPanel contentPane;
	private JPasswordField txtMatkhauCu;
	private JPasswordField txtMatkhauMoi;
	private JPasswordField txtKiemtraMatkhau;
	private TaiKhoan taikhoan;
	private JButton btnDoiMk;
	private JLabel errorMatkhauCu;
	private JLabel errorMatkhauMoi;
	private JLabel errorKiemtraMatkhau;
	private JButton btnDong;

	/**
	 * Create the frame.
	 */
	public FormDoiMatkhau(TaiKhoan tk) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(550, 250, 800, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnTitle = new JPanel();
		pnTitle.setBackground(new Color(255, 204, 102));
		pnTitle.setBounds(0, 0, 782, 70);
		contentPane.add(pnTitle);
		pnTitle.setLayout(null);
		
		JLabel lblTitle = new JLabel("ĐỔI MẬT KHẨU");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(new Color(21, 25, 28));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTitle.setBounds(0, 11, 782, 50);
		pnTitle.add(lblTitle);
		
		JPanel panel_Info = new JPanel();
		panel_Info.setBounds(0, 70, 782, 248);
		contentPane.add(panel_Info);
		panel_Info.setLayout(null);
		
		JLabel lblMatkhauCu = new JLabel("Mật khẩu cũ:");
		lblMatkhauCu.setBounds(10, 32, 222, 30);
		panel_Info.add(lblMatkhauCu);
		lblMatkhauCu.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		txtMatkhauCu = new JPasswordField();
		txtMatkhauCu.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtMatkhauCu.setBounds(242, 32, 300, 30);
		panel_Info.add(txtMatkhauCu);
		txtMatkhauCu.setColumns(10);
		
		errorMatkhauCu = new JLabel("(*)");
		errorMatkhauCu.setForeground(Color.RED);
		errorMatkhauCu.setFont(new Font("Tahoma", Font.PLAIN, 17));
		errorMatkhauCu.setBounds(552, 32, 220, 30);
		panel_Info.add(errorMatkhauCu);
		
		JLabel lblMatkhauMoi = new JLabel("Mật khẩu mới:");
		lblMatkhauMoi.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblMatkhauMoi.setBounds(10, 73, 222, 30);
		panel_Info.add(lblMatkhauMoi);
		
		txtMatkhauMoi = new JPasswordField();
		txtMatkhauMoi.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtMatkhauMoi.setColumns(10);
		txtMatkhauMoi.setBounds(242, 73, 300, 30);
		panel_Info.add(txtMatkhauMoi);
		
		errorMatkhauMoi = new JLabel("(*)");
		errorMatkhauMoi.setForeground(Color.RED);
		errorMatkhauMoi.setFont(new Font("Tahoma", Font.PLAIN, 17));
		errorMatkhauMoi.setBounds(552, 73, 220, 30);
		panel_Info.add(errorMatkhauMoi);
		
		JLabel lblKiemtraMatkhau = new JLabel("Nhập lại mật khẩu:");
		lblKiemtraMatkhau.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblKiemtraMatkhau.setBounds(10, 114, 222, 30);
		panel_Info.add(lblKiemtraMatkhau);
		
		txtKiemtraMatkhau = new JPasswordField();
		txtKiemtraMatkhau.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtKiemtraMatkhau.setColumns(10);
		txtKiemtraMatkhau.setBounds(242, 114, 300, 30);
		panel_Info.add(txtKiemtraMatkhau);
		
		errorKiemtraMatkhau = new JLabel("(*)");
		errorKiemtraMatkhau.setForeground(Color.RED);
		errorKiemtraMatkhau.setFont(new Font("Tahoma", Font.PLAIN, 17));
		errorKiemtraMatkhau.setBounds(552, 114, 220, 30);
		panel_Info.add(errorKiemtraMatkhau);
		
		btnDoiMk = new JButton("Xác nhận");
		btnDoiMk.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnDoiMk.setBackground(new Color(255, 204, 102));
		btnDoiMk.setBounds(459, 198, 160, 40);
		panel_Info.add(btnDoiMk);
		
		btnDong = new JButton("Đóng");
		btnDong.addActionListener(this);
		btnDong.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnDong.setBackground(new Color(255, 204, 102));
		btnDong.setBounds(622, 198, 150, 40);
		panel_Info.add(btnDong);
		
		btnDoiMk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnDoiMk.addActionListener(this);
		btnDong.addActionListener(this);
		taikhoan = tk;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o =e.getSource();
		if(o.equals(btnDoiMk)) {
			if(validData()) {
				DAO_Taikhoan dao_Taikhoan = new DAO_Taikhoan();
				if(dao_Taikhoan.doiMatkhau(taikhoan, String.valueOf(txtMatkhauMoi.getPassword()))) {
					JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
					this.dispose();
				}
				
			}
		}
		if(o.equals(btnDong)) {
			this.dispose();
		}
	}
	
	/**Kiểm tra mật khẩu cũ, mật khẩu mới và nhập lại mật khẩu có đúng không 
	 * 
	 * @return
	 */
	public boolean validData() {
		if (txtMatkhauCu.getPassword().length == 0) {
			errorMatkhauCu.setText("Mật khẩu rỗng");
			txtMatkhauCu.requestFocus();
			return false;
		}
		else if(!(String.valueOf(txtMatkhauCu.getPassword()).equals(taikhoan.getMatkhau()))) {
			errorMatkhauCu.setText("Mật khẩu cũ không chính xác");
			return false;
		}
		else {
			errorMatkhauCu.setText("");
		}
		if (txtMatkhauMoi.getPassword().length == 0) {
			errorMatkhauMoi.setText("Mật khẩu rỗng");
			txtMatkhauMoi.requestFocus();
			return false;
		} else {
			errorMatkhauMoi.setText("");
		}
		if (txtKiemtraMatkhau.getPassword().length == 0) {
			errorKiemtraMatkhau.setText("Rỗng");
			txtKiemtraMatkhau.requestFocus();
			return false;
		} else if (!(String.valueOf(txtKiemtraMatkhau.getPassword()).equals(String.valueOf(txtMatkhauMoi.getPassword())))) {
			errorKiemtraMatkhau.setText("Không chính xác");
			txtKiemtraMatkhau.requestFocus();
			return false;
		} else {
			errorKiemtraMatkhau.setText("");
		}
		
		return true;
	}
}
