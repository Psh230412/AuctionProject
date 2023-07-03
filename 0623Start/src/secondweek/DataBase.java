package secondweek;

public class DataBase {
	private User currentUser;
	private Product particiapteProduct;
	private int index = 0;
	private int indexPar = 0;
	private int indexMain = 0;
	private int indexMainSearch = 0;
	private boolean checkBtn = false;
	private String searchText = null;

	private String categoryText;
	private String auctionRadioText;
	private String priceFrontText;
	private String priceBackText;

	public DataBase() {
//		currentUser = new User(1, "abc", "abc", 2010); // 테스트용 User 계정
		currentUser = null;
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

	public int getIndexMainSearch() {
		return indexMainSearch;
	}

	public void setIndexMainSearch(int indexMainSearch) {
		this.indexMainSearch = indexMainSearch;
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

	public String getCategoryText() {
		return categoryText;
	}

	public void setCategoryText(String categoryText) {
		this.categoryText = categoryText;
	}

	public String getAuctionRadioText() {
		return auctionRadioText;
	}

	public void setAuctionRadioText(String auctionRadioText) {
		this.auctionRadioText = auctionRadioText;
	}

	public String getPriceFrontText() {
		return priceFrontText;
	}

	public void setPriceFrontText(String priceFrontText) {
		this.priceFrontText = priceFrontText;
	}

	public String getPriceBackText() {
		return priceBackText;
	}

	public void setPriceBackText(String priceBackText) {
		this.priceBackText = priceBackText;
	}
	
	

}
