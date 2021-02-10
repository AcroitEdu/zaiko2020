package jp.co.acroit.zaiko2020.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.acroit.zaiko2020.history.History;

/**
 * 操作履歴データベースアクセスクラス
 * @version 1.0
 * Phase 3
 * @author yohei nishida
 *
 */

public class HistoryDataAccess {

	public HistoryDataAccess() {
		//接続情報を格納する。
		url = "jdbc:mysql://localhost/zaiko2020?characterEncoding=UTF-8&serverTimezone=JST&zeroDateTimeBehavior=convertToNull";
		driver = "com.mysql.cj.jdbc.Driver";
		username = "root";
		password = "";

		//カラム名を格納する。
		idColumn = "id";
		dateColumn = "date";
		timeColumn = "time";
		userIdColumn = "user_id";
		bookIdColumn = "book_id";
		operationIdColumn = "operation_id";
		libraryHoldingsColumn = "libraryHoldings";

	}

	String url;
	String driver;
	String username;
	String password;
	String query;
	String idColumn;
	String dateColumn;
	String timeColumn;
	String userIdColumn;
	String bookIdColumn;
	String operationIdColumn;
	String libraryHoldingsColumn;

	/* 履歴作成を行うメソッド */
	public void InsertHistory(long userId, int bookId, int operationId) throws SQLException {

		long startTime = System.currentTimeMillis(); //forCalc

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);

			//日付・時刻の生成
			Date date = new Date();
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
			String operationDate = sdfDate.format(date);
			System.out.println(operationDate);
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
			String operationTime = sdfTime.format(date);
			System.out.println(operationTime);

			//クエリの生成・実行を行う。
			query = "INSERT INTO `histories` (`date`, `time`, `user_id`, `book_id`, `operation_id`) "
					+ "VALUES (?, ?, ?, ?, ?);";

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, operationDate);
			ps.setString(2, operationTime);
			ps.setLong(3, userId);
			ps.setInt(4, bookId);
			ps.setInt(5, operationId);
			System.out.println(query);
			long intervalTime = System.currentTimeMillis(); //forCalc
			ps.executeUpdate();

			con.close();
			con = null;

			long endTime = System.currentTimeMillis();
			System.out.println("処理時間(途中)：" + (intervalTime - startTime) + " ms");
			System.out.println("処理時間(途中以降)：" + (endTime - intervalTime) + " ms");
			System.out.println("BookDAO:処理時間：" + (endTime - startTime) + " ms" + System.getProperty("line.separator")); //forCalc


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
	/* 履歴作成を行うメソッドここまで */

	/* 履歴検索を行うメソッド */
	//一覧画面に表示する履歴の取得
	public List<History> find(SearchCondition sc) throws SQLException {

		long startTime = System.currentTimeMillis(); //forCalc

		//検索用日付の生成
			Date date = new Date();
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
			String operationDate = sdfDate.format(date);

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);

			//クエリの生成・実行を行う。(テーブル結合有・カラム名は履歴テーブルに対応させる)
			query = "SELECT histories.id AS id, `date`, `time`, user.name AS user_id, "
					+ "books.title AS book_id, operations.name AS operation_id FROM `histories` "
					+ "INNER JOIN user ON histories.user_id = user.id "
					+ "INNER JOIN books ON histories.book_id = books.id "
					+ "INNER JOIN operations ON histories.operation_id = operations.id";

			query = query + " WHERE " + sqlWhere(sc) + sqlLimit(sc) + ";";

			PreparedStatement ps = con.prepareStatement(query);
			System.out.println("履歴SQL： "+ query);
			long intervalTime = System.currentTimeMillis(); //forCalc
			ResultSet rs = ps.executeQuery();

			int dbId = 0;
			LocalDate dbOperationDate;
//			LocalDate dbOperationTime;
			LocalTime dbOperationTime;
			String dbUserId = null;
			String dbBookId = null;
			String dbOperationId = null;


			List<History> historyList = new ArrayList<History>();

			while (rs.next()) {

				dbId = rs.getInt(idColumn);
				dbOperationDate = rs.getDate(dateColumn).toLocalDate();
//				dbOperationTime = rs.getDate(timeColumn).toLocalDate();
				dbOperationTime = rs.getTime(timeColumn).toLocalTime();
				System.out.println(dbOperationTime);
				dbUserId = rs.getString(userIdColumn);
				dbBookId = rs.getString(bookIdColumn);
				dbOperationId = rs.getString(operationIdColumn);
				History aHistory = new History(dbId, dbOperationDate, dbOperationTime,
						dbUserId, dbBookId, dbOperationId);
				historyList.add(aHistory);

			}

			rs.close();
			con.close();
			con = null;

			long endTime = System.currentTimeMillis();
			System.out.println("処理時間(途中)：" + (intervalTime - startTime) + " ms");
			System.out.println("処理時間(途中以降)：" + (endTime - intervalTime) + " ms");
			System.out.println("BookDAO:処理時間：" + (endTime - startTime) + " ms" + System.getProperty("line.separator")); //forCalc

			return historyList;

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
	/* 履歴検索を行うメソッドここまで */

	/* 総件数の取得を行うメソッド */
	//検索条件にヒットした履歴の総件数を取得
	public int countAll(SearchCondition sc) throws SQLException {

		Connection con = null;

		try {

			con = DriverManager.getConnection(url, username, password);
			//総件数取得クエリ
			query = "SELECT COUNT(*) AS libraryHoldings FROM histories "
			+ "INNER JOIN user ON histories.user_id = user.id "
			+ "INNER JOIN books ON histories.book_id = books.id "
			+ "INNER JOIN operations ON histories.operation_id = operations.id";
			query = query + " WHERE " + sqlWhere(sc) + " ;";
			System.out.println("総件数SQL：" + query);

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
	/* 総件数の取得を行うメソッドここまで */

	/* WHERE句の生成を行うメソッド*/
	public String sqlWhere(SearchCondition sc) {
		//生成した文字列の一時保存を行う。
		String query = "";

		//各検索条件の取得
		String operationDate = sc.getOperationDate();
		String operationDateFlag = sc.getOperationDateFlag();
		String userId = sc.getUserId();
		int operation = sc.getOperation();

		//queryに継ぎ足す部分の一時保存場所
		String AddSql = null;

		//検索条件生成
		//日付絞り込み(必須)

		switch (operationDateFlag) {
			case "equals":
				AddSql = " = ";
				break;
			case "lower":
				AddSql = " <= ";
				break;
			case "higher":
				AddSql = " >= ";
				break;

		}
		query = dateColumn + AddSql + "'" +  operationDate + "'";

		//操作絞り込み(必須)
		if (operation == 99) {
			AddSql = " <> ";
		} else {
			AddSql = " = ";
		}

		query = query + " AND " + operationIdColumn + AddSql + operation;

		//実行者(完全一致のみ)
		if(userId != null) {
			AddSql = " AND user.name = " + "'" + userId + "'";
			query = query + AddSql;
		}

		return query;
	}
	/* WHERE句の生成を行うメソッド此処まで*/

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
}


