package com.denchevgod.security.service;

import com.denchevgod.security.model.User;
import com.denchevgod.security.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TODO: Fix Exception
    @Override
    public User saveNewUser(User userToSave) {
        User persistedUser = userRepository.findByUsername(userToSave.getUsername());
        if (persistedUser != null) {
            throw new RuntimeException("Username is taken");
        }
        return userRepository.save(userToSave);
    }

    @Override
    public User updateUser(User userToUpdate) {
        User persistedUser = userRepository.findByUsername(userToUpdate.getUsername());
        if (persistedUser != null) {
            throw new RuntimeException("Username is taken");
        }
        return userRepository.save(userToUpdate);
    }
}
