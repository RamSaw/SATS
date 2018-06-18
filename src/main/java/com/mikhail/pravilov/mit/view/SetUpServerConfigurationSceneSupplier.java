package com.mikhail.pravilov.mit.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SetUpServerConfigurationSceneSupplier implements SceneSupplier {
    @Override
    public Scene getScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/SetUpServerConfigurationScene.fxml"));
        return new Scene(root);
    }
}
