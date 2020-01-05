package info.wondee.app.financeapplication;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import info.wondee.app.financeapp.FinanceApplication;
import info.wondee.app.financeapp.user.FinanceUser;
import info.wondee.app.financeapp.user.FinanceUserRepository;
import info.wondee.app.financeapp.user.UserAccount;
import info.wondee.app.financeapp.user.UserAccountRepository;


// uncomment and run for adding a new user to the DB
//@SpringBootApplication
@ComponentScan("info.wondee.app.financeapp")
public class FinanceApplicationTestRunner implements CommandLineRunner{
  
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
