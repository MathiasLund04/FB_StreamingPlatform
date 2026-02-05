package Repository.User;

import Infrastructure.DbConfig;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MySqlUserRepository {
    private DbConfig db;
    public MySqlUserRepository(DbConfig db) {
        this.db = db;
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM User WHERE email = ?";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

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
                rs.getString("subscription_type")
        );
    }



}
