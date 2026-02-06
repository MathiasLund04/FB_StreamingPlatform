package Repository.User;

import Exceptions.DataAccessException;
import Infrastructure.DbConfig;
import Model.Movie;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlUserRepository implements UserRepository {
    private DbConfig db;
    public MySqlUserRepository(DbConfig db) {
        this.db = db;
    }

    public List<User> findAllUsers() throws DataAccessException {
        String sql = "select id,name,email,subscriptionType from user";

        List<User> users = new ArrayList<>();

        try(Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                users.add(mapUser(rs));
            }
            return users;

        }catch(Exception e){
            throw new  DataAccessException("Error in findAllUsers()" , e);
        }


    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT id, email, name, subscriptionType FROM User WHERE LOWER(email) LIKE LOWER(?)";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = mapUser(rs);
                    return Optional.of(user);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved findByEmail()", e);
        }

        return Optional.empty();
    }

    private User mapUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("name"),
                rs.getString("subscriptionType")
        );
    }



}
