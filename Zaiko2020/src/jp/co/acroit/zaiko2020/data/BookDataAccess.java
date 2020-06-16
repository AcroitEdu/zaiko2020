package jp.co.acroit.zaiko2020.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import jp.co.acroit.zaiko2020.book.Book;

/**
 * 書籍データベースアクセスクラス
 * @version 1.0
 * @author hiroe ishioka
 */
public class BookDataAccess {

	public BookDataAccess() {
		url = "jdbc:mysql://localhost/zaiko2020?characterEncoding=UTF-8&serverTimezone=JST";
		driver = "com.mysql.cj.jdbc.Driver";
		username = "tomcat";
		password = "RC2-Z%b9e85PWqR";
		query = "SELECT * FROM books WHERE ";

		numberColumn = "id";
		titleColumn = "title";
		authorColumn = "author";
		salesDateColumn = "salesDate";
		isbnColumn = "isbn";
		priceColumn = "price";
		stockColumn = "stock";
		deleteflgColumn = "deleteflg";



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
	String titleColumn;
	String authorColumn;
	String salesDateColumn;
	String isbnColumn;
	String priceColumn;
	String stockColumn;
	String deleteflgColumn;

	DataSource datasource;

	// 書籍検索

	public List<Book> find(SearchCondition sc) throws SQLException {
		Connection con = null;

		try {
			con = datasource.getConnection();

			String book = ;
			String publisher = "";
			String author = "";
			String salsDate = "";
			String stock = "";
			String salsDateFlag = "";
			String stockFlag = "";




			String[] whereDataName = {"book", "publisher", "author", "salsDate", "stock"};
			String[] whereData = {book, publisher, author, salsDate, stock};

			boolean addAnd = false;
			for (int i = 0; i < whereData.length; i++) {
				if(!whereData[i].isEmpty()) {

					// 二つ目以降はANDを付与
					if(addAnd) {
						query = query + " AND";
					} else {
						addAnd = true;
					}

					// カラム名の付与
					query = query + " " + whereDataName[i] + " ";

					// 比較演算子の付与
					switch (whereDataName[i]) {
						case "salsDate":
							switch (salsDateFlag) {
								case "以前":
									query = query + "<=";
									break;
								case "以降":
									query = query + ">=";
									break;
								default:
									query = query + "=";
							}
							break;
						case "stock":
							switch (stockFlag) {
								case "以上":
									query = query + ">=";
									break;
								case "以下":
									query = query + "<=";
									break;
							}
							break;
						default:
							query = query + "=";
					}

					query = query + " " + whereData[i];
				}
			}


			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			long number = -1;
			String digest = null;
			String idFound = null;


			while (rs.next()) {
				number = rs.getLong(numberColumn);
				idFound = rs.getString(idColumn);
				digest = rs.getString(passwordColumn);


				//idの一致するユーザーを返す
				if (idFound.equals(id)) {
					rs.close();
					con.close();
					con = null;
					return new Book(id, name, publisher, author, isbn, salesDate, price, stock, deleteFlag);
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
