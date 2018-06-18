package com.mikhail.pravilov.mit.controller;

import com.mikhail.pravilov.mit.model.statistic.TestConfiguration;
import com.mikhail.pravilov.mit.view.ChartsSceneSupplier;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static com.mikhail.pravilov.mit.model.statistic.TestConfiguration.ChangingParameter;
import static com.mikhail.pravilov.mit.model.statistic.TestConfiguration.ChangingParameter.*;
import static com.mikhail.pravilov.mit.model.statistic.TestConfiguration.ServerType;
import static com.mikhail.pravilov.mit.model.statistic.TestConfiguration.ServerType.*;

public class SetUpConfigurationScene {
    public VBox serverArchitectureTypes;
    public RadioButton oneThreadRadioButton;
    public RadioButton multiThreadingRadioButton;
    public RadioButton unblockingRadioButton;
    public RadioButton numberOfElementsRadioButton;
    public RadioButton numberOfClientsRadioButton;
    public RadioButton timeBetweenRequestsRadioButton;
    public TextField numberOfElementsTextField;
    public TextField numberOfClientsTextField;
    public TextField timeBetweenRequestsTextField;
    public TextField numberOfRequests;
    public TextField fromBoundTextField;
    public TextField toBoundTextField;
    public TextField stepTextField;

    private ServerType serverType = ONE_THREAD;
    private ChangingParameter changingParameter = NUMBER_OF_ELEMENTS;

    public void GoButtonMouseClicked(MouseEvent mouseEvent) {
        try {
            TestConfiguration testConfiguration = new TestConfiguration(serverType);
            testConfiguration.setChangingParameter(changingParameter);
            testConfiguration.setNumberOfRequests(Integer.valueOf(numberOfRequests.getText()));
            testConfiguration.setChangingBound(new Pair<>(Integer.valueOf(fromBoundTextField.getText()), Integer.valueOf(toBoundTextField.getText())));
            testConfiguration.setChangingStep(Integer.valueOf(stepTextField.getText()));
            if (changingParameter != NUMBER_OF_ELEMENTS) {
                testConfiguration.setNumberOfElements(Integer.valueOf(numberOfElementsTextField.getText()));
            } else {
                testConfiguration.setNumberOfElements(testConfiguration.getChangingBound().getKey());
            }
            if (changingParameter != NUMBER_OF_CLIENTS) {
                testConfiguration.setNumberOfClients(Integer.valueOf(numberOfClientsTextField.getText()));
            } else {
                testConfiguration.setNumberOfClients(testConfiguration.getChangingBound().getKey());
            }
            if (changingParameter != TIME_BETWEEN_REQUESTS) {
                testConfiguration.setTimeBetweenRequests(Integer.valueOf(timeBetweenRequestsTextField.getText()));
            } else {
                testConfiguration.setTimeBetweenRequests(testConfiguration.getChangingBound().getKey());
            }
            try {
                getStage(mouseEvent).setScene(new ChartsSceneSupplier(testConfiguration).getScene());
            } catch (IOException e) {
                showExceptionMessage("Error during loading window to draw charts", e);
            }
        } catch (NumberFormatException e) {
            showExceptionMessage("You typed not a number", e);
        }
    }

    public void OneThreadRadioButtonClicked(MouseEvent mouseEvent) {
        serverType = ONE_THREAD;
        multiThreadingRadioButton.setSelected(false);
        unblockingRadioButton.setSelected(false);
    }

    public void MultiThreadingRadioButtonClicked(MouseEvent mouseEvent) {
        serverType = MULTITHREADING;
        oneThreadRadioButton.setSelected(false);
        unblockingRadioButton.setSelected(false);
    }

    public void UnblockingRadioButtonClicked(MouseEvent mouseEvent) {
        serverType = UNBLOCKING;
        oneThreadRadioButton.setSelected(false);
        multiThreadingRadioButton.setSelected(false);
    }

    public void NumberOfElementsRadioButtonClicked(MouseEvent mouseEvent) {
        changingParameter = NUMBER_OF_ELEMENTS;
        updateParametersTextFields(numberOfElementsTextField);
        numberOfElementsTextField.setDisable(true);
        numberOfClientsRadioButton.setSelected(false);
        timeBetweenRequestsRadioButton.setSelected(false);
    }

    public void NumberOfClientsRadioButtonClicked(MouseEvent mouseEvent) {
        changingParameter = NUMBER_OF_CLIENTS;
        updateParametersTextFields(numberOfClientsTextField);
        numberOfClientsTextField.setDisable(true);
        numberOfElementsRadioButton.setSelected(false);
        timeBetweenRequestsRadioButton.setSelected(false);
    }

    public void TimeBetweenRequestsRadioButtonClicked(MouseEvent mouseEvent) {
        changingParameter = TIME_BETWEEN_REQUESTS;
        updateParametersTextFields(timeBetweenRequestsTextField);
        timeBetweenRequestsTextField.setDisable(true);
        numberOfElementsRadioButton.setSelected(false);
        numberOfClientsRadioButton.setSelected(false);
    }

    private void updateParametersTextFields(TextField parameterTextField) {
        numberOfElementsTextField.setDisable(numberOfElementsTextField == parameterTextField);
        numberOfClientsTextField.setDisable(numberOfClientsTextField == parameterTextField);
        timeBetweenRequestsTextField.setDisable(timeBetweenRequestsTextField == parameterTextField);
    }

    @NotNull
    private Stage getStage(@NotNull MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        return (Stage) source.getScene().getWindow();
    }

    private void showExceptionMessage(@NotNull String description, @NotNull Exception e) {
        Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
        exceptionAlert.setTitle("Error occurred");
        exceptionAlert.setHeaderText(description);
        exceptionAlert.setContentText("Reason: " + e.getMessage());
        exceptionAlert.showAndWait();
    }
}
