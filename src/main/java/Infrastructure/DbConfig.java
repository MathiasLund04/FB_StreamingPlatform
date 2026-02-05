package Infrastructure;

import Exceptions.DataAccessException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConfig {

 private final String url;
 private final String user;
 private final String password;


    public DbConfig() {
        Properties props = new Properties();

        try (InputStream is = getClass().getResourceAsStream("/db.properties")) {
            if (is == null) {
                throw new DataAccessException("Could not find db.properties in resources");
            }
            props.load(is);
        } catch (Exception e) {
            throw new DataAccessException("Could not read db.properties", e);
        }
        this.url = props.getProperty("db.url");
        this.user = props.getProperty("db.user");
        this.password = props.getProperty("db.password");
    }


    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        }  catch (SQLException e) {
            throw new DataAccessException("Connection failed.", e);
        }

    }

    public void testConnection() {
        try (Connection c = getConnection()) {
            DatabaseMetaData md = c.getMetaData();
            System.out.println("✅ Connection OK: " + md.getURL());
            System.out.println("    Driver: " + md.getDriverName() + " - " + md.getDriverVersion());

        } catch (Exception e) {
            System.out.println("❌ Connection ERROR: " + e.getMessage());
            System.out.println("Tip: Check URL/USER/PASS and MySQL is running.");
        }
    }

}
