package secondweek;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

					ProductCacheMap.put(productNo, new Product(setNo, productNo, auctionNo, productName, productPriceNow, productContent, startTime, endTime, image, popularity, category));
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

	public static void addAllIntoProductCacheMap() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

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

					ProductCacheMap.put(productNo, new Product(setNo, productNo, auctionNo, productName,productPriceNow, productContent, startTime, endTime, image, popularity, category));
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
