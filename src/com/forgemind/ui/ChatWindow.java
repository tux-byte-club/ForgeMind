package com.forgemind.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class ChatWindow extends Application {

    private void addButtonEffects(Button button) {
        // Scale on hover
        button.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });
        button.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        // Fade on press
        button.setOnMousePressed(e -> {
            FadeTransition ft = new FadeTransition(Duration.millis(100), button);
            ft.setToValue(0.7);
            ft.play();
        });
        button.setOnMouseReleased(e -> {
            FadeTransition ft = new FadeTransition(Duration.millis(100), button);
            ft.setToValue(1.0);
            ft.play();
        });
    }

    private void addChoiceBoxEffects(ChoiceBox<?> choiceBox) {
        choiceBox.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(120), choiceBox);
            st.setToX(1.03);
            st.setToY(1.03);
            st.play();
        });
        choiceBox.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(120), choiceBox);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }


    private VBox chatArea;
    private ChoiceBox<String> modeSelector;
    private CheckBox darkModeToggle;
    private Scene scene;

    @Override
    public void start(Stage stage) {
        // Chat messages area (scrollable)
        chatArea = new VBox(10);
        chatArea.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(chatArea);
        scrollPane.setFitToWidth(true);

        // Input area
        TextField inputField = new TextField();
        inputField.setPromptText("Type your message...");
        Button sendButton = new Button("Send");
        addButtonEffects(sendButton);

        HBox inputBox = new HBox(10, inputField, sendButton);
        inputBox.setPadding(new Insets(10));
        HBox.setHgrow(inputField, Priority.ALWAYS);

        // Mode selector (top)
        modeSelector = new ChoiceBox<>();
        modeSelector.getItems().addAll("Normal Chat", "Temporary Chat", "Web Search Chat");
        modeSelector.setValue("Normal Chat"); // default
        addChoiceBoxEffects(modeSelector);

        // Dark mode toggle
        darkModeToggle = new CheckBox("Dark Mode");
        darkModeToggle.setOnAction(e -> applyTheme());

        HBox topBar = new HBox(new Label("Mode: "), modeSelector, darkModeToggle);
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(10);

        // Main layout
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(scrollPane);
        root.setBottom(inputBox);

        scene = new Scene(root, 600, 450);
        applyTheme(); // apply default theme

        stage.setTitle("Forge Mind — Project Plasma");
        Image icon = new Image(getClass().getResourceAsStream("images/icon.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

        // Event: send message
        sendButton.setOnAction(e -> handleUserMessage(inputField));
        inputField.setOnAction(e -> handleUserMessage(inputField));
    }

    private void handleUserMessage(TextField inputField) {
        String message = inputField.getText();
        if (message == null || message.isBlank()) return;

        addMessage("You", message, Pos.CENTER_RIGHT, Color.web("#1976d2"), Color.WHITE, "user");

        inputField.clear();

        // Simulate AI response depending on mode
        new Thread(() -> {
            try {
                Thread.sleep(800); // simulate thinking
            } catch (InterruptedException ignored) {}

            String mode = modeSelector.getValue();
            String response;

            switch (mode) {
                case "Temporary Chat" -> response = "Temporary reply to: " + message;
                case "Web Search Chat" -> response = "Web search results for: " + message + " (stub)";
                default -> response = "I heard you say '" + message + "'";
            }

            Platform.runLater(() ->
                addMessage("AI", response, Pos.CENTER_LEFT, Color.LIGHTGRAY, Color.BLACK, "ai")
            );
        }).start();
    }

    private void addMessage(String sender, String text, Pos alignment, Color bgColor, Color textColor, String type) {
        Label msgLabel = new Label(text);
        msgLabel.setWrapText(true);

        msgLabel.setPadding(new Insets(8, 12, 8, 12));
        msgLabel.getStyleClass().add(type.equals("user") ? "user-bubble" : "ai-bubble");

        // Avatar (placeholder icons)
        Image avatarImg;
        if (type.equals("user")) {
            avatarImg = new Image("https://img.icons8.com/color/48/user.png", 32, 32, true, true);
        } else {
            // avatarImg = new Image("https://img.icons8.com/ios-filled/50/robot.png", 32, 32, true, true);
            avatarImg = new Image("https://img.icons8.com/fluency/48/robot-2.png", 32, 32, true, true);

        }
        ImageView avatar = new ImageView(avatarImg);

        HBox msgBox;
        if (type.equals("user")) {
            msgBox = new HBox(msgLabel, avatar); // avatar on right
            msgBox.setAlignment(alignment);
        } else {
            msgBox = new HBox(avatar, msgLabel); // avatar on left
            msgBox.setAlignment(alignment);
        }

        msgBox.setSpacing(8);
        chatArea.getChildren().add(msgBox);
    }

    private void applyTheme() {
        scene.getStylesheets().clear();
        if (darkModeToggle.isSelected()) {
            scene.getStylesheets().add(getClass().getResource("theme/dark-theme.css").toExternalForm());
        } else {
            scene.getStylesheets().add(getClass().getResource("theme/light-theme.css").toExternalForm());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
