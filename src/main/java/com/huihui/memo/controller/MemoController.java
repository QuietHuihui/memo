package com.huihui.memo.controller;
import com.huihui.memo.MemoApplication;
import com.huihui.memo.view.AddMemoView;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

@FXMLController
public class MemoController {

    @FXML
    private Button btnAddMemo;

    @FXML
    private Button btnAllMemo;

    @FXML
    private Button btnFinishedMemo;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUnfinishedMemo;

    @FXML
    private TableColumn<?, ?> numCol;

    @FXML
    private TableColumn<?, ?> operationCol;

    @FXML
    private TableColumn<?, ?> titleCol;

    @FXML
    private TextField txtSearch;

    @FXML
    void AddMemo(ActionEvent event) {
    	MemoApplication.showView(AddMemoView.class);
    	MemoApplication.getStage().sizeToScene();
    }

    @FXML
    void Logout(ActionEvent event) {

    }

    @FXML
    void Search(ActionEvent event) {

    }

    @FXML
    void getAllMemo(ActionEvent event) {

    }

    @FXML
    void getFinishedMemo(ActionEvent event) {

    }

    @FXML
    void getUnfinishedMemo(ActionEvent event) {

    }

}

