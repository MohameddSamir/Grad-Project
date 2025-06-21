package com.favtour.travel.nileCruise.itinerary.service;

import com.favtour.travel.nileCruise.itinerary.dto.ItineraryDto;
import com.favtour.travel.nileCruise.itinerary.entity.Itinerary;
import com.favtour.travel.nileCruise.itinerary.entity.ItineraryDuration;
import com.favtour.travel.nileCruise.itinerary.mapper.ItineraryMapper;
import com.favtour.travel.nileCruise.itinerary.repository.ItineraryRepository;
import com.favtour.travel.shared.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final ItineraryMapper itineraryMapper;

    @Transactional
    public Itinerary createItinerary(ItineraryDto itineraryDto) {

        Itinerary savedItinerary= itineraryMapper.mapToItinerary(itineraryDto);

        savedItinerary.setDurations(new ArrayList<>());

        itineraryRepository.save(savedItinerary);

        addDurations(savedItinerary, itineraryDto.getDurations());

        return savedItinerary;
    }

    public void deleteItineraryById(int itineraryId) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId).orElseThrow(()->
                new EntityNotFoundException("Itinerary not found"));
        itineraryRepository.delete(itinerary);
    }

    private void addDurations(Itinerary savedItinerary, List<ItineraryDuration> durations) {
        for (ItineraryDuration duration : durations) {
            duration.setItinerary(savedItinerary);
        }
        savedItinerary.setDurations(durations);
    }
}
