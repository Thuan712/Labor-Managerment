package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import connect.ConnectDB;
import entity.TrinhDo;
import entity.BangPhanCong;
import entity.CongNhan;
import entity.CongViec;

/**
 * 
 * @author nmthu
 *
 */
public class DAO_BangPhanCong {
	DAO_TrinhDo dao_Trinhdo = new DAO_TrinhDo();
	DAO_CongNhan dao_Congnhan = new DAO_CongNhan();
	DAO_CongViec dao_Congviec = new DAO_CongViec();

	/**
	 * load dữ liệu bảng phân công => danh sách nhân viên được phân vào 1 công việc
	 * cụ thể
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
			vector.add(rs.getString("IDPhanCong"));
			vector.add(rs.getString("TenCongNhan"));
			TrinhDo td = dao_Trinhdo.getTrinhDoByID(rs.getString("IDTrinhDo"));
			vector.add(td.getTenTrinhDo());
			vector.add(rs.getString("TrangThai"));
			tableModel.addRow(vector);
		}
	}

	/**
	 * Tạo phân công mới => Phân công công nhân vào 1 công việc nào đó
	 * 
	 * @param bpc
	 * @return
	 */
	public boolean taoBangPhancong(BangPhanCong bpc) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		int n = 0;
		try {
			ps = con.prepareStatement("insert into BangPhanCong VALUES(?,?,?,?,?,?)");
			ps.setString(1, bpc.getMaPhanCong());
			ps.setString(2, bpc.getCongviec().getMaCongViec());
			ps.setString(3, bpc.getCongviec().getTenCongViec());
			ps.setString(4, bpc.getCongnhan().getiDCongNhan());
			ps.setDate(5, bpc.getNgayBatDauCV());
			ps.setDate(6, bpc.getNgayKetThucCV());
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
	 * lấy tổng số lượng bảng phân công => tạo bảng phân công mới không trùng mã
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int getSoluongBangPhanCong(String sql) throws SQLException {
		int i = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			i++;
		}
		return i;
	}
	/**Cập nhật ngày kết thúc công việc
	 * Khi công nhân được chuyển ra khỏi công việc đang làm hoặc công việc kết thúc
	 * @param idPhancong
	 * @param ngayKetThuc
	 * @return
	 */
	public boolean updateNgayKetThucCV(String idPhancong, Date ngayKetThuc) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		int n = 0;
		try {
			ps = con.prepareStatement("update BangPhanCong set NgayKetThucCV=? where IDPhanCong=?");
			ps.setDate(1, ngayKetThuc);
			ps.setString(2, idPhancong);
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
	/**Lấy ra bảng phân công có mã được chọn
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public BangPhanCong getBangPhancongByID(String id) throws SQLException {
		BangPhanCong bpc = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from BangPhanCong where idphancong='" + id + "'";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String maPC = rs.getString("IDPhanCong");
			String maCV = rs.getString("IDCongViec");
			String maCN = rs.getString("IDCongNhan");
			Date ngayBD = rs.getDate("NgayBatDauCV");
			Date ngayKT = rs.getDate("NgayKetThucCV");
			CongViec cv = dao_Congviec.getCongviecByID(maCV);
			CongNhan cn = dao_Congnhan.getCongNhanByID(maCN);
			bpc = new BangPhanCong(maPC, cv, cn, ngayBD, ngayKT);
		}
		return bpc;
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
					"update BangPhanCong set NgayKetThucCV=? where IDCongViec=?");
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
}
