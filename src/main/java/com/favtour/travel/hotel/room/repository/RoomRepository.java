package com.favtour.travel.hotel.room.repository;

import com.favtour.travel.hotel.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
