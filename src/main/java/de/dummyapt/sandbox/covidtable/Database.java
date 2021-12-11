package de.dummyapt.sandbox.covidtable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private static final String URL = "jdbc:mariadb://[::1]";
    private static final String DB = "corona";
    private static final String PORT = "3306";
    private static final String USER = "root";
    private static final String PASS = "";
    private static Connection connection;

    private Database() {
        throw new IllegalStateException("Utility class");
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null)
            connection = DriverManager.getConnection(URL + ':' + PORT + '/' + DB, USER, PASS);
        return connection;
    }
}
