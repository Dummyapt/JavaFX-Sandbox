package de.dummyapt.javafx_sandbox.emschertrade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private static final String URL = "jdbc:mariadb://[::1]";
    private static Connection connection;

    private Database() {
        throw new IllegalStateException("Utility class");
    }

    static Connection getConnection(String... credentials) throws SQLException {
        if (connection == null)
            connection = DriverManager.getConnection(URL + ':' + credentials[0] + '/' + credentials[1],
                    credentials[2], credentials[3]);
        return connection;
    }
}
