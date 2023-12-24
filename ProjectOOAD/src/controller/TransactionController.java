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
import model.BookPc;
import model.Connect;
import model.TransactionDetail;
import model.TransactionHeader;
import view.ViewTransactionDetail;
import view.ViewTransactionHeader;

public class TransactionController {
	public ArrayList<TransactionHeader> getAllTransaction(){
		ArrayList<TransactionHeader> data = new ArrayList<>();
		Connect connect = Connect.getInstance();
		String query = "SELECT * FROM TransactionHeaders";

		try {
			Connection connection = connect.getConnection();
			Statement stement = connection.createStatement();
			ResultSet resultSet = stement.executeQuery(query);

			
			while (resultSet.next()) {
				int id = resultSet.getInt("TransactionId");
				int staffId = resultSet.getInt("StaffId");
				String staffName = resultSet.getString("StaffName");
				Date thDate = resultSet.getDate("TransactionDate");
                data.add(new TransactionHeader(id,staffId, staffName, thDate));
			}
			
			resultSet.close();
			stement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return data;
	}
	public ArrayList<TransactionHeader> getAllTransactionById(int customerId){
		ArrayList<TransactionHeader> data = new ArrayList<>();
		Connect connect = Connect.getInstance();
		String query = "SELECT DISTINCT th.TransactionId, th.StaffID, th.StaffName, th.TransactionDate " +
	               "FROM transactionheaders th " +
	               "JOIN transactiondetails td ON th.TransactionId = td.TransactionId " +
	               "JOIN users u ON u.UserName = td.CustomerName " +
	               "WHERE u.UserId = ?;";
		try {
			Connection connection = connect.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, customerId);
	        ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("TransactionId");
				int staffId = resultSet.getInt("StaffID");
				String staffName = resultSet.getString("StaffName");
				Date thDate = resultSet.getDate("TransactionDate");
                data.add(new TransactionHeader(id,staffId, staffName, thDate));
			}
			
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return data;
	}
	
	public ArrayList<TransactionDetail> getAllTransactionDetail(int transactionId){
		ArrayList<TransactionDetail> data = new ArrayList<>();
		Connect connect = Connect.getInstance();
		String query = "SELECT * FROM TransactionDetails WHERE TransactionId = ?";
		try {
			Connection connection = connect.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, transactionId);
	        ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("TransactionId");
				int pcId = resultSet.getInt("PcId");
				String customerName = resultSet.getString("CustomerName");
				Date tdDate = resultSet.getDate("BookedTime");
                data.add(new TransactionDetail(id,pcId, customerName, tdDate));
			}
			
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return data;
	}
	public ArrayList<TransactionDetail> getAllTransactionDetailByName(int transactionId, int userId){
		UserController user = new UserController();
		
		String username = user.getUserName(userId);
		ArrayList<TransactionDetail> data = new ArrayList<>();
		Connect connect = Connect.getInstance();
		String query = "SELECT * FROM TransactionDetails WHERE TransactionId = ? AND CustomerName = ?";
		try {
			Connection connection = connect.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, transactionId);
			preparedStatement.setString(2, username);
	        ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("TransactionId");
				int pcId = resultSet.getInt("PcId");
				String customerName = resultSet.getString("CustomerName");
				Date tdDate = resultSet.getDate("BookedTime");
                data.add(new TransactionDetail(id,pcId, customerName, tdDate));
			}
			
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return data;
	}
	
	public int addTransactionHeader(int staffId, String staffName, ArrayList<BookPc> PcBook) {
	    int transactionId = -1;

	    String query = "INSERT INTO TransactionHeaders VALUE(null, ? ,?, ?)";

	    try {
	        Connect c = Connect.getInstance();
	        Connection connection = c.getConnection();

	        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        preparedStatement.setInt(1, staffId);
	        preparedStatement.setString(2, staffName);
	        preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));

	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                transactionId = generatedKeys.getInt(1); 
	                addTransactionDetail(transactionId, PcBook);
	            }
	            generatedKeys.close();
	             showAlert(Alert.AlertType.INFORMATION, "Notification", "Finish Booking Success!");
	        } else {
	            showAlert(Alert.AlertType.ERROR, "Error", "Insert Transaction failed!");
	        }

	        preparedStatement.close();
	        connection.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return transactionId;
	}

	private void addTransactionDetail( int TransactionId ,ArrayList<BookPc> PcBook) {
		String query = "INSERT INTO TransactionDetails VALUE (?,?,?,?)";
		UserController uc = new UserController ();
		for(int i = 0; i<PcBook.size() ;i++ ) {
    		String username = uc.getUserName(PcBook.get(i).getUserId());
    		try {
    	        Connect c = Connect.getInstance();
    	        Connection connection = c.getConnection();
    	        PreparedStatement preparedStatement = connection.prepareStatement(query);
    	        preparedStatement.setInt(1, TransactionId);
    	        preparedStatement.setInt(2, PcBook.get(i).getPcId());
    	        preparedStatement.setString(3, username);
    	        preparedStatement.setDate(4, PcBook.get(i).getBookedDate());

    	        int rowsAffected = preparedStatement.executeUpdate();

    	        if (rowsAffected > 0) {
    
    	        } else {
    	            showAlert(Alert.AlertType.ERROR, "Error", "Insert Transaction detail failed!");
    	        }

    	        preparedStatement.close();
    	        connection.close();
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    	}
	}

	
	private void showAlert(Alert.AlertType alertType, String title, String message) {
	    Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	
	public void showTransactionHeader(int userId, Stage stage, BorderPane bp, String role) {
		ViewTransactionHeader thview = new ViewTransactionHeader(userId);
		thview.showTransactionHeader(stage, bp, role);
	}
	
	public void showTransactionDetail(int thid, int userid, Stage stage, BorderPane bp, String role) {
		ViewTransactionDetail tdview = new ViewTransactionDetail(thid);
		tdview.showTransactionDetail(userid, stage, bp, role);
	}
	
	public TransactionController() {
		// TODO Auto-generated constructor stub
	}

}
