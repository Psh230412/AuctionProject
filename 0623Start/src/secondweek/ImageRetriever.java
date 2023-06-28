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
	
	static List<ImageIcon> list = new ArrayList<>();
	
	
	public static void setImage(byte[] imageBytes) {
		ImageIcon imageIcon = new ImageIcon(imageBytes);
		
		Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
		
	
		list.add(new ImageIcon(image));
//		return new ImageIcon(image);
	}

	public static void retrieveImage(Connection conn) throws SQLException {
		list.clear();
		
//		String sql = "SELECT image FROM product WHERE productno = ?";
		String sql = "SELECT auction.starttime,auction.deadline,\r\n" + 
				"			enrollmentinfo.productno,\r\n" + 
				"			product.productname,product.image\r\n" + 
				"FROM auction\r\n" + 
				"INNER JOIN enrollmentinfo\r\n" + 
				"ON auction.setno = enrollmentinfo.setno\r\n" + 
				"INNER JOIN product\r\n" + 
				"ON enrollmentinfo.productno = product.productno\r\n" + 
				"WHERE deadline>current_time()\r\n" + 
				"ORDER BY deadline-starttime;";
//		int id = index; // 이미지를 가져올 레코드의 ID

		try (PreparedStatement statement = conn.prepareStatement(sql)) {
//			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			
			
			while(resultSet.next()) {
				Blob imageBlob = resultSet.getBlob("image");
				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				
				
				setImage(imageBytes);
			}
			
			
			
//			if (resultSet.next()) {
//				// 이미지 데이터 가져오기
//				Blob imageBlob = resultSet.getBlob("image");
//				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
//				
//				ImageIcon imageIcon = setImage(imageBytes);
//				
//				return imageIcon;
//
//			} else {
//				
//				return null;
//			}
		}
	}
}

