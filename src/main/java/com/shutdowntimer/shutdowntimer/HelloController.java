package com.shutdowntimer.shutdowntimer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController {
    public Button btn_start;
    public Button btn_cancel;
    public Spinner<Integer> spin_sec;
    public Spinner<Integer> spin_min;
    public Spinner<Integer> spin_hour;
    public static int time;
    public Label lbl_time = new Label("00:00:00");
    Timer timer = new Timer();

    public void initialize() {
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

    public static void setLabels(int hour, int min, int sec, Label lbl_time){
        if(hour<10 && min<10 && sec<10){
            System.out.println(hour+":"+min+":"+sec);

            lbl_time.setText("0"+ hour + ":0" + min + ":0" + sec);
        }
        if (hour<10 && min<10 && sec>=10) {
            System.out.println(hour+":"+min+":"+sec);
            lbl_time.setText("0"+ hour + ":0" + min + ":" + sec);
        }
        if (hour<10 && min>=10 && sec<10){
            System.out.println(hour+":"+min+":"+sec);
            lbl_time.setText("0"+ hour + ":" + min + ":0" + sec);
        }
        if (hour>=10 && min<10 && sec<10){
            System.out.println(hour+":"+min+":"+sec);
            lbl_time.setText(hour + ":0" + min + ":0" + sec);
        }
        if (hour<10 && min>=10 && sec>=10){
            System.out.println(hour+":"+min+":"+sec);
            lbl_time.setText("0"+ hour + ":0" + min + ":0" + sec);
        }
        if (hour>=10 && min<10 && sec>=10){
            System.out.println(hour+":"+min+":"+sec);
            lbl_time.setText(hour + ":0" + min + ":" + sec);
        }
        if (hour>=10 && min>=10 && sec<10){
            System.out.println(hour+":"+min+":"+sec);
            lbl_time.setText(hour + ":" + min + ":0" + sec);
        }
        if (hour>=10 && min>=10 && sec>=10){
            System.out.println(hour+":"+min+":"+sec);
            lbl_time.setText(hour + ":" + min + ":" + sec);
        }

    }

    @FXML
    protected void onStartButtonClick() {
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

    @FXML
    protected void onCancelButtonClick() {
        ProcessBuilder pb =   new ProcessBuilder("shutdown", "-a");
        try {
            pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        timer.cancel();

        setLabels(0, 0, 0, lbl_time);
    }
}