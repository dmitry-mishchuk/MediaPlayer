package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private MediaPlayer mediaPlayer;

    private String filePath;

    @FXML
    private MediaView mediaView;

    @FXML
    private Slider sceneSlider;

    @FXML
    private Slider volumeSlider;

    @FXML
    private void handleButtonActoin(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.
                ExtensionFilter("Select a File (.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toURI().toString();

        if (filePath != null){
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            mediaView.setFitHeight(500);
            mediaView.setFitWidth(500);
            //DoubleProperty width = mediaView.fitWidthProperty();
           // DoubleProperty hight = mediaView.fitHeightProperty();

           // width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "wigth"));
           // hight.bind(Bindings.selectDouble(mediaView.sceneProperty(), "hight"));
            volumeSlider.setValue(mediaPlayer.getVolume() * 100);
            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                }
            });
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(
                        ObservableValue<? extends Duration> observable,
                        Duration oldValue,
                        Duration newValue
                ) {
                    sceneSlider.setValue(newValue.toSeconds());
                }
            });
            sceneSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(sceneSlider.getValue()));
                }
            });
            mediaPlayer.play();
        }

    }
    @FXML
    private void playVideo(ActionEvent event){
        mediaPlayer.play();
        mediaPlayer.setRate(1.0);
    }
    @FXML
    private void stopVideo(ActionEvent event){
        mediaPlayer.stop();
    }
    @FXML
    private void pauseVideo(ActionEvent event){
        mediaPlayer.pause();
    }
    @FXML
    private void fastPlayVideo(ActionEvent event){
        mediaPlayer.setRate(1.5);
    }
    @FXML
    private void veryFastPlayVideo(ActionEvent event){
        mediaPlayer.setRate(2);
    }
    @FXML
    private void slowPlayVideo(ActionEvent event){
        mediaPlayer.setRate(0.5);
    }
    @FXML
    private void verySlowPlayVideo(ActionEvent event){
        mediaPlayer.setRate(0.25);
    }
    @FXML
    private void exitVideo(ActionEvent event){
        System.exit(0);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
