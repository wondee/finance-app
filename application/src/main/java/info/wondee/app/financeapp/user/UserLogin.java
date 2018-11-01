package info.wondee.app.financeapp.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class UserLogin {
  @Id
  private String id;
  
  @Indexed(unique=true)
  private String name;
  
  private String password;

}
