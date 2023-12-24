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
import view.CustomerBookPc;
import view.OperatorPcBookedData;

public class BookController {
	
	private PcController pcController = new PcController();
	
	public ArrayList<BookPc> getAllBookPc(){
		 ArrayList<BookPc> data = new ArrayList<>();
		Connect connect = Connect.getInstance();
		String query = "SELECT * FROM bookpcs";

		try {
			Connection connection = connect.getConnection();
			Statement stement = connection.createStatement();
			ResultSet resultSet = stement.executeQuery(query);
			
			while (resultSet.next()) {
				int bookId = resultSet.getInt("BookId");
				int pcId = resultSet.getInt("PcId");
				int userId = resultSet.getInt("UserId");
				Date bookedDate = resultSet.getDate("BookedDate");
                data.add(new BookPc(bookId, pcId, userId, bookedDate));
			}
			
			resultSet.close();
			stement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return data;
	}
	
	
	
	public void bookDate(String id,String status,  LocalDate date, int userid) {
		
		LocalDate yesterday = LocalDate.now().minusDays(1);
		if(id.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Error", "Id cannot be empty, Click the table to get id!");
			return;
		}else if(date == null) {
			showAlert(Alert.AlertType.ERROR, "Error", "Date cannot be empty");	
			return;
		}else if(date.isBefore(yesterday)) {
	        showAlert(Alert.AlertType.ERROR, "Error", "The date in the book cannot be in the past.");
	        return;
	    }
		Date bookedDate = Date.valueOf(date);
	    if(!isAvaiable(bookedDate, Integer.parseInt(id))) {
	    	showAlert(Alert.AlertType.ERROR, "Error", "At that time Pc is booked");
	        return;
	    }
		if(!status.equals("Usable")) {
			showAlert(Alert.AlertType.ERROR, "Error", "Pc is " + status);
		}else {
			int ids = Integer.parseInt(id);
			String q = "INSERT INTO bookpcs VALUES(null, ?, ?, ?)";
			
			try {
				Connect c = Connect.getInstance();
			    Connection connection = c.getConnection();
			    
			    PreparedStatement preparedStatement = connection.prepareStatement(q);
			    Date sqlDate = Date.valueOf(date);
			    preparedStatement.setInt(1, ids);
			    preparedStatement.setInt(2, userid);
			    preparedStatement.setDate(3, sqlDate);
			    
			    int rowsAffected = preparedStatement.executeUpdate();

			    if (rowsAffected > 0) {
					showAlert(Alert.AlertType.INFORMATION, "Notification", "Book Success!");	
			    } else {
					showAlert(Alert.AlertType.ERROR, "Error", "Book failed!");	
			    }

			    preparedStatement.close();
			    connection.close();
			} catch (SQLException e) {
			    e.printStackTrace();
			}
		}
	}
	
	public boolean isAvaiable(  Date date, int Pcid) {
		boolean isAble = true;
		String query = "SELECT * From BookPcs WHERE BookedDate = ? AND PcId = ?";
		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		  
		    preparedStatement.setDate(1, date);
		    preparedStatement.setInt(2, Pcid);
		    ResultSet resultSet = preparedStatement.executeQuery();
		    
		    if (resultSet.next()) {
	            isAble = false;
	        } 
		    preparedStatement.close();
		    connection.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		return isAble;
	}
	
	public void changePc(int bookId, int pcId, String searchPc ,Date date) {
		if(bookId == 0 ) {
			showAlert(Alert.AlertType.ERROR, "Error", "Job ID cannot empty!");	
			return;
		}
		if(searchPc.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Error", "Search PC ID cannot empty!");	
			return;
		}
		
		int searchPcId = Integer.parseInt(searchPc);
	
		if(!isAvaiable(date, searchPcId)){
	    	showAlert(Alert.AlertType.ERROR, "Error", "At that time Pc "+ searchPcId +" is booked");
	    }
		else{
			if(pcController.searchPCStatus(searchPcId)){
				updateBookPc(bookId, searchPcId);
				showAlert(Alert.AlertType.INFORMATION, "Success", "Success change seat");
			}
		}
	}
	
	public void updateBookPc(int bookId, int pcId) {
		String q = "UPDATE BookPcs SET pcId = (?) where BookId = (?)";
		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    PreparedStatement preparedStatement = connection.prepareStatement(q);
		    
		    preparedStatement.setInt(1, pcId);
		    preparedStatement.setInt(2, bookId);
		    preparedStatement.executeUpdate();
		    preparedStatement.close();
		    connection.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	
	public void cancelBooking(LocalDate date) {
		if(date == null) {
			showAlert(Alert.AlertType.ERROR, "Error", "Cancel date cannot empty");
			return;
		}else if(date.isBefore(LocalDate.now())) {
			showAlert(Alert.AlertType.ERROR, "Error", "Booking date must more than date now!");
			return;
		} 
		Date bookedDate = Date.valueOf(date);
		String query = "DELETE FROM BookPcs WHERE BookedDate <= ?";
		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setDate(1, bookedDate);

		    int rowsAffected = preparedStatement.executeUpdate();

		    if (rowsAffected > 0) {
				showAlert(Alert.AlertType.INFORMATION, "Notification", "Cancel booking Success!");	
		    } else {
				showAlert(Alert.AlertType.ERROR, "Error", "Cancel failed booking date doesn't exist!");	
		    }

		    preparedStatement.close();
		    connection.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	public void deleteBooking(LocalDate date) {
	    Date bookedDate = Date.valueOf(date);
	    String query = "DELETE FROM BookPcs WHERE BookedDate = ?";
	    try {
	        Connect c = Connect.getInstance();
	        Connection connection = c.getConnection();

	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setDate(1, bookedDate);

	        int rowsAffected = preparedStatement.executeUpdate(); // Execute the delete query

	        if (rowsAffected > 0) {
	            System.out.println("Bookings for date " + date + " deleted successfully.");
	        } else {
	            System.out.println("No bookings found for date " + date + ".");
	        }

	        preparedStatement.close();
	        connection.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void finishBooking(LocalDate date, int StaffId ) {
		if(date == null) {
			showAlert(Alert.AlertType.ERROR, "Error", "Finish date cannot empty");
			return;
		}else if(date.isBefore(LocalDate.now())) {
			showAlert(Alert.AlertType.ERROR, "Error", "Cant finish past date!!");
			return;
		} 
		
		Date bookDate = Date.valueOf(date);
		UserController us = new UserController();
		String staffName = us.getUserName(StaffId);
		String query = "SELECT * FROM BookPcs WHERE BookedDate = ?";
		
		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		  
		    preparedStatement.setDate(1, bookDate);
		   
		    ResultSet resultSet = preparedStatement.executeQuery();
		    TransactionController tc = new TransactionController();;
		    ArrayList<BookPc> pcBook = new ArrayList<>();
		    while (resultSet.next()){
		    	int bookId = resultSet.getInt("BookId");
	            int pcId = resultSet.getInt("PcId");
	            int userId = resultSet.getInt("UserId");
	            Date bookedDate = resultSet.getDate("BookedDate");
	            
	            pcBook.add(new BookPc(bookId,pcId,userId,bookedDate));
	         
	        }
		    
		    if(pcBook.isEmpty()) {
		    	showAlert(Alert.AlertType.ERROR, "Error", "BookDate Invalid!, please input the exist date");
		    }else {
		    	tc.addTransactionHeader(StaffId, staffName, pcBook);
		    	deleteBooking(date);
		    }
		    
		    preparedStatement.close();
		    connection.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	
	private void showAlert(Alert.AlertType alertType, String title, String message) {
	    Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	
	public void viewCustomerBookPc(int id, Stage primaryStage, BorderPane bp ) {
		CustomerBookPc bookPc = new CustomerBookPc(id);
		bookPc.showViewBookPc(primaryStage,bp);
	}
	
	public void viewPcBookData(Stage primaryStage, BorderPane bp, int staffId ) {
		OperatorPcBookedData view = new OperatorPcBookedData(staffId);
		view.showPcBookDate(primaryStage, bp);
	}
	
	public BookController() {
		
	}

}
