package com.favtour.travel.hotel.hotel.controller;

import com.favtour.travel.core.payload.ApiResponse;
import com.favtour.travel.hotel.hotel.dto.HotelCard;
import com.favtour.travel.hotel.hotel.dto.HotelRequest;
import com.favtour.travel.hotel.hotel.dto.HotelResponse;
import com.favtour.travel.hotel.hotel.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<HotelCard>>> getAllHotels() {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "All hotels are ready", hotelService.getAllHotelCards()));
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<ApiResponse<HotelResponse>> getHotelById(@PathVariable int hotelId) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Hotel is found", hotelService.getHotelById(hotelId)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<HotelCard>>> searchHotel(@RequestParam String name,
                                                                    @RequestParam String destination,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Hotels are found", hotelService.findHotels(name, destination, from, to)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HotelResponse>> createHotel(@Valid @RequestPart HotelRequest hotelRequest,
                                                                  @RequestPart MultipartFile coverPhoto) {
        return ResponseEntity.status(HttpStatus.CREATED).body
                (new ApiResponse<>(true, "Hotel has been created successfully", hotelService.createHotel(hotelRequest, coverPhoto)));
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<ApiResponse<HotelResponse>> updateHotel(@PathVariable int hotelId,
                                                                  @RequestPart(required = false) HotelRequest hotelRequest,
                                                                  @RequestPart(required = false) MultipartFile coverPhoto) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Hotel has been updated successfully", hotelService.updateHotelById(hotelId, hotelRequest, coverPhoto)));
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<ApiResponse<Void>> deleteHotel(@PathVariable int hotelId) {
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Hotel has been deleted successfully", null));
    }
}
