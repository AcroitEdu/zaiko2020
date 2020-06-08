package jp.co.acroit.zaiko2020.filters;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * HTTP通信の文字コードをUTF-8に変更するフィルタ
 * @version 1.0
 * @author Koki OISHI
 */
@WebFilter(dispatcherTypes = {
        DispatcherType.REQUEST,
        DispatcherType.FORWARD,
        DispatcherType.INCLUDE,
        DispatcherType.ERROR
}, asyncSupported = true, urlPatterns = { "/*" })
public class CharsetFilter implements Filter {

    /**
     * コンストラクタ
     */
    public CharsetFilter() {
    }

    /**
     * フィルタ処理
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //文字コードの変更
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

}
