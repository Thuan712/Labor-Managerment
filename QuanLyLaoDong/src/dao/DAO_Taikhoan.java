package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connect.ConnectDB;
import entity.TaiKhoan;
/**
 * 
 * @author nmthu
 *
 */
public class DAO_Taikhoan {
	
	public DAO_Taikhoan() {
		
	}
	public ArrayList<TaiKhoan> getDsTaikhoan() throws SQLException {
		ArrayList<TaiKhoan> dstk = new ArrayList<TaiKhoan>();
		ConnectDB.getInstance();
        Connection con=ConnectDB.getCon();
		String sql = "select * from TaiKhoan";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			TaiKhoan tk = new TaiKhoan(rs.getString(1), rs.getString(2), rs.getString(3));
			dstk.add(tk);
		}
		return dstk;
	}
	public TaiKhoan getTaiKhoanByID(String idTaikhoan) throws SQLException {
		TaiKhoan tk = null;
		ConnectDB.getInstance();
        Connection con=ConnectDB.getCon();
		String sql = "select * from TaiKhoan where idtaikhoan = N'"+idTaikhoan+"'";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			tk = new TaiKhoan(rs.getString(1), rs.getString(2), rs.getString(3));
		}
		return tk;
	}
	/**Kiểm tra tên tài khoản có tồn tại hay không => mục đích: tạo tài khoản cho nhân viên mới không bị trùng
	 * 
	 * @param taikhoan
	 * @return tk
	 * @throws SQLException
	 */
	public TaiKhoan checkTenTaikhoanAvailable(String taikhoan) throws SQLException {
		TaiKhoan tk = null;
		ConnectDB.getInstance();
        Connection con=ConnectDB.getCon();
        String sql = "select * from TaiKhoan where tentaikhoan = N'"+taikhoan+"'";
        PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			tk = new TaiKhoan(rs.getString(1), rs.getString(2), rs.getString(3));
		}
		return tk;
	}
	/**Thêm tài khoản mới vào hệ thống
	 * 
	 * @param tk
	 * @return
	 * @throws SQLException
	 */
	public boolean themTaikhoan(TaiKhoan tk) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		int n = 0;
		try {
			ps = con.prepareStatement("insert into TaiKhoan values(?,?,?)");
			ps.setString(1, tk.getmaTaikhoan());
			ps.setString(2, tk.getTenTaikhoan());
			ps.setString(3, tk.getMatkhau());
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
	/** đổi mật khẩu tài khoản
	 * 
	 * @param tk
	 * @return
	 */
	public boolean doiMatkhau(TaiKhoan tk, String matkhaumoi) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		int n = 0;
		try {
			ps = con.prepareStatement("update TaiKhoan set MatKhau=? where TenTaiKhoan=?");
			ps.setString(1, matkhaumoi);
			ps.setString(2, tk.getTenTaikhoan());
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
	/**Tìm tài khoản theo tên tài khoản
	 * 
	 * @param tenTK
	 * @return
	 */
	public TaiKhoan getTaikhoanByName(String tenTK) {
		TaiKhoan tk = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from TaiKhoan where TenTaiKhoan = N'" + tenTK + "'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tk = new TaiKhoan(rs.getString(1), rs.getString(2), rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tk;
	}
	/**Kiểm tra tài khoản mật khẩu để đăng nhập
	 * 
	 * @param user
	 * @param pass
	 * @return
	 */
	public boolean checkAccount(String user, String pass) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		int n=0;
		try {
			String sql = "select * from TaiKhoan where TenTaiKhoan =? and MatKhau=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				n++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n>0 ? true : false;
	}
}
