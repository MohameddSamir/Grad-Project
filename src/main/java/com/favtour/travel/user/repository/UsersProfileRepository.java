package com.favtour.travel.user.repository;

import com.favtour.travel.user.entity.UsersProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersProfileRepository extends JpaRepository<UsersProfile, Integer> {
}
