module co.edu.uniquindio.marketplace.marketplaceapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.marketplace.marketplaceapp to javafx.fxml;
    exports co.edu.uniquindio.marketplace.marketplaceapp;
}