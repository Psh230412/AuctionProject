import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dbutil.DBUtil;

public class TimerTest implements ITimer {

	@Override
	public LocalDateTime selectTime(int butten) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT `time` FROM timer WHERE no = ?");

			stmt.setInt(1, butten);

			rs = stmt.executeQuery();
			if (rs.next()) {
				LocalDateTime time = rs.getObject("time", LocalDateTime.class);
				return time;
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

	@Override
	public int updateTime(int butten) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "UPDATE timer \r\n" + "SET `time` = `time` + INTERVAL 10 SECOND \r\n" + "WHERE no = 1";
			stmt = conn.prepareStatement(sql);

			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return 0;
	}

	@Override
	public List<Product> selectProductId(int userId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Product> list = new ArrayList<>();

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT * \r\n" + "FROM product A\r\n"
					+ "WHERE A.productno IN (SELECT productno\r\n" + "                    FROM enrollmentinfo A\r\n"
					+ "                    WHERE A.userno = (SELECT userno\r\n"
					+ "                                        FROM `user`\r\n"
					+ "                                        WHERE userno = ?\r\n" + "));");

			stmt.setInt(1, userId);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int productNo = rs.getInt("productno");
				String productName = rs.getString("productname");
				int productPriceNow = rs.getInt("initialprice");
				String productContent = rs.getString("detailinfo");
				Blob image = rs.getBlob("image");

				File file = new File("E:\\사진테스트\\" + productName + ".jpg");
				list.add(new Product(userId, productNo, productName, productPriceNow, productContent, null, null));

				if (file.exists()) {
					continue;
				}

				Files.copy(image.getBinaryStream(), Paths.get("E:\\사진테스트\\" + productName + ".jpg"));
			}
			rs.close();
			stmt.close();

			stmt = conn.prepareStatement("SELECT * \r\n" + "FROM auction A\r\n" + "WHERE A.setno IN (SELECT setno\r\n"
					+ "                    FROM enrollmentinfo A\r\n"
					+ "                    WHERE A.userno = (SELECT userno\r\n"
					+ "                                        FROM `user`\r\n"
					+ "                                        WHERE userno = ?\r\n" + "))");

			stmt.setInt(1, userId);

			rs = stmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Timestamp timestamp = rs.getTimestamp("starttime");
				LocalDateTime startTime = timestamp.toLocalDateTime();

				Timestamp timestamp2 = rs.getTimestamp("deadline");
				LocalDateTime endTime = timestamp2.toLocalDateTime();

				list.get(i).setStartTime(startTime);
				list.get(i).setEndTime(endTime);
				i++;
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return list;
	}

	@Override
	public List<Product> selectProduct() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Product> list = new ArrayList<>();

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT *\r\n"
					+ "FROM auction AS C\r\n"
					+ "RIGHT JOIN (SELECT A.productno, A.productname, A.initialprice, A.detailinfo, A.image\r\n"
					+ "	, B.setno\r\n"
					+ "		FROM product AS A\r\n"
					+ "		LEFT JOIN enrollmentinfo AS B ON A.productno = B.productno) AS D\r\n"
					+ "ON C.setno = D.setno\r\n"
					+ "ORDER BY TIMESTAMPDIFF(SECOND, CURRENT_TIMESTAMP(), C.deadline);");

			rs = stmt.executeQuery();
			while (rs.next()) {
				int productNo = rs.getInt("productno");
				String productName = rs.getString("productname");
				int productPriceNow = rs.getInt("initialprice");
				String productContent = rs.getString("detailinfo");
				Blob image = rs.getBlob("image");
				Timestamp timestamp = rs.getTimestamp("starttime");
				LocalDateTime startTime = timestamp.toLocalDateTime();
				Timestamp timestamp2 = rs.getTimestamp("deadline");
				LocalDateTime endTime = timestamp2.toLocalDateTime();
				
				File file = new File("E:\\사진테스트\\" + productName + ".jpg");
				list.add(new Product(productNo, productName, productPriceNow, productContent, startTime, endTime));

				if (file.exists()) {
					continue;
				}

				Files.copy(image.getBinaryStream(), Paths.get("E:\\사진테스트\\" + productName + ".jpg"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return list;
	}
}
