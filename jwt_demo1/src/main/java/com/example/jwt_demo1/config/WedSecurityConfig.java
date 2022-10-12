package com.example.jwt_demo1.config;
import com.example.jwt_demo1.jwt.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WedSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/user").hasRole("ADMIN")
                .and().httpBasic().and().csrf().disable();
        http.authorizeRequests().antMatchers("/api/user/**")
                .hasAnyRole("ADMIN","EDITER").and().httpBasic().and().csrf().disable();
        http.authorizeRequests().antMatchers("/api/random")
                .hasAnyRole("ADMIN","EDITER").and().httpBasic().and().csrf().disable();
        http.authorizeRequests().antMatchers("api/login/**").permitAll();
        http.addFilterBefore(authenticationJwtTokenFilter(),UsernamePasswordAuthenticationFilter.class);

    }
}
