package info.wondee.app.financeapplication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCreator {


  public static void main(String[] args) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    String encode = encoder.encode("$Lakers11");

    System.out.println(encode);
  }
  
}
