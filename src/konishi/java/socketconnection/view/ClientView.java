package konishi.java.socketconnection.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * クライアント画面のFXMLファイルが読み込まれるクラスです。
 * @author konishi
 *
 */
public class ClientView extends Application {
	
	public void initialize() {
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Client Application");
//		stage.setFullScreen(true);
		
		AnchorPane root = FXMLLoader.load(getClass().getResource("client.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		stage.show();
	}
	@Override
	public void stop() {
		System.exit(0);
	}
}
