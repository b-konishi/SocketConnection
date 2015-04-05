package konishi.java.socketconnection.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ServerView extends Application {
	private Stage stage = null;
	private AnchorPane root = null;
		
	public void initialize() {
		launch();
	}
	
	@Override
	public void start(Stage _stage) throws Exception {
		stage = _stage;
		stage.setTitle("Server Application");
//		stage.setFullScreen(true);
		
		root = FXMLLoader.load(getClass().getResource("server.fxml"));
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		stage.show();
		
		
	}
	
	@Override
	public void stop() {
		System.exit(0);
	}
}
