package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.acroit.zaiko2020.data.BookDataAccess;

/**
 * Servlet implementation class ArrivalProcessingController
 */
@WebServlet("/ArrivalProcessingController")
public class ArrivalProcessingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ArrivalProcessingController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

		int id = 0;
		int stock = 0;

		//書籍検索
		BookDataAccess bda = new BookDataAccess();

		try {

			//IDの取得
			id = Integer.parseInt(request.getParameter("id"));
			stock = bda.findStock(id);

			//IDをセッションに設定
			session.setAttribute("id", id);

			//検索
			foundBook = bda.findid(id);

			//検索結果をセッションに設定
			session.setAttribute("book", foundBook);

			//該当書籍なし ※あり得ない
//			if (specificBook.isEmpty()) {
//				session.setAttribute("error", "該当する書籍は見つかりませんでした。");
//			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ArrivalForm.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
			dispatcher.forward(request, response);
			e.printStackTrace();
		}



	}

}
