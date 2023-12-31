package org.omega.omegapoisk.security;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.omega.omegapoisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConf {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private UserService userService;

    private RestAuthenticationEntryPoint entryPoint;

    @Bean
    public AuthFilter filter(){
        return new AuthFilter();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//        auth.userDetailsService(userService).passwordEncoder(encoder);
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors(cors -> cors.disable())
                .csrf(cs -> cs.disable())
                .addFilterBefore(filter(), AuthorizationFilter.class)
                .authorizeHttpRequests(authz -> authz.requestMatchers("auth/*","test/**", "api/**")
                        .permitAll()


                        );

        return http.build();
    }



    @Bean
    public WebMvcConfigurer corsConfigurer(){

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOriginPatterns("*")
                        .allowedMethods("*")
                        .allowedHeaders("Authorization", "Cache-Control", "Content-Type")
                        .allowedHeaders("*")
                        .allowedOrigins(null,"null")
                        .allowCredentials(true);
            }
        };
    }
}
