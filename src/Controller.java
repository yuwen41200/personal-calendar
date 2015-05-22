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
    }

    public void run() {
        view.mainWindow(calendar);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (event.getSource() instanceof JDatePanel) {
            JDatePanel datePanel = (JDatePanel) event.getSource();
            view.subWindow1(datePanel.getDate());
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("Move Backward")) {
            calendar.add(Calendar.DATE, -7);
            view.mainWindow(calendar);
        }
        else if (event.getActionCommand().equals("Move Forward")) {
            calendar.add(Calendar.DATE, 7);
            view.mainWindow(calendar);
        }
        else if (event.getActionCommand().equals("Integrate Google Calendar")) {
            view.subWindow2();
        }
        else if (event.getActionCommand().equals("About")) {
            view.subWindow3();
        }
        else if (event.getActionCommand().equals("Add Event")) {
            // do sth
        }
        else if (event.getActionCommand().equals("Modify Event")) {
            // do sth
        }
        else if (event.getActionCommand().equals("Remove Event")) {
            // do sth
        }
    }

}
