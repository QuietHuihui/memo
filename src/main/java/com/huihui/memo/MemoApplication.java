package com.huihui.memo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.huihui.memo.view.RegisterView;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;

@SpringBootApplication
public class MemoApplication extends AbstractJavaFxApplicationSupport {

	//把注册视图作为主视图
    public static void main(String[] args) {
        launch(MemoApplication.class, RegisterView.class, args);
    }
}
