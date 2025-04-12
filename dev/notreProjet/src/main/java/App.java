import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.MainMenu;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {

        Parent root;

        try {

            root = FXMLLoader.load(getClass().getResource("/fxml/MenuView.fxml"));

            Scene menuScene = new Scene(root);
            primaryStage.setScene(menuScene);
            primaryStage.sizeToScene();
            primaryStage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

//        Scene menuScene = new Scene(new MainMenu(primaryStage));
//
//        primaryStage.setTitle("Menu Principal");
//        primaryStage.setScene(menuScene);
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
