package org.github.msh.servlet.response;

import com.sun.deploy.net.HttpResponse;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import one.nio.http.Response;
import org.github.msh.servlet.core.OneNioContext;
import org.springframework.mock.web.DelegatingServletOutputStream;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.Assert;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

/**
 * @author msh
 * Created on 2023-12-20 12:28
 */
public class HttpServletResponseImpl implements HttpServletResponse {

    private  Response response;
    private  HttpServletRequest request;
    private  OneNioContext servletContext;

    ByteArrayOutputStream content = new ByteArrayOutputStream(1024);

    //
    public HttpServletResponseImpl(HttpServletRequest request, OneNioContext servletContext) {
        this.request = request;
        this.servletContext = servletContext;
    }

    public byte[] getContentAsByteArray() {
        return this.content.toByteArray();
    }

    public  Response getResponse() {
        Response response1 = new Response("200", getContentAsByteArray());
        response1.addHeader("Content-Type: text/plain; charset=utf-8");
        return response1;
    }

    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public boolean containsHeader(String s) {
        return false;
    }

    @Override
    public String encodeURL(String s) {
        return "";
    }

    @Override
    public String encodeRedirectURL(String s) {
        return "";
    }

    @Override
    public String encodeUrl(String s) {
        return "";
    }

    @Override
    public String encodeRedirectUrl(String s) {
        return "";
    }

    @Override
    public void sendError(int i, String s) throws IOException {

    }

    @Override
    public void sendError(int i) throws IOException {

    }

    @Override
    public void sendRedirect(String s) throws IOException {

    }

    @Override
    public void setDateHeader(String s, long l) {

    }

    @Override
    public void addDateHeader(String s, long l) {

    }

    @Override
    public void setHeader(String s, String s1) {

    }

    @Override
    public void addHeader(String s, String s1) {

    }

    @Override
    public void setIntHeader(String s, int i) {

    }

    @Override
    public void addIntHeader(String s, int i) {

    }

    @Override
    public void setStatus(int i) {

    }

    @Override
    public void setStatus(int i, String s) {

    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public String getHeader(String s) {
        if ("Content-Type".equals(s)) {
            return "application/json;charset=utf-8";
        }
        if ("Content-Length".equals(s)) {
            return "51";
        }
        return "";
    }

    @Override
    public Collection<String> getHeaders(String s) {
        return Collections.emptyList();
    }

    @Override
    public Collection<String> getHeaderNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return "";
    }

    @Override
    public String getContentType() {
        return "application/json;charset=UTF-8";
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ResponseServletOutputStream(content);
    }

    public PrintWriter getWriter() throws UnsupportedEncodingException {
        return new ResponsePrintWriter(new OutputStreamWriter(this.content));
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

//    public String getContentAsByteArray()

    @Override
    public Locale getLocale() {
        return null;
    }

    private class ResponseServletOutputStream extends DelegatingServletOutputStream {
        public ResponseServletOutputStream(OutputStream out) {
            super(out);
        }

        public void write(int b) throws IOException {
            super.write(b);
            super.flush();
        }

        public void flush() throws IOException {
            super.flush();
        }
    }

    private class ResponsePrintWriter extends PrintWriter {
        public ResponsePrintWriter(Writer out) {
            super(out, true);
        }

        public void write(char[] buf, int off, int len) {
            super.write(buf, off, len);
            super.flush();
        }

        public void write(String s, int off, int len) {
            super.write(s, off, len);
            super.flush();
        }

        public void write(int c) {
            super.write(c);
            super.flush();
        }

        public void flush() {
            super.flush();
        }

        public void close() {
            super.flush();
            super.close();
        }
    }
}
