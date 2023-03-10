package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import connect.ConnectDB;
import dao.DAO_Nhanvien;
import dao.DAO_Taikhoan;
import entity.NhanVien;
/**
 * 
 * @author nmthu
 *
 */
public class FormQLNhanVien extends JPanel implements ActionListener, MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField txtTimkiem;
	private JButton btnThem;
	private JButton btnCapNhat;
	private JButton btnOff;
	private JButton btnDoiMk;
	private JButton btnResetMk;
	private JComboBox<String> comboBox;
	private JButton btnTim;
	private DefaultTableModel tableModel;
	private JTextField txtTenNv;
	private JTextField txtID;
	private JTextField txtNgaySinh;
	private JTextField txtGioitinh;
	private JTextField txtCmnd;
	private JTextField txtEmail;
	private JTextField txtDiachi;
	private JTextField txtTrangthai;
	private JTextField txtTaikhoan;
	private JTextField txtSdt;
	private JButton btnOn;
	private JButton btnReload;
	private JPanel pnTitle;
	private JPanel pnChiTietTable;
	private JLabel lblCount;
	private DAO_Nhanvien dao = new DAO_Nhanvien();
	private JButton btnHelp;
	/**
	 * Create the panel.
	 */
	@SuppressWarnings("serial")
	public FormQLNhanVien() {
		setBounds(0, 0, 1620, 1020);
		setLayout(null);
		
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		DAO_Nhanvien dao_nhanvien = new DAO_Nhanvien(); 
		
		JPanel pnChucNang = new JPanel();
		pnChucNang.setBackground(Color.WHITE);
		pnChucNang.setBounds(0, 45, 1620, 70);
		pnChucNang.setLayout(null);
		add(pnChucNang);
		
		btnThem = new JButton("Th??m");
		btnThem.setForeground(SystemColor.controlText);
		btnThem.setBackground(new Color(255, 204, 102));
		btnThem.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnThem.setBounds(10, 11, 200, 50);
		btnThem.setIcon(new ImageIcon("icons/plus32.png"));
		btnThem.setFocusable(false);
		pnChucNang.add(btnThem);

		
		btnCapNhat = new JButton("C???p nh???t");
		btnCapNhat.setForeground(SystemColor.controlText);
		btnCapNhat.setBackground(new Color(255, 204, 102));
		btnCapNhat.setFont(new Font("Arial", Font.PLAIN, 28));
		btnCapNhat.setBounds(220, 11, 200, 50);
		btnCapNhat.setIcon(new ImageIcon("icons/edit.png"));
		btnCapNhat.setFocusable(false);
		pnChucNang.add(btnCapNhat);
		
		btnResetMk = new JButton("Ph???c h???i m???t kh???u");
		btnResetMk.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnResetMk.setBackground(new Color(255, 204, 102));
		btnResetMk.setFocusable(false);
		btnResetMk.setBounds(430, 11, 280, 50);
		pnChucNang.add(btnResetMk);
		btnResetMk.addActionListener(this);
		
		btnDoiMk = new JButton("?????i m???t kh???u");
		btnDoiMk.setBackground(new Color(255, 204, 102));
		btnDoiMk.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnDoiMk.setFocusable(false);
		btnDoiMk.setBounds(724, 11, 206, 50);
		pnChucNang.add(btnDoiMk);
		
		btnOff = new JButton();
		BufferedImage image;
		try {
			image = ImageIO.read(new File("icons/switch-off.png"));
			ImageIcon offIcon = new ImageIcon(image.getScaledInstance(60, 50, Image.SCALE_SMOOTH));
			btnOff.setIcon(offIcon);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		btnOff.setBorderPainted(false);
		btnOff.setBorder(null);
		btnOff.setFocusable(false);
		btnOff.setBackground(SystemColor.window);
		btnOff.setBounds(810, 11, 60, 50);
		pnChucNang.add(btnOff);
		
		btnOn = new JButton();
		try {
			image = ImageIO.read(new File("icons/switch-on.png"));
			ImageIcon onIcon = new ImageIcon(image.getScaledInstance(60, 50, Image.SCALE_SMOOTH));
			btnOn.setIcon(onIcon);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		btnOn.setFocusable(false);
		btnOn.setBorderPainted(false);
		btnOn.setBorder(null);
		btnOn.setBackground(SystemColor.window);
		btnOn.setBounds(870, 11, 60, 50);
		pnChucNang.add(btnOn);
		
		txtTimkiem = new JTextField();
		txtTimkiem.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTimkiem.setBounds(1155, 17, 335, 40);
		pnChucNang.add(txtTimkiem);
		txtTimkiem.setColumns(10);
		
		btnTim = new JButton("T??m");
		btnTim.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnTim.setBackground(new Color(255, 204, 102));
		btnTim.setIcon(new ImageIcon("icons/loupe.png"));
		btnTim.setBounds(1500, 17, 110, 40);
		pnChucNang.add(btnTim);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 239, 213));
		panel.setBounds(0, 45, 1620, 975);
		add(panel);
		panel.setLayout(null);
		
		JPanel panel_Info = new JPanel();
		panel_Info.setBackground(new Color(255, 239, 213));
		panel_Info.setBounds(1140, 120, 470, 820);
		panel.add(panel_Info);
		panel_Info.setLayout(null);
		
		JLabel lblTenNv = new JLabel("T??n Nh??n vi??n:");
		lblTenNv.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblTenNv.setBounds(10, 80, 423, 30);
		panel_Info.add(lblTenNv);
		
		txtTenNv = new JTextField();
		txtTenNv.setBackground(SystemColor.textHighlightText);
		txtTenNv.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTenNv.setBounds(10, 108, 450, 35);
		panel_Info.add(txtTenNv);
		
		JLabel lblID = new JLabel("M?? nh??n vi??n:");
		lblID.setBounds(10, 10, 423, 30);
		panel_Info.add(lblID);
		lblID.setFont(new Font("Tahoma", Font.BOLD, 26));
		
		txtID = new JTextField();
		txtID.setBackground(SystemColor.textHighlightText);
		txtID.setBounds(10, 39, 450, 35);
		panel_Info.add(txtID);
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		JLabel lblNgaySinh = new JLabel("Ng??y sinh:");
		lblNgaySinh.setBounds(10, 149, 423, 30);
		panel_Info.add(lblNgaySinh);
		lblNgaySinh.setFont(new Font("Tahoma", Font.BOLD, 26));
		
		txtNgaySinh = new JTextField();
		txtNgaySinh.setBackground(SystemColor.window);
		txtNgaySinh.setBounds(10, 179, 450, 35);
		panel_Info.add(txtNgaySinh);
		txtNgaySinh.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		JLabel lblGioitinh = new JLabel("Gi???i t??nh:");
		lblGioitinh.setBounds(10, 218, 423, 30);
		panel_Info.add(lblGioitinh);
		lblGioitinh.setFont(new Font("Tahoma", Font.BOLD, 26));
		
		txtGioitinh = new JTextField();
		txtGioitinh.setBounds(10, 250, 450, 35);
		panel_Info.add(txtGioitinh);
		txtGioitinh.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		JLabel lblCmnd = new JLabel("CMND:");
		lblCmnd.setBounds(10, 285, 423, 30);
		panel_Info.add(lblCmnd);
		lblCmnd.setFont(new Font("Tahoma", Font.BOLD, 26));
		
		txtCmnd = new JTextField();
		txtCmnd.setBounds(10, 318, 450, 35);
		panel_Info.add(txtCmnd);
		txtCmnd.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 422, 423, 30);
		panel_Info.add(lblEmail);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 26));
		
		txtEmail = new JTextField();
		txtEmail.setBounds(10, 449, 450, 35);
		panel_Info.add(txtEmail);
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		JLabel lblSdt = new JLabel("S??? ??i???n tho???i:");
		lblSdt.setBounds(10, 354, 423, 30);
		panel_Info.add(lblSdt);
		lblSdt.setFont(new Font("Tahoma", Font.BOLD, 26));
		
		txtSdt = new JTextField();
		txtSdt.setBounds(10, 386, 450, 35);
		panel_Info.add(txtSdt);
		txtSdt.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		JLabel lblDiachi = new JLabel("?????a ch???:");
		lblDiachi.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblDiachi.setBounds(10, 490, 423, 30);
		panel_Info.add(lblDiachi);
		
		txtDiachi = new JTextField();
		txtDiachi.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtDiachi.setBounds(10, 520, 450, 35);
		panel_Info.add(txtDiachi);
		
		JLabel lblTrangthai = new JLabel("Tr???ng th??i:");
		lblTrangthai.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblTrangthai.setBounds(10, 561, 423, 30);
		panel_Info.add(lblTrangthai);
		
		txtTrangthai = new JTextField();
		txtTrangthai.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTrangthai.setBounds(10, 595, 450, 35);
		panel_Info.add(txtTrangthai);
		
		JLabel lblTaikhoan = new JLabel("T??i kho???n:");
		lblTaikhoan.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblTaikhoan.setBounds(10, 635, 423, 30);
		panel_Info.add(lblTaikhoan);
		
		txtTaikhoan = new JTextField();
		txtTaikhoan.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTaikhoan.setBounds(10, 664, 450, 35);
		panel_Info.add(txtTaikhoan);
		
		JLabel lblInfoNV = new JLabel("Th??ng tin chi ti???t nh??n vi??n");
		lblInfoNV.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoNV.setBounds(1140, 75, 470, 40);
		panel.add(lblInfoNV);
		lblInfoNV.setFont(new Font("Tahoma", Font.BOLD, 28));
		
		JLabel lblDSNV = new JLabel("Danh s??ch nh??n vi??n:");
		lblDSNV.setHorizontalAlignment(SwingConstants.LEFT);
		lblDSNV.setFont(new Font("Arial", Font.BOLD, 26));
		lblDSNV.setBounds(10, 78, 380, 40);
		panel.add(lblDSNV);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 120, 1130, 820);
		panel.add(scrollPane);
		
		String[] header = {"M?? NV", "T??n NV","Gi???i t??nh","Ng??y sinh", "CMND", "T??i Kho???n", "Tr???ng th??i"};
		tableModel = new DefaultTableModel(header, 0){
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
		table = new JTable()
		{
			/**
			 * t?? m??u t???ng d??ng
			 */
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (!isRowSelected(row))
					c.setBackground(row % 2 == 0 ? getBackground() : new Color(218, 223, 225));
				return c;
			}
			
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
            @Override
            public void doLayout()
            {
                TableColumn resizingColumn = null;

                if (tableHeader != null)
                    resizingColumn = tableHeader.getResizingColumn();

                //  Viewport size changed. May need to increase columns widths

                if (resizingColumn == null)
                {
                    setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    super.doLayout();
                }

                //  Specific column resized. Reset preferred widths

                else
                {
                    TableColumnModel tcm = getColumnModel();

                    for (int i = 0; i < tcm.getColumnCount(); i++)
                    {
                        TableColumn tc = tcm.getColumn(i);
                        tc.setPreferredWidth( tc.getWidth() );
                    }

                    // Columns don't fill the viewport, invoke default layout

                    if (tcm.getTotalColumnWidth() < getParent().getWidth())
                        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                        super.doLayout();
                }

                setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            }

        };
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 20));
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.setBackground(SystemColor.WHITE);
		table.setRowHeight(45);
		table.addMouseListener(this);
		table.setModel(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.getTableHeader().setBackground(new Color(255, 208, 120));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(table);
		
		try {
			image = ImageIO.read(new File("icons/switch-on.png"));
			ImageIcon onIcon = new ImageIcon(image.getScaledInstance(60, 50, Image.SCALE_SMOOTH));
			btnOn.setIcon(onIcon);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		btnReload = new JButton("T???I L???I");
		try {
			image = ImageIO.read(new File("icons/reload.png"));
			ImageIcon reloadIcon = new ImageIcon(image.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
			btnReload.setIcon(reloadIcon);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		btnReload.setFocusable(false);
		btnReload.setBorderPainted(false);
		btnReload.setOpaque(false);
		btnReload.setBorder(null);
		btnReload.setBackground(new Color(255, 204, 102));
		btnReload.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnReload.setBounds(980, 76, 150, 40);
		panel.add(btnReload);
		btnReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				reloadData();
			}
		});
		
		
		try {
			dao_nhanvien.loadData("select * from NhanVien where TrangThai = N'??i L??m'", tableModel); // load danh s??ch nh??n vi??n ??ang l??m v??o table
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		pnTitle = new JPanel();
		pnTitle.setBorder(null);
		pnTitle.setBackground(new Color(255, 204, 102));
		pnTitle.setBounds(0, 0, 1620, 45);
		add(pnTitle);
		pnTitle.setLayout(null);
		
		JLabel lblTitle = new JLabel("Qu???n L?? T??i Kho???n Nh??n Vi??n");
		lblTitle.setForeground(new Color(0, 0, 51));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblTitle.setBounds(10, 5, 496, 35);
		pnTitle.add(lblTitle);
		
		btnHelp = new JButton("");
		btnHelp.setIcon(new ImageIcon("icons/infor.png"));
		btnHelp.setBorder(null);
		btnHelp.setBorderPainted(false);
		btnHelp.setBackground(new Color(255, 204, 102));
		btnHelp.setForeground(new Color(0, 0, 0));
		btnHelp.setBounds(1575, 2, 40, 40);
		pnTitle.add(btnHelp);
		
		pnChiTietTable = new JPanel();
		pnChiTietTable.setBounds(0, 985, 1130, 35);
		pnChiTietTable.setBackground(new Color(255, 239, 213));
		add(pnChiTietTable);
		pnChiTietTable.setLayout(null);
		
		lblCount = new JLabel("");
		lblCount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCount.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCount.setBounds(715, 5, 415, 25);
		pnChiTietTable.add(lblCount);
		
		
		btnCapNhat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDoiMk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHelp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOff.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReload.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(765, 75, 205, 40);
		panel.add(comboBox);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 28));
		comboBox.addItem("??ang l??m vi???c");
		comboBox.addItem("???? ngh??? vi???c");
		comboBox.addItem("T???t c???");
		comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) comboBox.getSelectedItem(); // l???y th??ng tin item ???????c ch???n
				
				switch (s) {
				case "??ang l??m vi???c":
					try {
						tableModel.setRowCount(0);
						dao_nhanvien.loadData("select * from NhanVien where TrangThai = N'??i L??m'", tableModel);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					break;
				case "???? ngh??? vi???c":
					try {
						tableModel.setRowCount(0);
						dao_nhanvien.loadData("select * from NhanVien where TrangThai = N'Ngh???'", tableModel);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					break;
				case "T???t c???":
					try {
						tableModel.setRowCount(0);
						dao_nhanvien.loadData("select * from NhanVien", tableModel);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					break;
				default:
					break;
				}
			}
		});
		btnResetMk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTim.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		// ????ng k?? s??? ki???n cho button
		btnThem.addActionListener(this);
		btnCapNhat.addActionListener(this);
		btnOff.addActionListener(this);
		btnOn.addActionListener(this);
		btnDoiMk.addActionListener(this);
		btnTim.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnThem)) {
			FormThemNhanvien formThemNV = new FormThemNhanvien();
			formThemNV.setVisible(true);
		}
		if(o.equals(btnCapNhat)) {
			int i = table.getSelectedRow();
			if (i != -1) {
				FormCapNhatNhanvien formSua = new FormCapNhatNhanvien(txtID.getText());
				formSua.setVisible(true);
				
			} else {
				JOptionPane.showMessageDialog(this, "Vui l??ng ch???n nh??n vi??n c???n c???p nh???t th??ng tin");
			}
		}
		if(o.equals(btnResetMk)) {
			int i = table.getSelectedRow();
			if(i!=-1) {
			int xacnhan = JOptionPane.showConfirmDialog(this, "B???n x??c nh???n mu???n ph???c h???i m???t kh???u m???c ?????nh cho nh??n vi??n n??y?", "Ch?? ??", JOptionPane.YES_NO_OPTION);
			if(xacnhan == JOptionPane.YES_OPTION) {
				DAO_Nhanvien dao_Nhanvien = new DAO_Nhanvien();
				try {
					NhanVien nv = dao_Nhanvien.getNhanvienByID(txtID.getText());
					DAO_Taikhoan dao_Taikhoan = new DAO_Taikhoan();
					if(dao_Taikhoan.doiMatkhau(nv.getTaikhoan(), "123456")) {
						JOptionPane.showMessageDialog(this, "Ph???c h???i m???t kh???u th??nh c??ng");
					}
					else {
						JOptionPane.showMessageDialog(this, "???? c?? l???i x???y ra. Vui l??ng th??? l???i");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}else {
			JOptionPane.showMessageDialog(this, "Vui l??ng ch???n nh??n vi??n\n c???n reset m???t kh???u");
		}
		}
		if(o.equals(btnDoiMk)) {
			int i = table.getSelectedRow();
			if(i!=-1) {
				DAO_Nhanvien dao_Nhanvien = new DAO_Nhanvien();
				NhanVien nv;
				try {
					nv = dao_Nhanvien.getNhanvienByID(txtID.getText());
					FormDoiMatkhau formDoiMk = new FormDoiMatkhau(nv.getTaikhoan());
					formDoiMk.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "Vui l??ng ch???n nh??n vi??n\n c???n ?????i m???t kh???u");
			}
			
		}
		if(o.equals(btnOff)) {
			int i = table.getSelectedRow();
			if(i!=-1) {
			int xacnhan = JOptionPane.showConfirmDialog(this, "B???n x??c nh???n mu???n cho nh??n vi??n n??y ngh????", "Ch?? ??", JOptionPane.YES_NO_OPTION);
			if(xacnhan == JOptionPane.YES_OPTION) {
				DAO_Nhanvien dao_Nhanvien = new DAO_Nhanvien();
				if(dao_Nhanvien.thaydoiTrangthaiNhanvien(txtID.getText(), "Ngh???")) {
					JOptionPane.showMessageDialog(this, "Nh??n vi??n n??y ???? ???????c ngh???");
					reloadData();
				}
				else {
					JOptionPane.showMessageDialog(this, "???? c?? l???i x???y ra. Vui l??ng th??? l???i");
				}
			}
		}else {
			JOptionPane.showMessageDialog(this, "Vui l??ng ch???n nh??n vi??n\n c????n cho nghi??");
		}
		}
		if(o.equals(btnOn)) {
			int i = table.getSelectedRow();
			if(i!=-1) {
			int xacnhan = JOptionPane.showConfirmDialog(this, "B???n x??c nh???n mu???n nh??n vi??n n??y ??i l??m l???i?", "Ch?? ??", JOptionPane.YES_NO_OPTION);
			if(xacnhan == JOptionPane.YES_OPTION) {
				DAO_Nhanvien dao_Nhanvien = new DAO_Nhanvien();
				if(dao_Nhanvien.thaydoiTrangthaiNhanvien(txtID.getText(), "??i L??m")) {
					JOptionPane.showMessageDialog(this, "Nh??n vi??n n??y ???? ???????c chuy???n v??? l??m");
					comboBox.setSelectedItem("??ang l??m vi???c");
				}
				else {
					JOptionPane.showMessageDialog(this, "???? c?? l???i x???y ra. Vui l??ng th??? l???i");
					reloadData();
				}
			}
		}else {
			JOptionPane.showMessageDialog(this, "Vui l??ng ch???n nh??n vi??n\n c????n cho ??i la??m");
		}
		}
		if(o.equals(btnTim)) {
			DAO_Nhanvien dao_nhanvien = new DAO_Nhanvien();
			try {
				String timkiem = txtTimkiem.getText().toLowerCase(); // chuy???n in hoa th??nh in th?????ng h???t ????? t??m ki???m ?????y ?????
				comboBox.setSelectedItem("T???t c???");; //T???t c???
				tableModel.setRowCount(0);
				dao_nhanvien.loadData("select *  from NhanVien where IDNhanVien like N'%"+timkiem+"%'", tableModel); // t??m theo m??
				dao_nhanvien.loadData("select *  from NhanVien where TenNhanVien like N'%"+timkiem+"%'", tableModel); // t??m theo t??n
				dao_nhanvien.loadData("select *  from NhanVien where CMND like N'%"+timkiem+"%'", tableModel);	// t??m theo cmnd
				dao_nhanvien.loadData("select *  from NhanVien where Phone like N'%"+timkiem+"%'", tableModel);	// t??m theo sdt
				dao_nhanvien.loadData("select *  from NhanVien where email like N'%"+timkiem+"%'", tableModel);	// t??m theo email
				txtTimkiem.setText("");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int i = table.getSelectedRow();
		try {
			txtID.setText(tableModel.getValueAt(i, 0).toString());
			txtTenNv.setText(tableModel.getValueAt(i, 1).toString());
			txtGioitinh.setText(tableModel.getValueAt(i, 2).toString());
			txtNgaySinh.setText(tableModel.getValueAt(i, 3).toString());
			txtSdt.setText(dao.getNhanvienByID(tableModel.getValueAt(i, 0).toString()).getPhone());
			txtCmnd.setText(tableModel.getValueAt(i, 4).toString());
			txtEmail.setText(dao.getNhanvienByID(tableModel.getValueAt(i, 0).toString()).getEmail());
			txtDiachi.setText(dao.getNhanvienByID(tableModel.getValueAt(i, 0).toString()).getDiaChi());
			txtTaikhoan.setText(tableModel.getValueAt(i, 5).toString());
			txtTrangthai.setText(tableModel.getValueAt(i, 6).toString());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void reloadData() {
		DAO_Nhanvien dao_nhanvien = new DAO_Nhanvien();
		comboBox.setSelectedItem("??ang l??m vi???c");
		try {
			tableModel.setRowCount(0);
			dao_nhanvien.loadData("select * from NhanVien where TrangThai = N'??i L??m'", tableModel);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
