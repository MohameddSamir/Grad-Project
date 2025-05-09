package com.favtour.travel.destination.service;

import com.favtour.travel.core.util.FileStorageService;
import com.favtour.travel.destination.dto.DestinationCardDto;
import com.favtour.travel.destination.dto.DestinationWithTrips;
import com.favtour.travel.destination.entity.Destination;
import com.favtour.travel.destination.entity.DestinationActivity;
import com.favtour.travel.destination.repository.DestinationRepository;
import com.favtour.travel.shared.EntityNotFoundException;
import com.favtour.travel.shared.FileStorageException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final FileStorageService fileStorageService;

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public List<DestinationCardDto> getAllDestinationCards() {
        return destinationRepository.findAll().stream().map(destination ->
                new DestinationCardDto(destination.getDestinationId(), destination.getDestinationName(),
                        destination.getCoverPhoto(), destination.getTrips().size())).toList();
    }

    public Destination getDestinationById(int id) {
        return destinationRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Destination not found with id " + id));
    }

    public Destination getDestinationByName(String name) {
        return destinationRepository.findByDestinationName(name).orElseThrow(()->
                new EntityNotFoundException("Destination not found with name " + name));
    }

    public DestinationWithTrips getDestinationWithTrips(int id) {
        Destination destination = getDestinationById(id);
        return new DestinationWithTrips(destination.getDestinationId(), destination.getDestinationName(), destination.getCoverPhoto(), destination.getTrips());
    }

    @Transactional
    public Destination createDestination(Destination destination, MultipartFile coverPhoto, MultipartFile mapPhoto) {

        validateArguments(destination, coverPhoto, mapPhoto);

        for (DestinationActivity activity : destination.getDestinationActivities()) {
            activity.setDestination(destination);
        }

        Destination savedDestination = destinationRepository.save(destination);

        addPhotos(savedDestination, coverPhoto, mapPhoto);

        return savedDestination;
    }

    public Destination updateDestination(int id, Destination updatedDestination, MultipartFile coverPhoto, MultipartFile mapPhoto) {

        if(updatedDestination == null) {
            throw new IllegalArgumentException("Destination can not be null");
        }

        Destination destination = getDestinationById(id);

        validateFields(destination, updatedDestination);
        if(updatedDestination.getCoverPhoto() != null && mapPhoto != null) {
            addPhotos(destination, coverPhoto, mapPhoto);
        }
        if(updatedDestination.getDestinationActivities() != null){
            destination.setDestinationActivities(updatedDestination.getDestinationActivities());
            for (DestinationActivity activity : updatedDestination.getDestinationActivities()) {
                activity.setDestination(destination);
            }
        }
        return destinationRepository.save(destination);
    }

    public void deleteDestination(int id) throws IOException {
        Destination destination = getDestinationById(id);
        fileStorageService.deleteImagesWithDirectory("photos/destination/"+id);
        destinationRepository.deleteById(id);
    }

    private void validateFields(Destination destination, Destination updatedDestination) {
        if(updatedDestination.getDestinationName() != null){
            destination.setDestinationName(updatedDestination.getDestinationName());
        }
        if(updatedDestination.getCoverPhoto() != null){
            destination.setCoverPhoto(updatedDestination.getCoverPhoto());
        }
        if(updatedDestination.getTrips() != null){
            destination.setTrips(updatedDestination.getTrips());
        }
        if(updatedDestination.getDestinationGuide() != null){
            destination.setDestinationGuide(updatedDestination.getDestinationGuide());
        }
    }

    private void addPhotos(Destination destination, MultipartFile coverPhoto, MultipartFile mapPhoto) {

        String uploadDirectory= "photos/destination/" + destination.getDestinationId();
        String coverPhotoName= System.currentTimeMillis() + "_" + coverPhoto.getOriginalFilename();
        String mapPhotoName= System.currentTimeMillis() + "_" + mapPhoto.getOriginalFilename();

        destination.setCoverPhoto(coverPhotoName);
        destination.setMapPhoto(mapPhotoName);

        try {
            fileStorageService.saveImage(coverPhoto, uploadDirectory,coverPhotoName);
            fileStorageService.saveImage(mapPhoto, uploadDirectory,mapPhotoName);
        }catch (IOException exc){
            throw new FileStorageException("Can not save photos");
        }
    }

    private void validateArguments(Destination destination, MultipartFile coverPhoto, MultipartFile mapPhoto) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination can not be null");
        }

        if (coverPhoto == null || coverPhoto.isEmpty()) {
            throw new IllegalArgumentException("You must provide a cover photo");
        }

        if (mapPhoto == null || mapPhoto.isEmpty()) {
            throw new IllegalArgumentException("You must provide a map photo");
        }
    }

}
