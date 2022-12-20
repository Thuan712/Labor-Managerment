package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import connect.ConnectDB;
import entity.LoaiCongTrinh;

/**
 * 
 * @author nmthu
 *
 */
public class DAO_LoaiCongTrinh {

	public DefaultTableModel getAllLCT() throws SQLException {
		String[] header = { "Mã Loại", "Tên Loại" };
		DefaultTableModel model = new DefaultTableModel(header, 0);
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from LoaiCongTrinh";
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			Object[] o = { rs.getString(1), rs.getString(2) };
			model.addRow(o);
		}
		return model;
	}
	
	public ArrayList<LoaiCongTrinh> getLoaiCongtrinhs() throws SQLException {
		ArrayList<LoaiCongTrinh> list = new ArrayList<LoaiCongTrinh>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from LoaiCongTrinh";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			LoaiCongTrinh lct = new LoaiCongTrinh(rs.getString(1), rs.getString(2));
			list.add(lct);
		}
		return list;
	}

	public LoaiCongTrinh getLoaiCT(String IDLoaiCT) throws SQLException {
		LoaiCongTrinh lct = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from LoaiCongTrinh where IDLoaiCT = N'" + IDLoaiCT + "'";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			lct = new LoaiCongTrinh(rs.getString(1), rs.getString(2));
		}
		return lct;
	}

	public LoaiCongTrinh getLoaiCTByName(String ten) throws SQLException {
		LoaiCongTrinh lct = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from LoaiCongTrinh where TenLoaiCongTrinh like N'" + ten + "'";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			lct = new LoaiCongTrinh(rs.getString(1), rs.getString(2));
		}
		return lct;
	}

	public boolean themLCT(LoaiCongTrinh lct) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		int n = 0;
		try {
			ps = con.prepareStatement("insert into LoaiCongTrinh values(?,?)");
			ps.setString(1, lct.getMaLoai());
			ps.setString(2, lct.getTenLoai());
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

	public ArrayList<LoaiCongTrinh> getDsLoaiCT() throws SQLException {
		ArrayList<LoaiCongTrinh> dslct = new ArrayList<LoaiCongTrinh>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from LoaiCongTrinh";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String ma = rs.getString("idloaict");
			String ten = rs.getString("tenloaicongtrinh");

			LoaiCongTrinh lct = new LoaiCongTrinh(ma, ten);
			dslct.add(lct);
		}
		return dslct;
	}
}
