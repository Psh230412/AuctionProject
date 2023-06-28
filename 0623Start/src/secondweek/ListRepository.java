package secondweek;

import java.awt.Image;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import dbutil.DBUtil;

public class ListRepository{
	
	// 이미지파일 변환(김명완 작성본 복사)
	public ImageIcon setImage(byte[] imageBytes) {
		ImageIcon imageIcon = new ImageIcon(imageBytes);

		Image image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);

		return new ImageIcon(image);
	}

	// 등록 리스트
	public List<EnrollParticipate> getEnrollment(int userno) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EnrollParticipate> list = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT B.setno, A.userno, C.productno, C.productname, C.image, D.deadline, D.finalprice FROM user A\r\n" + 
					"INNER JOIN enrollmentinfo B ON A.userno = B.userno\r\n" + 
					"INNER JOIN product C ON B.productno = C.productno \r\n" + 
					"INNER JOIN copy_auction D ON B.setno = D.setno\r\n" + 
					"WHERE A.userno = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userno);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Integer setnoParse = rs.getObject("setno", Integer.class);
				Integer usernoParse = rs.getObject("userno", Integer.class);
				Integer productnoParse = rs.getObject("productno", Integer.class);
				String productname = rs.getString("productname");
				Blob imageBlob = rs.getBlob("image");

				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				ImageIcon imageIcon = setImage(imageBytes);

				int finalprice = rs.getInt("finalprice");

				LocalDateTime endTime = rs.getObject("deadline", LocalDateTime.class);

				list.add(new EnrollParticipate(setnoParse, usernoParse, productnoParse, productname, imageIcon, endTime,
						finalprice));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return null;
	}

	// 입찰 리스트
	public List<EnrollParticipate> getParticipateList(int userno) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EnrollParticipate> list = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT C.setno, A.userno, E.productno, E.productname, E.image, C.finalprice, C.deadline FROM user A\r\n" + 
					"INNER JOIN participate B ON A.userno = B.userno \r\n" + 
					"INNER JOIN copy_auction C ON B.auctionno = C.auctionno \r\n" + 
					"INNER JOIN enrollmentinfo D ON C.setno = D.setno \r\n" + 
					"INNER JOIN product E ON D.productno = E.productno \r\n" + 
					"WHERE A.userno = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userno);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Integer setnoParse = rs.getObject("setno", Integer.class);
				Integer usernoParse = rs.getObject("userno", Integer.class);
				Integer productnoParse = rs.getObject("productno", Integer.class);
				String productname = rs.getString("productname");
				Blob imageBlob = rs.getBlob("image");

				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				ImageIcon imageIcon = setImage(imageBytes);

				int finalprice = rs.getInt("finalprice");

				LocalDateTime endTime = rs.getObject("deadline", LocalDateTime.class);

				list.add(new EnrollParticipate(setnoParse, usernoParse, productnoParse, productname, imageIcon, endTime,
						finalprice));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return null;
	}

	// 낙찰/유찰 리스트
	public List<Bidinfo> getBidinfoList(int userno) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Bidinfo> list = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT B.successbidno, A.userno, C.productno, C.productname, C.image, D.finalprice, B.isbid FROM user A\r\n" + 
					"INNER JOIN successbidinfo B ON A.userno = B.userno\r\n" + 
					"INNER JOIN product C ON B.productno = C.productno\r\n" + 
					"INNER JOIN copy_auction D ON B.auctioncopyno = D.auctionno\r\n" + 
					"WHERE A.userno = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userno);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Integer bidnoParse = rs.getObject("successbidno", Integer.class);
				Integer usernoParse = rs.getObject("userno", Integer.class);
				Integer productnoParse = rs.getObject("productno", Integer.class);
				String productname = rs.getString("productname");
				Blob imageBlob = rs.getBlob("image");
				
				byte[] imageBytes = imageBlob.getBytes(1, (int)imageBlob.length());
				ImageIcon imageIcon = setImage(imageBytes);
				
				int finalprice = rs.getInt("finalprice");
				
				int bidint = rs.getInt("isbid");
				boolean bidParse = (bidint == 1);
				
				list.add(new Bidinfo(bidnoParse, usernoParse, productnoParse, productname, imageIcon, finalprice, bidParse));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return null;
	}
}
