package guru.springframework.service.mapservices;

import guru.springframework.domain.DomainObject;
import guru.springframework.domain.User;
import guru.springframework.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@Profile("map")
public class UserServiceMapImpl extends AbstractMapService implements UserService {

    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public User getById(Integer id) {
        return (User) super.getById(id);
    }

    @Override
    public User saveOrUpdate(User domainObject) {
        return (User) super.saveOrUpdate(domainObject);
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }

    @Override
    public User findByUserName(String username) {
        Optional returnUser = domainMap.values().stream().filter(domainObject -> {
            User user = (User) domainObject;
            return user.getUsername().equalsIgnoreCase(username);
        }).findFirst();

        return (User) returnUser.get();
    }
}
