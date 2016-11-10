package guru.springframework.service;

import guru.springframework.domain.User;

public interface UserService extends CrudService<User> {

    User findByUserName(String userName);

}
