package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * 復元画面サーブレット
 * @version 1.1
 * 復元画面サーブレット新規作成
 * 総件数の取得しページ数を計算・セッションに総研数とページ数を保存
 * 該当書籍が存在するか判定
 * @version 1.2
 *入力値の表示
 * @version 1.3
 * 入力値の初期値リセットを抑制
 * @version 1.4
 * ページ数の入力値チェック（９桁）を追記
 * @author hiroki tajima
 */
@WebServlet("/Restoration")
public class RestorationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String bookName = null;
		String author = null;
		String publisher = null;
		String isbn = null;
		String salesDate = null;
		String stock = null;
		String salesDateFlag = "equals";
		String stockFlag = "equals";
		int page = 1;	//1ページ目
		int sort = 0;	//発売日
		int lift = 1;	//昇順

		int count = 0;
		int pageCount = 0;

		HttpSession session = request.getSession();

		SearchCondition sc = (SearchCondition)session.getAttribute("RestorationForm");
		if(sc == null) {
			sc = new SearchCondition();
		}

		//書籍検索
		BookDataAccess bda = new BookDataAccess();
		List<Book> bookList = new ArrayList<Book>();

		try {
			//総件数の取得
			count = bda.countAllDeleat(sc);
			pageCount = (count + 199) / 200;

			//総件数・最大ページ数をセッションに設定
			session.setAttribute("count", count);
			session.setAttribute("maxPage", pageCount);

			//検索
			bookList = bda.findDeleat(sc);

			//該当なし
			if (bookList.isEmpty()) {
				session.setAttribute("msg", "該当する書籍は見つかりませんでした。");
			}

			//書籍情報・現ページをセッションに設定
			session.setAttribute("items", bookList);
			session.setAttribute("page", page);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/RestrationForm.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {

			e.printStackTrace();
			//セッションの破棄
			request.getSession().invalidate();
			//セッションの再生成
			request.getSession(true);
			request.getSession().setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/loginForm");

		} catch (Exception e) {

			e.printStackTrace();
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/RestrationForm.jsp");
			dispatcher.forward(request, response);

		}


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		session.setAttribute("msg", "");

		String branch = null;

		if(!request.getParameter("form").isEmpty()) {
			branch = request.getParameter("form");
		}

		String bookName = null;		//書籍名
		String author = null;			//著者
		String publisher = null;		//出版社
		String isbn = null;			//ISBN
		String salesDate = null;		//発売日
		String stock = null;			//在庫数
		String salesDateFlag = "equals";	//発売日プルダウンフラグ
		String stockFlag = "equals";		//在庫数プルダウンフラグ
		int page = 1;	//1ページ目
		int sort = 0;	//発売日
		int lift = 1;	//昇順

		int count = 0;
		int pageCount = 0;

		SearchCondition sc = (SearchCondition)session.getAttribute("RestorationForm");
		if(sc == null) {
			sc = new SearchCondition();
		}

		//入力フォームの初期値設定
		switch (branch) {
		case "復元":
			//初期値の設定
			sc.setName(bookName);
			sc.setAuthor(author);
			sc.setPublisher(publisher);
			sc.setIsbn(isbn);
			sc.setSalesDate(salesDate);
			sc.setStock(stock);
			sc.setSalesDateFlag(salesDateFlag);
			sc.setStockFlag(stockFlag);
			sc.setPage(page);

			break;

			//検索ボタン押下
		case "検索":

			//検索条件取得
			bookName = request.getParameter("name");
			author = request.getParameter("author");
			publisher = request.getParameter("publisher");
			isbn = request.getParameter("isbn");
			salesDate = request.getParameter("date");
			stock = request.getParameter("stock");
			salesDateFlag = request.getParameter("beforeAfter");
			stockFlag = request.getParameter("largeOrSmall");

			//入力値チェック
			if (!isbn.matches("^[0-9]*$") || 13 < isbn.length() || !stock.matches("^[0-9]*$")) {

				session.setAttribute("msg", "指定されている形式で入力してください。");
				break;

			}

			sc.setName(bookName);
			sc.setAuthor(author);
			sc.setPublisher(publisher);
			sc.setIsbn(isbn);
			sc.setSalesDate(salesDate);
			sc.setStock(stock);
			sc.setSalesDateFlag(salesDateFlag);
			sc.setStockFlag(stockFlag);
			sc.setPage(page);

			break;

			//ページ移動
		case "ページ":
			//ページ番号の空白判定
			String pageNumberCheck = null;
			pageNumberCheck = request.getParameter("page");
			if (pageNumberCheck == null || pageNumberCheck.isEmpty()) {

				session.setAttribute("msg", "ページ番号を入力してください。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/RestrationForm.jsp");
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
				session.setAttribute("msg", "指定されている形式で入力してください。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/RestrationForm.jsp");
				dispatcher.forward(request, response);
				return;
			}





			int maxPage = (int) session.getAttribute("maxPage");
			if (maxPage < page) {

				session.setAttribute("msg", "該当するページは見つかりませんでした。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/RestrationForm.jsp");
				dispatcher.forward(request, response);
				return;

			}

			sc.setPage(page);

			break;


			//ソート
		case "ソート":
			sort = Integer.parseInt(request.getParameter("index"));
			lift = Integer.parseInt(request.getParameter("direction"));
			sc.setPage(1);
			sc.setSort(sort);
			sc.setLift(lift);

			break;

		}

		//検索条件をセッションに設定
		session.setAttribute("RestorationForm", sc);

		//書籍検索
		BookDataAccess bda = new BookDataAccess();
		List<Book> bookList = new ArrayList<Book>();

		try {
			//総件数の取得
			if (branch.equals("検索") || branch.equals("復元")) {

				count = bda.countAllDeleat(sc);
				pageCount = (count + 199) / 200;

				//総件数・最大ページ数をセッションに設定
				session.setAttribute("count", count);
				session.setAttribute("maxPage", pageCount);

			}

			//検索
			bookList = bda.findDeleat(sc);

			//該当なし
			if (bookList.isEmpty()) {

				session.setAttribute("msg", "該当する書籍は見つかりませんでした。");

			}

			//書籍情報・現ページをセッションに設定
			session.setAttribute("items", bookList);
			session.setAttribute("page", page);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/RestrationForm.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {

			e.printStackTrace();
			//セッションの破棄
			request.getSession().invalidate();
			//セッションの再生成
			request.getSession(true);
			request.getSession().setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/loginForm");

		} catch (Exception e) {

			e.printStackTrace();
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/RestrationForm.jsp");
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

