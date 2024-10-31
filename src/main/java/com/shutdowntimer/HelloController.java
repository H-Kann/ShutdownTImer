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


    // Saves the theme user chose as a user preference
    private void savePreferences(String SelectedTheme) {
        Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
        preferences.put(THEME, SelectedTheme);
    }

    // Loads the user preferences
    private void loadPreferences(){
        Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
        handleThemeChange(preferences.get(THEME, "Dracula"));
    }

    /**
     * Initializes the application components upon startup.
     * <p>
     * This method is called to perform necessary setup tasks, including loading
     * icons, loading user preferences, setting up fonts, configuring spinners,
     * and initializing the theme selection combo box. It also sets the initial
     * time label to "00 : 00 : 00".
     * </p>
     */
    public void initialize() {
        loadIcons();
        loadPreferences();
        Font.loadFont(getClass().getResourceAsStream("/fonts/Montserrat-Bold.ttf"), 96);
        setupSpinners();
        setupThemeComboBox();
        lbl_time.setText("00 : 00 : 00");
    }

    // Loads all icons from assets folder
    private void loadIcons() {
        String[] colors = {"purple", "blue", "white", "black"};
        for (String color : colors) {
            startIcons.put(color, createImageView("/icons/start_" + color + ".png"));
            stopIcons.put(color, createImageView("/icons/stop_" + color + ".png"));
        }
    }

    // Creates the image view based on the icon chosen
    private ImageView createImageView(String iconPath) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath)));
        return new ImageView(image);
    }


    /**
     * Configures the hour, minute, and second spinners for time input.
     * <p>
     * This method initializes the spinners with appropriate value ranges:
     * - Hours: 0 to 99
     * - Minutes: 0 to 59
     * - Seconds: 0 to 59
     * It also sets the default values for each spinner to 0.
     * </p>
     */
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

    /**
     * Handles changes to the application's theme based on the selected theme.
     * <p>
     * This method updates the user agent stylesheet, sets button icon colors,
     * and adjusts the text color of the time label according to the selected theme.
     * It also saves the user's theme preferences.
     * The icon color is determined by the selected theme, with specific colors assigned to each theme category.
     * If the selected theme is unrecognized, the method exits without making changes.
     * </p>
     *
     * @param selectedTheme the name of the theme selected by the user.
     */
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

    /**
     * Retrieves the user agent stylesheet based on the specified theme.
     * <p>
     * This method checks the provided theme name and returns the corresponding
     * user agent stylesheet for that theme. If the theme is not recognized,
     * it returns {@code null}.
     * </p>
     *
     * @param theme the name of the theme for which to retrieve the stylesheet.
     * @return the user agent stylesheet as a {@code String} for the specified theme,
     *         or {@code null} if the theme is unrecognized.
     */
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

    /**
     * Sets the icons and their colors
     * <p>
     * This method gets the colors of both the start and stop icons according to the theme,
     * and applies them to the application while adjusting the dimensions.
     * </p>
     * @param color The color value to apply to the start and stop buttons
     */
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

    /**
     * Sets the text of the specified label to the formatted time.
     * <p>
     * This method takes hour, minute, and second values, formats them into a
     * string in the format "HH : MM : SS", and updates the provided label
     * with this formatted string.
     * </p>
     *
     * @param hour the hour component of the time to be displayed.
     * @param min  the minute component of the time to be displayed.
     * @param sec  the second component of the time to be displayed.
     * @param lbl_time the label to update with the formatted time string.
     */
    public static void setLabels(int hour, int min, int sec, Label lbl_time){
        String formattedTime = String.format("%02d : %02d : %02d", hour, min, sec);
        lbl_time.setText(formattedTime);
    }

    /**
     * Handles the action when the start button is clicked.
     * <p>
     * This method cancels any existing timer and initializes a new one. It retrieves
     * the time values from the hour, minute, and second spinners, sets the corresponding
     * labels, and converts the time to seconds. It then starts the system shutdown process
     * using the specified time and begins a countdown timer that updates the display every second.
     * </p>
     * <p>
     * If an error occurs while attempting to start the shutdown process, a {@link RuntimeException}
     * is thrown.
     * </p>
     *
     * @throws RuntimeException if an I/O error occurs during the shutdown process.
     */
    @FXML
    protected void onStartButtonClick() {
        // Cancel running timer if it exists
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

    /**
     * Decrements the countdown timer and updates the displayed time on the given label.
     * <p>
     * This method reduces the remaining time by one second, calculates the new hours,
     * minutes, and seconds, and updates the specified label with the formatted time.
     * </p>
     *
     * @param lbl_time the label to update with the current time remaining in the countdown.
     */
    private static void setInterval(Label lbl_time) {
        --time;
        int seconds = time % 60;
        int secondsInMinutes = (time - seconds) / 60;
        int minutes = secondsInMinutes % 60;
        int hours = (secondsInMinutes - minutes) / 60;
        setLabels(hours, minutes, seconds, lbl_time);
    }

    /**
     * Cancels the ongoing shutdown process and resets the timer and UI components.
     * <p>
     * This method constructs a process builder to abort the shutdown, resets the values
     * of the hour, minute, and second spinners to their default ranges, and updates the
     * display label to show zero time remaining. If an error occurs while attempting to
     * cancel the shutdown, a {@link RuntimeException} is thrown.
     * </p>
     *
     * @throws RuntimeException if an I/O error occurs during the cancellation of the shutdown process.
     */
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