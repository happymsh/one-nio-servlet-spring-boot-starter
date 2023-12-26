package org.github.msh.servlet.core;

import one.nio.http.HttpServerConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.github.msh.servlet.demo.DemoHandler;
import org.github.msh.servlet.server.HttpServerConfigFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerException;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author msh
 * Created on 2023-12-20 12:28
 */

public class ServletContainer implements WebServer {
    private final Log log = LogFactory.getLog(getClass());

    private final InetSocketAddress address; //监听端口地址
    private final OneNioContext servletContext; //Context


    CustomHttpServer server = null;
    HttpServerConfig config = null;

    public ServletContainer(InetSocketAddress address, OneNioContext context) {
        this.address = address;
        this.servletContext = context;
    }

    @Override
    public void start() throws WebServerException {
        config  = HttpServerConfigFactory.create(this.getPort());

        try {
            server = new CustomHttpServer(config);
            server.addRequestHandler(new DemoHandler(servletContext));
        } catch (IOException e) {
            //e.printStackTrace();
            log.error("Could not start OneNio Server", e);
        }
        server.start();
    }

    @Override
    public void stop() throws WebServerException {
        server.stop();
    }

    @Override
    public int getPort() {
        return address.getPort();
    }
}
