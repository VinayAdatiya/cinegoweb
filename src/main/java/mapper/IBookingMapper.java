package mapper;

import dto.booking.BookingRequestDTO;
import dto.booking.BookingResponseDTO;
import model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IBookingMapper {

    @Mapping(source = "showId", target = "show.showId")
    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "paymentMethodId", target = "paymentMethod.paymentMethodId")
    Booking toBooking(BookingRequestDTO bookingRequestDTO);


    BookingResponseDTO toBookingResponseDTO(Booking booking);
}