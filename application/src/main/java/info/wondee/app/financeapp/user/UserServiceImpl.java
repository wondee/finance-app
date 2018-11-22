package info.wondee.app.financeapp.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import info.wondee.app.financeapp.BusinessException;
import info.wondee.app.financeapp.financedata.FinanceData;
import info.wondee.app.financeapp.financedata.FinanceDataRepository;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private FinanceUserRepository financeUserRepository;
  
  @Autowired
  private UserAccountRepository userAccountRespository;
  
  @Autowired
  private FinanceDataRepository financeDataRepository;
  
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void createUser(String name, String password) {
    
    if (findByName(name).isPresent()) {
      throw new BusinessException("Der Name '%s' ist bereits vergeben", name);
    }
    
    userAccountRespository.insert(
        new UserAccount(null, name, passwordEncoder.encode(password), findFinanceData().getId()));
    
  }
  
  @Override
  public void changePassword(String oldPassword, String newPassword) {
    UserAccount currentUser = userAccountRespository.findCurrentUser();
    
    if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
      throw new BusinessException("Passwort ist nicht korrekt");
    }
    
    currentUser.setPassword(passwordEncoder.encode(newPassword));
    
    userAccountRespository.save(currentUser);
    
  }
  
  @Override
  @Cacheable(cacheNames="userCache", key="#name")
  public Optional<? extends UserLogin> findByName(String name) {

    Optional<FinanceUser> oldUserDocument = financeUserRepository.findByName(name);
    
    if (!oldUserDocument.isPresent()) {
      return userAccountRespository.findByName(name);
      
    } 
    
    FinanceUser oldUser = oldUserDocument.get();
    
    FinanceData financeData = new FinanceData(null, 
          oldUser.getCurrentAmount(), 
          oldUser.getMonthlyFixedCosts(), 
          oldUser.getQuaterlyFixedCosts(), 
          oldUser.getYearlyFixedCosts(), 
          oldUser.getSpecialCosts()
        );
    
    FinanceData newFinanceData = financeDataRepository.insert(financeData);

    UserAccount userAccount = userAccountRespository.insert(
        new UserAccount(null, name, oldUser.getPassword(), newFinanceData.getId()));
    
    financeUserRepository.delete(oldUser);
    
    return Optional.of(userAccount);
  }

  @Override
  public FinanceData findFinanceData() {
    UserAccount currentUser = userAccountRespository.findCurrentUser();
    return financeDataRepository.findById(currentUser.getDataId()).orElseGet(() -> createFinanceData(currentUser));
  }

  private FinanceData createFinanceData(UserAccount currentUser) {
    FinanceData financeData = new FinanceData();
    
    FinanceData created = financeDataRepository.insert(financeData);
    
    currentUser.setDataId(created.getId());
    
    userAccountRespository.save(currentUser);
    
    return created;
  }


  
}
