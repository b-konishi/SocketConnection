package konishi.java.socketconnection.controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import konishi.java.socketconnection.base.ControllerBase;
import konishi.java.socketconnection.main.TransmitServer;
import konishi.java.socketconnection.model.ReceiveModel;
import konishi.java.socketconnection.model.StoreData;

/**
 * サーバー側のコントローラーです。
 * 
 * @version 1.0.0
 * @author konishi
 * @see ClientController
 */
public class ServerController extends ControllerBase {
	
	@FXML public AnchorPane root_map;
	
	@FXML public Button connection_button;
	@FXML public Button disconnection_button;
	
	@FXML public Button front_camera1_button;
	@FXML public Button back_camera1_button;
	@FXML public Button arm_camera1_button;
	
	@FXML public Button front_camera2_button;
	@FXML public Button back_camera2_button;
	@FXML public Button arm_camera2_button;
	
	@FXML public Button front_camera3_button;
	@FXML public Button back_camera3_button;
	@FXML public Button arm_camera3_button;
	
	@FXML public Button front_camera4_button;
	@FXML public Button back_camera4_button;
	@FXML public Button arm_camera4_button;
	
	@FXML public Button submit1_button;
	@FXML public Button submit2_button;
	@FXML public Button submit3_button;
	@FXML public Button submit4_button;
	
	@FXML public Button robot_map_button;
	@FXML public Button rubble_map_button;
	@FXML public Button doll_map_button;
	@FXML public Button dust_map_button;
	
	@FXML public Button shutdown_button;
	
	@FXML public Label ip_adress;
	@FXML public Label ip_adress1;
	@FXML public Label ip_adress2;
	@FXML public Label ip_adress3;
	@FXML public Label ip_adress4;
	
	
	
//	private static final String MAP_FILE = StoreData.SERVER_MAP_FILE;
	
	private TransmitServer server = null;
	private AnimationTimer timer = null;
	
	private int clientValue = 0;
				
	public void initialize() {
		timer = new AnimationTimer() {	
			@Override
			public void handle(long now) {
				portPrinter();
				mapPainter(StoreData.DEFAULT_MAP_ITEM_SIZE, StoreData.DEFAULT_MAP_ITEM_SIZE);
			}
		};
		timer.start();
	}
	
	/**
	 * connectボタンを押した際の初期処理です。
	 * #initializeとは違うことに注意してください。
	 * @throws Exception
	 */
	public void firstConnection() throws Exception {
		stackTrace(StoreData.PORT);
		server = new TransmitServer();
		stackTrace();
		setRootMap(root_map);
		
		ip_adress.setText(server.getPort());
	}
	
	/**
	 * サーバー画面のポート表示を管理します。
	 * クライアントが切断された時、自動的に対象から外します。
	 */
	public void portPrinter() {
		Label[] clientAdress = {ip_adress1, ip_adress2, ip_adress3, ip_adress4};
		
		while (clientValue > ReceiveModel.clientValue) {
			clientAdress[--clientValue].setText("未設定");
		}
		clientValue = ReceiveModel.clientValue;
		for (int i = 0; i < clientValue; i++) {
			if (clientAdress[i].getText().equals("未設定")) {
				clientAdress[i].setText(ReceiveModel.clientAdress.get(i));
			}
		}
	}
	
	/**
	 * マウスクリック時の動作を定義します。
	 * @param event マウスクリック時に受け取るイベント
	 */
	@FXML public void handleMouseAction(MouseEvent event) throws Exception {
		if (mapFrag != 0 && ReceiveModel.clientValue >= 0) {
			ReceiveModel.data = stringMapEventAgent(StoreData.LOCAL, mapFrag, (int)event.getX(), (int)event.getY());
			ReceiveModel.sendData = stringMapEventAgent(StoreData.REMOTE_CLIENT, mapFrag, (int)event.getX(), (int)event.getY());
			ReceiveModel.isUpdated = true;
		}
	}
	
	/**
	 * ボタンアクションを読み取ります。
	 * @param event イベントアクション
	 * @throws Exception エラー
	 */
	@FXML public void handleButtonAction(ActionEvent event) throws Exception {
		switch (getId(event.toString())) {
		case "connection_button":
			connection_button.setText("Connecting...");
			firstConnection();
			clientValue = 0;
			break;
		case "disconnection_button":
			connection_button.setText("NotConnection");
			ReceiveModel.emergencyStopSignal = true;
			stackTrace(clientValue);
			if (ReceiveModel.clientValue == 0)
				server.closeTransport();
			break;
			
		case "front_camera1_button":
			break;
		case "back_camera1_button":
			break;
		case "arm_camera1_button":
			break;
			
		case "front_camera2_button":
			break;
		case "back_camera2_button":
			break;
		case "arm_camera2_button":
			break;
			
		case "front_camera3_button":
			break;
		case "back_camera3_button":
			break;
		case "arm_camera3_button":
			break;
			
		case "front_camera4_button":
			break;
		case "back_camera4_button":
			break;
		case "arm_camera4_button":
			break;
			
		case "robot_map_button":
			if (mapFrag != 1)	mapFrag = 0;
			mapFrag ^= 1;
			break;
		case "rubble_map_button":
			if (mapFrag != 2)	mapFrag = 0;
			mapFrag ^= 2;
			break;
		case "doll_map_button":
			if (mapFrag != 3)	mapFrag = 0;
			mapFrag ^= 3;
			break;
		case "dust_map_button":
			if (mapFrag != 4)	mapFrag = 0;
			mapFrag ^= 4;
			break;
			
		case "submit1_button":
			break;
		case "submit2_button":
			break;
		case "submit3_button":
			break;
		case "submit4_button":
			break;
			
		case "shutdown_button":
			ReceiveModel.shutdownSignal = true;
			break;
		}
	}
}
