package secondweek;

import java.awt.Image;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class ImageRetriever {
	
	
	
//	static List<ImageIcon> list = new ArrayList<>();
	
	static List<ImageAndNum> llist = new ArrayList<>();
	static List<ImageAndNum> llistForDetail = new ArrayList<>();
	
	public static void setImageForDetail(byte[] imageBytes,int productno) {
		ImageIcon imageIcon = new ImageIcon(imageBytes);
		
		Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
		
		llistForDetail.add(new ImageAndNum(new ImageIcon(image), productno));
	}
	
	
	public static void setImage(byte[] imageBytes,int productno) {
		ImageIcon imageIcon = new ImageIcon(imageBytes);
		
		Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
		

//		list.add(new ImageIcon(image));
		llist.add(new ImageAndNum(new ImageIcon(image), productno));
	}

	public static void retrieveImage(Connection conn) throws SQLException {
//		list.clear();
		llist.clear();
		
		String sql = "SELECT auction.starttime,auction.deadline,\r\n" + 
				"			enrollmentinfo.productno,\r\n" + 
				"			product.productname,product.image\r\n" + 
				"FROM auction\r\n" + 
				"INNER JOIN enrollmentinfo\r\n" + 
				"ON auction.setno = enrollmentinfo.setno\r\n" + 
				"INNER JOIN product\r\n" + 
				"ON enrollmentinfo.productno = product.productno\r\n" + 
				"WHERE deadline>current_time()\r\n" + 
				"ORDER BY deadline-current_time();";

		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Blob imageBlob = resultSet.getBlob("image");
				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				int productno = resultSet.getInt("productno");
				
				setImage(imageBytes,productno);
				setImageForDetail(imageBytes,productno);
			}
			
		}
	}
}

