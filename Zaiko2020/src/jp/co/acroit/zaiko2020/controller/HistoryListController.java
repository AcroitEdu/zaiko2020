package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.acroit.zaiko2020.data.HistoryDataAccess;
import jp.co.acroit.zaiko2020.data.SearchCondition;
import jp.co.acroit.zaiko2020.history.History;

/**
 * 履歴一覧サーブレット
 * @version 1.0
 * @author yohei nishida
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
		System.out.println(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String operatingDate = sdf.format(date);
		String operatingDateFlag = "equals";
		String operator = null;
		int operation = 99;
		int page = 1;	//１ページ
		int totalLimitedCount = 50000;
		System.out.println(operatingDate);
		SearchCondition sc = (SearchCondition)session.getAttribute("conditions");
		//検索条件がない
		if(sc.getOperationDate() == null) {
			sc = new SearchCondition();

			//初期値設定
			sc.setOperationDate(operatingDate);
			sc.setOperationDateFlag(operatingDateFlag);
			sc.setUserId(operator);
			sc.setOperation(operation);
			System.out.println("検索条件" + sc.getOperation());
			sc.setPage(page);
		}

		//履歴検索
		HistoryDataAccess hda = new HistoryDataAccess();
		List<History> historyList = new ArrayList<History>();

		try {
			int count = 0;
			int pageCount = 0;
			boolean limitedFlag = false;

			//総件数の取得
			count = hda.countAll(sc);
			System.out.println(count);
			if (count > totalLimitedCount) {

				limitedFlag = true;
				count = totalLimitedCount;

			}

			//総ページ数
			pageCount = (count + 199) / 200;

			//総件数・現ページ・最大ページ数をセッションに設定
			session.setAttribute("count", count);
			session.setAttribute("page", page);
			session.setAttribute("maxPage", pageCount);

			//検索
			historyList = hda.find(sc);

			//検索結果・検索条件をセッションに設定
			session.setAttribute("lists", historyList);
			session.setAttribute("conditions", sc);
			System.out.println("検索条件" + sc.getOperation());

			//該当履歴なし
			if (historyList.isEmpty()) {

				session.setAttribute("error", "該当する履歴は見つかりませんでした。");

			}else if (limitedFlag) {

				session.setAttribute("error", "5万件以上ヒットしています。検索条件で絞り込んで下さい。");
			}
			System.out.println("正常");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/HistoryList.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {


			e.printStackTrace();
			//セッションの破棄
			request.getSession().invalidate();
			//セッションの再生成
			request.getSession(true);
			request.getSession().setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/loginForm");

		} catch (Exception e) {

			e.printStackTrace();
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/HistoryList.jsp");
			dispatcher.forward(request, response);

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();


		Date date = new Date();
		System.out.println(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String operatingDate = sdf.format(date);
		String operatingDateFlag = "equals";
		String operator = null;
		int operation = 99;
		int page = 1;	//１ページ
		int totalLimitedCount = 50000;
		int value = Integer.parseInt(request.getParameter("form"));


		session.setAttribute("flg", false);
		SearchCondition sc = (SearchCondition)session.getAttribute("conditions");
		session.setAttribute("error", "");

		if(sc == null) {

			sc = new SearchCondition();

		}

		if(value == 1) {

			//初期値設定
			sc.setOperationDate(operatingDate);
			sc.setOperationDateFlag(operatingDateFlag);
			sc.setUserId(operator);
			sc.setOperation(operation);
			System.out.println("検索条件" + sc.getOperation());
			sc.setPage(page);
		}

		switch (value) {
		//検索ボタン
		case 2:
			//検索条件取得
			operatingDate = request.getParameter("operatingDate");
			operatingDateFlag = request.getParameter("beforeAfter");
			operator = request.getParameter("operator");
			if (operator.isEmpty()) { //空文字対処
				operator = null;
			}
			operation = Integer.parseInt(request.getParameter("operation"));

			sc.setOperationDate(operatingDate);
			sc.setOperationDateFlag(operatingDateFlag);
			sc.setUserId(operator);
			sc.setOperation(operation);
			sc.setPage(page);

			break;

			//表ページ移動
		case 3:
			//ページ番号の空白判定
			String pageNumberCheck = null;
			pageNumberCheck = request.getParameter("page");
			if (pageNumberCheck == null || pageNumberCheck.isEmpty()) {

				session.setAttribute("error", "ページ番号を入力してください。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
				dispatcher.forward(request, response);
				return;

			}

			//入力値チェック
			if (!pageNumberCheck.matches("^[0-9]*$") || page < 1) {

				session.setAttribute("error", "指定されている形式で入力してください。");
				break;

			}

			if(area(pageNumberCheck)) {
				page = Integer.parseInt(request.getParameter("page"));
			}else {
				session.setAttribute("error", "指定されている形式で入力してください。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
				dispatcher.forward(request, response);
				return;
			}


			int maxPage = (int) session.getAttribute("maxPage");
			if (maxPage < page) {

				session.setAttribute("error", "該当するページは見つかりませんでした。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/InventoryList.jsp");
				dispatcher.forward(request, response);
				return;

			}

			sc.setPage(page);
			break;


		}

		//検索をセッションに設定
		session.setAttribute("conditions", sc);

		//履歴検索
				HistoryDataAccess hda = new HistoryDataAccess();
				List<History> historyList = new ArrayList<History>();

				try {
					int count = 0;
					int pageCount = 0;
					boolean limitedFlag = false;

					//総件数の取得
					count = hda.countAll(sc);
					System.out.println(count);
					if (count > totalLimitedCount) {

						limitedFlag = true;
						count = totalLimitedCount;

					}

					//総ページ数
					pageCount = (count + 199) / 200;

					//総件数・現ページ・最大ページ数をセッションに設定
					session.setAttribute("count", count);
					session.setAttribute("page", page);
					session.setAttribute("maxPage", pageCount);

					//検索
					historyList = hda.find(sc);

					//検索結果・検索条件をセッションに設定
					session.setAttribute("lists", historyList);
					session.setAttribute("conditions", sc);
					System.out.println("検索条件" + sc.getOperation());

					//該当履歴なし
					if (historyList.isEmpty()) {

						session.setAttribute("error", "該当する履歴は見つかりませんでした。");

					}else if (limitedFlag) {

						session.setAttribute("error", "5万件以上ヒットしています。検索条件で絞り込んで下さい。");
					}
					System.out.println("正常");
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/HistoryList.jsp");
					dispatcher.forward(request, response);

				} catch (SQLException e) {


					e.printStackTrace();
					//セッションの破棄
					request.getSession().invalidate();
					//セッションの再生成
					request.getSession(true);
					request.getSession().setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
					response.sendRedirect("/Zaiko2020/loginForm");

				} catch (Exception e) {

					e.printStackTrace();
					session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/HistoryList.jsp");
					dispatcher.forward(request, response);

				}

	}

	public boolean area(String pageNumberCheck) {
		try {
			int area = Integer.parseInt(pageNumberCheck);
			return true;
		}catch (NumberFormatException  e) {
			return false;
		}

	}

}
