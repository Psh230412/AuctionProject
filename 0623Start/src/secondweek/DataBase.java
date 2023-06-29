package secondweek;

public class DataBase {
	private User currentUser;
	private Product particiapteProduct;
	private int registIndex = 0;
	private int bidIndex = 0;

	public DataBase() {
		currentUser = new User(1, "abc", "abc", 2010); // 테스트용 User 계정
//		currentUser = null;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public Product getProduct() {
		return particiapteProduct;
	}

	public void setProduct(Product product) {
		this.particiapteProduct = product;
	}

	public Product getParticiapteProduct() {
		return particiapteProduct;
	}

	public void setParticiapteProduct(Product particiapteProduct) {
		this.particiapteProduct = particiapteProduct;
	}

	public int getRegistIndex() {
		return registIndex;
	}

	public void setRegistIndex(int registIndex) {
		this.registIndex = registIndex;
	}

	public int getBidIndex() {
		return bidIndex;
	}

	public void setBidIndex(int bidIndex) {
		this.bidIndex = bidIndex;
	}


}
