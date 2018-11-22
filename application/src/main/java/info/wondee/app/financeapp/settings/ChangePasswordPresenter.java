package info.wondee.app.financeapp.settings;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordPresenter {

  @NotNull
  @Size(min=8, max=40)
  private String newPassword;
  
  @NotNull
  @Size(min=8, max=40)
  private String oldPassword;
  
  
}
