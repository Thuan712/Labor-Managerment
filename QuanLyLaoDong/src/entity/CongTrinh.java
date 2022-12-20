package entity;

import java.sql.Date;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import dao.DAO_CongTrinh;
/**
 * 
 * @author nmthu
 *
 */
public class CongTrinh {
	private String tenCT;
	private String maCT;
	private String diaDiem;
	private Date ngayCapPhep;
	private Date ngayKhoiCong;
	private Date ngayDuKien;
	private String tienDo;
	private LoaiCongTrinh loaiCT;

	public String getTenCT() {
		return tenCT;
	}

	public void setTenCT(String tenCT) {
		this.tenCT = tenCT;
	}

	public String getMaCT() {
		return maCT;
	}

	public void setMaCT(String maCT) {
		this.maCT = maCT;
	}

	public String getDiaDiem() {
		return diaDiem;
	}

	public void setDiaDiem(String diaDiem) {
		this.diaDiem = diaDiem;
	}

	public Date getNgayCapPhep() {
		return ngayCapPhep;
	}

	public void setNgayCapPhep(Date ngayCapPhep) {
		this.ngayCapPhep = ngayCapPhep;
	}

	public Date getNgayKhoiCong() {
		return ngayKhoiCong;
	}

	public void setNgayKhoiCong(Date ngayKhoiCong) {
		this.ngayKhoiCong = ngayKhoiCong;
	}

	public Date getNgayDuKien() {
		return ngayDuKien;
	}

	public void setNgayDuKien(Date ngayDuKien) {
		this.ngayDuKien = ngayDuKien;
	}

	public String getTienDo() {
		return tienDo;
	}

	public void setTienDo(String tienDo) {
		this.tienDo = tienDo;
	}

	public LoaiCongTrinh getLoaiCT() {
		return loaiCT;
	}

	public void setLoaiCT(LoaiCongTrinh loaiCT) {
		this.loaiCT = loaiCT;
	}

	public CongTrinh(String tenCT, String maCT, String diaDiem, Date ngayCapPhep, Date ngayKhoiCong, Date ngayDuKien,
			String tienDo, LoaiCongTrinh loaiCT) {
		super();
		this.tenCT = tenCT;
		this.maCT = maCT;
		this.diaDiem = diaDiem;
		this.ngayCapPhep = ngayCapPhep;
		this.ngayKhoiCong = ngayKhoiCong;
		this.ngayDuKien = ngayDuKien;
		this.tienDo = tienDo;
		this.loaiCT = loaiCT;
	}

	public CongTrinh(String tenCT, String diaDiem, Date ngayCapPhep, Date ngayKhoiCong, Date ngayDuKien, String tienDo,
			LoaiCongTrinh loaiCT) {
		super();
		this.tenCT = tenCT;
		try {
			setMaCongTrinh();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.diaDiem = diaDiem;
		this.ngayCapPhep = ngayCapPhep;
		this.ngayKhoiCong = ngayKhoiCong;
		this.ngayDuKien = ngayDuKien;
		this.tienDo = tienDo;
		this.loaiCT = loaiCT;
	}

	public CongTrinh() {
		super();
	}

	public void setMaCongTrinh() throws SQLException {

		DAO_CongTrinh dao_ct = new DAO_CongTrinh();
		int count = dao_ct.getDsCongTrinh().size();
		AtomicInteger ID_GENERATOR = new AtomicInteger(count);
		int ma = ID_GENERATOR.incrementAndGet();
		String convert = String.format("%6d", ma).replaceAll(" ", "0");
		while(dao_ct.getCongTrinhByID("CT"+convert) != null) {
			ma++;
			convert = String.format("%6d", ma).replaceAll(" ", "0");
		}
		convert = String.format("%6d", ma).replaceAll(" ", "0");
		this.maCT = "CT" + convert;
	}

	@Override
	public String toString() {
		return "CongTrinh [tenCT=" + tenCT + ", maCT=" + maCT + ", diaDiem=" + diaDiem + ", ngayCapPhep=" + ngayCapPhep
				+ ", ngayKhoiCong=" + ngayKhoiCong + ", ngayDuKien=" + ngayDuKien + ", tienDo=" + tienDo + ", loaiCT="
				+ loaiCT + "]";
	}
}
