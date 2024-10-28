module com.shutdowntimer.shutdowntimer {
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires atlantafx.base;

    opens com.shutdowntimer.shutdowntimer to javafx.fxml;
    exports com.shutdowntimer.shutdowntimer;
}