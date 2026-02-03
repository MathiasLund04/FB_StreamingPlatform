package Repository.Movie;

import Infrastructure.DbConfig;
import Repository.DataAccessException;

import java.sql.*;

public class MySqlMovieRepository {

    private final DbConfig db;

    public MySqlMovieRepository(DbConfig db) {
        this.db = db;
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
}
