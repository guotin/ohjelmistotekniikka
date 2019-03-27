package ui;

import domain.PurchaseService;
import domain.Purchase;
import dao.PurchaseDao;

import java.time.LocalDate;
import java.util.Date;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AppUi extends Application {

    private PurchaseService purchaseService;
    private PurchaseDao purchaseDao;

    @Override
    public void init() throws Exception {

        purchaseDao = new PurchaseDao();
        purchaseService = new PurchaseService(purchaseDao);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button addButton = new Button("Add");
        Button refreshButton = new Button("Refresh total money spent");
        TextField sumTextfield = new TextField();
        DatePicker datePicker = new DatePicker();
        Label addMessage = new Label("");
        Label moneySpent = new Label("Money spent: 0");
        

        GridPane innerLayout = new GridPane();
        innerLayout.setPrefSize(900, 640);
        innerLayout.setAlignment(Pos.CENTER);
        
        innerLayout.add(new Label("Add a new purchase"), 0, 0);
        innerLayout.add(sumTextfield, 0, 1);
        innerLayout.add(new Label("Purchase sum in euros"), 1, 1);
        innerLayout.add(datePicker, 0, 2);
        innerLayout.add(new Label("Date of purchase"), 1, 2);
        innerLayout.add(addButton, 0, 3);
        innerLayout.add(addMessage, 0, 4);
        innerLayout.add(moneySpent, 0, 5);
        innerLayout.add(refreshButton, 1, 5);

        innerLayout.setVgap(10);
        innerLayout.setHgap(10);
        innerLayout.setPadding(new Insets(20, 20, 20, 20));

        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(innerLayout);
 
          
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
        refreshButton.setOnAction((event) -> {
            moneySpent.setText("Money spent: " + purchaseService.getMoneySpent());

        });
        
        

        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
