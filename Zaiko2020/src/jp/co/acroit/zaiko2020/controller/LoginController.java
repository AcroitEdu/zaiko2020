package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;

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
 * ログインサーブレット
 * @version 1.0
 * @author hiroki tajima
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String id = null;
		String password = null;
		boolean isIdBlank = false;
		boolean isPasswordBlank = false;

		//IDとPasswordの取得
		id = request.getParameter("id");
		password = request.getParameter("password");

		//idとpasswordのnull判定
		if(id == null || id.isBlank()) {
			isIdBlank = true;
		}
		if(password == null || password.isBlank()) {
			isPasswordBlank = true;
		}

		if (isIdBlank || isPasswordBlank) {
			request.getSession().setAttribute("error", "ユーザー名とパスワードを入力してください。");
			response.sendRedirect("/Zaiko2020/loginForm");
			return;
		}

		//id検索
		UserDataAccess uda = new UserDataAccess();
		User user = uda.findById(id);

		if (user != null) {
			PasswordComparator comparator = new PasswordComparator();

			//sessionにユーザー情報設定
			if (comparator.compare(password, user.getPassword())) {
				request.getSession().setAttribute("error", "");
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect("/Zaiko2020/AfterLogin");
				return;
			}
		}
		request.getSession().setAttribute("error", "ユーザー名またはパスワードが間違っています。");
		response.sendRedirect("/Zaiko2020/loginForm");
	}
}
