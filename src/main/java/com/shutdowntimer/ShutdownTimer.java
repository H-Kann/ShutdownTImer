package com.shutdowntimer;

import atlantafx.base.theme.Dracula;
import ch.micheljung.fxwindow.FxStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ShutdownTimer extends Application {

    public static void main(String[]args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
        FXMLLoader fxmlLoader = new FXMLLoader(ShutdownTimer.class.getResource("shutdown-timer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Shutdown Timer!");
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(ShutdownTimer.class.getResourceAsStream("/icons/Shutdowntimer.png"))));
        stage.show();
        FxStage.configure(stage).apply();
    }
}