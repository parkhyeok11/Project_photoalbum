package com.squarecross.photoalbum.service;

import org.springframework.stereotype.Service;
import com.squarecross.photoalbum.mapper.UserMapper;
import com.squarecross.photoalbum.dto.UserDto;
import com.squarecross.photoalbum.domain.User;
import com.squarecross.photoalbum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static com.squarecross.photoalbum.mapper.UserMapper.convertToDto;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDto login(String userId, String password) {
        User user = userRepository.findByUserId(userId);
        if (user != null && user.getPassword().equals(password)) {
            return convertToDto(user);
        }
        return null;
    }
}
