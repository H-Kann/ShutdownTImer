package com.shutdowntimer;

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
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

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
    private static final String THEME = "theme";
    private final Map<String, ImageView> startIcons = new HashMap<>();
    private final Map<String, ImageView> stopIcons = new HashMap<>();

    private void savePreferences(String SelectedTheme) {
        Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
        preferences.put(THEME, SelectedTheme);
    }
    private void loadPreferences(){
        Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
        handleThemeChange(preferences.get(THEME, "Dracula"));
    }

    public void initialize() {
        loadIcons();
        loadPreferences();
        Font.loadFont(getClass().getResourceAsStream("/fonts/Montserrat-Bold.ttf"), 96);
        setupSpinners();
        setupThemeComboBox();
        lbl_time.setText("00 : 00 : 00");
    }

    private void loadIcons() {
        String[] colors = {"purple", "blue", "white", "black"};
        for (String color : colors) {
            startIcons.put(color, createImageView("/icons/start_" + color + ".png"));
            stopIcons.put(color, createImageView("/icons/stop_" + color + ".png"));
        }
    }

    private ImageView createImageView(String iconPath) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath)));
        return new ImageView(image);
    }

    private void setupSpinners() {
        SpinnerValueFactory<Integer> valueFactoryHour = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99);
        SpinnerValueFactory<Integer> valueFactoryMin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        SpinnerValueFactory<Integer> valueFactorySec = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);

        valueFactoryHour.setValue(0);
        valueFactoryMin.setValue(0);
        valueFactorySec.setValue(0);

        spin_hour.setValueFactory(valueFactoryHour);
        spin_min.setValueFactory(valueFactoryMin);
        spin_sec.setValueFactory(valueFactorySec);
    }

    private void setupThemeComboBox() {
        ObservableList<String> options = FXCollections.observableArrayList("Cupertino Dark", "Cupertino Light", "Dracula", "Nord Dark", "Nord Light", "Primer Dark", "Primer Light");
        cmb_theme.setItems(options);
        cmb_theme.setOnAction(_ -> handleThemeChange(cmb_theme.getSelectionModel().getSelectedItem()));
    }

    private void handleThemeChange(String selectedTheme) {
        String iconColor;
        switch (selectedTheme) {
            case "Cupertino Dark":
            case "Cupertino Light":
            case "Primer Dark":
            case "Primer Light":
                iconColor = "blue";
                break;
            case "Dracula":
                iconColor = "purple";
                break;
            case "Nord Dark":
                iconColor = "white";
                break;
            case "Nord Light":
                iconColor = "black";
                break;
            default:
                return;
        }

        Application.setUserAgentStylesheet(getUserAgentStylesheet(selectedTheme));
        setButtonIcons(iconColor);
        lbl_time.setTextFill(selectedTheme.contains("Light") ? Color.BLACK : Color.WHITE);
        savePreferences(selectedTheme);
    }

    private String getUserAgentStylesheet(String theme) {
        return switch (theme) {
            case "Cupertino Dark" -> new CupertinoDark().getUserAgentStylesheet();
            case "Cupertino Light" -> new CupertinoLight().getUserAgentStylesheet();
            case "Dracula" -> new Dracula().getUserAgentStylesheet();
            case "Nord Dark" -> new NordDark().getUserAgentStylesheet();
            case "Nord Light" -> new NordLight().getUserAgentStylesheet();
            case "Primer Dark" -> new PrimerDark().getUserAgentStylesheet();
            case "Primer Light" -> new PrimerLight().getUserAgentStylesheet();
            default -> null;
        };
    }

    private void setButtonIcons(String color) {
        ImageView startIcon = startIcons.get(color);
        ImageView stopIcon = stopIcons.get(color);

        if (startIcon != null && stopIcon != null) {
            startIcon.setFitHeight(20);
            startIcon.setFitWidth(20);
            btn_start.setGraphic(startIcon);

            stopIcon.setFitHeight(20);
            stopIcon.setFitWidth(20);
            btn_cancel.setGraphic(stopIcon);
        }
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