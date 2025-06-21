package com.favtour.travel.nileCruise.nileCruise.controller;

import com.favtour.travel.core.payload.ApiResponse;
import com.favtour.travel.nileCruise.nileCruise.dto.NileCruiseCard;
import com.favtour.travel.nileCruise.nileCruise.dto.NileCruiseRequest;
import com.favtour.travel.nileCruise.nileCruise.dto.NileCruiseResponse;
import com.favtour.travel.nileCruise.nileCruise.entity.NileCruise;
import com.favtour.travel.nileCruise.nileCruise.service.NileCruiseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/nile-cruises")
@RequiredArgsConstructor
public class NileCruiseController {

    private final NileCruiseService nileCruiseService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NileCruiseCard>>> getAllNileCruises(){
        return ResponseEntity.ok
                (new ApiResponse<>(true, "All Nile cruises are ready", nileCruiseService.getNileCruiseCards()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NileCruise>> getNileCruiseById(@PathVariable int id) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Nile cruise found", nileCruiseService.getNileCruiseById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<NileCruiseCard>>> search(@RequestParam String label) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Nile cruises are found", nileCruiseService.getNileCruiseCardsByName(label)));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<NileCruiseCard>>> filter(@RequestParam String itinerary) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Nile cruises are found", nileCruiseService.getNileCruiseCardsByItinerary(itinerary)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NileCruiseResponse>> addNileCruise(@Valid @RequestPart NileCruiseRequest nileCruiseRequest,
                                                                         @RequestPart MultipartFile coverPhoto,
                                                                         @RequestPart MultipartFile[] NileCruiseImages) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Nile Cruise has been saved successfully",
                        nileCruiseService.createNileCruise(nileCruiseRequest, coverPhoto, NileCruiseImages)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NileCruiseResponse>> updateNileCruise(@PathVariable int id,
                                               @RequestPart(required = false) NileCruiseRequest nileCruiseRequest,
                                               @RequestPart(required = false) MultipartFile coverPhoto,
                                               @RequestPart(required = false) MultipartFile[] NileCruiseImages) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Nile cruise has been updated successfully",
                        nileCruiseService.updateNileCruiseById(id, nileCruiseRequest, coverPhoto, NileCruiseImages)));
    }

    @PutMapping("/{nileCruiseId}/assign-to-itinerary/{itineraryId}")
    public ResponseEntity<ApiResponse<Void>> assignItineraryToNileCruise(@PathVariable int nileCruiseId,
                                                                         @PathVariable int itineraryId) {
        nileCruiseService.assignItineraryToNileCruise(nileCruiseId, itineraryId);
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Itinerary has been added successfully to Nile cruise", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNileCruise(@PathVariable int id) {
        nileCruiseService.deleteNileCruise(id);
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Nile cruise has been deleted successfully", null));
    }
}
