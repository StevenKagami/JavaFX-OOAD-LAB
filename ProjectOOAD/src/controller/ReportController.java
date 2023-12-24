package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Connect;
import model.Report;
import view.AddReport;
import view.ViewAllReport;


public class ReportController {

	public ReportController() {
		
	}
	
	public ArrayList<Report> getAllReport(){
		ArrayList<Report> report = new ArrayList<>();
		Connect connect = Connect.getInstance();
		String query = "SELECT * FROM Reports";

		try {
			Connection connection = connect.getConnection();
			Statement stement = connection.createStatement();
			ResultSet resultSet = stement.executeQuery(query);
			
			while (resultSet.next()) {
				int reportId = resultSet.getInt("ReportId");
				String userRole = resultSet.getString("UserRole");
				int pcId  = resultSet.getInt("PcId");
				String reportNote = resultSet.getString("ReportNote");
				Date reportDate = resultSet.getDate("ReportDate");
				report.add(new Report(reportId,pcId,reportNote, userRole,reportDate));
			}
			
			resultSet.close();
			stement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return report;
	}
	
	public void showReport(Stage stage, BorderPane bp, int userId, String role) {
		AddReport report = new AddReport(userId, role);
		report.showReport(stage, bp);
	}
	
	public void viewAllReport(BorderPane bp) {
		ViewAllReport report = new ViewAllReport();
		report.showViewAllReport(bp);
	}
	
	public void addReport(String pcId, String Note, int userId, String userRole) {
		if(pcId.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Error", "Id cannot be empty!");
		}else if(Note.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Error", "Note cannot be empty!");
		}else if(!isExist(pcId)) {
			showAlert(Alert.AlertType.ERROR, "Error", "PC ID Not Exist!");
		}else {
			addToDatabase(pcId, Note, userId, userRole);
		}
	}
	
	
	private void addToDatabase(String id, String Note, int userId, String userRole) {
		int PcId = Integer.parseInt(id);
		String query = "INSERT INTO reports(ReportId, UserRole, PcId, ReportNote, ReportDate) VALUES (null,?, ?, ?, ?)";
		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    
		    Date now = Date.valueOf(LocalDate.now());  
		    preparedStatement.setString(1, userRole);
		    preparedStatement.setInt(2, PcId);
		    preparedStatement.setString(3, Note);
		    preparedStatement.setDate(4, now);

		    int rowsAffected = preparedStatement.executeUpdate();

		    if (rowsAffected > 0) {
				showAlert(Alert.AlertType.INFORMATION, "Notification", "Report Success!");	
		    } else {
				showAlert(Alert.AlertType.ERROR, "Error", "Report failed!");	
		    }
		    preparedStatement.close();
		    connection.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	private boolean isExist(String id) {
		int PcId = Integer.parseInt(id);
		String query = "SELECT * FROM Pcs WHERE PcId = ?";
        boolean isSame = false;
        try {
			Connect c = Connect.getInstance();
            Connection connection = c.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, PcId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	isSame = true;
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSame;
		
	}

	private void showAlert(Alert.AlertType alertType, String title, String message) {
	    Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
}
