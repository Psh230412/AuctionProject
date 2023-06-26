package database;

import objects.User;

public class DataBase {
	private User currentUser;
//	private List<Auction> auctions;
//	private List<Enrollment> enrolls;
//	private List<Participate> participates;
//	private List<Bid> bids;

//	private List<JPanel> auctionPnls;

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

}
