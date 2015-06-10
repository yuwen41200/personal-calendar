public class Application {

    public static final String version = "v0.99";

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
