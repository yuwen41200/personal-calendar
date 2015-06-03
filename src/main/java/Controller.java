import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Controller extends MouseAdapter implements ActionListener {

    private Model model;
    private View view;
    private Calendar calendar;

    public void init(Model model, View view) {
        this.model = model;
        this.view = view;
        this.calendar = Calendar.getInstance();
        int displacement = - calendar.get(Calendar.DAY_OF_WEEK) + 2;
        displacement = (displacement == 1) ? -6 : displacement;
        calendar.add(Calendar.DATE, displacement);
    }

    public void run() {
        view.mainWindow(calendar);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (event.getSource() instanceof JDatePanel) {
            JDatePanel datePanel = (JDatePanel) event.getSource();
            view.subWindow1(datePanel.getCalendar());
        }
    }

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
            model.fetchGoogleCalendar();
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
    }

}
