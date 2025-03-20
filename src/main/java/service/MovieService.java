package service;

import common.Message;
import common.Role;
import common.exception.ApplicationException;
import common.exception.DBException;
import dao.IMovieDAO;
import dao.impl.MovieDAOImpl;
import dto.movie.MovieRequestDTO;
import dto.movie.MovieResponseDTO;
import mapper.IMovieMapper;
import model.Movie;
import org.mapstruct.factory.Mappers;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService {

    private final IMovieDAO movieDAO = new MovieDAOImpl();
    private final IMovieMapper movieMapper = Mappers.getMapper(IMovieMapper.class);
    private static final String STORAGE_PATH = "C:\\apache-tomcat-10.1.35\\webapps\\cinego\\MoviePosters";

    public void addMovie(MovieRequestDTO movieRequestDTO) throws ApplicationException {
        String posterPath = storeMoviePoster(movieRequestDTO.getMoviePosterPath(), movieRequestDTO.getMovieTitle());
        Movie movie = movieMapper.toMovieModel(movieRequestDTO);
        movie.setMoviePosterPath(posterPath);
        movie.setCreatedBy(Role.ROLE_SUPER_ADMIN.getRoleId());
        movieDAO.addMovie(movie);
    }

    public MovieResponseDTO getMovieById(int movieId) throws DBException {
        Movie movie = movieDAO.getMovieById(movieId);
        return movieMapper.toMovieResponseDTO(movie);
    }

    public List<MovieResponseDTO> getAllMovies() throws DBException {
        List<Movie> movies = movieDAO.getAllMovies();
        return movies.stream()
                .map(movieMapper::toMovieResponseDTO)
                .collect(Collectors.toList());
    }

    public void updateMovie(MovieRequestDTO movieRequestDTO) throws DBException, ApplicationException {
        String posterPath = storeMoviePoster(movieRequestDTO.getMoviePosterPath(), movieRequestDTO.getMovieTitle());
        Movie movie = movieMapper.toMovieModel(movieRequestDTO);
        movie.setMoviePosterPath(posterPath);
        movieDAO.updateMovie(movie);
    }

    public void deleteMovie(int movieId) throws DBException {
        movieDAO.deleteMovie(movieId);
    }

    private String storeMoviePoster(String posterPath, String movieTitle) throws ApplicationException {
        try {
            File sourceFile = new File(posterPath);
            if (!sourceFile.exists()) {
                throw new ApplicationException(Message.Error.POSTER_PATH_REQUIRED + posterPath);
            }
            File file = new File(STORAGE_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            String extension = getFileExtension(sourceFile);
            String fileName = "MOV_" + movieTitle.replaceAll("\\s+", "_") + extension;
            Path path = Paths.get(STORAGE_PATH, fileName);
            Files.copy(sourceFile.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
            return path.toString();
        } catch (IOException e) {
            throw new DBException(Message.Error.STORE_FAILED + e.getMessage());
        }
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }
}