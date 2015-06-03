import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class Model {

    private Connection connection;
    private String table;

    public void init(String username) {
        LibMysqlConnector libMysqlConnector = new LibMysqlConnector();
        this.connection = libMysqlConnector.getConnection();
        this.table = "user_" + username;
        createDatabase();
    }

    public void createDatabase() {
        if (!Pattern.matches("^[a-zA-Z0-9_]+$", table)) {
            String message = "Invalid username.";
            message += "\nOnly alphanumeric characters and underscores are allowed.";
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        PreparedStatement statement = null;
        String query = "CREATE TABLE IF NOT EXISTS " + table + " (";
        query += "id INT NOT NULL AUTO_INCREMENT,";
        query += "event VARCHAR(255) NOT NULL,";
        query += "date DATE NOT NULL,";
        query += "PRIMARY KEY (id)";
        query += ") ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci";

        try {
            statement = connection.prepareStatement(query);
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
        PreparedStatement statement = null;
        String query = "UPDATE " + table + " SET event = ? WHERE id = ? LIMIT 1";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, event);
            statement.setString(2, id);
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

    public void deleteDatabase(String id) {
        PreparedStatement statement = null;
        String query = "DELETE FROM " + table + " WHERE id = ? LIMIT 1";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, id);
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

    public String sequenceDatabase(String date, String count) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + table + " WHERE date = ?";
        String id = "-1";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, date);
            resultSet = statement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                if (String.valueOf(i).equals(count)) {
                    id = resultSet.getString("id");
                    break;
                }
                ++i;
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

        return id;
    }

    public void fetchGoogleCalendar() {
        List<Event> items = null;
        try {
            items = LibGoogleCalendarConnector.getConnection();
        }
        catch (IOException exception) {
            String message = "I/O for Google Calendar failed.";
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        if (items.size() == 0)
            System.out.println("No upcoming events found.");
        else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                start = (start == null) ? event.getStart().getDate() : start;
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
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
