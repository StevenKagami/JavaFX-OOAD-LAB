package view;

import java.sql.Date;
import controller.TransactionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.TransactionHeader;

public class ViewTransactionHeader {
	TableView<TransactionHeader> thTable;
	TableColumn<TransactionHeader, Integer> thIdColumn, thStaffIdColumn;
	TableColumn<TransactionHeader, String> thStaffnameColumn;
	TableColumn<TransactionHeader, Date> thDateColumn;
	ObservableList<TransactionHeader> thData;
	TransactionController thController ;
	Label thLabel, thNoteLabel;
	VBox thBox;
	
	private int customerId;
	
	private void init() {
		thController = new TransactionController();
		thTable = new TableView<>();
		thIdColumn = new TableColumn<TransactionHeader, Integer>("Transacion ID");
		thStaffIdColumn = new TableColumn<TransactionHeader, Integer>("Staff ID");
		thStaffnameColumn = new TableColumn<TransactionHeader, String>("Staff Name");
		thDateColumn = new TableColumn<TransactionHeader, Date>("Transacion Date");
	
		thLabel = new Label("Transaction History");
		thNoteLabel = new Label("Click transaction to see the detail!");   
		thBox = new VBox(10);
	}
	
	@SuppressWarnings("unchecked")
	private void layout(BorderPane bp, String role) {
		thIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		thStaffIdColumn.setCellValueFactory(new PropertyValueFactory<>("staffId"));
		thStaffnameColumn.setCellValueFactory(new PropertyValueFactory<>("staffName"));
		thDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
		
		if(role.equals("Admin")) {
			thData = FXCollections.observableArrayList(thController.getAllTransaction()); 
		}else {
			thData = FXCollections.observableArrayList(thController.getAllTransactionById(customerId)); 
		}
		thTable.getColumns().addAll(thIdColumn,thStaffIdColumn,thStaffnameColumn,thDateColumn);
		thTable.setItems(thData);
		
		VBox.setMargin(thLabel,new Insets(10));
		thBox.setAlignment(Pos.TOP_CENTER);
		thBox.getChildren().addAll(thLabel,thTable,thNoteLabel);
		
		bp.setCenter(thBox);
	}
	
	private void eventControl(int userId, Stage stage, BorderPane bp, String role) {
		thTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	int thId =	newSelection.getTransactionId();
            	thController.showTransactionDetail(thId, userId, stage, bp, role);
            }
        });
	}
	
	public void showTransactionHeader(Stage stage, BorderPane bp, String role) {
		init();
		layout(bp,role);
		eventControl(customerId,stage, bp, role);
	}
	
	public ViewTransactionHeader(int userId) {
		this.customerId = userId;
	}

}
