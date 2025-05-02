package com.favtour.travel.user.dto;

import com.favtour.travel.user.entity.Users;

public class UserMapper {

    public static UserDto toDto(Users users){
        UserDto userDto= new UserDto();
        userDto.setId(users.getUserId());
        userDto.setEmail(users.getEmail());
        userDto.setFirstName(users.getProfile().getFirstName());
        userDto.setLastName(users.getProfile().getLastName());
        userDto.setNationality(users.getProfile().getNationality());
        return userDto;
    }
}
