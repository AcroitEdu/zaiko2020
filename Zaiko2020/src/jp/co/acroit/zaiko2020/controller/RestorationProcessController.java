package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.acroit.zaiko2020.data.BookDataAccess;

/**
 * 復元処理サーブレット
 * @version 1.0
 * 復元処理サーブレット新規作成
 * 選択された書籍のid取得
 * 書籍の復元処理
 * @version 1.1
 * 書籍が選択されていない場合の判定
 * @author hiroki tajima
 */
@WebServlet("/RestorationProcess")
public class RestorationProcessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

		HttpSession session = request.getSession();

		String[] checkedId = null;

		BookDataAccess bda = new BookDataAccess();
		try {

			//チェックボックス未選択の判定
			if(request.getParameterValues("checkbox") == null ) {
				session.setAttribute("msg", "書籍を選択してください。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/RestrationForm.jsp");
				dispatcher.forward(request, response);
				return;
			}

			//選択された書籍の復元
			checkedId = request.getParameterValues("checkbox");

			bda.restoration(checkedId);

			session.setAttribute("msg", "書籍の復元を行いました。");
			response.sendRedirect("/Zaiko2020/Restoration");

		} catch (SQLException e) {

			//セッションの破棄
			request.getSession().invalidate();
			//セッションの再生成
			request.getSession(true);
			request.getSession().setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/loginForm");

		} catch (Exception e) {
		}

	}

}
