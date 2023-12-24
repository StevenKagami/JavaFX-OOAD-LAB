package view;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import controller.BookController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.BookPc;

public class OperatorPcBookedData {
	TableView<BookPc> bookPcTable;
	TableColumn<BookPc, Integer> bookIdColumn, pcIdColumn, userIdColumn;
	TableColumn<BookPc, Date> bookedDateColumn;
	ObservableList<BookPc> data;
	BookController bookController;
	
	HBox noteLabelBox;
	DatePicker cancelDatePicker, finishDatePicker;
	Button cancelBookingButton, finishBookingButton, assignUser;
	Label noteLabel, pcIdLabel, jobIdLabel, cancelLabel, finishLabel;
	VBox tableBox;
	GridPane grid;
	TextField pcIdField, jobIdField;
    
	private int bookId = 0, pcId = 0, staffId;
	private Date bookeDate;

	public void initialize() {
		bookController = new BookController();
		data = FXCollections.observableArrayList(bookController.getAllBookPc());
		
		tableBox = new VBox();
		bookPcTable = new TableView<>();
		bookPcTable.setMaxHeight(300);
		
		bookIdColumn = new TableColumn<BookPc, Integer> ("Booked Id");
		bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
		
		bookedDateColumn = new TableColumn<BookPc, Date> ("Booked Date");
		bookedDateColumn.setCellValueFactory(new PropertyValueFactory<>("bookedDate"));
		
		pcIdColumn = new TableColumn<BookPc, Integer> ("PC Id");
		pcIdColumn.setCellValueFactory(new PropertyValueFactory<>("pcId"));
		
		userIdColumn = new TableColumn<BookPc, Integer> ("User Id");
		userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
		
		cancelDatePicker = new DatePicker();
		cancelBookingButton = new Button("Cancel Booked Data");
		cancelLabel = new Label("Cancel: ");
		
		finishDatePicker = new DatePicker();
		finishBookingButton = new Button("Finish Booked Data");
		finishLabel = new Label("Finish: ");
		
		noteLabelBox = new HBox(10);
		noteLabel = new Label("Note : BLM ADA DEH ANJ SUSAH SOALNYA");
		
		grid = new GridPane();
		pcIdField = new TextField();
		jobIdField = new TextField();
		assignUser = new Button("Change User PC");
		pcIdLabel = new Label("PC ID: ");
		jobIdLabel = new Label("Job ID: ");
	}
	
	@SuppressWarnings("unchecked")
	public void layout(BorderPane bp) {
		cancelDatePicker.setPromptText("Select date to Cancel");	
		finishDatePicker.setPromptText("Select Start Date");	
		noteLabelBox.setPadding(new Insets(20));
		jobIdField.setPromptText("Click the table");
	    pcIdField.setPromptText("Enter PC ID");
	    
	    jobIdField.setEditable(false);
	    
		grid.setPadding(new Insets(20));
	    grid.setHgap(10);
	    grid.setVgap(10);
	    
	    grid.add(jobIdLabel, 0, 0);
        grid.add(jobIdField, 1, 0);
        grid.add(pcIdLabel, 0, 1);
        grid.add(pcIdField, 1, 1);
        grid.add(assignUser, 1, 2);
        
        grid.add(cancelLabel, 0, 6);
        grid.add(cancelDatePicker,1,6);
        grid.add(cancelBookingButton, 2, 6);

        grid.add(finishLabel, 0, 10);
        grid.add(finishDatePicker, 1, 10);
        grid.add(finishBookingButton, 2, 10);
	    
		bookPcTable.getColumns().addAll(bookIdColumn,bookedDateColumn, pcIdColumn, userIdColumn);
		bookPcTable.setItems(data);
		tableBox.setAlignment(Pos.TOP_CENTER);
		tableBox.getChildren().addAll(bookPcTable);
		bp.setCenter(tableBox);
		
		bp.setBottom(grid);
	}
	
	public void eventControl(Stage primaryStage) {
		bookPcTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				
				jobIdField.setText(String.valueOf(newSelection.getBookId()));
				this.pcId = newSelection.getPcId();
				this.bookId = newSelection.getBookId();
				this.bookeDate = newSelection.getBookedDate();
			}
		});
		
		cancelBookingButton.setOnAction(e->{
			LocalDate date = cancelDatePicker.getValue();
			bookController.cancelBooking(date);
			loadAllData();
		});
		
		finishBookingButton.setOnAction(e->{
			LocalDate date = finishDatePicker.getValue();
			bookController.finishBooking(date,staffId);
			loadAllData();
		});
		
		assignUser.setOnAction(e->{
			String searchPcId = pcIdField.getText();
			bookController.changePc(bookId, pcId, searchPcId,bookeDate);
			loadAllData();
		});
	}


	public void loadAllData() {
		ArrayList<BookPc> data = bookController.getAllBookPc();
		bookPcTable.getItems().setAll(data);
	}
	
	public void showPcBookDate(Stage primaryStage, BorderPane bp) {
		primaryStage.setResizable(true);
		initialize();
		layout(bp);
		eventControl(primaryStage);
	}
	
	public OperatorPcBookedData(int staffId) {
		this.staffId = staffId;
	}
	
}
