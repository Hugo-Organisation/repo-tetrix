package views;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaManager {

    private static MediaManager instance;
    private MediaPlayer mediaPlayer;

    private MediaManager() {
        // Ne rien faire ici
    }

    public static MediaManager getInstance() {
        if (instance == null) {
            instance = new MediaManager();
        }
        return instance;
    }

    public MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null) {
            try {
                String musicFile = getClass().getResource("/mp3/made_in_abyss_drums.wav").toExternalForm();
                Media media = new Media(musicFile);
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(0.5);
                mediaPlayer.play();
            } catch (Exception e) {
                System.err.println("Erreur lors de la lecture du média : " + e.getMessage());
                e.printStackTrace();
            }
        }
        return mediaPlayer;
    }
}
