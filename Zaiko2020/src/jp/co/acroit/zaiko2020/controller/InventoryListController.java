package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.acroit.zaiko2020.book.Book;
import jp.co.acroit.zaiko2020.data.BookDataAccess;
import jp.co.acroit.zaiko2020.data.SearchCondition;

/**
 * 在庫一覧サーブレット
 * @version 1.0
 * @author hiroki tajima
 */
@WebServlet("/inventoryList")
public class InventoryListController extends HttpServlet {
	private BookDataAccess bookDataAccess;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InventoryListController() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		//初期表示の検索条件設定
		String bookName = null;				//書籍名
		String author = null;					//著者
		String publisher = null;				//出版社
		String isbn = null;					//isbn
		String salsDate = sdf.format(date);	//発売日
		String stock = "0";						//在庫数
		String salsDateFlag = "after";			//発売日検索条件
		String stockFlag = "gtoe";				//在庫数検索条件
		int page = 1;							//表ページ数
		String sort = "0";						//ソート条件:発売日
		String lift = "-1";						//降順:-1

		SearchCondition sc = new SearchCondition();

		sc.setName(bookName);
		sc.setAuthor(author);
		sc.setPublisher(publisher);
		sc.setIsbn(isbn);
		sc.setSalesDate(salsDate);
		sc.setStock(stock);
		sc.setSalsDateFlag(salsDateFlag);
		sc.setStockFlag(stockFlag);
		sc.setPage(page);
		sc.setSort(sort);
		sc.setLift(lift);

		//書籍検索
		BookDataAccess bda = new BookDataAccess();

		int count = 0;
		int pageCount = 0;

		List<Book> bookList;
		try{
			//総件数の取得
			//count = bda.countAll();
			pageCount = count / 200 + 1;
			//総件数と最大ページ数をセッションに設定
			request.getSession().setAttribute("count", count);
			request.getSession().setAttribute("maxPage", pageCount);

			bookList = bda.find(sc);

			request.getSession().setAttribute("items", bookList);

			//該当書籍なし
			if(bookList.isEmpty()) {
				request.getSession().setAttribute("error", "該当する書籍は見つかりませんでした。");
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
			dispatcher.forward(request, response);

		}catch (Exception e) {
			request.getSession().setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
			dispatcher.forward(request, response);
		}



	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
System.out.println("---------------------");
		request.setCharacterEncoding("UTF-8");
		String bookName = null;		//書籍名
		String author = null;			//著者
		String publisher = null;		//出版社
		String isbn = null;			//isbn
		String salsDate = null;		//発売日
		String stock = "0";				//在庫数
		String salsDateFlag = null;	//発売日検索条件
		String stockFlag = "gtoe";		//在庫数検索条件
		int page = 1;					//表ページ数
		String sort = "0";				//ソート条件
		String lift = "-1";				//昇順降順 1/-1

		int value = Integer.parseInt(request.getParameter("form"));

		SearchCondition sc = new SearchCondition();
		switch(value) {
		//検索ボタン
		case 0:

			System.out.println("case0------------------------------------------------");
			//検索条件取得
			bookName = request.getParameter("bookName");
			author = request.getParameter("author");
			publisher = request.getParameter("publisher");
			isbn = request.getParameter("isbn");
			salsDate = request.getParameter("salsdate");
			stock = request.getParameter("stock");
			salsDateFlag = request.getParameter("beforeAfter");
			stockFlag = request.getParameter("largeOrSmall");

			sc.setName(bookName);
			sc.setAuthor(author);
			sc.setPublisher(publisher);
			sc.setIsbn(isbn);
			sc.setSalesDate(salsDate);
			sc.setStock(stock);
			sc.setSalsDateFlag(salsDateFlag);
			sc.setStockFlag(stockFlag);
			sc.setPage(page);
			sc.setSort(sort);
			sc.setLift(lift);



			System.out.println("case0終了--------------------------------------------");
			break;

			//表ページ移動
		case 1:

			System.out.println("case1------------------------------------------------");
			//表ページ取得
			page = Integer.parseInt(request.getParameter("page"));

			int maxPage = (int)request.getSession().getAttribute("maxPage");
			if(maxPage < page) {
				request.getSession().setAttribute("error", "該当するページは見つかりませんでした。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
				dispatcher.forward(request, response);
			}
			sc.setPage(page);

			System.out.println("case1終了--------------------------------------------");
			break;

			//ソート
		case 2:

			System.out.println("case2------------------------------------------------");
			//ソート条件取得
			sort = request.getParameter("index");
			lift = request.getParameter("direction");
			sc.setSort(sort);
			sc.setLift(lift);
			System.out.println("case2終了--------------------------------------------");
		}

		request.getSession().setAttribute("conditions", sc);

		//書籍検索
		BookDataAccess bda = new BookDataAccess();
		//検索結果の総件数取得
		if(value == 0) {
			int count = 0;
			int pageCount = 0;
			//count = bda.countAll();
			pageCount = count / 200 + 1;
			//総件数と最大ページ数をセッションに設定
			request.getSession().setAttribute("count", count);
			request.getSession().setAttribute("maxPage", pageCount);
		}
		List<Book> bookList;
		try{
			bookList = bda.find(sc);
			if(bookList.isEmpty()) {
				request.getSession().setAttribute("error", "該当する書籍は見つかりませんでした。");
			}

			//書籍情報をセッションに設定
			request.getSession().setAttribute("items", bookList);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
			dispatcher.forward(request, response);

		}catch (Exception e) {
			request.getSession().setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
			dispatcher.forward(request, response);
		}

	}

}
