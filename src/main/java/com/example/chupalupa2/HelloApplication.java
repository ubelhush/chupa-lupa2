package com.example.chupalupa2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
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

                // Создание экземпляра MyMemory
                MyMemory myMemory = new MyMemory(pid, addressStr);

                // Чтение значения из адреса
                int readValue = myMemory.read();
                System.out.println("Read value from address " + addressStr + ": " + readValue);

                // Запись значения в адрес
                myMemory.write(valueToWrite);
                System.out.println("Wrote value " + valueToWrite + " to address " + addressStr);
            } catch (NumberFormatException ex) {
                System.err.println("Invalid input. Please enter valid integer values.");
            }
        });

        grid.getChildren().addAll(pidLabel, pidInput, addressLabel, addressInput, valueLabel, valueInput, submitButton);

        Scene scene = new Scene(grid, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Memory Editor");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}