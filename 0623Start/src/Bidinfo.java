
import javax.swing.ImageIcon;

// 낙찰/유찰 정보

public class Bidinfo {
	private Integer bidno;
	private Integer userno;
	private Integer productno;
	private String productname;
	private ImageIcon image;
	private int productPriceFinal;
	private boolean bid;

	public Bidinfo(Integer bidno, Integer userno, Integer productno, String productname, ImageIcon image,
			int productPriceFinal, boolean bid) {
		super();
		this.bidno = bidno;
		this.userno = userno;
		this.productno = productno;
		this.productname = productname;
		this.image = image;
		this.productPriceFinal = productPriceFinal;
		this.bid = bid;
	}

	public Integer getBidno() {
		return bidno;
	}

	public void setBidno(Integer bidno) {
		this.bidno = bidno;
	}

	public Integer getUserno() {
		return userno;
	}

	public void setUserno(Integer userno) {
		this.userno = userno;
	}

	public Integer getProductno() {
		return productno;
	}

	public void setProductno(Integer productno) {
		this.productno = productno;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public int getProductPriceFinal() {
		return productPriceFinal;
	}

	public void setProductPriceFinal(int productPriceFinal) {
		this.productPriceFinal = productPriceFinal;
	}

	public boolean isBid() {
		return bid;
	}

	public void setBid(boolean bid) {
		this.bid = bid;
	}

}
