import java.io.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

/**
 * The connector library for MySQL servers.
 */
public final class LibMysqlConnector {

    /**
     * Build a MySQL connection.
     * @return a Connection object
     */
    public Connection getConnection() {
        String usr = "ywpu";
        String pwd = "";

        try {
            InputStream file = LibMysqlConnector.class.getResourceAsStream("/passwd");
            Scanner scanner = new Scanner(file);
            pwd = scanner.nextLine();
            scanner.close();
        }
        catch (Exception exception) {
            String message = "Load password file failed.";
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        String jdbcDriver = "com.mysql.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://localhost:3306/personal_calendar";
        jdbcUrl += "?user=" + usr;
        jdbcUrl += "&password=" + pwd;
        jdbcUrl += "&useUnicode=true&characterEncoding=UTF-8";
        Connection connection = null;

        try {
            Class.forName(jdbcDriver).newInstance();
        }
        catch (Exception exception) {
            String message = "Load MySQL driver failed.";
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        try {
            connection = DriverManager.getConnection(jdbcUrl);
        }
        catch (SQLException exception) {
            String message = "SQL Exception: " + exception.getMessage();
            message += "\nSQL State: " + exception.getSQLState();
            message += "\nVendor Error: " + exception.getErrorCode();
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        return connection;
    }

}
