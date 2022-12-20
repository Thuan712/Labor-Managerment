package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connect.ConnectDB;
import entity.TrinhDo;
/**
 * 
 * @author nmthu
 *
 */
public class DAO_TrinhDo {
	public DAO_TrinhDo() {
	}

	public ArrayList<TrinhDo> getDsTrinhDo() {
		ArrayList<TrinhDo> listTD = new ArrayList<TrinhDo>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from TrinhDo";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TrinhDo td = new TrinhDo(rs.getString(1), rs.getString(2));
				listTD.add(td);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listTD;
	}

	public TrinhDo getTrinhDoByID(String idTrinhDo) {
		TrinhDo td = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from TrinhDo where IDTrinhDo = N'" + idTrinhDo + "'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				td = new TrinhDo(rs.getString(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return td;
	}

	public TrinhDo getTrinhDoByName(String tenTD) {
		TrinhDo td = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from TrinhDo where TenTrinhDo = N'" + tenTD + "'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				td = new TrinhDo(rs.getString(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return td;
	}

}
