import javax.swing.*;

public class Application {

    public static void main(String args[]) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller();

        String username = JOptionPane.showInputDialog(null, "User: ", "Starting...", JOptionPane.PLAIN_MESSAGE);
        String version = "v0.99";
        if (username == null || username.equals(""))
            System.exit(0);

        model.init(username);
        view.init(model, controller, version);
        controller.init(model, view);
        controller.run();
    }

}
