package view;

import controller.UserController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class Login {
	Stage primaryStage;
	Scene loginScene;
	
	BorderPane bp;
	GridPane gp;
	VBox vb;
	
	Text titleTxt, accountTxt;
	Label userNameLbl, passwordLbl;
	TextField userNameField;
	PasswordField passwordField;
	Hyperlink registerLink;
	Button loginBtn;
	
	Font font1, font2;
	Alert alertMessage;
	String username,password;
	
	private void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		
		font1 = Font.font("Verdana", FontWeight.EXTRA_BOLD, 20);
		font2 = Font.font("Verdana", FontWeight.BOLD, 12);
		
		titleTxt = new Text("Login");
		titleTxt.setFont(font1);
		
		userNameLbl = new Label("User Name");
		userNameField = new TextField();
		userNameField.setPromptText("User Name");
		userNameField.setMaxWidth(300);
		
		passwordLbl = new Label("Password");
		passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		passwordField.setMaxWidth(300);
		
		loginBtn = new Button("Login");
		loginBtn.setMinWidth(300);
		loginBtn.setMinHeight(35);
		loginBtn.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));
		loginBtn.setFont(font2);
		loginBtn.setTextFill(Color.WHITE);
		
		accountTxt = new Text("Don't have an account?");
	
		registerLink = new Hyperlink("Register");

		loginScene = new Scene(bp, 500, 700);
	}
	
	private void layout() {
		gp.add(userNameLbl, 0, 0);
		gp.add(userNameField, 1, 0);
		gp.add(passwordLbl, 0, 1);
		gp.add(passwordField, 1, 1);
		
		gp.add(loginBtn, 1, 2);
		
		gp.setHgap(20);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		
		vb = new VBox(titleTxt, gp, accountTxt, registerLink);
		VBox.setMargin(titleTxt, new Insets(25));
		VBox.setMargin(accountTxt, new Insets(35, 0, 15, 0));
		vb.setAlignment(Pos.CENTER);

		bp.setCenter(vb);
	}	
	
	
	private void eventHandler(Stage primaryStage, Button loginBtn, Hyperlink registerLink) {
		UserController userController = new UserController();
		loginBtn.setOnAction(e -> {
			String username = this.userNameField.getText();
		    String password = this.passwordField.getText();
			userController.loginValidate(primaryStage,username, password);
        });
		
		registerLink.setOnMouseClicked(event -> {
		    if (event.getSource() == registerLink) {
		    	userController.viewRegister(primaryStage);
		    }
		});
	}
	
	public void eventControl(Stage primaryStage) {
		Button loginBtn = this.loginBtn;
		Hyperlink registerLink = this.registerLink;
		eventHandler(primaryStage,loginBtn, registerLink);
	}
	
	public void showLogin(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initialize();
		layout();
		eventControl(primaryStage);
		
		primaryStage.setResizable(true);
		primaryStage.getIcons().add(new Image("file:src/assets/book.png"));
		primaryStage.setTitle("Internet CLafes");
		primaryStage.setScene(loginScene);
		primaryStage.show();
	}
	
	public Login() {

	}
	
}
