package entity;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import dao.DAO_Taikhoan;
/**
 * 
 * @author nmthu
 *
 */
public class TaiKhoan {
	private String maTaikhoan;
	private String tenTaikhoan;
	private String matkhau;
	public TaiKhoan(String maTaikhoan, String tenTaikhoan, String matkhau) {
		super();
		this.maTaikhoan = maTaikhoan;
		this.tenTaikhoan = tenTaikhoan;
		this.matkhau = matkhau;
	}
	public TaiKhoan(String tenTaikhoan, String matkhau) {
		super();
		try {
			setmaTaikhoan();
			this.tenTaikhoan = tenTaikhoan;
			this.matkhau = matkhau;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public TaiKhoan() {
		super();
	}
	public String getmaTaikhoan() {
		return maTaikhoan;
	}
	public void setmaTaikhoan() throws SQLException {
		DAO_Taikhoan dao_Taikhoan = new DAO_Taikhoan();
		int count = dao_Taikhoan.getDsTaikhoan().size();
		AtomicInteger ID_GENERATOR = new AtomicInteger(count);
		int ma = ID_GENERATOR.incrementAndGet();
		String convert = String.format("%6d", ma).replaceAll(" ", "0");
		while(dao_Taikhoan.getTaiKhoanByID("TK"+convert) != null) {
			ma++;
			convert = String.format("%6d", ma).replaceAll(" ", "0");
		}
		convert = String.format("%6d", ma).replaceAll(" ", "0");
		this.maTaikhoan = "TK" + ma;
	}
	public String getTenTaikhoan() {
		return tenTaikhoan;
	}
	public void setTenTaikhoan(String tenTaikhoan) {
		this.tenTaikhoan = tenTaikhoan;
	}
	public String getMatkhau() {
		return matkhau;
	}
	public void setMatkhau(String matkhau) {
		this.matkhau = matkhau;
	}
	@Override
	public String toString() {
		return "TaiKhoan [maTaikhoan=" + maTaikhoan + ", tenTaikhoan=" + tenTaikhoan + ", matkhau=" + matkhau + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maTaikhoan == null) ? 0 : maTaikhoan.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
//		if (getClass() != obj.getClass())
//			return false;
		TaiKhoan other = (TaiKhoan) obj;
		if (maTaikhoan == null) {
			if (other.maTaikhoan != null)
				return false;
		} else if (!maTaikhoan.equals(other.maTaikhoan))
			return false;
		return true;
	}
}
	