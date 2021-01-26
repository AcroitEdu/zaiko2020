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
 * @version 4.0
 * Phase4追記
 * 追加・編集・削除処理追記
 * @version 4.1
 * 書籍検索deleteflg=0をWHEREに追加・deleteflg=1の書籍検索メソッド追加
 * 総件数取得deleteflg=0をWHEREに追加・deleteflg=1の総件数取得メソッド追加
 * @version 4.2
 * 更新フラグの判定・更新フラグを立てる・更新フラグを戻すメソッド追加
 * @version 4.3
 * dbsalsDate→dbsalesDateに修正
 * dbDeleteflg→dbDeleteFlagに修正
 * salsDate→salesDateに修正
 * deleteflg→deleteFlagに修正
 * @author hiroki tajima
 */
public class BookDataAccess {

	public BookDataAccess() {

		//接続情報を格納する。
		url = "jdbc:mysql://localhost/zaiko2020?characterEncoding=UTF-8&serverTimezone=JST&zeroDateTimeBehavior=convertToNull";
		driver = "com.mysql.cj.jdbc.Driver";
		username = "root";
		password = "";

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
		updateflgColumn = "updateflg";
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
	String updateflgColumn;
	String libraryHoldingsColumn;

	/* 書籍検索（deleatflg=0）を行うメソッド */
	//一覧画面に表示する書籍の取得
	public List<Book> find(SearchCondition sc) throws SQLException {

		long startTime = System.currentTimeMillis(); //forCalc

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);

			//クエリの生成・実行を行う。
			query = "SELECT `id`,`title`, `publisher` ,`author`,`salesDate`,`isbn`,`price`,`stock`, `deleteflg` FROM books";
			//検索条件＋deleatflg = 0
			query = query + " WHERE " + sqlWhere(sc) + " " + deleteflgColumn + " = 0 " + sqlOrderBy(sc) + sqlLimit(sc) + ";";

			PreparedStatement ps = con.prepareStatement(query);
			System.out.println(query);
			long intervalTime = System.currentTimeMillis(); //forCalc
			ResultSet rs = ps.executeQuery();

			int dbId = 0;
			String dbBookName = null;
			String dbPublisher = null;
			String dbAuthor = null;
			String dbIsbn = null;
			LocalDate dbSalesDate;
			String dbPrice = null;
			String dbStock = null;
			int dbDeleteFlag = 0;

			List<Book> bookList = new ArrayList<Book>();

			while (rs.next()) {

				dbId = rs.getInt(idColumn);
				dbBookName = rs.getString(titleColumn);
				dbPublisher = rs.getString(publisherColumn);
				dbAuthor = rs.getString(authorColumn);
				dbIsbn = rs.getString(isbnColumn);
				dbSalesDate = rs.getDate(salesDateColumn).toLocalDate();
				dbPrice = rs.getString(priceColumn);
				dbStock = rs.getString(stockColumn);
				dbDeleteFlag = rs.getInt(deleteflgColumn);
				Book aBook = new Book(dbId, dbBookName, dbPublisher, dbAuthor, dbIsbn, dbSalesDate, dbPrice, dbStock,
						dbDeleteFlag);
				bookList.add(aBook);

			}

			rs.close();
			con.close();
			con = null;

			long endTime = System.currentTimeMillis();
			// DB処理速度算出用
			System.out.println("処理時間(途中)：" + (intervalTime - startTime) + " ms");
			System.out.println("処理時間(途中以降)：" + (endTime - intervalTime) + " ms");
			System.out.println("BookDAO:処理時間：" + (endTime - startTime) + " ms" + System.getProperty("line.separator")); //forCalc
			// DB処理速度算出用 此処まで

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
	/* 書籍検索（deleatflg=0）を行うメソッドここまで */


	/* 書籍検索（deleatflg=1）を行うメソッド */
	//復元画面に表示する書籍の取得
	public List<Book> findDeleat(SearchCondition sc) throws SQLException {

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);

			//クエリの生成・実行を行う。
			query = "SELECT * FROM books";
			//検索条件＋deleatflg = 0
			query = query + " WHERE " + sqlWhere(sc) + " " + deleteflgColumn + " = 1 " + sqlOrderBy(sc) + sqlLimit(sc) + ";";

			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();


			int dbId = 0;
			String dbBookName = null;
			String dbPublisher = null;
			String dbAuthor = null;
			String dbIsbn = null;
			LocalDate dbSalesDate;
			String dbPrice = null;
			String dbStock = null;
			int dbDeleteFlag = 0;

			List<Book> bookList = new ArrayList<Book>();

			while (rs.next()) {

				dbId = rs.getInt(idColumn);
				dbBookName = rs.getString(titleColumn);
				dbPublisher = rs.getString(publisherColumn);
				dbAuthor = rs.getString(authorColumn);
				dbIsbn = rs.getString(isbnColumn);
				dbSalesDate = rs.getDate(salesDateColumn).toLocalDate();
				dbPrice = rs.getString(priceColumn);
				dbStock = rs.getString(stockColumn);
				dbDeleteFlag = rs.getInt(deleteflgColumn);
				Book aBook = new Book(dbId, dbBookName, dbPublisher, dbAuthor, dbIsbn, dbSalesDate, dbPrice, dbStock,
						dbDeleteFlag);
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
	/* 書籍検索（deleatflg=1）を行うメソッドここまで */

	/* 総件数(deleatflg=0)の取得を行うメソッド */
	//検索条件にヒットした書籍の総件数を取得（復元画面）
	public int countAll(SearchCondition sc) throws SQLException {

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);
			//総件数取得クエリ・検索条件＋削除flgが0の書籍
			query = "SELECT COUNT(*) AS libraryHoldings FROM books";
			query = query + " WHERE " + sqlWhere(sc) + " " + deleteflgColumn + " = 0 " + sqlOrderBy(sc) + ";";

			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			int libraryHoldings = 0;

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
	/* 総件数(deleatflg=0)の取得を行うメソッドここまで */

	/* 総件数(deleatflg=1)の取得を行うメソッド */
	//検索条件にヒットした書籍の総件数を取得（一覧画面）
	public int countAllDeleat(SearchCondition sc) throws SQLException {

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);
			//総件数取得クエリ・検索条件＋削除flgが1の書籍
			query = "SELECT COUNT(*) AS libraryHoldings FROM books";
			query = query + " WHERE " + sqlWhere(sc) + " " + deleteflgColumn + " = 1 " + sqlOrderBy(sc) + ";";

			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			int libraryHoldings = 0;

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
	/* 総件数(deleatflg=1)の取得を行うメソッドここまで */

	/* WHERE句の生成を行うメソッド */
	public String sqlWhere(SearchCondition sc) {

		//生成した文字列の一時保存を行う。
		String query = "";

		//各検索条件の取得
		String bookName = sc.getName();
		String publisher = sc.getPublisher();
		String author = sc.getAuthor();
		String isbn = sc.getIsbn();
		String salesDate = sc.getSalesDate();
		String stock = sc.getStock();
		String salesDateFlag = sc.getSalesDateFlag();
		String stockFlag = sc.getStockFlag();

		//queryに継ぎ足す部分の一時保存場所
		String AddSql = null;

		//WHERE句で用いるカラム名
		String[] whereDataName = { titleColumn, publisherColumn, authorColumn,
				isbnColumn, salesDateColumn, stockColumn };
		//WHERE句で用いるフィールド
		String[] whereData = { bookName, publisher, author, isbn, salesDate, stock };

		//WHERE句を一度でも加えたか否か
		boolean isAddWhere = false;

		for (int i = 0; i < whereData.length; i++) {

			//条件が空白でない場合
			if (whereData[i] != null && !whereData[i].isEmpty()) {

				//比較演算子の付与
				switch (whereDataName[i]) {

				case "salesDate": //発売日の場合
					switch (salesDateFlag) {
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

						//						AddSql = " WHERE" + AddSql;
						isAddWhere = true;

					}

					//SQLへの加筆
					query = query + AddSql;

				}
			}

			AddSql = null;

		}

		if(!query.isEmpty()) {
			query = query + " AND ";

		}

		return query;

	}
	/* WHERE句の生成を行うメソッドここまで */

	/* ORDER BY句の生成 */
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
	/* ORDER BY句の生成ここまで */

	/* LIMIT句の生成 */
	public String sqlLimit(SearchCondition sc) {

		//生成した文字列の一時保存
		String query = null;

		//表ページ数の取得
		int page = sc.getPage();

		//生成
		query = " LIMIT " + String.valueOf((page - 1) * 200) + ", 200";
		return query;

	}
	/* LIMIT句の生成ここまで */

	/* idによる書籍の検索を行うメソッド */
	//一意の書籍の検索
	//一覧画面から入荷・出荷・編集画面に遷移する際に選択した書籍をidをもとに取得する
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
			LocalDate dbSalesDate = null;
			String dbPrice = null;
			String dbStock = null;
			int dbDeleteFlag = 0;

			while (rs.next()) {

				dbId = rs.getInt(idColumn);
				dbBookName = rs.getString(titleColumn);
				dbPublisher = rs.getString(publisherColumn);
				dbAuthor = rs.getString(authorColumn);
				dbIsbn = rs.getString(isbnColumn);
				dbSalesDate = rs.getDate(salesDateColumn).toLocalDate();
				dbPrice = rs.getString(priceColumn);
				dbStock = rs.getString(stockColumn);
				dbDeleteFlag = rs.getInt(deleteflgColumn);

			}

			rs.close();
			con.close();
			con = null;

			return new Book(dbId, dbBookName, dbPublisher, dbAuthor, dbIsbn, dbSalesDate, dbPrice, dbStock,
					dbDeleteFlag);

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
	/* idによる書籍の検索を行うメソッドここまで */

	/* DB在庫数更新処理を行うメソッド */
	//入荷・出荷による在庫数の更新
	public Book update(int id, int input) throws SQLException {

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);

			//オートコミットOFFにする。
			con.setAutoCommit(false);
			query = "SELECT " + stockColumn + " FROM books WHERE " + idColumn + "=" + id + " AND " + deleteflgColumn + " = 0 for update;";
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
			query = "UPDATE books SET " + stockColumn + " = " + updateStock + " WHERE " + idColumn + " = " + id + " AND " + deleteflgColumn + " = 0;";
			PreparedStatement ps2 = con.prepareStatement(query);
			ps2.executeUpdate();

			//更新後の書籍情報を取得取得する。
			query = "SELECT id,title,author,publisher,salesDate,isbn,price,stock,deleteflg FROM books WHERE " + idColumn + "=" + id + " AND " + deleteflgColumn + " = 0;";
			PreparedStatement ps3 = con.prepareStatement(query);
			ResultSet rs2 = ps3.executeQuery();

			int dbId = 0;
			String dbBookName = null;
			String dbPublisher = null;
			String dbAuthor = null;
			String dbIsbn = null;
			LocalDate dbSalesDate = null;
			String dbPrice = null;
			String dbStock = null;
			int dbDeleteFlag = 0;

			while (rs2.next()) {

				dbId = rs2.getInt(idColumn);
				dbBookName = rs2.getString(titleColumn);
				dbPublisher = rs2.getString(publisherColumn);
				dbAuthor = rs2.getString(authorColumn);
				dbIsbn = rs2.getString(isbnColumn);
				dbSalesDate = rs2.getDate(salesDateColumn).toLocalDate();
				dbPrice = rs2.getString(priceColumn);
				dbStock = rs2.getString(stockColumn);
				dbDeleteFlag = rs2.getInt(deleteflgColumn);

			}

			rs1.close();
			rs2.close();

			//問題がなければコミットを行う。
			con.commit();
			con.close();
			con = null;

			Book book = new Book(dbId, dbBookName, dbPublisher, dbAuthor, dbIsbn, dbSalesDate, dbPrice, dbStock,
					dbDeleteFlag);

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
	/* DB在庫数更新処理を行うメソッドここまで */

	/* 書籍の追加を行うメソッド */
	public void add(Book book) throws SQLException {

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);

			//オートコミットOFFにする。
			con.setAutoCommit(false);

			//追加クエリ
			query = "INSERT INTO books (title,author,publisher,salesDate,isbn,price,stock,deleteflg)" +
					"VALUES ('" + book.getName() + "','" + book.getAuthor() + "','" + book.getPublisher() +
					"','" + book.getSalesDate() + "','" + book.getIsbn() + "'," + book.getPrice() + "," + book.getStock() + ",0)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.executeUpdate();

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
	/* 書籍の追加を行うメソッドここまで */

	/* 追加した書籍を取得するメソッド */
	//結果確認画面に表示するために、入力された書籍の情報をもとに追加した書籍を取得
	public Book addSearch(Book book) throws SQLException {

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);

			//追加した書籍の取得
			query = "SELECT * FROM books WHERE " + titleColumn  + " = '" + book.getName() + "' AND " + authorColumn   + " = '" + book.getAuthor() + "' AND " + publisherColumn  + " = '" + book.getPublisher() + "' AND " + salesDateColumn   + " = '" + book.getSalesDate() + "' AND " + isbnColumn   + " = " + book.getIsbn() + " AND " + priceColumn   + " = " + book.getPrice() + " AND " + stockColumn   + " = " + book.getStock() + " AND " + deleteflgColumn + " = 0;";

			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			int dbId = 0;
			String dbBookName = null;
			String dbPublisher = null;
			String dbAuthor = null;
			String dbIsbn = null;
			LocalDate dbSalesDate = null;
			String dbPrice = null;
			String dbStock = null;
			int dbDeleteFlag = 0;

			while (rs.next()) {

				dbId = rs.getInt(idColumn);
				dbBookName = rs.getString(titleColumn);
				dbPublisher = rs.getString(publisherColumn);
				dbAuthor = rs.getString(authorColumn);
				dbIsbn = rs.getString(isbnColumn);
				dbSalesDate = rs.getDate(salesDateColumn).toLocalDate();
				dbPrice = rs.getString(priceColumn);
				dbStock = rs.getString(stockColumn);
				dbDeleteFlag = rs.getInt(deleteflgColumn);

			}
			rs.close();
			con.close();
			con = null;

			return new Book(dbId, dbBookName, dbPublisher, dbAuthor, dbIsbn, dbSalesDate, dbPrice, dbStock,
					dbDeleteFlag);

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
	/* 追加した書籍を取得するメソッドここまで */

	/* 編集する書籍のflgを立てるメソッド */
	public void flg(int id) throws SQLException {

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);

			//オートコミットOFFにする。
			con.setAutoCommit(false);
			//更新対象のレコードをロック
			query = "SELECT " + updateflgColumn+ " FROM books WHERE " + idColumn + "=" + id + " AND " + deleteflgColumn + " = 0 for update;";
			PreparedStatement ps1 = con.prepareStatement(query);
			ps1.executeQuery();
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();

			//更新flgを立てるクエリ
			query = "UPDATE books SET " + updateflgColumn + " = 1 WHERE " + idColumn + " = " + id + " AND " + deleteflgColumn + " = 0;";

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
	/* 編集する書籍のflgを立てるメソッドここまで */

	/* 編集中の書籍のflgを戻すメソッド */
	public void flgReturn(int id) throws SQLException {

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);

			//オートコミットOFFにする。
			con.setAutoCommit(false);
			//更新対象のレコードのカラムをロック
			query = "SELECT " + updateflgColumn+ " FROM books WHERE " + idColumn + "=" + id + " AND " + deleteflgColumn + " = 0 for update;";
			PreparedStatement ps1 = con.prepareStatement(query);
			ps1.executeQuery();
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();

			//更新flgを戻すクエリ
			query = "UPDATE books SET " + updateflgColumn + " = 0 WHERE " + idColumn + " = " + id + " AND " + deleteflgColumn + " = 0;";
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
	/* 編集中の書籍のflgを戻すメソッド */

	/* 選択した書籍が編集中かbooleanで返すメソッド */
	public boolean flgCheck(int id) throws SQLException {

		Connection con = null;

		int updateFlg = 0;

		try {

			con = DriverManager.getConnection(url, username, password);

			//選択された書籍の更新flgを取得するクエリ
			query = "SELECT " + updateflgColumn+ " FROM books WHERE " + idColumn + "=" + id + " AND " + deleteflgColumn + " = 0;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.executeQuery();
			ResultSet rs = ps.executeQuery();
			rs.next();
			updateFlg = rs.getInt(updateflgColumn);
			rs.close();
			con.close();
			con = null;

			return updateFlg == 1;

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
	/* 選択した書籍が編集中かbooleanで返すメソッドここまで */

	/* 書籍の編集を行うメソッド */
	public void edit(Book book,int id) throws SQLException {

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);

			//オートコミットOFFにする。
			con.setAutoCommit(false);
			//更新対象のレコードのカラムをロック
			query = "SELECT " + titleColumn + "," + publisherColumn + "," + authorColumn + "," + salesDateColumn + "," + isbnColumn + "," + priceColumn + "," + stockColumn + " FROM books WHERE " + idColumn + " = " + id + " AND " + deleteflgColumn + " = 0 for update;";

			PreparedStatement ps1 = con.prepareStatement(query);
			ps1.executeQuery();
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();

			//更新クエリの作成と実行
			query = "UPDATE books SET " + titleColumn + " = '" + book.getName() + "'," + publisherColumn + " = '" + book.getPublisher() + "'," + authorColumn + " = '" + book.getAuthor() + "'," + salesDateColumn + " = '" + book.getSalesDate() + "'," + isbnColumn + " = '" + book.getIsbn() + "'," + priceColumn + " = " + book.getPrice() + "," + stockColumn + " = " + book.getStock() + " WHERE " + idColumn + " = " + id + " AND " + deleteflgColumn + " = 0;";
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
	/* 書籍の編集を行うメソッドここまで */

	/* 書籍の削除を行うメソッド */
	public void delete(int id) throws SQLException {

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);

			//オートコミットOFFにする。
			con.setAutoCommit(false);

			//削除対象のレコードのカラムをロック
			query = "SELECT " + deleteflgColumn + " FROM books WHERE " + idColumn + "=" + id +  " AND " + deleteflgColumn + " = 0 for update;";
			PreparedStatement ps1 = con.prepareStatement(query);
			ps1.executeQuery();
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();

			//削除クエリ
			query = "UPDATE books SET " + deleteflgColumn + " = 1 WHERE " + idColumn + " = " + id + " AND " + deleteflgColumn + " = 0;";
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
	/* 書籍の削除を行うメソッドここまで */

	/* 書籍の復元を行うメソッド */
	public void restoration(String[] checkedId) throws SQLException {

		//WHERE条件の作成
		String where = "";

		for (int i = 0 ; i < checkedId.length ; i++) {

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

			//更新対象のレコードのカラムをロック
			query = "SELECT " + deleteflgColumn + " FROM books WHERE " + where + " AND " + deleteflgColumn + " = 1 for update;";
			PreparedStatement ps1 = con.prepareStatement(query);
			ps1.executeQuery();
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();

			//復元クエリの作成と実行
			query = "UPDATE books SET " + deleteflgColumn + " = 0 WHERE " + where + " AND " + deleteflgColumn + " = 1;";
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
	/* 書籍の復元を行うメソッドここまで */

}
