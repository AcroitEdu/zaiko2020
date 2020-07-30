package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.acroit.zaiko2020.book.Book;

/**
 * Servlet implementation class EditChekController
 */
@WebServlet("/EditCheck")
public class EditCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String title = null;
		String publisher = null;
		String author = null;
		String isbn = null;
		Date day = null;
		LocalDate salsDate = null;
		String price = null;
		String stock = null;
		int deleteFlg = 0;


		HttpSession session = request.getSession();

		try {

			//入力値のnull判定
			if(request.getParameter("bookName").isEmpty() || request.getParameter("publisher").isEmpty() || request.getParameter("author").isEmpty() || request.getParameter("isbn").isEmpty() || request.getParameter("date").isEmpty() || request.getParameter("price").isEmpty() || request.getParameter("stock").isEmpty()) {
				throw new NullPointerException();
			}

			title = request.getParameter("bookName");
			publisher = request.getParameter("publisher");
			author = request.getParameter("author");
			isbn = request.getParameter("isbn");
			day = sdf.parse(request.getParameter("date"));
			salsDate = day.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			price = request.getParameter("price");
			stock = request.getParameter("stock");

			//文字チェック
			if (!isbn.matches("^[0-9]*$") || !stock.matches("^[0-9]*$") || !isbn.matches("^[0-9]*$") || 13 == isbn.length()) {

				session.setAttribute("error", "指定されている形式で入力してください。");
				response.sendRedirect("/Zaiko2020/Add");
				return;
			}

			//値の範囲チェック
			if(Integer.parseInt(price) < 1 || 999999 < Integer.parseInt(price) || Integer.parseInt(stock) < 1 || 999999 < Integer.parseInt(stock) ) {
				session.setAttribute("error", "指定されている形式で入力してください。");
				response.sendRedirect("/Zaiko2020/Add");
				return;
			}


			Book addbook = new Book(0, null, null, null, null, null, null, null, 0);

			addbook.setId((int)session.getAttribute("id"));
			addbook.setName(title);
			addbook.setPublisher(publisher);
			addbook.setAuthor(author);
			addbook.setIsbn(isbn);
			addbook.setSalesDate(salsDate);
			addbook.setPrice(price);
			addbook.setStock(stock);
			addbook.setDeleteFlag(deleteFlg);

			session.setAttribute("book", addbook);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/EditCheck.jsp");
			dispatcher.forward(request, response);
		} catch (NullPointerException e) {
			e.printStackTrace();
			session.setAttribute("error", "指定されている形式で入力してください。");
			response.sendRedirect("/Zaiko2020/Edit");
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/Edit");
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
