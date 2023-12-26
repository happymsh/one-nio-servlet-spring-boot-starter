package org.github.msh.servlet.core;

import one.nio.http.*;
import one.nio.http.gen.RequestHandlerGenerator;
import one.nio.net.Socket;
import one.nio.server.RejectedSessionException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RedStar
 * @Date 2023/12/20
 * @Description
 */
public class CustomHttpServer extends HttpServer {
    private final PathMapper defaultMapper = new PathMapper();
    private final Map<String, PathMapper> mappersByAlias = new HashMap<>();
    private final Map<String, PathMapper> mappersByHost = new HashMap<>();

    public CustomHttpServer(HttpServerConfig config, Object... routers) throws IOException {
        super(config);

        if (config.virtualHosts != null) {
            for (Map.Entry<String, String[]> virtualHost : config.virtualHosts.entrySet()) {
                PathMapper mapper = new PathMapper();
                mappersByAlias.put(virtualHost.getKey(), mapper);
                for (String host : virtualHost.getValue()) {
                    mappersByHost.put(host.toLowerCase(), mapper);
                }
            }
        }

        addRequestHandlers(this);
        for (Object router : routers) {
            addRequestHandlers(router);
        }
    }

    @Override
    public HttpSession createSession(Socket socket) throws RejectedSessionException {
        return new HttpSession(socket, this);
    }

    public void handleRequest(Request request, HttpSession session) throws IOException {
        RequestHandler handler = findHandlerByHost(request);
        if (handler == null) {
            handler = defaultMapper.find(request.getPath(), request.getMethod());
        }

        if (handler != null) {
            handler.handleRequest(request, session);
        } else {
            handleDefault(request, session);
        }
    }

    public void handleDefault(Request request, HttpSession session) throws IOException {
        Response response = new Response(Response.NOT_FOUND, Response.EMPTY);
        session.sendResponse(response);
    }

    public void addRequestHandler(RequestHandler requestHandler) {
        defaultMapper.add("/", new int[]{1,2}, requestHandler);
        defaultMapper.add("/test", new int[]{1,2}, requestHandler);
    }

    public void addRequestHandlers(Object router) {
        ArrayList<Class> supers = new ArrayList<>(4);
        for (Class<?> cls = router.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            supers.add(cls);
        }

        RequestHandlerGenerator generator = new RequestHandlerGenerator();
        for (int i = supers.size(); --i >= 0; ) {
            Class<?> cls = supers.get(i);

            VirtualHost virtualHost = cls.getAnnotation(VirtualHost.class);
            String[] aliases = virtualHost == null ? null : virtualHost.value();

            for (Method m : cls.getMethods()) {
                Path annotation = m.getAnnotation(Path.class);
                if (annotation == null) {
                    continue;
                }

                RequestMethod requestMethod = m.getAnnotation(RequestMethod.class);
                int[] methods = requestMethod == null ? null : requestMethod.value();

                RequestHandler requestHandler = generator.generateFor(m, router);
                for (String path : annotation.value()) {
                    if (!path.startsWith("/")) {
                        throw new IllegalArgumentException("Path '" + path + "' is not absolute");
                    }

                    if (aliases == null || aliases.length == 0) {
                        defaultMapper.add(path, methods, requestHandler);
                    } else {
                        for (String alias : aliases) {
                            PathMapper mapper = mappersByAlias.get(alias);
                            if (mapper != null) {
                                mapper.add(path, methods, requestHandler);
                            }
                        }
                    }
                }
            }
        }
    }

    protected RequestHandler findHandlerByHost(Request request) {
        if (!mappersByHost.isEmpty()) {
            String host = request.getHost();
            if (host != null) {
                PathMapper mapper = mappersByHost.get(host.toLowerCase());
                if (mapper != null) {
                    return mapper.find(request.getPath(), request.getMethod());
                }
            }
        }
        return null;
    }
}
