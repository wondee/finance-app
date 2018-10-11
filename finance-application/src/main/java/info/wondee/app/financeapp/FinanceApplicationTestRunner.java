package info.wondee.app.financeapp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import info.wondee.app.financeapp.user.FinanceUser;
import info.wondee.app.financeapp.user.FinanceUserRepository;

//@SpringBootApplication
@ComponentScan("info.wondee.app.financeapp")
public class FinanceApplicationTestRunner implements CommandLineRunner{
  
  @Autowired
  private FinanceUserRepository repository;

  @Override
  public void run(String... args) throws Exception {
   
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    String encode = encoder.encode("$Lakers11");
    
    FinanceUser entity = new FinanceUser("Wondrak", encode);
    repository.insert(entity );
    
    Optional<FinanceUser> findByName = repository.findByName("Wondrak");
    System.out.println(findByName);
  }

  
  public static void main(String[] args) {
    SpringApplication.run(FinanceApplication.class, args);

  }

}