package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.acroit.zaiko2020.auth.PasswordComparator;
import jp.co.acroit.zaiko2020.data.UserDataAccess;
import jp.co.acroit.zaiko2020.user.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		boolean isIdBlank = id == null || id.isBlank();
		boolean isPasswordBlank = password == null || password.isBlank();

		if(isIdBlank || isPasswordBlank) {
			request.getSession().setAttribute("error", "ユーザー名とパスワードを入力してください。");
			response.sendRedirect("/Zaiko2020/loginForm");
			return;
		}

		UserDataAccess userdataaccess = new UserDataAccess();
		User user = userdataaccess.findById(id);
		if(user != null) {
			PasswordComparator passwordcomparator = new PasswordComparator();
			if(passwordcomparator.compare(password, user.getPass())) {
				HttpSession sesstion = request.getSession();
				sesstion.setAttribute("user", user);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/Zaiko2020/placeholderAfterLogin");
				dispatcher.forward(request, response);
			}
			response.sendRedirect("/Zaiko2020/loginForm");
		}





	}

}
