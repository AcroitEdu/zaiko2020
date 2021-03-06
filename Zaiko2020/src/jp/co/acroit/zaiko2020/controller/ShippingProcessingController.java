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
 * 出荷処理サーブレット
 * @version 1.3
 *  @version 1.4
 *  idの上書きを""からnullに変更
 * @version 1.5
 * 履歴作成処理を追加
 * @author yohei nishida
 */
@WebServlet("/ship")
public class ShippingProcessingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShippingProcessingController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

		HttpSession session = request.getSession();

		//同じ入荷画面で2度入荷処理を行おうとしていないかの確認
		boolean flg = (boolean) session.getAttribute("flg");
		if(flg) {

			session.setAttribute("error", "出荷処理は既に実行されています。最初からやり直してください。");
			session.setAttribute("flg", false);
			response.sendRedirect("/Zaiko2020/shippingForm");
			return;

		}

		int id = 0;
		long count = 0;

		//入荷の操作ID
		int operationId = 2;

		//書籍の検索用
		BookDataAccess bda = new BookDataAccess();

		Book foundBook;

		try {

			//IDと出荷数を取得する
			id = Integer.parseInt(request.getParameter("id"));
			count = Integer.parseInt(request.getParameter("count"));

			if(count < 1 || Integer.MAX_VALUE < count) {

				throw new IndexOutOfBoundsException("入荷数超過または出荷数超過");

			}

			//DBを操作し読み込む
			foundBook = bda.update(id, -count);

			//入荷履歴の作成
			HistoryDataAccess hda = new HistoryDataAccess();
			User user = (User)session.getAttribute("user");
			long userId = user.getNumber();
			int bookId = id;
			hda.InsertHistory(userId, bookId, operationId);

			//検索結果をセッションに設定
			session.setAttribute("book", foundBook);

			session.setAttribute("id", null);

			session.setAttribute("flg", true);

			response.sendRedirect("/Zaiko2020/resultForm");
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			//エラーを返し出荷画面にリダイレクト
			session.setAttribute("error", "出荷数が在庫数を超過するためキャンセルされました。");
			response.sendRedirect("/Zaiko2020/shippingForm");

		} catch (SQLException e) {

			request.getSession().setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/shippingForm");

		} catch (Exception e) {

			e.printStackTrace();
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/shippingForm");

		}


	}

}
