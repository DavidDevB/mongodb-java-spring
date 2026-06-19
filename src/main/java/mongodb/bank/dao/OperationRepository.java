package mongodb.bank.dao;

import java.util.List;
import mongodb.bank.models.Operation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OperationRepository extends MongoRepository<Operation, String> {
  Operation save(Operation operation);
  List<Operation> findByAccountId(String accountId);

  void deleteByAccountId(String accountId);

  void deleteByAccountIdAndType(String accountId, String type);

  void deleteByAccountIdAndDate(String accountId, String date);

  void deleteByAccountIdAndTypeAndDate(String accountId, String type, String date);

  void deleteByAccountIdAndTypeAndDateAndAmount(String accountId, String type, String date, String amount);

  void deleteByAccountIdAndTypeAndDateAndAmountAndId(
    String accountId,
    String type,
    String date,
    String amount,
    String id
  );
}
