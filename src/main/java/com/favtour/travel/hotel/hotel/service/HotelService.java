package com.favtour.travel.hotel.hotel.service;

import com.favtour.travel.core.util.FileStorageService;
import com.favtour.travel.destination.entity.Destination;
import com.favtour.travel.destination.repository.DestinationRepository;
import com.favtour.travel.hotel.hotel.dto.HotelCard;
import com.favtour.travel.hotel.hotel.dto.HotelRequest;
import com.favtour.travel.hotel.hotel.dto.HotelResponse;
import com.favtour.travel.hotel.hotel.entity.Hotel;
import com.favtour.travel.hotel.hotel.mapper.HotelMapper;
import com.favtour.travel.hotel.hotel.repository.HotelRepository;
import com.favtour.travel.shared.EntityNotFoundException;
import com.favtour.travel.shared.FileStorageException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final DestinationRepository destinationRepository;
    private final FileStorageService fileStorageService;

    public List<HotelCard> getAllHotelCards() {
        return hotelRepository.findAll().stream().map(hotelMapper::mapToHotelCard).collect(Collectors.toList());
    }

    public List<HotelCard> findHotels(String hotelName, String destinationName, LocalDate from, LocalDate to) {
        boolean hasHotelName = hotelName != null && !hotelName.isEmpty();
        boolean hasDestinationName = destinationName != null && !destinationName.isEmpty();
        boolean hasFromTo = from != null && to != null && from.isBefore(to);

        List<HotelCard> hotelCards;
        if(hasHotelName && hasDestinationName && hasFromTo) {
            hotelCards= hotelRepository.findByNameAndDestinationNameAndAvailableFromTo(hotelName, destinationName, from, to).stream().map(hotelMapper::mapToHotelCard).toList();
        } else if (hasHotelName && hasDestinationName) {
            hotelCards= hotelRepository.findByNameAndDestination(hotelName, destinationName).stream().map(hotelMapper::mapToHotelCard).toList();
        } else if (hasHotelName && hasFromTo) {
            hotelCards= hotelRepository.findByNameAndAvailableFromTo(hotelName, from, to).stream().map(hotelMapper::mapToHotelCard).toList();
        } else if (hasDestinationName && hasFromTo) {
            hotelCards= hotelRepository.findByDestinationAndAvailableFromTo(destinationName, from, to).stream().map(hotelMapper::mapToHotelCard).toList();
        }else if (hasHotelName){
            hotelCards= hotelRepository.findByNameContainingIgnoreCase(hotelName).stream().map(hotelMapper::mapToHotelCard).toList();
        }else if (hasDestinationName){
            hotelCards= hotelRepository.findByDestinationName(destinationName).stream().map(hotelMapper::mapToHotelCard).toList();
        }else if (hasFromTo){
            hotelCards= hotelRepository.findByAvailableFromTo(from, to).stream().map(hotelMapper::mapToHotelCard).toList();
        }else {
            hotelCards= hotelRepository.findAll().stream().map(hotelMapper::mapToHotelCard).toList();
        }
        return hotelCards;
    }

    public HotelResponse getHotelById(int id) {
        return hotelMapper.mapToHotelResponse(hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found")));
    }

    @Transactional
    public HotelResponse createHotel(HotelRequest hotelRequest, MultipartFile coverPhoto) {

        if(coverPhoto == null || coverPhoto.isEmpty()) {
            throw new IllegalArgumentException("You must provide a cover photo");
        }

        Destination destination = destinationRepository.findById(hotelRequest.getDestinationId()).orElseThrow(()->
                new EntityNotFoundException("Destination not found"));

        Hotel savedHotel= hotelMapper.mapToHotel(hotelRequest);

        savedHotel.setDestination(destination);

        hotelRepository.save(savedHotel);

        addCoverPhoto(savedHotel, coverPhoto);

        return hotelMapper.mapToHotelResponse(savedHotel);
    }

    public void deleteHotelById(int id) {
        if(!hotelRepository.existsById(id)) {
            throw new EntityNotFoundException("Hotel not found");
        }
        hotelRepository.deleteById(id);
        try {
            fileStorageService.deleteImagesWithDirectory("photos/hotel/"+id);
        } catch (IOException e) {
            throw new FileStorageException("Failed to delete photos");
        }
    }

    public HotelResponse updateHotelById(int id, HotelRequest hotelRequest, MultipartFile coverPhoto) {
        Hotel updatedHotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        validateFields(hotelRequest, updatedHotel);

        if(coverPhoto != null && !coverPhoto.isEmpty()) {
            addCoverPhoto(updatedHotel, coverPhoto);
        }

        return hotelMapper.mapToHotelResponse(hotelRepository.save(updatedHotel));
    }

    private void addCoverPhoto(Hotel savedHotel, MultipartFile coverPhoto) {
        String uploadDirectory= "photos/hotel/" + savedHotel.getId();
        String coverPhotoName= System.currentTimeMillis() + "_" +coverPhoto.getOriginalFilename();

        savedHotel.setCoverPhoto(coverPhotoName);

        try {
            fileStorageService.saveImage(coverPhoto, uploadDirectory, coverPhotoName);
        } catch (IOException e) {
            throw new FileStorageException("Could not save cover photo");
        }
    }

    private void validateFields(HotelRequest hotelRequest, Hotel hotel) {
        if(hotelRequest.getName() != null){
            hotel.setName(hotelRequest.getName());
        }
        if(hotelRequest.getAddress() != null){
            hotel.setAddress(hotelRequest.getAddress());
        }
        if(hotelRequest.getDescription() != null){
            hotel.setDescription(hotelRequest.getDescription());
        }
        if(hotelRequest.getAveragePrice()>0){
            hotel.setAveragePrice(hotelRequest.getAveragePrice());
        }
    }
}
