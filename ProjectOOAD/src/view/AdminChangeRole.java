package view;

import java.util.ArrayList;

import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class AdminChangeRole {
	TableView<User> userTable;
	TableColumn<User, Integer> userId, userAge;
	TableColumn<User, String> userName, role;
	ObservableList<User> data;
	UserController userController;
	
	VBox tableBox;
	GridPane grid;
	Label userIdLabel, changeRoleLable;
	
	TextField changeRoleField, userIdField;
	Button changeRoleButton;
	
	private String userRole, newUserRole;
	private int id = 0;
	
	private ComboBox<String> changeRoleDropdown;
	
	public void initialize() {
		userController = new UserController();
		data = FXCollections.observableArrayList(userController.getAllUser());
		changeRoleDropdown = new ComboBox<>();

		tableBox = new VBox();
		userTable = new TableView<>();
	    
		userId = new TableColumn<User, Integer>("User Id");
		userId.setCellValueFactory(new PropertyValueFactory<>("id"));
	    
		userAge = new TableColumn<User, Integer>("User Age");
		userAge.setCellValueFactory(new PropertyValueFactory<>("age"));
		
		userName = new TableColumn<User, String>("Username");
		userName.setCellValueFactory(new PropertyValueFactory<>("username"));
		
		role = new TableColumn<User, String>("User Role");
		role.setCellValueFactory(new PropertyValueFactory<>("role"));
		
		grid = new GridPane();
		
		changeRoleLable = new Label("New Role : ");
		changeRoleField = new TextField();
		
		userIdLabel = new Label("User Id : ");
		userIdField = new TextField();
		
		changeRoleButton = new Button("Change Role");
	}
	
	@SuppressWarnings("unchecked")
	public void layout(BorderPane bp) {
		userTable.getColumns().addAll(userId,userAge,userName, role);
		userTable.setItems(data);
		userTable.setMaxHeight(300);
		
		changeRoleDropdown.getItems().addAll(
                "Admin",
                "Customer",
                "Computer Technician",
                "Operator"
        );
		
		userIdField.setEditable(false);
		userIdField.setPromptText("Click the table!");
		changeRoleField.setPromptText("Input new role");
		
		grid.setPadding(new Insets(20));
		grid.setHgap(10);
		grid.setVgap(10);
		grid.addRow(0, userIdLabel, userIdField);
		grid.addRow(1, changeRoleLable, changeRoleDropdown);
		grid.add(changeRoleButton, 1, 3);
		
		grid.setAlignment(Pos.CENTER);
		tableBox.setAlignment(Pos.TOP_CENTER);
		tableBox.getChildren().addAll(userTable,grid);
		
		bp.setCenter(tableBox);
	}
	
	
	public void eventControl(Stage primaryStage) {
		changeRoleDropdown.setOnAction(e->{
			this.newUserRole = changeRoleDropdown.getValue();
		});
		
		userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	this.userRole = newSelection.getRole();
            	this.id = newSelection.getId();
            	userIdField.setText(String.valueOf(id));
            }
        });
		
		changeRoleButton.setOnAction(e->{
			userController.changeRole(id, userRole, this.newUserRole);
			loadAllData();
		});
	}
	
	public void loadAllData() {
		ArrayList<User> data = userController.getAllUser();
		userTable.getItems().setAll(data);
	}
	
	public void showAdminChangeRole(Stage primaryStage, BorderPane bp) {
		primaryStage.setResizable(true);
		initialize();
		layout(bp);
		eventControl(primaryStage);
	}
	
	public AdminChangeRole() {
		
	}

}
