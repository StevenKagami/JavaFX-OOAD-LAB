package view;

import java.util.ArrayList;

import controller.JobController;
import controller.PcController;
import controller.UserController;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Job;
import model.Pc;
import model.User;

public class AdminJobManagement {
	
	
	TableView<Job> jobTable;
	TableColumn<Job, Integer> jobIdColumn, pcIdColumn, userIdColumn;
	TableColumn<Job, String> jobStatusColumn;
	ObservableList<Job> jobData;
	JobController jobController ;
	
	TableView<Pc> pcTable;
	TableColumn<Pc, Integer> pcIdDataColumn;
	TableColumn<Pc, String> pcStatusColumn;
	ObservableList<Pc> pcData;
	PcController pcController ;
	
	TableView<User> userTable;
	TableColumn<User, Integer> userIdDataColumn, userAgeColumn ;
	TableColumn<User, String> userRoleColumn, usernameColumn;
	ObservableList<User> userData;
	UserController userController ;
	
	HBox pcAndUserBox;
	VBox formBox;
	
	GridPane gridPane;
	Label jobIdLabel, pcIdLabel, userIdLable, jobStatusLabel, noteLabel;
	TextField jobIdField, pcIdField, userIdField, jobStatusField;
	Button addJob, updateJobButton, clearButton;
	
	private void initialize() {
		jobController= new JobController();
		jobData = FXCollections.observableArrayList(jobController.getAllJobData());
		jobTable = new TableView<>();
		jobIdColumn = new TableColumn<Job, Integer>("Job ID");
		pcIdColumn = new TableColumn<Job, Integer>("PC ID");
		userIdColumn = new TableColumn<Job, Integer>("User ID");
		jobStatusColumn = new TableColumn<Job, String>("Job Status");
		
		pcController= new PcController();
		pcData = FXCollections.observableArrayList(pcController.getAllUnusablePcData());
		pcTable = new TableView<>();
		pcIdDataColumn = new TableColumn<Pc, Integer>("PC ID");
		pcStatusColumn = new TableColumn<Pc, String>("Pc Status");
		
		userController= new UserController();
		userData = FXCollections.observableArrayList(userController.getAllTechnician());
		userTable = new TableView<>();
		userIdDataColumn = new TableColumn<User, Integer>("User ID");
		usernameColumn = new TableColumn<User, String>("User Name");
		userAgeColumn = new TableColumn<User, Integer>("User Age");
		userRoleColumn = new TableColumn<User, String>("User role");
		
		pcAndUserBox = new HBox();
		formBox = new VBox();
		gridPane = new GridPane();
		
		jobIdLabel = new Label("Job ID : ");
		pcIdLabel= new Label("PC ID : "); 
		userIdLable= new Label("User ID : ");
		jobStatusLabel= new Label("Job Status : ");
		noteLabel = new Label("Job id is for update!!");
		
		jobIdField = new TextField();
		pcIdField = new TextField();
		userIdField = new TextField();
		jobStatusField = new TextField();
		
		addJob = new Button("Add Job");
		updateJobButton = new Button("Update Job Status");
		clearButton = new Button("Clear form");	
	}
	
	@SuppressWarnings("unchecked")
	private void layout(BorderPane bp) {
		jobIdColumn.setCellValueFactory(new PropertyValueFactory<>("JobId"));
		userIdColumn.setCellValueFactory(new PropertyValueFactory<>("UserId"));
		pcIdColumn.setCellValueFactory(new PropertyValueFactory<>("PcId"));
		jobStatusColumn.setCellValueFactory(new PropertyValueFactory<>("JobStatus"));
		jobTable.getColumns().addAll(jobIdColumn,userIdColumn,pcIdColumn ,jobStatusColumn);
		jobTable.setItems(jobData);
		jobTable.setMaxHeight(200);
		
		pcIdDataColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		pcStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		pcTable.getColumns().addAll(pcIdDataColumn,pcStatusColumn);
		pcTable.setItems(pcData);
		pcTable.setMaxHeight(200);
		pcTable.setMinWidth(200);
		
		userIdDataColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		userAgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
		userRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
		userTable.getColumns().addAll(userIdDataColumn,usernameColumn,userAgeColumn, userRoleColumn);
		userTable.setItems(userData);
		userTable.setMaxHeight(200);
		userTable.setMinWidth(400);
		
		pcAndUserBox.setAlignment(Pos.CENTER);
		pcAndUserBox.setFillHeight(true);
		pcAndUserBox.getChildren().addAll(pcTable,userTable);
		
		jobIdField.setEditable(false);
		jobIdField.setPromptText("Click the job table!");
		pcIdField.setPromptText("enter pc id!");
		userIdField.setPromptText("Enter user id!");
		jobStatusField.setPromptText("Enter update status");
		
		gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

		gridPane.addColumn(0, jobIdLabel, jobStatusLabel);
		gridPane.addColumn(1, jobIdField, jobStatusField,updateJobButton);
		gridPane.addColumn(2, clearButton);
		
		gridPane.addColumn(3, pcIdLabel,userIdLable);
		gridPane.addColumn(4, pcIdField,userIdField,addJob);
		
        gridPane.setAlignment(Pos.CENTER);
		formBox.getChildren().addAll(jobTable,pcAndUserBox,gridPane);
		
		bp.setCenter(formBox);
	}
	
	public void eventControl(Stage stage, BorderPane bp) {
		jobTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	String jobId =	String.valueOf(newSelection.getJobId());
            	jobIdField.setText(jobId);
            }
        });
		
		userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	
            	userIdField.setText(String.valueOf(newSelection.getId()));
            }
        });
		
		pcTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	pcIdField.setText(String.valueOf(newSelection.getId()));
            }
        });
		
		addJob.setOnAction(e->{
			String pcId = pcIdField.getText();
			String userId = userIdField.getText();
			String error = jobController.addJob(pcId, userId);
			if(error.equals("Success")) {
				loadData();
				pcIdField.clear();
				userIdField.clear();
			}
		});
		
		updateJobButton.setOnAction(e->{
			String jobId = jobIdField.getText();
			String jobStatus = jobStatusField.getText();
			jobController.updateJobStatus(jobId, jobStatus);
			loadData();
		});
		
		clearButton.setOnAction(e->{
			jobIdField.clear();
			pcIdField.clear();
			jobStatusField.clear();
			userIdField.clear();
		});
	}
	
	private void loadData() {
		ArrayList<Pc> data = pcController.getAllUnusablePcData();
		pcTable.getItems().setAll(data);
		ArrayList<User> userData = userController.getAllTechnician();
		userTable.getItems().setAll(userData);
		ArrayList<Job> jobData = jobController.getAllJobData();
		jobTable.getItems().setAll(jobData);
	}
	
	public void showJobManagement(Stage stage, BorderPane bp) {
		 initialize();
		 layout(bp);
		 eventControl(stage, bp);
	}
	
	public AdminJobManagement() {
		
	}

}
