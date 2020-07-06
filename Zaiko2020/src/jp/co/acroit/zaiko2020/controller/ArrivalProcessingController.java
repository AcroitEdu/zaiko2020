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
 * 入荷処理サーブレット
 * @version 1.1
 * @author hiroe ishioka
 */
@WebServlet("/arrive")
public class ArrivalProcessingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ArrivalProcessingController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

		HttpSession session = request.getSession();
		boolean flg = (boolean) session.getAttribute("flg");

		if(flg) {
			session.setAttribute("error", "再読み込み");
			session.setAttribute("flg", false);
			response.sendRedirect("/Zaiko2020/arrivalForm");
			return;
		}


		int id = 0;
		int count = 0;

		//書籍検索
		BookDataAccess bda = new BookDataAccess();

		//HttpSession session = request.getSession();
		Book foundBook;

		try {

			//IDの取得
			id = Integer.parseInt(request.getParameter("id"));
			count = Integer.parseInt(request.getParameter("count"));

			if(0 > count || count >= 1000000) {
				throw new IndexOutOfBoundsException("入荷数超過または出荷数超過");
			}

			//操作・読込
			foundBook = bda.update(id, count);

			//検索結果をセッションに設定
			session.setAttribute("book", foundBook);

			session.setAttribute("flg", true);

			response.sendRedirect("/Zaiko2020/resultForm");

		} catch (IndexOutOfBoundsException e) {
			session.setAttribute("error", "保管可能な在庫数を超過するためキャンセルされました。");

			System.out.println(session.getAttribute("error"));

			response.sendRedirect("/Zaiko2020/arrivalForm");
		} catch (Exception e) {
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/arrivalForm");
			e.printStackTrace();
		}



	}

}
