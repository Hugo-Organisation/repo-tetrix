package views;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaManager {

    private static MediaManager instance;

    private MediaPlayer musicPlayer;
    private MediaPlayer destructionPlayer;
    private MediaPlayer groundCollisionPlayer;

    private Media destructionMedia;
    private Media groundCollisionMedia;
    private boolean soundPreloaded = false;

    private boolean isCollisionPlaying = false;
    private boolean isDestructionPlaying = false;

    private MediaManager() {
    }

    public static MediaManager getInstance() {
        if (instance == null) {
            instance = new MediaManager();
        }
        return instance;
    }

    public MediaPlayer getMusicPlayer() {
        if (musicPlayer == null) {
            try {
                String musicFile = getClass().getResource("/mp3/made_in_abyss_drums.wav").toExternalForm();
                Media media = new Media(musicFile);
                musicPlayer = new MediaPlayer(media);
                musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                musicPlayer.setVolume(0.5);
                musicPlayer.play();
            } catch (Exception e) {
                System.err.println("Erreur lors de la lecture du média : " + e.getMessage());
                e.printStackTrace();
            }
        }
        return musicPlayer;
    }

    public void preloadSounds() {
        if (!soundPreloaded) {
            try {
                String destructionPath = getClass().getResource("/mp3/mario_hit.wav").toExternalForm();
                destructionMedia = new Media(destructionPath);
                destructionPlayer = new MediaPlayer(destructionMedia);
                destructionPlayer.setOnEndOfMedia(() -> {
                    destructionPlayer.dispose();
                    isDestructionPlaying = false;
                });


                String collisionPath = getClass().getResource("/mp3/minecraft_placing_block.wav").toExternalForm();
                groundCollisionMedia = new Media(collisionPath);
                groundCollisionPlayer = new MediaPlayer(groundCollisionMedia);
                groundCollisionPlayer.setOnEndOfMedia(() -> {
                    groundCollisionPlayer.dispose();
                    isCollisionPlaying = false;
                });

                
                soundPreloaded = true;
            } catch (Exception e) {
                System.err.println("Erreur lors du préchargement des médias : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void playDestructionSound() {
        if (destructionMedia == null || isDestructionPlaying) return;
        destructionPlayer = new MediaPlayer(destructionMedia);
        destructionPlayer.setOnEndOfMedia(() -> {
            destructionPlayer.dispose();
            isDestructionPlaying = false;
        });
        isDestructionPlaying = true;
        destructionPlayer.play();
    }

    public void playGroundCollisionSound() {
        if (groundCollisionMedia == null || isCollisionPlaying) return;
        groundCollisionPlayer = new MediaPlayer(groundCollisionMedia);
        groundCollisionPlayer.setOnEndOfMedia(() -> {
            groundCollisionPlayer.dispose();
            isCollisionPlaying = false;
        });
        isCollisionPlaying = true;
        groundCollisionPlayer.play();
    }
}
