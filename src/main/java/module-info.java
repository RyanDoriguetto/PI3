module pi2025 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports view;
    exports controller;

    opens view to javafx.fxml;
    opens controller to javafx.fxml;
}
