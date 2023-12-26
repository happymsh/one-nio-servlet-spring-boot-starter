package org.github.msh.servlet.bootstrap;

import org.github.msh.servlet.core.OneNioContext;
import org.github.msh.servlet.core.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

import javax.servlet.ServletException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

/**
 * @author msh
 * Created on 2023-12-20 12:28
 */

public class OneNioServletWebServerFactory extends AbstractServletWebServerFactory implements  ResourceLoaderAware {
    private static final Charset DEFAULT_CHARSET;
    private static final Set<Class<?>> NO_CLASSES;
    //public static final String DEFAULT_PROTOCOL = "org.apache.coyote.http11.Http11NioProtocol";

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String SERVER_INFO = "OneNio@SpringBoot";
    private ResourceLoader resourceLoader;

    static {
        DEFAULT_CHARSET = StandardCharsets.UTF_8;
        NO_CLASSES = Collections.emptySet();
    }

    @Override
    public WebServer getWebServer(ServletContextInitializer... initializers) {
        ClassLoader parentClassLoader = resourceLoader != null ? resourceLoader.getClassLoader() : ClassUtils.getDefaultClassLoader();
        //Context init
        OneNioContext context = new OneNioContext(getContextPath(), new URLClassLoader(new URL[]{}, parentClassLoader), SERVER_INFO);
        for (ServletContextInitializer initializer : initializers) {
            try {
                initializer.onStartup(context);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
        //从SpringBoot获取端口
        int port = getPort() > 0 ? getPort() : new Random().nextInt(65535 - 1024) + 1024;
        InetSocketAddress address = new InetSocketAddress(port);
        log.info("Server initialized with port: " + port);
        return new ServletContainer(address, context);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public String getContextPath() {
        return "/";
    }
}
