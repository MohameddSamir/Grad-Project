package com.favtour.travel.core.security.auth.service;

import com.favtour.travel.user.entity.Users;
import com.favtour.travel.user.repository.UsersRepository;
import com.favtour.travel.shared.EntityNotFoundException;
import com.favtour.travel.core.security.auth.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users= usersRepository.findByEmail(username).orElseThrow(()->
                new EntityNotFoundException("The User is not found"));
        return UserPrincipal.create(users);
    }
}
