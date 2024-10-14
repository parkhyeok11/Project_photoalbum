package com.squarecross.photoalbum.service;

import org.springframework.stereotype.Service;
import com.squarecross.photoalbum.mapper.UserMapper;
import com.squarecross.photoalbum.dto.UserDto;
import com.squarecross.photoalbum.domain.User;
import com.squarecross.photoalbum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public UserDto signup(String userId, String password) {
        // 중복체크
        if (userRepository.findByUserId(userId) != null) {
            throw new IllegalArgumentException("User ID already exists");
        }

        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setPassword(password);

        // 넘버생성
        Long userNumber = generateUserNumber();
        newUser.setUserNumber(userNumber);

        // db에 저장
        User savedUser = userRepository.save(newUser);

        return convertToDto(savedUser);
    }

    private Long generateUserNumber() {
        return userRepository.count() + 1;
    }
}