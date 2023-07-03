package secondweek;


// 유저가 직접 등록한 물품 관련 정보 or
// 유저가 참여한 입찰 - 다른 유저가 등록한 물품을 현재 유저가 입찰하므로 담는 정보가 Enrollment와 다름

import java.time.LocalDateTime;

import javax.swing.ImageIcon;

public class EnrollParticipate {
	private Integer setno;
	private Integer userno;
	private Integer productno;
	private Integer auctionno;
	private String productname;
	private ImageIcon image;
	private LocalDateTime endTime;
	private int productPriceNow;

	public EnrollParticipate(Integer setno, Integer userno, Integer productno, Integer auctionno, String productname,
			ImageIcon image, LocalDateTime endTime, int productPriceNow) {
		super();
		this.setno = setno;
		this.userno = userno;
		this.productno = productno;
		this.auctionno = auctionno;
		this.productname = productname;
		this.image = image;
		this.endTime = endTime;
		this.productPriceNow = productPriceNow;
	}

	public Integer getSetno() {
		return setno;
	}

	public void setSetno(Integer setno) {
		this.setno = setno;
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
	

	public Integer getAuctionno() {
		return auctionno;
	}

	public void setAuctionno(Integer auctionno) {
		this.auctionno = auctionno;
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

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public int getProductPriceNow() {
		return productPriceNow;
	}

	public void setProductPriceNow(int productPriceNow) {
		this.productPriceNow = productPriceNow;
	}

}
