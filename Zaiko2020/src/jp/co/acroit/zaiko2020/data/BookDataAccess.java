package jp.co.acroit.zaiko2020.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		query = "SELECT * FROM books";
		idColumn = "id";
		titleColumn = "title";
		publisherColumn = "publisher";
		authorColumn = "author";
		salesDateColumn = "salesDate";
		isbnColumn = "isbn";
		priceColumn = "price";
		stockColumn = "stock";
		deleteflgColumn = "deleteflg";
		libraryHoldingsColumn = "libraryHoldings";

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
	String idColumn;
	String titleColumn;
	String publisherColumn;
	String authorColumn;
	String salesDateColumn;
	String isbnColumn;
	String priceColumn;
	String stockColumn;
	String deleteflgColumn;
	String libraryHoldingsColumn;
	DataSource datasource;

	// 書籍検索
	public List<Book> find(SearchCondition sc) throws SQLException {
		Connection con = null;
		try {
			con = datasource.getConnection();

			//実クエリの固定部分
			query = "SELECT * FROM books";
			//実クエリへの加筆
			sqlWhere(sc);
			sqlOrderBy(sc);
			sqlLimit(sc);
			sqlSemicolon();

			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			// sqlから取り出したものを入れる変数
			int dbId = 0;
			String dbBookName = null;
			String dbPublisher = null; // 出版社の取得
			String dbAuthor = null; // 著者の取得
			String dbIsbn = null; // ISBNの取得
			Date dbSalsDate; // 発売日の取得
			int dbPrice = 0; // 価格の取得
			int dbStock = 0; // 在庫数の取得
			int dbDeleteflg = 0;

			// sqlから取り出した書籍情報をまとめるリスト
			List<Book> bookList = new ArrayList<Book>();

			// リストに１冊ずつ加えていく
			while (rs.next()) {
				dbId = rs.getInt(idColumn);
				dbBookName = rs.getString(titleColumn);
				dbPublisher = rs.getString(publisherColumn);
				dbAuthor = rs.getString(authorColumn);
				dbIsbn = rs.getString(isbnColumn);
				dbSalsDate = rs.getDate(salesDateColumn);
				dbPrice = rs.getInt(priceColumn);
				dbStock = rs.getInt(stockColumn);

				dbDeleteflg = rs.getInt(deleteflgColumn);
				Book aBook = new Book(dbId, dbBookName, dbPublisher, dbAuthor, dbIsbn, dbSalsDate, dbPrice, dbStock,
						dbDeleteflg);
				bookList.add(aBook);
			}
			rs.close();
			con.close();
			con = null;

			//リストの送信
			return bookList;
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

	//総件数の取得
	public int countAll(SearchCondition sc) throws SQLException {
		Connection con = null;

		try {
			con = datasource.getConnection();

			//実クエリの固定部分
			query = "SELECT libraryHoldings=COUNT(*) AS libraryHoldings FROM books";
			//実クエリへの加筆
			sqlWhere(sc);
			sqlOrderBy(sc);
			sqlSemicolon();


			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			// sqlから取り出した総件数を入れる変数
			int libraryHoldings = 0;

			// 総件数を取得する
			while (rs.next()) {
				libraryHoldings = rs.getInt(libraryHoldingsColumn);
			}
			rs.close();
			con.close();
			con = null;
			return libraryHoldings;
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

	//WHERE句の生成・加筆
	public void sqlWhere(SearchCondition sc) {
		//Where句をここに生成し最後に実クエリに加筆する
		String query = "";

		String bookName = sc.getName(); //書籍名の取得
		String publisher = sc.getPublisher(); //出版社の取得
		String author = sc.getAuthor(); //著者の取得
		String isbn = sc.getIsbn(); //ISBNの取得
		String salsDate = sc.getSalesDate(); //発売日の取得
		String stock = sc.getStock(); //在庫数の取得
		String salsDateFlag = sc.getSalsDateFlag(); //発売日検索条件の取得
		String stockFlag = sc.getStockFlag(); //在庫数検索条件の取得

		// queryに継ぎ足す部分の一時保存
		String AddSql = null;

		// SQLのWHERE句で用いるカラム名
		String[] whereDataName = { titleColumn, publisherColumn, authorColumn,
				isbnColumn, salesDateColumn, stockColumn };
		// SQLのWHERE句で用いるフィールド
		String[] whereData = { bookName, publisher, author, isbn, salsDate, stock };

		// WHERE句を加えたか否か
		boolean isAddWhere = false;

		// SQLのWHERE句の生成
		for (int i = 0; i < whereData.length; i++) {

			//条件が空白でない場合
			if (whereData[i] != null && !whereData[i].isEmpty()) {

				// 比較演算子の付与
				switch (whereDataName[i]) {
				case "salsDate": // 発売日の場合
					switch (salsDateFlag) {
					case "equals": // ～に一致
						AddSql = "=";
						break;
					case "before": // ～以前
						AddSql = "<=";
						break;
					case "after": // ～以降
						AddSql = ">=";
						break;
					}
					break;
				case "stock": // 在庫数の場合
					switch (stockFlag) {
					case "lt": // ～未満
						AddSql = "<";
						break;
					case "ltoe": // ～以下
						AddSql = "<=";
						break;
					case "equals": // ～に等しい
						AddSql = "=";
						break;
					case "gtoe": // ～以上
						AddSql = ">=";
						break;
					case "gt": // ～より多い
						AddSql = ">";
						break;

					}
					break;
				default: // 上記以外(あいまい検索)の場合
					AddSql = "LIKE";
				}

				//検索条件が指定なしでない場合にSQLへの加筆を行う
				if (!AddSql.isEmpty()) {

					// フィールドの付与
					if (AddSql.equals("LIKE")) { //文字列の検索の場合
						AddSql = AddSql + " '%" + whereData[i] + "%'";
					} else if (whereDataName[i].equals("salsDate")) { //発売日の検索の場合
						AddSql = AddSql + " '" + whereData[i] + "'";
					} else { // 数値の検索の場合
						AddSql = AddSql + " " + whereData[i];
					}

					//カラム名の付与
					AddSql = " " + whereDataName[i] + " " + AddSql;

					// １つ目にはWHEREを、二つ目以降はANDを先頭に付与
					if (isAddWhere) {
						AddSql = " AND" + AddSql;
					} else {
						AddSql = " WHERE" + AddSql;
						isAddWhere = true;
					}

					//SQLへの加筆
					query = query + AddSql;
				}
			}
			AddSql = null;
		}

		// 実クエリへの加筆
		this.query += query;
	}

	// ORDER BY句の生成・加筆
	public void sqlOrderBy(SearchCondition sc) {
		//ORDERBY句をここに生成し最後に実クエリに加筆する
		String query = " ORDER BY ";
		String sort = sc.getSort(); //ソート条件の取得
		String lift = sc.getLift(); //昇順・降順の取得
		switch (sort) {
		case "0": // 発売日でソート
			query = query + "salsDate";
			break;
		case "1": // ISBNでソート
			query = query + "isbn";
			break;
		case "2": // 在庫数のソート
			query = query + "stock";
			break;
		}
		if (lift.equals("1")) { // 昇順の場合
			query = query + " ASC";
		} else { // 降順の場合
			query = query + " DESC";
		}

		// 実クエリへの加筆
		this.query += query;
	}

	// LIMIT句の生成・加筆
	public void sqlLimit(SearchCondition sc) {
		int page = sc.getPage(); //表ページ数の取得
		// 実クエリへの加筆
		query = query + " LIMIT " + String.valueOf((page - 1) * 200) + ", 200";
	}

	// SQLの最後のセミコロンの加筆
	public void sqlSemicolon() {
		query = query + ";";
	}
}
