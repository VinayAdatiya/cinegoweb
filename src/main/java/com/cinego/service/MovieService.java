package com.cinego.service;

import com.cinego.common.Message;
import com.cinego.common.enums.Role;
import com.cinego.common.exception.ApplicationException;
import com.cinego.model.Movie;
import com.cinego.common.exception.DBException;
import com.cinego.common.utils.DatabaseUtil;
import com.cinego.dao.IMovieDAO;
import com.cinego.dao.impl.MovieDAOImpl;
import com.cinego.dto.movie.MovieRequestDTO;
import com.cinego.dto.movie.MovieDTO;
import com.cinego.mapper.IMovieMapper;
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

    public MovieDTO getMovieById(int movieId) throws ApplicationException {
        Movie movie = movieDAO.getMovieById(movieId);
        MovieDTO movieResponseDTO = movieMapper.toMovieResponseDTO(movie);
        if (movieResponseDTO != null) {
            return movieResponseDTO;
        } else {
            throw new ApplicationException(Message.Error.NO_RECORD_FOUND);
        }
    }

    public List<MovieDTO> getAllMovies() throws DBException {
        List<Movie> movies = movieDAO.getAllMovies();
        return movies.stream()
                .map(movieMapper::toMovieResponseDTO)
                .collect(Collectors.toList());
    }

    public void updateMovie(MovieRequestDTO movieRequestDTO) throws ApplicationException {
        String posterPath = storeMoviePoster(movieRequestDTO.getMoviePosterPath(), movieRequestDTO.getMovieTitle());
        Movie movie = movieMapper.toMovieModel(movieRequestDTO);
        movie.setMoviePosterPath(posterPath);
        movie.setUpdatedBy(Role.ROLE_SUPER_ADMIN.getRoleId());
        movieDAO.updateMovie(movie);
    }

    public void deleteMovie(int movieId) throws ApplicationException {
        if (DatabaseUtil.checkRecordExists("movie", "movie_id", movieId)) {
            movieDAO.deleteMovie(movieId);
        } else {
            throw new ApplicationException(Message.Error.INVALID_ID);
        }
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