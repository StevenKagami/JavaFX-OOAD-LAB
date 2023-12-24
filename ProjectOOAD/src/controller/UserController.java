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
import model.User;
import view.AdminChangeRole;
import view.CustomerMainForm;
import view.Login;
import view.Register;
import view.UserMain;

public class UserController {
	
	public ArrayList<User> getAllUser(){
		ArrayList<User> data = new ArrayList<>();
		Connect connect = Connect.getInstance();
		String query = "SELECT * FROM Users";

		try {
			Connection connection = connect.getConnection();
			Statement stement = connection.createStatement();
			ResultSet resultSet = stement.executeQuery(query);
			
			while (resultSet.next()) {
				int userId = resultSet.getInt("UserId");
				int age = resultSet.getInt("UserAge");
				String userName = resultSet.getString("UserName");
				String userRole = resultSet.getString("UserRole");
				
				String password = resultSet.getString("UserPassword");
                data.add(new User(userName,password,age, userId, userRole));
			}
			
			resultSet.close();
			stement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return data;
	}
	
	public ArrayList<User> getAllTechnician(){
		ArrayList<User> data = new ArrayList<>();
		Connect connect = Connect.getInstance();
		String query = "SELECT * FROM Users WHERE UserRole = 'Computer Technician'";

		try {
			Connection connection = connect.getConnection();
			Statement stement = connection.createStatement();
			ResultSet resultSet = stement.executeQuery(query);
			
			while (resultSet.next()) {
				int userId = resultSet.getInt("UserId");
				int age = resultSet.getInt("UserAge");
				String userName = resultSet.getString("UserName");
				String userRole = resultSet.getString("UserRole");
				
				String password = resultSet.getString("UserPassword");
                data.add(new User(userName,password,age, userId, userRole));
			}
			
			resultSet.close();
			stement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return data;
	}
	
	public UserController() {

	}
	
	public void changeRole(int userId,String role, String newRole) {
		if(userId == 0) {
			showAlert(Alert.AlertType.ERROR, "Error", "User ID cannot empty, select the table");
		}else if(newRole.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Error", "Change Role cannot empty");
		}else if(newRole.equals("Admin")||newRole.equals("Operator")|| newRole.equals("Computer Technician") ||  newRole.equals("Customer") ){
			if(role.equals("Customer")) {
				showAlert(Alert.AlertType.ERROR, "Error", "User with role customer cannot change role");
			}else {
				updateRole(newRole, userId);
				showAlert(Alert.AlertType.INFORMATION, "Success", "Success change role");
			}
		}else {
			showAlert(Alert.AlertType.ERROR, "Error", newRole + " Can't be new role. New Role must Admin, Operator, Computer Technician, and Customer");
		}
	}
	
	public void updateRole(String newRole, int userId) {
		String query = "UPDATE Users SET UserRole = ? WHERE UserId = ?";
		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    
		    preparedStatement.setString(1, newRole);
		    preparedStatement.setInt(2, userId);
		    preparedStatement.executeUpdate();
		    preparedStatement.close();
		    connection.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	
	public void  register(Stage primaryStage, String username, String password, String confirmPassword, String age) {
		String error;
		error = validateRequirement(username, password, confirmPassword, age);
		if(!error.equals("Success register")) {
			showAlert(Alert.AlertType.ERROR, "Error", error);		
		}else {
			registerToDatabase(username, password, age);
			viewLogin(primaryStage);
		}
	}
	
	
	public String validateRequirement(String username, String password, String confirmPassword, String age) {
		if(username.isEmpty()) {
			return "Username cannot be empty";
		}else if(username.length() < 7){
			return "Username minimal 7 character long";
		}else if(!isUsernameUnique(username)) {
			return "Username already taken";
		}
		
		if(password.isEmpty()) {
			return "Password cannot be empty";
		}else if(password.length() < 6) {
			return "Password minimal 7 character long";
		}else if(!isAlphaNumeric(password)) {
			return "Password must be alpha numeric";
		}
		
		if(confirmPassword.isEmpty()) {
			return "Confirm password cannot be empty";
		}else if(!confirmPassword.equals(password)) {
			return "Confirm password is not match";
		}
		if(age.isEmpty()) {
			return "Age cannot be empty";
		}
		try {
		    int ages = Integer.parseInt(age);
		    if(ages < 13 || ages > 65) {
		    	return "Age must be between 13 – 65 (inclusive)";
		    }
		} catch (NumberFormatException e) {

		    return "Age must be numberic";
		}
		
		return "Success register";
		
	}
	
	private boolean isAlphaNumeric(String password) {
		boolean containsLetter = false;
	    boolean containsDigit = false;

	    for (char c : password.toCharArray()) {
	        if (Character.isLetter(c)) {
	            containsLetter = true;
	        } else if (Character.isDigit(c)) {
	            containsDigit = true;
	        }
	        if (containsLetter && containsDigit) {
	            return true;
	        }
	    }

	    return false;
	}
	
	public boolean checkAlNumPassword(String password) {
		boolean alphaPassword = false;
		boolean numericPassword = false;
		
		for(int i=0; i<password.length();i++) {
			if(password.charAt(i) >= 'a' && password.charAt(i) <= 'z' || password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') {
				alphaPassword = true;
			}else if(password.charAt(i) >= '0' && password.charAt(i) <= '9') {
				numericPassword = true;
			}
		}
		
		if(alphaPassword == false || numericPassword == false) {
			return true;
		}else {
			return false;
		}
	}
	 
	private boolean  isUsernameUnique(String username) {
		String query = "SELECT * FROM users WHERE username = ?";
        boolean isUnique = true;
        try {
			Connect c = Connect.getInstance();
            Connection connection = c.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Username already exists
                isUnique = false;
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isUnique;
	}
	
	public String getUserName(int userID) {
		String query = "SELECT * FROM users WHERE UserId = ?";
		String name = null;
        try {
			Connect c = Connect.getInstance();
            Connection connection = c.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                 name = resultSet.getString("UserName");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return name;
	}
	
	public void loginValidate(Stage primaryStage,String username, String password) {
		if(username.isEmpty()) {
			 showAlert(Alert.AlertType.ERROR, "Error",  "Username cannot be empty");
		}else if(password.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Error",  "Password cannot be empty");
		}else {
			Connect c = Connect.getInstance();
			String q = "SELECT * FROM users";
			String em, p, r = "", n = "";
			int id = 0;
			int flag = 0;
			try {
				java.sql.Connection conn = c.getConnection();
				java.sql.Statement stmt = conn.createStatement();
				java.sql.ResultSet rs = stmt.executeQuery(q);
				
				while (rs.next()) {
					em = rs.getString("UserName");
					p = rs.getString("UserPassword");
					
					if (username.equals(em) && password.equals(p)) {
						flag = 1;
						r = rs.getString("UserRole");
						n = rs.getString("UserName");
						id = rs.getInt("UserId");
					}
				}
				
				rs.close();
				stmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			if (flag == 1) {
				if(r.equals("Customer")) {
					showAlert(Alert.AlertType.INFORMATION, "Welcome ", n);
				}else {
					showAlert(Alert.AlertType.INFORMATION, "Welcome ", n+" as " + r);
				}
				primaryStage.close();
				Stage newStage = new Stage();
				UserMain userMain = new UserMain(id, r);
				userMain.viewUserMain(newStage);
			} else {
				showAlert(Alert.AlertType.ERROR, "Error", "Invalid Username or Password!");
			}
		}
	}

	public void registerToDatabase(String username, String password, String age) {
		int ages = Integer.parseInt(age);
		String q = "INSERT INTO users VALUES(null, ?, ?, ?, 'Customer')";

		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    
		    PreparedStatement preparedStatement = connection.prepareStatement(q);
		    preparedStatement.setString(1, username);
		    preparedStatement.setString(2, password);
		    preparedStatement.setInt(3, ages);

		    int rowsAffected = preparedStatement.executeUpdate();

		    if (rowsAffected > 0) {
				showAlert(Alert.AlertType.INFORMATION, "Notification", "Register Success!");	
		    } else {
				showAlert(Alert.AlertType.ERROR, "Error", "Registration failed!");	
		    }

		    preparedStatement.close();
		    connection.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	public boolean isUserTechnician(int userId) {
		String query = "SELECT * FROM Users WHERE UserId = ?";
		try {
			Connect c = Connect.getInstance();
		    Connection connection = c.getConnection();
		    PreparedStatement preparedStatement = connection.prepareStatement(query);
		    preparedStatement.setInt(1, userId);
		    
		    ResultSet resultSet = preparedStatement.executeQuery();
		    
            if (resultSet.next()) {
                if(resultSet.getString("UserRole").equals("Computer Technician")) {
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
	
	private void showAlert(Alert.AlertType alertType, String title, String message) {
	    Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	
	public void viewRegister(Stage primaryStage) {
		primaryStage.close();
		Stage stage = new Stage();
		Register registerView = new Register();
		registerView.showRegister(stage);
	}
	
	public void viewLogin(Stage primaryStage) {
		primaryStage.close();
		Stage stage = new Stage();
		Login loginView = new Login();
		loginView.showLogin(stage);
	}

	public void showHome(BorderPane bp) {
		CustomerMainForm customerMainForm =  new CustomerMainForm();
		customerMainForm.showCustomerMainForm(bp);
	}
	
	public void showAdminChangeRole(Stage primaryStage, BorderPane bp) {
		AdminChangeRole admin =  new AdminChangeRole();
		admin.showAdminChangeRole(primaryStage, bp);
	}


}
