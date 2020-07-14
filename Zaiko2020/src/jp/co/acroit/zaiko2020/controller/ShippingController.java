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
 * 出荷画面サーブレット
 * @version 1.2
 * @author hiroe ishioka
 */
@WebServlet("/shippingForm")
public class ShippingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShippingController() {
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
			if (request.getParameter("id") == null) { //出荷処理のエラーでリダイレクトされ、左方の値が存在しない時
				if (session.getAttribute("id") != null) {
					id = (int) session.getAttribute("id");
				} else {
					session.setAttribute("error", "書籍を選択してください。");
					response.sendRedirect("/Zaiko2020/inventoryList");
					return;
				}
			} else { //在庫一覧で入荷を押され、左方の値が存在する時
				id = Integer.parseInt(request.getParameter("id"));
			}

			//IDをセッションに設定
			session.setAttribute("id", id);

			//特定書籍の検索を行い、結果をセッションに設定
			foundBook = bda.findId(id);
			session.setAttribute("book", foundBook);

			//出荷画面へフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ShippingForm.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException e) {

			session.setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			//エラーを返しリダイレクト
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
			dispatcher.forward(request, response);
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
