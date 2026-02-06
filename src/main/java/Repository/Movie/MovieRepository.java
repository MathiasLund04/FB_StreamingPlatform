package Repository.Movie;

import Model.Movie;

import java.util.List;

public interface MovieRepository {
    List<Movie> findAll();
}
