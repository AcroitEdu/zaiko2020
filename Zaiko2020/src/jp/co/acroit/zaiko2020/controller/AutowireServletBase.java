package jp.co.acroit.zaiko2020.controller;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

/**
 * Servlet autowire magic class AutowireServletBase
 */
public abstract class AutowireServletBase extends HttpServlet {

	protected AutowireCapableBeanFactory ctx;
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
        ctx = ((ApplicationContext) getServletContext().getAttribute(
                "applicationContext")).getAutowireCapableBeanFactory();
        ctx.autowireBean(this);
	}

}
