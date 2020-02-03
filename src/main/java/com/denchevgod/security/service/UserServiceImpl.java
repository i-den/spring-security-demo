package com.denchevgod.security.service;

import com.denchevgod.security.model.User;
import com.denchevgod.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // TODO: Fix Exception
    @Override
    public User saveNewUser(User userToSave) {
        User persistedUser = userRepository.findByUsername(userToSave.getUsername());
        if (persistedUser != null) {
            throw new RuntimeException("Username is taken");
        }
        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));
        return userRepository.save(userToSave);
    }

    @Override
    public User updateUser(User userToUpdate) {
        User persistedUser = userRepository.findByUsername(userToUpdate.getUsername());
        if (persistedUser != null) {
            throw new RuntimeException("Username is taken");
        }
        userToUpdate.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));
        return userRepository.save(userToUpdate);
    }
}
