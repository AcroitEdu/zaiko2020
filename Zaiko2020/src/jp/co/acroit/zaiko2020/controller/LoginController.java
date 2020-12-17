package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.acroit.zaiko2020.auth.LoginStatusVerification;
import jp.co.acroit.zaiko2020.auth.PasswordComparator;
import jp.co.acroit.zaiko2020.data.UserDataAccess;
import jp.co.acroit.zaiko2020.user.User;

/**
 * ログインサーブレット
 * @version 1.1
 * @version 1.2
 * 二重ログイン対策処理を追加
 * @author yohei.nishida
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
		int valueWhenLoggingIn = 1;

		//IDとPasswordの取得
		id = request.getParameter("id");
		password = request.getParameter("password");

		//idのnull判定
		if(id == null || id.isBlank()) {

			isIdBlank = true;
		}

		//passwordのnull判定
		if(password == null || password.isBlank()) {

			isPasswordBlank = true;

		}

		if (isIdBlank || isPasswordBlank) {

			request.getSession().setAttribute("error", "IDとPASSWORDを入力してください。");
			response.sendRedirect("/Zaiko2020/loginForm");
			return;

		}


		//入力された文字列のチェック
		if(!id.matches("^[0-9a-zA-Z]+$") || !password.matches("^[0-9a-zA-Z]+$")) {

			request.getSession().setAttribute("error", "指定されている形式で入力してください。");
			response.sendRedirect("/Zaiko2020/loginForm");
			return;

		}


		//id検索
		UserDataAccess uda = new UserDataAccess();
		try{
			User user = uda.findById(id);


			if (user != null) {
				System.out.println(user.getId() + user.getLoginStatus()); // checkComment

				PasswordComparator comparator = new PasswordComparator();

				//パスワード比較
				if (comparator.compare(password, user.getPassword())) {

					LoginStatusVerification verification = new LoginStatusVerification();

					//二重ログイン確認
					if(verification.verify(user.getLoginStatus())) {
						System.out.println("id:" + id);
						//ログイン状況を更新する
						uda.updateLoginStatus(id, valueWhenLoggingIn);

						//セッションにユーザーを登録する

						HttpSession session = request.getSession();
						session.setAttribute("user", user);
						session.setAttribute("error", "");
						response.sendRedirect("/Zaiko2020/inventoryList");
						System.out.println("session-user:" + session.getAttribute("user").toString());
						return;

					}
					System.out.println("");
					request.getSession().setAttribute("error", "多重ログインの疑いにより、ログインできません。");
					response.sendRedirect("/Zaiko2020/loginForm");
					return;

				}
			}

			request.getSession().setAttribute("error", "IDもしくはPASSWORDに誤りがあります。");
			response.sendRedirect("/Zaiko2020/loginForm");

		}catch (SQLException e) {

			request.getSession().setAttribute("error", "データベースに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/loginForm");

		}catch (Exception e) {

			request.getSession().setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/loginForm");

		}
	}
}
