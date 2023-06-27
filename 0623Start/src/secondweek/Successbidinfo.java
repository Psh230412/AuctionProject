package secondweek;

public class Successbidinfo {
	private int successbidno;
	private int userno;
	private int auctioncopyno;
	private int productno;
	private int isbid;
	
	public Successbidinfo(int successbidno, int userno, int auctioncopyno, int productno, int isbid) {
		super();
		this.successbidno = successbidno;
		this.userno = userno;
		this.auctioncopyno = auctioncopyno;
		this.productno = productno;
		this.isbid = isbid;
	}

	public int getSuccessbidno() {
		return successbidno;
	}

	public void setSuccessbidno(int successbidno) {
		this.successbidno = successbidno;
	}

	public int getUserno() {
		return userno;
	}

	public void setUserno(int userno) {
		this.userno = userno;
	}

	public int getAuctioncopyno() {
		return auctioncopyno;
	}

	public void setAuctioncopyno(int auctioncopyno) {
		this.auctioncopyno = auctioncopyno;
	}

	public int getProductno() {
		return productno;
	}

	public void setProductno(int productno) {
		this.productno = productno;
	}

	public int getIsbid() {
		return isbid;
	}

//	public void setIsbid(int isbid) {
//		this.isbid = isbid;
//	}

	@Override
	public String toString() {
		return "Successbidinfo [successbidno=" + successbidno + ", userno=" + userno + ", auctioncopyno="
				+ auctioncopyno + ", productno=" + productno + ", isbid=" + isbid + "]";
	}
	
	
	
	
}
