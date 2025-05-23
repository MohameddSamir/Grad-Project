package com.favtour.travel.destination.service;

import com.favtour.travel.core.util.FileStorageService;
import com.favtour.travel.destination.dto.*;
import com.favtour.travel.destination.entity.Destination;
import com.favtour.travel.destination.mapper.DestinationMapper;
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
    private final DestinationMapper destinationMapper;


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

    public DestinationWithActivities getDestinationWithActivities(int id) {
        Destination destination = getDestinationById(id);
        return destinationMapper.toDestinationWithActivities(destination);
    }

    @Transactional
    public DestinationResponse createDestination(DestinationRequest destinationRequest, MultipartFile coverPhoto, MultipartFile mapPhoto) {

        validateArguments(destinationRequest, coverPhoto, mapPhoto);

        Destination savedDestination = destinationRepository.save(destinationMapper.toDestination(destinationRequest));

        addPhotos(savedDestination, coverPhoto, mapPhoto);

        return destinationMapper.toDestinationResponse(savedDestination);
    }

    public DestinationResponse updateDestination(int id, Destination updatedDestination, MultipartFile coverPhoto, MultipartFile mapPhoto) {

        Destination destination = getDestinationById(id);

        if(updatedDestination != null) {
            validateFields(destination, updatedDestination);
        }

        if(coverPhoto != null && mapPhoto != null) {
            addPhotos(destination, coverPhoto, mapPhoto);
        }

        return destinationMapper.toDestinationResponse(destinationRepository.save(destination));
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

    private void validateArguments(DestinationRequest destinationRequest, MultipartFile coverPhoto, MultipartFile mapPhoto) {
        if (destinationRequest == null) {
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
