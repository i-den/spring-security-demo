package com.denchevgod.security.converters;

import com.denchevgod.security.model.User;
import com.denchevgod.security.persistence.UserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringIdToUserConverter implements Converter<String, User> {

    private final UserRepository userRepository;

    public StringIdToUserConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User convert(String id) {
        return userRepository.findUserById(Long.valueOf(id));
    }
}
