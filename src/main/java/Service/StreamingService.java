package Service;

import Model.Movie;
import Repository.DataAccessException;
import Repository.Movie.MySqlMovieRepository;

import java.util.List;
import java.util.Optional;

public class StreamingService {

    private final MySqlMovieRepository mRepo;

    public StreamingService(MySqlMovieRepository mRepo) {
        this.mRepo = mRepo;
    }

    public List<Movie> getAllMovies() throws DataAccessException {
        return mRepo.findAll();
    }

    public Optional<Movie> findByTitle(String title){
        return mRepo.findByTitle(title);
    }

    public void movieCount(){
        mRepo.movieCount();
    }



}
