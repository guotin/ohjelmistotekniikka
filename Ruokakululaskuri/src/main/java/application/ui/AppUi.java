package application.ui;

import application.domain.PurchaseService;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AppUi extends Application {

    private PurchaseService purchaseService;

    private Scene loginScene;
    private Scene registerScene;
    private Scene mainScene;

    @Override
    public void init() throws Exception {

        purchaseService = new PurchaseService();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // login window
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        BorderPane loginOuterLayout = new BorderPane();
        GridPane loginInnerLayout = new GridPane();
        loginOuterLayout.setCenter(loginInnerLayout);
        loginInnerLayout.setAlignment(Pos.CENTER);
        loginInnerLayout.setVgap(10);
        loginInnerLayout.setHgap(10);
        loginInnerLayout.setPadding(new Insets(20, 20, 20, 20));

        loginInnerLayout.add(new Label("Username"), 0, 0);
        loginInnerLayout.add(usernameField, 0, 1);
        loginInnerLayout.add(new Label("Password"), 0, 2);
        loginInnerLayout.add(passwordField, 0, 3);
        loginInnerLayout.add(loginButton, 0, 4);
        loginInnerLayout.add(registerButton, 1, 4);

        loginScene = new Scene(loginOuterLayout, 900, 640);

        loginButton.setOnAction((event) -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (purchaseService.loginUser(username, password)) {
                usernameField.setText("");
                passwordField.setText("");
                primaryStage.setScene(mainScene);
            }

        });

        registerButton.setOnAction((event) -> {
            usernameField.setText("");
            passwordField.setText("");
            primaryStage.setScene(registerScene);
        });

        // register window
        BorderPane registerOuterLayout = new BorderPane();
        GridPane registerInnerLayout = new GridPane();
        registerOuterLayout.setCenter(registerInnerLayout);
        registerInnerLayout.setAlignment(Pos.CENTER);
        registerInnerLayout.setVgap(10);
        registerInnerLayout.setHgap(10);
        registerInnerLayout.setPadding(new Insets(20, 20, 20, 20));

        Button createUserButton = new Button("Create user");
        Button backButton = new Button("Back to login screen");
        Label registerMessage = new Label("");
        TextField createUsernameField = new TextField();
        PasswordField createPasswordField = new PasswordField();

        registerInnerLayout.add(new Label("Username"), 0, 0);
        registerInnerLayout.add(createUsernameField, 0, 1);
        registerInnerLayout.add(new Label("Password"), 0, 2);
        registerInnerLayout.add(createPasswordField, 0, 3);
        registerInnerLayout.add(createUserButton, 0, 4);
        registerInnerLayout.add(backButton, 1, 4);
        registerInnerLayout.add(registerMessage, 0, 5);

        registerScene = new Scene(registerOuterLayout, 900, 640);

        backButton.setOnAction((event) -> {
            registerMessage.setText("");
            createUsernameField.setText("");
            createPasswordField.setText("");
            primaryStage.setScene(loginScene);
        });

        createUserButton.setOnAction((event) -> {

            String username = createUsernameField.getText();
            String password = createPasswordField.getText();
            if (!username.equals("") && !password.equals("")) {
                if (!purchaseService.usernameExists(username)) {
                    if (purchaseService.createUser(username, password)) {
                        registerMessage.setText("New user added to database");
                        createUsernameField.setText("");
                        createPasswordField.setText("");
                    } else {
                        registerMessage.setText("Registration failed, try again");
                        createUsernameField.setText("");
                        createPasswordField.setText("");
                    }
                } else {
                    registerMessage.setText("Username already taken");
                    createUsernameField.setText("");
                    createPasswordField.setText("");
                }
            } else {
                registerMessage.setText("Please enter values");
            }

        });

        // main window 
        BorderPane mainOuterLayout = new BorderPane();
        GridPane mainInnerLayout = new GridPane();
        mainOuterLayout.setCenter(mainInnerLayout);
        mainInnerLayout.setAlignment(Pos.CENTER);
        mainInnerLayout.setVgap(10);
        mainInnerLayout.setHgap(10);
        mainInnerLayout.setPadding(new Insets(20, 20, 20, 20));

        Button addButton = new Button("Add");
        Button refreshButton = new Button("Refresh total money spent");
        Button logoutButton = new Button("Logout");
        TextField sumTextfield = new TextField();
        DatePicker datePicker = new DatePicker();
        Label addMessage = new Label("");
        Label moneySpent = new Label("Money spent: 0");

        mainInnerLayout.add(new Label("Add a new purchase"), 0, 0);
        mainInnerLayout.add(sumTextfield, 0, 1);
        mainInnerLayout.add(new Label("Purchase sum in euros"), 1, 1);
        mainInnerLayout.add(datePicker, 0, 2);
        mainInnerLayout.add(new Label("Date of purchase"), 1, 2);
        mainInnerLayout.add(addButton, 0, 3);
        mainInnerLayout.add(logoutButton, 1, 3);
        mainInnerLayout.add(addMessage, 0, 4);
        mainInnerLayout.add(moneySpent, 0, 5);
        mainInnerLayout.add(refreshButton, 1, 5);

        mainScene = new Scene(mainOuterLayout, 900, 640);

        logoutButton.setOnAction((event) -> {
            primaryStage.setScene(loginScene);
        });

        //Add a new purchase
        addButton.setOnAction((event) -> {
            LocalDate date = datePicker.getValue();
            String sum = sumTextfield.getText();
            try {
                Integer.parseInt(sum);
                if (datePicker.getValue() != null) {
                    if (purchaseService.createPurchase(sum, date)) {
                        datePicker.setValue(null);
                        sumTextfield.setText("");
                        addMessage.setText("Purchase saved");

                    } else {
                        datePicker.setValue(null);
                        sumTextfield.setText("");
                        addMessage.setText("Purchase saving failed, check input and try again");
                    }
                } else {
                    addMessage.setText("Please enter a date");
                }

            } catch (NumberFormatException e) {
                addMessage.setText("Sum is not a number");
            }

        });

        // Refresh total money spent
        refreshButton.setOnAction((event) -> {
            moneySpent.setText("Money spent: " + purchaseService.getMoneySpent());
        });

        //Initial setup
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Food purchase tracker");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
