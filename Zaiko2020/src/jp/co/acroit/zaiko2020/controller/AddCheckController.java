package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 追加確認サーブレット
 * @version 1.0
 * @author hiroki tajima
 */
@WebServlet("/AddCheck")
public class AddCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		String title = null;
		String publisher = null;
		String author = null;
		String isbn = null;
		String date = null;
		int price = 0;
		int stock = 0;
		int dbDeleteflg = 0;

		title = request.getParameter("bookname");
		publisher = request.getParameter("publisher");
		author = request.getParameter("author");
		isbn = request.getParameter("isbn");
		date = request.getParameter("date");
		price = Integer.parseInt(request.getParameter("price"));
		stock = Integer.parseInt(request.getParameter("stock"));

		HttpSession session = request.getSession();
		session.setAttribute("titile", title);
		session.setAttribute("publisher", publisher);
		session.setAttribute("author", author);
		session.setAttribute("isbn", isbn);
		session.setAttribute("date", date);
		session.setAttribute("price", price);
		session.setAttribute("stock", stock);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/AddCheck.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
