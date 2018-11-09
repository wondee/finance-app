package info.wondee.app.financeapp.user;

import java.util.Optional;

import info.wondee.app.financeapp.financedata.FinanceData;

public interface UserService {

  Optional<? extends UserLogin> findByName(String name);

  FinanceData findFinanceData();

  void createUser(String name, String password);
  
}
