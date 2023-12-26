package org.github.msh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@EnableAutoConfiguration(exclude = WebMvcAutoConfiguration.class)
@ComponentScan
@EnableWebMvc
public class TestWebApp {
    private static final String MESSAGE = "Hello, World!";

    public static void main(String[] args) {
        SpringApplication.run(TestWebApp.class, args);
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public Message test() {
        return new Message(MESSAGE);
    }

    @Bean
    public ServletRegistrationBean nullServletRegistration() {
        return new ServletRegistrationBean(new HttpServlet(){
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                resp.getOutputStream().print("Null Servlet Test");
            }
        }, "/null");
    }

    /**
     * 异步测试
     * @return
     */
//    @RequestMapping(value = "/async")
//    @ResponseBody
//    public Callable<String> async() {
//        return () -> MESSAGE;
//    }


    private static class Message {
        private final String message;
        public Message(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

}
