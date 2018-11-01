package info.wondee.app.financeapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAccountRepositoryImpl implements UserAccountCustomRepository {

  @Autowired
  private UserAccountRepository repository;
  
  @Override
  public UserAccount findCurrentUser() {
    
    String userName = SecurityContextHolder.getContext().getAuthentication().getName();
    
    return repository.findByName(userName).orElseThrow(() -> new IllegalStateException("User not found in DB"));
    
  }

}
