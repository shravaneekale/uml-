/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package POS;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.util.StringConverter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;



public class App extends Application {

    /*
        ---- DO NOT TOUCH THIS APP ----    
    */
    private static final int APP_WIDTH = 450;
    private static final int APP_HEIGHT = 505;
    private static List<Product> products = new ArrayList<>();
    private static List<PurchaseItem> purchases = new ArrayList<>();
    
    private static void addToComboBox(ComboBox<Product> box) {
        for(Product p : products) {
            box.getItems().add(p);
        }
    }
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Ted's Bikes");
        stage.setResizable(false);
        TableView<PurchaseItem> table = new TableView<>();
        TableColumn<PurchaseItem, String> productColumn = new TableColumn<>("Item Name");
        TableColumn<PurchaseItem, String> qtyColumn = new TableColumn<>("Qty");
        TableColumn<PurchaseItem, String> ppuColumn = new TableColumn<>("Price");
        TableColumn<PurchaseItem, String> totalColumn = new TableColumn<>("Total");
        productColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        ppuColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        ComboBox<Product> combobox = new ComboBox<>();
        addToComboBox(combobox);
        combobox.getSelectionModel().selectFirst();
        combobox.setConverter(new StringConverter<Product>() {
            public Product fromString(String s) { return null; }
            public String toString(Product p) {
                if(p != null) {
                    return p.name();
                } else {
                    return ""; //Awful hack here!
                }
            } 
        });
        table.getColumns().add(productColumn);
        table.getColumns().add(qtyColumn);
        table.getColumns().add(ppuColumn);
        table.getColumns().add(totalColumn);
        productColumn.setPrefWidth(150);
        
        Label qtyLabel = new Label("Qty");
        TextField qtyInput = new TextField();
        qtyLabel.setPrefWidth(APP_WIDTH/4);
        qtyInput.setPrefWidth((APP_WIDTH/4)*3);
        qtyInput.setText("1");
        
        VBox vbox = new VBox(table);
        vbox.setPrefWidth(APP_WIDTH);
        
        Button addProduct = new Button();
        addProduct.setText("Add Product");
        addProduct.setPrefWidth(APP_WIDTH);
        
        
        Button orderBtn = new Button();
        orderBtn.setText("Order");
        orderBtn.setPrefWidth(APP_WIDTH);

        HBox productBox = new HBox(combobox);
        productBox.setPrefWidth(APP_WIDTH);
        combobox.setPrefWidth(APP_WIDTH);
        
        
        
        addProduct.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Product selectedProduct = combobox.getValue();
                int qty = Integer.parseInt(qtyInput.getText());
                PurchaseItem it = selectedProduct.purchase(qty);
                purchases.add(it);
                table.getItems().add(it);
            }
        });
        
        Alert notify = new Alert(AlertType.NONE);
        notify.setHeaderText("Order has been created!");
        //notify.setContentText("Order has been created!");
        orderBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Creates an order
                if(createOrder()) {
                    notify.setAlertType(AlertType.INFORMATION);
                    notify.showAndWait();
                } else {
                    notify.setHeaderText("Order Failed!");
                    notify.setAlertType(AlertType.ERROR);
                    notify.showAndWait();
                }
            }
        });
        
        FlowPane root = new FlowPane();
        root.setAlignment(Pos.TOP_CENTER);
        
        root.getChildren().add(vbox);
        root.getChildren().add(productBox);
        root.getChildren().add(qtyLabel);
        root.getChildren().add(qtyInput);
        root.getChildren().add(addProduct);
        root.getChildren().add(orderBtn);
        
        stage.setScene(new Scene(root, APP_WIDTH, APP_HEIGHT));
        stage.show();
    }
    
    public static boolean createOrder() {
        //Product a CSV
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
	        Date date = new Date();
            File file = new File("order-"+dateFormat.format(date)+".csv");
            PrintWriter writer = new PrintWriter(file);
            writer.println("name,qty,price,total");
            for(PurchaseItem item : purchases) {
                writer.print(item.getItem() + ",");
                writer.print(item.getQty() + ",");
                writer.print(item.getPrice() + ",");
                writer.println(item.getTotal());
            }
            writer.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return true;
    }

    /**
     *   @param Product p, adds a product to be purchasable
     */
    public static void registerProduct(Product p) {
        products.add(p);
    }

    /*
        ---- DO NOT MODIFY ANYTHING ABOVE THIS LINE ----    
    */

    public static void run(String[] args) {
        launch(args);
    }
    
    public static void main(String[] args) {
        //To add products that can be purchased, 
        //use register product
        //registerProduct(new TestProduct());
        run(args);
    }
}
