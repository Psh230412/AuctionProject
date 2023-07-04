package secondweek;

import java.awt.Image;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;

import dbutil.DBUtil;
// key는 auction테이블의 주요키와 같아야한다.
// 스케줄러 안에는 auction테이블의 주요키와 map의 key를 비교하는 코드

public class Cache {
//	DetailFrame에서 쓰임
	public static HashMap<Integer, List<byte[]>> cacheMap = new HashMap<>();
//	AuctionFrame 기본창에서 쓰임
	public static HashMap<Integer, Product> ProductCacheMap = new HashMap<>();
//	AuctionFrame 검색창에서 쓰임
//	검색을 했을 때 만들어진다.
	public static HashMap<Integer, Product> ProductSearchCacheMap = new HashMap<>();
//	등록물품 MypageFrame에서 쓰임
	public static HashMap<Integer, EnrollParticipate> enrollCacheMap = new HashMap<>();
//	등록물품 MypageFrame에서 쓰임
	public static HashMap<Integer, EnrollParticipate> participateCacheMap = new HashMap<>();

	public static ImageIcon setImage(byte[] imageBytes) {
		ImageIcon imageIcon = new ImageIcon(imageBytes);
		Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);

		return new ImageIcon(image);
	}
	public static void isProductnoparticipateCacheMap (int userno, Connection conn) throws SQLException {
		Set<Integer>  participateCacheset = participateCacheMap.keySet();
		// 등록 테이블의 productno가 담기는 리스트
		List<Integer> participateList = new ArrayList();

//		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmtRevise = null;
		PreparedStatement stmtRevise2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rsForstmtRevise = null;
		ResultSet rsForstmtRevise2 = null;

		try {
//			conn = DBUtil.getConnection();

//		 	productno를 가져오기 위한 테이블 
			stmt = conn.prepareStatement(
					"SELECT C.setno, A.userno, E.productno, E.productname, C.finalprice, C.deadline, C.auctionno\r\n" + 
					"FROM user A INNER JOIN ( SELECT p.*   FROM participate p\r\n" + 
					"INNER JOIN ( SELECT auctionno, MAX(participatetime) AS max_time\r\n" + 
					"FROM participate GROUP BY auctionno\r\n" + 
					") AS PP ON p.auctionno = PP.auctionno AND p.participatetime = PP.max_time\r\n" + 
					") AS B ON A.userno = B.userno INNER JOIN copy_auction C ON B.auctionno = C.auctionno\r\n" + 
					"INNER JOIN enrollmentinfo D ON C.setno = D.setno\r\n" + 
					"INNER JOIN product E ON D.productno = E.productno WHERE A.userno = ? \r\n" + 
					"AND C.deadline > CURRENT_TIME() ORDER BY C.deadline;");

			stmt2 = conn.prepareStatement(
					"SELECT C.setno, A.userno, E.productno, E.productname, C.finalprice, C.deadline, C.auctionno\r\n" + 
					"FROM user A INNER JOIN (SELECT p.* FROM participate p\r\n" + 
					"INNER JOIN ( SELECT auctionno, MAX(participatetime) AS max_time\r\n" + 
					"FROM participate GROUP BY auctionno\r\n" + 
					") AS PP ON p.auctionno = PP.auctionno AND p.participatetime = PP.max_time\r\n" + 
					") AS B ON A.userno = B.userno INNER JOIN copy_auction C ON B.auctionno = C.auctionno\r\n" + 
					"INNER JOIN enrollmentinfo D ON C.setno = D.setno\r\n" + 
					"INNER JOIN product E ON D.productno = E.productno WHERE A.userno =  ? \r\n" + 
					"AND TIMESTAMPDIFF(SECOND, C.deadline, current_timestamp()) > 0\r\n" + 
					"AND DATEDIFF(C.deadline, current_timestamp()) >= -7 ORDER BY C.deadline DESC;");

			stmt.setInt(1, userno);
			stmt2.setInt(1, userno);

			rs = stmt.executeQuery();
			rs2 = stmt2.executeQuery();

			while (rs.next()) {
				int productno = rs.getInt("productno");
				participateList.add(productno);
			}
			while (rs2.next()) {
				int productno = rs2.getInt("productno");
				participateList.add(productno);
			}

//			테이블에서 삭제가 됏는데 캐시에 반영이 안됐다는 뜻이다.
			if (participateCacheset.size() > participateList.size()) {

				List<Integer> llist = new ArrayList();

				Iterator<Integer> iterator = participateCacheset.iterator();
				while (iterator.hasNext()) {
					Integer item = iterator.next();
					if (!participateList.contains(item)) {
						llist.add(item);
					}

				}
				for (int i = 0; i < llist.size(); i++) {
					participateCacheMap.remove(llist.get(i));
				}

				return;

			}
//			테이블에 추가가 됐는데 캐시에 반영이 안됐다는 뜻이다.
			if (participateCacheset.size() < participateList.size()) {

				stmtRevise = conn.prepareStatement(
						"SELECT C.setno, A.userno, E.productno, E.productname,  C.finalprice, C.deadline, C.auctionno\r\n" + 
						"FROM user A INNER JOIN ( SELECT p.*   FROM participate p\r\n" + 
						"INNER JOIN ( SELECT auctionno, MAX(participatetime) AS max_time\r\n" + 
						"FROM participate GROUP BY auctionno\r\n" + 
						") AS PP ON p.auctionno = PP.auctionno AND p.participatetime = PP.max_time\r\n" + 
						") AS B ON A.userno = B.userno INNER JOIN copy_auction C ON B.auctionno = C.auctionno\r\n" + 
						"INNER JOIN enrollmentinfo D ON C.setno = D.setno\r\n" + 
						"INNER JOIN product E ON D.productno = E.productno WHERE A.userno = ? AND E.productno = ?\r\n" + 
						"AND C.deadline > CURRENT_TIME() ORDER BY C.deadline;");
				stmtRevise2 = conn.prepareStatement(
						"SELECT C.setno, A.userno, E.productno, E.productname,C.finalprice, C.deadline, C.auctionno\r\n" + 
						"FROM user A INNER JOIN (SELECT p.* FROM participate p\r\n" + 
						"INNER JOIN ( SELECT auctionno, MAX(participatetime) AS max_time\r\n" + 
						"FROM participate GROUP BY auctionno\r\n" + 
						") AS PP ON p.auctionno = PP.auctionno AND p.participatetime = PP.max_time\r\n" + 
						") AS B ON A.userno = B.userno INNER JOIN copy_auction C ON B.auctionno = C.auctionno\r\n" + 
						"INNER JOIN enrollmentinfo D ON C.setno = D.setno\r\n" + 
						"INNER JOIN product E ON D.productno = E.productno WHERE A.userno = ? AND E.productno = ?\r\n" + 
						"AND TIMESTAMPDIFF(SECOND, C.deadline, current_timestamp()) > 0\r\n" + 
						"AND DATEDIFF(C.deadline, current_timestamp()) >= -7 ORDER BY C.deadline DESC;");

				Iterator<Integer> iterator = participateList.iterator();
				while (iterator.hasNext()) {
					Integer item = iterator.next();
					if (!participateCacheset.contains(item)) {
						stmtRevise.setInt(1, userno);
						stmtRevise.setInt(2, item);

						stmtRevise2.setInt(1, userno);
						stmtRevise2.setInt(2, item);

						rsForstmtRevise = stmtRevise.executeQuery();
						rsForstmtRevise2 = stmtRevise2.executeQuery();

						while (rsForstmtRevise.next()) {
							Integer setnoParse = rsForstmtRevise.getObject("setno", Integer.class);
							Integer usernoParse = rsForstmtRevise.getObject("userno", Integer.class);
							Integer productnoParse = rsForstmtRevise.getObject("productno", Integer.class);
							Integer auctionnoParse = rsForstmtRevise.getObject("auctionno", Integer.class);
							String productname = rsForstmtRevise.getString("productname");
							Blob imageBlob = rsForstmtRevise.getBlob("image");

							byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
							ImageIcon imageIcon = setImage(imageBytes);

							int finalprice = rsForstmtRevise.getInt("finalprice");

							LocalDateTime endTime = rsForstmtRevise.getObject("deadline", LocalDateTime.class);

							if (!participateCacheMap.containsKey(productnoParse)) {
								participateCacheMap.put(productnoParse, new EnrollParticipate(setnoParse, usernoParse,
										productnoParse, auctionnoParse, productname, imageIcon, endTime, finalprice));

							}

							
						}
						while (rsForstmtRevise2.next()) {
							Integer setnoParse = rsForstmtRevise2.getObject("setno", Integer.class);
							Integer usernoParse = rsForstmtRevise2.getObject("userno", Integer.class);
							Integer productnoParse = rsForstmtRevise2.getObject("productno", Integer.class);
							Integer auctionnoParse = rsForstmtRevise2.getObject("auctionno", Integer.class);
							String productname = rsForstmtRevise2.getString("productname");
							Blob imageBlob = rsForstmtRevise2.getBlob("image");

							byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
							ImageIcon imageIcon = setImage(imageBytes);

							int finalprice = rsForstmtRevise2.getInt("finalprice");

							LocalDateTime endTime = rsForstmtRevise2.getObject("deadline", LocalDateTime.class);

							if (!participateCacheMap.containsKey(productnoParse)) {
								participateCacheMap.put(productnoParse, new EnrollParticipate(setnoParse, usernoParse,
										productnoParse, auctionnoParse, productname, imageIcon, endTime, finalprice));

							}

						}

					}
				}
				return;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rsForstmtRevise2);
			DBUtil.close(rsForstmtRevise);
			DBUtil.close(rs2);
			DBUtil.close(rs);
			DBUtil.close(stmtRevise2);
			DBUtil.close(stmtRevise);
			DBUtil.close(stmt2);
			DBUtil.close(stmt);
//			DBUtil.close(conn);
		}
	}

	public static void isProductnoEnrollCacheMap(int userno, Connection conn) throws SQLException {
		Set<Integer> enrollCacheset = enrollCacheMap.keySet();
		// 등록 테이블의 productno가 담기는 리스트
		List<Integer> enrollList = new ArrayList();

//		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmtRevise = null;
		PreparedStatement stmtRevise2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rsForstmtRevise = null;
		ResultSet rsForstmtRevise2 = null;

		try {
//			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement(
					"SELECT B.setno, A.userno, C.productno, C.productname, D.deadline, D.finalprice, D.auctionno FROM user A\r\n"
							+ "INNER JOIN enrollmentinfo B ON A.userno = B.userno\r\n"
							+ "INNER JOIN product C ON B.productno = C.productno \r\n"
							+ "INNER JOIN copy_auction D ON B.setno = D.setno WHERE A.userno = ? \r\n"
							+ "AND D.deadline > current_timestamp() ORDER BY D.deadline;");

			stmt2 = conn.prepareStatement(
					"SELECT B.setno, A.userno, C.productno, C.productname, D.deadline, D.finalprice, D.auctionno FROM user A\r\n"
							+ "INNER JOIN enrollmentinfo B ON A.userno = B.userno\r\n"
							+ "INNER JOIN product C ON B.productno = C.productno\r\n"
							+ "INNER JOIN copy_auction D ON B.setno = D.setno WHERE A.userno = ? \r\n"
							+ "AND TIMESTAMPDIFF(SECOND, D.deadline, current_timestamp()) > 0\r\n"
							+ "AND DATEDIFF(D.deadline, current_timestamp()) >= -7 ORDER BY D.deadline DESC;");

			stmt.setInt(1, userno);
			stmt2.setInt(1, userno);

			rs = stmt.executeQuery();
			rs2 = stmt2.executeQuery();

			while (rs.next()) {
				int productno = rs.getInt("productno");
				enrollList.add(productno);
			}
			while (rs2.next()) {
				int productno = rs2.getInt("productno");
				enrollList.add(productno);
			}

//			테이블에서 삭제가 됏는데 캐시에 반영이 안됐다는 뜻이다.
			if (enrollCacheset.size() > enrollList.size()) {

				List<Integer> llist = new ArrayList();

				Iterator<Integer> iterator = enrollCacheset.iterator();
				while (iterator.hasNext()) {
					Integer item = iterator.next();
					if (!enrollList.contains(item)) {
						llist.add(item);
					}

				}
				for (int i = 0; i < llist.size(); i++) {
					enrollCacheMap.remove(llist.get(i));
				}

				return;

			}
//			테이블에 추가가 됐는데 캐시에 반영이 안됐다는 뜻이다.
			if (enrollCacheset.size() < enrollList.size()) {

				stmtRevise = conn.prepareStatement(
						"SELECT B.setno, A.userno, C.productno, C.productname, C.image,D.deadline, D.finalprice, D.auctionno FROM user A\r\n"
								+ "INNER JOIN enrollmentinfo B ON A.userno = B.userno\r\n"
								+ "INNER JOIN product C ON B.productno = C.productno \r\n"
								+ "INNER JOIN copy_auction D ON B.setno = D.setno WHERE A.userno = ? AND  C.productno=? \r\n"
								+ "AND D.deadline > current_timestamp() ORDER BY D.deadline;");
				stmtRevise2 = conn.prepareStatement(
						"SELECT B.setno, A.userno, C.productno, C.productname,C.image, D.deadline, D.finalprice, D.auctionno FROM user A\r\n"
								+ "INNER JOIN enrollmentinfo B ON A.userno = B.userno\r\n"
								+ "INNER JOIN product C ON B.productno = C.productno\r\n"
								+ "INNER JOIN copy_auction D ON B.setno = D.setno WHERE A.userno = ? AND C.productno = ? \r\n"
								+ "AND TIMESTAMPDIFF(SECOND, D.deadline, current_timestamp()) > 0\r\n"
								+ "AND DATEDIFF(D.deadline, current_timestamp()) >= -7 ORDER BY D.deadline DESC;");

				Iterator<Integer> iterator = enrollList.iterator();
				while (iterator.hasNext()) {
					Integer item = iterator.next();
					if (!enrollCacheset.contains(item)) {
						stmtRevise.setInt(1, userno);
						stmtRevise.setInt(2, item);

						stmtRevise2.setInt(1, userno);
						stmtRevise2.setInt(2, item);

						rsForstmtRevise = stmtRevise.executeQuery();
						rsForstmtRevise2 = stmtRevise2.executeQuery();

						while (rsForstmtRevise.next()) {
							Integer setnoParse = rsForstmtRevise.getObject("setno", Integer.class);
							Integer usernoParse = rsForstmtRevise.getObject("userno", Integer.class);
							Integer productnoParse = rsForstmtRevise.getObject("productno", Integer.class);
							Integer auctionnoParse = rsForstmtRevise.getObject("auctionno", Integer.class);
							String productname = rsForstmtRevise.getString("productname");
							Blob imageBlob = rsForstmtRevise.getBlob("image");
							int finalprice = rsForstmtRevise.getInt("finalprice");

							byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
							ImageIcon imageIcon = setImage(imageBytes);

							LocalDateTime endTime = rsForstmtRevise.getObject("deadline", LocalDateTime.class);

							if (!enrollCacheMap.containsKey(productnoParse)) {

//							key를 productnoParse로
								enrollCacheMap.put(productnoParse, new EnrollParticipate(setnoParse, usernoParse, productnoParse,
										auctionnoParse, productname, imageIcon, endTime, finalprice));

							}

							
						}
						while (rsForstmtRevise2.next()) {
							Integer setnoParse = rsForstmtRevise2.getObject("setno", Integer.class);
							Integer usernoParse = rsForstmtRevise2.getObject("userno", Integer.class);
							Integer productnoParse = rsForstmtRevise2.getObject("productno", Integer.class);
							Integer auctionnoParse = rsForstmtRevise2.getObject("auctionno", Integer.class);
							String productname = rsForstmtRevise2.getString("productname");
							Blob imageBlob = rsForstmtRevise2.getBlob("image");

							byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
							ImageIcon imageIcon = setImage(imageBytes);

							int finalprice = rsForstmtRevise2.getInt("finalprice");

							LocalDateTime endTime = rsForstmtRevise2.getObject("deadline", LocalDateTime.class);

							if (enrollCacheMap.containsKey(productnoParse)) {
								enrollCacheMap.put(productnoParse, new EnrollParticipate(setnoParse, usernoParse, productnoParse,
										auctionnoParse, productname, imageIcon, endTime, finalprice));

							}

						}

					}
				}
				return;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rsForstmtRevise2);
			DBUtil.close(rsForstmtRevise);
			DBUtil.close(rs2);
			DBUtil.close(rs);
			DBUtil.close(stmtRevise2);
			DBUtil.close(stmtRevise);
			DBUtil.close(stmt2);
			DBUtil.close(stmt);
//			DBUtil.close(conn);
		}
	}

//	mypage 입찰물품에 전부다 집어넣는 메서드
//	AuctionFrame 클래스에서 Mypage버튼 누르면 호출 
//	or LoginFrame 생성자에서 호출 
	public static void putAllParticipateMap(int userno, Connection conn) throws SQLException {
//		Connection conn = null;

		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
//			conn = DBUtil.getConnection();
			String sql = "SELECT C.setno, A.userno, E.productno, E.productname, E.image, C.finalprice, C.deadline, C.auctionno\n"
					+ "FROM user A\n" + "INNER JOIN (\n" + "    SELECT p.*\n" + "    FROM participate p\n"
					+ "    INNER JOIN (\n" + "        SELECT auctionno, MAX(participatetime) AS max_time\n"
					+ "        FROM participate\n" + "        GROUP BY auctionno\n"
					+ "    ) AS PP ON p.auctionno = PP.auctionno AND p.participatetime = PP.max_time\n"
					+ ") AS B ON A.userno = B.userno\n" + "INNER JOIN copy_auction C ON B.auctionno = C.auctionno\n"
					+ "INNER JOIN enrollmentinfo D ON C.setno = D.setno\n"
					+ "INNER JOIN product E ON D.productno = E.productno\n" + "WHERE A.userno = ?\n"
					+ "AND C.deadline > CURRENT_TIME()\n" + "ORDER BY C.deadline;";
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

				if (!participateCacheMap.containsKey(productnoParse)) {
					participateCacheMap.put(productnoParse, new EnrollParticipate(setnoParse, usernoParse,
							productnoParse, auctionnoParse, productname, imageIcon, endTime, finalprice));

				}

//				list.add(new EnrollParticipate(setnoParse, usernoParse, productnoParse, auctionnoParse, productname, imageIcon, endTime,
//						finalprice));
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

				if (!participateCacheMap.containsKey(productnoParse)) {
					participateCacheMap.put(productnoParse, new EnrollParticipate(setnoParse, usernoParse,
							productnoParse, auctionnoParse, productname, imageIcon, endTime, finalprice));

				}

//				list.add(new EnrollParticipate(setnoParse, usernoParse, productnoParse, auctionnoParse, productname,
//						imageIcon, endTime, finalprice));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs2);
			DBUtil.close(stmt2);
			DBUtil.close(rs);
			DBUtil.close(stmt);
		}
	}

//	enrollCacheMap에 전부다 집어넣는 메서드 
	public static void putAllenrollCacheMap(int userno, Connection conn) throws SQLException {
//		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		try {
//			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement(
					"SELECT B.setno, A.userno, C.productno, C.productname, C.image, D.deadline, D.finalprice, D.auctionno FROM user A\r\n"
							+ "INNER JOIN enrollmentinfo B ON A.userno = B.userno\r\n"
							+ "INNER JOIN product C ON B.productno = C.productno \r\n"
							+ "INNER JOIN copy_auction D ON B.setno = D.setno WHERE A.userno = ? \r\n"
							+ "AND D.deadline > current_timestamp() ORDER BY D.deadline;");

			stmt.setInt(1, userno);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Integer setnoParse = rs.getObject("setno", Integer.class);
				Integer usernoParse = rs.getObject("userno", Integer.class);
				Integer productnoParse = rs.getObject("productno", Integer.class);
				Integer auctionnoParse = rs.getObject("auctionno", Integer.class);
				String productname = rs.getString("productname");
				Blob imageBlob = rs.getBlob("image");
				int finalprice = rs.getInt("finalprice");

				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				ImageIcon imageIcon = setImage(imageBytes);

				LocalDateTime endTime = rs.getObject("deadline", LocalDateTime.class);

				if (!enrollCacheMap.containsKey(productnoParse)) {

//				key를 productnoParse로
					enrollCacheMap.put(productnoParse, new EnrollParticipate(setnoParse, usernoParse, productnoParse,
							auctionnoParse, productname, imageIcon, endTime, finalprice));

				}

//				list.add(new EnrollParticipate(setnoParse, usernoParse, productnoParse, auctionnoParse, productname, imageIcon, endTime,
//						finalprice));
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

				if (enrollCacheMap.containsKey(productnoParse)) {
					enrollCacheMap.put(productnoParse, new EnrollParticipate(setnoParse, usernoParse, productnoParse,
							auctionnoParse, productname, imageIcon, endTime, finalprice));

				}

//				list.add(new EnrollParticipate(setnoParse, usernoParse, productnoParse, auctionnoParse, productname, imageIcon, endTime,
//						finalprice));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
		}
	}

	public static void isProductnoProductCacheMap(Connection conn) throws SQLException {
		Set<Integer> ProductCacheset = ProductCacheMap.keySet();
		List<Integer> Productlist = new ArrayList();

//		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtRevise = null;
		ResultSet rs = null;
		ResultSet rsforStmtRevise = null;

		try {
//			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement("SELECT * FROM auction AS C RIGHT JOIN (\n"
					+ "SELECT A.productno, A.productname,  A.category, B.setno\n"
					+ "FROM product AS A LEFT JOIN enrollmentinfo AS B ON A.productno = B.productno\n"
					+ ") AS D ON C.setno = D.setno LEFT JOIN (\n" + "SELECT auctionno, COUNT(*) AS popularity FROM (\n"
					+ "SELECT DISTINCT auctionno, userno FROM participate\n"
					+ "WHERE participateprice > 0) AS A GROUP BY auctionno\n"
					+ ") AS E ON C.auctionno = E.auctionno WHERE C.deadline > current_timestamp() \n"
					+ "ORDER BY TIMESTAMPDIFF(SECOND, CURRENT_TIMESTAMP(), C.deadline);");

			rs = stmt.executeQuery();

			while (rs.next()) {
				int productno = rs.getInt("productno");
				Productlist.add(productno);
			}

//			테이블에서 삭제가 됏는데 캐시에 반영이 안됐다는 뜻이다.
			if (ProductCacheset.size() > Productlist.size()) {

				List<Integer> llist = new ArrayList();

				Iterator<Integer> iterator = ProductCacheset.iterator();
				while (iterator.hasNext()) {
					Integer item = iterator.next();
					if (!Productlist.contains(item)) {

						llist.add(item);
					}

				}
				for (int i = 0; i < llist.size(); i++) {
					ProductCacheMap.remove(llist.get(i));
				}

				return;

			}
//			테이블에 추가가 됐는데 캐시에 반영이 안됐다는 뜻이다.
			if (ProductCacheset.size() < Productlist.size()) {

				stmtRevise = conn.prepareStatement("SELECT * FROM auction AS C RIGHT JOIN (\n"
						+ "SELECT A.productno, A.productname, A.initialprice, A.detailinfo, A.image, A.category, B.setno\n"
						+ "FROM product AS A LEFT JOIN enrollmentinfo AS B ON A.productno = B.productno\n"
						+ ") AS D ON C.setno = D.setno LEFT JOIN (\n"
						+ "SELECT auctionno, COUNT(*) AS popularity FROM (\n"
						+ "SELECT DISTINCT auctionno, userno FROM participate\n"
						+ "WHERE participateprice > 0) AS A GROUP BY auctionno\n"
						+ ") AS E ON C.auctionno = E.auctionno \n"
						+ "WHERE C.deadline > current_timestamp() AND D.productno = ? \n"
						+ "ORDER BY TIMESTAMPDIFF(SECOND, CURRENT_TIMESTAMP(), C.deadline);");

				Iterator<Integer> iterator = Productlist.iterator();
				while (iterator.hasNext()) {
					Integer item = iterator.next();
					if (!ProductCacheset.contains(item)) {
						stmtRevise.setInt(1, item);

						rsforStmtRevise = stmtRevise.executeQuery();
						while (rsforStmtRevise.next()) {

							int setNo = rs.getInt("setno");
							int productNo = rs.getInt("productno");
							int auctionNo = rs.getInt("auctionno");
							String productName = rs.getString("productname");
							int initialprice = rs.getInt("initialprice");
							int productPriceNow = rs.getInt("finalprice");
							String productContent = rs.getString("detailinfo");
							Blob image = rs.getBlob("image");
							Timestamp timestamp = rs.getTimestamp("starttime");
							LocalDateTime startTime = timestamp.toLocalDateTime();
							Timestamp timestamp2 = rs.getTimestamp("deadline");
							LocalDateTime endTime = timestamp2.toLocalDateTime();
							int popularity = rs.getInt("popularity");
							String category = rs.getString("category");

							if (productNo == item && !ProductCacheMap.containsKey(productNo)) {

								ProductCacheMap.put(productNo,
										new Product(setNo, productNo, auctionNo, productName, initialprice,
												productPriceNow, productContent, startTime, endTime, image, popularity,
												category));
							}
						}

					}
				}
				return;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rsforStmtRevise);
			DBUtil.close(rs);
			DBUtil.close(stmtRevise);
			DBUtil.close(stmt);
//			DBUtil.close(conn);
		}
	}

	public static void putProductSearchCacheMap(String searchObject) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement("SELECT *\r\n" + "FROM auction AS C\r\n"
					+ "RIGHT JOIN (SELECT A.productno, A.productname, A.initialprice, A.detailinfo, A.image, B.setno \r\n"
					+ "			FROM product AS A\r\n" + "			LEFT JOIN enrollmentinfo AS B \r\n"
					+ "            ON A.productno = B.productno) AS D\r\n" + "ON C.setno = D.setno\r\n"
					+ "WHERE C.deadline > current_timestamp()\r\n" + "AND productname LIKE ?\r\n"
					+ "ORDER BY TIMESTAMPDIFF(SECOND, CURRENT_TIMESTAMP(), C.deadline)");
			stmt.setString(1, "%" + searchObject + "%");
			rs = stmt.executeQuery();
			while (rs.next()) {
				int setNo = rs.getInt("setno");
				int productNo = rs.getInt("productno");
				int auctionNo = rs.getInt("auctionno");
				String productName = rs.getString("productname");

				int productPriceNow = rs.getInt("finalprice");
				String productContent = rs.getString("detailinfo");
				Blob image = rs.getBlob("image");
				Timestamp timestamp = rs.getTimestamp("starttime");
				LocalDateTime startTime = timestamp.toLocalDateTime();
				Timestamp timestamp2 = rs.getTimestamp("deadline");
				LocalDateTime endTime = timestamp2.toLocalDateTime();

//				list.add(new Product(setNo, productNo, auctionNo, productName, productPriceNow, productContent,
//						startTime, endTime, image));

				ProductSearchCacheMap.put(productNo, new Product(setNo, productNo, auctionNo, productName,
						productPriceNow, productContent, startTime, endTime, image));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);

			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public static void putProductCacheMap(int productno) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement("SELECT * FROM auction AS C RIGHT JOIN (\r\n"
					+ "SELECT A.productno, A.productname, A.initialprice, A.detailinfo, A.category, B.setno,A.image\r\n"
					+ "FROM product AS A  \r\n" + "LEFT JOIN enrollmentinfo AS B ON A.productno = B.productno\r\n"
					+ ") AS D ON C.setno = D.setno \r\n" + "LEFT JOIN (\r\n"
					+ "SELECT auctionno, COUNT(*) AS popularity  \r\n" + " FROM (\r\n"
					+ "SELECT DISTINCT auctionno, userno   \r\n" + "FROM participate\r\n"
					+ "WHERE participateprice > 0  \r\n" + ") AS A  \r\n" + "GROUP BY auctionno\r\n"
					+ ") AS E ON C.auctionno = E.auctionno \r\n" + "WHERE C.deadline > current_timestamp() \r\n"
					+ "ORDER BY productno desc limit 1;");

			rs = stmt.executeQuery();
			if (rs.next()) {
				int setNo = rs.getInt("setno");
				int productNo = rs.getInt("productno");
				int auctionNo = rs.getInt("auctionno");
				String productName = rs.getString("productname");
				int initialprice = rs.getInt("initialprice");
				int productPriceNow = rs.getInt("finalprice");
				String productContent = rs.getString("detailinfo");
				Blob image = rs.getBlob("image");
				Timestamp timestamp = rs.getTimestamp("starttime");
				LocalDateTime startTime = timestamp.toLocalDateTime();
				Timestamp timestamp2 = rs.getTimestamp("deadline");
				LocalDateTime endTime = timestamp2.toLocalDateTime();
				int popularity = rs.getInt("popularity");
				String category = rs.getString("category");

				if (!ProductCacheMap.containsKey(productNo) && productNo == productno) {

					ProductCacheMap.put(productNo, new Product(setNo, productNo, auctionNo, productName, initialprice,
							productPriceNow, productContent, startTime, endTime, image, popularity, category));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

//	최근에 추가된 product 추가하는 메서드
//	RegistFrame에서 호출한다.
	public static void putProductCacheMap() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement("SELECT * FROM auction AS C RIGHT JOIN (\r\n"
					+ "SELECT A.productno, A.productname, A.initialprice, A.detailinfo, A.category, B.setno,A.image\r\n"
					+ "FROM product AS A  \r\n" + "LEFT JOIN enrollmentinfo AS B ON A.productno = B.productno\r\n"
					+ ") AS D ON C.setno = D.setno \r\n" + "LEFT JOIN (\r\n"
					+ "SELECT auctionno, COUNT(*) AS popularity  \r\n" + " FROM (\r\n"
					+ "SELECT DISTINCT auctionno, userno   \r\n" + "FROM participate\r\n"
					+ "WHERE participateprice > 0  \r\n" + ") AS A  \r\n" + "GROUP BY auctionno\r\n"
					+ ") AS E ON C.auctionno = E.auctionno \r\n" + "WHERE C.deadline > current_timestamp() \r\n"
					+ "ORDER BY productno desc limit 1;");

			rs = stmt.executeQuery();
			if (rs.next()) {
				int setNo = rs.getInt("setno");
				int productNo = rs.getInt("productno");
				int auctionNo = rs.getInt("auctionno");
				String productName = rs.getString("productname");
				int initialprice = rs.getInt("initialprice");
				int productPriceNow = rs.getInt("finalprice");
				String productContent = rs.getString("detailinfo");
				Blob image = rs.getBlob("image");
				Timestamp timestamp = rs.getTimestamp("starttime");
				LocalDateTime startTime = timestamp.toLocalDateTime();
				Timestamp timestamp2 = rs.getTimestamp("deadline");
				LocalDateTime endTime = timestamp2.toLocalDateTime();
				int popularity = rs.getInt("popularity");
				String category = rs.getString("category");

				if (!ProductCacheMap.containsKey(productNo)) {

					ProductCacheMap.put(productNo, new Product(setNo, productNo, auctionNo, productName, initialprice,
							productPriceNow, productContent, startTime, endTime, image, popularity, category));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public static void addAllIntoProductCacheMap(Connection conn) {
//		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
//			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement("SELECT *\r\n" + "FROM auction AS C\r\n" + "RIGHT JOIN (\r\n"
					+ "    SELECT A.productno, A.productname, A.initialprice, A.detailinfo, A.image, A.category, B.setno\r\n"
					+ "    FROM product AS A\r\n" + "    LEFT JOIN enrollmentinfo AS B ON A.productno = B.productno\r\n"
					+ ") AS D ON C.setno = D.setno\r\n" + "LEFT JOIN (\r\n" + "\r\n"
					+ "    SELECT auctionno, COUNT(*) AS popularity\r\n" + "    FROM (\r\n"
					+ "        SELECT DISTINCT auctionno, userno\r\n" + "        FROM participate\r\n"
					+ "        WHERE participateprice > 0\r\n" + "    ) AS A\r\n" + "    GROUP BY auctionno\r\n"
					+ ") AS E ON C.auctionno = E.auctionno\r\n" + "WHERE C.deadline > current_timestamp() "
					+ "ORDER BY TIMESTAMPDIFF(SECOND, CURRENT_TIMESTAMP(), C.deadline)");

			rs = stmt.executeQuery();

			while (rs.next()) {
				int setNo = rs.getInt("setno");
				int productNo = rs.getInt("productno");
				int auctionNo = rs.getInt("auctionno");
				String productName = rs.getString("productname");
				int initialprice = rs.getInt("initialprice");
				int productPriceNow = rs.getInt("finalprice");
				String productContent = rs.getString("detailinfo");
				Blob image = rs.getBlob("image");
				Timestamp timestamp = rs.getTimestamp("starttime");
				LocalDateTime startTime = timestamp.toLocalDateTime();
				Timestamp timestamp2 = rs.getTimestamp("deadline");
				LocalDateTime endTime = timestamp2.toLocalDateTime();
				int popularity = rs.getInt("popularity");
				String category = rs.getString("category");

				if (!ProductCacheMap.containsKey(productNo)) {

					ProductCacheMap.put(productNo, new Product(setNo, productNo, auctionNo, productName, initialprice,
							productPriceNow, productContent, startTime, endTime, image, popularity, category));
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
//			DBUtil.close(conn);
		}
	}

//	LoginFrame에서 호출
	public static void addAllIntoCacheMap() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement(
					"SELECT auction.auctionno,enrollmentinfo.productno,product.productname,product.image,product.subimage1,product.subimage2,product.subimage3 \r\n"
							+ "FROM auction\r\n"
							+ "INNER JOIN enrollmentinfo ON auction.setno = enrollmentinfo.setno\r\n"
							+ "INNER JOIN product ON enrollmentinfo.productno = product.productno\r\n"
							+ "WHERE deadline>current_time() ORDER BY deadline-current_time();");

			rs = stmt.executeQuery();

			while (rs.next()) {
				int auctionno = rs.getInt("auctionno");
				Blob image = rs.getBlob("image");
				Blob subimage1 = rs.getBlob("subimage1");
				Blob subimage2 = rs.getBlob("subimage2");
				Blob subimage3 = rs.getBlob("subimage3");

				List<byte[]> list = new ArrayList<byte[]>();
				list.add(image.getBytes(1, (int) image.length()));
				list.add(subimage1.getBytes(1, (int) subimage1.length()));
				list.add(subimage2.getBytes(1, (int) subimage2.length()));
				list.add(subimage3.getBytes(1, (int) subimage3.length()));

				Cache.cacheMap.put(auctionno, list);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

//	auction 테이블에서 방금 추가된 auctionno의 값을 구하는 메서드
	public static int getMaxAuctionno(Connection conn) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("SELECT MAX(auctionno) from auction;");

			rs = stmt.executeQuery();

			if (rs.next()) {
				int auctionno = rs.getInt("MAX(auctionno)");
				return auctionno;
			}

		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
		}
		return 0;
	}

	public static void putCacheMap(int auctionno, byte[] imageBytes, byte[] imageBytes2, byte[] imageBytes3,
			byte[] imageBytes4) {
		if (!containsKeyCacheMap(auctionno)) {

			List<byte[]> imageList = new ArrayList<byte[]>();
			imageList.add(imageBytes);
			imageList.add(imageBytes2);
			imageList.add(imageBytes3);
			imageList.add(imageBytes4);

			cacheMap.put(auctionno, imageList);
		}
	}

//	auction 테이블에 최근 추가된 auctionno이 cachemap에 있는지 검증
	public static boolean containsKeyCacheMap(int auctionno) {

		return cacheMap.containsKey(auctionno);

	}

	public Object get(Integer key) {
		return cacheMap.get(key);
	}

	public static void remove(Integer auctionno) {

		cacheMap.remove(auctionno);
	}

	public void clear() {
		cacheMap.clear();
	}

	public int size() {
		return cacheMap.size();
	}

}
