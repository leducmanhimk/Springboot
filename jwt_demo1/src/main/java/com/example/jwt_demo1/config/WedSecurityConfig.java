package com.example.jwt_demo1.config;


import com.example.jwt_demo1.jwt.JwtAuth;
import com.example.jwt_demo1.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WedSecurityConfig extends WebSecurityConfigurerAdapter {


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

        // Disable CSRF (cross site request forgery)
        http.csrf().disable();
        http.cors();

        // Make sure we use stateless session; session won't be used to store user's state.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Handle an authorized attempts
        http.authorizeRequests()

//                .antMatchers("/random/**").hasAnyAuthority("ADMIN")
                // No need authentication.

                .antMatchers("/api/login/**").permitAll()
                .antMatchers("/api/random/**").hasAuthority("ADMIN")


                // Need authentication.
                .anyRequest().authenticated();
                http.addFilterBefore(new JwtAuth(),UsernamePasswordAuthenticationFilter.class);
    }
}
