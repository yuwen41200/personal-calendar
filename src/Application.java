public class Application {

    public static void main(String args[]) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller();

        String username = "yuwen41200";
        String version = "v0.99";

        model.init(username);
        view.init(model, controller, version);
        controller.init(model, view);
        controller.run();
    }

}
