package com.squarecross.photoalbum.mapper;

import com.squarecross.photoalbum.domain.User;
import com.squarecross.photoalbum.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;
public class UserMapper {

    public static UserDto convertToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUserName(user.getUserName());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setCreatedAt(user.getCreatedAt());
        return userDto;
    }

    public static User convertToModel(UserDto userDto){
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUserName((userDto.getUserName()));
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setCreatedAt(userDto.getCreatedAt());
        return user;
    }

}
