package guru.springframework.service;

import guru.springframework.domain.Customer;
import guru.springframework.domain.DomainObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CustomerServiceImpl extends AbstractMapService implements CustomerService {

    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public Customer getById(Integer id) {
        return (Customer) super.getById(id);
    }

    @Override
    public Customer saveOrUpdate(Customer customer) {
        return (Customer) super.saveOrUpdate(customer);
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }

    protected void loadDomainObjects() {
        domainMap = new HashMap<>();

        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setFirstName("Alberta");
        customer1.setLastName("Shultz");
        customer1.setEmail("AlbertaGShultz@jourrapide.com");
        customer1.setPhoneNumber("732-619-7166");
        customer1.setAddressLineOne("3136 Webster Street");
        customer1.setAddressLineTwo("");
        customer1.setCity("Teterboro");
        customer1.setState("NJ");
        customer1.setZipCode("07608");
        domainMap.put(1, customer1);

        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setFirstName("Tami");
        customer2.setLastName("Cabezas");
        customer2.setEmail("TamiJCabezas@jourrapide.com");
        customer2.setPhoneNumber("205-271-9661");
        customer2.setAddressLineOne("2525 Retreat Avenue");
        customer2.setAddressLineTwo("");
        customer2.setCity("Birmingham");
        customer2.setState("AL");
        customer2.setZipCode("35203");
        domainMap.put(2, customer2);

        Customer customer3 = new Customer();
        customer3.setId(3);
        customer3.setFirstName("William");
        customer3.setLastName("Philip");
        customer3.setEmail("WilliamGPhilip@dayrep.com");
        customer3.setPhoneNumber("214-225-1154");
        customer3.setAddressLineOne("482 Fancher Drive");
        customer3.setAddressLineTwo("");
        customer3.setCity("Dallas");
        customer3.setState("TX");
        customer3.setZipCode("75201");
        domainMap.put(3, customer3);

        Customer customer4 = new Customer();
        customer4.setId(4);
        customer4.setFirstName("Paul");
        customer4.setLastName("Heslin");
        customer4.setEmail("PaulCHeslin@armyspy.com");
        customer4.setPhoneNumber("406-721-8674");
        customer4.setAddressLineOne("2479 Tibbs Avenue");
        customer4.setAddressLineTwo("");
        customer4.setCity("Missoula");
        customer4.setState("MT");
        customer4.setZipCode("59802");
        domainMap.put(4, customer4);

        Customer customer5 = new Customer();
        customer5.setId(5);
        customer5.setFirstName("Douglas");
        customer5.setLastName("Scott");
        customer5.setEmail("DouglasLScott@jourrapide.com");
        customer5.setPhoneNumber("540-453-6394");
        customer5.setAddressLineOne("4471 Jehovah Drive");
        customer5.setAddressLineTwo("");
        customer5.setCity("Harrisonburg");
        customer5.setState("VA");
        customer5.setZipCode("22801");
        domainMap.put(5, customer5);
    }
}
