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
import jp.co.acroit.zaiko2020.data.HistoryDataAccess;
import jp.co.acroit.zaiko2020.user.User;

/**
 * 復元処理サーブレット
 * @version 1.0
 * 復元処理サーブレット新規作成
 * 選択された書籍のid取得
 * 書籍の復元処理
 * @version 1.1
 * 書籍が選択されていない場合の判セッション「id」をnullで上書き
 * @version 1.2
 * 復元処理実行後に
 * @version 1.3
 * 履歴作成処理を追加
 * @version 1.4
 * DBエラー処理の遷移先の修正
 * @author yohei nishida
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

			//履歴作成
			int operationId = 6; //復元の操作ID
			for(String id : checkedId) {
				HistoryDataAccess hda = new HistoryDataAccess();
				User user = (User)session.getAttribute("user");
				long userId = user.getNumber();
				int bookId = Integer.parseInt(id);
				hda.InsertHistory(userId, bookId, operationId);
			}

			session.setAttribute("id",null);

			session.setAttribute("msg", "書籍の復元を行いました。");
			response.sendRedirect("/Zaiko2020/Restoration");

		} catch (SQLException e) {

//			//セッションの破棄
//			request.getSession().invalidate();
//			//セッションの再生成
//			request.getSession(true);
//			request.getSession().setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
//			response.sendRedirect("/Zaiko2020/loginForm");
			e.printStackTrace();
			request.getSession().setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/RestrationForm.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {

			e.printStackTrace();
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/RestrationForm.jsp");
			dispatcher.forward(request, response);

		}

	}

}
