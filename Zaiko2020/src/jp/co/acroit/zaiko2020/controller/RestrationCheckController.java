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
 * 復元書籍確認一覧
 * @version 1.0
 * @author yohei nishida
 */
@WebServlet("/RestrationCheck")
public class RestrationCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//共用変数
	int sumIds = 0;
	int pages = 0;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();

		String[] checkedIds = null;
		checkedIds = request.getParameterValues("checkbox");
		//チェックボックス未選択の判定
		if(checkedIds == null ) {
			session.setAttribute("msg", "書籍を選択してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/RestrationForm.jsp");
			dispatcher.forward(request, response);
			return;
		}

		session.setAttribute("checkedBooks", checkedIds);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/RestrationCheck.jsp");
		dispatcher.forward(request, response);

//		BookDataAccess bda = new BookDataAccess();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
