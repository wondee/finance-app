package info.wondee.app.financeapp.user;

public interface UserAccountCustomRepository {

  UserAccount findCurrentUser();
  
}
