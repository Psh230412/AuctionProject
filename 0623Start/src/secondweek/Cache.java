package secondweek;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dbutil.DBUtil;
// key는 auction테이블의 주요키와 같아야한다.
// 스케줄러 안에는 auction테이블의 주요키와 map의 key를 비교하는 코드

public class Cache {

	private static HashMap<Integer, List<byte[]>> cacheMap = new HashMap<>();
	
//	LoginFrame에서 호출
	public static void addAllCacheMap() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement(
					"SELECT auction.auctionno,enrollmentinfo.productno,product.productname,product.image,product.subimage1,product.subimage2,product.subimage3 \r\n" + 
					"FROM auction\r\n" + 
					"INNER JOIN enrollmentinfo ON auction.setno = enrollmentinfo.setno\r\n" + 
					"INNER JOIN product ON enrollmentinfo.productno = product.productno\r\n" + 
					"WHERE deadline>current_time() ORDER BY deadline-current_time();");
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
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

	public static void put(int auctionno, byte[] imageBytes, byte[] imageBytes2, byte[] imageBytes3,
			byte[] imageBytes4) {
		if (!containsKey(auctionno)) {

			List<byte[]> imageList = new ArrayList<byte[]>();
			imageList.add(imageBytes);
			imageList.add(imageBytes2);
			imageList.add(imageBytes3);
			imageList.add(imageBytes4);

			cacheMap.put(auctionno, imageList);
		}
	}

//	auction 테이블에 최근 추가된 auctionno이 cachemap에 있는지 검증
	public static boolean containsKey(int auctionno) {

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
