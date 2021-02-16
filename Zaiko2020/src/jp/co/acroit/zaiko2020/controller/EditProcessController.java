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
 * 編集処理サーブレット
 * @version 1.0
 * 編集処理サーブレット新規作成
 * セッションから入力内容を取得
 * 書籍情報を編集
 * 編集後の書籍情報取得・セッションに設定
 * @version 1.1
 * 更新フラグを戻す
 * @version 1.2
 * セッションに格納したidの上書き方法変更
 * 空文字からnullに変更
 * @version 1.3
 * 履歴作成処理を追加
 * @version 1.4
 * DBエラー処理の遷移先修正
 * @author yohei nishida
 */
@WebServlet("/EditProcess")
public class EditProcessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//idを取得する
		int id = 0;

		HttpSession session = request.getSession();

		//書籍の編集内容を取得する
		Book editbook = (Book)session.getAttribute("book");

		Book book;

		BookDataAccess bda = new BookDataAccess();

		try {

			id = Integer.parseInt(request.getParameter("id"));
			System.out.println(id);

			//書籍情報の編集
			bda.edit(editbook,id);
			//編集後の書籍情報を取得
			book = bda.findId(id);
			//更新flgを0に戻す。
			bda.flgReturn(id);

			//履歴作成
			int operationId = 4; //編集の操作ID
			HistoryDataAccess hda = new HistoryDataAccess();
			User user = (User)session.getAttribute("user");
			long userId = user.getNumber();
			int bookId = book.getId();
			hda.InsertHistory(userId, bookId, operationId);

			//取得した書籍の情報をセッションに設定する
			session.setAttribute("book", book);
			session.setAttribute("id", null);

			response.sendRedirect("/Zaiko2020/resultForm");

		} catch (SQLException e) {

			request.getSession().setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/loginForm");


		} catch (Exception e) {
			//エラーを返しリダイレクト
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/Edit");
			e.printStackTrace();

		}

	}

}
