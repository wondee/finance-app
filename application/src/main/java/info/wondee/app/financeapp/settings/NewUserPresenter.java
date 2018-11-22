package info.wondee.app.financeapp.settings;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserPresenter {

  @NotNull
  @Size(min=4, max=100)
  private String name;
  
  @NotNull
  @Size(min=8, max=40)
  private String password;
  
  
}
