package org.github.msh.servlet.bootstrap;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import one.nio.http.*;

/**
 * @author msh
 * Created on 2023-12-20 12:28
 */
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@ConditionalOnWebApplication // 在Web环境下才会起作用
public class OneNioAutoConfiguration {
    @Configuration
    @ConditionalOnClass({HttpServer.class})
    @ConditionalOnMissingBean(value = ServletWebServerFactory.class, search = SearchStrategy.CURRENT)
    public static  class EmbeddedServer {
        @Bean
        public OneNioServletWebServerFactory oneNioServletWebServerFactory() {
            return new OneNioServletWebServerFactory();
        }
    }
}
