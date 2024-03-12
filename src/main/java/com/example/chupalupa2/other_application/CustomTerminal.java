package com.example.chupalupa2.other_application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class CustomTerminal extends Application {
    private static String currentDirectory = System.getProperty("user.dir");

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        TextArea outputTextArea = new TextArea();
        outputTextArea.setEditable(false);
        ScrollPane scrollPane = new ScrollPane(outputTextArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        root.setCenter(scrollPane);

        TextField inputTextField = new TextField();
        inputTextField.setOnAction(event -> {
            String command = inputTextField.getText();
            outputTextArea.appendText(">> " + command + "\n");
            outputTextArea.appendText(commands(command));
            inputTextField.clear();
        });
        root.setBottom(inputTextField);
        BorderPane.setMargin(inputTextField, new Insets(10));

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("JavaFX Terminal");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static String commands(String input) {
        String result = "> ";
        String[] parts = input.split(" ");
        String commandName = parts[0];

        switch (commandName) {
            case "free":
                result = executeCommand("free");
                break;
            case "vmstat":
                result = executeCommand("vmstat");
                break;
            case "meminfo":
                result = executeCommand("cat /proc/meminfo");
                break;
            case "ps":
                result = executeCommand("ps aux");
                break;
            case "top":
                result = executeCommand("top -n 1");
                break;
            case "pmap":
                result = executeCommand("pmap `pidof java`");
                break;
            case "sar":
                result = executeCommand("sar -r 1 3");
                break;
            case "slabtop":
                result = executeCommand("slabtop");
                break;
            case "swappiness":
                result = executeCommand("cat /proc/sys/vm/swappiness");
                break;
            case "cache_pressure":
                result = executeCommand("cat /proc/sys/vm/vfs_cache_pressure");
                break;
            // Другие команды
            default:
                result = "Unknown command: " + commandName + "\n";
                break;
        }

        return result;
    }

    private static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}