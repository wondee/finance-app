package info.wondee.app.financeapp.user;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public abstract class UserLogin implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private String id;
  
  @Indexed(unique=true)
  private String name;
  
  @Setter
  private String password;

}
