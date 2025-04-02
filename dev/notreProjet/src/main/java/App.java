import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.MainMenu;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        Scene menuScene = new Scene(new MainMenu(primaryStage));

        primaryStage.setTitle("Menu Principal");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
