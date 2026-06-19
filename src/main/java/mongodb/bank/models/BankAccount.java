package mongodb.bank.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

  @Id
  private String accountNumber;
  private String balance;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime openingDate;
  private String clientId;
}
