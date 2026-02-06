package Repository.Favorite;

import Infrastructure.DbConfig;
import Model.Movie;
import Exceptions.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlFavoriteRepository {
    public final DbConfig db;

    public MySqlFavoriteRepository(DbConfig db) {
        this.db = db;
    }

    public List<Movie> findFavoriteByUserID(int userId){
    String sql = """
            select movie.id, movie.title, movie.rating, movie.genre
            from favorite
            inner join user on favorite.userID = user.id
            inner join movie on favorite.movieID = movie.id
            where user.id = ?;
            """;

    List<Movie> result = new ArrayList<>();

    try (Connection conn = db.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, userId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                    result.add(favMapRow(rs));
            }
        }
        return  result;

    }catch(SQLException e){
        throw new DataAccessException("Could not get Favorites for userID" + e);
    }
}

    public void addFavorite(int userID, int movieID){
        String sql = "INSERT IGNORE INTO favorite (userID,movieID)  VALUES (?,?)";

        try(Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userID);
            ps.setInt(2, movieID);
            ps.executeUpdate();

        } catch (SQLException e){
            throw new DataAccessException("Could not add to favorite");
        }

    }

    public void favCount(int userID){
        String sql = "select count(*) from favorite where userID = ?";

        try(Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, userID);
            ps.executeQuery();

        } catch (SQLException e){
            throw new DataAccessException("Could not acces favorite");
        }


    }

    public void removeFavorite(int userID, int movieID){
        String sql = "DELETE FROM favorite where userID = ? and movieID = ?";

        try(Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, userID);
            ps.setInt(2, movieID);
            ps.executeUpdate();

        }   catch (SQLException e){
            throw new DataAccessException("Could not remove from favorite");
        }
    }

    public Movie favMapRow(ResultSet rs) throws SQLException {
        return  new Movie(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getDouble("rating"),
                rs.getString("genre")
        );
    }

}
