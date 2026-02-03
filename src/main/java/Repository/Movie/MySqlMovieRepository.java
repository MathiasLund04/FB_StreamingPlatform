package Repository.Movie;

import Infrastructure.DbConfig;
import Model.Movie;
import Repository.DataAccessException;
import Service.StreamingService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlMovieRepository {

    private final DbConfig db;

    public MySqlMovieRepository(DbConfig db) {
        this.db = db;
    }

    public List<Movie> findAll() throws DataAccessException {
        String sql = "select * from movie";

        List<Movie> movies = new ArrayList<>();

        try(Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                movies.add(mapRow(rs));
            }
            return movies;

        }catch(Exception e){
        throw new  DataAccessException("Error in findAll()" , e);
        }


    }

    public void movieCount(){
    String sql = "select count(*) from Movie";

    try(Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int count = rs.getInt(1);
            System.out.println("Movie count : " + count);
        }

    } catch (Exception e ){
        throw new DataAccessException("Error counting movies", e);
    }

    }

    public Movie mapRow(ResultSet rs) throws SQLException{
        return  new Movie(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getDouble("rating"),
            rs.getString("genre")
        );
    }

}
