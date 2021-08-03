package com.lxh.test.basic.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodedingFilter  implements Filter {
    protected String encoding = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("utf-8");
    }

    @Override
    public void destroy() {
        this.encoding = null;
    }

    public EncodedingFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (this.encoding != null) {
            request.setCharacterEncoding(this.encoding);
            response.setCharacterEncoding(this.encoding);
        }
        chain.doFilter(request, response);
    }
}
