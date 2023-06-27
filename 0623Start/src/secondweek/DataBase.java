package secondweek;

public class DataBase {
	private User currentUser;
	private Product particiapteProduct;

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
}
