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
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + table;
        query += " WHERE date = \'" + calendarToStr(calendar) + "\'";
        ArrayList<String> results = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                results.add(resultSet.getString("event"));
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
                    String message = "Close result set failed.";
                    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (SQLException exception) {
                    String message = "Close statement failed.";
                    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        return results;
    }

    public void insertDatabase(String date, String event) {

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
        return new Calendar.Builder().setDate(intDate[0], intDate[1], intDate[2]).build();
    }

}
