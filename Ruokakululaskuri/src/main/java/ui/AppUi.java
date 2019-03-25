
package ui;
import domain.PurchaseService;
import domain.Purchase;
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
    
    @Override
    public void init() throws Exception {
        purchaseService = new PurchaseService();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button = new Button("Add");
        TextField sumTextfield = new TextField();
        DatePicker datePicker = new DatePicker();

        GridPane innerLayout = new GridPane ();
        innerLayout.setPrefSize(640, 480);
        innerLayout.setAlignment(Pos.CENTER);
        
        innerLayout.add(new Label("Add a new purchase"), 0, 0);
        innerLayout.add(sumTextfield, 0, 1);
        innerLayout.add(datePicker, 0, 2);
        innerLayout.add(button, 0, 3);
        
        innerLayout.setVgap(10);
        innerLayout.setHgap(10);
        innerLayout.setPadding(new Insets(20,20,20,20));
         
        BorderPane mainLayout = new BorderPane();   
        mainLayout.setCenter(innerLayout);
        
        button.setOnAction((event) -> {
            LocalDate date = datePicker.getValue();
            Double sum = Double.parseDouble(sumTextfield.getText());
            Purchase purchase = new Purchase(sum, date);
            datePicker.setValue(null);
            sumTextfield.setText("");
        });
        
        
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
