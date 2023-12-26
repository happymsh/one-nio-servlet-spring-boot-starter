package org.github.msh.servlet.demo;

import one.nio.http.HttpSession;
import one.nio.http.Request;
import one.nio.http.RequestHandler;
import org.github.msh.servlet.core.OneNioContext;
import org.github.msh.servlet.request.HttpServletRequestImpl;
import org.github.msh.servlet.response.HttpServletResponseImpl;

import javax.servlet.DispatcherType;
import javax.servlet.Servlet;

public class DemoHandler implements RequestHandler {

    private OneNioContext oneNioContext;

    public DemoHandler(OneNioContext oneNioContext) {
        this.oneNioContext = oneNioContext;
    }

    @Override
    public void handleRequest(Request request, HttpSession session) {
        HttpServletRequestImpl httpServletRequest = new HttpServletRequestImpl(request, oneNioContext, DispatcherType.REQUEST);
        HttpServletResponseImpl httpServletResponse = new HttpServletResponseImpl(httpServletRequest, oneNioContext);
        try {
            Servlet servlet = oneNioContext.getServlet("dispatcherServlet");
            servlet.service(httpServletRequest, httpServletResponse);
            session.sendResponse(httpServletResponse.getResponse());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
