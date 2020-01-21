package com.denchevgod.security.persistence;

import com.denchevgod.security.model.User;

public interface UserRepository {

    Iterable<User> findAll();

    User save(User user);

    User findUserById(Long id);

    void deleteUser(Long id);
}
