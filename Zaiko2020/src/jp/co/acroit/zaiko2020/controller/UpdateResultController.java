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
 * 更新結果サーブレット
 * @version 1.1
 * @author hiroki tajima
 */
@WebServlet("/resultForm")
public class UpdateResultController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateResultController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("error", "");

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ResultForm.jsp");
		dispatcher.forward(request, response);
	}

}
