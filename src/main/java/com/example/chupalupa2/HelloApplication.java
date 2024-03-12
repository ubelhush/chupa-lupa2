package com.example.chupalupa2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class HelloApplication extends Application {
    static final String TERMINAL_PATH="/home/voin/IdeaProjects/chupa-lupa2/out/artifacts/custom_terminal/custom_terminal.jar";
    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label pidLabel = new Label("PID:");
        GridPane.setConstraints(pidLabel, 0, 0);
        TextField pidInput = new TextField();
        pidInput.setPromptText("Enter PID");
        GridPane.setConstraints(pidInput, 1, 0);

        Label addressLabel = new Label("Address:");
        GridPane.setConstraints(addressLabel, 0, 1);
        TextField addressInput = new TextField();
        addressInput.setPromptText("Enter Address");
        GridPane.setConstraints(addressInput, 1, 1);

        Label valueLabel = new Label("Value:");
        GridPane.setConstraints(valueLabel, 0, 2);
        TextField valueInput = new TextField();
        valueInput.setPromptText("Enter Value");
        GridPane.setConstraints(valueInput, 1, 2);

        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 3);
        submitButton.setOnAction(e -> {
            try {
                int pid = Integer.parseInt(pidInput.getText());
                String addressStr = addressInput.getText();
                int valueToWrite = Integer.parseInt(valueInput.getText());

                MyMemory myMemory = new MyMemory(pid, addressStr);

                int readValue = myMemory.read();
                System.out.println("Read value from address " + addressStr + ": " + readValue);

                // Запись значения в адрес
                myMemory.write(valueToWrite);
                System.out.println("Wrote value " + valueToWrite + " to address " + addressStr);
            } catch (NumberFormatException ex) {
                System.err.println("Invalid input. Please enter valid integer values.");
            }
        });

        Label ramLabel = new Label("RAM");
        GridPane.setConstraints(ramLabel, 0, 8);
        Label ramText = new Label("RAM");
        GridPane.setConstraints(ramText, 1, 8);
        Button ramButton = new Button("get");
        GridPane.setConstraints(ramButton, 1, 10);
        ramButton.setOnAction(actionEvent -> {

            String result = new String();
            try {
                Process free = Runtime.getRuntime().exec("free");
                BufferedReader reader = new BufferedReader(new InputStreamReader(free.getInputStream()));
                String line;

                for (int i = 0; (line = reader.readLine()) != null; i++) {
                    if (i == 1) {
                        result = line.split(" ")[13];
                        break;
                    }

                }
                free.waitFor();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            ramText.setText(result);
        });

        Button termButton = new Button("terminal");
        GridPane.setConstraints(termButton, 2, 10);
        termButton.setOnAction(actionEvent -> {
            Runtime runtime=Runtime.getRuntime();
            try {
                Process process=runtime.exec("java -jar " + TERMINAL_PATH);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        grid.getChildren().addAll(
                pidLabel, pidInput, addressLabel,
                addressInput, valueLabel, valueInput,
                submitButton, ramLabel, ramText,
                ramButton,  termButton);

        Scene scene = new Scene(grid, 350, 250);
        stage.setScene(scene);
        stage.setTitle("Memory Editor");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}