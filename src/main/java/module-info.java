module com.shutdowntimer.shutdowntimer {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.shutdowntimer.shutdowntimer to javafx.fxml;
    exports com.shutdowntimer.shutdowntimer;
}