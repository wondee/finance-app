package info.wondee.app.financeapp.user;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;


@Document
@Getter
public class UserAccount extends UserLogin {

  private static final long serialVersionUID = 1L;
  
  @Setter
  private ObjectId dataId;

  public UserAccount(String id, String name, String password, ObjectId dataId) {
    super(id, name, password);
    
    this.dataId = dataId;
  }

  
}
