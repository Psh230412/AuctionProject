package login_signup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import dbutil.DBUtil;
import objects.User;

public class LoginSignupRepository {

	// 로그인 시도할때 아이디, 비밀번호가 DB에 있는지 검색하고 있으면 유저 반환
	public User searchIdPassword(String id, String password) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM user WHERE id = ? AND password = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, password);

			rs = stmt.executeQuery();
			if (rs.next()) {
				Integer userno = rs.getObject("userno", Integer.class);
				String idParse = rs.getString("id");
				String passwordParse = rs.getString("password");
				int depositParse = rs.getInt("deposit");
				return new User(userno, idParse, passwordParse, depositParse);
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

	// 회원가입할 때 중복되는 아이디가 DB에 있는지 검색하고 있으면 그 아이디 반환
	public String searchId(String id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM user WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);

			rs = stmt.executeQuery();
			if (rs.next()) {
				String idParse = rs.getString("id");
				return idParse;
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

	// 회원가입 시 DB에 아이디와 비밀번호 입력
	public int insertIdPassword(String id, String password) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "INSERT INTO user (id, password, deposit) VALUES (?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, password);
			stmt.setInt(3, 0);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return 0;
	}

	public boolean isMatchesString(String text) {
		Pattern p = Pattern.compile("\\w{1,15}");
		Matcher m = p.matcher(text);
		return m.matches();
	}

}
