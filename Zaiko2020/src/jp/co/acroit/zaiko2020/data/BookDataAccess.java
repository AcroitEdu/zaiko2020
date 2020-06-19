package jp.co.acroit.zaiko2020.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
		// 接続情報を格納する
		url = "jdbc:mysql://localhost/zaiko2020?characterEncoding=UTF-8&serverTimezone=JST&zeroDateTimeBehavior=convertToNull";
		driver = "com.mysql.cj.jdbc.Driver";
		username = "tomcat";
		password = "RC2-Z%b9e85PWqR";

		// カラム名を格納する
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
			query = query + sqlWhere(sc) + sqlOrderBy(sc) + sqlLimit(sc) + sqlSemicolon();

			//クエリの送信・実行
			PreparedStatement ps = con.prepareStatement(query);
			//DBから受け取る用
			ResultSet rs = ps.executeQuery();

			// sqlから取り出したものを入れる変数
			int dbId = 0;
			String dbBookName = null;
			String dbPublisher = null;
			String dbAuthor = null;
			String dbIsbn = null;
			LocalDate dbSalsDate;
			int dbPrice = 0;
			int dbStock = 0;
			int dbDeleteflg = 0;

			// sqlから取り出した書籍情報をまとめるリスト
			List<Book> bookList = new ArrayList<Book>();

			// リストに１冊ずつ加えていく
			while (rs.next()) {
				dbId = rs.getInt(idColumn);
				dbBookName = rs.getString(titleColumn);
				//dbPublisher = rs.getString(publisherColumn);
				dbAuthor = rs.getString(authorColumn);
				dbIsbn = rs.getString(isbnColumn);
				dbSalsDate = rs.getDate(salesDateColumn).toLocalDate();
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
			query = "SELECT COUNT(*) AS libraryHoldings FROM books";

			//実クエリへの加筆
			query = query + sqlWhere(sc) + sqlOrderBy(sc) + sqlSemicolon();

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

			// 総件数の送信
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

	//WHERE句の生成
	public String sqlWhere(SearchCondition sc) {
		//Where句をここに生成する
		String query = "";

		//各検索条件の取得
		String bookName = sc.getName();
		String publisher = sc.getPublisher();
		String author = sc.getAuthor();
		String isbn = sc.getIsbn();
		String salsDate = sc.getSalesDate();
		String stock = sc.getStock();
		String salsDateFlag = sc.getSalesDateFlag();
		String stockFlag = sc.getStockFlag();

		// queryに継ぎ足す部分の一時保存
		String AddSql = null;

		// WHERE句で用いるカラム名
		String[] whereDataName = { titleColumn, publisherColumn, authorColumn,
				isbnColumn, salesDateColumn, stockColumn };
		// WHERE句で用いるフィールド
		String[] whereData = { bookName, publisher, author, isbn, salsDate, stock };

		// WHERE句を一度でも加えたか否か
		boolean isAddWhere = false;

		// WHERE句の生成
		for (int i = 0; i < whereData.length; i++) {

			//条件が空白でない場合
			if (whereData[i] != null && !whereData[i].isEmpty()) {

				// 比較演算子の付与
				switch (whereDataName[i]) {
				case "salesDate": // 発売日の場合
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
				if (AddSql != null && !AddSql.isEmpty()) {

					// フィールドの付与
					if (AddSql.equals("LIKE")) { //文字列の検索の場合
						AddSql = AddSql + " '%" + whereData[i] + "%'";
					} else if (whereDataName[i].equals(salesDateColumn)) { //発売日の検索の場合
						AddSql = AddSql + " '" + whereData[i] + "'";
					} else { // 数値の検索の場合
						AddSql = AddSql + " " + whereData[i];
					}

					//カラム名の付与
					AddSql = " " + whereDataName[i] + " " + AddSql;

					if (isAddWhere) { // WHERE句２つ目以降の条件には先頭にANDを付与
						AddSql = " AND" + AddSql;
					} else { // WHERE句最初の条件には先頭にWHEREを付与
						AddSql = " WHERE" + AddSql;
						isAddWhere = true;
					}

					//SQLへの加筆
					query = query + AddSql;
				}
			}
			AddSql = null;
		}

		return query;
	}

	// ORDER BY句の生成
	public String sqlOrderBy(SearchCondition sc) {
		//ORDER BY句をここに生成する
		String query = " ORDER BY ";

		int sort = sc.getSort(); //ソート条件の取得
		int lift = sc.getLift(); //昇順・降順の取得

		switch (sort) {
		case 0: // 発売日でソート
			query = query + salesDateColumn;
			break;
		case 1: // ISBNでソート
			query = query + "isbn";
			break;
		case 2: // 在庫数のソート
			query = query + "stock";
			break;
		}
		if (lift == 1) { // 昇順の場合
			query = query + " ASC";
		} else { // 降順の場合
			query = query + " DESC";
		}

		return query;
	}

	// LIMIT句の生成
	public String sqlLimit(SearchCondition sc) {
		//LIMIT句をここに生成する
		String query = null;
		//表ページ数の取得
		int page = sc.getPage();

		//LIMIT句の生成
		query = " LIMIT " + String.valueOf((page - 1) * 200) + ", 200";

		return query;
	}

	// クエリの最後のセミコロン
	public String sqlSemicolon() {
		return ";";
	}
}
