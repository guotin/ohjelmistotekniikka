package application.ui;

import application.dao.DatabaseCreatorDao;
import application.dao.PurchaseDao;
import application.dao.UserDao;
import application.domain.Purchase;
import application.domain.PurchaseService;

import java.time.LocalDate;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class AppUi extends Application {

    private PurchaseService purchaseService;

    private Scene loginScene;
    private Scene registerScene;
    private Scene mainScene;

    @Override
    public void init() throws Exception {
        PurchaseDao purchaseDao = new PurchaseDao("foodpurchases");
        UserDao userDao = new UserDao("foodpurchases");
        DatabaseCreatorDao databasecreatorDao = new DatabaseCreatorDao("foodpurchases");
        purchaseService = new PurchaseService(purchaseDao, userDao, databasecreatorDao);

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
        mainOuterLayout.setBottom(mainInnerLayout);
        mainInnerLayout.setAlignment(Pos.BOTTOM_LEFT);
        mainInnerLayout.setVgap(10);
        mainInnerLayout.setHgap(10);
        mainInnerLayout.setPadding(new Insets(20, 20, 20, 20));

        // x-axis of purchase graph
        NumberAxis xAxis = new NumberAxis();
        xAxis.setUpperBound(30);
        xAxis.setLowerBound(0);
        xAxis.setTickLength(1);
        xAxis.setTickUnit(1);
        xAxis.setAnimated(true);
        xAxis.setAutoRanging(false);
        xAxis.setLabel("Timeframe");

        // y-axis of purchase graph
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Money spent in euros");

        // purchase graph
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Food purchases");
        mainOuterLayout.setTop(lineChart);

        Button monthButton = new Button("Purchases this month");
        Button yearButton = new Button("Purchases this year");
        Label sumSpentPeriod = new Label("");
        GridPane buttonPlacement = new GridPane();
        buttonPlacement.add(monthButton, 1, 0);
        buttonPlacement.add(yearButton, 2, 0);
        buttonPlacement.add(sumSpentPeriod, 3, 0);
        buttonPlacement.setHgap(20);
        mainOuterLayout.setCenter(buttonPlacement);

        Button addButton = new Button("Add");
        Button refreshButton = new Button("Refresh total money spent");
        Button logoutButton = new Button("Logout");
        TextField sumTextfield = new TextField();
        DatePicker datePicker = new DatePicker();
        Label addMessage = new Label("");
        Label moneySpent = new Label("Money spent: 0 euros");

        mainInnerLayout.add(new Label("Add a new purchase"), 0, 0);
        mainInnerLayout.add(sumTextfield, 0, 1);
        mainInnerLayout.add(new Label("Purchase sum in euros"), 1, 1);
        mainInnerLayout.add(datePicker, 0, 2);
        mainInnerLayout.add(new Label("Date of purchase"), 1, 2);
        mainInnerLayout.add(addButton, 0, 3);
        mainInnerLayout.add(addMessage, 1, 3);
        mainInnerLayout.add(moneySpent, 0, 5);
        mainInnerLayout.add(refreshButton, 1, 5);
        mainInnerLayout.add(logoutButton, 2, 5);
        mainInnerLayout.setHgrow(addMessage, Priority.ALWAYS);

        mainScene = new Scene(mainOuterLayout, 900, 640);

        // draw data of current month purchases
        monthButton.setOnAction((event) -> {
            xAxis.setLabel("Days of month");
            xAxis.setUpperBound(purchaseService.lastDayOfMonth());
            xAxis.setLowerBound(0);
            List<Purchase> purchaseList = purchaseService.getPurchasesOfCurrentMonth();
            XYChart.Series purchaseData = new XYChart.Series();
            purchaseData.setName("Purchases of current month");
            int yHeight = 20;
            purchaseData.getData().add(new XYChart.Data(0, 0));
            int totalSpent = 0;
            for (Purchase purchase : purchaseList) {
                purchaseData.getData().add(new XYChart.Data(purchase.getDate().getDayOfMonth(), purchase.getSum() + totalSpent));
                if (purchase.getSum() > yHeight) {
                    yHeight = purchase.getSum();
                }
                totalSpent += purchase.getSum();
            }
            sumSpentPeriod.setText("Total money spent this month: " + totalSpent + " euros");
            yAxis.setUpperBound(yHeight + 10);
            lineChart.getData().clear();
            lineChart.getData().add(purchaseData);
        });

        // draw data of current year purchases - not yet functional
        yearButton.setOnAction((event) -> {
            sumSpentPeriod.setText("");
            xAxis.setUpperBound(12);
            xAxis.setLowerBound(0);
            xAxis.setLabel("Months of year");
            lineChart.getData().clear();
        });

        // logout and clear ui
        logoutButton.setOnAction((event) -> {
            sumSpentPeriod.setText("");
            addMessage.setText("");
            xAxis.setLabel("Timeframe");
            lineChart.getData().clear();
            primaryStage.setScene(loginScene);
        });

        //Add a new purchase
        addButton.setOnAction((event) -> {
            LocalDate date = datePicker.getValue();
            String sum = sumTextfield.getText();
            if (date == null || sum.equals("")) {
                addMessage.setText("Please provide input");
            } else {
                int returnValue = purchaseService.createPurchase(sum, date);
                if (returnValue == 0) {
                    addMessage.setText("Sum is not a number");
                } else if (returnValue == 1) {
                    datePicker.setValue(null);
                    sumTextfield.setText("");
                    addMessage.setText("Negative purchases are not allowed");
                } else if (returnValue == -1) {
                    datePicker.setValue(null);
                    sumTextfield.setText("");
                    addMessage.setText("Purchase saving failed, check input and try again");
                } else if (returnValue == 2) {
                    datePicker.setValue(null);
                    sumTextfield.setText("");
                    addMessage.setText("Purchase saved");
                }
            }
        });

        // Refresh total money spent
        refreshButton.setOnAction((event) -> {
            moneySpent.setText("Money spent: " + purchaseService.getMoneySpent() + " euros");
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
