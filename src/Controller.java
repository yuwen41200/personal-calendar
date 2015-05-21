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
        view.subWindow1();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("showSubWindow2")) {
            view.subWindow2();
        }
        else if (event.getActionCommand().equals("showSubWindow3")) {
            view.subWindow3();
        }
        else if (event.getActionCommand().equals("moveBackward")) {
            calendar.add(Calendar.DATE, -7);
            view.mainWindow(calendar);
        }
        else if (event.getActionCommand().equals("moveForward")) {
            calendar.add(Calendar.DATE, 7);
            view.mainWindow(calendar);
        }
    }

}
