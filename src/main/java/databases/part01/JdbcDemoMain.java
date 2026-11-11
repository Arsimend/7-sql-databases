
package databases.part01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDemoMain {

    private static final String JDBC_CONNECTION_STRING = "jdbc:sqlite:data/Chinook_Sqlite.sqlite";

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(JDBC_CONNECTION_STRING)) {

            // Create the kaveri table
            String createTable = "CREATE TABLE IF NOT EXISTS kaveri (id INTEGER PRIMARY KEY, nimi TEXT)";

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTable);
            }

            // Insert friends into the table
            String insertSql = "INSERT OR IGNORE INTO kaveri (id, nimi) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {

                preparedStatement.setInt(1, 1);
                preparedStatement.setString(2, "juhanna");
                preparedStatement.executeUpdate();

              
            }

            // Get all friends from the database
            String selectSql = "SELECT id, nimi FROM kaveri ORDER BY id ASC";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    int id = resultSet.getInt("id");
                    String nimi = resultSet.getString("nimi");

                    System.out.println(id + " " + nimi);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
