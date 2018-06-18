package com.mikhail.pravilov.mit.view;

import javafx.scene.Scene;

import java.io.IOException;

/**
 * Interface that describes methods of scene suppliers.
 */
interface SceneSupplier {
    /**
     * Scene getter.
     *
     * @return Stage instance.
     */
    Scene getScene() throws IOException;
}