package com.huihui.memo.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
    
    @Autowired
    NoteDao noteDao;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    CurrentUserDao currentUserDao;

    @FXML
    void back(ActionEvent event) {
		MemoApplication.showView(MemoView.class);
		//调整为适当大小
		MemoApplication.getStage().sizeToScene();
    }

    @FXML
    void submit(ActionEvent event) {
    	String title = txtTitle.getText();
    	if(title.equals("")) {
			Alert alert = new Alert(Alert.AlertType.WARNING,"标题不能为空！");
	        alert.initOwner(MemoApplication.getStage());
	        alert.showAndWait();
			throw new RuntimeException("标题不能为空！");
    	}
    	
    	//设置备忘的标题和内容
    	String content = txtContent.getText();
    	Note note = new Note();
    	note.setTitle(title);
    	note.setContent(content);
    	
		//获取创建记录的时间
	    Calendar calendar= Calendar.getInstance();
	    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
	    java.sql.Date createdDate = java.sql.Date.valueOf(dateFormat.format(calendar.getTime()));
    	
	    note.setCreatedDate(createdDate);
	    
	    //设置备忘状态
	    note.setStatus("unfinished");
	    
	    //获取当前用户，设置此条备忘的主人
    	List<CurrentUser> currentUser = currentUserDao.findAll();
    	User user = currentUser.get(0).getUser();
    	note.setUser(user);
    	
    	//把备忘保存到数据库中
    	noteDao.save(note);
		Alert alert = new Alert(Alert.AlertType.INFORMATION,"备忘已保存。");
        alert.initOwner(MemoApplication.getStage());
        alert.showAndWait();
        
    }

}
