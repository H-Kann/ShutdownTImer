module com.shutdowntimer.shutdowntimer {
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires atlantafx.base;
    requires fxstage;
    requires java.prefs;

    opens com.shutdowntimer to javafx.fxml;
    exports com.shutdowntimer;
}