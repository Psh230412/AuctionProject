package secondweek;

import java.awt.Image;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class ImageRetriever {

//	static List<ImageIcon> list = new ArrayList<>();

	static List<ImageAndNum> llist = new ArrayList<>();
	static List<ImageAndNum> llistForDetail = new ArrayList<>();
	static List<Auction> listForSearch = new ArrayList<>();

	public static void setImageForSearch(byte[] imageBytes,String productname,int finalprice,Timestamp deadline) {
		
		ImageIcon imageIcon = new ImageIcon(imageBytes);

		Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);

		listForSearch.add(new Auction(new ImageIcon(image), productname, finalprice, deadline));
	}

	public static void setImageForDetail(byte[] imageBytes, int productno) {
		ImageIcon imageIcon = new ImageIcon(imageBytes);

		Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);

		llistForDetail.add(new ImageAndNum(new ImageIcon(image), productno));
	}

	public static void setImage(byte[] imageBytes, int productno) {
		ImageIcon imageIcon = new ImageIcon(imageBytes);

		Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);

//		list.add(new ImageIcon(image));
		llist.add(new ImageAndNum(new ImageIcon(image), productno));
	}

//	추가 할때는 auction 테이블의 주요키와 같은 정수를 키로 가지는 해쉬맵에다가 저장
//	삭제 될떄는 
	public static void retrieveImage(Connection conn) throws SQLException {
//		list.clear();
		llist.clear();

		String sql = "SELECT auction.starttime,auction.deadline,\r\n" + "			enrollmentinfo.productno,\r\n"
				+ "			product.productname,product.image\r\n" + "FROM auction\r\n"
				+ "INNER JOIN enrollmentinfo\r\n" + "ON auction.setno = enrollmentinfo.setno\r\n"
				+ "INNER JOIN product\r\n" + "ON enrollmentinfo.productno = product.productno\r\n"
				+ "WHERE deadline>current_time()\r\n" + "ORDER BY deadline-current_time();";

		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Blob imageBlob = resultSet.getBlob("image");
				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				int productno = resultSet.getInt("productno");

				setImage(imageBytes, productno);
				setImageForDetail(imageBytes, productno);
			}

		}
	}
}
