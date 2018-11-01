package info.wondee.app.financeapp.financedata;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FinanceDataRepository extends MongoRepository<FinanceData, ObjectId>, FinanceDataCustomRepository {

  
  @Override
  <S extends FinanceData> S save(S entity);


}
