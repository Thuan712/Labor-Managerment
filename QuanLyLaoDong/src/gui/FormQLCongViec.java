package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;

import connect.ConnectDB;
import dao.DAO_BangPhanCong;
//import dao.DAO_Chuyenmon;
import dao.DAO_CongTrinh;
import dao.DAO_CongViec;
import dao.DAO_LoaiCongTrinh;
import dao.DAO_Nhanvien;
import entity.LoaiCongTrinh;
import entity.NhanVien;
/**
 * 
 * @author nmthu
 *
 */
public class FormQLCongViec extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton btnThem;
	private DefaultTableModel tableModel;
	private JButton btnKetthucCV;
	private JTextField txtTimCT;
	private JTable table_CT;
	private DefaultTableModel tableModel_CT;
	private JButton btnCapNhat;
	private JButton btnTimCT;
	private NhanVien nhanvien;
	private JPanel pnTitle;
	private JButton btnHelp;
	private JLabel lblLoaiCT;
	private JLabel lblNamKhoiCong;
	private JComboBox<String> cbbNamKhoicong;
	private JComboBox<String> cbbLoaiCT;
	private JTextField txtTimCV;
	private JButton btnTimCV;
	private JComboBox<String> cbbNhanVien;
	private JDateChooser dcNgayBd;
	private JButton btnTimCvTheoNgayBd;
	private JComboBox<String> cbbTrangThai;

	/**
	 * Create the panel.
	 */
	public FormQLCongViec(String maNhanvien) {
		setBackground(new Color(255, 239, 213));
		setBounds(0, 0, 1620, 1020);
		setLayout(null);

		pnTitle = new JPanel();
		pnTitle.setLayout(null);
		pnTitle.setBorder(null);
		pnTitle.setBackground(new Color(255, 204, 102));
		pnTitle.setBounds(0, 0, 1620, 45);
		add(pnTitle);

		btnHelp = new JButton("");
		btnHelp.setIcon(new ImageIcon("icons/infor.png"));
		btnHelp.setBorder(null);
		btnHelp.setBorderPainted(false);
		btnHelp.setBackground(new Color(255, 204, 102));
		btnHelp.setForeground(new Color(0, 0, 0));
		btnHelp.setBounds(1575, 2, 40, 40);
		pnTitle.add(btnHelp);

		DAO_CongTrinh dao_Congtrinh = new DAO_CongTrinh();
		DAO_CongViec dao_Congviec = new DAO_CongViec();
		DAO_LoaiCongTrinh dao_lct = new DAO_LoaiCongTrinh();
		DAO_Nhanvien dao_nv = new DAO_Nhanvien();

		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		JLabel lblTitle = new JLabel("Quản lý Công Việc\r\n");
		lblTitle.setForeground(new Color(0, 0, 51));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblTitle.setBounds(10, 5, 270, 35);
		pnTitle.add(lblTitle);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 450, 1150, 562);
		add(scrollPane);

		String[] header = { "Mã Công Việc", "Tên Công Việc", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái" };
		tableModel = new DefaultTableModel(header, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * tô màu từng dòng
			 */
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (!isRowSelected(row))
					c.setBackground(row % 2 == 0 ? getBackground() : new Color(218, 223, 225));
				return c;
			}
			
			public boolean getScrollableTracksViewportWidth() {
				return getPreferredSize().width < getParent().getWidth();
			}

			@Override
			public void doLayout() {
				TableColumn resizingColumn = null;

				if (tableHeader != null)
					resizingColumn = tableHeader.getResizingColumn();

				if (resizingColumn == null) {
					setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					super.doLayout();
				}

				else {
					TableColumnModel tcm = getColumnModel();

					for (int i = 0; i < tcm.getColumnCount(); i++) {
						TableColumn tc = tcm.getColumn(i);
						tc.setPreferredWidth(tc.getWidth());
					}

					if (tcm.getTotalColumnWidth() < getParent().getWidth())
						setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					super.doLayout();
				}

				setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			}

		};
		table.setBackground(SystemColor.control);
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.setModel(tableModel);
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 20));
		table.getTableHeader().setBackground(new Color(255, 204, 102));
		table.setRowHeight(45);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(table);

		JPanel panel_Chucnang = new JPanel();
		panel_Chucnang.setBounds(5, 50, 1615, 100);
		add(panel_Chucnang);
		panel_Chucnang.setLayout(null);
		panel_Chucnang.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "C\u00E1c Thao T\u00E1c",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 16), Color.DARK_GRAY));
		panel_Chucnang.setBackground(SystemColor.textHighlightText);

		btnThem = new JButton("Thêm\r\n");
		btnThem.setBounds(20, 32, 200, 50);
		btnThem.setIcon(new ImageIcon("icons/plus32.png"));
		btnThem.setFocusable(false);
		btnThem.setForeground(SystemColor.controlText);
		btnThem.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnThem.setBackground(new Color(255, 204, 102));
		panel_Chucnang.add(btnThem);

		btnCapNhat = new JButton("Cập nhật");
		btnCapNhat.setForeground(SystemColor.controlText);
		btnCapNhat.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnCapNhat.setBackground(new Color(255, 204, 102));
		btnCapNhat.setIcon(new ImageIcon("icons/edit.png"));
		btnCapNhat.setBounds(250, 32, 200, 50);
		btnCapNhat.setFocusable(false);
		panel_Chucnang.add(btnCapNhat);

		btnKetthucCV = new JButton("Hoàn thành");
		btnKetthucCV.setIcon(new ImageIcon("icons/check.png"));
		btnKetthucCV.setForeground(SystemColor.controlText);
		btnKetthucCV.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnKetthucCV.setBackground(new Color(255, 204, 102));
		btnKetthucCV.setBounds(480, 32, 230, 50);
		btnKetthucCV.setFocusable(false);
		panel_Chucnang.add(btnKetthucCV);

		JPanel panel_CongTrinh = new JPanel();
		panel_CongTrinh.setBackground(SystemColor.window);
		panel_CongTrinh.setBorder(new TitledBorder(null, "Ch\u1ECDn c\u00F4ng tr\u00ECnh", TitledBorder.LEADING,
				TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 24), null));
		panel_CongTrinh.setBounds(5, 155, 1615, 290);
		add(panel_CongTrinh);
		panel_CongTrinh.setLayout(null);

		JScrollPane scrollPane_CongTrinh = new JScrollPane();
		scrollPane_CongTrinh.setBounds(5, 31, 1140, 249);
		panel_CongTrinh.add(scrollPane_CongTrinh);

		String[] header_CT = { "Mã Công trình", "Tên công trình", "Tiến độ" };
		tableModel_CT = new DefaultTableModel(header_CT, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {  false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table_CT = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 * tô màu từng dòng
			 */
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (!isRowSelected(row))
					c.setBackground(row % 2 == 0 ? getBackground() : new Color(218, 223, 225));
				return c;
			}
			
			public boolean getScrollableTracksViewportWidth() {
				return getPreferredSize().width < getParent().getWidth();
			}

			@Override
			public void doLayout() {
				TableColumn resizingColumn = null;

				if (tableHeader != null)
					resizingColumn = tableHeader.getResizingColumn();

				// Viewport size changed. May need to increase columns widths

				if (resizingColumn == null) {
					setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					super.doLayout();
				}

				// Specific column resized. Reset preferred widths

				else {
					TableColumnModel tcm = getColumnModel();

					for (int i = 0; i < tcm.getColumnCount(); i++) {
						TableColumn tc = tcm.getColumn(i);
						tc.setPreferredWidth(tc.getWidth());
					}

					// Columns don't fill the viewport, invoke default layout

					if (tcm.getTotalColumnWidth() < getParent().getWidth())
						setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					super.doLayout();
				}

				setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			}

		};
		table_CT.setBackground(SystemColor.control);
		table_CT.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table_CT.setRowHeight(45);
		table_CT.setModel(tableModel_CT);
		table_CT.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 20));
		table_CT.getTableHeader().setBackground(new Color(255, 204, 102));
		table_CT.getColumnModel().getColumn(0).setMinWidth(140);
		table_CT.getColumnModel().getColumn(1).setMinWidth(575);
		table_CT.getColumnModel().getColumn(2).setMinWidth(175);
		scrollPane_CongTrinh.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane_CongTrinh.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_CongTrinh.setViewportView(table_CT);
		table_CT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table_CT.getSelectedRow();

				// load dữ liệu công việc thuộc công trình được chọn
				try {
					tableModel.setRowCount(0);
					dao_Congviec.loadData(
							"select * from CongViec where idcongtrinh = '" + table_CT.getValueAt(i, 0) + "'",
							tableModel);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				super.mouseClicked(e);
			}
		});
		// load dữ liệu công trình đang thi công

		try {
			tableModel_CT.setRowCount(0);
			dao_Congtrinh.getAllCT("select * from CongTrinh where tiendo = N'Đang Thực Hiện'", tableModel_CT);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		txtTimCT = new JTextField();
		txtTimCT.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTimCT.setBounds(1160, 51, 386, 35);
		panel_CongTrinh.add(txtTimCT);
		txtTimCT.setColumns(10);

		btnTimCT = new JButton("");
		btnTimCT.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTimCT.setIcon(new ImageIcon("icons/loupe.png"));
		btnTimCT.setBounds(1556, 51, 49, 35);
		panel_CongTrinh.add(btnTimCT);

		JLabel lblTimCT = new JLabel("Tìm công trình:");
		lblTimCT.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTimCT.setBounds(1160, 15, 275, 30);
		panel_CongTrinh.add(lblTimCT);

		lblLoaiCT = new JLabel("Loại công trình:");
		lblLoaiCT.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblLoaiCT.setBounds(1160, 97, 275, 30);
		panel_CongTrinh.add(lblLoaiCT);

		cbbLoaiCT = new JComboBox<String>();
		cbbLoaiCT.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbbLoaiCT.addItem("--Tất cả--");
		try {
			ArrayList<LoaiCongTrinh> listLCT = dao_lct.getLoaiCongtrinhs();
			for (int i = 0; i < listLCT.size(); i++) {
				LoaiCongTrinh lct = listLCT.get(i);
				cbbLoaiCT.addItem(lct.getTenLoai());
			}
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		cbbLoaiCT.setBounds(1160, 138, 386, 35);
		panel_CongTrinh.add(cbbLoaiCT);

		lblNamKhoiCong = new JLabel("Năm khởi công:");
		lblNamKhoiCong.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNamKhoiCong.setBounds(1160, 184, 275, 30);
		panel_CongTrinh.add(lblNamKhoiCong);

		cbbNamKhoicong = new JComboBox<String>();
		cbbNamKhoicong.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbbNamKhoicong.addItem("--Tất cả--");
		for (int years = Calendar.getInstance().get(Calendar.YEAR); years >= 2000; years--) {
			cbbNamKhoicong.addItem(years + "");
		}
		cbbNamKhoicong.setBounds(1160, 225, 386, 35);
		panel_CongTrinh.add(cbbNamKhoicong);
		btnTimCT.addActionListener(this);

		JPanel panel_Chitiet = new JPanel();
		panel_Chitiet.setBounds(1160, 450, 460, 562);
		add(panel_Chitiet);
		panel_Chitiet.setForeground(Color.RED);
		panel_Chitiet.setLayout(null);
		panel_Chitiet.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Tìm kiếm công việc", TitledBorder.CENTER, TitledBorder.TOP,
				new Font("Tahoma", Font.BOLD, 16), new Color(255, 0, 0)));
		panel_Chitiet.setBackground(SystemColor.window);

		JLabel lblTimCV = new JLabel("Tìm công việc:");
		lblTimCV.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTimCV.setBounds(10, 31, 275, 30);
		panel_Chitiet.add(lblTimCV);

		txtTimCV = new JTextField();
		txtTimCV.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTimCV.setBounds(10, 71, 361, 35);
		panel_Chitiet.add(txtTimCV);
		txtTimCV.setColumns(10);

		btnTimCV = new JButton("");
		btnTimCV.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTimCV.setIcon(new ImageIcon("icons/loupe.png"));
		btnTimCV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTimCV.setBounds(381, 71, 49, 35);
		panel_Chitiet.add(btnTimCV);

		JLabel lblNhanVien = new JLabel("Nhân viên khởi tạo:");
		lblNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNhanVien.setBounds(10, 378, 275, 30);
		panel_Chitiet.add(lblNhanVien);

		List<NhanVien> nhanviens = new ArrayList<NhanVien>();
		try {
			nhanviens = dao_nv.getDsNhanvien();
		} catch (Exception e) {
			e.printStackTrace();
		}
		cbbNhanVien = new JComboBox<String>();
		cbbNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbbNhanVien.addItem("Tất cả");
		for (NhanVien nhanVien : nhanviens) {
			cbbNhanVien.addItem(nhanVien.getMaNhanvien() + " - " + nhanVien.getTenNhanvien());
		}
		cbbNhanVien.setBounds(10, 419, 361, 35);
		panel_Chitiet.add(cbbNhanVien);
		cbbNhanVien.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nv = (String) cbbNhanVien.getSelectedItem();
				String maNV = nv.substring(0, 9);
				int i = table_CT.getSelectedRow();
				if(i == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một công trình để lọc công việc");
				}
				if(nv.equals("Tất cả")) {
					try {
						tableModel.setRowCount(0);
						dao_Congviec.loadData("select * from CongViec where idcongtrinh = '" + table_CT.getValueAt(i, 0).toString()
								+ "'", tableModel);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						tableModel.setRowCount(0);
						dao_Congviec.loadData("select * from CongViec where idcongtrinh = '" + table_CT.getValueAt(i, 0).toString()
								+ "' and IDNhanVien = N'" + maNV + "'", tableModel);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});

		JLabel lblNgayBd = new JLabel("Bắt đầu sau ngày:");
		lblNgayBd.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNgayBd.setBounds(10, 291, 275, 30);
		panel_Chitiet.add(lblNgayBd);

		dcNgayBd = new JDateChooser();
		dcNgayBd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dcNgayBd.setBounds(10, 332, 361, 35);
		panel_Chitiet.add(dcNgayBd);

		JLabel lblTrangThai = new JLabel("Trạng thái:");
		lblTrangThai.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTrangThai.setBounds(10, 204, 275, 30);
		panel_Chitiet.add(lblTrangThai);

		cbbTrangThai = new JComboBox<String>();
		cbbTrangThai.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbbTrangThai.addItem("Chưa hoàn thành");
		cbbTrangThai.addItem("Hoàn thành");
		cbbTrangThai.addItem("Tất cả");
		cbbTrangThai.setBounds(10, 245, 361, 35);
		panel_Chitiet.add(cbbTrangThai);
		cbbTrangThai.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String tt = (String) cbbTrangThai.getSelectedItem();
				int i = table_CT.getSelectedRow();
				if(i == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một công trình để lọc công việc");
				}
				if(tt.equals("Tất cả")) {
					try {
						tableModel.setRowCount(0);
						dao_Congviec.loadData("select * from CongViec where idcongtrinh = '" + table_CT.getValueAt(i, 0).toString()
								+ "'", tableModel);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						tableModel.setRowCount(0);
						dao_Congviec.loadData("select * from CongViec where idcongtrinh = '" + table_CT.getValueAt(i, 0).toString()
								+ "' and TrangThai = N'" + tt + "'", tableModel);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});

		btnTimCvTheoNgayBd = new JButton("");
		btnTimCvTheoNgayBd.setIcon(new ImageIcon("icons/loupe.png"));
		btnTimCvTheoNgayBd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTimCvTheoNgayBd.setBounds(381, 332, 49, 35);
		panel_Chitiet.add(btnTimCvTheoNgayBd);

		// Lấy thông tin nhân viên
		DAO_Nhanvien dao_Nhanvien = new DAO_Nhanvien();
		try {
			nhanvien = dao_Nhanvien.getNhanvienByID(maNhanvien);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		btnThem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCapNhat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHelp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnKetthucCV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTimCT.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTimCV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTimCvTheoNgayBd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbbLoaiCT.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbbNamKhoicong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbbNhanVien.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbbTrangThai.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dcNgayBd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		// đăng ký sự kiện
		btnThem.addActionListener(this);
		btnCapNhat.addActionListener(this);
		btnKetthucCV.addActionListener(this);
		cbbLoaiCT.addActionListener(this);
		cbbNamKhoicong.addActionListener(this);
		btnTimCV.addActionListener(this);
		btnTimCvTheoNgayBd.addActionListener(this);
		
		txtTimCV.getInputMap(JTextField.WHEN_FOCUSED)
		.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Tìm CV");
		txtTimCV.getActionMap().put("Tìm CV", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				DAO_CongViec dao = new DAO_CongViec();
				try {
					String timkiem = txtTimCV.getText().toLowerCase(); // chuyển in hoa thành in thường hết để tìm kiếm đầy
																		// đủ
					int i2 = table_CT.getSelectedRow();
					String mact = table_CT.getValueAt(i2, 0).toString();
					if(i2 == -1) {
						JOptionPane.showMessageDialog(null, "Vui lòng chọn một công trình để lọc công việc");
					}
					tableModel.setRowCount(0);
					dao.loadData("select *  from CongViec where IDCongViec like N'%" + timkiem + "%' and IDCongTrinh = '"
							+ mact + "'", tableModel); // tìm theo mã
					dao.loadData("select *  from CongViec where TenCongViec like N'%" + timkiem + "%' and IDCongTrinh = '"
							+ mact + "'", tableModel); // tìm theo tên

					txtTimCV.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		txtTimCT.getInputMap(JTextField.WHEN_FOCUSED)
		.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Tìm CT");
		txtTimCT.getActionMap().put("Tìm CT", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				DAO_CongTrinh dao = new DAO_CongTrinh();
				try {
					String timkiem = txtTimCT.getText().toLowerCase(); // chuyển in hoa thành in thường hết để tìm kiếm đầy đủ
					tableModel_CT.setRowCount(0);
					dao.getAllCT("select *  from CongTrinh where IDCongTrinh like N'%" + timkiem
							+ "%' and TienDo = N'Đang thực hiện'", tableModel_CT); // tìm theo mã
					dao.getAllCT("select *  from CongTrinh where TenCongTrinh like N'%" + timkiem
							+ "%' and TienDo = N'Đang thực hiện'", tableModel_CT); // tìm theo tên
					txtTimCT.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		int i = table_CT.getSelectedRow();
		int i1 = table.getSelectedRow();
		if (o.equals(btnThem)) {
			if (i >= 0) {
				DAO_CongTrinh dao = new DAO_CongTrinh();
				try {
					if(dao.getCongTrinhByID(table_CT.getValueAt(i, 0).toString()).getTienDo().equals("Hoàn thành")) {
						JOptionPane.showMessageDialog(this, "Công trình đã hoàn thành.\nKhông thể phân công thêm");
					} else {
						FormThemCongViec form = new FormThemCongViec(table_CT.getValueAt(i, 0).toString(),
								nhanvien.getMaNhanvien());
						form.setVisible(true);
						form.addWindowListener(new WindowAdapter() {
							public void windowClosed(WindowEvent e) {
								reload();
							}
						});
					}
				} catch (HeadlessException | SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "Đã có lỗi phát sinh.\nVui lòng chọn một công trình dể thực hiện các thao tác");
				}
			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn công trình cần thêm công việc");
			}
		}
		if (o.equals(btnCapNhat)) {
			if (i1 >= 0) {
				FormCapNhatCongViec form = new FormCapNhatCongViec(table.getValueAt(i1, 0).toString());
				form.setVisible(true);
				form.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						reload();
					}
				});
			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn công việc cần cập nhật");
			}
		}
		if (o.equals(btnKetthucCV)) {
			DAO_CongViec dao = new DAO_CongViec();
			DAO_BangPhanCong dao_bpc = new DAO_BangPhanCong();
			try {
				if(dao.getCongviecByID(table.getValueAt(i1, 0).toString()).getTrangthai().equals("Hoàn thành")) {
					JOptionPane.showMessageDialog(this, "Công việc đã hoàn thành.");
				} else {
					try {
						dao.hoanthanhCongviec(table.getValueAt(i1, 0).toString());
						dao_bpc.hoanthanhCongviec(table.getValueAt(i1, 0).toString());
						JOptionPane.showMessageDialog(null, "Công việc hoàn thành");
						reload();
					} catch (Exception e2) {
						e2.printStackTrace();
						JOptionPane.showMessageDialog(null, "Đã có lỗi xảy ra. Vui lòng thử lại");
					}
				}
			} catch (HeadlessException | SQLException e1) {
				JOptionPane.showMessageDialog(this, "Đã có lỗi phát sinh.\nVui lòng chọn một công việc dể thực hiện các thao tác");
				e1.printStackTrace();
			}
		}
		if (o.equals(btnTimCV)) {
			DAO_CongViec dao = new DAO_CongViec();
			try {
				String timkiem = txtTimCV.getText().toLowerCase(); // chuyển in hoa thành in thường hết để tìm kiếm đầy đủ
				int i2 = table_CT.getSelectedRow();
				String mact = table_CT.getValueAt(i2, 0).toString();
				if(i2 == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một công trình để lọc công việc");
				}
				tableModel.setRowCount(0);
				dao.loadData("select *  from CongViec where IDCongViec like N'%" + timkiem + "%' and IDCongTrinh = '"
						+ mact + "'", tableModel); // tìm theo mã
				dao.loadData("select *  from CongViec where TenCongViec like N'%" + timkiem + "%' and IDCongTrinh = '"
						+ mact + "'", tableModel); // tìm theo tên

				txtTimCV.setText("");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btnTimCT)) {
			DAO_CongTrinh dao = new DAO_CongTrinh();
			try {
				String timkiem = txtTimCT.getText().toLowerCase(); // chuyển in hoa thành in thường hết để tìm kiếm đầy đủ
				tableModel_CT.setRowCount(0);
				dao.getAllCT("select *  from CongTrinh where IDCongTrinh like N'%" + timkiem
						+ "%' and TienDo = N'Đang thực hiện'", tableModel_CT); // tìm theo mã
				dao.getAllCT("select *  from CongTrinh where TenCongTrinh like N'%" + timkiem
						+ "%' and TienDo = N'Đang thực hiện'", tableModel_CT); // tìm theo tên
				txtTimCT.setText("");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(cbbLoaiCT)) {
			DAO_CongTrinh dao = new DAO_CongTrinh();
			String s = (String) cbbLoaiCT.getSelectedItem(); // lấy thông tin item được chọn
			if(s.equals("--Tất cả--")) {
				try {
					tableModel_CT.setRowCount(0);
					dao.getAllCT(
							"select IDCongTrinh, TenCongTrinh, TienDo from CongTrinh as ct join LoaiCongTrinh as lct on ct.IDLoaiCT=lct.IDLoaiCT where TenLoaiCongTrinh = N'"
									+ s + "' and (TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành')",
							tableModel_CT);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {
				try {
					tableModel_CT.setRowCount(0);
					dao.getAllCT(
							"select IDCongTrinh, TenCongTrinh, TienDo from CongTrinh as ct join LoaiCongTrinh as lct on ct.IDLoaiCT=lct.IDLoaiCT where TenLoaiCongTrinh = N'"
									+ s + "' and ct.TienDo = N'Đang thực hiện'",
							tableModel_CT);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		}
		// SELECT * FROM CongTrinh where YEAR(NgayKhoiCong) = 2020
		if (o.equals(cbbNamKhoicong)) {
			DAO_CongTrinh dao = new DAO_CongTrinh();
			String s = (String) cbbNamKhoicong.getSelectedItem(); // lấy thông tin item được chọn
			if(s.equals("--Tất cả--")) {
				tableModel_CT.setRowCount(0);
				try {
					dao.getAllCT(
							"SELECT * FROM CongTrinh where YEAR(NgayKhoiCong) = " + s + " and (TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành')",
							tableModel_CT);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {
				try {
					tableModel_CT.setRowCount(0);
					dao.getAllCT(
							"SELECT * FROM CongTrinh where YEAR(NgayKhoiCong) = " + s + " and TienDo = N'Đang thực hiện'",
							tableModel_CT);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (o.equals(btnTimCvTheoNgayBd)) {
			DAO_CongViec dao = new DAO_CongViec();
			int i2 = table_CT.getSelectedRow();
			String mact = table_CT.getValueAt(i2, 0).toString();
			tableModel.setRowCount(0);
			java.util.Date date_util = dcNgayBd.getDate();
			Date d = new Date(date_util.getTime());
			try {
				dao.isAfter(d, mact, tableModel);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void reload() {
		DAO_CongViec dao_Congviec = new DAO_CongViec();
		int i = table_CT.getSelectedRow();

		// load dữ liệu công việc thuộc công trình được chọn
		try {
			tableModel.setRowCount(0);
			dao_Congviec.loadData("select * from CongViec where idcongtrinh = '" + table_CT.getValueAt(i, 0).toString() + "'",
					tableModel);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
