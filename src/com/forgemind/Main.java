package com.forgemind;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage)  {
        Label label = new Label("Forge Mind - Project Plasma");
        Scene scene = new Scene(label, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Forge Mind");
        stage.show();
    }

    public static void main(String args[]) {
        launch();
    }
}