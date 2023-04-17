package com.hieu.crypto.service.core.impl;

import com.hieu.crypto.entity.User;
import com.hieu.crypto.repository.UserRepository;
import com.hieu.crypto.service.core.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        if (StringUtils.isNotEmpty(username))
            return userRepository.findByUsername(username);
        return null;
    }

    @Override
    public User save(User user) {
        if (Objects.nonNull(user))
            return userRepository.save(user);
        return null;
    }
}
