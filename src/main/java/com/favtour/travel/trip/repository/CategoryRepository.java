package com.favtour.travel.trip.repository;

import com.favtour.travel.trip.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
