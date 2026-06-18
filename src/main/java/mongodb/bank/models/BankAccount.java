package mongodb.bank.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BankAccount {

  private String accountNumber;
  private String balance;
  private LocalDateTime openingDate;
  private String clientId;
}
