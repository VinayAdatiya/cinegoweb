package com.cinego.mapper;

import com.cinego.dto.booking.BookingRequestDTO;
import com.cinego.dto.booking.BookingResponseDTO;
import com.cinego.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IBookingMapper {

    @Mapping(source = "showId", target = "show.showId")
    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "paymentMethodId", target = "paymentMethod.paymentMethodId")
    Booking toBooking(BookingRequestDTO bookingRequestDTO);

    @Mapping(source = "show.showId", target = "showId")
    @Mapping(source = "show.showDate", target = "showDate")
    @Mapping(source = "show.showTime", target = "showTime")
    @Mapping(source = "show.movie.movieTitle", target = "movieTitle")
    @Mapping(source = "show.movie.movieDuration", target = "movieDuration")
    @Mapping(source = "show.screen.screenId", target = "screenId")
    @Mapping(source = "show.screen.screenTitle", target = "screenTitle")
    @Mapping(source = "show.screen.screenType.screenType", target = "screenType")
    @Mapping(source = "show.screen.theater.theaterId", target = "theaterId")
    @Mapping(source = "show.screen.theater.theaterName", target = "theaterName")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.userName", target = "userName")
    @Mapping(source = "bookedShowSeats", target = "bookedShowSeats")
    BookingResponseDTO toBookingResponseDTO(Booking booking);
}