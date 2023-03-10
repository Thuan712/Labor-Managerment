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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import dao.DAO_CongNhan;
import dao.DAO_TrinhDo;
import entity.TrinhDo;

/**
 * 
 * @author nmthu
 *
 */
public class FormQLCongNhan extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private JPanel pnTitle;
	private JTextField txtTim;
	private JButton btnThem;
	private JButton btnCapNhat;
	private DAO_CongNhan daoCN = new DAO_CongNhan();
	private DAO_TrinhDo daoTD = new DAO_TrinhDo();
	private JTextField txtMaCN;
	private JTextField txtTenCN;
	private JTextField txtGioiTinh;
	private JTextField txtNgaySinh;
	private JTextField txtCMND;
	private JTextField txtEmail;
	private JLabel txtDiaChi;
	private JTextField txtTrangThai;
	private JTextField txtTrinhDo;
	private JTextField txtPhone;
	private JScrollPane scrollPane;
	private JComboBox<String> cbTrangThai;
	private JComboBox<String> cbtrinhdo;
	private JComboBox<String> cbTim;
	private JButton btnOff;
	private JButton btnOn;
	private JButton btnXoa;
	private JButton btnTim;
	private JButton btnTaiLai;
	private JLabel lblCount;
	private JPanel pnChiTietTable;
	private JButton btnHelp;
	private JPanel pnMain;
	private int count;

	/**
	 * Create the panel.
	 */

	@SuppressWarnings("serial")
	public FormQLCongNhan() {

		// panel l???n
		setBounds(0, 0, 1620, 1019);
		setLayout(null);
		pnMain = new JPanel();
		pnMain.setBackground(new Color(255, 255, 255));
		pnMain.setBounds(0, 0, 1620, 1020);
		pnMain.setLayout(null);
		add(pnMain);

		// panel table
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(0, 165, 1130, 820);
		pnMain.add(scrollPane);

		// panel ti??u ?????
		pnTitle = new JPanel();
		pnTitle.setBorder(null);
		pnTitle.setBackground(new Color(255, 204, 102));
		pnTitle.setBounds(0, 0, 1620, 45);
		pnMain.add(pnTitle);
		pnTitle.setLayout(null);

		JLabel lblTitle = new JLabel("Qu???n L?? C??ng Nh??n\r\n");
		lblTitle.setForeground(new Color(0, 0, 51));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTitle.setBounds(10, 2, 400, 40);
		pnTitle.add(lblTitle);

		btnHelp = new JButton("");
		btnHelp.setIcon(new ImageIcon("icons/infor.png"));
		btnHelp.setBorder(null);
		btnHelp.setBorderPainted(false);
		btnHelp.setBackground(new Color(255, 204, 102));
		btnHelp.setForeground(new Color(0, 0, 0));
		btnHelp.setBounds(1575, 2, 40, 40);
		pnTitle.add(btnHelp);

		// table
		table = new JTable() {
			public boolean getScrollableTracksViewportWidth() {
				return getPreferredSize().width < getParent().getWidth();
			}

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
		table.getTableHeader().setBackground(new Color(255, 208, 120));
		;
		String[] header = { "M?? CN", "H??? v?? t??n", "Gi???i t??nh", "Ng??y sinh", "CMND", "Tr???ng th??i", "Tr??nh ?????" };
		table.setModel(model = new DefaultTableModel(header, 0) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		// T???i danh s??ch
		loadDSCongNhan();
		count = getRowCount();
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 22));
		table.setFont(new Font("Tahoma", Font.PLAIN, 22));
		table.setBackground(SystemColor.control);
		table.setRowHeight(45);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int i = table.getSelectedRow();
					txtMaCN.setText(table.getValueAt(i, 0).toString());
					txtTenCN.setText(table.getValueAt(i, 1).toString());
					txtGioiTinh.setText(table.getValueAt(i, 2).toString());
					txtNgaySinh.setText(table.getValueAt(i, 3).toString());
					txtPhone.setText(daoCN.getCongNhanByID(txtMaCN.getText()).getPhone());
					txtCMND.setText(table.getValueAt(i, 4).toString());
					txtEmail.setText(daoCN.getCongNhanByID(txtMaCN.getText()).getEmail());
					txtDiaChi.setText("<html>" + daoCN.getCongNhanByID(txtMaCN.getText()).getDiaChi()
							.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
					txtTrangThai.setText(table.getValueAt(i, 5).toString());
					txtTrinhDo.setText(table.getValueAt(i, 6).toString());
					super.mouseClicked(e);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(table);

		pnChiTietTable = new JPanel();
		pnChiTietTable.setBounds(0, 985, 1130, 35);
		pnChiTietTable.setBackground(new Color(255, 239, 213));
		pnMain.add(pnChiTietTable);
		pnChiTietTable.setLayout(null);

		lblCount = new JLabel("");
		lblCount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCount.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblCount.setBounds(609, 0, 521, 30);
		lblCount.setText(Integer.toString(count) + " c??ng nh??n trong c??ng ty");
		pnChiTietTable.add(lblCount);

		// Panel ch???c n??ng qu???n l??
		JPanel pnChucNang = new JPanel();
		pnChucNang.setBackground(Color.WHITE);
		pnChucNang.setBounds(0, 45, 1620, 70);
		pnChucNang.setLayout(null);
		pnMain.add(pnChucNang);

		btnThem = new JButton("Th??m");
		btnThem.setForeground(SystemColor.controlText);
		btnThem.setBackground(new Color(255, 204, 102));
		btnThem.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnThem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThem.setBounds(10, 11, 200, 50);
		btnThem.setIcon(new ImageIcon("icons/plus32.png"));
		btnThem.setFocusable(false);
		pnChucNang.add(btnThem);

		btnCapNhat = new JButton("C???p nh???t");
		btnCapNhat.setForeground(SystemColor.controlText);
		btnCapNhat.setBackground(new Color(255, 204, 102));
		btnCapNhat.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnCapNhat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCapNhat.setBounds(220, 11, 200, 50);
		btnCapNhat.setIcon(new ImageIcon("icons/edit.png"));
		btnCapNhat.setFocusable(false);
		pnChucNang.add(btnCapNhat);

		btnXoa = new JButton("X??a");
		btnXoa.setForeground(SystemColor.controlText);
		btnXoa.setBackground(new Color(255, 204, 102));
		btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnXoa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnXoa.setBounds(430, 11, 200, 50);
		btnXoa.setIcon(new ImageIcon("icons/remove.png"));
		btnXoa.setFocusable(false);
		pnChucNang.add(btnXoa);

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
		btnOff.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOff.setBounds(640, 11, 60, 50);
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
		btnOn.setBounds(710, 11, 60, 50);
		btnOn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pnChucNang.add(btnOn);

		txtTim = new JTextField();
		txtTim.setBounds(1129, 17, 365, 38);
		txtTim.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTim.setColumns(10);
		pnChucNang.add(txtTim);

		btnTim = new JButton("T??m");
		btnTim.setIcon(new ImageIcon("icons/loupe.png"));
		btnTim.setFont(new Font("Arial", Font.PLAIN, 28));
		btnTim.setBackground(new Color(255, 204, 102));
		btnTim.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTim.setFocusable(false);
		btnTim.setBounds(1496, 16, 119, 40);
		pnChucNang.add(btnTim);

		cbTim = new JComboBox<String>();
		cbTim.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbTim.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbTim.addItem("Theo m??");
		cbTim.addItem("Theo t??n");
		cbTim.setBounds(952, 17, 173, 38);
		pnChucNang.add(cbTim);

		JPanel pnChiTiet = new JPanel();
		pnChiTiet.setBackground(new Color(255, 239, 213));
		pnChiTiet.setBounds(1130, 115, 490, 905);
		pnChiTiet.setLayout(null);
		pnMain.add(pnChiTiet);

		JLabel lblMaCN = new JLabel("M?? c??ng nh??n:");
		lblMaCN.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblMaCN.setBounds(10, 60, 282, 25);
		pnChiTiet.add(lblMaCN);

		txtMaCN = new JTextField();
		txtMaCN.setEditable(false);
		txtMaCN.setBackground(SystemColor.control);
		txtMaCN.setForeground(Color.BLACK);
		txtMaCN.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtMaCN.setBounds(10, 93, 470, 30);
		txtMaCN.setColumns(10);
		txtMaCN.setBorder(BorderFactory.createEmptyBorder());
		pnChiTiet.add(txtMaCN);

		JLabel lblTenCN = new JLabel("H??? v?? t??n:");
		lblTenCN.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTenCN.setBounds(10, 129, 282, 25);
		pnChiTiet.add(lblTenCN);

		txtTenCN = new JTextField();
		txtTenCN.setEditable(false);
		txtTenCN.setBackground(SystemColor.control);
		txtTenCN.setForeground(Color.BLACK);
		txtTenCN.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTenCN.setColumns(10);
		txtTenCN.setBounds(10, 165, 470, 30);
		txtTenCN.setBorder(BorderFactory.createEmptyBorder());
		pnChiTiet.add(txtTenCN);

		JLabel lblGioiTinh = new JLabel("Gi???i t??nh:");
		lblGioiTinh.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblGioiTinh.setBounds(10, 201, 184, 30);
		pnChiTiet.add(lblGioiTinh);

		txtGioiTinh = new JTextField();
		txtGioiTinh.setEditable(false);
		txtGioiTinh.setBackground(SystemColor.control);
		txtGioiTinh.setForeground(Color.BLACK);
		txtGioiTinh.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtGioiTinh.setColumns(10);
		txtGioiTinh.setBounds(10, 237, 223, 30);
		txtGioiTinh.setBorder(BorderFactory.createEmptyBorder());
		pnChiTiet.add(txtGioiTinh);

		JLabel lblNgaySinh = new JLabel("Ng??y sinh:");
		lblNgaySinh.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNgaySinh.setBounds(254, 201, 150, 30);
		pnChiTiet.add(lblNgaySinh);

		txtNgaySinh = new JTextField();
		txtNgaySinh.setEditable(false);
		txtNgaySinh.setBackground(SystemColor.control);
		txtNgaySinh.setForeground(Color.BLACK);
		txtNgaySinh.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtNgaySinh.setColumns(10);
		txtNgaySinh.setBounds(254, 237, 226, 30);
		txtNgaySinh.setBorder(BorderFactory.createEmptyBorder());
		pnChiTiet.add(txtNgaySinh);

		JLabel lblCMND = new JLabel("CMND:");
		lblCMND.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCMND.setBounds(10, 275, 282, 25);
		pnChiTiet.add(lblCMND);

		txtCMND = new JTextField();
		txtCMND.setEditable(false);
		txtCMND.setBackground(SystemColor.control);
		txtCMND.setForeground(Color.BLACK);
		txtCMND.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtCMND.setColumns(10);
		txtCMND.setBounds(10, 311, 470, 30);
		txtCMND.setBorder(BorderFactory.createEmptyBorder());
		pnChiTiet.add(txtCMND);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblEmail.setBounds(12, 419, 280, 25);
		pnChiTiet.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setEditable(false);
		txtEmail.setBackground(SystemColor.control);
		txtEmail.setForeground(Color.BLACK);
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtEmail.setColumns(10);
		txtEmail.setBounds(10, 455, 470, 30);
		txtEmail.setBorder(BorderFactory.createEmptyBorder());
		pnChiTiet.add(txtEmail);

		JLabel lblDiaChi = new JLabel("?????a ch???:");
		lblDiaChi.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblDiaChi.setBounds(10, 491, 282, 25);
		pnChiTiet.add(lblDiaChi);

		txtDiaChi = new JLabel("");
		txtDiaChi.setHorizontalAlignment(SwingConstants.LEFT);
		txtDiaChi.setBackground(SystemColor.control);
		txtDiaChi.setForeground(Color.BLACK);
		txtDiaChi.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtDiaChi.setBounds(10, 527, 470, 60);
		txtDiaChi.setBorder(BorderFactory.createEmptyBorder());
		pnChiTiet.add(txtDiaChi);

		JLabel lblTrangThai = new JLabel("Tr???ng th??i:");
		lblTrangThai.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTrangThai.setBounds(10, 593, 282, 25);
		pnChiTiet.add(lblTrangThai);

		txtTrangThai = new JTextField();
		txtTrangThai.setEditable(false);
		txtTrangThai.setBackground(SystemColor.control);
		txtTrangThai.setForeground(Color.BLACK);
		txtTrangThai.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTrangThai.setColumns(10);
		txtTrangThai.setBounds(10, 629, 470, 30);
		txtTrangThai.setBorder(BorderFactory.createEmptyBorder());
		pnChiTiet.add(txtTrangThai);

		JLabel lblTrinhDo = new JLabel("Tr??nh ?????:");
		lblTrinhDo.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTrinhDo.setBounds(10, 665, 282, 25);
		pnChiTiet.add(lblTrinhDo);

		txtTrinhDo = new JTextField();
		txtTrinhDo.setEditable(false);
		txtTrinhDo.setBackground(SystemColor.control);
		txtTrinhDo.setForeground(Color.BLACK);
		txtTrinhDo.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtTrinhDo.setColumns(10);
		txtTrinhDo.setBounds(10, 701, 470, 30);
		txtTrinhDo.setBorder(BorderFactory.createEmptyBorder());
		pnChiTiet.add(txtTrinhDo);

		JLabel lblPhone = new JLabel("S??? ??i???n tho???i:");
		lblPhone.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblPhone.setBounds(10, 347, 282, 25);
		pnChiTiet.add(lblPhone);

		txtPhone = new JTextField();
		txtPhone.setForeground(Color.BLACK);
		txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtPhone.setEditable(false);
		txtPhone.setColumns(10);
		txtPhone.setBorder(BorderFactory.createEmptyBorder());
		txtPhone.setBackground(SystemColor.menu);
		txtPhone.setBounds(10, 383, 470, 30);
		pnChiTiet.add(txtPhone);

		JLabel lblThongTinCN = new JLabel("TH??NG TIN C??NG NH??N");
		lblThongTinCN.setFont(new Font("Arial", Font.BOLD, 32));
		lblThongTinCN.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinCN.setBounds(0, 0, 500, 50);
		pnChiTiet.add(lblThongTinCN);

		JPanel pnDiaChi = new JPanel();
		pnDiaChi.setBounds(10, 527, 470, 60);
		pnChiTiet.add(pnDiaChi);

		btnTaiLai = new JButton("T???i l???i");
		btnTaiLai.setBounds(1010, 123, 116, 35);
		btnTaiLai.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

		cbTrangThai = new JComboBox<String>();
		cbTrangThai.setBounds(550, 120, 180, 40);
		cbTrangThai.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbTrangThai.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbTrangThai.addItem("--Tr???ng Th??i--");
		cbTrangThai.addItem("T???t c???");
		cbTrangThai.addItem("??ang L??m");
		cbTrangThai.addItem("Ngh??? vi???c");
		cbTrangThai.setSelectedItem("--Tr???ng th??i--");

		pnMain.add(cbTrangThai);

		cbTrangThai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) cbTrangThai.getSelectedItem();
				switch (s) {
				case "--Tr???ng Th??i--":
					loadDSCongNhan();
					count = getRowCount();
					lblCount.setText(Integer.toString(count) + " c??ng nh??n trong c??ng ty");
					break;
				case "T???t c???":
					loadDSCongNhan();
					count = getRowCount();
					lblCount.setText(Integer.toString(count) + " c??ng nh??n trong c??ng ty");
					break;
				case "??ang L??m":
					model.setRowCount(0);
					try {
						daoCN.loadData("select * from CongNhan where trangthai =N'??ang L??m'", model);
						count = getRowCount();
						lblCount.setText(Integer.toString(count) + " c??ng nh??n ??ang l??m vi???c");

					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					break;
				case "Ngh??? vi???c":
					model.setRowCount(0);
					try {
						daoCN.loadData("select * from CongNhan where trangthai =N'Ngh??? vi???c'", model);
						count = getRowCount();
						lblCount.setText(Integer.toString(count) + " c??ng nh??n ??ang ngh??? vi???c");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				default:
					break;
				}

			}
		});

		cbtrinhdo = new JComboBox<String>();
		cbtrinhdo.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cbtrinhdo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbtrinhdo.setBounds(740, 120, 250, 40);
		cbtrinhdo.addItem("--Tr??nh ?????--");
		cbtrinhdo.addItem("T???t c???");
		cbtrinhdo.setSelectedItem("--Tr??nh ?????--");
		ArrayList<TrinhDo> listTD = daoTD.getDsTrinhDo();
		for (TrinhDo trinhDo : listTD) {
			cbtrinhdo.addItem(trinhDo.getTenTrinhDo());

		}
		pnMain.add(cbtrinhdo);

		JLabel lblDSCN = new JLabel("Danh s??ch c??ng nh??n");
		lblDSCN.setHorizontalAlignment(SwingConstants.CENTER);
		lblDSCN.setFont(new Font("Arial", Font.BOLD, 32));
		lblDSCN.setBounds(0, 121, 350, 40);
		pnMain.add(lblDSCN);

		JPanel panel = new JPanel();
		panel.setBounds(0, 115, 1130, 52);
		panel.setBackground(new Color(255, 239, 213));
		pnMain.add(panel);

		cbtrinhdo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = cbtrinhdo.getSelectedIndex();
				if (i == 0 || i == 1) {
					loadDSCongNhan();
					lblCount.setText(Integer.toString(count) + " c??ng nh??n trong c??ng ty");
				} else {
					String s = (String) cbtrinhdo.getSelectedItem();
					model.setRowCount(0);
					try {
						daoCN.loadData("select * from CongNhan where trinhdo =N'" + s + "'", model);
						count = getRowCount();
						lblCount.setText(Integer.toString(count) + " c??ng nh??n c?? tr??nh ????? " + s);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		// ????ng k?? actionlistener
		btnXoa.addActionListener(this);
		btnThem.addActionListener(this);
		btnCapNhat.addActionListener(this);
		btnTim.addActionListener(this);
		btnTaiLai.addActionListener(this);
		btnOff.addActionListener(this);
		btnOn.addActionListener(this);
	}

	/**
	 * S??? ki???n
	 */
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			new FormThemCongNhan().setVisible(true);
		}
		if (o.equals(btnCapNhat)) {
			int i = table.getSelectedRow();
			if (i != -1) {
				String iDTrinhDo = daoTD.getTrinhDoByName(txtTrinhDo.getText().trim()).getiDTrinhDo();
				FormCapNhatCongNhan frmCapNhat = new FormCapNhatCongNhan(txtMaCN.getText(), iDTrinhDo);
				frmCapNhat.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Vui l??ng ch???n c??ng nh??n c???n c???p nh???t th??ng tin");
			}
		}
		if (o.equals(btnTaiLai)) {
			cbTrangThai.setSelectedItem("--Tr???ng th??i--");
			cbtrinhdo.setSelectedItem("--Tr??nh ?????--");
			clear();
			loadDSCongNhan();
		}
		if (o.equals(btnTim)) {
			String timkiem = txtTim.getText().toLowerCase().trim();
			String s = (String) cbTim.getSelectedItem();

			if (s.equalsIgnoreCase("Theo m??")) {
				if (!timkiem.equals("")) {
					model.setRowCount(0);
					try {
						daoCN.loadData("select * from CongNhan where IDCongNhan like N'%" + timkiem + "%'", model);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					txtTim.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "M?? CN g???m CNxxxx! \n T??m ki???m g???n ????ng", "L???i",
							JOptionPane.ERROR_MESSAGE);
					txtTim.requestFocus();
					return;
				}
			}
			if (s.equalsIgnoreCase("Theo t??n")) {
				if (!timkiem.equals("")) {
					model.setRowCount(0);
					try {
						daoCN.loadData("select * from CongNhan where TenCongNhan like N'%" + timkiem + "%'", model);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					txtTim.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "Vui l??ng nh???p t??n c??ng nh??n! \n T??m ki???m g???n ????ng", "L???i",
							JOptionPane.ERROR_MESSAGE);
					txtTim.requestFocus();
					return;
				}
			}
		}
		if (o.equals(btnXoa)) {
			int row = table.getSelectedRow();
			if (row != -1) {
				try {
					if (!(daoCN.checkCNCoCongTrinh(txtMaCN.getText()) == null)) {
						int xacnhan1 = JOptionPane.showConfirmDialog(this,
								"C??ng nh??n n??y n???m trong c??ng vi???c\nB???n c?? ch???c mu???n x??a kh??ng?", "Ch?? ??",
								JOptionPane.YES_NO_OPTION);
						if (xacnhan1 == JOptionPane.YES_OPTION) {
							if (daoCN.delCongNhan(txtMaCN.getText())) {
								model.removeRow(row);
								JOptionPane.showMessageDialog(this, "X??a kh??ng th??nh c??ng!");
								loadDSCongNhan();
							} 
							else 
								JOptionPane.showMessageDialog(this, "X??a c??ng nh??n " + txtMaCN.getText() + " th??nh c??ng!");
								
						}
					} else {
						int xacnhan = JOptionPane.showConfirmDialog(this, "B???n c?? ch???c mu???n x??a c??ng nh??n n??y", "Ch?? ??",
								JOptionPane.YES_NO_OPTION);
						if (xacnhan == JOptionPane.YES_OPTION) {
							if (daoCN.delCongNhan(txtMaCN.getText())) {
								model.removeRow(row);
								JOptionPane.showMessageDialog(this, "X??a kh??ng th??nh c??ng!");
								loadDSCongNhan();
							} 
							else {
								JOptionPane.showMessageDialog(this, "X??a c??ng nh??n " + txtMaCN.getText() + " th??nh c??ng!");
							}
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else
				JOptionPane.showMessageDialog(this, "Vui l??ng ch???n c??ng nh??n ????? x??a");
		}
		if (o.equals(btnOff)) {
			int xacnhan = JOptionPane.showConfirmDialog(this, "B???n x??c nh???n mu???n cho c??ng nh??n n??y ngh????", "Ch?? ??",
					JOptionPane.YES_NO_OPTION);
			if (xacnhan == JOptionPane.YES_OPTION) {
				if (daoCN.thaydoiTrangthaiCongnhan(txtMaCN.getText(), "Ngh??? vi???c")) {
					JOptionPane.showMessageDialog(this, "Nh??n vi??n n??y ???? ???????c ngh???");
					loadDSCongNhan();
				} else {
					JOptionPane.showMessageDialog(this, "???? c?? l???i x???y ra. Vui l??ng th??? l???i");
				}
			}
		}
		if (o.equals(btnOn)) {
			int xacnhan = JOptionPane.showConfirmDialog(this, "B???n x??c nh???n mu???n nh??n vi??n n??y ??i l??m l???i?", "Ch?? ??",
					JOptionPane.YES_NO_OPTION);
			if (xacnhan == JOptionPane.YES_OPTION) {
				if (daoCN.thaydoiTrangthaiCongnhan(txtMaCN.getText(), "??ang l??m")) {
					JOptionPane.showMessageDialog(this, "Nh??n vi??n n??y ???? chuy???n sang ??ang l??m");
					loadDSCongNhan();
				} else {
					JOptionPane.showMessageDialog(this, "???? c?? l???i x???y ra. Vui l??ng th??? l???i");
				}
			}
		}
	}

	/**
	 * x??a tr???ng
	 */
	private void clear() {
		txtMaCN.setText("");
		txtTenCN.setText("");
		txtCMND.setText("");
		txtEmail.setText("");
		txtPhone.setText("");
		txtTrinhDo.setText("");
		txtTrangThai.setText("");
		txtNgaySinh.setText("");
		txtGioiTinh.setText("");
		txtDiaChi.setText("");
	}

	/**
	 * size c???t
	 */
	private void resizeCol() {
		table.getColumnModel().getColumn(0).setMinWidth(70);
		table.getColumnModel().getColumn(1).setMinWidth(230);
		table.getColumnModel().getColumn(2).setMinWidth(50);
		table.getColumnModel().getColumn(3).setMinWidth(50);
		table.getColumnModel().getColumn(4).setMinWidth(70);
		table.getColumnModel().getColumn(5).setMinWidth(80);
		table.getColumnModel().getColumn(6).setMinWidth(150);
	}

	/**
	 * t???i danh s??ch
	 */
	private void loadDSCongNhan() {
		model.setRowCount(0);
		try {
			daoCN.loadData("select * from CongNhan", model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resizeCol();
	}

	/**
	 * @S??? d??ng trong danh s??ch
	 */
	private int getRowCount() {
		return model.getRowCount();
	}
}
