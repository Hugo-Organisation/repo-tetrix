import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.MainMenu;

public class App extends Application {

    private final int width_screen = 700;
    private final int height_screen = 700;
    private final int square_size = 100;

    @Override
    public void start(Stage primaryStage) {
        Scene menuScene = new Scene(new MainMenu(primaryStage, square_size, width_screen, height_screen));

        primaryStage.setTitle("Menu Principal");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
