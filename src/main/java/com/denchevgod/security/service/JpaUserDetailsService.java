package com.denchevgod.security.service;

import com.denchevgod.security.model.User;
import com.denchevgod.security.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                createAuthority("ROLE_USER")
        );
    }

    private List<GrantedAuthority> createAuthority(String... authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(authorities.length);
        for (String authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        return grantedAuthorities;
    }
}
