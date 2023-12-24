package view;

import java.sql.Date;

import controller.TransactionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.TransactionDetail;

public class ViewTransactionDetail {

	TableView<TransactionDetail> tdTable;
	TableColumn<TransactionDetail, Integer> tdPcIdColumn;
	TableColumn<TransactionDetail, String> tdCustomerName;
	TableColumn<TransactionDetail, Date> tdDateColumn;
	ObservableList<TransactionDetail> tdData;
	TransactionController tdController ;
	Label tdLabel,tdIdLabel, tdNoteLabel;
	VBox tdBox;
	Hyperlink back;
	private int thId, userId;
	private String role;
	private void init() {
		tdController = new TransactionController();
		tdTable = new TableView<>();
		tdPcIdColumn = new TableColumn<TransactionDetail, Integer>("PC ID");
		tdCustomerName = new TableColumn<TransactionDetail, String>("Customer Name");
		tdDateColumn = new TableColumn<TransactionDetail, Date>("Booked Time");
		tdLabel = new Label("Transaction Detail");
		tdIdLabel = new Label("Transaction ID : " + thId);
		tdBox = new VBox(10);
		back = new Hyperlink("Back");
	}

	@SuppressWarnings("unchecked")
	private void layout(BorderPane bp) {
		tdPcIdColumn.setCellValueFactory(new PropertyValueFactory<>("pcId"));
		tdCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		tdDateColumn.setCellValueFactory(new PropertyValueFactory<>("bookedTime"));
		
		if(role.equals("Admin")) {
			tdData = FXCollections.observableArrayList(tdController.getAllTransactionDetail(thId));
			
		}else {
			tdData = FXCollections.observableArrayList(tdController.getAllTransactionDetailByName(thId,userId));
		}
		
		tdTable.getColumns().addAll(tdPcIdColumn,tdCustomerName,tdDateColumn);
		tdTable.setItems(tdData);
		
		VBox.setMargin(tdLabel,new Insets(10));
		tdBox.setAlignment(Pos.TOP_CENTER);
		tdBox.getChildren().addAll(tdLabel,tdIdLabel, tdTable,back);
		
		bp.setCenter(tdBox);
	}
	
	private void eventControl(int userId, Stage stage, BorderPane bp, String role) {
		back.setOnAction(e->{
			bp.setCenter(null);
			bp.setBottom(null);
			tdController.showTransactionHeader(userId, stage, bp ,role);
		});
	}
	
	public void showTransactionDetail(int userId, Stage stage, BorderPane bp, String role) {
		this.role = role;
		this.userId = userId;
		init();
		layout(bp);
		eventControl(userId, stage, bp, role);
	}
	
	public ViewTransactionDetail(int thId) {
		this.thId = thId;
	
	}
	
	
}
