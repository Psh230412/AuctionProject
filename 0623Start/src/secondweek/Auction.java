package secondweek;

import java.sql.Timestamp;

import javax.swing.ImageIcon;

public class Auction {
	private int index;
	private int auctionNo;
	private ImageIcon imageIcon;
	private String productname;
	private int finalprice;
	private Timestamp deadline;

	public Auction(ImageIcon imageIcon, String productname, int finalprice, Timestamp deadline) {
		super();
		this.imageIcon = imageIcon;
		this.productname = productname;
		this.finalprice = finalprice;
		this.deadline = deadline;
	}

	public Auction(int index, int auctionNo) {
		super();
		this.index = index;
		this.auctionNo = auctionNo;
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public int getFinalprice() {
		return finalprice;
	}

	public void setFinalprice(int finalprice) {
		this.finalprice = finalprice;
	}

	public Timestamp getDeadline() {
		return deadline;
	}

	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		return "Auction [index=" + index + ", auctionNo=" + auctionNo + "]";
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getAuctionNo() {
		return auctionNo;
	}

	public void setAuctionNo(int auctionNo) {
		this.auctionNo = auctionNo;
	}
}
