package secondweek;

import java.awt.Image;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;

public class ImageRetriever {
	
	
	public static ImageIcon setImage(byte[] imageBytes) {
		ImageIcon imageIcon = new ImageIcon(imageBytes);
		
		Image image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		
		return new ImageIcon(image);
	}

	public static ImageIcon retrieveImage(Connection conn,int index) throws SQLException {
		
		
		String sql = "SELECT image FROM product WHERE productno = ?";
//		String sql = "SELECT image FROM product order by ";
		int id = index; // 이미지를 가져올 레코드의 ID

		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			
			if (resultSet.next()) {
				// 이미지 데이터 가져오기
				Blob imageBlob = resultSet.getBlob("image");
				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				
				ImageIcon imageIcon = setImage(imageBytes);
				
				return imageIcon;

			} else {
				
				return null;
			}
		}
	}
}

