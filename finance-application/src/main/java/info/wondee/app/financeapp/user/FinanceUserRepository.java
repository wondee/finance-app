package info.wondee.app.financeapp.user;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FinanceUserRepository extends MongoRepository<FinanceUser, String>, FinanceUserCustomRespository {

  @Cacheable(cacheNames="userCache", key="#name")
  Optional<FinanceUser> findByName(String name);

  @CacheEvict(cacheNames="userCache", key="#user.name")
  <S extends FinanceUser> S save(S user);
  
}
