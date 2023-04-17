package com.hieu.crypto.service.core;

import com.hieu.crypto.entity.User;

public interface UserService {

    User findByUsername(String username);
    User save(User user);
}
