package info.wondee.app.financeapp.user;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserAccountRepository extends MongoRepository<UserAccount, String>, UserAccountCustomRepository {

  Optional<UserAccount> findByName(String name);

  <S extends UserAccount> S save(S user);


}
