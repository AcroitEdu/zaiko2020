package jp.co.acroit.zaiko2020.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 未ログインのユーザーを弾くフィルタ
 */
@WebFilter(dispatcherTypes = {
        DispatcherType.REQUEST,
        DispatcherType.FORWARD,
        DispatcherType.INCLUDE,
        DispatcherType.ERROR
}, asyncSupported = true, urlPatterns = { "/*" })
public class LoginSessionFilter implements Filter {

    // アクセスを許可されたパスの一覧
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("", "/login", "/loginForm", "/WEB-INF/jsp/LoginForm.jsp", "/style.css")));
    // ログインフォームのURL
    String loginUrl = "/Zaiko2020/loginForm";
    // セッションスコープに格納されたユーザー情報を取得するためのキー
    String containerAttributeName = "user";

    /**
     * フィルタ処理
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;
            HttpSession session = request.getSession(false);
            //パスの前処理
            String path = request.getRequestURI().substring(request.getContextPath().length())
                    .replaceAll("/?Zaiko2020", "").replaceAll("[/]+$", "");
            //ログイン/ホワイトリスト判定
            boolean loggedIn = (session != null && session.getAttribute(containerAttributeName) != null);
            boolean allowedPath = ALLOWED_PATHS.contains(path);

            if (loggedIn || allowedPath) {
                chain.doFilter(req, res); // 許可
            } else {
                //拒否
                request.getSession().setAttribute("error", "ログインしてください。");
                response.sendRedirect(loginUrl);
            }
        }
    }

}
