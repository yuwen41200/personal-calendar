import javax.swing.*;
import java.sql.*;
import java.text.*;
import java.util.*;

public class Model {

    private Connection connection;
    private String table;

    public void init(String username) {
        LibMysqlConnector libMysqlConnector = new LibMysqlConnector();
        this.connection = libMysqlConnector.getConnection();
        this.table = "user_" + username;
    }

    public ArrayList<String> fetchDatabase(Calendar calendar) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + table + " WHERE date = ?";
        ArrayList<String> results = new ArrayList<>();

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, calendarToStr(calendar));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(resultSet.getString("event"));
            }
        }

        catch (SQLException exception) {
            String message = "SQL Exception: " + exception.getMessage();
            message += "\nSQL State: " + exception.getSQLState();
            message += "\nVendor Error: " + exception.getErrorCode();
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (SQLException exception) {
                    String message = "Close result set failed.";
                    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (SQLException exception) {
                    String message = "Close statement failed.";
                    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
        }

        return results;
    }

    public void insertDatabase(String date, String event) {
        PreparedStatement statement = null;
        String query = "INSERT INTO " + table + " (event, date) VALUES (?, ?)";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, event);
            statement.setString(2, date);
            statement.executeUpdate();
        }

        catch (SQLException exception) {
            String message = "SQL Exception: " + exception.getMessage();
            message += "\nSQL State: " + exception.getSQLState();
            message += "\nVendor Error: " + exception.getErrorCode();
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        finally {
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (SQLException exception) {
                    String message = "Close statement failed.";
                    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
        }
    }

    public void updateDatabase(String id, String event) {

    }

    public void deleteDatabase(String id) {

    }

    public String sequenceDatabase(String date, String count) {
        String id;

        return "-1";
    }

    public void fetchGoogleCalendar() {

    }

    public String calendarToStr(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(calendar.getTime());
    }

    public Calendar strToCalendar(String date) {
        String[] strDate = date.split("-");
        int[] intDate = new int[3];
        for (int i = 0; i < 3; i++)
            intDate[i] = Integer.parseInt(strDate[i]);
        return new Calendar.Builder().setDate(intDate[0], intDate[1]-1, intDate[2]).build();
    }

}
