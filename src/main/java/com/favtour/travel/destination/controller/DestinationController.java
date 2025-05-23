package com.favtour.travel.destination.controller;

import com.favtour.travel.core.payload.ApiResponse;
import com.favtour.travel.destination.dto.*;
import com.favtour.travel.destination.entity.Destination;
import com.favtour.travel.destination.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/destinations")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DestinationCardDto>>> getAllDestinations() {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "All destinations are ready", destinationService.getAllDestinationCards()));
    }

    @GetMapping("/guide/{id}")
    public ResponseEntity<ApiResponse<DestinationWithActivities>> getDestinationById(@PathVariable int id) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Destination found", destinationService.getDestinationWithActivities(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DestinationWithTrips>> getDestinationWithTrips(@PathVariable int id) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Destination found", destinationService.getDestinationWithTrips(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DestinationResponse>> addDestination(@RequestPart DestinationRequest destinationRequest,
                                                                           @RequestPart("cover_photo") MultipartFile coverPhoto,
                                                                           @RequestPart("map_photo") MultipartFile mapPhoto) {

        return ResponseEntity.ok
                (new ApiResponse<>(true, "Destination has been saved successfully",
                        destinationService.createDestination(destinationRequest,coverPhoto,mapPhoto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DestinationResponse>> updateDestination(@PathVariable int id,
                                                                      @RequestPart(required = false) Destination destination,
                                                                      @RequestPart(name = "cover_photo", required = false) MultipartFile coverPhoto,
                                                                      @RequestPart(name = "map_photo", required = false) MultipartFile mapPhoto) {

        return ResponseEntity.ok
                (new ApiResponse<>(true, "Destination has been updated successfully",
                        destinationService.updateDestination(id,destination,coverPhoto,mapPhoto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDestination(@PathVariable int id) throws IOException {
        destinationService.deleteDestination(id);
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Destination and related trips has been deleted successfully", null));
    }

}
