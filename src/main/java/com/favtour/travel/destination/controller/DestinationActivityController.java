package com.favtour.travel.destination.controller;

import com.favtour.travel.core.payload.ApiResponse;
import com.favtour.travel.destination.entity.DestinationActivity;
import com.favtour.travel.destination.service.DestinationActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/destinations/{destinationId}/activities")
@RequiredArgsConstructor
public class DestinationActivityController {

    private final DestinationActivityService destinationActivityService;

    @PostMapping
    public ResponseEntity<ApiResponse<DestinationActivity>> saveDestinationActivity(@PathVariable int destinationId,
                                                                                    @RequestPart DestinationActivity destinationActivity,
                                                                                    @RequestPart(name = "cover_photo") MultipartFile coverPhoto) {

        return ResponseEntity.status(HttpStatus.CREATED).body
                (new ApiResponse<>(true, "Destination Activity has been added",
                        destinationActivityService.saveDestinationActivity(destinationId, destinationActivity, coverPhoto)));
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<ApiResponse<Void>> deleteDestinationActivity(@PathVariable("destinationId") int destinationId,
                                                                       @PathVariable int activityId) throws IOException {

        destinationActivityService.deleteDestinationActivity(destinationId, activityId);
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Activity has been deleted successfully", null));
    }
}
