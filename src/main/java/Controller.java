import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * The Controller class of the MVC structure.
 */
public class Controller extends MouseAdapter implements ActionListener {

    /**
     * A Model object.
     */
    private Model model;

    /**
     * A View object.
     */
    private View view;

    /**
     * A date, used to control the windows in the View object.
     */
    private Calendar calendar;

    /**
     * Initialize the Controller object.
     * @param model a Model object
     * @param view a View object
     */
    public void init(Model model, View view) {
        this.model = model;
        this.view = view;
        this.calendar = Calendar.getInstance();
        int displacement = - calendar.get(Calendar.DAY_OF_WEEK) + 2;
        displacement = (displacement == 1) ? -6 : displacement;
        calendar.add(Calendar.DATE, displacement);
    }

    /**
     * Run the application.
     */
    public void run() {
        view.mainWindow(calendar);
    }

    /**
     * Actions to be done when the mouse is clicked.
     * @param event the source of the event
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        if (event.getSource() instanceof JDatePanel) {
            JDatePanel datePanel = (JDatePanel) event.getSource();
            view.subWindow1(datePanel.getCalendar());
        }
    }

    /**
     * Actions to be done when a button is clicked.
     * @param event the source of the event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("Move Backward")) {
            calendar.add(Calendar.DATE, -7);
            view.mainWindowRender(calendar);
        }

        else if (event.getActionCommand().equals("Move Forward")) {
            calendar.add(Calendar.DATE, 7);
            view.mainWindowRender(calendar);
        }

        else if (event.getActionCommand().equals("Synchronize Google Calendar")) {
            view.subWindow2();
        }

        else if (event.getActionCommand().equals("About")) {
            view.subWindow3();
        }

        else if (event.getActionCommand().contains("Add Event")) {
            String message = "New event: ";
            String input = JOptionPane.showInputDialog(null, message, "Add Event", JOptionPane.PLAIN_MESSAGE);

            if (input != null && !input.equals("")) {
                String[] commands = event.getActionCommand().split(" ");
                model.insertDatabase(commands[2], input);
                view.mainWindowRender(calendar);
                view.subWindow1(model.strToCalendar(commands[2]));
            }
        }

        else if (event.getActionCommand().contains("Modify Event")) {
            String message = "Edit event: ";
            int index = event.getActionCommand().indexOf("%");
            String initial = event.getActionCommand().substring(index+1);
            String input = (String) JOptionPane.showInputDialog
                    (null, message, "Modify Event", JOptionPane.PLAIN_MESSAGE, null, null, initial);

            if (input != null && !input.equals("")) {
                String[] commands = event.getActionCommand().split(" ");
                String id = model.sequenceDatabase(commands[2], commands[3]);
                model.updateDatabase(id, input);
                view.mainWindowRender(calendar);
                view.subWindow1(model.strToCalendar(commands[2]));
            }
        }

        else if (event.getActionCommand().contains("Remove Event")) {
            String message = "Delete the event permanently?";
            int input = JOptionPane.showConfirmDialog(null, message, "Remove Event", JOptionPane.YES_NO_OPTION);

            if (input == JOptionPane.YES_OPTION) {
                String[] commands = event.getActionCommand().split(" ");
                String id = model.sequenceDatabase(commands[2], commands[3]);
                model.deleteDatabase(id);
                view.mainWindowRender(calendar);
                view.subWindow1(model.strToCalendar(commands[2]));
            }
        }

        else if (event.getActionCommand().contains("Add Google Calendar Event")) {
            String[] commands = event.getActionCommand().split(" ");
            int index = event.getActionCommand().indexOf("%");
            String initial = event.getActionCommand().substring(index+1);
            model.insertDatabase(commands[4], initial);
            view.mainWindowRender(calendar);
            view.subWindow1(model.strToCalendar(commands[4]));
        }
    }

}
