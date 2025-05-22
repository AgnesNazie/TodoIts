package se.Lexicon.App;

import se.Lexicon.Exception.DataBaseException;
import se.Lexicon.Utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println(" Database connection successful!");
            } else {
                System.out.println(" Failed to establish connection.");
            }
        } catch (DataBaseException e) {
            System.out.println(" Error during database connection:");
            e.printStackTrace();
        }
    }
}
