package view;

import java.util.ArrayList;

import controller.JobController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Job;

public class ViewTechnicianJob {
	private int technicianId;
	private String jobStatus;
	
	TableView<Job> jobTable;
	TableColumn<Job, Integer> jobIdColumn, pcIdColumn, userIdColumn;
	TableColumn<Job, String> jobStatusColumn, pcStatusColumn;
	ObservableList<Job> jobData;
	JobController jobController ;
	
	VBox formBox;
	GridPane gridPane;
	Label pcId;
	TextField pcIdField;
	Button finishButton;
	
	private void initialize() {
		jobController= new JobController();
		jobData = FXCollections.observableArrayList(jobController.getTechnicianJob(technicianId));
		jobTable = new TableView<>();
		jobIdColumn = new TableColumn<Job, Integer>("Job ID");
		pcIdColumn = new TableColumn<Job, Integer>("PC ID");
		userIdColumn = new TableColumn<Job, Integer>("User ID");
		jobStatusColumn = new TableColumn<Job, String>("Job Status");

		formBox = new VBox(50);
		gridPane = new GridPane();
		pcId = new Label("PC ID: ");
		pcIdField = new TextField();
		finishButton = new Button("Finish Job");
	}
	@SuppressWarnings("unchecked")
	private void layout(BorderPane bp) {
		jobIdColumn.setCellValueFactory(new PropertyValueFactory<>("JobId"));
		userIdColumn.setCellValueFactory(new PropertyValueFactory<>("UserId"));
		pcIdColumn.setCellValueFactory(new PropertyValueFactory<>("PcId"));
		jobStatusColumn.setCellValueFactory(new PropertyValueFactory<>("JobStatus"));
		jobTable.getColumns().addAll(jobIdColumn,userIdColumn,pcIdColumn ,jobStatusColumn);
		
		jobTable.setItems(jobData);
		jobTable.setMaxHeight(300);
		
		pcIdField.setEditable(false);
		pcIdField.setPromptText("Click on the table");
		gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        gridPane.addRow(0, pcId, pcIdField, finishButton);
		
		formBox.getChildren().addAll(jobTable,gridPane );
		bp.setCenter(formBox);
	}
	public void eventControl(Stage stage) {
		jobTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	String pcId =	String.valueOf(newSelection.getPcId());
            	this.jobStatus = String.valueOf(newSelection.getJobStatus());
            	pcIdField.setText(pcId);
            }
        });
		
		finishButton.setOnAction(e->{
			String pcId = pcIdField.getText();
			jobController.finishJob(pcId, jobStatus);
			loadData() ;
		});
	}
	
	private void loadData() {
		ArrayList<Job> jobData = jobController.getTechnicianJob(this.technicianId);
		jobTable.getItems().setAll(jobData);
		pcIdField.clear();
	}
	
	public void showTechnicianJob(Stage stage, BorderPane bp) {
		initialize();
		layout(bp);
		eventControl(stage);
	}
	
	public ViewTechnicianJob(int id) {
		this.technicianId = id;
	}

}
