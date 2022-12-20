package entity;
/**
 * 
 * @author nmthu
 *
 */
public class LoaiCongTrinh {
	private String maLoai;
	private String tenLoai;

	public String getMaLoai() {
		return maLoai;
	}

	public void setMaLoai(String maLoai) {
		this.maLoai = maLoai;
	}

	public String getTenLoai() {
		return tenLoai;
	}

	public void setTenLoai(String tenLoai) {
		this.tenLoai = tenLoai;
	}

	public LoaiCongTrinh(String maLoai, String tenLoai) {
		super();
		this.maLoai = maLoai;
		this.tenLoai = tenLoai;
	}

	public LoaiCongTrinh(String tenLoai) {
		super();
		this.tenLoai = tenLoai;
	}

	@Override
	public String toString() {
		return "LoaiCongTrinh [maLoai=" + maLoai + ", tenLoai=" + tenLoai + "]";
	}

}