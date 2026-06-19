package mongodb.bank.dao;

import java.util.List;
import mongodb.bank.models.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<BankAccount, String> {
  BankAccount save(BankAccount bankAccount);
  List<BankAccount> findByClientId(String clientId);

  BankAccount findByAccountNumber(String accountNumber);

  void deleteByAccountNumber(String accountNumber);

  void deleteByClientId(String clientId);

  void deleteByClientIdAndAccountNumber(String clientId, String accountNumber);
}
