package com.anto.logbookerrorfilter;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.logbook.DefaultHttpLogWriter;
import org.zalando.logbook.Logbook;

@SpringBootApplication
public class LogbookErrorFilterApplication extends SpringBootServletInitializer {

    private static Class<LogbookErrorFilterApplication> applicationClass = LogbookErrorFilterApplication.class;

    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    @RestController
    public class GreetingController {
        private static final String template = "Hello, %s!";

        @RequestMapping("/greeting/{name}")
        public String greeting(@PathVariable(value = "name") String name) {
            return String.format(template, name);
        }
    }

    @Configuration
    public class Config {
        @Bean
        public Logbook logbook() {
            return Logbook.builder()
                    .writer(new DefaultHttpLogWriter(
                            LoggerFactory.getLogger("http.wire-log"),
                            DefaultHttpLogWriter.Level.INFO))
                    .build();
        }
    }

}
