package com.huihui.memo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.huihui.memo.view.MainView;
import com.huihui.memo.view.RegisterView;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;

@SpringBootApplication
public class MemoApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(MemoApplication.class, RegisterView.class, args);
    }
}
