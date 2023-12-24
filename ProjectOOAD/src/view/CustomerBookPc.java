package view;

import java.time.LocalDate;
import java.util.ArrayList;

import controller.BookController;
import controller.PcController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Pc;

public class CustomerBookPc {
	Stage primaryStage;
	Scene scene;
	BorderPane bp;
	TableView<Pc> pcTable;
	TableColumn<Pc, Integer> idColumn;
	TableColumn<Pc, String> statusColumn;
	ObservableList<Pc> data;
	Pc pc;
	PcController pcController ;
	BookController bookController;
	
	VBox tableBox;
	Label pcIdLabel, bookedDateLabel;
	TextField pcIdField;
	DatePicker bookedDate;
	Button bookPcBtn;
	GridPane gridPane = new GridPane();
	String status;
	private int userid;

	public void initialize() {
		pcController = new PcController();
		bookController = new BookController();
		data = FXCollections.observableArrayList(pcController.getAllPc());

	    pcTable = new TableView<>();
	    pcTable.setPrefSize(1000, 250); 
	    tableBox = new VBox();
	    
	    idColumn = new TableColumn<Pc, Integer>("Id");
	    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
	    
	    statusColumn = new TableColumn<Pc, String>("Status");
	    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

		pcIdLabel= new Label("PC ID : ");
		pcIdField = new TextField();
		
		bookedDateLabel = new Label("Book Date: ");
		bookedDate = new DatePicker();

		bookPcBtn = new Button("Book This PC");
	}
	
	@SuppressWarnings("unchecked")
	public void layout(BorderPane bp) {
		pcIdField.setEditable(false);
		gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setHgap(0);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.add(pcIdLabel, 0, 0);
        gridPane.add(pcIdField, 1, 0);
        gridPane.add(bookedDateLabel, 0, 1);
        gridPane.add(bookedDate, 1, 1);
        gridPane.add(bookPcBtn, 0, 2, 2, 1);
        GridPane.setMargin(bookPcBtn, new Insets(10, 0, 0, 0));
		
		pcTable.getColumns().addAll(idColumn,statusColumn);
		pcTable.setItems(data);
		pcTable.setMaxHeight(300);
		
		tableBox.setAlignment(Pos.TOP_CENTER);
		tableBox.getChildren().addAll(pcTable);

		bp.setCenter(tableBox);
		bp.setBottom(gridPane);
	}
	public void eventControl(Stage primaryStage){
		pcTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	int id = newSelection.getId();
            	pcIdField.setText(String.valueOf(id));
            	this.status = newSelection.getStatus();
            }
        });
		
		bookPcBtn.setOnAction(e->{
			String id = pcIdField.getText();
			LocalDate date = bookedDate.getValue();
			bookController.bookDate(id, status, date, userid);
			
			loadAllData();
			
		});
	}
	
	void loadAllData() {
		
		ArrayList<Pc> data = pcController.getAllPc();
		pcTable.getItems().setAll(data);
	}

	public void showViewBookPc(Stage primaryStage, BorderPane bp) {
		primaryStage.setResizable(true);
		initialize();
		layout(bp);
		eventControl(primaryStage);	
	}

	public CustomerBookPc(int userId) {
		this.userid = userId;
	}
	
	public CustomerBookPc() {
		
	}

}
