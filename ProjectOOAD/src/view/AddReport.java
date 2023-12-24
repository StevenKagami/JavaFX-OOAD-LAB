package view;

import controller.ReportController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddReport {
	   
	GridPane gridPane;
	Label idLabel, userNameLabel;
	TextField idTextField, noteTextField;;
	Label noteLabel;
	Button addButton;
	ReportController reportController;
	
	private int userId;
	private String role;
	
	public void init() {
		gridPane = new GridPane();
		
		idLabel = new Label("PC ID:");
		idTextField = new TextField();
		
		noteTextField = new TextField();
		noteLabel = new Label("Note:");
		
		addButton = new Button("Submit Report");
		reportController = new ReportController();
	}
	public void layout(BorderPane bp) {
		gridPane.setPadding(new Insets(10));
		gridPane.setHgap(10);
	    gridPane.setVgap(10);
		    
	    gridPane.addRow(0, idLabel, idTextField);
	    gridPane.addRow(1, noteLabel, noteTextField);
	    gridPane.add(addButton, 1, 3);

	    gridPane.setAlignment(Pos.CENTER);
	    BorderPane.setAlignment(gridPane, Pos.CENTER);
	    
	    bp.setCenter(gridPane);
	}
	
	public void eventController(Stage stage) {
		addButton.setOnAction(e->{
			String pcId = idTextField.getText();
			String note = noteTextField.getText();
			int userId = this.userId;
			reportController.addReport(pcId, note, userId, role);
			idTextField.clear();
			noteTextField.clear();
		});
	}
	
	public void showReport(Stage stage, BorderPane bp) {
		init();
		stage.setResizable(true);
		layout(bp);
		eventController(stage);
	}
	
	public AddReport(int userId, String role) {
		this.role = role;
		this.userId = userId;
	}

}
