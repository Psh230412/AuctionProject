package secondweek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

import dbutil.DBUtil;

public class LoginSignupRepository {

	// 로그인 시도할때 아이디, 비밀번호가 DB에 있는지 검색하고 있으면 유저 반환
	public User searchIdPassword(String id, String password, Connection conn) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
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
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
		}
		return null;
	}

	// 회원가입할 때 중복되는 아이디가 DB에 있는지 검색하고 있으면 그 아이디 반환
	public String searchId(String id, Connection conn) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM user WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);

			rs = stmt.executeQuery();
			if (rs.next()) {
				String idParse = rs.getString("id");
				return idParse;
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
		}
		return null;
	}

	// 비밀번호 찾기
	public String passwordSearch(String id, String name, String telNumStr) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT password FROM user " + "WHERE id = ? AND name = ? AND phonenumber = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, name);
			stmt.setString(3, telNumStr);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("password");
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

	public int insertUserInfo(String id, String password, String nickname, String name, LocalDate date, int genderInt,
			String phonenumber, String bigAreaComboStr, String mediumAreaComboStr, String smallAreaComboStr,
			Connection conn) throws SQLException {
		PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO user (`id`, `password`, `nickname`, `name`, `birthday`, `gender`, `phonenumber`, `bigarea`, `mediumarea`, `detailarea`, `deposit`) \r\n"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0);";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, password);
			stmt.setString(3, nickname);
			stmt.setString(4, name);
			java.sql.Date sqlDate = java.sql.Date.valueOf(date);
			stmt.setDate(5, sqlDate);
			stmt.setInt(6, genderInt);
			stmt.setString(7, phonenumber);
			stmt.setString(8, bigAreaComboStr);
			stmt.setString(9, mediumAreaComboStr);
			stmt.setString(10, smallAreaComboStr);

			return stmt.executeUpdate();
		} finally {
			DBUtil.close(stmt);
		}
	}

	// 중복되는 닉네임 있는지 검색후 해당하는 개수 반환
	public int searchNickName(String newNickName, Connection conn) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT COUNT(*) AS count FROM user WHERE nickname = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, newNickName);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("count");
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
		}
		return 0;
	}

	// 생성 가능한 닉네임인지?
	public boolean correctNickName(String newNickName, Connection conn) throws SQLException {
		if (searchNickName(newNickName, conn) == 0 && matchNickName(newNickName)) {
			return true;
		} else {
			return false;
		}
	}

	// 아이디 조건 - 영소문자 필수, 숫자포함가능 10자리이상 20자리이하
	public boolean matchId(String id) {
		Pattern p = Pattern.compile("^(?=.*[a-z])[a-z0-9]{10,20}$"); // o 0630성지수_수정
		Matcher m = p.matcher(id);
		return m.matches();
	}

	// 이름 조건 - 영문자로만 이루어지거나(4자이상 20자이하), 한글로만 이루어져야 함(2자이상).
	public boolean matchName(String name) {
		Pattern p = Pattern.compile("[a-zA-Z]{4,20}"); // o
		Matcher m = p.matcher(name);

		Pattern p2 = Pattern.compile("[\\uAC00-\\uD7AF]{2,20}"); // o
		Matcher m2 = p2.matcher(name);

		return (m.matches() || m2.matches());
	}

	// 닉네임 조건 - 영대소문자 or 한글 or 숫자 포함 4자리이상 20자리이하 // 수정
	public boolean matchNickName(String newNickName) {
		Pattern p = Pattern.compile("[\\w\\uAC00-\\uD7AF]{4,20}"); // o

		Matcher m = p.matcher(newNickName);
		return m.matches();
	}

	// 비밀번호 조건 - 영대소문자, 숫자 각 1개씩 필수포함 10자리이상 20자리이하
	public boolean matchPassword(String newPassword) {
		Pattern p = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[a-zA-Z0-9]{10,20}$"); // o
		Matcher m = p.matcher(newPassword);
		return m.matches();
	}
}

class NumberOnlyFilter extends DocumentFilter {
	private int maxLength; // 최대 입력 길이

	public NumberOnlyFilter(int maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
			throws BadLocationException {
		// 입력 문자열이 숫자인 경우에만 삽입을 허용합니다.
		if (string.matches("\\d") && (fb.getDocument().getLength() + string.length()) <= maxLength) {
			super.insertString(fb, offset, string, attr);
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
		// 입력 문자열이 숫자인 경우에만 대체를 허용합니다.
		if (text.matches("\\d") && (fb.getDocument().getLength() - length + text.length()) <= maxLength) {
			super.replace(fb, offset, length, text, attrs);
		}
	}

}
