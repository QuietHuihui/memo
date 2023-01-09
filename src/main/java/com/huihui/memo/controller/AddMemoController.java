package com.huihui.memo.controller;

import java.io.FileWriter;
import java.io.IOException;
import com.huihui.memo.MemoApplication;
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
