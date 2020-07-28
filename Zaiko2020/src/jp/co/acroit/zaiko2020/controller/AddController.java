package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.acroit.zaiko2020.book.Book;

/**
 * 追加サーブレット
 * @version 1.1
 * @author hiroki tajima
 */
@WebServlet("/Add")
public class AddController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);

		String branch = null;

		String title = null;
		String publisher = null;
		String author = null;
		String isbn = null;
		LocalDate salsDate = null;
		int price = 0;
		int stock = 0;
		int deleteFlg = 0;

		HttpSession session = request.getSession();

		try {

			branch = request.getParameter("button");
			System.out.println(branch);

			switch(branch) {
			case "追加":
				Book addbook = new Book(0, null, null, null, null, null, 0, 0, 0);

				addbook.setName(title);
				addbook.setPublisher(publisher);
				addbook.setAuthor(author);
				addbook.setIsbn(isbn);
				addbook.setSalesDate(salsDate);
				addbook.setPrice(price);
				addbook.setStock(stock);
				addbook.setDeleteFlag(deleteFlg);

				session.setAttribute("book", addbook);

				break;
			}

//			HttpSession session = request.getSession();
//			session.setAttribute("titile", "");
//			session.setAttribute("publisher", "");
//			session.setAttribute("author", "");
//			session.setAttribute("isbn", "");
//			session.setAttribute("date", "");
//			session.setAttribute("price", "");
//			session.setAttribute("stock", "");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/AddForm.jsp");
			dispatcher.forward(request, response);

		}catch (Exception e) {

			e.printStackTrace();
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/inventoryList");
		}

	}

}
