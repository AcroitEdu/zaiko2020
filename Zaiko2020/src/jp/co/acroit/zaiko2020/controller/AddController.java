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
 * 追加画面サーブレット
 * @version 1.0
 * @author hiroki tajima
 */
@WebServlet("/Add")
public class AddController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String Branch = null;
		Branch = request.getParameter("button");

		switch(Branch) {
		case "実行":
			HttpSession session = request.getSession();
			session.setAttribute("titile", "");
			session.setAttribute("publisher", "");
			session.setAttribute("author", "");
			session.setAttribute("isbn", "");
			session.setAttribute("date", "");
			session.setAttribute("price", "");
			session.setAttribute("stock", "");
			break;
		}

//		HttpSession session = request.getSession();
//		session.setAttribute("titile", "");
//		session.setAttribute("publisher", "");
//		session.setAttribute("author", "");
//		session.setAttribute("isbn", "");
//		session.setAttribute("date", "");
//		session.setAttribute("price", "");
//		session.setAttribute("stock", "");

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/AddForm.jsp");
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
