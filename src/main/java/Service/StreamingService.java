package Service;

import Exceptions.ValidationException;
import Model.Movie;
import Model.User;
import Exceptions.DataAccessException;
import Repository.Favorite.MySqlFavoriteRepository;
import Repository.Movie.MySqlMovieRepository;
import Repository.User.MySqlUserRepository;

import java.util.List;

public class StreamingService {

    private final MySqlMovieRepository mRepo;
    private final MySqlUserRepository uRepo;
    private final MySqlFavoriteRepository fRepo;

    public StreamingService(MySqlMovieRepository mRepo, MySqlUserRepository uRepo, MySqlFavoriteRepository fRepo) {
        this.mRepo = mRepo;
        this.uRepo = uRepo;
        this.fRepo = fRepo;
    }

    public List<Movie> getAllMovies() throws DataAccessException {
        return mRepo.findAll();
    }

    public List<Movie> findFavoriteByEmail(String email){
        String e = validateEmail(email);

        User user = uRepo.findByEmail(e);

        return fRepo.findFavoriteByUserID(user.getId());
    }

    public void movieCount(){
        mRepo.movieCount();
    }


    private String validateEmail(String email) {
        if (email == null) throw new ValidationException("Ugyldig email");
        String e = email.trim();
        if (e.isBlank() || !e.contains("@")) throw new ValidationException("Ugyldig email");
        return e;
    }


}
