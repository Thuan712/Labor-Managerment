package entity;

import java.sql.Date;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import dao.DAO_CongNhan;


/**
 * 
 * @author nmthu
 *
 */
public class CongNhan {
	private String iDCongNhan;
	private String tenCongNhan;
	private String gioiTinh;
	private String phone;
	private Date ngaySinh;
	private String cMND;
	private String email;
	private String diaChi;
	private String trangThai;
	private TrinhDo TrinhDo;
	public CongNhan() {
		super();
	}

	public CongNhan(String iDCongNhan, String tenCongNhan, String gioiTinh, String phone, Date ngaySinh, String cMND,
			String email, String diaChi, String trangThai, TrinhDo trinhDo) {
		super();
		this.iDCongNhan = iDCongNhan;
		this.tenCongNhan = tenCongNhan;
		this.gioiTinh = gioiTinh;
		this.phone = phone;
		this.ngaySinh = ngaySinh;
		this.cMND = cMND;
		this.email = email;
		this.diaChi = diaChi;
		this.trangThai = trangThai;
		this.TrinhDo = trinhDo;
	}
	
	public CongNhan(String tenCongNhan, String gioiTinh, String phone, Date ngaySinh, String cMND, String email,
			String diaChi, String trangThai, TrinhDo trinhDo) {
		super();
		try {
			setiDCongNhan();
			this.tenCongNhan = tenCongNhan;
			this.gioiTinh = gioiTinh;
			this.phone = phone;
			this.ngaySinh = ngaySinh;
			this.cMND = cMND;
			this.email = email;
			this.diaChi = diaChi;
			this.trangThai = trangThai;
			this.TrinhDo = trinhDo;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public String getiDCongNhan() {
		return iDCongNhan;
	}
	public void setiDCongNhan() throws SQLException {
		DAO_CongNhan dao_CongNhan = new DAO_CongNhan();
		int count = dao_CongNhan.getDSCongNhan("select * from CongNhan").size();
		AtomicInteger themID = new AtomicInteger(count);
		int ma = themID.incrementAndGet();
		String convert = String.format("%6d", ma).replaceAll(" ", "0"); 
		while(dao_CongNhan.getCongNhanByID("CN"+convert) != null) {
			ma++;
			convert = String.format("%6d", ma).replaceAll(" ", "0"); // Format đuôi mã CN
		}
		convert = String.format("%6d", ma).replaceAll(" ", "0");
		this.iDCongNhan = "CN" + convert;
	}
	public String getTenCongNhan() {
		return tenCongNhan;
	}
	public void setTenCongNhan(String tenCongNhan) {
		this.tenCongNhan = tenCongNhan;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getcMND() {
		return cMND;
	}
	public void setcMND(String cMND) {
		this.cMND = cMND;
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

	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public TrinhDo getTrinhDo() {
		return TrinhDo;
	}
	public void setTrinhDo(TrinhDo trinhDo) {
		this.TrinhDo = trinhDo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iDCongNhan == null) ? 0 : iDCongNhan.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		CongNhan other = (CongNhan) obj;
		if (iDCongNhan == null) {
			if (other.iDCongNhan != null)
				return false;
		} else if (!iDCongNhan.equals(other.iDCongNhan))
			return false;
		return true;
	}
	
}
