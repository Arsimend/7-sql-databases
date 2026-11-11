
package databases.part01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcDemoMain {

    private static final String JDBC_CONNECTION_STRING = "jdbc:sqlite:data/Chinook_Sqlite.sqlite";

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(JDBC_CONNECTION_STRING)) {

            // Get all artists sorted by name
            String sql = "SELECT ArtistId, Name FROM Artist ORDER BY Name ASC";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    int id = resultSet.getInt("ArtistId");
                    String name = resultSet.getString("Name");

                    System.out.println(id + " " + name);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
