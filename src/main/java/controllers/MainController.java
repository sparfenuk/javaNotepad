package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;
import models.States;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class MainController {

    private volatile String state;

    @FXML
    private AnchorPane anchor;

    @FXML
    private TextField path;

//    @FXML
//    private TextArea rtBox;

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

    private StyleClassedTextArea rtBox;

    @FXML
    public void initialize(){
        numberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    numberField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });



        rtBox  = new StyleClassedTextArea ();
        rtBox.setParagraphGraphicFactory(LineNumberFactory.get(rtBox));
        rtBox.setLayoutX(14.0);
        rtBox.setLayoutY(47.0);
        rtBox.setPrefHeight(312.0);
        rtBox.setPrefWidth(556.0);
        anchor.getChildren().add(rtBox);



    }
    public MainController() {
        state = States.READY;
    }

    private void saveFile(String string)
    {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {

                    while(!state.equals(States.READY));

                    state = States.SAVING;

                    FileWriter nFile = new FileWriter(path.getText());
                    char[] arr = string.toCharArray();

                    for(int i =0;i<arr.length;i++)
                    {
                        //Thread.sleep(450);

                        progressBar.setProgress((double)(i+1)/(double)arr.length);
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

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while(!state.equals(States.READY));

                    state = States.SEARCHINGX;

                    BigInteger maxNumber = new BigInteger(numberField.getText());
                    BigInteger previousNumber = BigInteger.ZERO;
                    BigInteger nextNumber = BigInteger.ONE;


                    String text = "";
                    text += previousNumber.toString() + "\n";
                    text += nextNumber.toString() + "\n";

                    for (BigInteger i = BigInteger.ONE; i.compareTo(maxNumber) ==-1; i = i.add(BigInteger.ONE))
                    {
                        BigInteger sum = previousNumber.add(nextNumber);
                        previousNumber = nextNumber;
                        nextNumber = sum;
                        text += sum.toString() + "\n";

                        progressBar.setProgress((i.add(BigInteger.ONE).doubleValue())/maxNumber.doubleValue());
                    }


                    state = States.READY;

                    saveFile(text);



                } catch (Exception e) {
                    state = States.READY;
                    e.printStackTrace();
                }

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void onLoadClick(ActionEvent actionEvent) {

        Runnable runnable = new Runnable() {

            public void run() {
                try {


                    while(!state.equals(States.READY));

                    state = States.LOADING;

                    File toLoad =new File(path.getText());
                    //rtBox.replaceText("");

                    FileReader fileReader = new FileReader(toLoad);

                    //int data = fileReader.read();

                    double dataLength = toLoad.length()/1000;

                    int data = fileReader.read();
                    int j = 0;
                    StringBuilder sb = new StringBuilder();
                    for(BigInteger i = BigInteger.ONE; data != -1 ; data = fileReader.read() , j++) {


                        sb.append((char)data);

                        progressBar.setProgress((i.add(BigInteger.ONE).doubleValue())/dataLength);

                        if(j >= 1000){
                            j=0;
                            i=i.add(BigInteger.ONE);
                        }

                    }
                    rtBox.replaceText(sb.toString());


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
