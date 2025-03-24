package mapper;

import dto.theater.TheaterRequestDTO;
import dto.theater.TheaterResponseDTO;
import model.Theater;
import org.mapstruct.Mapper;

@Mapper
public interface ITheaterMapper {
    Theater toTheaterModel(TheaterRequestDTO theaterRequestDTO);

    TheaterResponseDTO toTheaterResponseDTO(Theater theater);
}
