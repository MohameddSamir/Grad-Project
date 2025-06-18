package com.favtour.travel.trip.service;

import com.favtour.travel.core.util.FileStorageService;
import com.favtour.travel.destination.service.DestinationService;
import com.favtour.travel.shared.EntityNotFoundException;
import com.favtour.travel.shared.FileStorageException;
import com.favtour.travel.trip.dto.TripRequest;
import com.favtour.travel.trip.entity.Category;
import com.favtour.travel.trip.mapper.TripMapper;
import com.favtour.travel.trip.dto.TripResponse;
import com.favtour.travel.trip.entity.Inclusion;
import com.favtour.travel.trip.entity.Trip;
import com.favtour.travel.trip.entity.TripImages;
import com.favtour.travel.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final FileStorageService fileStorageService;
    private final TripMapper tripMapper;
    private final DestinationService destinationService;
    private final CategoryService categoryService;

    private Trip findByIdOrThrow(int id){
        return tripRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Trip not found with id " + id));
    }

    public List<TripResponse> getAllTrips(){
        return tripRepository.findAll().stream().map(tripMapper ::mapTripToTripResponse).collect(Collectors.toList());
    }

    public TripResponse getTripById(int id){
        return tripMapper.mapTripToTripResponse(findByIdOrThrow(id));
    }

    @Transactional
    public TripResponse createTrip(TripRequest tripRequest, MultipartFile coverImage , MultipartFile[] images) {

        if(tripRequest == null){
            throw new IllegalArgumentException("Trip can not be null");
        }

        if(coverImage == null || coverImage.isEmpty()){
            throw new IllegalArgumentException("You must provide cover image");
        }

        if(images == null || images.length==0) {
            throw new IllegalArgumentException("You must add one image at least");
        }

        if(tripRequest.getInclusions() == null || tripRequest.getInclusions().isEmpty()){
            throw new IllegalArgumentException("You must add one inclusion at least");
        }

        Trip savedTrip= tripMapper.mapTripRequestToTrip(tripRequest);
        savedTrip.setTripImages(new ArrayList<>());
        savedTrip.setInclusions(new ArrayList<>());

        tripRepository.save(savedTrip);

        addInclusions(savedTrip, tripRequest.getInclusions());

        addCoverImage(savedTrip, coverImage);

        addImages(savedTrip, images);

        return tripMapper.mapTripToTripResponse(savedTrip);  // No need to save again (Jpa auto-update)
    }

    public TripResponse updateTripById(int id, TripRequest updatedTripRequest, MultipartFile coverImage,
                                       MultipartFile[] newImages){
        Trip trip = findByIdOrThrow(id);

        if(updatedTripRequest != null){

            validateFields(trip, updatedTripRequest);

            if(updatedTripRequest.getInclusions() != null){
                addInclusions(trip, updatedTripRequest.getInclusions());
            }
        }

        if(coverImage != null){
            addCoverImage(trip, coverImage);
        }

        if(newImages != null){
            addImages(trip, newImages);
        }

        return tripMapper.mapTripToTripResponse(tripRepository.save(trip));
    }

    public void deleteTrip(int id) throws IOException {
        Trip deleteTrip = findByIdOrThrow(id);
        fileStorageService.deleteImagesWithDirectory("photos/trip/"+id);
        tripRepository.delete(deleteTrip);
    }

    public void assignTripToCategory(int tripId, int categoryId){

        Trip trip = findByIdOrThrow(tripId);

        Category category = categoryService.findByIdOrThrow(categoryId);

        if(trip.getCategories() == null || trip.getCategories().isEmpty()){
            trip.setCategories(new ArrayList<>());
        }

        if(trip.getCategories().contains(category)){
            throw new IllegalArgumentException("Trip has been already assigned to this Category");
        }

        trip.getCategories().add(category);
        tripRepository.save(trip);
    }

    private void addCoverImage(Trip trip, MultipartFile coverImage) {

        String uploadDir= "photos/trip/"+ trip.getTripId();
        String imageFileName = System.currentTimeMillis() + "_" + coverImage.getOriginalFilename();
        trip.setCoverImage(imageFileName);

        try {
            fileStorageService.saveImage(coverImage, uploadDir, imageFileName);
        } catch (IOException e) {
            throw new FileStorageException("Could not save cover image");
        }
    }

    private void addImages(Trip trip, MultipartFile[] images){

        String uploadDir= "photos/trip/"+ trip.getTripId();

        for (MultipartFile image:images){
            validateImage(image);

            String imageFileName= System.currentTimeMillis() + "_" + image.getOriginalFilename();
            TripImages tripImage= new TripImages();
            tripImage.setImageUrl(imageFileName);
            tripImage.setTrip(trip);
            trip.addTripImage(tripImage);

            try {
                fileStorageService.saveImage(image,uploadDir,imageFileName);
            }catch (IOException exc){
                throw new FileStorageException("failed to store a file"+ image.getOriginalFilename());
            }

        }
    }

    private void validateFields(Trip trip, TripRequest updateTripRequest) {
        if(updateTripRequest.getLabel() != null){
            trip.setLabel(updateTripRequest.getLabel());
        }
        if(updateTripRequest.getDestination() != null){
            trip.setDestination(destinationService.getDestinationByName(updateTripRequest.getDestination()));
        }
        if(updateTripRequest.getDuration() != null){
            trip.setDuration(updateTripRequest.getDuration());
        }
        if(updateTripRequest.getRun() != null){
            trip.setRun(updateTripRequest.getRun());
        }
        if(updateTripRequest.getTripType() != null){
            trip.setTripType(updateTripRequest.getTripType());
        }
        if(updateTripRequest.getPrice() != 0){
            trip.setPrice(updateTripRequest.getPrice());
        }
        if(updateTripRequest.getDescription() != null){
            trip.setDescription(updateTripRequest.getDescription());
        }
        if(updateTripRequest.getInclusions() != null){
            trip.setInclusions(updateTripRequest.getInclusions());
        }
        if(updateTripRequest.getMeetingPoint() != null){
            trip.setMeetingPoint(updateTripRequest.getMeetingPoint());
        }
        if(updateTripRequest.getCancellationPolicy() != null){
            trip.setCancellationPolicy(updateTripRequest.getCancellationPolicy());
        }
    }

    private void validateImage(MultipartFile image){
        if(image.isEmpty()){
            throw new IllegalArgumentException("This image File is empty "+image.getOriginalFilename());
        }
    }

    private void addInclusions(Trip trip, List<Inclusion> inclusions) {
        for (Inclusion inclusion : inclusions) {
            inclusion.setTrip(trip);
        }
        trip.setInclusions(inclusions);
    }

}
