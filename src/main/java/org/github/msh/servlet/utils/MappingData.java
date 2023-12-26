package org.github.msh.servlet.utils;

import javax.servlet.Servlet;

/**
 * @author msh
 * Created on 2023-12-20 12:28
 */

public class MappingData {

    Servlet servlet = null;
    String servletName;
    String redirectPath ;

    public void recycle() {
        servlet = null;
        servletName = null;
        redirectPath = null;
    }

}
