package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.SearchX;
import models.States;

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

    private void saveFile(String string)
    {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {

                    while(state!=States.READY);

                    state = States.SAVING;

                    FileWriter nFile = new FileWriter(path.getText());
                    char[] arr = string.toCharArray();

                    for(int i =0;i<arr.length;i++)
                    {
                        Thread.sleep(450);
                        double prog = (double)(i+1)/(double)arr.length;
                        progressBar.setProgress(prog);
                        nFile.write((int)arr[i]);
                    }
                    nFile.flush();
                    nFile.close();

                    state = States.READY;

                } catch (Exception e) {
                    state = States.READY;
                    JOptionPane.showMessageDialog(null,"Path not valid");
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();


    }


    @FXML
    void onFibonacciClick(ActionEvent event) {
        //SearchX searchX = new SearchX()
    }

    public void onLoadClick(ActionEvent actionEvent) {
        final String Path = path.getText();
        Runnable runnable = new Runnable() {

            public void run() {
                try {

                    while(state!=States.READY);

                    state = States.LOADING;
                    FileReader fileReader = new FileReader(Path);

                    int data = fileReader.read();
                    while(data != -1) {

                        rtBox.setText(rtBox.getText() + (char)data);

                        data = fileReader.read();
                    }
                    fileReader.close();

                    state = States.READY;

                } catch (Exception e) {
                    state = States.READY;
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    @FXML
    void onSaveClick(ActionEvent event) {
        saveFile(rtBox.getText());
    }

}
