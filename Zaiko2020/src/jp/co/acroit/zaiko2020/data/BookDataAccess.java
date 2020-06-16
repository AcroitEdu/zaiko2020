package jp.co.acroit.zaiko2020.data;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class BookDataAccess {
	public BookDataAccess() {
		url = "jdbc:mysql://localhost/zaiko2020?characterEncoding=UTF-8&serverTimezone=JST";
		driver = "com.mysql.cj.jdbc.Driver";
		username = "tomcat";
		password = "RC2-Z%b9e85PWqR";
		query = "SELECT * FROM user WHERE name = ?";
		numberColumn = "id";
		idColumn = "name";
		passwordColumn = "pass";

		PoolProperties p = new PoolProperties();

		// 接続情報の設定
		p.setUrl(url);
		p.setDriverClassName(driver);
		p.setUsername(username);
		p.setPassword(password);

		datasource = new DataSource();
		datasource.setPoolProperties(p);
	}

	String url;
	String driver;
	String username;
	String password;
	String query;
	String numberColumn;
	String idColumn;
	String passwordColumn;
	DataSource datasource;
}
