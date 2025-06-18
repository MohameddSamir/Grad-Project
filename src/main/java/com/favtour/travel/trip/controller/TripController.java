package com.favtour.travel.trip.controller;

import com.favtour.travel.core.payload.ApiResponse;
import com.favtour.travel.trip.dto.TripRequest;
import com.favtour.travel.trip.dto.TripResponse;
import com.favtour.travel.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TripResponse>>> trips(){
        return ResponseEntity.ok
                (new ApiResponse<>(true, "All trips are ready", tripService.getAllTrips()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TripResponse>> trip(@PathVariable int id){
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Trip found", tripService.getTripById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TripResponse>> saveTrip(@RequestPart TripRequest tripRequest,
                                                              @RequestPart MultipartFile coverImage,
                                                              @RequestPart MultipartFile[] images) {

        return ResponseEntity.ok
                (new ApiResponse<>(true, "Trip has been saved successfully",
                        tripService.createTrip(tripRequest, coverImage, images)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TripResponse>> updateTrip(@PathVariable int id,
                                                                @RequestPart(required = false) TripRequest tripRequest,
                                                                @RequestPart(required = false) MultipartFile coverImage,
                                                                @RequestPart(value = "images", required = false) MultipartFile[] newImages){

        return ResponseEntity.ok
                (new ApiResponse<>(true, "Trip has been updated successfully",
                        tripService.updateTripById(id, tripRequest, coverImage, newImages)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTrip(@PathVariable int id) throws IOException {
        tripService.deleteTrip(id);
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Trip of id " + id + " has been deleted successfully",  null));
    }

    // assign the trip to a category
    @PostMapping("/{tripId}/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> assignToCategory(@PathVariable int tripId, @PathVariable int categoryId) {
        tripService.assignTripToCategory(tripId, categoryId);
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Trip has been assigned to category successfully",null));
    }

}
