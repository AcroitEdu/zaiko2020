package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.acroit.zaiko2020.data.UserDataAccess;
import jp.co.acroit.zaiko2020.user.User;

/**
 * ログアウト処理
 * @version 1.0
 * @version 1.1
 * ログアウト時にログイン状況を更新
 * エラー出力文を調整
 * @author yohei.nishida
 */
@WebServlet("/logout")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutController() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//userデータを確保
		User user = (User)request.getSession().getAttribute("user");

		//セッションの破棄
		request.getSession().invalidate();

		//セッションの再生成
		request.getSession(true);

		//ログイン状況を更新
		int valueWhenLoggingOut = 0;
		UserDataAccess uda = new UserDataAccess();
		try {
			uda.updateLoginStatus(user.getId(), valueWhenLoggingOut);
		} catch (SQLException e) {
			request.getSession().setAttribute("error", "データベースに異常が発生しています。"
					+ System.getProperty("line.separator") + "正常のログアウトに失敗しました。"
					+ System.getProperty("line.separator") + "システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/loginForm");
			return ;
		} catch (Exception e) {
			request.getSession().setAttribute("error", "システムに異常が発生しています。"
					+ System.getProperty("line.separator") + "正常のログアウトに失敗しました。"
					+ System.getProperty("line.separator") + "システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/loginForm");
			return;
		}

		request.getSession().setAttribute("error", "ログアウトしました。");
		response.sendRedirect("/Zaiko2020/loginForm");
	}

}
