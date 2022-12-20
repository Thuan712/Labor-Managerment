package entity;
/**
 * 
 * @author nmthu
 *
 */
public class TrinhDo {
	private String iDTrinhDo;
	private String tenTrinhDo;
	/**
	 * 
	 */
	public TrinhDo() {
		super();
	}
	/**
	 * @param iDTrinhDo
	 * @param tenTrinhDo
	 */
	public TrinhDo(String iDTrinhDo, String tenTrinhDo) {
		super();
		this.iDTrinhDo = iDTrinhDo;
		this.tenTrinhDo = tenTrinhDo;
	}
	
	public TrinhDo(String tenTrinhDo) {
		super();
		this.tenTrinhDo = tenTrinhDo;
	}
	
	
	public String getiDTrinhDo() {
		return iDTrinhDo;
	}
	public void setiDTrinhDo(String iDTrinhDo) {
		this.iDTrinhDo = iDTrinhDo;
	}
	public String getTenTrinhDo() {
		return tenTrinhDo;
	}
	public void setTenTrinhDo(String tenTrinhDo) {
		this.tenTrinhDo = tenTrinhDo;
	}
	
}
