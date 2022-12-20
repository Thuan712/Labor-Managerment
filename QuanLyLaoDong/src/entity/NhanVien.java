package entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import dao.DAO_Nhanvien;
/**
 * 
 * @author nmthu
 *
 */
public class NhanVien implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2591703112972605174L;
	private String maNhanvien;
	private String tenNhanvien;
	private String gioitinh;
	private Date ngaysinh;
	private String phone;
	private String cmnd;
	private String email;
	private String diaChi;
	private String trangthai;
	private TaiKhoan taikhoan;

	public NhanVien(String tenNhanvien, String gioitinh, Date ngaysinh, String phone, String cmnd, String email, String diaChi,
			String trangthai, TaiKhoan taikhoan) {
		super();
		try {
			setMaNhanvien();
			this.tenNhanvien = tenNhanvien;
			this.gioitinh = gioitinh;
			this.ngaysinh = ngaysinh;
			this.phone = phone;
			this.cmnd = cmnd;
			this.email = email;
			this.diaChi = diaChi;
			this.trangthai = trangthai;
			this.taikhoan = taikhoan;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public NhanVien(String maNhanvien, String tenNhanvien, String gioitinh, Date ngaysinh, String phone,String cmnd, String email,
			String diaChi, String trangthai, TaiKhoan taikhoan) {
		super();
		this.maNhanvien = maNhanvien;
		this.tenNhanvien = tenNhanvien;
		this.gioitinh = gioitinh;
		this.ngaysinh = ngaysinh;
		this.phone = phone;
		this.cmnd = cmnd;
		this.email = email;
		this.diaChi = diaChi;
		this.trangthai = trangthai;
		this.taikhoan = taikhoan;
	}

	public NhanVien() {
		super();
	}
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setMaNhanvien(String maNhanvien) {
		this.maNhanvien = maNhanvien;
	}
	
	public String getMaNhanvien() {
		return maNhanvien;
	}
	public void setMaNhanvien() throws SQLException {
		DAO_Nhanvien dao_Nhanvien = new DAO_Nhanvien();
		int count = dao_Nhanvien.getDsNhanvien().size();
		AtomicInteger ID_GENERATOR = new AtomicInteger(count);
		int ma = ID_GENERATOR.incrementAndGet();
		String convert = String.format("%6d", ma).replaceAll(" ", "0"); 
		while(dao_Nhanvien.getNhanvienByID("NV"+convert) != null) {
			ma++;
			convert = String.format("%6d", ma).replaceAll(" ", "0");
		}
		convert = String.format("%6d", ma).replaceAll(" ", "0");
		this.maNhanvien = "NV" + convert;
	}

	public String getTenNhanvien() {
		return tenNhanvien;
	}

	public void setTenNhanvien(String tenNhanvien) {
		this.tenNhanvien = tenNhanvien;
	}

	public String getGioitinh() {
		return gioitinh;
	}

	public void setGioitinh(String gioitinh) {
		this.gioitinh = gioitinh;
	}

	public Date getNgaysinh() {
		return ngaysinh;
	}

	public void setNgaysinh(Date ngaysinh) {
		this.ngaysinh = ngaysinh;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public TaiKhoan getTaikhoan() {
		return taikhoan;
	}

	public void setTaikhoan(TaiKhoan taikhoan) {
		this.taikhoan = taikhoan;
	}


	@Override
	public String toString() {
		return "NhanVien [maNhanvien=" + maNhanvien + ", tenNhanvien=" + tenNhanvien + ", gioitinh=" + gioitinh
				+ ", ngaysinh=" + ngaysinh + ", phone=" + phone + ", cmnd=" + cmnd + ", email=" + email + ", diaChi="
				+ diaChi + ", trangthai=" + trangthai + ", taikhoan=" + taikhoan + "]";
	}

}
