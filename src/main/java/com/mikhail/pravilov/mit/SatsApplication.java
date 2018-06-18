package com.mikhail.pravilov.mit;

import com.mikhail.pravilov.mit.view.SetUpServerConfigurationSceneSupplier;
import javafx.application.Application;
import javafx.stage.Stage;

public class SatsApplication extends Application {
    /**
     * Main method of program. Runs SATS application.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        stage.setScene(new SetUpServerConfigurationSceneSupplier().getScene());
        stage.show();
    }
}
