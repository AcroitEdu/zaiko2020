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
 * 入荷画面サーブレット
 * @version 1.1
 * @author hiroe ishioka
 */
@WebServlet("/arrivalForm")
public class ArrivalController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ArrivalController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//特定書籍の検索条件設定用
		int id = 0;

		//書籍の検索用
		BookDataAccess bda = new BookDataAccess();

		HttpSession session = request.getSession();
		Book foundBook;

		try {
			//IDの取得
			if (request.getParameter("id") == null) { //入荷処理のエラーでリダイレクトされ、左方の値が存在しない時
				if (session.getAttribute("id") != null) {
					id = (int) session.getAttribute("id");
				} else {
					System.out.println("エラー");
					session.setAttribute("error", "書籍を選択してください。");
					response.sendRedirect("/Zaiko2020/inventoryList");
					return;
				}
			} else {
				//在庫一覧で入荷を押され、左方の値が存在する時
				id = Integer.parseInt(request.getParameter("id"));
			}

			//更新flgが立っているか確認
			if(bda.flgCheck(id)) {
				session.setAttribute("error", "選択した書籍は現在操作中です。");
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

			//入荷画面へフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ArrivalForm.jsp");
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		//エラーメッセージの初期化
		session.setAttribute("error", "");

		doGet(request, response);
	}
}
