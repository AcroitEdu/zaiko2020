package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/logout" })
public final class LogoutServlet extends AutowireServletBase {
	private static final long serialVersionUID = 1L;

    /**
     * @see AutowireServletBase#AutowireServletBase()
     */
    public LogoutServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		session = request.getSession();
		session.setAttribute("error", "ログアウトしました。");
		//リダイレクト
		response.sendRedirect("/Zaiko2020/loginForm");
	}

}
