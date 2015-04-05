package konishi.java.socketconnection.controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	
	private static final String MAP_FILE = StoreData.SERVER_MAP_FILE;
	
	private TransmitServer server = null;
	private AnimationTimer timer = null;
	
	private int[] coordinate = new int[3];
			
	// #initializeとすると初期値設定メソッドのオーバーライドだと思われてしまうことに注意
	public void init() throws Exception {
		server = new TransmitServer();
		stackTrace();
		setRootMap(root_map);
		server.clearFile(MAP_FILE);
		
		timer = new AnimationTimer() {	
			@Override
			public void handle(long now) {
				try {
					if (ReceiveModel.isUpdatedListToDraw) {
						coordinate = parseIntArray(stringSeparator(ReceiveModel.data));
						stackTrace(coordinate[0] + " " + coordinate[1] + " " + coordinate[2]);	
						drawFigure(coordinate[0], coordinate[1], coordinate[2]);
						ReceiveModel.isUpdatedListToDraw = false;
					}

				} catch (Exception e) {
//					errorStackTrace(e);
				}
			}
		};
		timer.start();
		
	}
		
	/**
	 * マウスクリック時の動作を定義します。
	 * @param event マウスクリック時に受け取るイベント
	 */
	@FXML public void handleMouseAction(MouseEvent event) throws Exception {
		if (mapFrag != 0) {
			
			ReceiveModel.data = mapFrag + ":" + (int)event.getX() + ":" + (int)event.getY();
			stackTrace(ReceiveModel.data);			
			ReceiveModel.isUpdatedListToDraw = true;
			
//			server.writeFile(MAP_FILE, list);
//			server.writeObject(list);
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
			connection_button.setText("Hello");
			init();
			break;
		case "disconnection_button":
			connection_button.setText("Connection");
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
			
		case "submit1_button":
			break;
		case "submit2_button":
			break;
		case "submit3_button":
			break;
		case "submit4_button":
			break;
		}
	}
}
