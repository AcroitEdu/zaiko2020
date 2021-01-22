package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 履歴一覧サーブレット
 */
@WebServlet("/HistoryList")
public class HistoryListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		HttpSession session = request.getSession();

		session.setAttribute("flg", false);

		//初期表示の検索条件設定
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String operatingDate = sdf.format(date);
		String operatingDateFlag = "equals";
		String operator = null;
		int operation = 99;
		int page = 1;	//１ページ
		int totalLimitedCount = 50000;

		// SearchCondition sc = (SearchCondition)session.getAttribute("conditions");
		// //検索条件がない
		// if(sc == null) {
		// 	sc = new SearchCondition();

		// 	//初期値設定
		// 	sc.setName(operatingDate);
		// 	sc.setAuthor(operatingDateFlag);
		// 	sc.setPublisher(operator);
		// 	sc.setIsbn(operation);
		// 	sc.setPage(page);
		// }

		// //履歴検索
		// HistoryDataAccess hda = new HistoryDataAccess();
		// List<History> historyList = new ArrayList<History>();

		try {

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/testert.jsp");
			dispatcher.forward(request, response);

		}
//		catch (SQLException e) {
//
//			e.printStackTrace();
//			//セッションの破棄
//			request.getSession().invalidate();
//			//セッションの再生成
//			request.getSession(true);
//			request.getSession().setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
//			response.sendRedirect("/Zaiko2020/loginForm");
//
//		}
		catch (Exception e) {

			e.printStackTrace();
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/tester.jsp");
			dispatcher.forward(request, response);

		}

		//response.getWriter().append("Served at: ").append(request.getContextPath()); 自動生成文
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doGet(request, response);
	}

}
