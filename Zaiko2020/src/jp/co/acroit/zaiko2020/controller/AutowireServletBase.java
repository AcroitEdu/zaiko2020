package jp.co.acroit.zaiko2020.controller;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Servlet autowire magic class AutowireServletBase
 */
public abstract class AutowireServletBase extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutowireServletBase() {
        super();
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

}
