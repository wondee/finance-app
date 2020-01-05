package info.wondee.app.financeapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http
        .authorizeRequests()
          .antMatchers("/api", "/")
              .authenticated()
              .anyRequest()
              .permitAll()
          .and()
        .formLogin()
          .loginPage("/login")
          .loginProcessingUrl("/doLogin")
          .permitAll()
          .and()
        .logout()
          .permitAll()
          .and()
      .csrf().disable();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth,
          UserDetailsService userDetailsService,
          PasswordEncoder passwordEncoder) throws Exception {

      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

}