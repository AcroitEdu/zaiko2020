package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;

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
 * @version 1.0
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

		//特定書籍の検索条件設定
		int id = 0;

		//書籍検索
		BookDataAccess bda = new BookDataAccess();

		HttpSession session = request.getSession();
		Book foundBook;

		try {
			//IDの取得
			if(request.getParameter("id") == null) { //出荷処理のエラーでリダイレクトされ、左方の値が存在しない時
				id = (int)session.getAttribute("id");
			} else {								  //在庫一覧で出荷を押された時の最初のidの読み込み
				id = Integer.parseInt(request.getParameter("id"));
			}

			//IDをセッションに設定
			session.setAttribute("id", id);

			//検索
			foundBook = bda.findId(id);

			//検索結果をセッションに設定
			session.setAttribute("book", foundBook);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ShippingForm.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ShippingForm.jsp");
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
