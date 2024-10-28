package com.shutdowntimer.shutdowntimer;

import atlantafx.base.theme.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController {
    @FXML
    public Button btn_start;
    public Button btn_cancel;
    public Spinner<Integer> spin_sec;
    public Spinner<Integer> spin_min;
    public Spinner<Integer> spin_hour;
    public static int time;
    public Label lbl_time = new Label();
    public AnchorPane lyt_anchor;
    public ComboBox<String> cmb_theme;
    Timer timer;

    Image imgStartPurple = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/start_purple.png")));
    ImageView imgViewPurple = new ImageView(imgStartPurple);
    Image imgStartBlue = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/start_blue.png")));
    ImageView imgViewBlue = new ImageView(imgStartBlue);
    Image imgStartWhite = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/start_white.png")));
    ImageView imgViewWhite = new ImageView(imgStartWhite);
    Image imgStartBlack = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/start_black.png")));
    ImageView imgViewBlack = new ImageView(imgStartBlack);


    Image imgStopPurple = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/stop_purple.png")));
    ImageView imgViewStopPurple = new ImageView(imgStopPurple);
    Image imgStopBlue = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/stop_blue.png")));
    ImageView imgViewStopBlue = new ImageView(imgStopBlue);
    Image imgStopWhite = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/stop_white.png")));
    ImageView imgViewStopWhite = new ImageView(imgStopWhite);
    Image imgStopBlack = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/stop_black.png")));
    ImageView imgViewStopBlack = new ImageView(imgStopBlack);

    public void initialize() {

        Font.loadFont(getClass().getResourceAsStream("/fonts/Montserrat-Bold.ttf"), 96);

        imgViewPurple.setFitHeight(20);
        imgViewPurple.setFitWidth(20);
        btn_start.setGraphic(imgViewPurple);


        imgViewStopPurple.setFitHeight(20);
        imgViewStopPurple.setFitWidth(20);
        btn_cancel.setGraphic(imgViewStopPurple);

        lbl_time.setText("00 : 00 : 00");
        SpinnerValueFactory<Integer> valueFactoryHour = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99);
        SpinnerValueFactory<Integer> valueFactoryMin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        SpinnerValueFactory<Integer> valueFactorySec = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);

        valueFactoryHour.setValue(0);
        valueFactoryMin.setValue(0);
        valueFactorySec.setValue(0);

        spin_hour.setValueFactory(valueFactoryHour);
        spin_min.setValueFactory(valueFactoryMin);
        spin_sec.setValueFactory(valueFactorySec);

        ObservableList<String> options = FXCollections.observableArrayList("Cupertino Dark", "Cupertino Light", "Dracula", "Nord Dark", "Nord Light", "Primer Dark", "Primer Light");
        cmb_theme.setItems(options);
        cmb_theme.setOnAction(_ -> {
            switch (cmb_theme.getSelectionModel().getSelectedItem()) {
                case "Cupertino Dark":
                    Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());

                    imgViewBlue.setFitHeight(20);
                    imgViewBlue.setFitWidth(20);
                    btn_start.setGraphic(imgViewBlue);

                    imgViewStopBlue.setFitHeight(20);
                    imgViewStopBlue.setFitWidth(20);
                    btn_cancel.setGraphic(imgViewStopBlue);

                    lbl_time.setTextFill(Color.WHITE);
                    break;
                case "Cupertino Light":
                    Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());

                    imgViewBlue.setFitHeight(20);
                    imgViewBlue.setFitWidth(20);
                    btn_start.setGraphic(imgViewBlue);

                    imgViewStopBlue.setFitHeight(20);
                    imgViewStopBlue.setFitWidth(20);
                    btn_cancel.setGraphic(imgViewStopBlue);

                    lbl_time.setTextFill(Color.BLACK);
                    break;
                case "Dracula":
                    Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
                    imgViewPurple.setFitHeight(20);
                    imgViewPurple.setFitWidth(20);
                    btn_start.setGraphic(imgViewPurple);


                    imgViewStopPurple.setFitHeight(20);
                    imgViewStopPurple.setFitWidth(20);
                    btn_cancel.setGraphic(imgViewStopPurple);
                    lbl_time.setTextFill(Color.WHITE);
                    break;
                case "Nord Dark":
                    Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
                    imgViewWhite.setFitHeight(20);
                    imgViewWhite.setFitWidth(20);
                    btn_start.setGraphic(imgViewWhite);


                    imgViewStopWhite.setFitHeight(20);
                    imgViewStopWhite.setFitWidth(20);
                    btn_cancel.setGraphic(imgViewStopWhite);
                    lbl_time.setTextFill(Color.WHITE);
                    break;
                case "Nord Light":
                    Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
                    imgViewBlack.setFitHeight(20);
                    imgViewBlack.setFitWidth(20);
                    btn_start.setGraphic(imgViewBlack);


                    imgViewStopBlack.setFitHeight(20);
                    imgViewStopBlack.setFitWidth(20);
                    btn_cancel.setGraphic(imgViewStopBlack);
                    lbl_time.setTextFill(Color.BLACK);
                    break;
                case "Primer Dark":
                    Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
                    imgViewBlue.setFitHeight(20);
                    imgViewBlue.setFitWidth(20);
                    btn_start.setGraphic(imgViewBlue);

                    imgViewStopBlue.setFitHeight(20);
                    imgViewStopBlue.setFitWidth(20);
                    btn_cancel.setGraphic(imgViewStopBlue);
                    lbl_time.setTextFill(Color.WHITE);
                    break;
                case "Primer Light":
                    Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
                    imgViewBlue.setFitHeight(20);
                    imgViewBlue.setFitWidth(20);
                    btn_start.setGraphic(imgViewBlue);

                    imgViewStopBlue.setFitHeight(20);
                    imgViewStopBlue.setFitWidth(20);
                    btn_cancel.setGraphic(imgViewStopBlue);
                    lbl_time.setTextFill(Color.BLACK);
                    break;
            }
        });
    }

    public static void setLabels(int hour, int min, int sec, Label lbl_time){
        String formattedTime = String.format("%02d : %02d : %02d", hour, min, sec);
        lbl_time.setText(formattedTime);
    }

    @FXML
    protected void onStartButtonClick() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        // Get values from Spinners
        int hour = spin_hour.getValue();
        int min = spin_min.getValue();
        int sec = spin_sec.getValue();

        // Set the labels with the correct time
        setLabels(hour, min, sec, lbl_time);

        // Convert to seconds
        time = (hour*60*60) + (min*60) + sec;

        // Start the shutdown process
        ProcessBuilder pb =   new ProcessBuilder("shutdown", "-s", "-t", String.valueOf(time));
        try {
            pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Start the countdown
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(time>0) {
                    Platform.runLater(() -> setInterval(lbl_time));
                }

            }
        }, 1000, 1000);


    }

    private static void setInterval(Label lbl_time) {
        --time;
        int seconds = time % 60;
        int secondsInMinutes = (time - seconds) / 60;
        int minutes = secondsInMinutes % 60;
        int hours = (secondsInMinutes - minutes) / 60;
        setLabels(hours, minutes, seconds, lbl_time);
    }

    // Cancel the process and set the labels to 0
    @FXML
    protected void onCancelButtonClick() {
        ProcessBuilder pb =   new ProcessBuilder("shutdown", "-a");
        spin_hour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99));
        spin_min.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));
        spin_sec.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));
        try {
            pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        timer.cancel();
        timer.purge();
        timer = null;
        setLabels(0, 0, 0, lbl_time);
    }
}