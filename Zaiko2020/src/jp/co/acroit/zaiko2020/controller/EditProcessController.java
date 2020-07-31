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
 * Servlet implementation class EditProcessController
 */
@WebServlet("/EditProcess")
public class EditProcessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//		doGet(request, response);

		//idを取得する
		int id = 0;

		HttpSession session = request.getSession();

		//書籍の編集内容を取得する
		Book editbook = (Book)session.getAttribute("book");

		Book book;

		BookDataAccess bda = new BookDataAccess();

		try {

			id = Integer.parseInt(request.getParameter("id"));
			System.out.println(id);

			//書籍情報の編集
			bda.edit(editbook,id);
			//編集後の書籍情報を取得
			book = bda.findId(id);
			//更新flgを0に戻す。
			bda.flgReturn(id);
			//取得した書籍の情報をセッションに設定する
			session.setAttribute("book", book);
			session.setAttribute("id", "");

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
			response.sendRedirect("/Zaiko2020/Edit");
			e.printStackTrace();

		}
	}

}
