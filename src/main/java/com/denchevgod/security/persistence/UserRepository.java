package com.denchevgod.security.persistence;

import com.denchevgod.security.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    Iterable<User> findAll();

    User save(User user);

    User findUserById(Long id);

    void deleteUser(Long id);
}
