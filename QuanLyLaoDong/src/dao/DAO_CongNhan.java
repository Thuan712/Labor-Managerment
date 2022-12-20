package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import connect.ConnectDB;
import entity.CongNhan;
import entity.TrinhDo;

/**
 * 
 * @author nmthu
 *
 */
public class DAO_CongNhan {
	DAO_TrinhDo dao_Trinhdo = new DAO_TrinhDo();

	/**
	 * load dữ liệu công nhân cho Giao diện quản lý công nhân
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
			vector.add(rs.getString("IDCongNhan"));
			vector.add(rs.getString("TenCongNhan"));
			vector.add(rs.getString("GioiTinh"));
//			vector.add(rs.getString("Phone"));
			vector.add(rs.getDate("NgaySinh"));
			vector.add(rs.getString("CMND"));
//			vector.add(rs.getString("Email"));
//			vector.add(rs.getString("DiaChi"));
			vector.add(rs.getString("TrangThai"));
			TrinhDo td = dao_Trinhdo.getTrinhDoByID(rs.getString("IDTrinhDo"));
			vector.add(td.getTenTrinhDo());
			tableModel.addRow(vector);
		}
	}

	/**
	 * load dữ liệu công nhân cho mục đích phân công lao động
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
			vector.add(rs.getString("IDCongNhan"));
			vector.add(rs.getString("TenCongNhan"));
			TrinhDo td = dao_Trinhdo.getTrinhDoByID(rs.getString("IDTrinhDo"));
			vector.add(td.getTenTrinhDo());
			vector.add(rs.getString("TrangThai"));
			tableModel.addRow(vector);
		}
	}

	/**
	 * Lấy danh sách tất cả công nhân trong hệ thống
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<CongNhan> getDSCongNhan(String sql) throws SQLException {
		ArrayList<CongNhan> listCN = new ArrayList<CongNhan>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			TrinhDo td = dao_Trinhdo.getTrinhDoByID(rs.getString("idtrinhdo"));
			CongNhan cn = new CongNhan(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getDate(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
					td);
			listCN.add(cn);
		}
		return listCN;
	}

	/**
	 * Tìm công nhân có id được chọn
	 * 
	 * @param id
	 * @return CongNhan
	 * @throws SQLException
	 */
	public CongNhan getCongNhanByID(String id) throws SQLException {
		CongNhan cn = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from CongNhan where idcongnhan='" + id + "'";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String maNV = rs.getString(1);
			String ten = rs.getString(2);
			String gt = rs.getString(3);
			String phone = rs.getString(4);
			Date ns = rs.getDate(5);
			String cmnd = rs.getString(6);
			String email = rs.getString(7);
			String diachi = rs.getString(8);
			String trangthai = rs.getString(9);
			TrinhDo td = dao_Trinhdo.getTrinhDoByID(rs.getString(11));
			cn = new CongNhan(maNV, ten, gt, phone, ns, cmnd, email, diachi, trangthai, td);
		}
		return cn;
	}

	public boolean createCongNhan(CongNhan cn) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		int n = 0;
		try {
			String sql = "insert into CongNhan values(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, cn.getiDCongNhan());
			ps.setString(2, cn.getTenCongNhan());
			ps.setString(3, cn.getGioiTinh());
			ps.setString(4, cn.getPhone());
			ps.setDate(5, cn.getNgaySinh());
			ps.setString(6, cn.getcMND());
			ps.setString(7, cn.getEmail());
			ps.setString(8, cn.getDiaChi());
			ps.setString(9, cn.getTrangThai());
			ps.setString(10, cn.getTrinhDo().getTenTrinhDo());
			ps.setString(11, cn.getTrinhDo().getiDTrinhDo());
			n = ps.executeUpdate();
			if (n > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Thay đổi trạng thái hoạt động của công nhân [Đang làm / Đang rảnh]
	 * 
	 * @param id
	 * @return
	 */
	public boolean thaydoiTrangthaiCongnhan(String id, String trangthaiMoi) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		int n = 0;
		try {
			ps = con.prepareStatement("update CongNhan set TrangThai=? where IDCongNhan=?");
			ps.setString(1, trangthaiMoi);
			ps.setString(2, id);
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
	 * @param cn
	 * @return Cập nhật công nhân 
	 */
	public boolean updateCongNhan(CongNhan cn) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		int n = 0;
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(
					"update CongNhan set TenCongNhan=?,GioiTinh=?,Phone=?,NgaySinh=?,CMND=?,Email=?,DiaChi=?,TrangThai=?,TrinhDo=?,IDTrinhDo=? where idCongNhan=?");
			ps.setString(1, cn.getTenCongNhan());
			ps.setString(2, cn.getGioiTinh());
			ps.setString(3, cn.getPhone());
			ps.setDate(4, cn.getNgaySinh());
			ps.setString(5, cn.getcMND());
			ps.setString(6, cn.getEmail());
			ps.setString(7, cn.getDiaChi());
			ps.setString(8, cn.getTrangThai());
			TrinhDo td = dao_Trinhdo.getTrinhDoByID(cn.getTrinhDo().getiDTrinhDo());
			ps.setString(9, td.getTenTrinhDo());
			ps.setString(10, td.getiDTrinhDo());
			ps.setString(11, cn.getiDCongNhan());
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
	 * @param id
	 * @return check công nhân có trong bảng phân công hay không
	 * @throws SQLException
	 */
	public CongNhan checkCNCoCongTrinh(String id) throws SQLException {
		CongNhan cn = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select bangphancong.[IDCongNhan],[TenCongNhan],[GioiTinh],[Phone],[NgaySinh],[CMND],[Email],[DiaChi],[TrangThai],[TrinhDo] from CongNhan join BangPhanCong on CongNhan.IDCongNhan=BangPhanCong.IDCongNhan where bangphancong.idcongnhan='"
				+ id + "'";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String maNV = rs.getString(1);
			String ten = rs.getString(2);
			String gt = rs.getString(3);
			String phone = rs.getString(4);
			Date ns = rs.getDate(5);
			String cmnd = rs.getString(6);
			String email = rs.getString(7);
			String diachi = rs.getString(8);
			String trangthai = rs.getString(9);
			TrinhDo td = dao_Trinhdo.getTrinhDoByID(rs.getString(10));
			cn = new CongNhan(maNV, ten, gt, phone, ns, cmnd, email, diachi, trangthai, td);
		}
		return cn;
	}

	public boolean delCongNhan(String maCongNhan) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		try {
			PreparedStatement ps = con.prepareStatement("delete from CongNhan where IDCongNhan=?");
			ps.setString(1, maCongNhan);
			PreparedStatement ps1 = con.prepareStatement("delete from BangPhanCong where IDCongNhan=?");
			ps1.setString(1, maCongNhan);
			int n1 = ps1.executeUpdate();
			int n = ps.executeUpdate();
			if (n1 > 0) {
				if (n > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
