package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.acroit.zaiko2020.book.Book;
import jp.co.acroit.zaiko2020.data.BookDataAccess;

/**
 * 追加処理サーブレット
 * @version 1.0
 * @author hiroki tajima
 */
@WebServlet("/AddProcess")
public class AddProcessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		String title = null;
//		String publisher = null;
//		String author = null;
//		String isbn = null;
//		String date = null;
//		int price = 0;
//		int stock = 0;
//		int dbDeleteflg = 0;

		HttpSession session = request.getSession();
		Book book = (Book)session.getAttribute("items");


		BookDataAccess bda = new BookDataAccess();
		try {
			bda.add(book);
		} catch (SQLException e) {

			session.setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/inventoryList");

		} catch (Exception e) {
			//エラーを返しリダイレクト
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
//			dispatcher.forward(request, response);
			response.sendRedirect("/Zaiko2020/logout");
			e.printStackTrace();

		}
//		session.getAttribute("title");
//		session.setAttribute("publisher", publisher);
//		session.setAttribute("author", author);
//		session.setAttribute("isbn", isbn);
//		session.setAttribute("date", date);
//		session.setAttribute("price", price);
//		session.setAttribute("stock", stock);





	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
