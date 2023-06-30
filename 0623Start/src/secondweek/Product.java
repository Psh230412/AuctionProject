package secondweek;
import java.sql.Blob;
import java.time.LocalDateTime;

public class Product {
	private int setNo;
	private int productNo;
	private int auctionNo;
	private String productName;
	private int productPriceNow;
	private String productContent;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Blob image;
	
	public Product(int setNo, int productNo, int auctionNo, String productName, int productPriceNow,
			String productContent, LocalDateTime startTime, LocalDateTime endTime,Blob image) {
		super();
		this.setNo = setNo;
		this.productNo = productNo;
		this.auctionNo = auctionNo;
		this.productName = productName;
		this.productPriceNow = productPriceNow;
		this.productContent = productContent;
		this.startTime = startTime;
		this.endTime = endTime;
		this.image = image;
	}
	
	public int getAuctionNo() {
		return auctionNo;
	}

	public void setAuctionNo(int auctionNo) {
		this.auctionNo = auctionNo;
	}

	public int getSetNo() {
		return setNo;
	}

	public void setSetNo(int setNo) {
		this.setNo = setNo;
	}

	public int getProductNo() {
		return productNo;
	}

	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductPriceNow() {
		return productPriceNow;
	}

	public void setProductPriceNow(int productPriceNow) {
		this.productPriceNow = productPriceNow;
	}

	public String getProductContent() {
		return productContent;
	}

	public void setProductContent(String productContent) {
		this.productContent = productContent;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	
	
	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Product [setNo=" + setNo + ", productNo=" + productNo + ", productName=" + productName
				+ ", productPriceNow=" + productPriceNow + ", productContent=" + productContent + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}
}