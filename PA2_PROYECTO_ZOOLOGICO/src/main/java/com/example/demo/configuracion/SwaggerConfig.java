/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Licona
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controladores"))
                //.paths(PathSelectors.any())
                .paths(regex("/ws.*"))
                .build()
                .apiInfo(getInfo());

    }

    private ApiInfo getInfo() {

        return new ApiInfo(
                "Aplicacion de gestion de un zoologico",
                "Contiene una api y una aplicacion web",
                "0.0.0.1",
                "http://uth.hn",
                "Programacion Avanzada 2",
                "PA2",
                "PA2 URL");
    }

}
