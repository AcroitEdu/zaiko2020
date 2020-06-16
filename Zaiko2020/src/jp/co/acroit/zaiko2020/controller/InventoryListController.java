package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class InventoryLIstContoller
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
        // TODO Auto-generated constructor stub
    }

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//書記条件
		String book = null;				//書籍名
		String publisher = null;		//出版社
		String author = null;			//著者
		String salsDate = null;		//発売日
		String stock = null;			//在庫数
		String salsDateFlag = null;	//発売日検索条件
		String stockFlag = null;		//在庫数検索条件
		String page = "0";				//表ページ数
		String sort = "0";				//ソート条件
		String lift = "-1";			//昇順降順 true:1,false:-1
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String book = null;				//書籍名
		String publisher = null;		//出版社
		String author = null;			//著者
		String salsDate = null;		//発売日
		String stock = null;			//在庫数
		String salsDateFlag = null;	//発売日検索条件
		String stockFlag = null;		//在庫数検索条件
		String page = "0";				//表ページ数
		String sort = "0";				//ソート条件
		String lift = "-1";			//昇順降順 true:1,false:-1

		HttpSession session = request.getSession();

		SearchCondition sc = new SearchCondition();
			switch(0) {
			//検索ボタン
			case 0:

				book = request.getParameter("book");
				publisher = request.getParameter("publisher");
				author = request.getParameter("author");
				salsDate = request.getParameter("salsdate");
				stock = request.getParameter("stock");
				salsDateFlag = request.getParameter("befororafter");
				stockFlag = request.getParameter("largeorsmoll");

				sc.setName(book);
				sc.setName(publisher);
				sc.setName(author);
				sc.setName(salsDate);
				sc.setName(stock);
				sc.setName(salsDateFlag);
				sc.setName(page);
				sc.setName(sort);
				sc.setName(lift);

				break;

			//表ページ移動
			case 1:
				page = request.getParameter("");
				sc.setPage(page);

				break;

			//ソート
			case 2:
				sort = request.getParameter("");
				lift = request.getParameter("");
				sc.setName(sort);
				sc.setName(lift);

			}





		session.setAttribute("conditions", sc);

		//書籍検索
		BookDataAccess bda = new BookDataAccess();
		List<Book> bookList;
		try{
			//bookList = bda.find(sc);

			//session.setAttribute("itemes", bookList);
		}catch (Exception e) {
			// TODO: handle exception
		}


	}

}
