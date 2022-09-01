package com.example.jwt_demo1.config;


import com.example.jwt_demo1.jwt.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WedSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailsServiceImpl();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
////         Disable CSRF (cross site request forgery)
//        http.csrf().disable();
//        http.cors();
//        // Make sure we use stateless session; session won't be used to store user's state.
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        // Handle an authorized attempts
//        http.authorizeRequests()
////                .antMatchers("/random/**").hasAnyAuthority("ADMIN")
//                // No need authentication.
//                .antMatchers("/api/login/**").permitAll()
//                .antMatchers("/api/random/**").hasAuthority("ADMIN")
//                // Need authentication.
//                .anyRequest().authenticated();
//                http.addFilterBefore(new AuthTokenFilter(),UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests().antMatchers("/api/users").permitAll();
        http.authorizeRequests().antMatchers("/api/user").hasRole("ADMIN").and().httpBasic().and().csrf().disable();
        http.authorizeRequests().antMatchers("/api/user/**").hasRole("EDITER").and().httpBasic().and().csrf().disable();
        http.authorizeRequests().antMatchers("/api/random").hasRole("ADMIN").and().httpBasic().and().csrf().disable();
        http.authorizeRequests().antMatchers("api/login/**").permitAll();


        http.addFilterBefore(authenticationJwtTokenFilter(),UsernamePasswordAuthenticationFilter.class);
    }
}
