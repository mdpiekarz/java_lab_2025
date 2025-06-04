package music;

import database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public record Song(String artist, String name, int length) {
    public static class Persistence {
        public static Optional<Song> read(int id) throws SQLException {
            String sql = "SELECT artist, title, length FROM song WHERE id = ?";
            PreparedStatement statement = DatabaseConnection.getConnection("songs").prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return Optional.of(new Song(
                        resultSet.getString("artist"),
                        resultSet.getString("title"),
                        resultSet.getInt("length")
                ));
            }
            return Optional.empty();
        }
    }
}
