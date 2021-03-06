package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.acroit.zaiko2020.book.Book;
import jp.co.acroit.zaiko2020.data.BookDataAccess;
import jp.co.acroit.zaiko2020.data.HistoryDataAccess;
import jp.co.acroit.zaiko2020.user.User;

/**
 * 削除処理サーブレット
 * @version 1.0
 * セッションからidを取得
 * 書籍の作瀬尾
 * 削除した書籍情報取得・セッションに設定
 * @version 1.1
 * 履歴作成処理を追加
 * @author yohei nishida
 */
@WebServlet("/DeleteProcess")
public class DeleteProcessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

		int id = 0;
		BookDataAccess bda = new BookDataAccess();
		Book book;

		HttpSession session = request.getSession();

		try {

			id = Integer.parseInt(request.getParameter("id"));

			//更新flgをもとに戻す
			bda.flgReturn(id);
			//選択された書籍の削除
			bda.delete(id);
			//編集後の書籍情報を取得
			book = bda.findId(id);

			//履歴作成
			int operationId = 5; //削除の操作ID
			HistoryDataAccess hda = new HistoryDataAccess();
			User user = (User)session.getAttribute("user");
			long userId = user.getNumber();
			int bookId = book.getId();
			hda.InsertHistory(userId, bookId, operationId);

			//取得した書籍の情報をセッションに設定する
			session.setAttribute("book", book);

			response.sendRedirect("/Zaiko2020/resultForm");

		} catch (SQLException e) {

			request.getSession().setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/inventoryList");

		} catch (Exception e) {
			//エラーを返しリダイレクト
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/inventoryList");
			e.printStackTrace();

		}

	}

}
