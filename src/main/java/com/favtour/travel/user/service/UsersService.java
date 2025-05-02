package com.favtour.travel.user.service;

import com.favtour.travel.user.dto.UserCreateDto;
import com.favtour.travel.user.dto.UserDto;
import com.favtour.travel.user.dto.UserMapper;
import com.favtour.travel.user.entity.Users;
import com.favtour.travel.user.entity.UsersProfile;
import com.favtour.travel.user.exception.DuplicateEmailException;
import com.favtour.travel.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto save(UserCreateDto userCreateDto){
        if(usersRepository.findByEmail(userCreateDto.getEmail()).isPresent()){
            throw new DuplicateEmailException("Email "+ userCreateDto.getEmail() +" is Already registered, Please try other Email");
        }

        Users users= new Users();
        users.setEmail(userCreateDto.getEmail());
        users.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        users.setRole("USER");
        users.setActive(true);

        UsersProfile usersProfile= new UsersProfile(users);
        usersProfile.setFirstName(userCreateDto.getFirstName());
        usersProfile.setLastName(userCreateDto.getLastName());
        usersProfile.setPhone(userCreateDto.getPhone());
        usersProfile.setNationality(userCreateDto.getNationality());
        users.setProfile(usersProfile);
        Users savedUser= usersRepository.save(users);

        return UserMapper.toDto(savedUser);
    }
}
