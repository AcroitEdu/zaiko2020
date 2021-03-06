package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.acroit.zaiko2020.book.Book;
import jp.co.acroit.zaiko2020.data.BookDataAccess;
import jp.co.acroit.zaiko2020.data.SearchCondition;

/**
 * 在庫一覧サーブレット
 * @version 1.1
 * @version 1.2
 * 追加画面・復元画面から在庫一覧画面に遷移した場合に在庫数と最大ページ数を取得し直すように修正
 * @version 1.3
 * ページ数の入力値チェック（９桁）を追記
 * @version 1.4
 * ページ数の入力桁数を3桁に変更、在庫数上限桁数を10桁に変更
 * @author yohei nishida
 */
@WebServlet("/inventoryList")
public class InventoryListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		session.setAttribute("flg", false);


		//初期表示の検索条件設定
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String bookName = null;
		String author = null;
		String publisher = null;
		String isbn = null;
		String salsDate = sdf.format(date);
		String stock = "0";
		String salsDateFlag = "after";
		String stockFlag = "gtoe";
		int page = 1;	//１ページ
		int sort = 0;	//発売日
		int lift = 1;	//昇順
		int totalLimitedCount = 50000;


		SearchCondition sc = (SearchCondition)session.getAttribute("conditions");
		//検索条件がない
		if(sc == null) {
			sc = new SearchCondition();

			//初期値設定
			sc.setName(bookName);
			sc.setAuthor(author);
			sc.setPublisher(publisher);
			sc.setIsbn(isbn);
			sc.setSalesDate(salsDate);
			sc.setStock(stock);
			sc.setSalesDateFlag(salsDateFlag);
			sc.setStockFlag(stockFlag);
			sc.setPage(page);
			sc.setSort(sort);
			sc.setLift(lift);
		}

		//書籍検索
		BookDataAccess bda = new BookDataAccess();
		List<Book> bookList = new ArrayList<Book>();

		try {
			int count = 0;
			int pageCount = 0;
			boolean limitedFlag = false;

			//総件数の取得
			count = bda.countAll(sc);

			if (count > totalLimitedCount) {

				limitedFlag = true;
				count = totalLimitedCount;

			}

			//総ページ数
			pageCount = (count + 199) / 200;

			//総件数・現ページ・最大ページ数をセッションに設定
			session.setAttribute("count", count);
			session.setAttribute("page", page);
			session.setAttribute("maxPage", pageCount);

			//検索
			bookList = bda.find(sc);

			//検索結果・検索条件をセッションに設定
			session.setAttribute("items", bookList);
			session.setAttribute("conditions", sc);

			//該当書籍なし
			if (bookList.isEmpty()) {

				session.setAttribute("error", "該当する書籍は見つかりませんでした。");

			}else if (limitedFlag) {

				session.setAttribute("error", "5万件以上ヒットしています。検索条件で絞り込んで下さい。");
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {

			e.printStackTrace();
			//セッションの破棄
//			request.getSession().invalidate();
			//セッションの再生成
//			request.getSession(true);
			request.getSession().setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
			dispatcher.forward(request, response);
//			response.sendRedirect("/Zaiko2020/loginForm");

		} catch (Exception e) {

			e.printStackTrace();
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
			dispatcher.forward(request, response);

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();


		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String bookName = null;
		String author = null;
		String publisher = null;
		String isbn = null;
		String salsDate = sdf.format(date);
		String stock = "0";
		String salsDateFlag = "after";
		String stockFlag = "gtoe";
		int page = 1;	//１ページ
		int sort = 0;	//発売日
		int lift = 1;	//昇順
		int value = Integer.parseInt(request.getParameter("form"));
		int totalLimitedCount = 50000;


		session.setAttribute("flg", false);
		SearchCondition sc = (SearchCondition)session.getAttribute("conditions");
		session.setAttribute("error", "");

		if(sc == null) {

			sc = new SearchCondition();

		}

		if(value == 3) {

			//初期値設定
			sc.setName(bookName);
			sc.setAuthor(author);
			sc.setPublisher(publisher);
			sc.setIsbn(isbn);
			sc.setSalesDate(salsDate);
			sc.setStock(stock);
			sc.setSalesDateFlag(salsDateFlag);
			sc.setStockFlag(stockFlag);
			sc.setPage(page);
			sc.setSort(sort);
			sc.setLift(lift);
		}

		switch (value) {
		//検索ボタン
		case 0:
			//検索条件取得
			bookName = request.getParameter("name");
			author = request.getParameter("author");
			publisher = request.getParameter("publisher");
			isbn = request.getParameter("isbn");
			salsDate = request.getParameter("date");
			stock = request.getParameter("stock");
			salsDateFlag = request.getParameter("beforeAfter");
			stockFlag = request.getParameter("largeOrSmall");

			//入力値チェック
			if (!isbn.matches("^[0-9]*$") || 13 < isbn.length() || !stock.matches("^[0-9]*$") || 6 < stock.length()) {

				session.setAttribute("error", "指定されている形式で入力してください。");
				break;

			}

			sc.setName(bookName);
			sc.setAuthor(author);
			sc.setPublisher(publisher);
			sc.setIsbn(isbn);
			sc.setSalesDate(salsDate);
			sc.setStock(stock);
			sc.setSalesDateFlag(salsDateFlag);
			sc.setStockFlag(stockFlag);
			sc.setPage(page);

			break;

			//表ページ移動
		case 1:
			//ページ番号の空白判定
			String pageNumberCheck = null;
			pageNumberCheck = request.getParameter("page");
			if (pageNumberCheck == null || pageNumberCheck.isEmpty()) {

				session.setAttribute("error", "ページ番号を入力してください。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
				dispatcher.forward(request, response);
				return;

			}

			//入力値チェック
			if (!pageNumberCheck.matches("^[0-9]*$") || page < 1) {

				session.setAttribute("error", "指定されている形式で入力してください。");
				break;

			}

			if(area(pageNumberCheck)) {
				page = Integer.parseInt(request.getParameter("page"));
			}else {
				session.setAttribute("error", "指定されている形式で入力してください。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
				dispatcher.forward(request, response);
				return;
			}





			int maxPage = (int) session.getAttribute("maxPage");
			if (maxPage < page) {

				session.setAttribute("error", "該当するページは見つかりませんでした。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
				dispatcher.forward(request, response);
				return;

			}

			sc.setPage(page);
			break;

			//ソート
		case 2:
			sort = Integer.parseInt(request.getParameter("index"));
			lift = Integer.parseInt(request.getParameter("direction"));
			System.out.println(sort);
			System.out.println(lift);
			sc.setPage(1);
			sc.setSort(sort);
			sc.setLift(lift);
			break;

		}

		//検索をセッションに設定
		session.setAttribute("conditions", sc);

		//書籍検索
		BookDataAccess bda = new BookDataAccess();
		List<Book> bookList = new ArrayList<Book>();

		boolean limitedFlag = false;

		try {

			if(session.getAttribute("id") != null) {
				bda.flgReturn((int)session.getAttribute("id"));
				session.setAttribute("id", null);
			}

			//総件数の取得
			if (value == 0 || value == 3) {

				int count = 0;
				int pageCount = 0;
				count = bda.countAll(sc);
				if (count >= totalLimitedCount) {

					limitedFlag = true;
					count = totalLimitedCount;

				}
				pageCount = (count + 199) / 200;

				//総件数・最大ページ数をセッションに設定
				session.setAttribute("count", count);
				session.setAttribute("maxPage", pageCount);

			}

			//検索
			bookList = bda.find(sc);

			//該当なし
			if (bookList.isEmpty()) {

				session.setAttribute("error", "該当する書籍は見つかりませんでした。");

			}else if (limitedFlag) {

				session.setAttribute("error", "5万件以上ヒットしています。5万件までを表示しています。<br>検索条件で絞り込んで下さい。");
			}

			//書籍情報・現ページをセッションに設定
			session.setAttribute("items", bookList);
			session.setAttribute("page", page);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {

			e.printStackTrace();
			//セッションの破棄
//			request.getSession().invalidate();
			//セッションの再生成
//			request.getSession(true);
			request.getSession().setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
			dispatcher.forward(request, response);
//			response.sendRedirect("/Zaiko2020/loginForm");

		} catch (Exception e) {

			e.printStackTrace();
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
			dispatcher.forward(request, response);

		}

	}

	public boolean area(String pageNumberCheck) {
		try {
			int area = Integer.parseInt(pageNumberCheck);
			return true;
		}catch (NumberFormatException  e) {
			return false;
		}

	}

}
