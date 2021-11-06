module de.dummyapt.sandboxfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.dummyapt.sandboxfx to javafx.fxml;
    exports de.dummyapt.sandboxfx;
}