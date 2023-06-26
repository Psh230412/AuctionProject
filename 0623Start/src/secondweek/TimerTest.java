package secondweek;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import dbutil.DBUtil;

public class TimerTest implements ITimer {

	@Override
	public LocalDateTime select(int butten) {
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
	public int update(int butten) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "UPDATE timer \r\n" + 
					"SET `time` = `time` + INTERVAL 10 SECOND \r\n" + 
					"WHERE no = 1";
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

}
