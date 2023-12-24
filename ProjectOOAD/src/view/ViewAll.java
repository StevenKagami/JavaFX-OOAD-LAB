package view;

import controller.PcController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Pc;

public class ViewAll {

	TableView<Pc> pcTable;
	TableColumn<Pc, Integer> idColumn;
	TableColumn<Pc, String> statusColumn;
	ObservableList<Pc> data;
	Pc pc;
	PcController pcController ;
	
	private String role;
	private void initialize() {
		pcController = new PcController();
		data = FXCollections.observableArrayList(pcController.getAllPc());

	    pcTable = new TableView<>();
	    pcTable.setPrefSize(1000, 250); 
	    
	    idColumn = new TableColumn<Pc, Integer>("Id");
	    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
	    
	    statusColumn = new TableColumn<Pc, String>("Status");
	    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
	}
	
	@SuppressWarnings("unchecked")
	private void layout(BorderPane bp) {
		pcTable.getColumns().addAll(idColumn,statusColumn);
		pcTable.setItems(data);
		bp.setCenter(pcTable);
	}
	
	private void eventControl(Stage primaryStage, BorderPane bp) {
		pcTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	// Show detail pc
            	int Pcid = newSelection.getId();
            	String status = newSelection.getStatus();
            	pcController.viewDetailPc(Pcid, status, role, bp, primaryStage);
            }
        });
	}

	public ViewAll(String role) {
		this.role = role;
	}
	
	public void showViewAll(Stage primaryStage, BorderPane bp) {
		primaryStage.setResizable(true);
		initialize();
		layout(bp);
		eventControl(primaryStage, bp);

	}

}
