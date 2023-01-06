package com.huihui.memo.controller;


import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import com.huihui.memo.MemoApplication;
import com.huihui.memo.dao.UserDao;
import com.huihui.memo.pojo.User;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@FXMLController
public class RegisterController implements Initializable{

	private Stage primaryStage;
	@FXML
	public Button registerButton;
	@FXML
	public TextField nameField;
	@FXML
	public TextField emailField;
	@FXML
	public PasswordField pwdField;
	
	@Autowired
	public UserDao userDao;
	
	@Override
	public void initialize(URL location,ResourceBundle resources) {
		primaryStage = MemoApplication.getStage();
	}
	@FXML
	public void register(ActionEvent event)throws IOException {
		
		//获取输入的用户名
		String username = nameField.getText();
		System.out.println(username);
		//获取输入的电子邮箱
		String email = emailField.getText();
		System.out.println(email);
		//查找用户，判断此用户名是否已经被使用
		User userInDb_1 = userDao.findbyUsername(username);
		
		//用户已经被使用，弹出警告提示
		if(!ObjectUtils.isEmpty(userInDb_1)) {
			 Alert alert = new Alert(Alert.AlertType.WARNING,"用户名已经被注册，请更换用户名。");
	         alert.initOwner(primaryStage);
	         alert.showAndWait();
			 throw new RuntimeException("当前用户名已被注册！");
		}
		
		//查找用户，判断此电子邮箱是否已经被使用
		User userInDb_2 = userDao.findbyEmail(email);
		//邮箱已经被使用，弹出警告提示
		if(!ObjectUtils.isEmpty(userInDb_2)) {
			Alert alert = new Alert(Alert.AlertType.WARNING,"邮箱已经被注册，请更换邮箱。");
	         alert.initOwner(primaryStage);
	         alert.showAndWait();			
			throw new RuntimeException("当前邮箱已被注册！");
		}

		//用户名和邮箱都未被使用，把新用户的信息保存到数据库
		String password = pwdField.getText();
		//给明文的密码加密，相同字符串多次使用md5加密，加密的结果始终是相同的
		String codedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
		User user = new User();
		user.setPassword(codedPassword);
		user.setUsername(username);
		user.setEmail(email);
		user.setEmail(email);
		userDao.save(user);
		Alert alert = new Alert(Alert.AlertType.INFORMATION,"注册成功!");
        alert.initOwner(primaryStage);
        alert.showAndWait();	
	}
}
