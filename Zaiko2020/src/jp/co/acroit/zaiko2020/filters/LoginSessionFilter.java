package jp.co.acroit.zaiko2020.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Servlet Filter implementation class LoginSessionFilter
 */
@WebFilter(dispatcherTypes = {
        DispatcherType.REQUEST,
        DispatcherType.FORWARD,
        DispatcherType.INCLUDE,
        DispatcherType.ERROR
}, asyncSupported = true, urlPatterns = { "/*" })
public class LoginSessionFilter  implements Filter {



    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("", "/login", "/loginForm","/WEB-INF/jsp/LoginForm.jsp","/style.css")));
    @Autowired
    @Qualifier("loginUrl")
    String loginUrl;
    @Autowired
    @Qualifier("containerAttributeName")
    String containerAttributeName;

    /**
     * Default constructor.
     */
    public LoginSessionFilter() {

    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {

    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;
            HttpSession session = request.getSession(false);
            String path = request.getRequestURI().substring(request.getContextPath().length())
                    .replaceAll("/?Zaiko2020", "").replaceAll("[/]+$", "");
            //System.out.println("Accessing " + path);
            boolean loggedIn = (session != null && session.getAttribute(containerAttributeName) != null);
            boolean allowedPath = ALLOWED_PATHS.contains(path);

            if (loggedIn || allowedPath) {
                chain.doFilter(req, res);
            } else {
                request.getSession().setAttribute("error", "ログインしてください。");
                response.sendRedirect(loginUrl);
            }
        }
    }

}
