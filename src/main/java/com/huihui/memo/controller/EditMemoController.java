package com.huihui.memo.controller;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.huihui.memo.MemoApplication;
import com.huihui.memo.dao.NoteDao;
import com.huihui.memo.pojo.Note;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditMemoController{

    @Autowired
    NoteDao noteDao;
	
    private DialogPane dialogPane;
    
    @FXML
    private Button btnEditSubmit;

    @FXML
    private TextArea txtEditContent;

    @FXML
    private TextField txtEditTitle;
    
    @FXML
    private ChoiceBox<String> choiceBoxStatus;
    
    //状态选择ChoiceBox下的元素
    private ObservableList<String> statusList = FXCollections.observableArrayList();
    
    Note note;
    

    @FXML
    void EditSubmit(ActionEvent event) throws IOException {
    	String title=txtEditTitle.getText();
    	String content = txtEditContent.getText();
    	String status = choiceBoxStatus.getSelectionModel().getSelectedItem();
    	
    	
    	note.setTitle(title);
    	note.setContent(content);
    	note.setStatus(status);
    	
    	//把编辑内容输出到临时文件中
    	FileWriter fw = new FileWriter("temp.txt");
    	fw.write(title+"█"+content+"█"+status);
    	fw.close();
    }
    
    void setNote(Note note,DialogPane dialogPane) {
    	this.dialogPane = dialogPane;
    	this.note=note;
    	txtEditTitle.setText(note.getTitle());
    	txtEditContent.setText(note.getContent());
    	
		statusList.add("未完成");
		statusList.add("已完成");	
		choiceBoxStatus.setItems(statusList);
		choiceBoxStatus.setValue(note.getStatus());
		
		Button okButton = (Button)dialogPane.lookupButton(ButtonType.OK);
		okButton.addEventFilter(ActionEvent.ACTION, event->{
	    	String title=txtEditTitle.getText();
	    	String content = txtEditContent.getText();
	    	String status = choiceBoxStatus.getSelectionModel().getSelectedItem();
	    	
	    	
	    	note.setTitle(title);
	    	note.setContent(content);
	    	note.setStatus(status);
	    	
	    	//把编辑内容输出到临时文件中
	    	try {
		    	FileWriter fw = new FileWriter("temp.txt");
		    	fw.write(title+"█"+content+"█"+status);
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println(dialogPane);
    }
    
    public EditMemoController() {

    }
}
