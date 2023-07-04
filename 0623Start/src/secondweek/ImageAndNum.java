package secondweek;

import javax.swing.ImageIcon;

public class ImageAndNum {
	private ImageIcon imageicon;
	private int productno;
	
	public ImageAndNum(ImageIcon imageicon, int productno) {
		super();
		this.imageicon = imageicon;
		this.productno = productno;
	}

	public ImageIcon getImageicon() {
		return imageicon;
	}

	public void setImageicon(ImageIcon imageicon) {
		this.imageicon = imageicon;
	}

	public int getProductno() {
		return productno;
	}

	public void setProductno(int productno) {
		this.productno = productno;
	}

	@Override
	public String toString() {
		return "ImageAndNum [imageicon=" + imageicon + ", productno=" + productno + "]";
	}
}
