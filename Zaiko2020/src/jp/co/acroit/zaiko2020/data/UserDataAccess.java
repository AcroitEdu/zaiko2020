package jp.co.acroit.zaiko2020.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.acroit.zaiko2020.user.User;

/**
 * データベースアクセスクラス
 * @version 1.0
 * @version 1.1
 * 二重ログイン対策用'is_logged_in'関連の追加
 * @author yohei nishida
 */
public class UserDataAccess {

	public UserDataAccess() {
		url = "jdbc:mysql://localhost/zaiko2020?characterEncoding=UTF-8&serverTimezone=JST";
		driver = "com.mysql.cj.jdbc.Driver";
		username = "root";
		password = "";
//		username = "tomcat";
//		password = "RC2-Z%b9e85PWqR";
		query = "SELECT * FROM user WHERE name = ?";
		numberColumn = "id";
		idColumn = "name";
		passwordColumn = "pass";
		isLoggedInColumn = "is_logged_in";

	}

	String url;
	String driver;
	String username;
	String password;
	String query;
	String numberColumn;
	String idColumn;
	String passwordColumn;
	String isLoggedInColumn;

	//id検索
	public User findById(String id) throws SQLException, ClassNotFoundException {
		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,username,password);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			long number = -1;
			String digest = null;
			String idFound = null;
			int isLoggedIn = 0;

			while (rs.next()) {
				number = rs.getLong(numberColumn);
				idFound = rs.getString(idColumn);
				digest = rs.getString(passwordColumn);
				isLoggedIn = rs.getInt(isLoggedInColumn);

				//idの一致するユーザーを返す
				if (idFound.equals(id)) {
					rs.close();
					con.close();
					con = null;
					return new User(number, idFound, digest,isLoggedIn);
				}
			}
			rs.close();
			con.close();
			con = null;
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception ignore) {
				}
			}
		}
	}
}
