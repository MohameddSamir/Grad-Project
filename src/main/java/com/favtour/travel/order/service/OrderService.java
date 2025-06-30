package com.favtour.travel.order.service;

import com.favtour.travel.hotel.room.entity.Room;
import com.favtour.travel.hotel.room.repository.RoomRepository;
import com.favtour.travel.nileCruise.nileCruise.entity.NileCruise;
import com.favtour.travel.nileCruise.nileCruise.repository.NileCruiseRepository;
import com.favtour.travel.order.dto.OrderRequest;
import com.favtour.travel.order.dto.OrderResponse;
import com.favtour.travel.order.entity.Order;
import com.favtour.travel.order.entity.OrderStatus;
import com.favtour.travel.order.entity.OrderType;
import com.favtour.travel.order.exception.CancelOrderException;
import com.favtour.travel.order.mapper.OrderMapper;
import com.favtour.travel.order.repository.OrderRepository;
import com.favtour.travel.shared.EntityNotFoundException;
import com.favtour.travel.trip.entity.Trip;
import com.favtour.travel.trip.repository.TripRepository;
import com.favtour.travel.user.entity.Users;
import com.favtour.travel.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UsersRepository usersRepository;
    private final TripRepository tripRepository;
    private final RoomRepository roomRepository;
    private final NileCruiseRepository nileCruiseRepository;

    public List<OrderResponse> getUserOrders() {
        Users user= getLoggedInUser();
        return user.getOrders().stream().map(orderMapper::mapToOrderResponse).collect(Collectors.toList());
    }

    public OrderResponse getOrderById(int id) {
        return orderMapper.mapToOrderResponse(orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found")));
    }

    public OrderResponse createTripOrder(int tripId, OrderRequest orderRequest) {

        Users user= getLoggedInUser();
        Trip trip= tripRepository.findById(tripId).orElseThrow(()-> new EntityNotFoundException("Trip not found"));
        Order order=orderMapper.mapToOrder(orderRequest);

        order.setUser(user);
        order.setOrderName(trip.getLabel());
        order.setOrderType(OrderType.TRIP);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderCoverPhoto(trip.getCoverImage());
        order.setReferenceEntityId(tripId);
        order.setTotalPrice(trip.getPrice()*orderRequest.getNumberOfAdults()+
                trip.getPrice()*orderRequest.getNumberOfChildren()/2);

        return orderMapper.mapToOrderResponse(orderRepository.save(order));
    }

    public OrderResponse createHotelOrder(int roomId, OrderRequest orderRequest) {

        Users user= getLoggedInUser();
        Room room= roomRepository.findById(roomId).orElseThrow(()-> new EntityNotFoundException("Room not found"));
        Order order=orderMapper.mapToOrder(orderRequest);

        order.setUser(user);
        order.setOrderName(room.getHotel().getName()+ " " +room.getName());
        order.setOrderType(OrderType.HOTEL);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderCoverPhoto(room.getHotel().getCoverPhoto());
        order.setReferenceEntityId(roomId);
        order.setTotalPrice((int) (room.getPrice()* orderRequest.getNumberOfAdults()+
                        room.getPrice()* order.getNumberOfChildren()/2));

        return orderMapper.mapToOrderResponse(orderRepository.save(order));
    }

    public OrderResponse createNileCruiseOrder(int cruiseId, OrderRequest orderRequest) {

        Users user= getLoggedInUser();
        NileCruise nileCruise= nileCruiseRepository.findById(cruiseId).orElseThrow(()-> new EntityNotFoundException("NileCruise not found"));
        Order order=orderMapper.mapToOrder(orderRequest);

        order.setUser(user);
        order.setOrderName(nileCruise.getLabel());
        order.setOrderType(OrderType.CRUISE);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderCoverPhoto(nileCruise.getCoverPhoto());
        order.setReferenceEntityId(cruiseId);
        order.setTotalPrice(nileCruise.getPrice()*orderRequest.getNumberOfAdults() +
                nileCruise.getPrice()*orderRequest.getNumberOfChildren()/2);

        return orderMapper.mapToOrderResponse(orderRepository.save(order));
    }

    public OrderResponse cancelOrder(int orderId) {

        Order order= orderRepository.findById(orderId).orElseThrow(()-> new EntityNotFoundException("Order not found"));

        if(order.getCreatedAt().plusHours(24).isBefore(LocalDateTime.now())) {
            throw new CancelOrderException(
                    "You can't cancel this booking now. Cancellations are only allowed within 24 hours of booking.");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderMapper.mapToOrderResponse(orderRepository.save(order));
    }

    private Users getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        return usersRepository.findByEmail(username).orElseThrow(()->new EntityNotFoundException("User not found"));
    }
}
