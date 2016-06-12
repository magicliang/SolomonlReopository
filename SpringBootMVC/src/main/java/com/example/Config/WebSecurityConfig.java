package com.example.Config;

import com.example.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

//The configuration can be fetched and be profiled
//Without active profile specified, security config will be loaded anyway.
//The Profile is useless for this significant configuration?
@Profile("prod")
//Even in here, the configuration can be detected.
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Can use this to get datasource
        //DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
        //TODO: Only admin can post, and prevent csrf for posting
        http
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and().httpBasic().and()
        //TODO: logout fail. Why?
//                .logout().logoutUrl("/logout").deleteCookies("JSESSIONID")
// .logoutSuccessUrl("/")//If we let the / be logoutSuccessUrl, this page do not need to authenticate and authorized.
//                .permitAll().and()
                //TODO: how to do the csrf in post
                .csrf().disable()
                ;

    }

    //We can combine the configuration into one class
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //LDAP authentication
        auth
                .ldapAuthentication()
                //Only space ou is unavailable. I can add fuck ou to the patterns
                .userDnPatterns("uid={0},ou=people", "uid={0},ou=space", "uid={0},ou=otherpeople", "uid={0},ou=fuck")
                //.userDnPatterns("uid={0},ou=space")
                //.groupSearchBase("ou=groups")
                .contextSource().ldif("classpath:test-server.ldif");
        //In memory authentication
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("user")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN", "USER");

        //Another approach: http://www.mkyong.com/spring-security/spring-security-form-login-using-database/
        //This approach: http://www.mkyong.com/spring-security/spring-security-hibernate-annotation-example/
        //All we need to do is to load the user from database, to use this service to create UserDetails
        //I guess the encode will encode the password and try to match the encoded password in the database;
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//How to use the datasource?
//            //DataSource authentication
//        auth
//                .jdbcAuthentication()
//                //Can not bind wire the datasource automatically.
//                //With application.properties, we can autowire a
//                //JdbcTemplate, which can provide datasource
//                .dataSource((DataSource) applicationContext.getBean("dataSource"))
//                .withDefaultSchema()
//                .withUser("abc").password("abc").roles("USER").and()
//                .withUser("adm").password("adm").roles("USER", "ADMIN");

    }

}

//Don't have to use static inner class
//static inner class is for multiple configuration
//@Configuration
//class AuthenticationConfiguration extends
//        GlobalAuthenticationConfigurerAdapter {
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
////        //Autowired does not support static field
////        @Autowired
////        private JdbcTemplate jt;
//
//    @Override
//    public void init(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth
//                .ldapAuthentication()
//                //Only space ou is unavailable. I can add fuck ou to the patterns
//                .userDnPatterns("uid={0},ou=people", "uid={0},ou=space", "uid={0},ou=otherpeople", "uid={0},ou=fuck")
//                //.userDnPatterns("uid={0},ou=space")
//                //.groupSearchBase("ou=groups")
//                .contextSource().ldif("classpath:test-server.ldif");
//        //In memory authentication
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("user")
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password("admin")
//                .roles("ADMIN", "USER");
////How to use the datasource?
////            //DataSource authentication
////        auth
////                .jdbcAuthentication()
////                //Can not bind wire the datasource automatically.
////                //With application.properties, we can autowire a
////                //JdbcTemplate, which can provide datasource
////                .dataSource((DataSource) applicationContext.getBean("dataSource"))
////                .withDefaultSchema()
////                .withUser("abc").password("abc").roles("USER").and()
////                .withUser("adm").password("adm").roles("USER", "ADMIN");
//
//    }
//}