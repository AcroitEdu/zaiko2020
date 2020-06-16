package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.acroit.zaiko2020.data.BookDataAccess;

/**
 * Servlet implementation class InventoryLIstContoller
 */
@WebServlet("/InventoryLIstContoller")
public class InventoryLIstController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InventoryLIstController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String book = null;
		String publisher = null;
		String author = null;
		String salsdate = null;
		String stock = null;
		String salsdateflag = null;
		String stockflag = null;


		book = request.getParameter("book");
		publisher = request.getParameter("publisher");
		author = request.getParameter("author");
		salsdate = request.getParameter("salsdate");
		stock = request.getParameter("stock");

		String conditions = "WHERE";



		//書籍検索
		BookDataAccess bda = new BookDataAccess();


	}

}
