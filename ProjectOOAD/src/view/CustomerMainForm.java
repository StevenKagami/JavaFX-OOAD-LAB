package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class CustomerMainForm {
    Image bgImage;
    ImageView bgImageView;
    Text dateTimeText;

    private void initialize() {
       FileInputStream input = null;
       try {
          input = new FileInputStream("src/assets/5075656.jpg");
       } catch (FileNotFoundException var4) {
     	 var4.printStackTrace();
     	  System.out.println("TEST");
       }

       bgImage = new Image(input);
       bgImageView = new ImageView(this.bgImage);
       bgImageView.setFitWidth(800);
       bgImageView.setFitHeight(600);
       bgImageView.setPreserveRatio(true);
       
       dateTimeText = new Text();
       dateTimeText.setStyle("-fx-font-size: 20px; -fx-border-color: black; -fx-border-width: 1px;");
       updateDateTime();
    }
    
    private void layout(BorderPane bp) {
    	  StackPane stackPane = new StackPane();
          stackPane.getChildren().addAll(bgImageView, dateTimeText);
          StackPane.setAlignment(dateTimeText, javafx.geometry.Pos.CENTER);

          bp.setCenter(stackPane);
    }
   
    public void showCustomerMainForm(BorderPane bp){
    	initialize();
        layout(bp);
        startDateTimeUpdate();
    }
    
	public CustomerMainForm() {
		
	}
	
	private void updateDateTime() {
		 LocalDateTime now = LocalDateTime.now();
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	     String formattedDateTime = formatter.format(now);
	     dateTimeText.setText(formattedDateTime);
	}
	
    private void startDateTimeUpdate() {
        Thread dateTimeThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    updateDateTime();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        dateTimeThread.setDaemon(true);
        dateTimeThread.start();
    }

}
