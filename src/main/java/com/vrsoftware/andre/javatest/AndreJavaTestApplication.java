package com.vrsoftware.andre.javatest;

import com.vrsoftware.andre.javatest.view.MainView;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;

@SpringBootApplication
public class AndreJavaTestApplication implements CommandLineRunner {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AndreJavaTestApplication.class)
				.web(WebApplicationType.NONE)
				.headless(false)
				.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		MainView mainView = new MainView();
		mainView.pack();
		mainView.setLocationRelativeTo(null);
		mainView.setVisible(true);
	}
}
