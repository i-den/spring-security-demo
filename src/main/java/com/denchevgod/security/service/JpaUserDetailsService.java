package com.denchevgod.security.service;

import com.denchevgod.security.model.User;
import com.denchevgod.security.repository.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User persistedUser = userRepository.findByUsername(username);
        if (persistedUser == null) {
            throw new UsernameNotFoundException("User " + username + " not found!");
        }
        return new org.springframework.security.core.userdetails.User(
                username,
                persistedUser.getPassword(),
                true,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList("ROLE_USER")
        );
    }
}
