package jp.co.acroit.zaiko2020.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.acroit.zaiko2020.auth.IAuthenticator;
import jp.co.acroit.zaiko2020.user.ISessionContainer;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login" })
public final class LoginServlet extends AutowireServletBase {
    private static final long serialVersionUID = 1L;

    @Autowired
    IAuthenticator authenticator;
    @Autowired
    String loginUrl;
    @Autowired
    String successUrl;
    @Autowired
    String containerAttributeName;
    @Autowired
    String errorAttributeName;
    @Autowired
    String loginFailedMessage;

    /**
     * @see AutowireServletBase#AutowireServletBase()
     */
    public LoginServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        try {
            ISessionContainer session = authenticator.authenticate(id, password);
            request.getSession().invalidate();
            request.getSession(true).setAttribute(containerAttributeName, session);
            response.sendRedirect(successUrl);
        }catch (Exception e) {
            request.getSession().setAttribute(errorAttributeName, loginFailedMessage);
            response.sendRedirect(loginUrl);
            e.printStackTrace();
        }

    }

}
