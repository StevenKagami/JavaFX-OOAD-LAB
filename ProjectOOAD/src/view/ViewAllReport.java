package view;

import java.sql.Date;

import controller.ReportController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import model.Report;

public class ViewAllReport {

	TableView<Report> reportTable;
	TableColumn<Report, Integer>reportId, PcId;
	
	TableColumn<Report, String> userRole,note;
	TableColumn<Report, Date> reportDate;
	
	ObservableList<Report> data;
	
	ReportController reportController ;
	
	public void initialize() {
		reportController = new ReportController();
		data = FXCollections.observableArrayList(reportController.getAllReport());

		reportTable = new TableView<>();
	    reportTable.setPrefSize(1000, 250); 
	    
	    reportId = new TableColumn<Report,Integer>("ReportId");
	    reportId.setCellValueFactory(new PropertyValueFactory<>("reportId"));
	    
	    PcId = new TableColumn<Report,Integer>("PC ID");
	    PcId.setCellValueFactory(new PropertyValueFactory<>("pcId"));
	    
	    note = new TableColumn<Report,String>("Note");
	    note.setCellValueFactory(new PropertyValueFactory<>("reportNote"));
	    
	    userRole = new TableColumn<Report,String>("User Role");
	    userRole.setCellValueFactory(new PropertyValueFactory<>("userRole"));
	    
	    reportDate = new TableColumn<Report,Date>("Report Date");
	    reportDate.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
	}
	
	@SuppressWarnings("unchecked")
	public void layout(BorderPane bp) {
		reportTable.getColumns().addAll(reportId, userRole, PcId, note, reportDate);
		reportTable.setItems(data);

		bp.setCenter(reportTable);
	}
	
	public void showViewAllReport(BorderPane bp) {
		initialize();
		layout(bp);
	}
	
	public ViewAllReport() {
		
	}

}
