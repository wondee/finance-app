package info.wondee.app.financeapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http
        .authorizeRequests()
          .antMatchers(
              "/public/**", 
              "/.well-known/**", 
              "/resources/**",
              "/legal/**").permitAll()
          .anyRequest().authenticated()
          .and()
        .formLogin()
          .loginPage("/login")
          .permitAll()
          .and()
        .logout()
          .permitAll();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth,
          UserDetailsService userDetailsService,
          PasswordEncoder passwordEncoder) throws Exception {

      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

}