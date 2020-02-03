package com.denchevgod.security.service;

import com.denchevgod.security.model.User;

public interface UserService {

    User saveNewUser(User user);

    User updateUser(User user);
}
