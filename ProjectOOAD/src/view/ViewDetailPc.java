package view;

import controller.PcController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ViewDetailPc {
	PcController pcController;
	private String role;
	GridPane grid;
	
	Label pcIdLabel,pcIdLabel2,pcIdLabel3, pcStatusLabel, pcStatusLabel2, 
	pdIdDetailLabel, pcStatusDetailLabel, managePcLabel, addPcLabel, updateOrDeleteLabel;
	private int pcId ;
	private String pcStatus;
	
	Button addButton, deleteButton, updateButton ;
	Hyperlink back;
	TextField formPcIdField, formStatusField, addPcIdField, formAddPcField;
	
	private void initialize() {
		pcController = new PcController();
		grid = new GridPane();
		pcIdLabel = new Label("PC ID : ");

		pcStatusLabel = new Label("PC Status : ");
		
		pdIdDetailLabel = new Label(String.valueOf(pcId));
		pcStatusDetailLabel = new Label(pcStatus);
		back = new Hyperlink ("Back");
	}
	
	private void initAdmin() {
		managePcLabel = new Label("Manage PC");
		addPcLabel = new Label("Add Pc");
		updateOrDeleteLabel = new Label("Update Or Delete PC");
		pcStatusLabel2 = new Label("PC Status : ");
		pcIdLabel2 = new Label("PC ID : ");
		pcIdLabel3 = new Label("PC ID : ");
		
		addButton = new Button("Add PC");
		deleteButton = new Button("Delete PC");
		updateButton = new Button("Update PC");
		
		formPcIdField = new TextField();
		formAddPcField = new TextField();
		formStatusField = new TextField();
		addPcIdField = new TextField();
	}
	
	private void layoutAdmin(BorderPane bp) {
		layout(bp);
		formPcIdField.setText(pdIdDetailLabel.getText());
		formStatusField.setPromptText("Enter New Status!");
		formPcIdField.setEditable(false);
		grid.addRow(8, addPcLabel);
		grid.addRow(9, pcIdLabel2,formAddPcField, addButton );
		
		grid.addRow(15, updateOrDeleteLabel);
		grid.addRow(16, pcIdLabel3, formPcIdField);
		grid.addRow(17, pcStatusLabel2, formStatusField);
		grid.add(deleteButton, 1, 18);
		grid.add(updateButton, 2, 18);
		
		bp.setCenter(grid);
	}
	
	private void layout(BorderPane bp) {
		grid.setPadding(new Insets(20));
		grid.setHgap(10);
		grid.setVgap(10);
		grid.addRow(0, back);
		grid.addRow(1, pcIdLabel, pdIdDetailLabel);
		grid.addRow(2, pcStatusLabel, pcStatusDetailLabel);
		grid.setAlignment(Pos.CENTER);
		bp.setCenter(grid);
	
	}

	private void eventControl(Stage stage, BorderPane bp) {
		back.setOnAction(e->{
			bp.setBottom(null);
			pcController.viewAll(stage, bp, role);
		});
		
		if(role.equals("Admin")) {
			addButton.setOnAction(e->{
				String newPcId = formAddPcField.getText();
				String error = pcController.addPc(newPcId);
				if(error.equals("Success")) {
					pcController.viewAll(stage, bp, role);				
				}
			});
			
			deleteButton.setOnAction(e->{
				pcController.deletePc(pcId);
				pcController.viewAll(stage, bp, role);
			});
			
			updateButton.setOnAction(e->{
				String newStatus = formStatusField.getText();
				String error = pcController.updatePc(pcId, newStatus);
				if(error.equals("Success")) {
					loadDetail();
					initialize();
					layoutAdmin(bp);
					eventControl(stage, bp);
				}
				formStatusField.clear();
			});
		}
	}
	
	private void loadDetail() {
		this.pcStatus = formStatusField.getText();
	}
	
	public ViewDetailPc(int id, String status, String role) {
		this.role = role;
		this.pcId = id;
		this.pcStatus = status;
	}
	
	public void showViewDetailPc(BorderPane bp, Stage stage) {
		initialize();
		if(role.equals("Admin")) {
			initAdmin();
			layoutAdmin(bp);
		}else {
			layout(bp);
		}
		eventControl(stage, bp);
		
	}

}
