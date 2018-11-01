package info.wondee.app.financeapp.user;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class FinanceUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userService.findByName(username).map(
              user ->
              new User(
                      user.getName(), 
                      user.getPassword(), 
                      Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
                    )
            ).orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
