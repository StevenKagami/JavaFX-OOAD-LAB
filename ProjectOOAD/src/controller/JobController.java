package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Connect;
import model.Job;
import view.AdminJobManagement;
import view.ViewTechnicianJob;

public class JobController {

	
	public ArrayList<Job> getAllJobData(){
		ArrayList<Job> data = new ArrayList<>();
		Connect connect = Connect.getInstance();
		String query = "SELECT * FROM Jobs";
		try {
			Connection connection = connect.getConnection();
			Statement stement = connection.createStatement();
			ResultSet resultSet = stement.executeQuery(query);
			
			while (resultSet.next()) {
				int jobId = resultSet.getInt("JobId");
				int userId = resultSet.getInt("UserId");
				int pcId = resultSet.getInt("PcId");
				String jobStatus = resultSet.getString("JobStatus");
                data.add(new Job(jobId, userId , pcId, jobStatus));
			}
			
			resultSet.close();
			stement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return data;
	}


	public ArrayList<Job> getTechnicianJob(int TechnicianId){
		ArrayList<Job> data = new ArrayList<>();
		Connect connect = Connect.getInstance();
		String query = "SELECT * FROM Jobs WHERE UserId = ?";
		try {
			Connection connection = connect.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, TechnicianId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int jobId = resultSet.getInt("JobId");
				int userId = resultSet.getInt("UserId");
				int pcId = resultSet.getInt("PcId");
				String jobStatus = resultSet.getString("JobStatus");
                data.add(new Job(jobId, userId , pcId, jobStatus));
			}
			
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return data;
	}
	
	
	public String addJob (String pcId, String userId) {
		PcController pcControl = new PcController();
		UserController userControl = new UserController();
		if(pcId.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Error", "PC ID cannot empty!");
		}else if(userId.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Error", "User ID cannot empty!");
		}else if(!pcControl.isExistAndBroke(Integer.parseInt(pcId))) {
			showAlert(Alert.AlertType.ERROR, "Error", "PC id is not exist or pc is not broke!");
		}else if(!userControl.isUserTechnician( Integer.parseInt(userId))) {
			showAlert(Alert.AlertType.ERROR, "Error", "User is not Technician!");
		}else{
			addToJobDatabase( Integer.parseInt(pcId),  Integer.parseInt(userId));
			return "Success";
		}
		return "Error";	
	}
	
	private void addToJobDatabase(int pcId, int userId){
		String query = "INSERT INTO Jobs VALUES(null,?, ?, 'UnComplete')";
		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setInt(1, userId);
		    preparedStatement.setInt(2, pcId);

		    int rowsAffected = preparedStatement.executeUpdate();

		    if (rowsAffected > 0) {
				showAlert(Alert.AlertType.INFORMATION, "Notification", "Insert job Success!");	
		    } else {
				showAlert(Alert.AlertType.ERROR, "Error", "Insert job failed!");	
		    }

		    preparedStatement.close();
		    connection.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
	}
	
	public void updateJobStatus(String jobId, String jobStatus) {
		if(jobId.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Error", "Job id cannot empty!");
		}else if(jobStatus.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Error", "Job status cannot empty!");
		}else if(jobStatus.equals("Complete") || jobStatus.equals("UnComplete") ) {
			udpateStatus(Integer.parseInt(jobId), jobStatus);
			return;
		}else {
			showAlert(Alert.AlertType.ERROR, "Error", "Job Status must Complete or UnComplete");
		}
	}
	
	private void udpateStatus(int jobId, String jobStatus) {
		String query = "UPDATE Jobs SET JobStatus = ? WHERE JobId = ?";
		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setString(1, jobStatus);
		    preparedStatement.setInt(2, jobId);

		    int rowsAffected = preparedStatement.executeUpdate();
		    if (rowsAffected > 0) {
				showAlert(Alert.AlertType.INFORMATION, "Notification", "Update Success!");	
		    } else {
				showAlert(Alert.AlertType.ERROR, "Error", "Update failed!");	
		    }

		    preparedStatement.close();
		    connection.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	public void finishJob(String jobId, String jobStatus) {
		if(jobId.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Error", "Job id cannot empty!");
		}else if(jobStatus.equals("Complete")) {
			showAlert(Alert.AlertType.INFORMATION, "Information", "Job is already complete!");
		}else {
			udpateStatus(Integer.parseInt(jobId), "Complete");
		}
	}
	
	public void showTechnicianJob(Stage stage, BorderPane bp, int technicianId) {
		ViewTechnicianJob tech = new ViewTechnicianJob(technicianId);
		tech.showTechnicianJob(stage, bp);
	}
	
	public void showJobManagement(Stage stage, BorderPane bp) {
		AdminJobManagement admin = new AdminJobManagement();
		admin.showJobManagement(stage, bp);
	}
	
	public JobController() {
		
	}
	
	private void showAlert(Alert.AlertType alertType, String title, String message) {
	    Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}	
}
