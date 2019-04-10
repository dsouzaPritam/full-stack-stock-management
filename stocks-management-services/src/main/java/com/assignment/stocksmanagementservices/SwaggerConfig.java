package com.assignment.stocksmanagementservices;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String STOCK_API_DESCRIPTION = "A Java/ SpringBoot based REST API implementation to manage stocks. \n" +
            "\n" +
            "One can do the following  :\n" +
            "- get list of stocks [GET -> api/stocks/].\n" +
            "- get particular of stocks [GET -> api/stocks/{stockId}].\n" +
            "- create stock [POST -> api/stocks/].\n" +
            "- update stock [PUT -> api/stocks/{stockId}].\n" +
            "\n";


    private static final Contact DEFAULT_CONTACT = new Contact(
            "Pritam Dsouza", "", "dsouza.pritam@gmail.com");


    private static final ApiInfo STOCK_MANAGEMENT_API_INFO = new ApiInfo(
            "Stock Management API", STOCK_API_DESCRIPTION, "1.0",
            "urn:tos", DEFAULT_CONTACT,
            "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<>(Arrays.asList("application/json"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(STOCK_MANAGEMENT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.assignment.stocksmanagementservices.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
