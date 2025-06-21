package com.favtour.travel.nileCruise.nileCruise.service;

import com.favtour.travel.core.util.FileStorageService;
import com.favtour.travel.nileCruise.nileCruise.dto.NileCruiseCard;
import com.favtour.travel.nileCruise.nileCruise.dto.NileCruiseRequest;
import com.favtour.travel.nileCruise.nileCruise.dto.NileCruiseResponse;
import com.favtour.travel.nileCruise.itinerary.entity.Itinerary;
import com.favtour.travel.nileCruise.nileCruise.entity.NileCruise;
import com.favtour.travel.nileCruise.nileCruise.entity.NileCruiseImage;
import com.favtour.travel.nileCruise.nileCruise.mapper.NileCruiseMapper;
import com.favtour.travel.nileCruise.itinerary.repository.ItineraryRepository;
import com.favtour.travel.nileCruise.nileCruise.repository.NileCruiseRepository;
import com.favtour.travel.shared.EntityNotFoundException;
import com.favtour.travel.shared.FileStorageException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NileCruiseService {

    private final NileCruiseRepository nileCruiseRepository;
    private final NileCruiseMapper nileCruiseMapper;
    private final FileStorageService fileStorageService;
    private final ItineraryRepository itineraryRepository;

    public List<NileCruiseCard> getNileCruiseCards() {
        return nileCruiseRepository.findAll().stream().map(nileCruiseMapper :: mapToNileCruiseCard).collect(Collectors.toList());
    }

    public NileCruise getNileCruiseById(int id) {
        return nileCruiseRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Nile cruise not found"));
    }

    public List<NileCruiseCard> getNileCruiseCardsByName(String label) {
        return nileCruiseRepository.findByLabelContainingIgnoreCase(label).stream()
                .map(nileCruiseMapper::mapToNileCruiseCard).collect(Collectors.toList());
    }

    public List<NileCruiseCard> getNileCruiseCardsByItinerary(String itinerary) {
        return nileCruiseRepository.findNileCruiseByItineraryName(itinerary).stream()
                .map(nileCruiseMapper::mapToNileCruiseCard).collect(Collectors.toList());
    }

    @Transactional
    public NileCruiseResponse createNileCruise(NileCruiseRequest nileCruiseRequest, MultipartFile coverPhoto,
                                               MultipartFile[] nileCruiseImages) {

        if(coverPhoto == null || coverPhoto.isEmpty()) {
            throw new IllegalArgumentException("You must provide a cover photo");
        }

        if(nileCruiseImages == null || nileCruiseImages.length == 0) {
            throw new IllegalArgumentException("You must provide at least one image");
        }

        NileCruise nileCruise = nileCruiseRepository.save(nileCruiseMapper.mapToNileCruise(nileCruiseRequest));

        nileCruise.setImages(new ArrayList<>());

        addCoverPhoto(nileCruise, coverPhoto);

        addNileCruiseImages(nileCruise, nileCruiseImages);

        return nileCruiseMapper.mapToNileCruiseResponse(nileCruise);
    }

    public void assignItineraryToNileCruise(int nileCruiseId, int itineraryId) {

        NileCruise nileCruise = getNileCruiseById(nileCruiseId);

        Itinerary itinerary= itineraryRepository.findById(itineraryId).orElseThrow(()->
                new EntityNotFoundException("Itinerary not found"));

        List<Itinerary> itineraries= nileCruise.getItineraries();
        if(itineraries.contains(itinerary)) {
            throw new IllegalArgumentException("Itinerary already assigned");
        }

        itineraries.add(itinerary);

        nileCruiseRepository.save(nileCruise);
    }

    public NileCruiseResponse updateNileCruiseById(int id, NileCruiseRequest nileCruiseRequest, MultipartFile coverPhoto,
                                                   MultipartFile[] nileCruiseImages) {

        NileCruise nileCruise = getNileCruiseById(id);

        if(nileCruiseRequest != null) {
            validateFields(nileCruise, nileCruiseRequest);
        }

        if(coverPhoto != null && !coverPhoto.isEmpty()) {
            addCoverPhoto(nileCruise, coverPhoto);
        }

        if(nileCruiseImages != null && nileCruiseImages.length!=0){
            addNileCruiseImages(nileCruise, nileCruiseImages);
        }

        return nileCruiseMapper.mapToNileCruiseResponse(nileCruiseRepository.save(nileCruise));
    }

    public void deleteNileCruise(int nileCruiseId) {
        NileCruise nileCruise = getNileCruiseById(nileCruiseId);
        nileCruiseRepository.delete(nileCruise);
    }

    private void addCoverPhoto(NileCruise nileCruise, MultipartFile coverPhoto) {

        String uploadDirectory= "photos/nileCruise/" +nileCruise.getId();
        String imageFileName= System.currentTimeMillis()+ "_" +coverPhoto.getOriginalFilename();

        nileCruise.setCoverPhoto(imageFileName);

        try {
            fileStorageService.saveImage(coverPhoto, uploadDirectory, imageFileName);
        } catch (IOException e) {
            throw new FileStorageException("Could not save cover photo");
        }
    }

    private void addNileCruiseImages(NileCruise nileCruise, MultipartFile[] nileCruiseImages) {

        String uploadDirectory= "photos/nileCruise/" +nileCruise.getId();
        for (MultipartFile image: nileCruiseImages) {

            validate(image);
            String imageFileName= System.currentTimeMillis()+ "_" + image.getOriginalFilename();

            NileCruiseImage nileCruiseImage = new NileCruiseImage();
            nileCruiseImage.setImageUrl(imageFileName);
            nileCruiseImage.setNileCruise(nileCruise);
            nileCruise.addNileCruiseImage(nileCruiseImage);

            try {
                fileStorageService.saveImage(image, uploadDirectory, imageFileName);
            }catch (IOException e) {
                throw new FileStorageException("Could not save image");
            }
        }
    }

    private void validate(MultipartFile image) {
        if(image == null || image.isEmpty()) {
            throw new IllegalArgumentException("You must provide a valid image");
        }
    }

    private void validateFields(NileCruise nileCruise, NileCruiseRequest nileCruiseRequest) {
        if(nileCruiseRequest.getLabel() != null){
            nileCruise.setLabel(nileCruiseRequest.getLabel());
        }
        if(nileCruiseRequest.getLocation() != null){
            nileCruise.setLocation(nileCruiseRequest.getLocation());
        }
        if(nileCruiseRequest.getHighlights() != null){
            nileCruise.setHighlights(nileCruiseRequest.getHighlights());
        }
        if(nileCruiseRequest.getPrice()>0){
            nileCruise.setPrice(nileCruiseRequest.getPrice());
        }
        if(nileCruiseRequest.getCabinNumbers()>0){
            nileCruise.setCabinNumbers(nileCruiseRequest.getCabinNumbers());
        }
        if(nileCruiseRequest.getDecksNumber()>0){
            nileCruise.setDecksNumber(nileCruiseRequest.getDecksNumber());
        }
    }
}
