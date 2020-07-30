package jp.co.acroit.zaiko2020.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jp.co.acroit.zaiko2020.book.Book;

/**
 * 書籍データベースアクセスクラス
 * @version 3.2
 * @author hiroki tajima
 */
public class BookDataAccess {

	public BookDataAccess() {
		//接続情報を格納する。
		url = "jdbc:mysql://localhost/zaiko2020?characterEncoding=UTF-8&serverTimezone=JST&zeroDateTimeBehavior=convertToNull";
		driver = "com.mysql.cj.jdbc.Driver";
		username = "root";
		password = "";
//		username = "tomcat";
//		password = "RC2-Z%b9e85PWqR";

		//カラム名を格納する。
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

	//書籍検索（deleatflg=0）を行うメソッド
	public List<Book> find(SearchCondition sc) throws SQLException {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);

			//クエリの生成・実行を行う。
			query = "SELECT * FROM books";
			//検索条件＋deleatflg = 0
			query = query + sqlWhere(sc) + " AND " + deleteflgColumn + " = 0 " + sqlOrderBy(sc) + sqlLimit(sc) + ";";
			System.out.println(query);
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();


			int dbId = 0;
			String dbBookName = null;
			String dbPublisher = null;
			String dbAuthor = null;
			String dbIsbn = null;
			LocalDate dbSalsDate;
			String dbPrice = null;
			String dbStock = null;
			int dbDeleteflg = 0;

			List<Book> bookList = new ArrayList<Book>();

			while (rs.next()) {
				dbId = rs.getInt(idColumn);
				dbBookName = rs.getString(titleColumn);
				dbPublisher = rs.getString(publisherColumn);
				dbAuthor = rs.getString(authorColumn);
				dbIsbn = rs.getString(isbnColumn);
				dbSalsDate = rs.getDate(salesDateColumn).toLocalDate();
				dbPrice = rs.getString(priceColumn);
				dbStock = rs.getString(stockColumn);
				dbDeleteflg = rs.getInt(deleteflgColumn);
				Book aBook = new Book(dbId, dbBookName, dbPublisher, dbAuthor, dbIsbn, dbSalsDate, dbPrice, dbStock,
						dbDeleteflg);
				bookList.add(aBook);
			}
			rs.close();
			con.close();
			con = null;

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

	//書籍検索（deleatflg=1）を行うメソッド
		public List<Book> findDeleat(SearchCondition sc) throws SQLException {
			Connection con = null;
			try {
				con = DriverManager.getConnection(url, username, password);

				//クエリの生成・実行を行う。
				query = "SELECT * FROM books";
				//検索条件＋deleatflg = 0
				query = query + sqlWhere(sc) + " AND " + deleteflgColumn + " = 1 " + sqlOrderBy(sc) + sqlLimit(sc) + ";";
				System.out.println(query);
				PreparedStatement ps = con.prepareStatement(query);
				ResultSet rs = ps.executeQuery();


				int dbId = 0;
				String dbBookName = null;
				String dbPublisher = null;
				String dbAuthor = null;
				String dbIsbn = null;
				LocalDate dbSalsDate;
				String dbPrice = null;
				String dbStock = null;
				int dbDeleteflg = 0;

				List<Book> bookList = new ArrayList<Book>();

				while (rs.next()) {
					dbId = rs.getInt(idColumn);
					dbBookName = rs.getString(titleColumn);
					dbPublisher = rs.getString(publisherColumn);
					dbAuthor = rs.getString(authorColumn);
					dbIsbn = rs.getString(isbnColumn);
					dbSalsDate = rs.getDate(salesDateColumn).toLocalDate();
					dbPrice = rs.getString(priceColumn);
					dbStock = rs.getString(stockColumn);
					dbDeleteflg = rs.getInt(deleteflgColumn);
					Book aBook = new Book(dbId, dbBookName, dbPublisher, dbAuthor, dbIsbn, dbSalsDate, dbPrice, dbStock,
							dbDeleteflg);
					bookList.add(aBook);
				}
				rs.close();
				con.close();
				con = null;

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

	//idによる書籍の検索を行うメソッド
	public Book findId(int id) throws SQLException {

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);

			//クエリの生成・実行を行う。
			query = "SELECT * FROM books WHERE " + idColumn + "=" + id + ";";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			int dbId = 0;
			String dbBookName = null;
			String dbPublisher = null;
			String dbAuthor = null;
			String dbIsbn = null;
			LocalDate dbSalsDate = null;
			String dbPrice = null;
			String dbStock = null;
			int dbDeleteflg = 0;

			while (rs.next()) {

				dbId = rs.getInt(idColumn);
				dbBookName = rs.getString(titleColumn);
				dbPublisher = rs.getString(publisherColumn);
				dbAuthor = rs.getString(authorColumn);
				dbIsbn = rs.getString(isbnColumn);
				dbSalsDate = rs.getDate(salesDateColumn).toLocalDate();
				dbPrice = rs.getString(priceColumn);
				dbStock = rs.getString(stockColumn);
				dbDeleteflg = rs.getInt(deleteflgColumn);

			}
			rs.close();
			con.close();
			con = null;

			return new Book(dbId, dbBookName, dbPublisher, dbAuthor, dbIsbn, dbSalsDate, dbPrice, dbStock,
					dbDeleteflg);
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

	//DB在庫数更新処理を行うメソッド
	public Book update(int id, int input) throws SQLException {
			Connection con = null;
			try {

				con = DriverManager.getConnection(url, username, password);

				//オートコミットOFFにする。
				con.setAutoCommit(false);
				query = "SELECT " + stockColumn + " FROM books WHERE " + idColumn + "=" + id + " for update;";
				PreparedStatement ps1 = con.prepareStatement(query);
				ps1.executeQuery();
				ResultSet rs1 = ps1.executeQuery();
				rs1.next();

				int stock = rs1.getInt(stockColumn);

				//DBを更新する値の計算を行う。(出荷の場合はinputにマイナスの値が入る)
				int updateStock = stock + input;

				//入荷・出荷が可能なのか、判定を行う。
				if(updateStock < 0  || 999999 < updateStock) {
					con.rollback();
					throw new IndexOutOfBoundsException("入荷数超過または出荷数超過");
				}

				//更新クエリ
				query = "UPDATE books SET " + stockColumn + " = " + updateStock + " WHERE " + idColumn + " = " + id + ";";
				PreparedStatement ps2 = con.prepareStatement(query);
				ps2.executeUpdate();

				//更新後の書籍情報を取得取得する。
				query = "SELECT id,title,author,publisher,salesDate,isbn,price,stock,deleteflg FROM books WHERE " + idColumn + "=" + id + ";";
				PreparedStatement ps3 = con.prepareStatement(query);
				ResultSet rs2 = ps3.executeQuery();

				int dbId = 0;
				String dbBookName = null;
				String dbPublisher = null;
				String dbAuthor = null;
				String dbIsbn = null;
				LocalDate dbSalsDate = null;
				String dbPrice = null;
				String dbStock = null;
				int dbDeleteflg = 0;

				while (rs2.next()) {

					dbId = rs2.getInt(idColumn);
					dbBookName = rs2.getString(titleColumn);
					dbPublisher = rs2.getString(publisherColumn);
					dbAuthor = rs2.getString(authorColumn);
					dbIsbn = rs2.getString(isbnColumn);
					dbSalsDate = rs2.getDate(salesDateColumn).toLocalDate();
					dbPrice = rs2.getString(priceColumn);
					dbStock = rs2.getString(stockColumn);
					dbDeleteflg = rs2.getInt(deleteflgColumn);

				}

				rs1.close();
				rs2.close();

				//問題がなければコミットを行う。
				con.commit();
				con.close();
				con = null;

				Book book = new Book(dbId, dbBookName, dbPublisher, dbAuthor, dbIsbn, dbSalsDate, dbPrice, dbStock,
						dbDeleteflg);

				return book;

			} catch (SQLException e) {

				//エラー発生時にロールバックを行う。
				if(con != null) {
					con.rollback();
				}
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

	//総件数(deleatflg=0)の取得を行うメソッド
	public int countAll(SearchCondition sc) throws SQLException {
		Connection con = null;

		try {
			con = DriverManager.getConnection(url, username, password);
			//クエリの生成・実行を行う。
			query = "SELECT COUNT(*) AS libraryHoldings FROM books";
			query = query + sqlWhere(sc) + " AND " + deleteflgColumn + " = 0 " + sqlOrderBy(sc) + ";";

			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			int libraryHoldings = 0;

			//該当する書籍の総研の取得を行う。
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

	//総件数(deleatflg=1)の取得を行うメソッド
		public int countAllDeleat(SearchCondition sc) throws SQLException {
			Connection con = null;

			try {
				con = DriverManager.getConnection(url, username, password);
				//クエリの生成・実行を行う。
				query = "SELECT COUNT(*) AS libraryHoldings FROM books";
				query = query + sqlWhere(sc) +" AND " + deleteflgColumn + " = 1 " + sqlOrderBy(sc) + ";";

				PreparedStatement ps = con.prepareStatement(query);
				ResultSet rs = ps.executeQuery();

				int libraryHoldings = 0;

				//該当する書籍の総研の取得を行う。
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

	


	//書籍の追加を行うメソッド
	public void add(Book book) throws SQLException {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);

			//オートコミットOFFにする。
			con.setAutoCommit(false);
			//クエリの生成・実行を行う。
			query = "INSERT INTO books (title,author,publisher,salesDate,isbn,price,stock,deleteflg)" +
					"VALUES ('" + book.getName() + "','" + book.getAuthor() + "','" + book.getPublisher() + "','" + book.getSalesDate() + "','" + book.getIsbn() + "'," + book.getPrice() + "," + book.getStock() + ",0)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.executeUpdate();
			System.out.println(query);

			//問題がなければコミットを行う。
			con.commit();

			con.close();
			con = null;

			return;
		} catch (SQLException e) {
			//エラー発生時にロールバックを行う。
			if(con != null) {
				con.rollback();
			}

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


	//書籍の追加後に追加した書籍を取得するメソッド
	public Book addSearch(Book book) throws SQLException {
			Connection con = null;

			try {

				con = DriverManager.getConnection(url, username, password);

				//クエリの生成・実行を行う。
				query = "SELECT * FROM books WHERE " + titleColumn  + " = '" + book.getName() + "' AND " + authorColumn   + " = '" + book.getAuthor() + "' AND " + publisherColumn  + " = '" + book.getPublisher() + "' AND " + salesDateColumn   + " = '" + book.getSalesDate() + "' AND " + isbnColumn   + " = " + book.getIsbn() + " AND " + priceColumn   + " = " + book.getPrice() + " AND " + stockColumn   + " = " + book.getStock() + ";";
				System.out.println(query);
				PreparedStatement ps = con.prepareStatement(query);
				ResultSet rs = ps.executeQuery();

				int dbId = 0;
				String dbBookName = null;
				String dbPublisher = null;
				String dbAuthor = null;
				String dbIsbn = null;
				LocalDate dbSalsDate = null;
				String dbPrice = null;
				String dbStock = null;
				int dbDeleteflg = 0;

				while (rs.next()) {

					dbId = rs.getInt(idColumn);
					dbBookName = rs.getString(titleColumn);
					dbPublisher = rs.getString(publisherColumn);
					dbAuthor = rs.getString(authorColumn);
					dbIsbn = rs.getString(isbnColumn);
					dbSalsDate = rs.getDate(salesDateColumn).toLocalDate();
					dbPrice = rs.getString(priceColumn);
					dbStock = rs.getString(stockColumn);
					dbDeleteflg = rs.getInt(deleteflgColumn);

				}
				rs.close();
				con.close();
				con = null;

				return new Book(dbId, dbBookName, dbPublisher, dbAuthor, dbIsbn, dbSalsDate, dbPrice, dbStock,
						dbDeleteflg);
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

	//書籍の削除を行うメソッド
	public void delete(int id) throws SQLException {
		Connection con = null;
		try {

			con = DriverManager.getConnection(url, username, password);

			//オートコミットOFFにする。
			con.setAutoCommit(false);
			//削除対象のレコードをロック
			query = "SELECT " + deleteflgColumn + " FROM books WHERE " + idColumn + "=" + id + " for update;";
			PreparedStatement ps1 = con.prepareStatement(query);
			ps1.executeQuery();
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();

			//削除クエリ
			query = "UPDATE books SET " + deleteflgColumn + " = 1 WHERE " + idColumn + " = " + id
					+ ";";
			PreparedStatement ps2 = con.prepareStatement(query);
			ps2.executeUpdate();

			rs1.close();

			//問題がなければコミットを行う。
			con.commit();
			con.close();
			con = null;

		} catch (SQLException e) {

			//エラー発生時にロールバックを行う。
			if(con != null) {
				con.rollback();
			}
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

	//書籍の編集を行うメソッド
	public void edit(Book book,int id) throws SQLException {
		Connection con = null;
		try {

			System.out.println("-----------");
			con = DriverManager.getConnection(url, username, password);

			//オートコミットOFFにする。
			con.setAutoCommit(false);
			//更新対象のレコードをロック
			query = "SELECT " + titleColumn + "," + publisherColumn + "," + authorColumn + "," + salesDateColumn + "," + isbnColumn + "," + priceColumn + "," + stockColumn + " FROM books WHERE " + idColumn + " = " + id + " for update;";
			System.out.println(query);
			PreparedStatement ps1 = con.prepareStatement(query);
			ps1.executeQuery();
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();

			//更新クエリ
			query = "UPDATE books SET " + titleColumn + " = '" + book.getName() + "'," + publisherColumn + " = '" + book.getPublisher() + "'," + authorColumn + " = '" + book.getAuthor() + "'," + salesDateColumn + " = '" + book.getSalesDate() + "'," + isbnColumn + " = '" + book.getIsbn() + "'," + priceColumn + " = " + book.getPrice() + "," + stockColumn + " = " + book.getStock() + " WHERE " + idColumn + " = " + id + ";";
			System.out.println(query);
			PreparedStatement ps2 = con.prepareStatement(query);
			ps2.executeUpdate();

			System.out.println("成功");

			rs1.close();

			//問題がなければコミットを行う。
			con.commit();
			con.close();
			con = null;

		} catch (SQLException e) {

			//エラー発生時にロールバックを行う。
			if(con != null) {
				con.rollback();
			}
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

	//書籍の復元を行うメソッド
	public void restoration(String[] checkedId) throws SQLException {

		String where = "";
		int last = checkedId.length;

		//WHERE条件の作成
		for (int i = 0 ; i < checkedId.length ; i++) {
			System.out.println(i);
			System.out.println(checkedId[i]);
			if(i == checkedId.length - 1) {
				where = where + idColumn + " = " + checkedId[i];
			} else {
				where = where + idColumn + " = " + checkedId[i] + " OR ";
			}
		}


		Connection con = null;
		try {

			con = DriverManager.getConnection(url, username, password);

			//オートコミットOFFにする。
			con.setAutoCommit(false);
			//更新対象のレコードをロック
			query = "SELECT " + deleteflgColumn + " FROM books WHERE " + where + " for update;";
			PreparedStatement ps1 = con.prepareStatement(query);
			ps1.executeQuery();
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();
			
			//復元クエリの作成と実行
			query = "UPDATE books SET " + deleteflgColumn + " = 0 WHERE " + where + ";";
			PreparedStatement ps2 = con.prepareStatement(query);
			ps2.executeUpdate();

			rs1.close();

			//問題がなければコミットを行う。
			con.commit();
			con.close();
			con = null;

		} catch (SQLException e) {

			//エラー発生時にロールバックを行う。
			if(con != null) {
				con.rollback();
			}
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


	//WHERE句の生成を行うメソッド
	public String sqlWhere(SearchCondition sc) {
		//生成した文字列の一時保存を行う。
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

		//queryに継ぎ足す部分の一時保存場所
		String AddSql = null;

		//WHERE句で用いるカラム名
		String[] whereDataName = { titleColumn, publisherColumn, authorColumn,
				isbnColumn, salesDateColumn, stockColumn };
		//WHERE句で用いるフィールド
		String[] whereData = { bookName, publisher, author, isbn, salsDate, stock };

		//WHERE句を一度でも加えたか否か
		boolean isAddWhere = false;

		for (int i = 0; i < whereData.length; i++) {

			//条件が空白でない場合
			if (whereData[i] != null && !whereData[i].isEmpty()) {

				//比較演算子の付与
				switch (whereDataName[i]) {
				case "salesDate": //発売日の場合
					switch (salsDateFlag) {
					case "equals": //～に一致
						AddSql = "=";
						break;
					case "before": //～以前
						AddSql = "<=";
						break;
					case "after": //～以降
						AddSql = ">=";
						break;
					}
					break;
				case "stock": //在庫数の場合
					switch (stockFlag) {
					case "lt": //～未満
						AddSql = "<";
						break;
					case "ltoe": //～以下
						AddSql = "<=";
						break;
					case "equals": //～に等しい
						AddSql = "=";
						break;
					case "gtoe": //～以上
						AddSql = ">=";
						break;
					case "gt": //～より多い
						AddSql = ">";
						break;
					}
					break;
				default: //上記以外(あいまい検索)の場合
					AddSql = "LIKE";
				}

				//検索条件が指定なしでない場合にSQLへの加筆を行う
				if (AddSql != null && !AddSql.isEmpty()) {

					//フィールドの付与
					if (AddSql.equals("LIKE")) { //文字列の検索の場合
						AddSql = AddSql + " '%" + whereData[i] + "%'";
					} else if (whereDataName[i].equals(salesDateColumn)) { //発売日の検索の場合
						AddSql = AddSql + " '" + whereData[i] + "'";
					} else { //数値の検索の場合
						AddSql = AddSql + " " + whereData[i];
					}

					//カラム名の付与
					AddSql = " " + whereDataName[i] + " " + AddSql;

					if (isAddWhere) { //WHERE句２つ目以降の条件には先頭にANDを付与
						AddSql = " AND" + AddSql;
					} else { //WHERE句最初の条件には先頭にWHEREを付与
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

	//ORDER BY句の生成
	public String sqlOrderBy(SearchCondition sc) {
		//生成した文字列の一時保存
		String query = " ORDER BY ";

		int sort = sc.getSort(); //ソート条件の取得
		int lift = sc.getLift(); //昇順・降順の取得

		//生成
		switch (sort) {
		case 0: //発売日でソート
			query = query + salesDateColumn;
			break;
		case 1: //ISBNでソート
			query = query + "isbn";
			break;
		case 2: //在庫数のソート
			query = query + "stock";
			break;
		}
		if (lift == 1) { //昇順の場合
			query = query + " ASC";
		} else { //降順の場合
			query = query + " DESC";
		}
		return query;
	}

	//LIMIT句の生成
	public String sqlLimit(SearchCondition sc) {
		//生成した文字列の一時保存
		String query = null;

		//表ページ数の取得
		int page = sc.getPage();

		//生成
		query = " LIMIT " + String.valueOf((page - 1) * 200) + ", 200";
		return query;
	}

}
