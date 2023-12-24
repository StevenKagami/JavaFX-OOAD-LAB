package main;
import javafx.application.Application;
import javafx.stage.Stage;
import view.Login;

public class Main extends Application{


	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		Login loginView = new Login();
		loginView.showLogin(primaryStage);
	}
}
