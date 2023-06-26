import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Product {
	private int userId;
	private int productNo;
	private String productName;
	private int productPriceNow;
	private String productContent;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public Product(int userId, int productNo, String productName, int productPriceNow, String productContent,
			LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.userId = userId;
		this.productNo = productNo;
		this.productName = productName;
		this.productPriceNow = productPriceNow;
		this.productContent = productContent;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Product(int productNo, String productName, int productPriceNow, String productContent,
			LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.productNo = productNo;
		this.productName = productName;
		this.productPriceNow = productPriceNow;
		this.productContent = productContent;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
	
	@Override
	public String toString() {
		return "Product [userId=" + userId + ", productNo=" + productNo + ", productName=" + productName
				+ ", productPriceNow=" + productPriceNow + ", productContent=" + productContent + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}
	
	
}
