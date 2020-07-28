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
 * 書籍削除処理
 * @version 1.0
 * @author hiroki tajima
 */
@WebServlet("/Delete")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		BookDataAccess bda = new BookDataAccess();
		Book book;

		HttpSession session = request.getSession();

		try {

			id = Integer.parseInt(request.getParameter("id"));

			//選択された書籍の削除
			bda.delete(id);
			//編集後の書籍情報を取得
			book = bda.findId(id);
			//取得した書籍の情報をセッションに設定する
			session.setAttribute("book", book);

			response.sendRedirect("/Zaiko2020/resultForm");

		} catch (SQLException e) {

			session.setAttribute("error", "データべースに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/logout");

		} catch (Exception e) {
			//エラーを返しリダイレクト
			session.setAttribute("error", "システムに異常が発生しています。システム管理者に連絡してください。");
			response.sendRedirect("/Zaiko2020/inventoryList");
			e.printStackTrace();

		}
	}

}
