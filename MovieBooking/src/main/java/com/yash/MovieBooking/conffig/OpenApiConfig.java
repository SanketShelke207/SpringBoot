package com.yash.MovieBooking.conffig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up OpenAPI (Swagger) documentation.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Creates and configures the OpenAPI bean.
     *
     * @return An OpenAPI object with custom information.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Movie Booking API")
                        .version("1.0")
                        .description("API documentation for the Movie Booking System"));
    }
}