package mongodb.bank.dao;

import java.util.List;
import mongodb.bank.models.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, String> {
  Client save(Client client);
  List<Client> findByLastnameContainingIgnoreCaseOrFirstnameContainingIgnoreCaseOrAdressContainingIgnoreCase(
    String lastname,
    String firstname,
    String adress
  );

  List<Client> findByLastnameContainingIgnoreCaseAndFirstnameContainingIgnoreCaseAndAdressContainingIgnoreCase(
    String lastname,
    String firstname,
    String adress
  );

  List<Client> findByLastnameContainingIgnoreCaseAndFirstnameContainingIgnoreCase(String lastname, String firstname);

  List<Client> findByLastnameContainingIgnoreCase(String lastname);

  List<
    Client
  > findByLastnameContainingIgnoreCaseAndFirstnameContainingIgnoreCaseAndAdressContainingIgnoreCaseOrderByLastnameAscFirstnameAsc(
    String lastname,
    String firstname,
    String adress
  );

  List<Client> findAll();

  Client findByLastnameAndFirstnameAndAdress(String lastname, String firstname, String adress);

  Client findByLastnameAndFirstname(String lastname, String firstname);

  Client findByLastname(String lastname);

  Client findByAdress(String adress);

  Client findByPhoneNumber(String phoneNumber);

  Client findByLastnameAndFirstnameAndPhoneNumber(String lastname, String firstname, String phoneNumber);

  Void deleteByLastnameAndFirstnameAndPhoneNumber(String lastname, String firstname, String phoneNumber);

  Void deleteByLastnameAndFirstnameAndAdress(String lastname, String firstname, String adress);

}
