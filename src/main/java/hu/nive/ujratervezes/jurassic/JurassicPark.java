package hu.nive.ujratervezes.jurassic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JurassicPark {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public JurassicPark(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public List<String> checkOverpopulation() {
        String query = "SELECT breed, expected, actual " +
                "FROM dinosaur " +
                "WHERE actual > expected " +
                "ORDER BY breed ASC";
        String column = "breed";
        return getResult(query, column);
    }

    private List<String> getResult(String query, String column) {
        List<String> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result.add(rs.getString(column));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
