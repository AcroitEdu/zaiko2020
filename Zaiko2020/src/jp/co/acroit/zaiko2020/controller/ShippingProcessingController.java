package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.acroit.zaiko2020.book.Book;
import jp.co.acroit.zaiko2020.data.BookDataAccess;

/**
 * 出荷処理サーブレット
 * @version 1.1
 * @author hiroe ishioka
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

		int id = 0;
		int stock = 0;
		int count = 0;

		//書籍検索
		BookDataAccess bda = new BookDataAccess();

		HttpSession session = request.getSession();
		Book foundBook;

		try {

			//IDの取得
			id = Integer.parseInt(request.getParameter("id"));
			count = Integer.parseInt(request.getParameter("count"));

			//操作・読込
			foundBook = bda.update(id, -count);

			if(0 > foundBook.getStock() || foundBook.getStock() >= 1000000) {
				//con.rollback();
				throw new IndexOutOfBoundsException("入荷数超過または出荷数超過");
			}

			//検索結果をセッションに設定
			session.setAttribute("book", foundBook);

			response.sendRedirect("/Zaiko2020/resultForm");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("判定エラー");
			session.setAttribute("error", "出荷数が在庫数を超過するためキャンセルされました。");
			System.out.println(session.getAttribute("error"));
			response.sendRedirect("/Zaiko2020/shippingForm");
		} catch (Exception e) {
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/shippingForm");
			e.printStackTrace();
		}

	}

}
