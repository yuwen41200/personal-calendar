import java.awt.event.*;

public class Controller extends MouseAdapter implements ActionListener {

    private Model model;
    private View view;

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("showSubWindow2")) {
            // bla bla bla
        }
        else if (event.getActionCommand().equals("showSubWindow3")) {
            view.subWindow3();
        }
    }

    public void init(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        view.mainWindow();
    }

}
