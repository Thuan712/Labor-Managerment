package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import connect.ConnectDB;
import entity.CongTrinh;
import entity.LoaiCongTrinh;

/**
 * 
 * @author nmthu
 *
 */
public class DAO_CongTrinh {
	DAO_LoaiCongTrinh dao_loaiCT = new DAO_LoaiCongTrinh();

	/**
	 * load danh sách Công trình vào table (Thông tin bao gồm Tên công trình, Địa
	 * điểm, Tiến độ)
	 * 
	 * @param sql
	 * @param tableModel
	 * @throws SQLException
	 */
	public void loadData(String sql, DefaultTableModel tableModel) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			vector.add(rs.getString("IDCongTrinh"));
			vector.add(rs.getString("TenCongTrinh"));
			vector.add(rs.getString("DiaDiem"));
			vector.add(rs.getString("TienDo"));
			tableModel.addRow(vector);
		}
	}

	/**
	 * load danh sach cong trinh trong gui cong viec
	 * 
	 * @param sql
	 * @param model
	 * @throws SQLException
	 */
	public void getAllCT(String sql, DefaultTableModel model) throws SQLException {
		ConnectDB.getInstance().connect();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			vector.add(rs.getString("IDCongTrinh"));
			vector.add(rs.getString("TenCongTrinh"));
			vector.add(rs.getString("TienDo"));
			model.addRow(vector);
		}
	}

	public ArrayList<CongTrinh> getDsCongTrinh() throws SQLException {
		ArrayList<CongTrinh> dsct = new ArrayList<CongTrinh>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from CongTrinh where TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành'";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String maCT = rs.getString("IDCongTrinh");
			String tenCT = rs.getString("TenCongTrinh");
			String diaDiem = rs.getString("DiaDiem");
			Date ngayCapPhep = rs.getDate("NgayCapPhep");
			Date ngayKhoiCong = rs.getDate("NgayKhoiCong");
			Date ngayDuKien = rs.getDate("NgayDuKienHoanThanh");
			String tienDo = rs.getString("TienDo");
			LoaiCongTrinh lct = dao_loaiCT.getLoaiCT(rs.getString("IDLoaiCT"));
			CongTrinh ct = null;
			ct = new CongTrinh(tenCT, maCT, diaDiem, ngayCapPhep, ngayKhoiCong, ngayDuKien, tienDo, lct);

			dsct.add(ct);
		}
		return dsct;
	}
	
	public ArrayList<CongTrinh> getDsTienDoCT(String sql) throws SQLException {
		ArrayList<CongTrinh> dsct = new ArrayList<CongTrinh>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String maCT = rs.getString("IDCongTrinh");
			String tenCT = rs.getString("TenCongTrinh");
			String diaDiem = rs.getString("DiaDiem");
			Date ngayCapPhep = rs.getDate("NgayCapPhep");
			Date ngayKhoiCong = rs.getDate("NgayKhoiCong");
			Date ngayDuKien = rs.getDate("NgayDuKienHoanThanh");
			String tienDo = rs.getString("TienDo");
			LoaiCongTrinh lct = dao_loaiCT.getLoaiCT(rs.getString("IDLoaiCT"));
			CongTrinh ct = null;
			ct = new CongTrinh(tenCT, maCT, diaDiem, ngayCapPhep, ngayKhoiCong, ngayDuKien, tienDo, lct);

			dsct.add(ct);
		}
		return dsct;
	}
	
	public CongTrinh getCongTrinhByID(String id) throws SQLException {
		CongTrinh ct = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from CongTrinh where IDCongTrinh = '" + id + "'";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String maCT = rs.getString("IDCongTrinh");
			String tenCT = rs.getString("TenCongTrinh");
			String diaDiem = rs.getString("DiaDiem");
			Date ngayCapPhep = rs.getDate("NgayCapPhep");
			Date ngayKhoiCong = rs.getDate("NgayKhoiCong");
			Date ngayDuKien = rs.getDate("NgayDuKienHoanThanh");
			String tienDo = rs.getString("TienDo");
			LoaiCongTrinh lct = dao_loaiCT.getLoaiCT(rs.getString("IDLoaiCT"));
			ct = new CongTrinh(tenCT, maCT, diaDiem, ngayCapPhep, ngayKhoiCong, ngayDuKien, tienDo, lct);

		}
		return ct;
	}

	public CongTrinh getCongTrinhByName(String ten) throws SQLException {
		CongTrinh ct = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from CongTrinh where tenCongTrinh = N'" + ten + "'";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String maCT = rs.getString("IDCongTrinh");
			String tenCT = rs.getString("TenCongTrinh");
			String diaDiem = rs.getString("DiaDiem");
			Date ngayCapPhep = rs.getDate("NgayCapPhep");
			Date ngayKhoiCong = rs.getDate("NgayKhoiCong");
			Date ngayDuKien = rs.getDate("NgayDuKienHoanThanh");

			String tienDo = rs.getString("TienDo");
			LoaiCongTrinh lct = dao_loaiCT.getLoaiCT(rs.getString("IDLoaiCT"));

			ct = new CongTrinh(tenCT, maCT, diaDiem, ngayCapPhep, ngayKhoiCong, ngayDuKien, tienDo, lct);

		}
		return ct;
	}

	public boolean themCongTrinh(CongTrinh ct) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		int n = 0;
		try {
			ps = con.prepareStatement("insert into CongTrinh VALUES(?,?,?,?,?,?,NULL,?,?)");
			ps.setString(1, ct.getMaCT());
			ps.setString(2, ct.getTenCT());
			ps.setString(3, ct.getDiaDiem());
			ps.setDate(4, ct.getNgayCapPhep());
			ps.setDate(5, ct.getNgayKhoiCong());
			ps.setDate(6, ct.getNgayDuKien());
			LoaiCongTrinh lct = dao_loaiCT.getLoaiCT(ct.getLoaiCT().getMaLoai());
			ps.setString(7, ct.getTienDo());
			ps.setString(8, lct.getMaLoai());
			n = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return n > 0;
	}

	public boolean xoaCT(String maCT) throws SQLException {

		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("update CongTrinh set TienDo = N'Đã hủy' where IDCongTrinh=?");
			ps.setString(1, maCT);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	public boolean suaTTCT(CongTrinh ct) throws SQLException {

		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		int n = 0;
		try {
			ps = con.prepareStatement(
					"update CongTrinh set TenCongTrinh=?, DiaDiem=?, NgayCapPhep=?, NgayKhoiCong=?, NgayDuKienHoanThanh=?,TienDo=?,idloaict=? where IDCongTrinh=?");

			ps.setString(1, ct.getTenCT());
			ps.setString(2, ct.getDiaDiem());
			ps.setDate(3, ct.getNgayCapPhep());
			ps.setDate(4, ct.getNgayKhoiCong());
			ps.setDate(5, ct.getNgayDuKien());
			LoaiCongTrinh lct = dao_loaiCT.getLoaiCT(ct.getLoaiCT().getMaLoai());

			ps.setString(6, ct.getTienDo());
			ps.setString(7, lct.getMaLoai());
			ps.setString(8, ct.getMaCT());

			n = ps.executeUpdate();
			if (n > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return n > 0;

	}

	public DefaultTableModel timKiemMa(String maCT) throws SQLException {
		String[] header = { "Mã Công trình", "Tên công trình", "Địa Điểm", "Ngày cấp phép", "Ngày khởi công",
				"Ngày dự kiến hoàn thành", "Loại Công Trình", "Tiến độ" };
		DefaultTableModel tableModel = new DefaultTableModel(header, 0);
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from CongTrinh where IDCongTrinh like'" + maCT + "' and (TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành')";
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			Object[] o = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8) };
			tableModel.addRow(o);
		}
		return tableModel;
	}

	public DefaultTableModel timKiemTen(String ten) throws SQLException {
		String[] header = { "Mã Công trình", "Tên công trình", "Địa Điểm", "Ngày cấp phép", "Ngày khởi công",
				"Ngày dự kiến hoàn thành", "Loại Công Trình", "Tiến độ" };
		DefaultTableModel tableModel = new DefaultTableModel(header, 0);
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from CongTrinh where Tencongtrinh like N'" + ten + "' and (TienDo = N'Đang Thực Hiện' or TienDo = N'Hoàn Thành')";
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			Object[] o = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8) };
			tableModel.addRow(o);
		}
		return tableModel;
	}

}
