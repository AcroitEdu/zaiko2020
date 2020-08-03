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

/**
 * 追加処理サーブレット
 * @version 1.0
 * @author hiroki tajima
 */
@WebServlet("/AddProcess")
public class AddProcessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		//追加書籍情報の取得
		Book addbook = (Book)session.getAttribute("book");
		Book book;

		BookDataAccess bda = new BookDataAccess();
		try {
			//書籍追加
			bda.add(addbook);
			//追加書籍の取得
			book = bda.addSearch(addbook);
			//取得した所s系情報をセッションに設定
			session.setAttribute("book", book);

			response.sendRedirect("/Zaiko2020/resultForm");

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
			response.sendRedirect("/Zaiko2020/Add");
			e.printStackTrace();

		}

	}

}
