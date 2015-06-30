import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * The connector library for Google Calendar accounts.
 */
public final class LibGoogleCalendarConnector {

    /**
     * The name of the application.
     */
    private static final String APPLICATION_NAME = "Personal Calendar";

    /**
     * A String representing the credential directory path.
     */
    private static final String DATA_STORE_DIR = System.getProperty("user.home") + "/.credentials/calendar-api";

    /**
     * A File object referencing to the credential directory.
     */
    private static final File DATA_STORE_REF = new File(DATA_STORE_DIR);

    /**
     * A FileDataStoreFactory object built from the credential directory reference.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /**
     * A JsonFactory object.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * A HttpTransport object.
     */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * The scopes of the Google Calendar.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_REF);
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            String message = "Load Google API failed.";
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return a Credential object
     * @throws IOException an IOException object
     */
    public static Credential authorize() throws IOException {
        InputStream in = LibGoogleCalendarConnector.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
        Credential credential =
                new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        String message = "Credentials saved to " + DATA_STORE_REF.getAbsolutePath() + " successfully.";
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return a Calendar object
     * @throws IOException an IOException object
     */
    public static Calendar getCalendarService() throws IOException {
        Credential credential = authorize();
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME).build();
    }

    /**
     * Build a new authorized API client service and receive the event list.
     * @return a List of Event objects
     * @throws IOException an IOException object
     */
    public static List<Event> getConnection() throws IOException {
        Calendar service = getCalendarService();
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(100).setTimeMin(now).setOrderBy("startTime").setSingleEvents(true).execute();
        return events.getItems();
    }

}
