package org.github.msh.servlet.request;

import one.nio.http.Request;
import org.github.msh.servlet.core.OneNioContext;
import org.github.msh.servlet.core.OneNioRequestDispatcher;
import org.github.msh.servlet.utils.DateUtil;
import org.springframework.http.HttpRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

/**
 * @author msh
 */
public class HttpServletRequestImpl implements HttpServletRequest {

    public static final String DISPATCHER_TYPE = OneNioRequestDispatcher.class.getName() + ".DISPATCHER_TYPE";

    private Request request;
    private OneNioContext servletContext;
    private DispatcherType dispatcherType;
    //private HttpRequest request;
    private String requestUri;
    private Object attribute;
    private String characterEncoding;
    private Map<String, String[]> parameters;
    private String protocol;



    private HttpServletResponse httpServletResponse;


    public HttpServletRequestImpl(Request oneNioRequest ,OneNioContext context, DispatcherType dispatcherType) {
        this.request = oneNioRequest;
        //this.request = request;
        this.servletContext = context;
        this.dispatcherType = dispatcherType;
        //no used
        this.requestUri = oneNioRequest.getPath();
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public String getAuthType() {
        return "";
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String s) {
        String value = this.getHeader(s);
        if (value == null) {
            return -1L;
        } else {
            return DateUtil.parseDateHeader(s, value);
        }
    }

    @Override
    public String getHeader(String s) {
        return request.getHeader(s);
    }

    @Override
    public Enumeration<String> getHeaders(String s) {
        return Collections.emptyEnumeration();
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.emptyEnumeration();
    }

    @Override
    public int getIntHeader(String s) {
        return Integer.parseInt(request.getHeader(s));
    }

    @Override
    public String getMethod() {
        return request.getMethodName();
    }

    //待完善
    @Override
    public String getPathInfo() {
        return request.getPath();
    }

    @Override
    public String getPathTranslated() {
         throw new UnsupportedOperationException();
    }

    @Override
    public String getContextPath() {
        //return getRequestURI();
        return request.getPath();
    }

    @Override
    public String getQueryString() {
        return request.getQueryString();
    }

    @Override
    public String getRemoteUser() {
        return "";
    }

    @Override
    public boolean isUserInRole(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return "";
    }

    @Override
    public String getRequestURI() {
        return request.getURI();
    }

    @Override
    public StringBuffer getRequestURL() {
        return new StringBuffer(request.getURI());
    }
    //待完善
    @Override
    public String getServletPath() {
        return requestUri;
    }

    @Override
    public HttpSession getSession(boolean b) {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public String changeSessionId() {
        return "";
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
        return false;
    }

    @Override
    public void login(String s, String s1) throws ServletException {

    }

    @Override
    public void logout() throws ServletException {

    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return null;
    }

    @Override
    public Part getPart(String s) throws IOException, ServletException {
        return null;
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
        return null;
    }

    @Override
    public Object getAttribute(String s) {
        return this.servletContext.getAttribute(s);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return Collections.emptyEnumeration();
    }

    @Override
    public String getCharacterEncoding() {
        return characterEncoding ;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
        this.characterEncoding = s;
    }

    @Override
    public int getContentLength() {
        return Integer.parseInt(request.getHeader("Content-Length"));
    }

    @Override
    public long getContentLengthLong() {
        return Long.parseLong(request.getHeader("Content-Length"));
    }

    @Override
    public String getContentType() {
        return request.getHeader("Content-Type");
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String s) {
        return request.getParameter(s);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.emptyEnumeration();
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return new HashMap<>();
    }

    @Override
    public String getProtocol() {
        return "http11";
    }

    @Override
    public String getScheme() {
        return "";
    }

    @Override
    public String getServerName() {
        return "";
    }

    @Override
    public int getServerPort() {
        return 8000;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return "";
    }

    @Override
    public String getRemoteHost() {
        return "";
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return Collections.emptyEnumeration();
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return servletContext.getRequestDispatcher("/");
    }

    @Override
    public String getRealPath(String s) {
        return "";
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return "";
    }

    @Override
    public String getLocalAddr() {
        return "";
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return DispatcherType.REQUEST;
    }

    public final Map<String, String[]> getParameters() {
        if (this.parameters == null) {
            this.getParameter("");
        }

        return this.parameters;
    }
}
