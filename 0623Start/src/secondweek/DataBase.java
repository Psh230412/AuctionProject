package secondweek;

public class DataBase {
	private User currentUser;
	private Product particiapteProduct;
	private int index = 0;
	private int indexPar = 0;
	private int indexMain = 0;
	private boolean checkBtn = false;
	private String searchText;

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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndexPar() {
		return indexPar;
	}

	public void setIndexPar(int indexPar) {
		this.indexPar = indexPar;
	}

	public int getIndexMain() {
		return indexMain;
	}

	public void setIndexMain(int indexMain) {
		this.indexMain = indexMain;
	}

	public boolean isCheckBtn() {
		return checkBtn;
	}

	public void setCheckBtn(boolean checkBtn) {
		this.checkBtn = checkBtn;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	
}
