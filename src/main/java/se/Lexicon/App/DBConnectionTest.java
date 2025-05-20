package se.Lexicon.App;

import se.Lexicon.Exception.Database.DatabaseConnectionException;
import se.Lexicon.JDBC.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionTest {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println(" Connection successful: " + (conn != null));
        } catch (DatabaseConnectionException e) {
            System.out.println(" Custom DB exception: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQLException while closing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
