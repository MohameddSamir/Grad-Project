package com.favtour.travel.nileCruise.nileCruise.mapper;

import com.favtour.travel.nileCruise.nileCruise.dto.NileCruiseCard;
import com.favtour.travel.nileCruise.nileCruise.dto.NileCruiseRequest;
import com.favtour.travel.nileCruise.nileCruise.dto.NileCruiseResponse;
import com.favtour.travel.nileCruise.nileCruise.entity.NileCruise;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class NileCruiseMapper {

    public NileCruise mapToNileCruise(NileCruiseRequest nileCruiseRequest) {

        return NileCruise.builder()
                .label(nileCruiseRequest.getLabel())
                .location(nileCruiseRequest.getLocation())
                .price(nileCruiseRequest.getPrice())
                .highlights(nileCruiseRequest.getHighlights())
                .cabinNumbers(nileCruiseRequest.getCabinNumbers())
                .decksNumber(nileCruiseRequest.getDecksNumber())
                .build();
    }

    public NileCruiseResponse mapToNileCruiseResponse(NileCruise nileCruise) {

        return NileCruiseResponse.builder()
                .id(nileCruise.getId())
                .label(nileCruise.getLabel())
                .location(nileCruise.getLocation())
                .price(nileCruise.getPrice())
                .highlights(nileCruise.getHighlights())
                .cabinNumbers(nileCruise.getCabinNumbers())
                .decksNumber(nileCruise.getDecksNumber())
                .build();
    }

    public NileCruiseCard mapToNileCruiseCard(NileCruise nileCruise) {

        return NileCruiseCard.builder()
                .id(nileCruise.getId())
                .label(nileCruise.getLabel())
                .location(nileCruise.getLocation())
                .price(nileCruise.getPrice())
                .coverPhoto(nileCruise.getCoverPhoto())
                .build();
    }
}
