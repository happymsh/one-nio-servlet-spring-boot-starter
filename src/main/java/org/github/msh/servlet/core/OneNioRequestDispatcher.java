package org.github.msh.servlet.core;

import org.github.msh.servlet.request.HttpServletRequestImpl;

import javax.servlet.*;
import java.io.IOException;

/**
 * 除了传统的forward和include，把正常的Servlet调用也放在这里dispatch()方法,unused
 */
public class OneNioRequestDispatcher implements RequestDispatcher {
    private final ServletContext context;
    private final FilterChain filterChain;

    OneNioRequestDispatcher(ServletContext context, FilterChain filterChain) {
        this.context = context;
        this.filterChain = filterChain;
    }

    @Override
    public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.setAttribute(HttpServletRequestImpl.DISPATCHER_TYPE, DispatcherType.FORWARD);
        // TODO implement
    }

    @Override
    public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.setAttribute(HttpServletRequestImpl.DISPATCHER_TYPE, DispatcherType.INCLUDE);
        // TODO implement
    }

    void dispatch(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.setAttribute(HttpServletRequestImpl.DISPATCHER_TYPE, DispatcherType.ASYNC);
        filterChain.doFilter(request, response);
    }
}
