package com.denchevgod.security.service;

import com.denchevgod.security.model.User;
import com.denchevgod.security.validation.EmailExistsException;

public interface UserService {

    User registerNewUser(User user) throws EmailExistsException;

    User updateExistingUser(User user) throws EmailExistsException;

}
