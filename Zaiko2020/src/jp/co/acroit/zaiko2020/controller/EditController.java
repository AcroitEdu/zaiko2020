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

import jp.co.acroit.zaiko2020.book.Book;
import jp.co.acroit.zaiko2020.data.BookDataAccess;

/**
 * 編集サーブレット
 * @version 1.0
 * 編集画面サーブレット新規作成
 * idによる書籍検索
 * セッションにに書籍情報（入力フォームの初期値）設定
 * @version 1.1
 * エラーでリダイレクトされた場合の処理
 * @version 1.2
 * 更新flgの判定処理追加
 *  @version 1.3
 * 更新flgのエラーメッセージ変更
 *  @version 1.4
 * エラーメッセージ初期化位置変更
 * @author hiroki tajima
 */
@WebServlet("/Edit")
public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String branch = null;
		int id = 0;

		branch = request.getParameter("button");
		System.out.println(branch);

		//書籍の検索用
		BookDataAccess bda = new BookDataAccess();

		HttpSession session = request.getSession();
		Book foundBook;

		try {

			if (request.getParameter("button") == null) {
				branch = "エラー";
			} else {
				branch = request.getParameter("button");
			}

			switch(branch) {
			case "編集":
				//IDの取得
				id = Integer.parseInt(request.getParameter("id"));

				//更新flgが立っているか確認
				if(bda.flgCheck(id)) {
					session.setAttribute("error", "選択した書籍は現在編集中です。");
					response.sendRedirect("/Zaiko2020/inventoryList");
					return;
				}else {
					//更新flgを立てる
					bda.flg(id);
				}

				//IDをセッションに設定
				session.setAttribute("id", id);

				//特定書籍の検索し、結果をセッションに設定
				foundBook = bda.findId(id);
				session.setAttribute("book", foundBook);
			}

			//編集画面へフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/EditForm.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {

			//セッションの破棄
			request.getSession().invalidate();
			//セッションの再生成
			request.getSession(true);
			request.getSession().setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/loginForm");

		} catch (Exception e) {
			//エラーを返しリダイレクト
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/inventoryList");
			e.printStackTrace();

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("error", "");

		doGet(request, response);
	}

}
