package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.acroit.zaiko2020.book.Book;

/**
 * 追加確認サーブレット
 * @version 1.0
 * 追加確認画面サーブレット新規作成
 * 入力内容の取得・セッションに設定
 * @version 1.1
 * 入力値のチェック追記
 * @version 1.2
 * salsDate→salesDateに修正
 * deleteFlg→deleteFlagに修正
 * @version 1.3
 * エラーメッセージの初期化位置変更
 * @version 1.4
 * 価格の入力値チェックの漏れ修正
 * @author hiroki tajima
 */
@WebServlet("/AddCheck")
public class AddCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String title = null;
		String publisher = null;
		String author = null;
		String isbn = null;
		Date day = null;
		LocalDate salesDate = null;
		String price = null;
		String stock = null;
		int deleteFlag = 0;

		HttpSession session = request.getSession();

		try {

			//入力値のnullチェック
			if(request.getParameter("bookName").isEmpty() || request.getParameter("publisher").isEmpty() || request.getParameter("author").isEmpty() || request.getParameter("isbn").isEmpty() || request.getParameter("date").isEmpty() || request.getParameter("price").isEmpty() || request.getParameter("stock").isEmpty()) {
				throw new NullPointerException();
			}

			title = request.getParameter("bookName");
			publisher = request.getParameter("publisher");
			author = request.getParameter("author");
			isbn = request.getParameter("isbn");
			day = sdf.parse(request.getParameter("date"));
			salesDate = day.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			price = request.getParameter("price");
			stock = request.getParameter("stock");

			//文字チェック
			if (!isbn.matches("^[0-9]*$") || !stock.matches("^[0-9]*$") || !price.matches("^[0-9]*$") || !isbn.matches("^[0-9]*$") || 13 != isbn.length()) {

				session.setAttribute("error", "指定されている形式で入力してください。");
				response.sendRedirect("/Zaiko2020/Add");
				return;
			}

			//値の範囲チェック
			if(Integer.parseInt(price) < 0 || 999999 < Integer.parseInt(price) || Integer.parseInt(stock) < 0 || 999999 < Integer.parseInt(stock) ) {
				session.setAttribute("error", "指定されている形式で入力してください。");
				response.sendRedirect("/Zaiko2020/Add");
				return;
			}

			Book addbook = new Book(0, null, null, null, null, null, null, null, 0);

			addbook.setName(title);
			addbook.setPublisher(publisher);
			addbook.setAuthor(author);
			addbook.setIsbn(isbn);
			addbook.setSalesDate(salesDate);
			addbook.setPrice(price);
			addbook.setStock(stock);
			addbook.setDeleteFlag(deleteFlag);

			//検索条件設定
			session.setAttribute("book", addbook);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/AddCheck.jsp");
			dispatcher.forward(request, response);

		} catch (NullPointerException e) {
			e.printStackTrace();
			session.setAttribute("error", "指定されている形式で入力してください。");
			response.sendRedirect("/Zaiko2020/Add");
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/Add");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		session.setAttribute("error", "");
		doGet(request, response);
	}

}
