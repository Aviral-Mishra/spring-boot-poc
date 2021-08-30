package com.brillio.gatewayservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args){
		SpringApplication.run(GatewayServiceApplication.class,args);
	}

	@Bean
	public CorsWebFilter corsWebFilter() {

		final CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Collections.singletonList("*"));
		corsConfig.setMaxAge(8000L);
		corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		corsConfig.addAllowedHeader("*");

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);

		return new CorsWebFilter(source);
	}

	@Bean
	public RouteLocator apiRoutes(RouteLocatorBuilder builder) {

		return builder.routes()
				.route("main_route1", route ->
						route.path("/api/v1/**")
								.uri("lb://main-service")
				).build();
	}

}
