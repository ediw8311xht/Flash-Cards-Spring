package com.flashcardapp.flashcard;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;


//Settings for security of spring application.
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Autowired in spring allows for beans to be automatically inserted
    //into objects so that object can access them.
    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Csrf protection is disabled for now.

        //antmatchers().authenticated means that those urls require user to be
        //logged for them to access them.
        //If user is not logged in then Spring redirects them to a login page.
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/flashcard/makeNewFlashcardset").authenticated()
                .antMatchers("/flashcard/Flashcardset/edit").authenticated()
                .antMatchers("/flashcard/getFlashcardsetEdit").authenticated()
                .antMatchers("/flashcard/User/myFlashsets").authenticated()
                .antMatchers("/flashcard/ajax/Flashcardset/copy").authenticated()
                .antMatchers("/flashcard/Flashcardset/delete").authenticated()
                .antMatchers("/flashcard/User/me").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                    .formLogin()
                        .loginPage("/flashcard/User/login")
                        .defaultSuccessUrl("/flashcard/")
                .and()
                    .logout()
                    .logoutUrl("/flashcard/User/logout")
                    .logoutSuccessUrl("/flashcard/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Gets authentication source from "Users" table in database.
        //Encrypted using BCrypt.
        auth
            .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                    "select username, password, enabled from Users " +
                    "where username=?")
                .authoritiesByUsernameQuery(
                    "select username, password, enabled from Users " +
                    "where username=?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }

}
