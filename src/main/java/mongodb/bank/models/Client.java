package mongodb.bank.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Client {

  private String lastname;
  private String firstname;
  private String adress;
  private String phoneNumber;
}
