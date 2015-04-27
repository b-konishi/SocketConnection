package konishi.java.socketconnection.controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import konishi.java.socketconnection.base.ControllerBase;
import konishi.java.socketconnection.main.TransmitClient;
import konishi.java.socketconnection.model.ReceiveModel;
import konishi.java.socketconnection.model.StoreData;


/**
 * クライアント側のコントローラーです。
 * 
 * @version 1.0.0
 * @author konishi
 * @see ServerController
 */
public class ClientController extends ControllerBase {
	
	@FXML public AnchorPane root_map;
	
	@FXML public Button connection_button;
	@FXML public Button disconnection_button;
	
	@FXML public Button front_camera_button;
	@FXML public Button back_camera_button;
	@FXML public Button arm_camera_button;
	
	@FXML public Button robot_map_button;
	@FXML public Button rabble_map_button;
	@FXML public Button doll_map_button;
	
	@FXML public Button l2_controller_button;
	@FXML public Button l1_controller_button;
	@FXML public Button r2_controller_button;
	@FXML public Button r1_controller_button;
	@FXML public Button right_controller_button;
	@FXML public Button left_controller_button;
	@FXML public Button up_controller_button;
	@FXML public Button down_controller_button;
	@FXML public Button circle_controller_button;
	@FXML public Button cross_controller_button;
	@FXML public Button triangle_controller_button;
	@FXML public Button rectangle_controller_button;
	
	@FXML public Button submit_button;
	
	@FXML public Button shutdown_button;
	
	@FXML public Label ip_adress;
	@FXML public Label emergency_stop_signal;
	
	@FXML public TextField input_server_adress;
	
	private static final int CLIENT_MAP_ITEM_SIZE = StoreData.DEFAULT_MAP_ITEM_SIZE / StoreData.MAP_MAGNIFICATION;
	
	private TransmitClient client = null;
	
	public void initialize() {
		
		AnimationTimer timer = new AnimationTimer() {	
			@Override
			public void handle(long now) {
				if (ReceiveModel.emergencyStopSignal) {
					emergencyDisposal();
				} else {
					mapPainter(CLIENT_MAP_ITEM_SIZE, CLIENT_MAP_ITEM_SIZE);
				}
			}
		};
		timer.start();
	}
	
	/**
	 * 初期化処理を記述するメソッドです。
	 */
	public void firstConnection() throws Exception {
		client = new TransmitClient();
		
		setRootMap(root_map);
		
		ip_adress.setText(client.getPort());
	}
	
	/**
	 * 緊急時の処理をおこないます。
	 * 緊急時とは、サーバーの通信が途絶えた時、緊急時ストップシグナルフラグが立ち、
	 * 処理をおこなわれます。
	 */
	public void emergencyDisposal() {
		stackTrace();
		emergency_stop_signal.setText("サーバー停止");
		connection_button.setText("NotConnection");
		client.write("CLOSE");
		ReceiveModel.emergencyStopSignal = false;
		try {
			client.closeTransport();
		} catch (IOException e) {}
	}
	
	/**
	 * マウスクリック時の動作を定義します。
	 * @param event マウスクリック時に受け取るイベント
	 */
	@FXML public void handleMouseAction(MouseEvent event) throws Exception {
		if (mapFrag != 0) {
			ReceiveModel.data = stringMapEventAgent(StoreData.LOCAL, mapFrag, (int)event.getX(), (int)event.getY());
			ReceiveModel.sendData = stringMapEventAgent(StoreData.REMOTE_SERVER + client.getPort(), mapFrag, (int)event.getX(), (int)event.getY());
			client.write(ReceiveModel.sendData);
			ReceiveModel.isUpdated = true;
		}
	}

	@FXML public void handleKeyAction(KeyEvent event) {
		if (KeyCode.ENTER.equals(event.getCode())) {
			ArrayList<String> text = stringSeparator(input_server_adress.getText(), ":");
			
			StoreData.HOST_NAME = text.get(0);
			StoreData.PORT = Integer.parseInt(text.get(1));
			System.out.println(input_server_adress.getText());
			
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
			emergency_stop_signal.setText("");
			connection_button.setText("Connecting...");
			firstConnection();
			break;
		case "disconnection_button":
			connection_button.setText("NotConnection");
			client.closeTransport();
			break;
			
		case "front_camera_button":
			break;
		case "back_camera_button":
			break;
		case "arm_camera_button":
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
			
		case "l2_controller_button":
			break;
		case "l1_controller_button":
			break;
		case "r2_controller_button":
			break;
		case "r1_controller_button":
			break;
		case "right_controller_button":
			break;
		case "left_controller_button":
			break;
		case "up_controller_button":
			break;
		case "down_controller_button":
			break;
		case "circle_controller_button":
			break;
		case "cross_controller_button":
			break;
		case "triangle_controller_button":
			break;
		case "rectangle_controller_button":
			break;
			
		case "submit_button":
			break;
			
		case "shutdown_button":
			client.closeTransport();
			System.exit(0);
			break;
		
		}
	}
}
