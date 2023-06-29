package secondweek;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.swing.ImageIcon;

public class Auction {
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
		return "Auction [imageIcon=" + imageIcon + ", productname=" + productname + ", finalprice=" + finalprice
				+ ", deadline=" + deadline + "]";
	}
	
	
	
	
	
}
