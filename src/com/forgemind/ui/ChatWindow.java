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

import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class ChatWindow extends Application {
    private VBox chatArea;
    private ChoiceBox<String> modeSelector;
    private CheckBox darkModeToggle;
    private Scene scene;
    private ListView<String> chatList;
    private List<List<HBox>> conversations = new ArrayList<>();
    private int currentChatIndex = 0;

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

        // ---- Sidebar (Chat History) ----
        chatList = new ListView<>();
        chatList.setPrefWidth(150);
        chatList.getItems().add("Chat #1"); // first chat
        conversations.add(new ArrayList<>()); // messages of first chat

        Button newChatButton = new Button("+ New Chat");
        newChatButton.setOnAction(e -> createNewChat());

        VBox sidebar = new VBox(chatList, newChatButton);
        VBox.setVgrow(chatList, Priority.ALWAYS);

        chatList.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() >= 0) {
                switchConversation(newVal.intValue());
            }
        });

        // Main layout
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(scrollPane);
        root.setBottom(inputBox);
        root.setLeft(sidebar);

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

    private void handleUserMessage(TextField inputField) {
        String message = inputField.getText();
        if (message == null || message.isBlank()) return;

        // addMessage("You", message, Pos.CENTER_RIGHT, Color.web("#1976d2"), Color.WHITE, "user");
        HBox userMsg = createMessageBubble("You", message, Pos.CENTER_RIGHT, "user");
        chatArea.getChildren().add(userMsg);
        conversations.get(currentChatIndex).add(userMsg);

        inputField.clear();

        // Simulate AI response depending on mode
        new Thread(() -> {
            try {
                Thread.sleep(800); // simulate thinking
            } catch (InterruptedException ignored) {}

            String mode = modeSelector.getValue();
            String response = switch (mode) {
                case "Temporary Chat" -> response = "Temporary reply to: " + message;
                case "Web Search Chat" -> response = "Web search results for: " + message + " (stub)";
                default -> response = "I heard you say '" + message + "'";
            };

            HBox aiMsg = createMessageBubble("AI", response, Pos.CENTER_LEFT, "ai");
            Platform.runLater(() -> {
                chatArea.getChildren().add(aiMsg);
                conversations.get(currentChatIndex).add(aiMsg);
            });
            // Platform.runLater(() ->
            //     addMessage("AI", response, Pos.CENTER_LEFT, Color.LIGHTGRAY, Color.BLACK, "ai")
            // );
        }).start();
    }

    private HBox createMessageBubble(String sender, String text, Pos alignment, String type) {
        Label msgLabel = new Label(text);
        msgLabel.setWrapText(true);
        msgLabel.getStyleClass().add(type.equals("user") ? "user-bubble" : "ai-bubble");

        Image avatarImg;
        if (type.equals("user")) {
            avatarImg = new Image("https://img.icons8.com/color/48/user.png", 32, 32, true, true);
        } else {
            avatarImg = new Image("https://img.icons8.com/fluency/48/robot-2.png", 32, 32, true, true);
        }
        ImageView avatar = new ImageView(avatarImg);

        HBox msgBox;
        if (type.equals("user")) {
            msgBox = new HBox(msgLabel, avatar);
            msgBox.setAlignment(alignment);
        } else {
            msgBox = new HBox(avatar, msgLabel);
            msgBox.setAlignment(alignment);
        }

        msgBox.setSpacing(8);
        return msgBox;
    }

    private void createNewChat() {
        int newIndex = conversations.size() + 1;
        chatList.getItems().add("Chat #" + newIndex);
        conversations.add(new ArrayList<>());
        chatList.getSelectionModel().selectLast();
    }

    private void switchConversation(int index) {
        chatArea.getChildren().clear();
        chatArea.getChildren().addAll(conversations.get(index));
        currentChatIndex = index;
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
