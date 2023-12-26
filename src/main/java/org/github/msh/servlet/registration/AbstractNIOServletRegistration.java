package org.github.msh.servlet.registration;

import org.github.msh.servlet.core.OneNioContext;

import javax.servlet.FilterConfig;
import javax.servlet.Registration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 实现Filter和Servlet的动态注册的抽象类
 */
class AbstractNIOServletRegistration implements Registration, Registration.Dynamic, ServletConfig, FilterConfig {
    private final String name;
    private final String className;
    private final OneNioContext context;
    protected boolean asyncSupported;

    protected AbstractNIOServletRegistration(String name, String className, OneNioContext context) {
        this.name = checkNotNull(name);
        this.className = checkNotNull(className);
        this.context = checkNotNull(context);
    }

    @Override
    public void setAsyncSupported(boolean isAsyncSupported) {
        asyncSupported = isAsyncSupported;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String getFilterName() {
        return name;
    }

    @Override
    public String getServletName() {
        return name;
    }

    @Override
    public ServletContext getServletContext() {
        return context;
    }

    OneNioContext getOneNioContext() {
        return context;
    }

    //Init Parameter相关的方法，因为没用到，暂时不实现

    @Override
    public boolean setInitParameter(String name, String value) {
        return false;
    }

    @Override
    public String getInitParameter(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return Collections.emptyEnumeration();
    }

    @Override
    public Set<String> setInitParameters(Map<String, String> initParameters) {
        return Collections.emptySet();
    }

    @Override
    public Map<String, String> getInitParameters() {
        return Collections.emptyMap();
    }
}
