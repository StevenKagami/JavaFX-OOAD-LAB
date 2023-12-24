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
import model.Pc;
import view.ViewAll;
import view.ViewDetailPc;

public class PcController {
	
	public PcController() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<Pc> getAllPc(){
		ArrayList<Pc> data = new ArrayList<>();
		Connect connect = Connect.getInstance();
		String query = "SELECT * FROM Pcs";

		try {
			Connection connection = connect.getConnection();
			Statement stement = connection.createStatement();
			ResultSet resultSet = stement.executeQuery(query);
			
			while (resultSet.next()) {
				int id = resultSet.getInt("PcId");
				String status = resultSet.getString("PcStatus");
                data.add(new Pc(id, status));
			}
			
			resultSet.close();
			stement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return data;
	}
	
	public ArrayList<Pc> getAllUnusablePcData(){
		ArrayList<Pc> data = new ArrayList<>();
		Connect connect = Connect.getInstance();
		String query = "SELECT * FROM Pcs WHERE PcStatus != 'Usable'";

		try {
			Connection connection = connect.getConnection();
			Statement stement = connection.createStatement();
			ResultSet resultSet = stement.executeQuery(query);
			
			while (resultSet.next()) {
				int id = resultSet.getInt("PcId");
				String status = resultSet.getString("PcStatus");
                data.add(new Pc(id, status));
			}
			
			resultSet.close();
			stement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return data;
	}
	
	
	public void updateStatusPc(int pcId, String status) {
		String q = "UPDATE Pcs SET PcStatus = (?) where PcId = (?)";
		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    PreparedStatement preparedStatement = connection.prepareStatement(q);
		    
		    preparedStatement.setString(1, status);
		    preparedStatement.setInt(2, pcId);
		    preparedStatement.executeUpdate();
		    preparedStatement.close();
		    connection.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	public boolean searchPCStatus(int pcId) {
	    String query = "SELECT PcStatus FROM Pcs WHERE PcId = ?";
	    Connect connect = Connect.getInstance();

	    try (Connection connection = connect.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        
	        preparedStatement.setInt(1, pcId);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            String status = resultSet.getString("PcStatus");
	            if (!status.equals("Usable")) {
	                showAlert(Alert.AlertType.ERROR, "Error", "PC id " + pcId + " is " + status);
	                return false;
	            } else {
	                return true;
	            }
	        } else {
	            showAlert(Alert.AlertType.ERROR, "Error", "PC id " + pcId + " not found");
	            return false;
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        return false;
	    }
	}
	
	public String addPc(String newPcId) {
		if(newPcId.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Error", "PC ID Cannot null");
		}else if(!isValidNumber(newPcId)) {
			showAlert(Alert.AlertType.ERROR, "Error", "PC ID must be number");
		}else if(!isUnique(newPcId)) {
			showAlert(Alert.AlertType.ERROR, "Error", "PC ID must be unique");
		}else {
			int newPcID = Integer.parseInt(newPcId);
			addPcToDatabase(newPcID);
			return "Success";
		}
		return "Failed";
	}
	
	public String updatePc(int pcId, String newStatus) {
		if(newStatus.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Error", "Status cannot null");
		}else if(newStatus.equals("Usable") || newStatus.equals("Maintenance") || newStatus.equals("Broken")){
			updateStatusPc(pcId,newStatus);
			showAlert(Alert.AlertType.INFORMATION, "Success", "Success Update PC!");
			return "Success";
		}else {
			showAlert(Alert.AlertType.ERROR, "Error", newStatus+" can't update status. Status must Usable , Maintenance, or Broken");
		}
		return "Failed";
	}
	
	public void deletePc(int pcId) {
		String query = "DELETE FROM Pcs WHERE PcId = (?)";
		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setInt(1, pcId);

		    int rowsAffected = preparedStatement.executeUpdate();

		    if (rowsAffected > 0) {
				showAlert(Alert.AlertType.INFORMATION, "Notification", "Delete Success!");	
		    } else {
				showAlert(Alert.AlertType.ERROR, "Error", "Delete failed!");	
		    }

		    preparedStatement.close();
		    connection.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	private void addPcToDatabase(int newPcID) {
		String query = "INSERT INTO Pcs VALUES(?, ?)";
		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setInt(1, newPcID);
		    preparedStatement.setString(2, "Usable");
	

		    int rowsAffected = preparedStatement.executeUpdate();

		    if (rowsAffected > 0) {
				showAlert(Alert.AlertType.INFORMATION, "Notification", "Insert Success!");	
		    } else {
				showAlert(Alert.AlertType.ERROR, "Error", "Insert failed!");	
		    }

		    preparedStatement.close();
		    connection.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	private boolean isUnique(String newPcId) {
		int number = Integer.parseInt(newPcId);
		Boolean unique = true;
		String query = "SELECT * FROM Pcs WHERE PcId = ?";
		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setInt(1, number);
		    
		    ResultSet resultSet = preparedStatement.executeQuery();
		    
            if (resultSet.next()) {
            	unique = false;
            }
		    
		    preparedStatement.close();
		    connection.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		return unique;
	}
	
	public boolean isExistAndBroke(int PcId) {		
		String query = "SELECT * FROM Pcs WHERE PcId = ?";
		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setInt(1, PcId);
		    
		    ResultSet resultSet = preparedStatement.executeQuery();
		    
            if (resultSet.next()) {
                if(!resultSet.getString("PcStatus").equals("Usable")) {
                	return true;
                }
            }
		    preparedStatement.close();
		    connection.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		return false;
	}
	
	private boolean isValidNumber(String input) {
	    try {
	        Integer.parseInt(input);
	        return true;
	    } catch (NumberFormatException e) {
	    
	        return false;
	    }
	}
	
	private void showAlert(Alert.AlertType alertType, String title, String message) {
	    Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	
	public void viewAll( Stage primaryStage, BorderPane bp, String role) {
		ViewAll viewAll = new ViewAll(role);
		viewAll.showViewAll(primaryStage, bp);
	}
	
	public void viewDetailPc( int Pcid, String status, String role, BorderPane bp, Stage stage) {
		ViewDetailPc pc = new ViewDetailPc(Pcid,status, role);
		pc.showViewDetailPc(bp,stage);
	}


}
