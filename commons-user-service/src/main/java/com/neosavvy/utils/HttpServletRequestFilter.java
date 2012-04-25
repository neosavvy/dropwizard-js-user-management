package com.neosavvy.utils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpServletRequestFilter implements Filter
{

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpUtils.setHttpRequest((HttpServletRequest)request);
        }

        if ( response instanceof HttpServletResponse) {
            HttpUtils.setHttpResponse((HttpServletResponse)response);
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
