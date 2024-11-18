module co.edu.uniquindio.marketplace.marketplaceapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.marketplace.marketplaceapp to javafx.fxml;
    exports co.edu.uniquindio.marketplace.marketplaceapp;
    opens co.edu.uniquindio.marketplace.marketplaceapp.controller to javafx.fxml;
    exports  co.edu.uniquindio.marketplace.marketplaceapp.controller;
    opens  co.edu.uniquindio.marketplace.marketplaceapp.model to javafx.fxml;
    exports  co.edu.uniquindio.marketplace.marketplaceapp.model;
    opens images to javafx.fxml;
    //opens co.edu.uniquindio.marketplace.marketplaceapp.viewcontroller to javafx.fxml;
    //exports co.edu.uniquindio.marketplace.marketplaceapp.viewcontroller;
}