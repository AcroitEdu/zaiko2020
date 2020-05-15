package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginFormServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/loginForm" })
public final class LoginFormServlet extends AutowireServletBase {
	private static final long serialVersionUID = 1L;

    /**
     * @see AutowireServletBase#AutowireServletBase()
     */
    public LoginFormServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/LoginForm.jsp");
		dispatcher.forward(request, response);
	}

}
