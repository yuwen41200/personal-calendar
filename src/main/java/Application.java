/**
 * The application entry point.
 */
public class Application {

    /**
     * A String representing the application version.
     */
    public static final String version = "v0.99";

    /**
     * Start the application with MVC objects.
     * @param args optional arguments
     */
    public static void main(String args[]) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller();

        model.init();
        view.init(model, controller, version);
        controller.init(model, view);
        controller.run();
    }

}
