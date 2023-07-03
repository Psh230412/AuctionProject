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

public class ListRepository {

	// 이미지파일 변환(김명완 작성본 복사)
	public ImageIcon setImage(byte[] imageBytes) {
		ImageIcon imageIcon = new ImageIcon(imageBytes);
		Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);

		return new ImageIcon(image);
	}

	// 등록 리스트
	public List<EnrollParticipate> getEnrollment(int userno) {
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		List<EnrollParticipate> list = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT B.setno, A.userno, C.productno, C.productname, C.image, D.deadline, D.finalprice, D.auctionno FROM user A\r\n"
					+ "INNER JOIN enrollmentinfo B ON A.userno = B.userno\r\n"
					+ "INNER JOIN product C ON B.productno = C.productno \r\n"
					+ "INNER JOIN copy_auction D ON B.setno = D.setno\r\n" + "WHERE A.userno = ?\r\n"
					+ "AND D.deadline > current_timestamp()\r\n" + "ORDER BY D.deadline";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userno);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Integer setnoParse = rs.getObject("setno", Integer.class);
				Integer usernoParse = rs.getObject("userno", Integer.class);
				Integer productnoParse = rs.getObject("productno", Integer.class);
				Integer auctionnoParse = rs.getObject("auctionno", Integer.class);
				String productname = rs.getString("productname");
				Blob imageBlob = rs.getBlob("image");

				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				ImageIcon imageIcon = setImage(imageBytes);

				int finalprice = rs.getInt("finalprice");

				LocalDateTime endTime = rs.getObject("deadline", LocalDateTime.class);

				list.add(new EnrollParticipate(setnoParse, usernoParse, productnoParse, auctionnoParse, productname, imageIcon, endTime,
						finalprice));
			}
			String sql2 = "SELECT B.setno, A.userno, C.productno, C.productname, C.image, D.deadline, D.finalprice, D.auctionno FROM user A\n"
					+ "INNER JOIN enrollmentinfo B ON A.userno = B.userno\n"
					+ "INNER JOIN product C ON B.productno = C.productno\n"
					+ "INNER JOIN copy_auction D ON B.setno = D.setno\n" + "WHERE A.userno = ?\n"
					+ "AND TIMESTAMPDIFF(SECOND, D.deadline, current_timestamp()) > 0\n"
					+ "AND DATEDIFF(D.deadline, current_timestamp()) >= -7\n" + "ORDER BY D.deadline DESC;";

			stmt2 = conn.prepareStatement(sql2);
			stmt2.setInt(1, userno);
			rs2 = stmt2.executeQuery();

			while (rs2.next()) {
				Integer setnoParse = rs2.getObject("setno", Integer.class);
				Integer usernoParse = rs2.getObject("userno", Integer.class);
				Integer productnoParse = rs2.getObject("productno", Integer.class);
				Integer auctionnoParse = rs2.getObject("auctionno", Integer.class);
				String productname = rs2.getString("productname");
				Blob imageBlob = rs2.getBlob("image");

				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				ImageIcon imageIcon = setImage(imageBytes);

				int finalprice = rs2.getInt("finalprice");

				LocalDateTime endTime = rs2.getObject("deadline", LocalDateTime.class);

				list.add(new EnrollParticipate(setnoParse, usernoParse, productnoParse, auctionnoParse, productname, imageIcon, endTime,
						finalprice));
			}

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs2);
			DBUtil.close(stmt2);
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
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		List<EnrollParticipate> list = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT C.setno, A.userno, E.productno, E.productname, E.image, C.finalprice, C.deadline, C.auctionno\n"
					+ "FROM user A\n" 
					+ "INNER JOIN (\n" 
					+ "    SELECT p.*\n" 
					+ "    FROM participate p\n"
					+ "    INNER JOIN (\n" 
					+ "        SELECT auctionno, MAX(participatetime) AS max_time\n"
					+ "        FROM participate\n" 
					+ "        GROUP BY auctionno\n"
					+ "    ) AS PP ON p.auctionno = PP.auctionno AND p.participatetime = PP.max_time\n"
					+ ") AS B ON A.userno = B.userno\n" 
					+ "INNER JOIN copy_auction C ON B.auctionno = C.auctionno\n"
					+ "INNER JOIN enrollmentinfo D ON C.setno = D.setno\n"
					+ "INNER JOIN product E ON D.productno = E.productno\n" 
					+ "WHERE A.userno = ?\n"
					+ "AND C.deadline > CURRENT_TIME()\n" 
					+ "ORDER BY C.deadline;";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userno);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Integer setnoParse = rs.getObject("setno", Integer.class);
				Integer usernoParse = rs.getObject("userno", Integer.class);
				Integer productnoParse = rs.getObject("productno", Integer.class);
				Integer auctionnoParse = rs.getObject("auctionno", Integer.class);
				String productname = rs.getString("productname");
				Blob imageBlob = rs.getBlob("image");

				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				ImageIcon imageIcon = setImage(imageBytes);

				int finalprice = rs.getInt("finalprice");

				LocalDateTime endTime = rs.getObject("deadline", LocalDateTime.class);

				list.add(new EnrollParticipate(setnoParse, usernoParse, productnoParse, auctionnoParse, productname, imageIcon, endTime,
						finalprice));
			}
			String sql2 = "SELECT C.setno, A.userno, E.productno, E.productname, E.image, C.finalprice, C.deadline, C.auctionno\n"
					+ "FROM user A\n" + "INNER JOIN (\n" + "    SELECT p.*\n" + "    FROM participate p\n"
					+ "    INNER JOIN (\n" + "        SELECT auctionno, MAX(participatetime) AS max_time\n"
					+ "        FROM participate\n" + "        GROUP BY auctionno\n"
					+ "    ) AS PP ON p.auctionno = PP.auctionno AND p.participatetime = PP.max_time\n"
					+ ") AS B ON A.userno = B.userno\n" + "INNER JOIN copy_auction C ON B.auctionno = C.auctionno\n"
					+ "INNER JOIN enrollmentinfo D ON C.setno = D.setno\n"
					+ "INNER JOIN product E ON D.productno = E.productno\n" + "WHERE A.userno = ?\n"
					+ "AND TIMESTAMPDIFF(SECOND, C.deadline, current_timestamp()) > 0\n"
					+ "AND DATEDIFF(C.deadline, current_timestamp()) >= -7\n" + "ORDER BY C.deadline DESC;";

			stmt2 = conn.prepareStatement(sql2);
			stmt2.setInt(1, userno);
			rs2 = stmt2.executeQuery();

			while (rs2.next()) {
				Integer setnoParse = rs2.getObject("setno", Integer.class);
				Integer usernoParse = rs2.getObject("userno", Integer.class);
				Integer productnoParse = rs2.getObject("productno", Integer.class);
				Integer auctionnoParse = rs2.getObject("auctionno", Integer.class);
				String productname = rs2.getString("productname");
				Blob imageBlob = rs2.getBlob("image");

				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				ImageIcon imageIcon = setImage(imageBytes);

				int finalprice = rs2.getInt("finalprice");

				LocalDateTime endTime = rs2.getObject("deadline", LocalDateTime.class);

				list.add(new EnrollParticipate(setnoParse, usernoParse, productnoParse, auctionnoParse, productname, imageIcon, endTime,
						finalprice));
			}

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs2);
			DBUtil.close(stmt2);
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return list;
	}

	// 낙찰/유찰 리스트
	public List<Integer> getIsBid() {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Integer> list = new ArrayList<Integer>();
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT auctionno FROM participate";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Integer auctionnoParse = rs.getObject("auctionno", Integer.class);

				list.add(auctionnoParse);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return list;
	}
}
