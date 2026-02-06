package Service;

import Exceptions.ValidationException;
import Model.Movie;
import Model.User;
import Exceptions.DataAccessException;
import Repository.Favorite.FavoriteRepository;
import Repository.Favorite.MySqlFavoriteRepository;
import Repository.Movie.MySqlMovieRepository;
import Repository.User.MySqlUserRepository;

import java.util.List;
import java.util.Optional;

public class StreamingService {

    private final MySqlMovieRepository mRepo;
    private final MySqlUserRepository uRepo;
    private final FavoriteRepository fRepo;

    public StreamingService(MySqlMovieRepository mRepo, MySqlUserRepository uRepo, FavoriteRepository fRepo) {
        this.mRepo = mRepo;
        this.uRepo = uRepo;
        this.fRepo = fRepo;
    }

    public List<Movie> getAllMovies() throws DataAccessException {
        return mRepo.findAll();
    }
    public List<User> getAllUsers() throws DataAccessException {
        return uRepo.findAllUsers();
    }

    public List<Movie> findFavoriteByEmail(String email){
        String e = validateEmail(email);

        User user = uRepo.findByEmail(e).orElseThrow(() ->
                new DataAccessException("User with email " + e + " not found"));

        return fRepo.findFavoriteByUserID(user.getId());
    }

    public Optional<User> findByEmail(String email) {
        String e = validateEmail(email);
        return uRepo.findByEmail(e);
    }

    public void addFavoriteByEmail(String email, int movieID) throws DataAccessException {
        String e = validateEmail(email);

        User user = uRepo.findByEmail(e).orElseThrow(() -> new ValidationException("User with email " + e + " not found"));

        fRepo.addFavorite(user.getId(), movieID);
    }

    public void removeFavoriteByEmail(String email, int movieID) throws DataAccessException {
        String e = validateEmail(email);

        User user = uRepo.findByEmail(e).orElseThrow(() -> new ValidationException("User with email " + e + " not found"));

        fRepo.removeFavorite(user.getId(), movieID);
    }

    public void movieCount(){
        mRepo.movieCount();
    }

    public int favCount(int userID){
        fRepo.favCount(userID);
        return userID;
    }

    private String validateEmail(String email) {
        if (email == null) throw new ValidationException("Ugyldig email");
        String e = email.trim();
        if (e.isBlank() || !e.contains("@")) throw new ValidationException("Ugyldig email");
        return e;
    }


}
