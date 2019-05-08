package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainController {

    private String state;

    @FXML
    private TextField path;

    @FXML
    private TextArea rtBox;

    @FXML
    private Button loadBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField numberField;

    @FXML
    private Button fibonacciBtn;

    @FXML
    private ProgressBar progressBar;

    public MainController() {
        state = States.READY;
    }

    private void saveFile()
    {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    progressBar.setProgress(0);
                    FileWriter fileWriter = new FileWriter(path.getText(),false);

                    fileWriter.write(rtBox.getText());

                    fileWriter.close();
                    progressBar.setProgress(100);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null,"Path not valid");
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();


    }


    @FXML
    void onFibonacciClick(ActionEvent event) {

    }

    @FXML
    void onLoadClick(ActionEvent event) {

        Runnable runnable = new Runnable() {

            public void run() {
                try {
                    FileReader fileReader = new FileReader( path.getText());

                    int data = fileReader.read();
                    while(data != -1) {
                        rtBox.setText(rtBox.getText() + (char)data);

                        data = fileReader.read();
                    }
                    fileReader.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    @FXML
    void onSaveClick(ActionEvent event) {
        saveFile();
    }

}
