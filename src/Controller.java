import java.awt.event.*;

public class Controller extends MouseAdapter implements ActionListener {

    private Model model;
    private View view;

    public void init(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        view.mainWindow();
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
    }

}
