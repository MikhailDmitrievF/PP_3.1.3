package boot_security.service;

import boot_security.model.User;
import java.util.List;

public interface UserService {
    List<User> getAll();

    void add(User user);

    void update(User user);

    void delete(long id);

    User findById(long id);

}

