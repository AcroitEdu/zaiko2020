package jp.co.acroit.zaiko2020.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class bookDAO {

	public static void main(String[] args) {

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

		long startTime = System.currentTimeMillis(); //forCalc

		Connection con = null;

		try {
			con = DriverManager.getConnection(url, username, password);

			//クエリの生成・実行を行う。
			query = "SELECT * FROM books";
			//検索条件＋deleatflg = 0
			String loginUser = "root";
			String viewName = "searchViewBy" + loginUser;
			String mainQuery = "SELECT `id`,`title`, `publisher` ,`author`,`salesDate`,`isbn`,`price`,`stock`, `deleteflg` FROM books WHERE `salesDate` <= '2021-01-06' AND `stock` >= 0 AND `deleteflg` = 0";
			String queryCreateView = "CREATE VIEW OR REPLACE " + viewName + " AS " + mainQuery;
			String queryOrderBy = "SELECT * FROM " + viewName + " ORDER BY salesDate ASC";
			query = "SELECT `title`,`author`,`salesDate`,`isbn`,`price`,`stock`FROM books WHERE `salesDate` <= '2021-01-06' AND `stock` >= 0 AND `deleteflg` = 0";



			long intervalTime = System.currentTimeMillis(); //forCalc
			PreparedStatement ps = con.prepareStatement(queryCreateView);
			ps.executeUpdate();
			ps = con.prepareStatement(queryOrderBy);
			ResultSet rs = ps.executeQuery();

			rs.close();
			con.close();
			con = null;

			long endTime = System.currentTimeMillis();
			System.out.println("処理時間(途中)：" + (intervalTime - startTime) + " ms");
			System.out.println("処理時間(途中以降)：" + (endTime - intervalTime) + " ms");
			System.out.println("BookDAO:処理時間：" + (endTime - startTime) + " ms" + System.getProperty("line.separator")); //forCalc
			System.out.println(query);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();

		}catch (Exception e ) {
			// TODO: handle exception

		}finally {
			if (con != null) {

				try {

					con.close();

				} catch (Exception ignore) {

				}
			}
		}

	}

}
