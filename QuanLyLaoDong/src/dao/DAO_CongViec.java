package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import connect.ConnectDB;
import entity.CongTrinh;
import entity.CongViec;
import entity.NhanVien;

/**
 * 
 * @author nmthu
 *
 */
public class DAO_CongViec {
	DAO_CongTrinh dao_Congtrinh = new DAO_CongTrinh();
	DAO_Nhanvien dao_Nhanvien = new DAO_Nhanvien();

	/**
	 * load dữ liệu công việc
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
			vector.add(rs.getString("IDCongViec"));
			vector.add(rs.getString("TenCongViec"));
			vector.add(rs.getDate("NgayBatDauCV"));
			vector.add(rs.getDate("NgayKetThucCV"));
			vector.add(rs.getString("TrangThai"));
			tableModel.addRow(vector);
		}
	}

	/**
	 * load dữ liệu công việc cho mục đích phân công lao động
	 * 
	 * @param sql
	 * @param tableModel
	 * @throws SQLException
	 */
	public void loadDataPhancong(String sql, DefaultTableModel tableModel) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			vector.add(rs.getString("IDCongViec"));
			vector.add(rs.getString("TenCongViec"));
			vector.add(rs.getString("TrangThai"));
			tableModel.addRow(vector);
		}
	}

	/**
	 * Tìm công việc có mã được chọn
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public CongViec getCongviecByID(String id) throws SQLException {
		CongViec cv = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from CongViec where idcongviec='" + id + "'";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String macv = rs.getString("IDCongViec");
			String tencv = rs.getString("TenCongViec");
			Date ngayBD = rs.getDate("NgayBatDauCV");
			Date ngayKT = rs.getDate("NgayKetThucCV");
			String trangthai = rs.getString("TrangThai");
			CongTrinh ct = dao_Congtrinh.getCongTrinhByID(rs.getString("IDCongTrinh"));
			NhanVien nv = dao_Nhanvien.getNhanvienByID(rs.getString("IDNhanVien"));
			cv = new CongViec(macv, tencv, ngayBD, ngayKT, trangthai, ct, nv);
		}
		return cv;
	}

	/**
	 * Tìm công việc có tên được chọn
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public CongViec getCongviecByName(String ten) throws SQLException {
		CongViec cv = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from CongViec where tencongviec='" + ten + "'";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String macv = rs.getString("IDCongViec");
			String tencv = rs.getString("TenCongViec");
			Date ngayBD = rs.getDate("NgayBatDauCV");
			Date ngayKT = rs.getDate("NgayKetThucCV");
			String trangthai = rs.getString("TrangThai");
			CongTrinh ct = dao_Congtrinh.getCongTrinhByID(rs.getString("IDCongTrinh"));
			NhanVien nv = dao_Nhanvien.getNhanvienByID(rs.getString("IDNhanVien"));
			cv = new CongViec(macv, tencv, ngayBD, ngayKT, trangthai, ct, nv);
		}
		return cv;
	}

	/**
	 * lấy danh sách công việc trong hệ thống
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<CongViec> getDsCongviec() throws SQLException {
		ArrayList<CongViec> congviecs = new ArrayList<CongViec>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from CongViec";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String macv = rs.getString("IDCongViec");
			String tencv = rs.getString("TenCongViec");
			Date ngayBD = rs.getDate("NgayBatDauCV");
			Date ngayKT = rs.getDate("NgayKetThucCV");
			String trangthai = rs.getString("TrangThai");
			CongTrinh ct = dao_Congtrinh.getCongTrinhByID(rs.getString("IDCongTrinh"));
			NhanVien nv = dao_Nhanvien.getNhanvienByID(rs.getString("IDNhanVien"));
			CongViec cv = new CongViec(macv, tencv, ngayBD, ngayKT, trangthai, ct, nv);
			congviecs.add(cv);
		}
		return congviecs;
	}

	/**
	 * Kết thúc công việc có id được chọn (gán ngày kết thúc là ngày thực hiện chức
	 * năng)
	 * 
	 * @param id
	 * @return
	 */
	public boolean hoanthanhCongviec(String id) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(
					"update CongViec set NgayKetThucCV=?, TrangThai = N'Hoàn thành' where IDCongViec=?");
			ps.setDate(1, Date.valueOf(LocalDate.now()));
			ps.setString(2, id);
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

	/**
	 * Thêm công việc mới
	 * 
	 * @param cv
	 * @return true/false
	 */
	public boolean themCongviec(CongViec cv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		int n = 0;
		try {
			// ngày kết thúc = null, trạng thái công việc chưa hoàn thành
			ps = con.prepareStatement("insert into CongViec VALUES(?,?,?,NULL, N'Chưa hoàn thành',?,?)");
			ps.setString(1, cv.getMaCongViec());
			ps.setString(2, cv.getTenCongViec());
			ps.setDate(3, cv.getNgayBatDauCV());
			ps.setString(4, cv.getCongtrinh().getMaCT());
			ps.setString(5, cv.getNhanvien().getMaNhanvien());
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

	/**
	 * Thay đổi thông tin công việc
	 * 
	 * @param cv
	 * @return
	 */
	public boolean updateCV(CongViec cv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		int n = 0;
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(
					"update CongViec set TenCongViec=?,NgayBatDauCV=?,IDCongTrinh=? where IDCongViec =?");
			ps.setString(1, cv.getTenCongViec());
			ps.setDate(2, cv.getNgayBatDauCV());
			CongTrinh ct = dao_Congtrinh.getCongTrinhByID(cv.getCongtrinh().getMaCT());
			ps.setString(3, ct.getMaCT());
			ps.setString(4, cv.getMaCongViec());

			n = ps.executeUpdate();
			if (n > 0) {
				return true;
			}
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
	
	/**
	 * load dữ liệu công việc sau ngày được chọn
	 * 
	 * @param date
	 * @param tableModel
	 * @throws SQLException
	 */
	public void isAfter(Date ngayBd, String maCongtrinh, DefaultTableModel tableModel) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = con.prepareStatement("select * from CongViec where NgayBatDauCV > '"+ngayBd+"' and IDCongTrinh = '"+maCongtrinh+"'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			vector.add(rs.getString("IDCongViec"));
			vector.add(rs.getString("TenCongViec"));
			vector.add(rs.getDate("NgayBatDauCV"));
			vector.add(rs.getDate("NgayKetThucCV"));
			vector.add(rs.getString("TrangThai"));
			tableModel.addRow(vector);
		}
	}
}
