package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import dao.DAO_CongTrinh;
import dao.DAO_LoaiCongTrinh;
import entity.LoaiCongTrinh;
/**
 * 
 * @author nmthu
 *
 */
public class FormQLCongTrinh extends JPanel implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtMaCT;
	private JTextArea txtTenCT;
	private JTextField txtLoaiCT;
	private JTextField txtNgayCP;
	private JTextField txtNgayKC;
	private JTextField txtNgayDK;
	private JTextArea txtDiadiem;
	private JTextField txtTienDo;

	private JButton btnThem;

	private JButton btnCapNhat;

	private JButton btnXoa;

	private JButton btnTim;
	private JTextField txtTim;
	private JButton btnTaiLai;
	private JComboBox<String> cbTim;
	private JComboBox<String> cbTienDo;
	private JScrollPane scrollPane;
	private JButton btnHelp;
	private JPanel pnMain;
	private JPanel pnTitle;
	private JPanel pnChiTietTable;
	private JLabel lblCount;
	private int count;
	private JComboBox<String> cbLoaiCT;

	private DAO_CongTrinh dao = new DAO_CongTrinh();
	private DAO_LoaiCongTrinh dao_lct = new DAO_LoaiCongTrinh();
	private BufferedImage image;

	/**
	 * Create the panel.
	 */
	public FormQLCongTrinh() {
		setBounds(0, 0, 1620, 1020);
		setLayout(null);

		// main panel
		pnMain = new JPanel();
		pnMain.setBackground(new Color(255, 255, 255));
		pnMain.setBounds(0, 0, 1620, 1020);
		pnMain.setLayout(null);
		add(pnMain);

		// Panel chức năng quản lý
		JPanel pnChucNang = new JPanel();
		pnChucNang.setBackground(Color.WHITE);
		pnChucNang.setBounds(0, 45, 1620, 70);
		pnChucNang.setLayout(null);
		pnMain.add(pnChucNang);

		btnThem = new JButton("Thêm");
		btnThem.setForeground(SystemColor.controlText);
		btnThem.setBackground(new Color(255, 204, 102));
		btnThem.setFont(new Font("Arial", Font.BOLD, 28));
		btnThem.setBounds(10, 11, 200, 50);
		btnThem.setIcon(new ImageIcon("icons/plus32.png"));
		btnThem.setFocusable(false);
		pnChucNang.add(btnThem);

		btnCapNhat = new JButton("Cập nhật");
		btnCapNhat.setForeground(SystemColor.controlText);
		btnCapNhat.setBackground(new Color(255, 204, 102));
		btnCapNhat.setFont(new Font("Arial", Font.BOLD, 28));
		btnCapNhat.setBounds(220, 11, 200, 50);
		btnCapNhat.setIcon(new ImageIcon("icons/edit.png"));
		btnCapNhat.setFocusable(false);
		pnChucNang.add(btnCapNhat);

		btnXoa = new JButton("Xóa");
		btnXoa.setForeground(SystemColor.controlText);
		btnXoa.setBackground(new Color(255, 204, 102));
		btnXoa.setFont(new Font("Arial", Font.BOLD, 28));
		btnXoa.setBounds(430, 11, 200, 50);
		btnXoa.setIcon(new ImageIcon("icons/remove.png"));
		btnXoa.setFocusable(false);
		pnChucNang.add(btnXoa);

		txtTim = new JTextField();
		txtTim.setBounds(1129, 17, 360, 38);
		txtTim.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtTim.setColumns(10);
		pnChucNang.add(txtTim);

		btnTim = new JButton("Tìm");
		btnTim.setIcon(new ImageIcon("icons/loupe.png"));
		btnTim.setBackground(new Color(255, 204, 102));
		btnTim.setFont(new Font("Arial", Font.PLAIN, 26));
		btnTim.setFocusable(false);
		btnTim.setBounds(1490, 16, 125, 40);
		pnChucNang.add(btnTim);

		cbTim = new JComboBox<String>();
		cbTim.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbTim.addItem("Theo mã");
		cbTim.addItem("Theo tên");
		cbTim.setBounds(968, 17, 157, 38);
		pnChucNang.add(cbTim);

		cbLoaiCT = new JComboBox<String>();
		cbLoaiCT.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbLoaiCT.setBounds(610, 120, 160, 40);
		cbLoaiCT.addItem("--Loại CT--");
		pnMain.add(cbLoaiCT);
		try {
			ArrayList<LoaiCongTrinh> listLCT = dao_lct.getLoaiCongtrinhs();
			for (int i = 0; i < listLCT.size(); i++) {
				LoaiCongTrinh lct = listLCT.get(i);
				cbLoaiCT.addItem(lct.getTenLoai());
			}
		} catch (SQLException e3) {
			e3.printStackTrace();
		}

		cbTienDo = new JComboBox<String>();
		cbTienDo.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbTienDo.setBounds(780, 120, 195, 40);
		cbTienDo.addItem("--Tiến độ--");
		cbTienDo.addItem("Đang Thực Hiện");
		cbTienDo.addItem("Hoàn Thành");
		cbTienDo.addItem("Tất cả");
		pnMain.add(cbTienDo);

		// panel tiêu đề
		pnTitle = new JPanel();
		pnTitle.setBorder(null);
		pnTitle.setBackground(new Color(255, 204, 102));
		pnTitle.setBounds(0, 0, 1620, 45);
		pnMain.add(pnTitle);
		pnTitle.setLayout(null);

		// tiêu đề
		JLabel lblTitle = new JLabel("Quản Lý Công trình");
		lblTitle.setForeground(new Color(0, 0, 51));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTitle.setBounds(10, 2, 350, 40);
		pnTitle.add(lblTitle);

		// Nút help
		btnHelp = new JButton("");
		btnHelp.setIcon(new ImageIcon("icons/infor.png"));
		btnHelp.setBorder(null);
		btnHelp.setBorderPainted(false);
		btnHelp.setBackground(new Color(255, 204, 102));
		btnHelp.setForeground(new Color(0, 0, 0));
		btnHelp.setBounds(1575, 2, 40, 40);
		pnTitle.add(btnHelp);
		btnHelp.addActionListener(this);
	
		// tiêu đề bảng
		JLabel lblDsCt = new JLabel("Danh sách Công trình:");
		lblDsCt.setFont(new Font("Arial", Font.BOLD, 28));
		lblDsCt.setBounds(10, 121, 400, 40);
		pnMain.add(lblDsCt);

		pnChiTietTable = new JPanel();
		pnChiTietTable.setBounds(0, 985, 1130, 35);
		pnChiTietTable.setBackground(new Color(255, 239, 213));
		pnMain.add(pnChiTietTable);
		pnChiTietTable.setLayout(null);

		// cái bảng đây nè
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(0, 165, 1130, 820);
		pnMain.add(scrollPane);

		String[] header = { "Mã CT", "Tên công trình", "Địa Điểm", "Tiến độ" };
		model = new DefaultTableModel(header, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false };

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
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 20));
		table.getTableHeader().setBackground(new Color(255, 208, 120));
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.setBackground(SystemColor.control);
		table.setRowHeight(45);
		table.setModel(model);
		try {
			load("select * from CongTrinh where TienDo = N'Đang thực hiện'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		count = model.getRowCount();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

					int i = table.getSelectedRow();
					txtMaCT.setText(table.getValueAt(i, 0).toString());
					txtTenCT.setText(table.getValueAt(i, 1).toString());
					txtDiadiem.setText(table.getValueAt(i, 2).toString());
					Date dateCP = dao.getCongTrinhByID(txtMaCT.getText()).getNgayCapPhep();
					Date dateKC = dao.getCongTrinhByID(txtMaCT.getText()).getNgayKhoiCong();
					Date dateDK = dao.getCongTrinhByID(txtMaCT.getText()).getNgayDuKien();
					txtNgayCP.setText(df.format(dateCP));
					txtNgayKC.setText(df.format(dateKC));
					txtNgayDK.setText(df.format(dateDK));
					txtLoaiCT.setText(dao.getCongTrinhByID(txtMaCT.getText()).getLoaiCT().getTenLoai());
					txtTienDo.setText(table.getValueAt(i, 3).toString());

					super.mouseClicked(e);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(table);
		//
		lblCount = new JLabel("");
		lblCount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCount.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblCount.setBounds(134, 0, 996, 34);
		lblCount.setText(Integer.toString(count) + " công trình đang thực hiện");
		pnChiTietTable.add(lblCount);

		// panel chi tiết công trình (bên phải)
		JPanel pnChiTiet = new JPanel();
		pnChiTiet.setBackground(new Color(255, 239, 213));
		pnChiTiet.setBounds(1130, 115, 490, 905);
		pnChiTiet.setLayout(null);
		pnMain.add(pnChiTiet);

		JLabel lblThongTinCN = new JLabel("THÔNG TIN CÔNG TRÌNH");
		lblThongTinCN.setFont(new Font("Arial", Font.BOLD, 32));
		lblThongTinCN.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinCN.setBounds(0, 0, 490, 50);
		pnChiTiet.add(lblThongTinCN);

		JLabel lblMaCT = new JLabel("Mã công trình:");
		lblMaCT.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblMaCT.setBounds(10, 57, 281, 30);
		pnChiTiet.add(lblMaCT);

		txtMaCT = new JTextField();
		txtMaCT.setForeground(Color.BLACK);
		txtMaCT.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtMaCT.setEditable(false);
		txtMaCT.setColumns(10);
		txtMaCT.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtMaCT.setBackground(SystemColor.menu);
		txtMaCT.setBounds(10, 92, 435, 35);
		pnChiTiet.add(txtMaCT);

		JLabel lblTenCT = new JLabel("Tên công trình:");
		lblTenCT.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblTenCT.setBounds(10, 132, 281, 30);
		pnChiTiet.add(lblTenCT);

		JScrollPane scrollPane_TenCT = new JScrollPane();
		scrollPane_TenCT.setBounds(10, 167, 435, 93);
		pnChiTiet.add(scrollPane_TenCT);

		txtTenCT = new JTextArea();
		scrollPane_TenCT.setViewportView(txtTenCT);
		txtTenCT.setForeground(Color.BLACK);
		txtTenCT.setLineWrap(true);
		txtTenCT.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtTenCT.setEditable(false);
		txtTenCT.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtTenCT.setBackground(SystemColor.menu);

		JLabel lblDiadiem = new JLabel("Địa điểm:");
		lblDiadiem.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblDiadiem.setBounds(10, 266, 281, 30);
		pnChiTiet.add(lblDiadiem);

		JScrollPane scrollPane_Diadiem = new JScrollPane();
		scrollPane_Diadiem.setBounds(10, 305, 435, 106);
		pnChiTiet.add(scrollPane_Diadiem);

		txtDiadiem = new JTextArea();
		txtDiadiem.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtDiadiem.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtDiadiem.setBackground(SystemColor.menu);
		txtDiadiem.setLineWrap(true);
		scrollPane_Diadiem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_Diadiem.setViewportView(txtDiadiem);

		JLabel lblNgayCapPhep = new JLabel("Ngày cấp phép:");
		lblNgayCapPhep.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNgayCapPhep.setBounds(10, 422, 281, 30);
		pnChiTiet.add(lblNgayCapPhep);

		txtNgayCP = new JTextField();
		txtNgayCP.setForeground(Color.BLACK);
		txtNgayCP.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtNgayCP.setEditable(false);
		txtNgayCP.setColumns(10);
		txtNgayCP.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtNgayCP.setBackground(SystemColor.menu);
		txtNgayCP.setBounds(10, 457, 435, 35);
		pnChiTiet.add(txtNgayCP);

		JLabel lblNgayKhoiCong = new JLabel("Ngày khởi công:");
		lblNgayKhoiCong.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNgayKhoiCong.setBounds(10, 503, 281, 30);
		pnChiTiet.add(lblNgayKhoiCong);

		txtNgayKC = new JTextField();
		txtNgayKC.setForeground(Color.BLACK);
		txtNgayKC.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtNgayKC.setEditable(false);
		txtNgayKC.setColumns(10);
		txtNgayKC.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtNgayKC.setBackground(SystemColor.menu);
		txtNgayKC.setBounds(10, 538, 435, 35);
		pnChiTiet.add(txtNgayKC);

		JLabel lblNgayDukienHoanthanh = new JLabel("Ngày dự kiến hoàn thành:");
		lblNgayDukienHoanthanh.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNgayDukienHoanthanh.setBounds(10, 584, 370, 30);
		pnChiTiet.add(lblNgayDukienHoanthanh);

		txtNgayDK = new JTextField();
		txtNgayDK.setForeground(Color.BLACK);
		txtNgayDK.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtNgayDK.setEditable(false);
		txtNgayDK.setColumns(10);
		txtNgayDK.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtNgayDK.setBackground(SystemColor.menu);
		txtNgayDK.setBounds(10, 619, 435, 35);
		pnChiTiet.add(txtNgayDK);

		JLabel lblLoaiCongtrinh = new JLabel("Loại công trình:");
		lblLoaiCongtrinh.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblLoaiCongtrinh.setBounds(10, 665, 281, 30);
		pnChiTiet.add(lblLoaiCongtrinh);

		txtLoaiCT = new JTextField();
		txtLoaiCT.setForeground(Color.BLACK);
		txtLoaiCT.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtLoaiCT.setEditable(false);
		txtLoaiCT.setColumns(10);
		txtLoaiCT.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtLoaiCT.setBackground(SystemColor.menu);
		txtLoaiCT.setBounds(10, 700, 435, 35);
		pnChiTiet.add(txtLoaiCT);

		JLabel lblTiendo = new JLabel("Tiến độ:");
		lblTiendo.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblTiendo.setBounds(10, 746, 281, 30);
		pnChiTiet.add(lblTiendo);

		txtTienDo = new JTextField();
		txtTienDo.setForeground(Color.BLACK);
		txtTienDo.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtTienDo.setEditable(false);
		txtTienDo.setColumns(10);
		txtTienDo.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtTienDo.setBackground(SystemColor.menu);
		txtTienDo.setBounds(10, 787, 435, 35);
		pnChiTiet.add(txtTienDo);

		btnTaiLai = new JButton("Tải lại");
		btnTaiLai.setBounds(985, 123, 141, 35);
		btnTaiLai.setFont(new Font("Tahoma", Font.PLAIN, 18));
		try {
			image = ImageIO.read(new File("icons/reload.png"));
			ImageIcon reloadIcon = new ImageIcon(image.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
			btnTaiLai.setIcon(reloadIcon);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		btnTaiLai.setFocusable(false);
		btnTaiLai.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnTaiLai.setBorderPainted(false);
		btnTaiLai.setContentAreaFilled(false);
		btnTaiLai.setOpaque(false);
		btnTaiLai.setBackground(new Color(255, 204, 102));
		pnMain.add(btnTaiLai);

		// pn
		JPanel panel = new JPanel();
		panel.setBounds(0, 115, 1130, 52);
		panel.setBackground(new Color(255, 239, 213));
		pnMain.add(panel);
		panel.setLayout(null);

		btnCapNhat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHelp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTaiLai.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTim.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnXoa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbLoaiCT.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbTienDo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbTim.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// đăng ký sự kiện
		btnTaiLai.addActionListener(this);
		btnThem.addActionListener(this);
		btnCapNhat.addActionListener(this);
		btnTim.addActionListener(this);
		btnXoa.addActionListener(this);
		cbLoaiCT.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) cbLoaiCT.getSelectedItem(); // lấy thông tin item được chọn
				try {
					model.setRowCount(0);
					dao.loadData(
							"select IDCongTrinh, TenCongTrinh, DiaDiem, TienDo from CongTrinh as ct join LoaiCongTrinh as lct on ct.IDLoaiCT=lct.IDLoaiCT where TenLoaiCongTrinh = N'"
									+ s + "'  and (TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành')",
							model);
					count = getRowCount();
					lblCount.setText(Integer.toString(count) + " công trình " + s);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		cbTienDo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = (String) cbTienDo.getSelectedItem(); // lấy thông tin tiến độ item được chọn

				switch (s) {
				case "Đang Thực Hiện":
					try {
						model.setRowCount(0);
						load("select * from CongTrinh where tiendo = N'Đang Thực Hiện'");
						count = getRowCount();
						lblCount.setText(Integer.toString(count) + " công trình đang thực hiện");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					break;
				case "Hoàn Thành":
					try {
						model.setRowCount(0);

						load("select * from CongTrinh where TienDo = N'Hoàn Thành'");
						count = getRowCount();
						lblCount.setText(Integer.toString(count) + " công trình đã hoàn thành");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					break;
				case "Tất cả":
					try {
						load("select * from CongTrinh where TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành'");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					break;
				default:

				}

			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if (o.equals(btnTim)) {
			String timkiem = txtTim.getText().toLowerCase().trim();
			String s = (String) cbTim.getSelectedItem();

			if (s.equalsIgnoreCase("Theo mã")) {
				if (!timkiem.equals("")) {
					model.setRowCount(0);
					try {
						dao.loadData("select * from CongTrinh where IDCongTrinh like N'%" + timkiem + "%'", model);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					txtTim.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "Mã CT gồm CTxxxx! \n Tìm kiếm gần đúng", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					txtTim.requestFocus();
					return;
				}
			}
			if (s.equalsIgnoreCase("Theo tên")) {
				if (!timkiem.equals("")) {
					model.setRowCount(0);
					try {
						dao.loadData("select * from CongTrinh where TenCongTrinh like N'%" + timkiem + "%'", model);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					txtTim.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "Vui lòng nhập tên công Trình! \n Tìm kiếm gần đúng", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					txtTim.requestFocus();
					return;
				}
			}
		}
		if (o.equals(btnXoa)) {
			int row = table.getSelectedRow();

			try {
				if (row != -1) {
					int hoiNhac = JOptionPane.showConfirmDialog(this, "Công trình Này Sẽ Bị Xóa !!!", "Chú ý",
							JOptionPane.YES_NO_OPTION);
					if (hoiNhac == JOptionPane.YES_OPTION) {
						int r = table.getSelectedRow();
						model.removeRow(r);
						xoaCT();
						clear();
					}
				} else
					JOptionPane.showMessageDialog(null, "Vui lòng chọn Công trình để xóa");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (o.equals(btnTaiLai)) {
			try {
				load("select * from CongTrinh where TienDo = N'Đang Thực Hiện'");
				cbTienDo.setSelectedItem("--Tiến độ--");
				clear();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (o.equals(btnCapNhat)) {
			int i = table.getSelectedRow();
			if (i != -1) {
				FormCapNhatCongTrinh frmCapNhat = new FormCapNhatCongTrinh(txtMaCT.getText(), txtLoaiCT.getText());
				frmCapNhat.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn công trình cần cập nhật thông tin");
			}

		}
		if (o.equals(btnThem)) {
			FormThemCongTrinh frmThemCT = new FormThemCongTrinh();
			frmThemCT.setVisible(true);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	// load du lieu

	public void load(String s) throws SQLException {
		model.setRowCount(0);
		dao.loadData(s, model);
		resizeCol();
	}
	// xoa cong trinh

	private void xoaCT() throws SQLException {
		DAO_CongTrinh dao = new DAO_CongTrinh();
		dao.xoaCT(txtMaCT.getText());
		load("select * from CongTrinh where TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành'");
	}

	private void clear() {
		txtMaCT.setText("");
		txtTenCT.setText("");
		txtDiadiem.setText("");
		txtNgayCP.setText("");
		txtNgayKC.setText("");
		txtNgayDK.setText("");
		txtLoaiCT.setText("");
		txtTienDo.setText("");
		txtTim.setText("");
	}

	private int getRowCount() {
		return model.getRowCount();
	}

	private void resizeCol() {
		table.getColumnModel().getColumn(0).setMinWidth(60);
		table.getColumnModel().getColumn(1).setMinWidth(350);
		table.getColumnModel().getColumn(2).setMinWidth(350);
		table.getColumnModel().getColumn(3).setMinWidth(100);
	}
}
