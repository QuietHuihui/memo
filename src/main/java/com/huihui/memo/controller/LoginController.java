package com.huihui.memo.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.UserDataHandler;

import com.huihui.memo.MemoApplication;
import com.huihui.memo.dao.CurrentUserDao;
import com.huihui.memo.dao.UserDao;
import com.huihui.memo.pojo.CurrentUser;
import com.huihui.memo.pojo.User;
import com.huihui.memo.view.LoginView;
import com.huihui.memo.view.MemoView;
import com.huihui.memo.view.RegisterView;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@FXMLController
public class LoginController{
	
	@FXML
	public TextField nameField;
	
	@FXML
	public PasswordField pwdField;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	CurrentUserDao currentUserDao;
	
	public LoginController(){
		MemoApplication.getStage().setTitle("备忘录");
		MemoApplication.getStage().setResizable(false);
	}
	
	public void show() {
		//把应用程序的视图进行切换
		MemoApplication.showView(LoginView.class);
		
		//获取应用程序的当前stage
		//MemoApplication.getStage()
		//切换应用程序的视图
		//MemoApplication.showView(LoginView.class);
	}
	
	@FXML
	public void login(ActionEvent event)throws IOException{
		String username = nameField.getText();
		User user = userDao.findbyUsername(username);
		//判断用户名是否存在
		if(ObjectUtils.isEmpty(user)) {
			Alert alert = new Alert(Alert.AlertType.WARNING,"用户名不存在！");
	        alert.initOwner(MemoApplication.getStage());
	        alert.showAndWait();
			throw new RuntimeException("用户名不存在！");
		}
		String password = pwdField.getText();
		//将用户输入的密码加密，然后再将其与数据库中那个加密过的密码进行比较
		String passwordSecret = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
		//比较密码
		if(!user.getPassword().equals(passwordSecret)) {
			Alert alert = new Alert(Alert.AlertType.WARNING,"密码错误！");
	        alert.initOwner(MemoApplication.getStage());
	        alert.showAndWait();
			throw new RuntimeException("密码错误！");
		}
		
		//删除之前的用户
		currentUserDao.deleteAll();
		//把登录用户作为当前用户
		CurrentUser currentUser = new CurrentUser();
		currentUser.setUser(user);
		//把登录用户存到当前用户数据库中
		currentUserDao.save(currentUser);
		
		 Alert alert = new Alert(Alert.AlertType.INFORMATION,"登录成功！");
         alert.initOwner(MemoApplication.getStage());
         alert.showAndWait();
         MemoApplication.showView(MemoView.class);
         MemoApplication.getStage().sizeToScene();
	}
	
	@FXML
	public void register(ActionEvent event)throws IOException{
		//显示注册页面
		MemoApplication.showView(RegisterView.class);
		//调整为适当大小
		MemoApplication.getStage().sizeToScene();
	}
}
