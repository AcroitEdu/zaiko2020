package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PlaceholderAfterLoginServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/placeholderAfterLogin" })
public final class PlaceholderAfterLoginServlet extends AutowireServletBase {
    private static final long serialVersionUID = 1L;

    /**
     * @see AutowireServletBase#AutowireServletBase()
     */
    public PlaceholderAfterLoginServlet() {
        //super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.getWriter().append("Served at: ").append(request.getContextPath());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/PlaceholderAfterLogin.jsp");
        dispatcher.forward(request, response);
    }

}
