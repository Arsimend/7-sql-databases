
package databases.part02;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for the Artist table in the Chinook database.
 */
public class ArtistDAO {

    /**
     * The connection string used to connect to the database. You MUST use this
     * string when connecting to the database using JDBC. In the unit tests, this
     * field will be set to a different value.
     */
    private final String connectionString;

    /**
     * Creates a new ArtistDAO that uses the specified connection string to connect
     * to the database. For example: "jdbc:sqlite:data/Chinook_Sqlite.sqlite"
     *
     * @param connectionString, see https://www.baeldung.com/java-jdbc-url-format
     */
    public ArtistDAO(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     * Returns a list of all artists in the database. The list is ordered by artist
     * name. If there are no artists in the database, an empty list is returned.
     *
     * @return a list of all artists in the database.
     */
    public List<Artist> getArtists() {
        List<Artist> artists = new ArrayList<>();

        /*
         * Connect to the database and retrieve all artists.
         * The artists are ordered by name.
         */

        String sql = "SELECT ArtistId, Name FROM Artist ORDER BY Name";

        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                long id = resultSet.getLong("ArtistId");
                String name = resultSet.getString("Name");

                Artist artist = new Artist(id, name);
                artists.add(artist);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return artists;
    }

    /**
     * Returns the artist with the specified id, or null if no artist exists with
     * that id.
     *
     * @param id the id of the artist to retrieve.
     * @return the artist with the specified id, or null if no artist exists with
     *         that id.
     */
    public Artist getArtistById(long id) {

        /*
         * Use PreparedStatement to find an artist by id.
         * Return null if the artist does not exist.
         */

        String sql = "SELECT ArtistId, Name FROM Artist WHERE ArtistId = ?";

        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    long artistId = resultSet.getLong("ArtistId");
                    String name = resultSet.getString("Name");

                    return new Artist(artistId, name);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
