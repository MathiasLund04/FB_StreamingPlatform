package Repository.User;

import Model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAllUsers();
    Optional<User> findByEmail(String email);
}
