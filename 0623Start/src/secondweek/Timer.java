
package secondweek;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dbutil.DBUtil;

public class Timer {
	public List<Product> selectProduct() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Product> list = new ArrayList<>();

		try {
			conn = DBUtil.getConnection();
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
				int initialPrice = rs.getInt("initialprice");
				int productPriceNow = rs.getInt("finalprice");
				String productContent = rs.getString("detailinfo");
				Blob image = rs.getBlob("image");
				Timestamp timestamp = rs.getTimestamp("starttime");
				LocalDateTime startTime = timestamp.toLocalDateTime();
				Timestamp timestamp2 = rs.getTimestamp("deadline");
				LocalDateTime endTime = timestamp2.toLocalDateTime();
				int popularity = rs.getInt("popularity");
				String category = rs.getString("category");

				list.add(new Product(setNo, productNo, auctionNo, productName, initialPrice, productPriceNow,
						productContent, startTime, endTime, image, popularity, category));
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

	public List<Product> selectSearchProduct(String searchObject) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Product> list = new ArrayList<>();

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT *\r\n" + "FROM auction AS C\r\n" + "RIGHT JOIN (\r\n"
					+ "    SELECT A.productno, A.productname, A.initialprice, A.detailinfo, A.image, A.category, B.setno\r\n"
					+ "    FROM product AS A\r\n" + "    LEFT JOIN enrollmentinfo AS B ON A.productno = B.productno\r\n"
					+ ") AS D ON C.setno = D.setno\r\n" + "LEFT JOIN (\r\n"
					+ "    SELECT auctionno, COUNT(*) AS popularity\r\n" + "    FROM (\r\n"
					+ "        SELECT DISTINCT auctionno, userno\r\n" + "        FROM participate\r\n"
					+ "        WHERE participateprice > 0\r\n" + "    ) AS A\r\n" + "    GROUP BY auctionno\r\n"
					+ ") AS E ON C.auctionno = E.auctionno\r\n" + "WHERE C.deadline > current_timestamp()\r\n"
					+ "AND productname like ?\r\n"
					+ "ORDER BY TIMESTAMPDIFF(SECOND, CURRENT_TIMESTAMP(), C.deadline);");
			stmt.setString(1, "%" + searchObject + "%");
			rs = stmt.executeQuery();
			while (rs.next()) {
				int setNo = rs.getInt("setno");
				int productNo = rs.getInt("productno");
				int auctionNo = rs.getInt("auctionno");
				String productName = rs.getString("productname");
				int initialPrice = rs.getInt("initialprice");
				int productPriceNow = rs.getInt("finalprice");
				String productContent = rs.getString("detailinfo");
				Blob image = rs.getBlob("image");
				Timestamp timestamp = rs.getTimestamp("starttime");
				LocalDateTime startTime = timestamp.toLocalDateTime();
				Timestamp timestamp2 = rs.getTimestamp("deadline");
				LocalDateTime endTime = timestamp2.toLocalDateTime();
				int popularity = rs.getInt("popularity");
				String category = rs.getString("category");

				list.add(new Product(setNo, productNo, auctionNo, productName, initialPrice, productPriceNow,
						productContent, startTime, endTime, image, popularity, category));
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

	public void inputSuccessbidinfo() {
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtForDelete = null;

		try {
			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement("INSERT INTO successbidinfo (userno, auctioncopyno, productno)\r\n"
					+ "SELECT B.userno, A.auctionno, B.productno\r\n" + "FROM copy_auction A\r\n"
					+ "JOIN enrollmentinfo B ON A.setno = B.setno\r\n" + "WHERE A.deadline < CURRENT_TIMESTAMP()\r\n"
					+ "AND NOT EXISTS (\r\n" + "  SELECT *\r\n" + "  FROM successbidinfo C\r\n"
					+ "  WHERE C.userno = B.userno\r\n" + "    AND C.auctioncopyno = A.auctionno\r\n"
					+ "    AND C.productno = B.productno\r\n" + ")");

			stmt.executeUpdate();
//			deadline 지난것은 successbidinfo로 보내고 auction에서 삭제 시킨다.

			stmtForDelete = conn.prepareStatement("DELETE FROM auction\r\n" + "WHERE deadline<current_timestamp();");

			stmtForDelete.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmtForDelete);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void setIsBid() {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement("UPDATE successbidinfo\r\n" + "set isbid = 0\r\n"
					+ "where successbidinfo.auctioncopyno IN (SELECT A.auctionno from copy_auction A\r\n"
					+ "WHERE A.deadline < current_time() AND A.auctionno NOT IN  (SELECT auctionno FROM participate));");

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void updatePrice(int setNo, String bid, Connection conn) throws SQLException {
		try {
			conn = DBUtil.getConnection();

			PreparedStatement stmt = conn
					.prepareStatement("update auction\r\n" + "set finalprice = ?\r\n" + "WHERE setno = ?;");
			stmt.setInt(1, Integer.parseInt(bid));
			stmt.setInt(2, setNo);

			stmt.executeUpdate();

		} finally {
		}
	}

	public boolean isOwn(int userno, int presentProductno) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement("SELECT a.auctionno,a.setno,e.userno,e.productno FROM auction a \r\n"
					+ "JOIN enrollmentinfo e\r\n" + "ON e.setno=a.setno and userno = ?;");
			stmt.setInt(1, userno);

			rs = stmt.executeQuery();

			while (rs.next()) {
				int productno = rs.getInt("productno");

				if (productno == presentProductno) {
					return false;

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}

		return true;
	}

	public int getAuctionNo(int productno) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement(
					"SELECT auctionno FROM auction where setno = (SELECT setno FROM enrollmentinfo where productno = ? );");

			stmt.setInt(1, productno);

			rs = stmt.executeQuery();

			if (rs.next()) {
				int auctionno = rs.getInt("auctionno");
				return auctionno;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return -1;

	}

	public boolean isLeftOneMinute(LocalDateTime deadline, LocalDateTime now) {
		Duration duration = Duration.between(now, deadline);
//		long days = duration.toDays();
//		long hours = duration.toHours() % 24;
//		long minutes = duration.toMinutes() % 60;
//		long seconds = duration.getSeconds() % 60;

		long seconds = duration.getSeconds();

		if (seconds <= 60) {
			return true;
		}
		return false;
	}

	public void plusOneMinute(int auctionno, Connection conn) throws SQLException {
		LocalDateTime now = LocalDateTime.now();

		try {
			conn = DBUtil.getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT deadline FROM auction where auctionno = ? ;");

			stmt.setInt(1, auctionno);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Timestamp deadline = rs.getTimestamp("deadline");

				LocalDateTime localDateTime = deadline.toLocalDateTime();

				if (isLeftOneMinute(localDateTime, now)) {
					PreparedStatement stmtForPlusDuration = conn.prepareStatement("UPDATE auction \r\n"
							+ "SET deadline = DATE_ADD(deadline, INTERVAL 1 MINUTE)\r\n" + "where auctionno = ? ;");
					stmtForPlusDuration.setInt(1, auctionno);
					stmtForPlusDuration.executeUpdate();

				} else {
					return;
				}
			}
		} finally {
		}
	}

	// 입찰가격 입력
	public void insertParticipate(int userNo, int auctionNo, int price) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement(
					"INSERT INTO participate (userno, auctionno, participatetime, participateprice)\r\n"
							+ "VALUES (?, ?, ?, ?);");

			stmt.setInt(1, userNo);
			stmt.setInt(2, auctionNo);
			LocalDateTime now = LocalDateTime.now();
			stmt.setTimestamp(3, Timestamp.valueOf(now));
			stmt.setInt(4, price);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	// 최근입찰가격용
	public List<Integer> participateList(int auctionno, Connection conn) throws SQLException {
		List<Integer> list = new ArrayList<>();

		String sql = "SELECT A.participateprice FROM participate A \r\n"
				+ "INNER JOIN auction B ON A.auctionno = B.auctionno \r\n" + "WHERE B.auctionno = ?\r\n"
				+ "ORDER BY A.participatetime DESC\r\n" + "LIMIT 4";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, auctionno);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int participatePrice = rs.getInt("participateprice");
				list.add(participatePrice);
			}
			return list;
		}
	}

	public boolean isContinue(int auctionno, int userno) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(
					"SELECT userno,auctionno FROM participate where auctionno = ? ORDER BY participatetime DESC LIMIT 1;");

			stmt.setInt(1, auctionno);

			rs = stmt.executeQuery();
			if (rs.next()) {
				int lastUserno = rs.getInt("userno");
				if (lastUserno == userno) {
					return false;
				} else {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return true;
	}

	// 이미지 여러개 리스트
	public Blob[] productImages(int productno) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Blob[] blobs = new Blob[4];
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM product WHERE productno = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, productno);
			rs = stmt.executeQuery();
			if (rs.next()) {
				blobs[0] = rs.getBlob("image");
				blobs[1] = rs.getBlob("subimage1");
				blobs[2] = rs.getBlob("subimage2");
				blobs[3] = rs.getBlob("subimage3");
				return blobs;
			}
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
