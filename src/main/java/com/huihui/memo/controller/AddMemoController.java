package com.huihui.memo.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.huihui.memo.MemoApplication;
import com.huihui.memo.dao.CurrentUserDao;
import com.huihui.memo.dao.NoteDao;
import com.huihui.memo.dao.UserDao;
import com.huihui.memo.pojo.CurrentUser;
import com.huihui.memo.pojo.Note;
import com.huihui.memo.pojo.User;
import com.huihui.memo.view.MemoView;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@FXMLController
public class AddMemoController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnSubmit;

    @FXML
    private TextArea txtContent;

    @FXML
    private TextField txtTitle;

    private DialogPane dialogPane;

	public void setProperty(DialogPane addPane) {
		this.dialogPane = addPane;
		Button okButton = (Button)dialogPane.lookupButton(ButtonType.OK);
		okButton.setText("提交");	
		
		//设置事件
		okButton.addEventFilter(ActionEvent.ACTION, event->{
	    	String title = txtTitle.getText();
	    	if(title.equals("")) {
				Alert alert = new Alert(Alert.AlertType.WARNING,"标题不能为空！");
		        alert.initOwner(MemoApplication.getStage());
		        alert.showAndWait();
				throw new RuntimeException("标题不能为空！");
	    	}
	    	
	    	//设置备忘的标题和内容
	    	String content = txtContent.getText();
	    	
	    	try {
		    	FileWriter fw = new FileWriter("temp.txt");
		    	fw.write(title+"█"+content);
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	
		});
	}

}
