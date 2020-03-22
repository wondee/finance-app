package info.wondee.app.financeapp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import info.wondee.app.financeapp.user.UserAccount;
import info.wondee.app.financeapp.user.UserAccountRepository;


// uncomment and run for adding a new user to the DB
//@SpringBootApplication
public class FinanceApplicationUserCreator implements CommandLineRunner{
  
  @Autowired
  private UserAccountRepository repository;

  @Override
  public void run(String... args) throws Exception {
    
    
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    String encode = encoder.encode("Demo!");



    UserAccount entity = new UserAccount(null, "Demo", encode, null);
    repository.insert(entity );
    
    Optional<UserAccount> findByName = repository.findByName("Demo");
    System.out.println(findByName);
  }

  
  public static void main(String[] args) {
    // SpringApplication.run(FinanceApplication.class, args);

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    String encode = encoder.encode("Demo");

    System.out.println(encode);

  }

}
