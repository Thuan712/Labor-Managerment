package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import connect.ConnectDB;
import dao.DAO_BangPhanCong;
//import dao.DAO_Chuyenmon;
import dao.DAO_CongNhan;
import dao.DAO_CongTrinh;
import dao.DAO_CongViec;
import entity.BangPhanCong;
import entity.CongNhan;
import entity.CongTrinh;
import entity.CongViec;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
/**
 * 
 * @author nmthu
 *
 */
public class FormPhanCongLaoDong extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtTimCN;
	private JTable table_CongnhanTrongCT;
	private JPanel contentPane;
	private JTextField txtTimCV;
	private JTable table_CV;
	private DefaultTableModel tablemodel_CV;
	private JTable table_CongNhanNgoaiCT;
	private DefaultTableModel tableModel_CongNhanNgoaiCT;
	private DefaultTableModel tableModel_CongnhanTrongCT;
	private JComboBox<String> cboTrangthaiCN;
	private JComboBox<String> cboTrinhdo;
	private JButton btnTimCN;
	private JButton btnTimCV;
	private JButton btnThemVaoCongviec;
	private JButton btnChuyenKhoiCongviec;
	private CongTrinh ct;
	private JPanel pnTitle;
	private JLabel lblMaCT;
	private JComboBox<String> cbCongtrinh;
	private JLabel lblTimCN;
	private JButton btnThemCV;
	private String manv;
	private JScrollPane scrollPane;
	private JTextArea taTenCT;

	/**
	 * Create the frame.
	 */
	public FormPhanCongLaoDong(String id, String idNhanvien) {
		setTitle("Phân công lao động");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 0, 1615, 1040);

		try {
			setIconImage(ImageIO.read(new File("icons/iconFrameW.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		DAO_BangPhanCong dao_Phancong = new DAO_BangPhanCong();
		DAO_CongViec dao_Congviec = new DAO_CongViec();
		DAO_CongTrinh dao_Congtrinh = new DAO_CongTrinh();
		DAO_CongNhan dao_Congnhan = new DAO_CongNhan();
		manv = idNhanvien;
		try {
			ct = dao_Congtrinh.getCongTrinhByID(id);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 239, 213));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		pnTitle = new JPanel();
		pnTitle.setLayout(null);
		pnTitle.setBorder(null);
		pnTitle.setBackground(new Color(255, 204, 102));
		pnTitle.setBounds(0, 0, 1599, 45);
		contentPane.add(pnTitle);

		JLabel lblTitle = new JLabel("Phân Công Nhân Viên Vào Công Trình");
		lblTitle.setForeground(new Color(0, 0, 51));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblTitle.setBounds(10, 5, 600, 35);
		pnTitle.add(lblTitle);

		JPanel panel_ThongtinCT = new JPanel();
		panel_ThongtinCT.setBounds(5, 50, 740, 165);
		panel_ThongtinCT.setLayout(null);
		panel_ThongtinCT.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Chọn công trình",

				TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 22), null));
		panel_ThongtinCT.setBackground(Color.WHITE);
		contentPane.add(panel_ThongtinCT);

		lblMaCT = new JLabel("Mã công trình :");
		lblMaCT.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblMaCT.setBounds(10, 32, 190, 30);
		panel_ThongtinCT.add(lblMaCT);

		List<CongTrinh> list = new ArrayList<CongTrinh>();
		try {
			list = dao_Congtrinh.getDsCongTrinh();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		cbCongtrinh = new JComboBox<String>();
		cbCongtrinh.setFont(new Font("Tahoma", Font.PLAIN, 24));
		for (CongTrinh congTrinh : list) {
			cbCongtrinh.addItem(congTrinh.getMaCT());
		}
		cbCongtrinh.setSelectedItem(ct.getMaCT());
		cbCongtrinh.setBounds(210, 32, 510, 30);
		panel_ThongtinCT.add(cbCongtrinh);
		cbCongtrinh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String mact = (String) cbCongtrinh.getSelectedItem();
				try {
					CongTrinh ct = dao_Congtrinh.getCongTrinhByID(mact);
					taTenCT.setText(ct.getTenCT());
					tablemodel_CV.setRowCount(0);
					dao_Congviec.loadDataPhancong("select * from Congviec where IDCongTrinh = '" + mact + "' and TrangThai = N'Chưa hoàn thành'",
							tablemodel_CV);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		JLabel lblTenCT = new JLabel("Tên công trình :");
		lblTenCT.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTenCT.setBounds(10, 72, 190, 30);
		panel_ThongtinCT.add(lblTenCT);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(210, 72, 510, 85);
		panel_ThongtinCT.add(scrollPane);

		taTenCT = new JTextArea();
		taTenCT.setFont(new Font("Monospaced", Font.PLAIN, 24));
		taTenCT.setEditable(false);
		taTenCT.setText(ct.getTenCT());
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(taTenCT);

		btnThemVaoCongviec = new JButton("");
		btnThemVaoCongviec.setIcon(new ImageIcon("icons/page_right2.png"));
		btnThemVaoCongviec.setBounds(752, 650, 90, 60);
		btnThemVaoCongviec.setBackground(Color.GREEN);
		contentPane.add(btnThemVaoCongviec);

		btnChuyenKhoiCongviec = new JButton("");
		btnChuyenKhoiCongviec.setIcon(new ImageIcon("icons/page_left2.png"));
		btnChuyenKhoiCongviec.setBounds(752, 720, 90, 60);
		btnChuyenKhoiCongviec.setForeground(Color.ORANGE);
		btnChuyenKhoiCongviec.setBackground(Color.RED);
		contentPane.add(btnChuyenKhoiCongviec);

		JScrollPane scrollpane_CongNhanNgoaiCT = new JScrollPane();
		scrollpane_CongNhanNgoaiCT.setBounds(5, 450, 740, 543);
		contentPane.add(scrollpane_CongNhanNgoaiCT);

		String[] header_CongNhanNgoaiCT = { "Mã CN", "Tên công nhân", "Chuyên môn", "Trình Độ", "Trạng thái" };
		tableModel_CongNhanNgoaiCT = new DefaultTableModel(header_CongNhanNgoaiCT, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table_CongNhanNgoaiCT = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8277268862619066665L;

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
		table_CongNhanNgoaiCT.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 22));
		table_CongNhanNgoaiCT.getTableHeader().setBackground(new Color(255, 208, 120));
		table_CongNhanNgoaiCT.setRowHeight(40);
		table_CongNhanNgoaiCT.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table_CongNhanNgoaiCT.setModel(tableModel_CongNhanNgoaiCT);
		try {
			dao_Congnhan.loadDataPhancong("select * from CongNhan where TrangThai = N'Đang rảnh'",
					tableModel_CongNhanNgoaiCT);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		table_CongNhanNgoaiCT.getColumnModel().getColumn(0).setMinWidth(60);
		table_CongNhanNgoaiCT.getColumnModel().getColumn(1).setMinWidth(220);
		table_CongNhanNgoaiCT.getColumnModel().getColumn(2).setMinWidth(100);
		table_CongNhanNgoaiCT.getColumnModel().getColumn(3).setMinWidth(100);
		table_CongNhanNgoaiCT.getColumnModel().getColumn(4).setMinWidth(80);
		table_CongNhanNgoaiCT.setRowSelectionAllowed(true);
		table_CongNhanNgoaiCT.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollpane_CongNhanNgoaiCT.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollpane_CongNhanNgoaiCT.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane_CongNhanNgoaiCT.setViewportView(table_CongNhanNgoaiCT);

		JScrollPane scrollPane_CongnhanTrongCT = new JScrollPane();
		scrollPane_CongnhanTrongCT.setBounds(850, 450, 754, 543);
		contentPane.add(scrollPane_CongnhanTrongCT);

		String[] header_CongnhanTrongCT = { "Mã PC", "Tên công nhân", "Chuyên môn", "Trình Độ", "Trạng thái" };
		tableModel_CongnhanTrongCT = new DefaultTableModel(header_CongnhanTrongCT, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table_CongnhanTrongCT = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8277268862619066665L;

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
		table_CongnhanTrongCT.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 22));
		table_CongnhanTrongCT.getTableHeader().setBackground(new Color(255, 208, 120));
		table_CongnhanTrongCT.setRowHeight(40);
		table_CongnhanTrongCT.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table_CongnhanTrongCT.setModel(tableModel_CongnhanTrongCT);
		table_CongnhanTrongCT.getColumnModel().getColumn(0).setMinWidth(70);
		table_CongnhanTrongCT.getColumnModel().getColumn(1).setMinWidth(240);
		table_CongnhanTrongCT.getColumnModel().getColumn(2).setMinWidth(130);
		table_CongnhanTrongCT.getColumnModel().getColumn(3).setMinWidth(100);
		table_CongnhanTrongCT.getColumnModel().getColumn(4).setMinWidth(70);
		table_CongnhanTrongCT.setRowSelectionAllowed(true);
		table_CongnhanTrongCT.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane_CongnhanTrongCT.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane_CongnhanTrongCT.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_CongnhanTrongCT.setViewportView(table_CongnhanTrongCT);

		JPanel panel_TimCN = new JPanel();
		panel_TimCN.setBounds(5, 222, 740, 218);
		panel_TimCN.setLayout(null);
		panel_TimCN.setBorder(
				new TitledBorder(new LineBorder(new Color(0, 0, 0)), "T\u00ECm ki\u1EBFm C\u00F4ng nh\u00E2n",
						TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 22), new Color(0, 0, 0)));
		panel_TimCN.setBackground(Color.WHITE);
		contentPane.add(panel_TimCN);

		JLabel lblTrinhdo = new JLabel("Trình Độ:");
		lblTrinhdo.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblTrinhdo.setBounds(10, 70, 200, 30);
		panel_TimCN.add(lblTrinhdo);

		cboTrinhdo = new JComboBox<String>();
		cboTrinhdo.addItem("Tất cả");
		cboTrinhdo.addItem("Kỹ Sư");
		cboTrinhdo.addItem("Đại Học");
		cboTrinhdo.addItem("Trung học phổ thông");
		cboTrinhdo.addItem("Cao đẳng");
		cboTrinhdo.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cboTrinhdo.setBounds(224, 70, 365, 30);
		panel_TimCN.add(cboTrinhdo);
		cboTrinhdo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) cboTrinhdo.getSelectedItem(); // lấy thông tin item được chọn
				if (s.equals("Tất cả")) {
					try {
						tableModel_CongNhanNgoaiCT.setRowCount(0);
						dao_Congnhan.loadDataPhancong(
								"select IDCongNhan, TenCongNhan, cn.ChuyenMon, td.IDTrinhDo, TrangThai \r\n"
										+ "from CongNhan as cn join TrinhDo as td on cn.IDTrinhDo = td.IDTrinhDo\r\n",
								tableModel_CongNhanNgoaiCT);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						tableModel_CongNhanNgoaiCT.setRowCount(0);
						dao_Congnhan.loadDataPhancong(
								"select IDCongNhan, TenCongNhan, cn.ChuyenMon, td.IDTrinhDo, TrangThai \r\n"
										+ "from CongNhan as cn join TrinhDo as td on cn.IDTrinhDo = td.IDTrinhDo\r\n"
										+ "where TenTrinhDo = N'" + s + "'",
								tableModel_CongNhanNgoaiCT);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		JLabel lblTrangthaiCN = new JLabel("Trạng thái:");
		lblTrangthaiCN.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblTrangthaiCN.setBounds(10, 110, 200, 30);
		panel_TimCN.add(lblTrangthaiCN);

		cboTrangthaiCN = new JComboBox<String>();
		cboTrangthaiCN.addItem("Tất cả");
		cboTrangthaiCN.addItem("Đang rảnh");
		cboTrangthaiCN.addItem("Đang Làm");
		cboTrangthaiCN.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cboTrangthaiCN.setBounds(224, 110, 365, 30);
		panel_TimCN.add(cboTrangthaiCN);
		cboTrangthaiCN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = (String) cboTrangthaiCN.getSelectedItem(); // lấy thông tin item được chọn
				if (s.equals("Tất cả")) {
					try {
						tableModel_CongNhanNgoaiCT.setRowCount(0);
						dao_Congnhan.loadDataPhancong("select * from CongNhan", tableModel_CongNhanNgoaiCT);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						tableModel_CongNhanNgoaiCT.setRowCount(0);
						dao_Congnhan.loadDataPhancong("select * from CongNhan where TrangThai = N'" + s + "'",
								tableModel_CongNhanNgoaiCT);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		txtTimCN = new JTextField();
//		txtTimCN.setPlaceholder("Nhập tên, mã CN để tìm");
		txtTimCN.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTimCN.setColumns(10);
		txtTimCN.setBounds(224, 29, 365, 35);
		panel_TimCN.add(txtTimCN);

		btnTimCN = new JButton("Tìm");
		btnTimCN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTimCN.setBackground(UIManager.getColor("Button.background"));
		btnTimCN.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnTimCN.setBounds(599, 29, 121, 30);
		panel_TimCN.add(btnTimCN);

		lblTimCN = new JLabel("Tìm công nhân:");
		lblTimCN.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblTimCN.setBounds(10, 29, 200, 30);
		panel_TimCN.add(lblTimCN);

//		lblChuyenmon = new JLabel("Chuyên môn:");
//		lblChuyenmon.setFont(new Font("Tahoma", Font.PLAIN, 28));
//		lblChuyenmon.setBounds(10, 151, 200, 30);
//		panel_TimCN.add(lblChuyenmon);
//
//		Set<String> chuyenmons = new HashSet<String>();
//		try {
//			chuyenmons = dao_cm.getChuyenmons();
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//		cbChuyenmon = new JComboBox<String>();
//		cbChuyenmon.setFont(new Font("Tahoma", Font.PLAIN, 24));
//		cbChuyenmon.addItem("Tất cả");
//		for (String string : chuyenmons) {
//			cbChuyenmon.addItem(string);
//		}
//		cbChuyenmon.setBounds(224, 151, 365, 30);
//		panel_TimCN.add(cbChuyenmon);
//		cbChuyenmon.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				String cm = (String) cbChuyenmon.getSelectedItem();
//				if (cm.equals("Tất cả")) {
//					try {
//						tableModel_CongNhanNgoaiCT.setRowCount(0);
//						dao_Congnhan.loadDataPhancong("select * from CongNhan", tableModel_CongNhanNgoaiCT);
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				} else {
//					try {
//						tableModel_CongNhanNgoaiCT.setRowCount(0);
//						dao_Congnhan.loadDataPhancong("select * from CongNhan where ChuyenMon = N'" + cm + "'",
//								tableModel_CongNhanNgoaiCT);
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				}
//			}
//		});

		JPanel panel_TimCV = new JPanel();
		panel_TimCV.setLayout(null);
		panel_TimCV.setBorder(
				new TitledBorder(new LineBorder(new Color(0, 0, 0)), "T\u00ECm ki\u1EBFm C\u00F4ng vi\u1EC7c",
						TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 22), new Color(0, 0, 0)));
		panel_TimCV.setBackground(Color.WHITE);
		panel_TimCV.setBounds(850, 50, 754, 390);
		contentPane.add(panel_TimCV);

		txtTimCV = new JTextField();
//		txtTimCV.setPlaceholder("Nhập tên, mã CV để tìm");
		txtTimCV.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTimCV.setColumns(10);
		txtTimCV.setBounds(10, 30, 444, 35);
		panel_TimCV.add(txtTimCV);

		btnTimCV = new JButton("");
		btnTimCV.setIcon(new ImageIcon("icons/loupe.png"));
		btnTimCV.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnTimCV.setBackground(UIManager.getColor("Button.background"));
		btnTimCV.setBounds(464, 30, 50, 35);
		panel_TimCV.add(btnTimCV);

		JScrollPane scrollPane_Congviec = new JScrollPane();
		scrollPane_Congviec.setBounds(2, 70, 752, 320);
		panel_TimCV.add(scrollPane_Congviec);

		String[] header_CV = { "Mã CV", "Tên công việc", "Chuyên môn", "Trạng thái" };
		tablemodel_CV = new DefaultTableModel(header_CV, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table_CV = new JTable() {
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
		table_CV.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 24));
		table_CV.getTableHeader().setBackground(new Color(255, 208, 120));
		table_CV.setFont(new Font("Tahoma", Font.PLAIN, 22));
		table_CV.setRowHeight(40);
		table_CV.setModel(tablemodel_CV);
		table_CV.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table_CV.getSelectedRow();
				try {
					tableModel_CongnhanTrongCT.setRowCount(0);
					dao_Phancong
							.loadDataPhancong("select IDPhanCong, TenCongNhan, IDTrinhDo, TrangThai \r\n"
									+ "from CongNhan as cn join BangPhanCong as bpc on cn.IDCongNhan = bpc.IDCongNhan\r\n"
									+ "where bpc.IDCongViec = '" + table_CV.getValueAt(i, 0)
									+ "' and bpc.NgayKetThucCV IS NULL", tableModel_CongnhanTrongCT);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		table_CV.getColumnModel().getColumn(0).setMinWidth(50);
		table_CV.getColumnModel().getColumn(1).setMinWidth(160);
		table_CV.getColumnModel().getColumn(2).setMinWidth(140);
		table_CV.getColumnModel().getColumn(3).setMinWidth(120);
		scrollPane_Congviec.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane_Congviec.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_Congviec.setViewportView(table_CV);

		btnThemCV = new JButton("Thêm CV");
		btnThemCV.setIcon(new ImageIcon("icons/plus32_2.png"));
		btnThemCV.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnThemCV.setBackground(UIManager.getColor("Button.background"));
		btnThemCV.setBounds(524, 30, 160, 35);
		panel_TimCV.add(btnThemCV);
		
		JButton btnReloadTableCV = new JButton("");
		btnReloadTableCV.setIcon(new ImageIcon("icons/refresh.png"));
		btnReloadTableCV.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnReloadTableCV.setBackground(UIManager.getColor("Button.background"));
		btnReloadTableCV.setBounds(694, 30, 50, 35);
		panel_TimCV.add(btnReloadTableCV);
		btnReloadTableCV.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reloadTableCV();
			}
		});
		
		try {
			String mact = (String) cbCongtrinh.getSelectedItem();
			dao_Congviec.loadDataPhancong("select * from Congviec where IDCongTrinh = '" + mact + "' and TrangThai = N'Chưa hoàn thành'", tablemodel_CV);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		btnChuyenKhoiCongviec.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThemCV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThemVaoCongviec.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTimCN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTimCV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//		cbChuyenmon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbCongtrinh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboTrangthaiCN.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboTrinhdo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// Đăng ký sự kiện
		btnTimCN.addActionListener(this);
		btnTimCV.addActionListener(this);
		btnThemVaoCongviec.addActionListener(this);
		btnChuyenKhoiCongviec.addActionListener(this);
		btnThemCV.addActionListener(this);
		
		txtTimCV.getInputMap(JTextField.WHEN_FOCUSED)
		.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Tìm CV");
		txtTimCV.getActionMap().put("Tìm CV", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				DAO_CongViec dao_cv = new DAO_CongViec();
				try {
					String timkiem = txtTimCV.getText().toLowerCase(); // chuyển in hoa thành in thường hết để tìm
																		// kiếm đầy
																		// đủ
					tablemodel_CV.setRowCount(0);
					dao_cv.loadDataPhancong("select *  from CongViec where IDCongTrinh = '" + ct.getMaCT()
							+ "' and IDCongViec like N'%" + timkiem + "%' and TrangThai = N'Chưa hoàn thành'",
							tablemodel_CV); // tìm theo mã
					dao_cv.loadDataPhancong("select *  from CongViec where IDCongTrinh = '" + ct.getMaCT()
							+ "' and TenCongViec like N'%" + timkiem + "%' and TrangThai = N'Chưa hoàn thành'",
							tablemodel_CV); // tìm theo tên
					txtTimCV.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		txtTimCN.getInputMap(JTextField.WHEN_FOCUSED)
		.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Tìm CN");
		txtTimCN.getActionMap().put("Tìm CN", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				DAO_CongNhan dao_cn = new DAO_CongNhan();
				try {
					String timkiem = txtTimCN.getText().toLowerCase(); // chuyển in hoa thành in thường hết để tìm
																		// kiếm đầy
																		// đủ
					if(timkiem.equals("")) {
						tableModel_CongNhanNgoaiCT.setRowCount(0);
						dao_cn.loadDataPhancong("select *  from CongNhan where TrangThai = N'Đang rảnh'",
								tableModel_CongNhanNgoaiCT);
					} else {
						tableModel_CongNhanNgoaiCT.setRowCount(0);
						dao_cn.loadDataPhancong("select *  from CongNhan where IDCongNhan like N'%" + timkiem + "%'",
								tableModel_CongNhanNgoaiCT); // tìm theo mã
						dao_cn.loadDataPhancong("select *  from CongNhan where TenCongNhan like N'%" + timkiem + "%'",
								tableModel_CongNhanNgoaiCT); // tìm theo tên
					}
					txtTimCN.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnTimCN)) {
			DAO_CongNhan dao_cn = new DAO_CongNhan();
			try {
				String timkiem = txtTimCN.getText().toLowerCase(); // chuyển in hoa thành in thường hết để tìm kiếm đầy
																	// đủ
				if(timkiem.equals("")) {
					tableModel_CongNhanNgoaiCT.setRowCount(0);
					dao_cn.loadDataPhancong("select *  from CongNhan where TrangThai = N'Đang rảnh'",
							tableModel_CongNhanNgoaiCT);
				} else {
					tableModel_CongNhanNgoaiCT.setRowCount(0);
					dao_cn.loadDataPhancong("select *  from CongNhan where IDCongNhan like N'%" + timkiem + "%'",
							tableModel_CongNhanNgoaiCT); // tìm theo mã
					dao_cn.loadDataPhancong("select *  from CongNhan where TenCongNhan like N'%" + timkiem + "%'",
							tableModel_CongNhanNgoaiCT); // tìm theo tên
				}
				txtTimCN.setText("");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btnTimCV)) {
			DAO_CongViec dao_cv = new DAO_CongViec();
			try {
				String timkiem = txtTimCV.getText().toLowerCase(); // chuyển in hoa thành in thường hết để tìm kiếm đầy
																	// đủ
				tablemodel_CV.setRowCount(0);
				dao_cv.loadDataPhancong("select *  from CongViec where IDCongTrinh = '" + ct.getMaCT()
						+ "' and IDCongViec like N'%" + timkiem + "%' and TrangThai = N'Chưa hoàn thành'", tablemodel_CV); // tìm theo mã
				dao_cv.loadDataPhancong("select *  from CongViec where IDCongTrinh = '" + ct.getMaCT()
						+ "' and TenCongViec like N'%" + timkiem + "%' and TrangThai = N'Chưa hoàn thành'", tablemodel_CV); // tìm theo tên
				txtTimCV.setText("");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btnThemVaoCongviec)) {
			int[] selected = table_CongNhanNgoaiCT.getSelectedRows();
			int j = table_CV.getSelectedRow();
			DAO_CongNhan dao_Congnhan = new DAO_CongNhan();
			DAO_CongViec dao_Congviec = new DAO_CongViec();
			DAO_BangPhanCong dao_Phancong = new DAO_BangPhanCong();
			try {
				if (j >= 0) {
					if (selected.length == 0) {
						JOptionPane.showMessageDialog(null, "Vui lòng chọn công nhân để phân công");
					} else {
						for (int i = 0; i <= selected.length; i++) {
							if (i == selected.length) {
								JOptionPane.showMessageDialog(this, "Phân công nhân viên thành công");
								reloadTableCN();
								break;
							}
							CongNhan cn = dao_Congnhan
									.getCongNhanByID(table_CongNhanNgoaiCT.getValueAt(selected[i], 0).toString());
							CongViec cv = dao_Congviec.getCongviecByID(table_CV.getValueAt(j, 0).toString());
							BangPhanCong bpc = new BangPhanCong(cv, cn, Date.valueOf(LocalDate.now()));
							if (!(dao_Phancong.taoBangPhancong(bpc)
									&& dao_Congnhan.thaydoiTrangthaiCongnhan(cn.getiDCongNhan(), "Đang làm"))) {
								JOptionPane.showMessageDialog(this, "Đã có lỗi xảy ra. Vui lòng thử lại");
								break;
							}

						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một công việc trước khi phân công");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if (o.equals(btnChuyenKhoiCongviec)) {
			int[] selected = table_CongnhanTrongCT.getSelectedRows();
			int j = table_CV.getSelectedRow();
			DAO_CongNhan dao_Congnhan = new DAO_CongNhan();
			DAO_BangPhanCong dao_Phancong = new DAO_BangPhanCong();
			try {
				if (j >= 0) {
					if (selected.length == 0) {
						JOptionPane.showMessageDialog(null, "Vui lòng chọn công nhân để phân công");
					} else {
						for (int i = 0; i <= selected.length; i++) {
							if (i == selected.length) {
								JOptionPane.showMessageDialog(this, "Phân công nhân viên thành công");
								reloadTableCN();
								break;
							}
							BangPhanCong bpc = dao_Phancong
									.getBangPhancongByID(table_CongnhanTrongCT.getValueAt(selected[i], 0).toString());
							if (!(dao_Congnhan.thaydoiTrangthaiCongnhan(bpc.getCongnhan().getiDCongNhan(), "Đang rảnh")
									&& dao_Phancong.updateNgayKetThucCV(bpc.getMaPhanCong(),
											Date.valueOf(LocalDate.now())))) {
								JOptionPane.showMessageDialog(this, "Đã có lỗi xảy ra. Vui lòng thử lại");
								break;
							}

						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một công việc trước khi phân công");
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		if (o.equals(btnThemCV)) {
			String mact = (String) cbCongtrinh.getSelectedItem();
			FormThemCongViec form = new FormThemCongViec(mact, manv);
			form.setVisible(true);
			form.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
					reloadTableCV();
				}
			});
		}
	}

	public void reloadTableCV() {
		DAO_CongViec dao_Congviec = new DAO_CongViec();
		String mact = (String) cbCongtrinh.getSelectedItem();
		try {
			tablemodel_CV.setRowCount(0);
			dao_Congviec.loadDataPhancong("select * from Congviec where IDCongTrinh = '" + mact + "' and TrangThai = N'Chưa hoàn thành'", tablemodel_CV);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void reloadTableCN() {
		DAO_BangPhanCong dao_Phancong = new DAO_BangPhanCong();
		DAO_CongNhan dao_Congnhan = new DAO_CongNhan();
		int i = table_CV.getSelectedRow();
		try {
			tableModel_CongNhanNgoaiCT.setRowCount(0);
			dao_Congnhan.loadDataPhancong("select * from CongNhan where TrangThai = N'Đang rảnh'",
					tableModel_CongNhanNgoaiCT);
			tableModel_CongnhanTrongCT.setRowCount(0);
			dao_Phancong.loadDataPhancong("select IDPhanCong, TenCongNhan, IDTrinhDo, TrangThai \r\n"
					+ "from CongNhan as cn join BangPhanCong as bpc on cn.IDCongNhan = bpc.IDCongNhan\r\n"
					+ "where bpc.IDCongViec = '" + table_CV.getValueAt(i, 0) + "' and bpc.NgayKetThucCV IS NULL",
					tableModel_CongnhanTrongCT);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
