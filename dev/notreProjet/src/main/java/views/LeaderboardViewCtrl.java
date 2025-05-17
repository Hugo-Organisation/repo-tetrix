package views;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.*;
import java.util.*;

public class LeaderboardViewCtrl {

    @FXML
    private ListView<String> scoreList;

    @FXML
    public void initialize() {
        List<String> scores = readScores();
        scoreList.getItems().addAll(scores);
    }

    private List<String> readScores() {
        List<String> lines = new ArrayList<>();
        File file = new File("leaderboard.txt");

        if (!file.exists()) return lines;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.lines()
                .map(String::trim)
                .filter(l -> l.contains(":"))
                .sorted((a, b) -> {
                    int scoreA = Integer.parseInt(a.split(":")[1].trim());
                    int scoreB = Integer.parseInt(b.split(":")[1].trim());
                    return Integer.compare(scoreB, scoreA); // plus gros score en haut
                })
                .limit(10) // top 10
                .forEach(lines::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @FXML
    private void onReturnClick(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/MenuView.fxml"));
            Stage stage = (Stage) scoreList.getScene().getWindow();
            Scene menuScene = new Scene(root);
            stage.setScene(menuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
