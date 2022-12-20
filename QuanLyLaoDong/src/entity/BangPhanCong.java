package entity;

import java.sql.Date;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import dao.DAO_BangPhanCong;
/**
 * 
 * @author nmthu
 *
 */
public class BangPhanCong {
	private String maPhanCong;
	private CongViec congviec;
	private CongNhan congnhan;
	private Date ngayBatDauCV;
	private Date ngayKetThucCV;
	public BangPhanCong(String maPhanCong, CongViec congviec, CongNhan congnhan, Date ngayBatDauCV,
			Date ngayKetThucCV) {
		super();
		this.maPhanCong = maPhanCong;
		this.congviec = congviec;
		this.congnhan = congnhan;
		this.ngayBatDauCV = ngayBatDauCV;
		this.ngayKetThucCV = ngayKetThucCV;
	}
	public BangPhanCong() {
		super();
	}
	public BangPhanCong(CongViec congviec, CongNhan congnhan, Date ngayBatDauCV) {
		super();
		try {
			setMaPhanCong();
			this.congviec = congviec;
			this.congnhan = congnhan;
			this.ngayBatDauCV = ngayBatDauCV;
			this.ngayKetThucCV = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public String getMaPhanCong() {
		return maPhanCong;
	}
	public void setMaPhanCong() throws SQLException {
		DAO_BangPhanCong dao_BangPC = new DAO_BangPhanCong();
		int count = dao_BangPC.getSoluongBangPhanCong("select * from BangPhanCong");
		AtomicInteger ID_GENERATOR = new AtomicInteger(count);
		int ma = ID_GENERATOR.incrementAndGet();
		String convert = String.format("%6d", ma).replaceAll(" ", "0");
		while(dao_BangPC.getBangPhancongByID("PC"+convert) != null) {
			ma++;
			convert = String.format("%6d", ma).replaceAll(" ", "0");
		}
		convert = String.format("%6d", ma).replaceAll(" ", "0");
		this.maPhanCong = "PC" + convert;
	}
	public CongViec getCongviec() {
		return congviec;
	}
	public void setCongviec(CongViec congviec) {
		this.congviec = congviec;
	}
	public CongNhan getCongnhan() {
		return congnhan;
	}
	public void setCongnhan(CongNhan congnhan) {
		this.congnhan = congnhan;
	}
	public Date getNgayBatDauCV() {
		return ngayBatDauCV;
	}
	public void setNgayBatDauCV(Date ngayBatDauCV) {
		this.ngayBatDauCV = ngayBatDauCV;
	}
	public Date getNgayKetThucCV() {
		return ngayKetThucCV;
	}
	public void setNgayKetThucCV(Date ngayKetThucCV) {
		this.ngayKetThucCV = ngayKetThucCV;
	}
	@Override
	public String toString() {
		return "BangPhanCong [maPhanCong=" + maPhanCong + ", congviec=" + congviec + ", congnhan=" + congnhan
				+ ", ngayBatDauCV=" + ngayBatDauCV + ", ngayKetThucCV=" + ngayKetThucCV + "]";
	}
	
}
