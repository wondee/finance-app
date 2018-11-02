package info.wondee.app.financeapp.financedata;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FinanceDataRepository extends MongoRepository<FinanceData, ObjectId>, FinanceDataCustomRepository {

  
  @Override
  @CacheEvict(cacheNames={"financeDataCache", "overviewCache"}, key="#entity.id")
  <S extends FinanceData> S save(S entity);
  
  @Override
  @CacheEvict(cacheNames={"financeDataCache", "overviewCache"}, key="#entity.id")
  <S extends FinanceData> S insert(S entity);

  @Override
  @Cacheable(cacheNames="financeDataCache", key="#id")
  Optional<FinanceData> findById(ObjectId id);
  
}
