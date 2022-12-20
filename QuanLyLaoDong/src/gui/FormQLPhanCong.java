package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import connect.ConnectDB;
import dao.DAO_CongTrinh;
import dao.DAO_LoaiCongTrinh;
import entity.CongTrinh;
import entity.LoaiCongTrinh;
/**
 * 
 * @author nmthu
 *
 */
public class FormQLPhanCong extends JPanel implements ActionListener, MouseListener {
	private JTable table;
	private JButton btnPhanCong;
	private JButton btnTimKiem;
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
	private JTextField txtMaCT;
	private JTextArea txtTenCT;
	private JTextField txtNgayCapPhep;
	private JTextField txtNgayKhoiCong;
	private JTextField txtNgayDukienHoanthanh;
	private JTextField txtLoaiCongtrinh;
	private JTextField txtTiendo;
	private JComboBox<String> cbboxTim;
	private JTextArea txtDiadiem;
	private JButton btnReload;
	private JButton btnThemCV;
	private JPanel pnChiTiet;
	private JComboBox<String> cbboxLoaiCT;

	String maNV;
	private JPanel pnTitle;
	private JButton btnHelp;
	private JTextField txtTimkiemCT;

	/**
	 * Create the panel.
	 */
	public FormQLPhanCong(String idNhanvien) {
		setBounds(0, 0, 1620, 1040);
		setLayout(null);

		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		maNV = idNhanvien;
		DAO_LoaiCongTrinh dao_lct = new DAO_LoaiCongTrinh();
		DAO_CongTrinh dao_Congtrinh = new DAO_CongTrinh();

		pnTitle = new JPanel();
		pnTitle.setBorder(null);
		pnTitle.setLayout(null);
		pnTitle.setBackground(new Color(255, 204, 102));
		pnTitle.setBounds(0, 0, 1620, 45);
		add(pnTitle);

		JLabel lblTitle = new JLabel("Quản lý phân công\r\n");
		lblTitle.setForeground(new Color(0, 0, 51));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTitle.setBounds(10, 2, 350, 40);
		pnTitle.add(lblTitle);

		btnHelp = new JButton("");
		btnHelp.setIcon(new ImageIcon("icons/infor.png"));
		btnHelp.setBorder(null);
		btnHelp.setBorderPainted(false);
		btnHelp.setBackground(new Color(255, 204, 102));
		btnHelp.setForeground(new Color(0, 0, 0));
		btnHelp.setBounds(1575, 2, 40, 40);
		pnTitle.add(btnHelp);

		JPanel panel_Chucnang = new JPanel();
		panel_Chucnang.setBounds(0, 45, 1620, 70);
		panel_Chucnang.setLayout(null);
		panel_Chucnang.setBackground(Color.WHITE);
		add(panel_Chucnang);

		btnPhanCong = new JButton("Phân công");
		btnPhanCong.setFocusable(false);
		btnPhanCong.setBounds(10, 11, 250, 50);
		panel_Chucnang.add(btnPhanCong);
		btnPhanCong.setForeground(SystemColor.controlText);
		btnPhanCong.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnPhanCong.setBackground(new Color(255, 204, 102));

		btnThemCV = new JButton("Tạo công việc");
		btnThemCV.setIcon(new ImageIcon("icons/plus32.png"));
		btnThemCV.setBackground(new Color(255, 204, 102));
		btnThemCV.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnThemCV.setBounds(270, 11, 250, 50);
		panel_Chucnang.add(btnThemCV);

		cbboxTim = new JComboBox<String>();
		cbboxTim.setFont(new Font("Tahoma", Font.PLAIN, 26));
		cbboxTim.setBounds(530, 15, 200, 45);
		cbboxTim.addItem("--Tiến Độ--");
		cbboxTim.addItem("Đang thực hiện");
		cbboxTim.addItem("Đã hoàn thành");
		cbboxTim.addItem("Tất cả");
		panel_Chucnang.add(cbboxTim);
		cbboxTim.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) cbboxTim.getSelectedItem(); // lấy thông tin item được chọn 
				switch (s) {
				case "Đang thực hiện":
					try {
						tableModel.setRowCount(0);
						dao_Congtrinh.loadData("select * from CongTrinh where TienDo = N'Đang thực hiện'", tableModel);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					break;
				case "Đã hoàn thành":
					try {
						tableModel.setRowCount(0);
						dao_Congtrinh.loadData("select * from CongTrinh where TienDo = N'Hoàn thành'", tableModel);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					break;
				case "Tất cả":
					try {
						tableModel.setRowCount(0);
						dao_Congtrinh.loadData("select * from CongTrinh where TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành'", tableModel);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					break;
				default:
					break;
				}
			}
		});

		cbboxLoaiCT = new JComboBox<String>();
		cbboxLoaiCT.setFont(new Font("Tahoma", Font.PLAIN, 26));
		// đổ loại công trình vào comboBox
		cbboxLoaiCT.addItem("--Loại CT--");
		try {
			ArrayList<LoaiCongTrinh> listLCT = dao_lct.getLoaiCongtrinhs();
			for (int i = 0; i < listLCT.size(); i++) {
				LoaiCongTrinh lct = listLCT.get(i);
				cbboxLoaiCT.addItem(lct.getTenLoai());
			}
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		cbboxLoaiCT.setBounds(740, 15, 200, 45);
		panel_Chucnang.add(cbboxLoaiCT);
		cbboxLoaiCT.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) cbboxLoaiCT.getSelectedItem(); // lấy thông tin item được chọn
				try {
					tableModel.setRowCount(0);
					dao_Congtrinh.loadData(
							"select IDCongTrinh, TenCongTrinh, DiaDiem, TienDo from CongTrinh as ct join LoaiCongTrinh as lct on ct.IDLoaiCT=lct.IDLoaiCT where TenLoaiCongTrinh = N'"
									+ s + "'  and (TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành')",
							tableModel);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		txtTimkiemCT = new JTextField();
		txtTimkiemCT.setBounds(1000, 14, 480, 45);
		panel_Chucnang.add(txtTimkiemCT);
		txtTimkiemCT.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtTimkiemCT.setColumns(10);
		txtTimkiemCT.setBackground(Color.WHITE);

		btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setBounds(1490, 11, 120, 45);
		panel_Chucnang.add(btnTimKiem);
		btnTimKiem.setFocusable(false);
		btnTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTimKiem.setBackground(new Color(255, 204, 102));

		JLayeredPane panel_XuLy = new JLayeredPane();
		panel_XuLy.setBounds(0, 115, 1620, 930);
		add(panel_XuLy);
		panel_XuLy.setLayout(null);

		JPanel panel_Table = new JPanel();
		panel_Table.setBounds(0, 0, 1620, 912);
		panel_Table.setBackground(new Color(255, 239, 213));
		panel_XuLy.add(panel_Table, new Integer(0), 0);
		panel_Table.setLayout(null);

		JLabel lblDsCongTrinh = new JLabel("Danh sách công trình (Chọn 1 công trình để thực hiện các thao tác):");
		lblDsCongTrinh.setBounds(20, 10, 800, 35);
		panel_Table.add(lblDsCongTrinh);
		lblDsCongTrinh.setFont(new Font("Tahoma", Font.PLAIN, 26));

		BufferedImage image;
		try {
			image = ImageIO.read(new File("icons/reload.png"));
			ImageIcon reloadIcon = new ImageIcon(image.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
			btnReload = new JButton("Làm mới");
			btnReload.setBounds(1480, 5, 120, 40);
			panel_Table.add(btnReload);
			btnReload.setIcon(reloadIcon);
			btnReload.setFocusable(false);
			btnReload.setBorderPainted(false);
			btnReload.setBorder(null);
			btnReload.setBackground(new Color(255, 204, 102));
			btnReload.setFont(new Font("Tahoma", Font.PLAIN, 20));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 1600, 840);
		String[] header = { "Mã CT", "Công Trình", "Địa Điểm", "Tiến Độ" };
		tableModel = new DefaultTableModel(header, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {  false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		panel_Table.add(scrollPane);
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
					c.setBackground(row % 2 == 0 ? getBackground() :  new Color(218, 223, 225));
				return c;
			}

			public boolean getScrollableTracksViewportWidth() {
				return getPreferredSize().width < getParent().getWidth();
			}

			@Override
			public void doLayout() {
				TableColumn resizingColumn = null;
				TableColumnModel tcm = getColumnModel();

				if (tableHeader != null)
					resizingColumn = tableHeader.getResizingColumn();

				// Viewport size changed. May need to increase columns widths

				if (resizingColumn == null) {
					setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					super.doLayout();
				}

				// Specific column resized. Reset preferred widths

				else {

					for (int i = 0; i < tcm.getColumnCount(); i++) {
						resizingColumn = table.getColumnModel().getColumn(i);
						if (i == 1) {
							resizingColumn.setPreferredWidth(700); // second column is bigger
						} else {
							resizingColumn.setPreferredWidth(resizingColumn.getWidth());
						}
					}

					// Columns don't fill the viewport, invoke default layout

					if (tcm.getTotalColumnWidth() < getParent().getWidth())
						setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					super.doLayout();
				}

				setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			}

		};
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 26));
		table.getTableHeader().setBackground(new Color(255, 204, 102));
		table.setFont(new Font("Tahoma", Font.PLAIN, 24));
		table.setRowHeight(45);
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		table.addMouseListener(this);

		// load dữ liệu
		try {
			dao_Congtrinh.loadData("select * from CongTrinh where TienDo = N'Đang thực hiện'", tableModel);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resizeCol();

		// panel chi tiết công trình
		pnChiTiet = new JPanel();
		pnChiTiet.setBounds(1620, 0, 488, 912);
//		panel_XuLy.add(pnChiTiet);
		panel_XuLy.add(pnChiTiet, new Integer(1), 0);
		pnChiTiet.setForeground(Color.BLACK);
		pnChiTiet.setLayout(null);
		pnChiTiet.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		pnChiTiet.setBackground(new Color(255, 239, 213));

		JLabel lblMaCT = new JLabel("Mã công trình:");
		lblMaCT.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblMaCT.setBounds(10, 57, 281, 30);
		pnChiTiet.add(lblMaCT);

		txtMaCT = new JTextField();
		txtMaCT.setForeground(Color.BLACK);
		txtMaCT.setFont(new Font("Tahoma", Font.PLAIN, 28));
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

		txtNgayCapPhep = new JTextField();
		txtNgayCapPhep.setForeground(Color.BLACK);
		txtNgayCapPhep.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtNgayCapPhep.setEditable(false);
		txtNgayCapPhep.setColumns(10);
		txtNgayCapPhep.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtNgayCapPhep.setBackground(SystemColor.menu);
		txtNgayCapPhep.setBounds(10, 457, 435, 35);
		pnChiTiet.add(txtNgayCapPhep);

		JLabel lblNgayKhoiCong = new JLabel("Ngày khởi công:");
		lblNgayKhoiCong.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNgayKhoiCong.setBounds(10, 503, 281, 30);
		pnChiTiet.add(lblNgayKhoiCong);

		txtNgayKhoiCong = new JTextField();
		txtNgayKhoiCong.setForeground(Color.BLACK);
		txtNgayKhoiCong.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtNgayKhoiCong.setEditable(false);
		txtNgayKhoiCong.setColumns(10);
		txtNgayKhoiCong.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtNgayKhoiCong.setBackground(SystemColor.menu);
		txtNgayKhoiCong.setBounds(10, 538, 435, 35);
		pnChiTiet.add(txtNgayKhoiCong);

		JLabel lblNgayDukienHoanthanh = new JLabel("Ngày dự kiến hoàn thành:");
		lblNgayDukienHoanthanh.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNgayDukienHoanthanh.setBounds(10, 584, 435, 30);
		pnChiTiet.add(lblNgayDukienHoanthanh);

		txtNgayDukienHoanthanh = new JTextField();
		txtNgayDukienHoanthanh.setForeground(Color.BLACK);
		txtNgayDukienHoanthanh.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtNgayDukienHoanthanh.setEditable(false);
		txtNgayDukienHoanthanh.setColumns(10);
		txtNgayDukienHoanthanh.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtNgayDukienHoanthanh.setBackground(SystemColor.menu);
		txtNgayDukienHoanthanh.setBounds(10, 619, 435, 35);
		pnChiTiet.add(txtNgayDukienHoanthanh);

		JLabel lblLoaiCongtrinh = new JLabel("Loại công trình:");
		lblLoaiCongtrinh.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblLoaiCongtrinh.setBounds(10, 665, 281, 30);
		pnChiTiet.add(lblLoaiCongtrinh);

		txtLoaiCongtrinh = new JTextField();
		txtLoaiCongtrinh.setForeground(Color.BLACK);
		txtLoaiCongtrinh.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtLoaiCongtrinh.setEditable(false);
		txtLoaiCongtrinh.setColumns(10);
		txtLoaiCongtrinh.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtLoaiCongtrinh.setBackground(SystemColor.menu);
		txtLoaiCongtrinh.setBounds(10, 700, 435, 35);
		pnChiTiet.add(txtLoaiCongtrinh);

		JLabel lblTiendo = new JLabel("Tiến độ:");
		lblTiendo.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblTiendo.setBounds(10, 746, 281, 30);
		pnChiTiet.add(lblTiendo);

		txtTiendo = new JTextField();
		txtTiendo.setForeground(Color.BLACK);
		txtTiendo.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtTiendo.setEditable(false);
		txtTiendo.setColumns(10);
		txtTiendo.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtTiendo.setBackground(SystemColor.menu);
		txtTiendo.setBounds(10, 787, 435, 35);
		pnChiTiet.add(txtTiendo);

		JButton btnBack = new JButton("Đóng");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnBack.setFocusable(false);
		btnBack.setBorderPainted(false);
		btnBack.setBorder(null);
		btnBack.setIcon(new ImageIcon("icons/redo.png"));
		btnBack.setForeground(new Color(0, 128, 0));
		btnBack.setBackground(new Color(255, 239, 213));
		btnBack.setBounds(5, 10, 130, 40);
		pnChiTiet.add(btnBack);
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// animate panel
//				Rectangle from = new Rectangle(1620, 0, 488, 912);
//				Rectangle to = new Rectangle(1150, 0, 488, 912);
//				1150, 108, 488, 953
//				Animate animate = new Animate(pnChiTiet, to, from); // đóng panel chi tiết công trình
//				animate.start();
			}
		});

		btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPhanCong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHelp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReload.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThemCV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTimKiem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbboxLoaiCT.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbboxTim.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// đăng ký sự kiện
		btnReload.addActionListener(this);
		btnPhanCong.addActionListener(this);
		btnThemCV.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnHelp.addActionListener(this);
		
		txtTimkiemCT.getInputMap(JTextField.WHEN_FOCUSED)
		.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Tìm CT");
		txtTimkiemCT.getActionMap().put("Tìm CT", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				DAO_CongTrinh dao_ct = new DAO_CongTrinh();
				try {
					String timkiem = txtTimkiemCT.getText().toLowerCase(); // chuyển in hoa thành in thường hết để tìm kiếm
																			// đầy đủ
					cbboxTim.setSelectedItem("Tất cả");
					; // Tất cả
					tableModel.setRowCount(0);
					dao_ct.loadData("select *  from CongTrinh where IDCongTrinh like N'%" + timkiem + "%' and (TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành')", tableModel); // tìm
																															// theo
																															// mã
					dao_ct.loadData("select *  from CongTrinh where TenCongTrinh like N'%" + timkiem + "%' and (TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành')", tableModel); // tìm
																															// theo
																															// tên
					dao_ct.loadData("select *  from CongTrinh where DiaDiem like N'%" + timkiem + "%' and (TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành')", tableModel); // tìm
																														// theo
																														// địa
																														// điểm
					txtTimkiemCT.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnPhanCong)) {
			DAO_CongTrinh dao = new DAO_CongTrinh();
			int i = table.getSelectedRow();
			if(i >= 0) {
				try {
					if(dao.getCongTrinhByID(txtMaCT.getText()).getTienDo().equals("Hoàn thành")) {
						JOptionPane.showMessageDialog(this, "Công trình đã hoàn thành.\nKhông thể phân công thêm");
					} else {
						FormPhanCongLaoDong form = new FormPhanCongLaoDong(txtMaCT.getText(), maNV);
						form.setVisible(true);
					}
				} catch (HeadlessException | SQLException e1) {
					JOptionPane.showMessageDialog(this, "Đã có lỗi phát sinh.\nVui lòng chọn một công trình dể thực hiện các thao tác");
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn một công trình để phân công");
			}
			
		}
		if (o.equals(btnTimKiem)) {
			DAO_CongTrinh dao_ct = new DAO_CongTrinh();
			try {
				String timkiem = txtTimkiemCT.getText().toLowerCase(); // chuyển in hoa thành in thường hết để tìm kiếm
																		// đầy đủ
				cbboxTim.setSelectedItem("Tất cả");
				; // Tất cả
				tableModel.setRowCount(0);
				dao_ct.loadData("select *  from CongTrinh where IDCongTrinh like N'%" + timkiem + "%' and (TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành')", tableModel); // tìm
																														// theo
																														// mã
				dao_ct.loadData("select *  from CongTrinh where TenCongTrinh like N'%" + timkiem + "%' and (TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành')", tableModel); // tìm
																														// theo
																														// tên
				dao_ct.loadData("select *  from CongTrinh where DiaDiem like N'%" + timkiem + "%' and (TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành')", tableModel); // tìm
																													// theo
																													// địa
																													// điểm
				txtTimkiemCT.setText("");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if(o.equals(btnThemCV)) {
			DAO_CongTrinh dao_Congtrinh = new DAO_CongTrinh();
			int i = table.getSelectedRow();
			if (i >= 0) {
				DAO_CongTrinh dao = new DAO_CongTrinh();
				try {
					if(dao.getCongTrinhByID(txtMaCT.getText()).getTienDo().equals("Hoàn thành")) {
						JOptionPane.showMessageDialog(this, "Công trình đã hoàn thành.\nKhông thể phân công thêm");
					} else {
						FormThemCongViec form = new FormThemCongViec(table.getValueAt(i, 0).toString(),
								maNV);
						form.setVisible(true);
						form.addWindowListener(new WindowAdapter() {
							public void windowClosed(WindowEvent e) {
								try {
									tableModel.setRowCount(0);
									dao_Congtrinh.loadData("select * from CongTrinh where TienDo = N'Đang thực hiện'", tableModel);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
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
		if (o.equals(btnReload)) {
			reloadData();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int i = table.getSelectedRow();
		DAO_CongTrinh dao_Congtrinh = new DAO_CongTrinh();
		CongTrinh congTrinh;
		try {
			congTrinh = dao_Congtrinh.getCongTrinhByName(table.getValueAt(i, 1).toString());
			txtMaCT.setText(congTrinh.getMaCT());
			txtTenCT.setText(congTrinh.getTenCT());
			txtDiadiem.setText(congTrinh.getDiaDiem());
			txtNgayCapPhep.setText(congTrinh.getNgayCapPhep().toString());
			txtNgayKhoiCong.setText(congTrinh.getNgayKhoiCong().toString());
			txtNgayDukienHoanthanh.setText(congTrinh.getNgayDuKien().toString());
			txtLoaiCongtrinh.setText(congTrinh.getLoaiCT().getTenLoai());
			txtTiendo.setText(congTrinh.getTienDo());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// animate panel
//		Rectangle from = new Rectangle(1620, 0, 488, 912);
//		Rectangle to = new Rectangle(1150, 0, 488, 912);

//		Animate animate = new Animate(pnChiTiet, from, to);
//		animate.start();
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
		DAO_CongTrinh dao_Congtrinh = new DAO_CongTrinh();
		cbboxLoaiCT.setSelectedIndex(0);
		cbboxTim.setSelectedIndex(0);
		try {
			tableModel.setRowCount(0);
			dao_Congtrinh.loadData("select * from CongTrinh where TienDo = N'Đang thực hiện'", tableModel);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void resizeCol() {
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(1).setMinWidth(595);
		table.getColumnModel().getColumn(2).setMinWidth(595);
		table.getColumnModel().getColumn(3).setMinWidth(150);
	}
}
