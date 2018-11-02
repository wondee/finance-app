package info.wondee.app.financeapp.user;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FinanceUserRepository extends MongoRepository<FinanceUser, String> {

  Optional<FinanceUser> findByName(String name);
  
}
