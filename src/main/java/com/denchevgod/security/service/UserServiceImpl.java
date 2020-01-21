package com.denchevgod.security.service;

import com.denchevgod.security.model.User;
import com.denchevgod.security.persistence.UserRepository;
import com.denchevgod.security.validation.EmailExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User registerNewUser(User user) throws EmailExistsException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailExistsException("User with email " + user.getEmail() + " exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateExistingUser(User user) throws EmailExistsException {
        User emailOwner = userRepository.findByEmail(user.getEmail());
        if (emailOwner != null && !user.getId().equals(emailOwner.getId())) {
            throw new EmailExistsException("Email is taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
