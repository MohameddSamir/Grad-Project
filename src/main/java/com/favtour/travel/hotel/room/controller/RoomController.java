package com.favtour.travel.hotel.room.controller;

import com.favtour.travel.core.payload.ApiResponse;
import com.favtour.travel.hotel.room.dto.RoomRequest;
import com.favtour.travel.hotel.room.dto.RoomResponse;
import com.favtour.travel.hotel.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoomResponse>>> getAllRooms() {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "All rooms are ready", roomService.getAllRooms()));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse<RoomResponse>> getRoomById(@PathVariable int roomId) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Room found", roomService.getRoomById(roomId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoomResponse>> createRoom(@Valid @RequestPart RoomRequest roomRequest,
                                                                @RequestPart MultipartFile[] images) {
        return ResponseEntity.status(HttpStatus.CREATED).body
                (new ApiResponse<>(true, "The room has been created successfully", roomService.createRoom(roomRequest, images)));
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<ApiResponse<RoomResponse>> updateRoom(@PathVariable int roomId,
                                                                @RequestPart(required = false) RoomRequest roomRequest,
                                                                @RequestPart(required = false) MultipartFile[] images) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Room has been updated successfully", roomService.updateRoom(roomId, roomRequest, images)));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable int roomId) {
        roomService.deleteRoomById(roomId);
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Room has been deleted successfully", null));
    }
}
