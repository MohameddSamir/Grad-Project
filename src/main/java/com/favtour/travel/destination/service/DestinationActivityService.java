package com.favtour.travel.destination.service;

import com.favtour.travel.core.util.FileStorageService;
import com.favtour.travel.destination.entity.Destination;
import com.favtour.travel.destination.entity.DestinationActivity;
import com.favtour.travel.destination.repository.DestinationActivityRepository;
import com.favtour.travel.shared.EntityNotFoundException;
import com.favtour.travel.shared.FileStorageException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DestinationActivityService {

    private final DestinationActivityRepository destinationActivityRepository;
    private final DestinationService destinationService;
    private final FileStorageService fileStorageService;

    @Transactional
    public DestinationActivity saveDestinationActivity(int destinationId, DestinationActivity destinationActivity, MultipartFile coverPhoto) {

        Destination destination= destinationService.getDestinationById(destinationId);

        destinationActivity.setDestination(destination);

        if(coverPhoto == null || coverPhoto.isEmpty()){
            throw new IllegalArgumentException("You must provide a cover photo");
        }

        DestinationActivity savedDestinationActivity = destinationActivityRepository.save(destinationActivity);

        addCoverPhoto(destinationActivity, coverPhoto);
        return savedDestinationActivity;
    }

    public void deleteDestinationActivity(int destinationId, int activityId) throws IOException {

        Destination destination= destinationService.getDestinationById(destinationId);

       DestinationActivity destinationActivity = destinationActivityRepository.findById(activityId).orElseThrow(()->
               new EntityNotFoundException("Destination activity not found"));
       fileStorageService.deleteImagesWithDirectory("/photos/destination/" + destination.getDestinationId()+ "/activities");

       destinationActivityRepository.delete(destinationActivity);
    }

    private void addCoverPhoto(DestinationActivity destinationActivity, MultipartFile coverPhoto) {

        String uploadDirectory= "photos/destination/" + destinationActivity.getDestination().getDestinationId() + "/activities";
        String coverPhotoName = System.currentTimeMillis() + "_" + coverPhoto.getOriginalFilename();

        destinationActivity.setCoverPhoto(coverPhotoName);

        try {
            fileStorageService.saveImage(coverPhoto, uploadDirectory, coverPhotoName);
        }catch (IOException exc){
            throw new FileStorageException("can't save cover photo");
        }
    }
}
