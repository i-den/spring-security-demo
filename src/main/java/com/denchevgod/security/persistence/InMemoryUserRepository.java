package com.denchevgod.security.persistence;

import com.denchevgod.security.model.User;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, User> users = new ConcurrentHashMap<>();

    @Override
    public Iterable<User> findAll() {
        return users.values();
    }

    @Override
    public User save(User user) {
        Long id = user.getId();

        if (id == null) {
            id = counter.incrementAndGet();
            user.setId(id);
        }
        users.put(id, user);
        return user;
    }

    @Override
    public User findUserById(Long id) {
        return users.get(id);
    }

    @Override
    public void deleteUser(Long id) {
        users.remove(id);
    }
}
