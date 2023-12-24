package view;

import controller.BookController;
import controller.JobController;
import controller.PcController;
import controller.ReportController;
import controller.TransactionController;
import controller.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UserMain {
	Stage primaryStage;
	Scene scene;
	BorderPane bp;
	MenuBar menuBar;
	Menu userMenu, computerMenu,transactionMenu,reportMenu,technicianMenu,operatorMenu, manageMenu;
	MenuItem home,
	logOut, viewAllPc, bookPc , makeReport, 
	viewTechnicianJob,completeJob,
	changeRole, pcManagement, jobManagement, viewAllReport, viewAllTransactionHistory, viewPcBookedData, cancelBook,finishBook,assignUser;
	
	PcController pcController ;
	UserController userController;
	BookController bookController;
	ReportController reportController;
	TransactionController transactionController;

	JobController jobController;
	Hyperlink bookPcLink;
	
	private int userId;
	private String role;
	
	private void initialize() {
		pcController = new PcController();
		userController = new UserController();
		bookController = new BookController();
		reportController = new ReportController();
		jobController = new JobController();
		transactionController = new TransactionController();
		bp = new BorderPane();
		menuBar = new MenuBar();

		userMenu = new Menu("User");
	    computerMenu = new Menu("Computer");
	    transactionMenu = new Menu("Transaction");
	    reportMenu = new Menu("Report");
	    technicianMenu = new Menu("Technician");
	    operatorMenu = new Menu("Operator");
	    manageMenu = new Menu("Manage");
	    
	    home = new MenuItem("Home");
	    logOut = new MenuItem("Logout");
	    viewAllPc = new MenuItem("View All PC");
	    
	    bookPc = new MenuItem("Book PC");
	    makeReport = new MenuItem("Report");
	    
		viewTechnicianJob = new MenuItem("Technician Job");
		completeJob = new MenuItem("Complete Job");
		
		viewPcBookedData = new MenuItem("Pc Booked");
		
		changeRole = new MenuItem("Change Role");
		pcManagement = new MenuItem("PC Management");
		jobManagement = new MenuItem("job Management");
		viewAllReport = new MenuItem("View All Report");
		viewAllTransactionHistory = new MenuItem("View Transaction History");
		
	    scene = new Scene(bp, 600, 600);
	}
	
	private void layout() {
		if(role.equals("Customer")) {
			menuBar.getMenus().addAll(userMenu, computerMenu, transactionMenu, reportMenu);
			userMenu.getItems().addAll(home, viewAllPc,logOut);
			computerMenu.getItems().addAll(bookPc);
			transactionMenu.getItems().add(viewAllTransactionHistory);
			reportMenu.getItems().addAll(makeReport);
		
		}else if(role.equals("Computer Technician")) {
			menuBar.getMenus().addAll(userMenu, technicianMenu);
			userMenu.getItems().addAll(home,  viewAllPc,logOut);
			 technicianMenu.getItems().addAll(viewTechnicianJob);
			
		}else if(role.equals("Operator")) {
			menuBar.getMenus().addAll(userMenu, operatorMenu, reportMenu);
			userMenu.getItems().addAll(home,  viewAllPc,logOut);
			operatorMenu.getItems().addAll(viewPcBookedData);
			reportMenu.getItems().addAll(makeReport);
			
		}else if(role.equals("Admin")) {
			menuBar.getMenus().addAll(userMenu, manageMenu, transactionMenu, reportMenu);
			userMenu.getItems().addAll(home, viewAllPc, logOut);
			manageMenu.getItems().addAll( changeRole, jobManagement);
			transactionMenu.getItems().add(viewAllTransactionHistory);
			reportMenu.getItems().addAll(viewAllReport);
		}
		bp.setTop(menuBar);
		userController.showHome(bp);
	}
	
	private void eventControl(Stage primaryStage) {
		home.setOnAction(e->{
			clearBp();
			userController.showHome(bp);
		});
		logOut.setOnAction(e->{
			userController.viewLogin(primaryStage);
		});
		viewAllPc.setOnAction(e->{
			clearBp();
			pcController.viewAll(primaryStage, bp, role);	
		});
		bookPc.setOnAction(e->{
			clearBp();
			bookController.viewCustomerBookPc(userId, primaryStage, bp);
		});
		viewPcBookedData.setOnAction(e->{
			clearBp();
			bookController.viewPcBookData(primaryStage, bp, userId);
		});
		makeReport.setOnAction(e->{;
			clearBp();
			reportController.showReport(primaryStage, bp, userId, role);		
		});
		viewAllReport.setOnAction(e->{
			clearBp();
			reportController.viewAllReport(bp);
		});
		viewTechnicianJob.setOnAction(e->{
			clearBp();
			jobController.showTechnicianJob(primaryStage, bp, userId);
		});
		changeRole.setOnAction(e->{
			clearBp();
			userController.showAdminChangeRole(primaryStage, bp);
		});
		jobManagement.setOnAction(e->{
			clearBp();
			jobController.showJobManagement(primaryStage, bp);
		});
		viewAllTransactionHistory.setOnAction(e->{
			clearBp();
			transactionController.showTransactionHeader(userId, primaryStage, bp, role);
		});
	}
	
	private void clearBp() {
		bp.setCenter(null);
		bp.setBottom(null);
	}
	
	public UserMain(int userId, String role) {
		this.userId = userId;
		this.role = role;
	}
	
	public void viewUserMain(Stage primaryStage) {
		initialize();
		layout();
		eventControl(primaryStage);
		
		primaryStage.setResizable(true);
	    primaryStage.setTitle("Internet CLafes");
	    primaryStage.getIcons().add(new Image(getClass().getResource("/assets/book.png").toExternalForm()));
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}

}
