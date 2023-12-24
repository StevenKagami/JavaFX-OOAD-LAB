package view;

import java.util.Optional;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Register {
	Stage primaryStage;
	Scene registerScene;
	
	BorderPane bp;
	GridPane gp;
	FlowPane fp;
	HBox hb;
	VBox vb;
	
	Text titleTxt, accountTxt;
	Label nameLbl, passwordLbl, confirmPassLbl,ageLbl;
	TextField nameField, emailField, ageField;
	PasswordField passwordField, confirmPassField;
	CheckBox checkBox;
	Hyperlink loginLink;
	Button registerBtn;
	
	Font font1, font2;
	Alert alertMessage;
	Optional<ButtonType> result;

	public void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		fp = new FlowPane();
		
		font1 = Font.font("Verdana", FontWeight.EXTRA_BOLD, 20);
		font2 = Font.font("Verdana", FontWeight.BOLD, 12);
		
		titleTxt = new Text("Register");
		titleTxt.setFont(font1);
		
		nameLbl = new Label("User Name");
		nameField = new TextField();
		nameField.setPromptText("User Name");
		nameField.setMaxWidth(300);
		
		passwordLbl = new Label("Password");
		passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		passwordField.setMaxWidth(300);
		
		confirmPassLbl = new Label("Confirm Password");
		confirmPassField = new PasswordField();
		confirmPassField.setPromptText("Confirm Password");
		confirmPassField.setMaxWidth(300);
		
		ageLbl = new Label("Age");
		ageField = new TextField();
		ageField.setPromptText("Age");
		ageField.setMaxWidth(300);
		
		checkBox = new CheckBox("Agree to the terms and conditions");
		
		registerBtn = new Button("Register");
		registerBtn.setMaxWidth(300);
		registerBtn.setMinHeight(35);
		registerBtn.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, null, null)));
		registerBtn.setFont(font2);
		registerBtn.setTextFill(Color.WHITE);
		
		accountTxt = new Text("Already have an account?");
		
		loginLink = new Hyperlink("Login");
		
		registerScene = new Scene(bp, 500, 700);
	}
	
	private void layout() {
		gp.add(nameLbl, 0, 0);
		gp.add(nameField, 1, 0);

		gp.add(passwordLbl, 0, 1);
		gp.add(passwordField, 1, 1);

		gp.add(confirmPassLbl, 0, 2);
		gp.add(confirmPassField, 1, 2);

		gp.add(ageLbl, 0, 3); 
		gp.add(ageField, 1, 3);

		gp.add(checkBox, 1, 4);
		gp.add(registerBtn, 1, 5);

		
		gp.setHgap(20);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		
		vb = new VBox(titleTxt, gp, accountTxt, loginLink);
		VBox.setMargin(titleTxt, new Insets(35));
		VBox.setMargin(accountTxt, new Insets(35, 0, 15, 0));
		vb.setAlignment(Pos.CENTER);
		bp.setCenter(vb);
	}
	private void eventHandler(Stage primaryStage, Button registerBtn, Hyperlink loginLink) {
		UserController userController = new UserController();
		registerBtn.setOnAction(e -> {
			String username = this.nameField.getText();
		    String password = this.passwordField.getText();
		    String confirmPassword = this.confirmPassField.getText();
		    String age = this.ageField.getText();
			userController.register(primaryStage,username, password, confirmPassword, age);
        });
		
		loginLink.setOnMouseClicked(event -> {
		    if (event.getSource() == loginLink) {
		    	userController.viewLogin(primaryStage);
		    }
		});
		
	}
	
	public void eventControl(Stage primaryStage) {
		Button registerBtn = this.registerBtn;
		Hyperlink loginLink = this.loginLink;
		eventHandler(primaryStage,registerBtn, loginLink);

	}
	
	public void showRegister(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initialize();
		layout();
		eventControl(primaryStage);
		primaryStage.setResizable(true);
		primaryStage.getIcons().add(new Image("file:src/assets/book.png"));
		primaryStage.setTitle("Internet CLafes");
		primaryStage.setScene(this.registerScene);
		primaryStage.show();
	}
	
	public Register() {
		
	}
	

}
