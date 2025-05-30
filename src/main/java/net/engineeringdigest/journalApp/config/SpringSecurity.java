package net.engineeringdigest.journalApp.config;

import net.engineeringdigest.journalApp.Services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Override
    protected void configure(HttpSecurity httpSecurity)throws Exception{
             httpSecurity.authorizeRequests()
                     .antMatchers("/Journal/**","/User/**").authenticated()
                     .antMatchers("/admin/**").hasRole("Admin")//in userDetailsService u were sending role also so that was user here in authentication
                     .anyRequest().permitAll()
                     .and()
                     .httpBasic();
             httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());//this .passwordEncoder(...) is telling spring security to use BCrypt.... encryption and decrption method in verifying the password
    }
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

        //there are multiple password encoding techniques current we are using BcryptPasswordEncoder
//     return new Pbkdf2PasswordEncoder();
    }

}
