package Repository.Favorite;

import Model.Movie;

import java.util.List;

public interface FavoriteRepository {
    List<Movie> findFavoriteByUserID(int userID);
    void addFavorite(int userID, int movieID);
    void removeFavorite(int userID, int movieID);
    void favCount(int userID);
}
