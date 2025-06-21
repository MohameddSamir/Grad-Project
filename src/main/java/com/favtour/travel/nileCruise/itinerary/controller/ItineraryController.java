package com.favtour.travel.nileCruise.itinerary.controller;

import com.favtour.travel.core.payload.ApiResponse;
import com.favtour.travel.nileCruise.itinerary.dto.ItineraryDto;
import com.favtour.travel.nileCruise.itinerary.entity.Itinerary;
import com.favtour.travel.nileCruise.itinerary.service.ItineraryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itineraries")
@RequiredArgsConstructor
public class ItineraryController {

    private final ItineraryService itineraryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Itinerary>> addItinerary(@Valid @RequestBody ItineraryDto itineraryDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Itinerary has been saved successfully",
                        itineraryService.createItinerary(itineraryDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteItinerary(@PathVariable int id) {
        itineraryService.deleteItineraryById(id);
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Itinerary has been deleted successfully", null));
    }
}
