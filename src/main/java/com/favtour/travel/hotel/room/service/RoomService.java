package com.favtour.travel.hotel.room.service;

import com.favtour.travel.core.util.FileStorageService;
import com.favtour.travel.hotel.hotel.entity.Hotel;
import com.favtour.travel.hotel.hotel.repository.HotelRepository;
import com.favtour.travel.hotel.room.dto.RoomRequest;
import com.favtour.travel.hotel.room.dto.RoomResponse;
import com.favtour.travel.hotel.room.entity.Room;
import com.favtour.travel.hotel.room.entity.RoomImage;
import com.favtour.travel.hotel.room.mapper.RoomMapper;
import com.favtour.travel.hotel.room.repository.RoomRepository;
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
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final FileStorageService fileStorageService;

    private final HotelRepository hotelRepository;

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream().map(roomMapper::mapToRoomResponse).collect(Collectors.toList());
    }

    public RoomResponse getRoomById(int id) {
        return roomMapper.mapToRoomResponse(roomRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Room not found")));
    }

    @Transactional
    public RoomResponse createRoom(RoomRequest roomRequest, MultipartFile[] images) {

        if(images == null || images.length == 0) {
            throw new IllegalArgumentException("You must provide at least one image");
        }

        Hotel hotel= hotelRepository.findById(roomRequest.getHotelId()).orElseThrow(()-> new EntityNotFoundException("Hotel not found"));

        Room savedRoom= roomMapper.mapToRoom(roomRequest);

        savedRoom.setHotel(hotel);

        savedRoom.setImages(new ArrayList<>());

        roomRepository.save(savedRoom);

        addImages(savedRoom, images);

        return roomMapper.mapToRoomResponse(savedRoom);
    }

    public RoomResponse updateRoom(int roomId, RoomRequest roomRequest, MultipartFile[] images) {
        Room updatedRoom= roomRepository.findById(roomId).orElseThrow(()->new EntityNotFoundException("Room not found"));

        validateFields(roomRequest, updatedRoom);

        if(images != null && images.length > 0) {
            addImages(updatedRoom, images);
        }

        return roomMapper.mapToRoomResponse(roomRepository.save(updatedRoom));
    }

    public void deleteRoomById(int id) {
        if(!roomRepository.existsById(id)) {
            throw new EntityNotFoundException("Room not found");
        }
        roomRepository.deleteById(id);
    }

    private void addImages(Room room, MultipartFile[] images) {
        String uploadDirectory= "photos/hotel/"+room.getHotel().getId()+"/room" + room.getId();
        for (MultipartFile image : images) {
            validate(image);
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

            RoomImage roomImage = new RoomImage();
            roomImage.setImageUrl(fileName);
            roomImage.setRoom(room);
            room.addRoomImage(roomImage);

            try {
                fileStorageService.saveImage(image, uploadDirectory, fileName);
            } catch (IOException e) {
                throw new FileStorageException("Could not save image ");
            }
        }
    }

    private void validate(MultipartFile image) {
        if(image.isEmpty()){
            throw new IllegalArgumentException("Image is empty");
        }
    }

    private void validateFields(RoomRequest roomRequest, Room savedRoom) {
        if(roomRequest.getName() != null){
            savedRoom.setName(roomRequest.getName());
        }
        if(roomRequest.getType() != null){
            savedRoom.setType(roomRequest.getType());
        }
        if(roomRequest.getPrice()>0){
            savedRoom.setPrice(roomRequest.getPrice());
        }
        if(roomRequest.getAvailableFrom()!=null){
            savedRoom.setAvailableFrom(roomRequest.getAvailableFrom());
        }
        if(roomRequest.getAvailableTo()!=null){
            savedRoom.setAvailableTo(roomRequest.getAvailableTo());
        }
    }
}
