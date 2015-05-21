import java.io.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class LibMysqlConnector {

    public Connection getConnection() {
        String usr = "ywpu";
        String pwd = "";

        try {
            File file = new File("passwd");
            Scanner scanner = new Scanner(file);
            pwd = scanner.nextLine();
        }
        catch (FileNotFoundException exception) {
            String message = "Load password file failed.";
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }

        String jdbcDriver = "com.mysql.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://ckeisc.nctucs.net:3306/personal_calendar";
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
        }

        try {
            connection = DriverManager.getConnection(jdbcUrl);
        }
        catch (SQLException exception) {
            String message = "SQLException: " + exception.getMessage();
            message += "\nSQLState: " + exception.getSQLState();
            message += "\nVendorError: " + exception.getErrorCode();
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return connection;
    }

}
