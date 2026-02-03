package Service;

import Model.Movie;
import Repository.DataAccessException;
import Repository.Movie.MySqlMovieRepository;

import java.util.List;

public class StreamingService {

    private final MySqlMovieRepository mRepo;

    public StreamingService(MySqlMovieRepository mRepo) {
        this.mRepo = mRepo;
    }

    public List<Movie> getAllMovies() throws DataAccessException {
        return mRepo.findAll();
    }

    public void movieCount(){
        mRepo.movieCount();
    }



}
