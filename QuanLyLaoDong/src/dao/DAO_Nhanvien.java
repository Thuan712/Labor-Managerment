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
import entity.NhanVien;
import entity.TaiKhoan;
/**
 * 
 * @author nmthu
 *
 */
public class DAO_Nhanvien {
	DAO_Taikhoan dao_tk = new DAO_Taikhoan();

	public DAO_Nhanvien() {

	}

	/**
	 * load danh sách nhân viên vào table
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
			vector.add(rs.getString("idnhanvien")); // id
			vector.add(rs.getString("tennhanvien")); // ten
			vector.add(rs.getString("gioitinh")); // gioitinh
			vector.add(rs.getDate("ngaysinh")); // ngaysinh
			vector.add(rs.getString("cmnd")); // cmnd
			vector.add(dao_tk.getTaiKhoanByID(rs.getString("idtaikhoan")).getTenTaikhoan());
			vector.add(rs.getString("trangthai")); // trangthai
			tableModel.addRow(vector);
		}
	}

	/**
	 * tìm nhân viên theo id 
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public NhanVien getNhanvienByID(String id) throws SQLException {
		NhanVien nv = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from NhanVien where idnhanvien = '" + id + "'";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String manv = rs.getString("idnhanvien");
			String ten = rs.getString("tennhanvien");
			String gioitinh = rs.getString("gioitinh");
			Date ngaysinh = rs.getDate("ngaysinh");
			String phone = rs.getString("phone");
			String cmnd = rs.getString("cmnd");
			String email = rs.getString("email");
			String diachi = rs.getString("diachi");
			String trangthai = rs.getString("trangthai");
			TaiKhoan tk = dao_tk.getTaiKhoanByID(rs.getString("idtaikhoan"));
			nv = new NhanVien(manv, ten, gioitinh, ngaysinh, phone, cmnd, email, diachi, trangthai, tk);
		}
		return nv;
	}
	
	/**
	 * tìm nhân viên theo id tài khoản
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public NhanVien getNhanvienByIDTaiKhoan(String id) throws SQLException {
		NhanVien nv = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from NhanVien where idtaikhoan = '" + id + "'";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String manv = rs.getString("idnhanvien");
			String ten = rs.getString("tennhanvien");
			String gioitinh = rs.getString("gioitinh");
			Date ngaysinh = rs.getDate("ngaysinh");
			String phone = rs.getString("phone");
			String cmnd = rs.getString("cmnd");
			String email = rs.getString("email");
			String diachi = rs.getString("diachi");
			String trangthai = rs.getString("trangthai");
			TaiKhoan tk = dao_tk.getTaiKhoanByID(rs.getString("idtaikhoan"));
			nv = new NhanVien(manv, ten, gioitinh, ngaysinh, phone, cmnd, email, diachi, trangthai, tk);
		}
		return nv;
	}

	/**
	 * xuất danh sách nhân viên trong hệ thống
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<NhanVien> getDsNhanvien() throws SQLException {
		ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		String sql = "select * from NhanVien";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String manv = rs.getString("idnhanvien");
			String ten = rs.getString("tennhanvien");
			String gioitinh = rs.getString("gioitinh");
			Date ngaysinh = rs.getDate("ngaysinh");
			String phone = rs.getString("phone");
			String cmnd = rs.getString("cmnd");
			String email = rs.getString("email");
			String diachi = rs.getString("diachi");
			String trangthai = rs.getString("trangthai");
			TaiKhoan tk = dao_tk.getTaiKhoanByID(rs.getString("idtaikhoan"));
			NhanVien nv = new NhanVien(manv, ten, gioitinh, ngaysinh, phone, cmnd, email, diachi, trangthai, tk);
			dsnv.add(nv);
		}
		return dsnv;
	}

	/**
	 * Thêm nhân viên mới vào hệ thống
	 * 
	 * @param nv
	 * @return
	 * @throws SQLException
	 */
	public boolean themNhanvien(NhanVien nv) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		int n = 0;
		try {
			ps = con.prepareStatement("insert into NhanVien(IDNhanVien, TenNhanVien, GioiTinh, ngaySinh, Phone, CMND, email, diaChi, IDTaiKhoan, TrangThai) VALUES(?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, nv.getMaNhanvien());
			ps.setString(2, nv.getTenNhanvien());
			ps.setString(3, nv.getGioitinh());
			ps.setDate(4, nv.getNgaysinh());
			ps.setString(5, nv.getPhone());
			ps.setString(6, nv.getCmnd());
			ps.setString(7, nv.getEmail());
			ps.setString(8, nv.getDiaChi());
			TaiKhoan tk = dao_tk.getTaiKhoanByID(nv.getTaikhoan().getmaTaikhoan());
			ps.setString(9, tk.getmaTaikhoan());
			ps.setString(10, nv.getTrangthai());
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
	/**Sửa thông tin nhân viên 
	 * 
	 * @param nv
	 * @return
	 */
	public boolean suaThongtinNhanvien(NhanVien nv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		int n = 0;
		try {
			ps = con.prepareStatement("update NhanVien set TenNhanVien=?, GioiTinh=?, ngaySinh=?, Phone=?, CMND=?, email=?, diaChi=? where IDNhanVien=?");
			ps.setString(1, nv.getTenNhanvien());
			ps.setString(2, nv.getGioitinh());
			ps.setDate(3, nv.getNgaysinh());
			ps.setString(4, nv.getPhone());
			ps.setString(5, nv.getCmnd());
			ps.setString(6, nv.getEmail());
			ps.setString(7, nv.getDiaChi());
			ps.setString(8, nv.getMaNhanvien());
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
	/**Thay đổi trạng thái hoạt động của nhân viên [Đi Làm / Nghỉ]
	 * 
	 * @param id
	 * @return
	 */
	public boolean thaydoiTrangthaiNhanvien(String id, String trangthaiMoi) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getCon();
		PreparedStatement ps = null;
		int n = 0;
		try {
			ps = con.prepareStatement("update NhanVien set TrangThai=? where IDNhanVien=?");
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
	
}
