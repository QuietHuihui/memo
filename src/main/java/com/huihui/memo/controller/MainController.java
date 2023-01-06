package com.huihui.memo.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.huihui.memo.MemoApplication;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

@FXMLController
public class MainController implements Initializable {
    private Stage primaryStage;
    @FXML // 可忽略
    public Button button;// 注意必须为public修饰符
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        primaryStage = MemoApplication.getStage();
        button.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "hello world");
            alert.initOwner(primaryStage);
            alert.showAndWait();
        });
    }

}