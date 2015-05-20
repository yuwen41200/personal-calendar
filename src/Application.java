public class Application {

    public static void main(String args[]) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller();
        String version = "v0.99";
        model.init();
        view.init(controller, version);
        controller.init(model, view);
        controller.run();
    }

}
