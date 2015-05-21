import javax.swing.*;
import java.sql.*;
import java.text.*;
import java.util.*;

public class Model {

    private Connection connection;
    private String table;

    public void init(String username) {
        LibMysqlConnector libMysqlConnector = new LibMysqlConnector();
        connection = libMysqlConnector.getConnection();
        this.table = "user_" + username;
    }

    public String fetchDatabase(Calendar calendar) {
        Statement statement = null;
        ResultSet resultSet = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String query = "SELECT * FROM " + table;
        query += " WHERE date = \'" + simpleDateFormat.format(calendar.getTime()) + "\'";
        String output = "";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                output += resultSet.getString("event") + "<br>";
            }
        }
        catch (SQLException exception) {
            String message = "SQLException: " + exception.getMessage();
            message += "\nSQLState: " + exception.getSQLState();
            message += "\nVendorError: " + exception.getErrorCode();
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (SQLException exception) {
                    String message = "Unbelievable error.";
                    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (SQLException exception) {
                    String message = "Unbelievable error.";
                    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return output;
    }

}
