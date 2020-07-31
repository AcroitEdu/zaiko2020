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

		String branch = null;

		String title = null;
		String publisher = null;
		String author = null;
		String isbn = null;
		LocalDate salsDate = null;
		String price = null;
		String stock = null;

		HttpSession session = request.getSession();
		session.setAttribute("error", "");

		try {

			if (request.getParameter("form") == null) {
				branch = "エラー";
			} else {
				branch = request.getParameter("form");
			}

			switch(branch) {
			case "追加":
				System.out.println("スイッチ文");
				Book resetbook = new Book(0, null, null, null, null, null, null, null, 0);

				resetbook.setName(title);
				resetbook.setPublisher(publisher);
				resetbook.setAuthor(author);
				resetbook.setIsbn(isbn);
				resetbook.setSalesDate(salsDate);
				resetbook.setPrice(price);
				resetbook.setStock(stock);

				session.setAttribute("book", resetbook);
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/AddForm.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {

			e.printStackTrace();
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/inventoryList");

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);



	}

}
