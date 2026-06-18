package mongodb.bank.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Operation {

  private String type;
  private String amount;
  private String date;
  private String accountId;
}
